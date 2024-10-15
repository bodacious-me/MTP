package my.code.system.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import org.springframework.scheduling.annotation.Scheduled;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Component
public class DNSGenerator {
    @Autowired
    Properties myProperties;
    @Autowired
    NameGenerator nameGenerator;
    @Autowired
    JsonFileWriter jsonFileWriter;
    @Autowired
    JsonFileCleaner jsonFileCleaner;
    @Autowired
    DNSUpdater dnsUpdater;

    ObjectMapper objectMapper = new ObjectMapper();
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders httpHeaders = new HttpHeaders();
    ObjectMapper objectmapper = new ObjectMapper();

    public void DNSChanger() throws JsonMappingException, JsonProcessingException, UnsupportedEncodingException {

    }

    @Scheduled(fixedRate = 3600000, initialDelay = 240000)
    public void sendMessages() throws StreamReadException, DatabindException, IOException {
        System.out.println("Called the Send Message");
        // Read the JSON file and convert it to a List<String>
        List<String> messages = objectMapper.readValue(new File("./messages.json"), new TypeReference<List<String>>() {
        });
        String text = messages.get(0);
        String CHANNEL_ID = "@MTProxyMTP";
        String BOT_TOKEN = "7592458999:AAFRC9GHHXj--x9lqc5_17AB28E-HXKbNv4";

        // print the text
        System.out.println(text);

        String url = String.format("https://api.telegram.org/bot%s/sendMessage", BOT_TOKEN);
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("chat_id", CHANNEL_ID);
        requestBody.put("text", text);
        requestBody.put("parse_mode", "HTML");
        String response = restTemplate.postForObject(url, requestBody, String.class);
        System.out.println(response);

        // remove the used one
        messages.remove(0);
        // update the files
        try {
            objectMapper.writeValue(new File("./messages.json"), messages);
        } catch (Exception e) {
            System.out.println("Messages are not saved !!");
            e.printStackTrace();
        }

    }

    @Scheduled(fixedRate = 360000000, initialDelay = 60000)
    public void autoDNSUpdate1() throws JsonMappingException, JsonProcessingException {
        System.out.println("Called the S1");

        List<String> newDomains = new ArrayList<>();
        List<String> messages = new ArrayList<String>();

        // Generate random name servers
        for (int i = 0; i < 5; i++) {
            newDomains.add(i, nameGenerator.nameGeneratorMethod());
        }
        // Update the Dns records with the new random DNSs
        dnsUpdater.updater(newDomains);
        System.out.println("actuall dns updated now");
        // save the Secrets and Ports
        HashMap<String, Integer> SecretAndPort = new HashMap<String, Integer>();
        SecretAndPort.put("f5ae58e59e5be1f76485e870e483f6ec", 2137);
        SecretAndPort.put("827428c8345247c17e57b7a3e11bb369", 2138);
        SecretAndPort.put("a10ab1b7507faa3af0ee79fc70d2d2c4", 2139);
        SecretAndPort.put("b78a8e86c056d4ad204b4a9f093aa6c8", 2140);
        SecretAndPort.put("ec21a334ee6c1a459418ef15e2b5a374", 2141);
        SecretAndPort.put("0954a8517132e5b213c254bb2b563dd2", 2142);
        SecretAndPort.put("ebf82b3f4e236d623999671d72dc03e6", 2143);
        SecretAndPort.put("f820a6ac6d7528192baa22e980756061", 2144);
        SecretAndPort.put("28e8248e358b041e23400242a236319a", 2145);
        SecretAndPort.put("43278b738febd9dc2130bea84f966af7", 2146);
        // Generate the message:

        System.out.println("all names: " + newDomains);

        for (String key : SecretAndPort.keySet()) {
            for (int j = 0; j < 5; j++) {
                String proxyLink = String.format("♾♾♾♾♾♾♾♾♾\n" +
                        "<a href=\"" + "t.me/proxy?server=%s&port=%s&secret=%s" + "\">Server   " + newDomains.get(j)
                        + "\n" +
                        "Port     " + SecretAndPort.get(key) + "\n" +
                        "Secret   " + key + "</a>\n" +
                        "♾♾♾♾♾♾♾♾♾\n" +
                        "Location 🇩🇪\n" +
                        "♾♾♾♾♾♾♾♾♾\n" +
                        "Sponsor @MTProxyMTP\n" +
                        "♾♾♾♾♾♾♾♾♾",
                        newDomains.get(j),
                        SecretAndPort.get(key),
                        key);
                messages.add(proxyLink);
                System.out.println("Adding a message");
            }
            try {
                objectMapper.writeValue(new File("./messages.json"), messages);
                System.out.println("added the messages");
            } catch (Exception e) {
                System.out.println("Messages are not saved !!");
                e.printStackTrace();
            }
            System.out.println("add all messages");

        }

    }

    @Scheduled(fixedRate = 360000000, initialDelay = 120000)
    public void autoDNSUpdate2() throws JsonMappingException, JsonProcessingException {
        System.out.println("Called the S2");

        List<String> newDomains = new ArrayList<>();
        List<String> messages = new ArrayList<String>();

        // Generate random name servers
        for (int i = 0; i < 5; i++) {
            newDomains.add(i, nameGenerator.nameGeneratorMethod());
        }

        // Update the Dns records with the new random DNSs
        dnsUpdater.updater(newDomains);

        // save the Secrets and Ports
        HashMap<String, Integer> SecretAndPort = new HashMap<String, Integer>();
        SecretAndPort.put("f5ae58e59e5be1f76485e870e483f6ec", 2137);
        SecretAndPort.put("827428c8345247c17e57b7a3e11bb369", 2138);
        SecretAndPort.put("a10ab1b7507faa3af0ee79fc70d2d2c4", 2139);
        SecretAndPort.put("b78a8e86c056d4ad204b4a9f093aa6c8", 2140);
        SecretAndPort.put("ec21a334ee6c1a459418ef15e2b5a374", 2141);
        SecretAndPort.put("0954a8517132e5b213c254bb2b563dd2", 2142);
        SecretAndPort.put("ebf82b3f4e236d623999671d72dc03e6", 2143);
        SecretAndPort.put("f820a6ac6d7528192baa22e980756061", 2144);
        SecretAndPort.put("28e8248e358b041e23400242a236319a", 2145);
        SecretAndPort.put("43278b738febd9dc2130bea84f966af7", 2146);
        // Generate the message:

        for (String key : SecretAndPort.keySet()) {
            for (int j = 0; j < 5; j++) {
                String proxyLink = String.format("♾♾♾♾♾♾♾♾♾\n" +
                        "<a href=\"" + "t.me/proxy?server=%s&port=%s&secret=%s" + "\">Server   " + newDomains.get(j)
                        + "\n" +
                        "Port     " + SecretAndPort.get(key) + "\n" +
                        "Secret   " + key + "</a>\n" +
                        "♾♾♾♾♾♾♾♾♾\n" +
                        "Location 🇬🇧\n" +
                        "♾♾♾♾♾♾♾♾♾\n" +
                        "Sponsor @MTProxyMTP\n" +
                        "♾♾♾♾♾♾♾♾♾",
                        newDomains.get(j),
                        SecretAndPort.get(key),
                        key);
                messages.add(proxyLink);
            }
            try {
                objectMapper.writeValue(new File("./messages.json"), messages);
            } catch (Exception e) {
                System.out.println("Messages are not saved !!");
                e.printStackTrace();
            }

        }
    }
}
