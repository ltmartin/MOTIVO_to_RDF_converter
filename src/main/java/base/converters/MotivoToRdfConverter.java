package base.converters;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
@Lazy
public class MotivoToRdfConverter {

    private ConcurrentMap<Integer, String> replacementsMap;

    public MotivoToRdfConverter() {
        replacementsMap = new ConcurrentHashMap();
    }

    public void convert(){
        try {
            loadReplacements();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadReplacements() throws FileNotFoundException {
        File replacementsFile = new File("replacements.txt");
        Scanner scanner = new Scanner(replacementsFile);
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            int lastSpacePosition = line.lastIndexOf(" ");
            String node = line.substring(0, lastSpacePosition);
            Integer id = Integer.parseInt(line.substring(lastSpacePosition+1));
            replacementsMap.put(id, node);
        }
    }

    public ConcurrentMap<Integer, String> getReplacementsMap() {
        return replacementsMap;
    }
}
