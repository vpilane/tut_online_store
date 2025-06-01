package group1.tut.entities;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import group1.tut.entities.models.Account;
import group1.tut.entities.repository.AccountRepository;

@SpringBootApplication
public class EntitiesApplication {

    public static void main(String[] args) {
        SpringApplication.run(EntitiesApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(AccountRepository ar) {
        return args -> {
            Account aaa = new Account();
            aaa.setUsername("admin");
            aaa.setPassword("123");
			aaa.setRole("Administrator");
            ar.save(aaa);
            // ar.save(new Account("123456789", "123456789", "123456789", "123456789", "123456789", "123456789"));
        };
    }
}
