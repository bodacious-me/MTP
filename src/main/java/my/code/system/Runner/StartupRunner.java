package my.code.system.Runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import my.code.system.service.DNSGenerator;

//  Method and Class to start the application

@Component
public class StartupRunner implements CommandLineRunner {

    @Autowired
    private DNSGenerator myDNSGenerator;
    @Override
    public void run(String... args) throws Exception {
        myDNSGenerator.DNSChanger();

    }

}
