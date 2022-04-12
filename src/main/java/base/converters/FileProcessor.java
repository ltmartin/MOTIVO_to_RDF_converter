package base.converters;

import base.dataaccess.SparqlDAO;
import base.dataaccess.repository.MotifRepository;
import base.dataaccess.utils.SparqlUtils;
import base.domain.Motif;
import base.domain.Triple;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public class FileProcessor extends Thread{
    private final String COMMA_DELIMITER = ",";
    private String filename;
    private ConcurrentMap<Integer, String> replacementsMap;

    private SparqlDAO sparqlDAO;
    private MotifRepository motifRepository;


    public FileProcessor(String filename, ConcurrentMap<Integer, String> replacementsMap, SparqlDAO sparqlDAO, MotifRepository motifRepository) {
        this.filename = filename;
        this.replacementsMap = replacementsMap;
        this.sparqlDAO = sparqlDAO;
        this.motifRepository = motifRepository;
    }

    @Override
    public void run(){
        this.processFile();
    }

    public void processFile(){
        try (BufferedReader br = new BufferedReader(new FileReader("data/split/"+filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                List<String> nodes = new LinkedList<>();
                for (String nodeNumericalId : values) {
                    String originalNode = replacementsMap.get(Integer.parseInt(nodeNumericalId));
                    nodes.add(SparqlUtils.getSparqlCompatibleString(originalNode));
                }

                Motif motif = new Motif();
                for (String node : nodes) {
                    Set<Triple> triplesWithNodeAsSubject = sparqlDAO.getTriplesWithNodePlayingRole(node, SparqlDAO.ROLE_SUBJECT);
                    Set<Triple> triplesWithNodeAsObject = sparqlDAO.getTriplesWithNodePlayingRole(node, SparqlDAO.ROLE_OBJECT);

                    for (String adjacentNode: nodes) {
                        if (node.equals(adjacentNode))
                            continue;

                        triplesWithNodeAsSubject.parallelStream().forEach(ts -> {
                            if (SparqlUtils.getSparqlCompatibleString(ts.getObject()).equals(adjacentNode))
                                motif.addTriple(ts);
                        });

                        triplesWithNodeAsObject.parallelStream().forEach(to -> {
                            if (SparqlUtils.getSparqlCompatibleString(to.getSubject()).equals(adjacentNode))
                                motif.addTriple(to);
                        });
                    }
                }
                motifRepository.save(motif);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
