package my.code.system.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
@Component
public class DNSGenerator {
    @Autowired
    Properties myProperties;
    @Autowired
    NameGenerator nameGenerator;
    @Autowired
    JsonFileWriter jsonFileWriter;

    ObjectMapper objectMapper = new ObjectMapper();
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders httpHeaders = new HttpHeaders();
    ObjectMapper objectmapper = new ObjectMapper();
    private final ScheduledExecutorService schechuler = Executors.newScheduledThreadPool(1);

    public void DNSChanger() throws JsonMappingException, JsonProcessingException, UnsupportedEncodingException {
        // Clean the names.json file:

        try (FileWriter fileWriter = new FileWriter("./names.json", false)) {
            fileWriter.write("");
            System.out.println("Cleared Successfully");
        } catch (IOException e) {
            System.out.println("error Cleanering the file");
            e.printStackTrace();
        }
        // Generate the random NameServers
        List<String> DomainNames = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DomainNames.add(i, nameGenerator.nameGeneratorMethod());
        }
        // store them to the json file
        jsonFileWriter.writeFiles("./names.json", DomainNames);

        // Update the Dns records with the new random DNSs
        
        for (int i = 0; i < 10; i++) {
            String url = "https://api.cloudflare.com/client/v4/zones/207359c28ebf495446b657f15fb25a46/dns_records/"
                    + myProperties.getIds(i);
            httpHeaders.set("Authorization", "Bearer iHkMqqopGkxto9nHnSgzQgh81vQYVAHtXXsapw-X");
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            String jsonPayload = "{"
                    + "\"type\": \"A\","
                    + "\"name\": \"" + DomainNames.get(i) + "\","
                    + "\"content\": \"188.245.174.248\","
                    + "\"ttl\": 3600,"
                    + "\"proxied\": false"
                    + "}";
            HttpEntity<String> entity = new HttpEntity<>(jsonPayload, httpHeaders);
            String response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class).getBody();
            System.out.println(response);
        }

        // Publish the messages out
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
        List<String> messages = new ArrayList<String>();
        for (String key : SecretAndPort.keySet()) {
            for (int j = 0; j < 10; j++) {
                // messages.add("tg://proxy?server=" + DomainNames.get(j) + "&port=" +
                // SecretAndPort.get(key)
                // + "&secret=" + key);
                // t.me/proxy?server=<server>&port=<port>&secret=<secret>
                String proxyLink = String.format("t.me/proxy?server=%s&port=%s&secret=%s",
                        DomainNames.get(j),
                        SecretAndPort.get(key),
                        key);
                messages.add(proxyLink);
// https://t.me/share/url?url=https%3A%2F%2Ft.me%2Fproxy%3Fserver%3DKamilla.Liora.Meghan.Karime.Jenson.Kenyon.Muad.com.eriofndiohvndi.shop%26port%3D2142%26secret%3D0954a8517132e5b213c254bb2b563dd2&text=click
//https://t.me/proxy?server=Kamilla.Liora.Meghan.Karime.Jenson.Kenyo.Muad.com.eriofndiohvndi.shop&port=2142&secret=0954a8517132e5b213c254bb2b563dd2
            }
            // 
            try {
                objectMapper.writeValue(new File("./messages.json"),messages);
            } catch (Exception e) {
                System.out.println("Messages are not saved !!");
                e.printStackTrace();
            }
            // key : i
            // value : SecretAndPort.get(i);

        }
        for (int k = 0; k < 100; k++) {
            schechuler.schedule(() -> {
                try {
                    sendMessages();
                } catch (IOException e) {
                    System.out.println("Got Errors DickHead!! HAHAHAHAHAH");
                    e.printStackTrace();
                }
            }, k * 1, TimeUnit.HOURS);
        }

    }

    @Scheduled(fixedRate = 3600000, initialDelay = 15000)
    public void sendMessages() throws StreamReadException, DatabindException, IOException {
        System.out.println("Called the sendMessage method now!");
        List<String> messages = null;

        // Read the JSON file and convert it to a List<String>
        messages = objectMapper.readValue(new File("./messages.json"), new TypeReference<List<String>>() {
        });
        // String text = String.format("Here is the MTProto proxy link: <a
        // href=\"%s\">Click Here</a>", messages.get(0));
        // t.me/share?url=tg%3A%2F%2Fproxy%3Fserver%3DMaximilliano.Clarence.Kyia.Arial.Jayden.Francisco.Coralee.com.eriofndiohvndi.shop%26port%3D2145%26secret%3D28e8248e358b041e23400242a236319a&text=heheehe
        String text = messages.get(0);
        RestTemplate restTemplate = new RestTemplate();
        String CHANNEL_ID = "@MTProxyMTP";
        String BOT_TOKEN = "7592458999:AAFRC9GHHXj--x9lqc5_17AB28E-HXKbNv4";

        System.out.println(text);

        // String url =
        // String.format("https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s&parse_mode=HTML",
        // BOT_TOKEN, CHANNEL_ID, shareUrl);

        String url = String.format("https://api.telegram.org/bot%s/sendMessage",
                BOT_TOKEN);
                Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("chat_id", CHANNEL_ID);
                requestBody.put("text", text);
      //  restTemplate.getForObject(url, String.class);
      String response = restTemplate.postForObject(url, requestBody, String.class);

        messages.remove(0);

        try {
            objectMapper.writeValue(new File("./messages.json"), messages);
        } catch (Exception e) {
            System.out.println("Messages are not saved !!");
            e.printStackTrace();
        }

    }
}
// <a href="https://t.me/share/url?url=https%3A%2F%2Ft.me%2Fproxy%3Fserver%3DKamilla.Liora.Meghan.Karime.Jenson.Kenyon.Muad.com.eriofndiohvndi.shop%26port%3D2142%26secret%3D0954a8517132e5b213c254bb2b563dd2&text=click">Send via Telegram</a>
// https://t.me/share/url?url=https%3A%2F%2Ft.me%2Fproxy%3Fserver%3DKamilla.Liora.Meghan.Karime.Jenson.Kenyon.Muad.com.eriofndiohvndi.shop%26port%3D2142%26secret%3D0954a8517132e5b213c254bb2b563dd2&text=click
// cloudflare api token : iHkMqqopGkxto9nHnSgzQgh81vQYVAHtXXsapw-X
// zone id: 207359c28ebf495446b657f15fb25a46

// curl -X POST
// "https://api.cloudflare.com/client/v4/zones/207359c28ebf495446b657f15fb25a46/dns_records"
// \
// -H "Authorization: Bearer iHkMqqopGkxto9nHnSgzQgh81vQYVAHtXXsapw-X" \
// -H "Content-Type: application/json" \
// --data '{
// "type": "A",
// "name": "ikco.airbnb.telewebion.filimo.ir",
// "content": "188.245.174.248",
// "ttl": 3600,
// "proxied": false
// }'

// a7bbe9c3e696ca889982ac32829f6c0a

// curl -X PUT
// "https://api.cloudflare.com/client/v4/zones/207359c28ebf495446b657f15fb25a46/dns_records/a7bbe9c3e696ca889982ac32829f6c0a"
// \
// -H "Authorization: Bearer iHkMqqopGkxto9nHnSgzQgh81vQYVAHtXXsapw-X" \
// -H "Content-Type: application/json" \
// --data '{
// "type": "A", # Type of the DNS record (e.g., A, CNAME)
// "name": "newname.example.com", # New name for the DNS record
// "content": "188.245.174.248", # Current IP address or content
// "ttl": 3600, # Time to live
// "proxied": false # Proxy status
// }'
