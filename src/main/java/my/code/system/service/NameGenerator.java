package my.code.system.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.ResponseEntity;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

@Service
public class NameGenerator {
    public String nameGeneratorMethod() throws JsonMappingException, JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        String url = "https://randommer.io/api/Name?nameType=firstname&quantity=7";
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "*/*");
        headers.set("X-Api-Key", "e1185a2c6510480f8542aa5a72d19572");
        
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<String> jsonResponse = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        List<String> names = objectMapper.readValue(jsonResponse.getBody(), new TypeReference<List<String>>() {});
        
        String result = String.join(".", names);
        
        return result + ".com.eriofndiohvndi.shop";
    }
}
