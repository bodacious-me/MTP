package my.code.system.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Configuration
@Component
public class Properties {
    private List<String> dnsId = new ArrayList<>();
    @Value("${dns1.id}")
    private String dns1Id;

    @Value("${dns2.id}")
    private String dns2Id;

    @Value("${dns3.id}")
    private String dns3Id;

    @Value("${dns4.id}")
    private String dns4Id;

    @Value("${dns5.id}")
    private String dns5Id;

    @Value("${dns6.id}")
    private String dns6Id;

    @Value("${dns7.id}")
    private String dns7Id;

    @Value("${dns8.id}")
    private String dns8Id;

    @Value("${dns9.id}")
    private String dns9Id;

    @Value("${dns10.id}")
    private String dns10Id;

    @PostConstruct
    public void init(){
        dnsId.add(dns1Id);
        dnsId.add(dns2Id);
        dnsId.add(dns3Id);
        dnsId.add(dns4Id);
        dnsId.add(dns5Id);
        dnsId.add(dns6Id);
        dnsId.add(dns7Id);
        dnsId.add(dns8Id);
        dnsId.add(dns9Id);
        dnsId.add(dns10Id);
    }

    public String getIds(int i){
        return dnsId.get(i);
    }

}
