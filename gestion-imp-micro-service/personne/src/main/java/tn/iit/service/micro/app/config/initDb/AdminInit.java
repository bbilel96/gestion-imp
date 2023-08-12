package tn.iit.service.micro.app.config.initDb;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tn.iit.service.micro.app.model.Admin;
import tn.iit.service.micro.app.repository.AdminRepository;
import tn.iit.service.micro.utilenum.TypeUser;
import tn.iit.service.micro.utilenum.UtilisateurStatus;

import java.time.LocalDate;
import java.util.Date;
import java.util.stream.IntStream;
@Configuration
@RequiredArgsConstructor
public class AdminInit {
    @Bean
    CommandLineRunner commandLineRunnerFamille(AdminRepository repository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                    LocalDate date =  LocalDate.of(1996, 12, 12);
                    Admin admin = new Admin("12345678", date, "Mr", "Admin", "123", "admin@admin.com", "12345678", UtilisateurStatus.ACTIVER, TypeUser.ADMIN);


                    repository.save(admin);
            }
        };
    }
}
