package my.code.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Service
public class DNSUpdater {
    @Autowired
    Properties myProperties;

    HttpHeaders httpHeaders = new HttpHeaders();
    RestTemplate restTemplate = new RestTemplate();

    public void updater(List<String> DomainNames) {
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
    }

}
