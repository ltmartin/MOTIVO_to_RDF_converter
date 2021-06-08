package base.converters;

import base.Application;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.io.FileNotFoundException;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest(classes = Application.class)
class MotivoToRdfConverterTest {

    @Resource
    private MotivoToRdfConverter converter;

    @Test
    void loadReplacements() {
       /* try {
            converter.loadReplacements();
            ConcurrentMap<Integer, String> replacementsMap = converter.getReplacementsMap();
            replacementsMap.forEach((k, o) -> {
                System.out.println(k + " " + o);
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    @Test
    void convert() {
       // converter.convert();
    }
}