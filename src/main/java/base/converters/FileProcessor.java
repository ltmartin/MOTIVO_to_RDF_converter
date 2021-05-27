package base.converters;

import base.dataaccess.SparqlDAO;
import base.domain.Triple;

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

    @Resource
    private SparqlDAO sparqlDAO;

    public FileProcessor(String filename, ConcurrentMap<Integer, String> replacementsMap) {
        this.filename = filename;
        this.replacementsMap = replacementsMap;
    }

    @Override
    public void run(){
        this.processFile();
    }

    public void processFile(){
        try (BufferedReader br = new BufferedReader(new FileReader("split/"+filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                List<String> nodes = new LinkedList<>();
                for (String nodeNumericalId : values) {
                    String originalNode = replacementsMap.get(Integer.parseInt(nodeNumericalId));
                    nodes.add(originalNode);
                }

                for (String node : nodes) {
                    Set<Triple> triplesWithNodeAsSubject = sparqlDAO.getTriplesWithNodePlayingRole(node, SparqlDAO.ROLE_SUBJECT);
                    Set<Triple> triplesWithNodeAsObject = sparqlDAO.getTriplesWithNodePlayingRole(node, SparqlDAO.ROLE_OBJECT);
                    // TODO: Continue here.
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
