package base.converters;

import base.dataaccess.SparqlDAO;
import base.dataaccess.repository.MotifRepository;
import org.apache.commons.io.Charsets;
import org.apache.commons.lang3.CharEncoding;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;

@Component
@Lazy
public class MotivoToRdfConverter {

    private ConcurrentMap<Integer, String> replacementsMap;
    @Resource
    private SparqlDAO sparqlDAO;
    @Resource
    private MotifRepository motifRepository;

    public MotivoToRdfConverter() {
        replacementsMap = new ConcurrentHashMap();
    }

    public void convert(){
        try {
            loadReplacements();
            File splitFolder = new File("data/split");
            Queue<String> filenames = new ConcurrentLinkedQueue<>();
            filenames.addAll(Arrays.asList(splitFolder.list()));

            while (!filenames.isEmpty()) {
                String fileToProcess = filenames.poll();
                FileProcessor processor = new FileProcessor(fileToProcess, replacementsMap, sparqlDAO, motifRepository);
                processor.start();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadReplacements() throws FileNotFoundException {
        File replacementsFile = new File("data/replacements.txt");
        Scanner scanner = new Scanner(replacementsFile, "utf-8");
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            int lastSpacePosition = line.lastIndexOf(" ");
            try {
                if (-1 != lastSpacePosition) {
                    String node = line.substring(0, lastSpacePosition);
                    Integer id = Integer.parseInt(line.substring(lastSpacePosition + 1));
                    replacementsMap.put(id, node);
                }
            } catch (NumberFormatException e){
                System.out.println("===========================================");
                System.out.println("Error parsing the replacement: " + line);
                System.out.println("===========================================");
            }
        }
    }

}
