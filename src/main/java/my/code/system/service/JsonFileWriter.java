package my.code.system.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class JsonFileWriter {
    public void writeFiles(String path, List<String> list){
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new java.io.File(path), list);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
