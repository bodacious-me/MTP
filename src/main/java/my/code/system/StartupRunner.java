package my.code.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import my.code.system.service.DNSGenerator;

@Component
public class StartupRunner implements CommandLineRunner {

    @Autowired
    private DNSGenerator myDNSGenerator;

    @Override
    public void run(String... args) throws Exception {
      // Run Once Every 4 days:  myDNSGenerator.DNSChanger();
      myDNSGenerator.DNSChanger();
      
    }


    
}
