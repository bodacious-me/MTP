package my.code.system.service;

import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public class JsonFileCleaner {
    public void cleaner(){
                try (FileWriter fileWriter = new FileWriter("./names.json", false)) {
            fileWriter.write("");
            System.out.println("Cleared Successfully");
        } catch (IOException e) {
            System.out.println("error Cleanering the file");
            e.printStackTrace();
        }
    }
}
