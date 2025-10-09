package ma.pharmachain.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VaultTestRunner implements CommandLineRunner {

    @Value("${user}")
    private String user;

    @Value("${pass}")
    private String pass;

    @Value("${code}")
    private String code;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("========================================");
        System.out.println("Vault Connection Test");
        System.out.println("========================================");
        System.out.println("Username: " + user);
        System.out.println("Password: " + pass);
        System.out.println("Code: " + code);
        System.out.println("========================================");
        System.out.println("Successfully retrieved secrets from Vault!");
        System.out.println("========================================");
    }
}

