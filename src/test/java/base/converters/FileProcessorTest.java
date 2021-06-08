package base.converters;

import base.Application;
import base.dataaccess.SparqlDAO;
import base.dataaccess.repository.MotifRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.io.FileNotFoundException;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest(classes = Application.class)
class FileProcessorTest {

    @Resource
    private MotivoToRdfConverter converter;
    @Resource
    private SparqlDAO sparqlDAO;
    @Resource
    private MotifRepository motifRepository;

    @Test
    void processFile() {
        /*try {
            converter.loadReplacements();
            ConcurrentMap<Integer, String> replacementsMap = converter.getReplacementsMap();

            FileProcessor fileProcessor = new FileProcessor("split9.csv", replacementsMap, sparqlDAO, motifRepository);
            fileProcessor.processFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/

    }
}