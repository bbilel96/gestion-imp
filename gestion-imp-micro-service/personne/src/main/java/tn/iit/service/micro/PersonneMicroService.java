package tn.iit.service.micro;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import tn.iit.service.micro.app.model.Enseignant;
import tn.iit.service.micro.app.model.Utilisateur;
import tn.iit.service.micro.mapper.DateMapper;
import tn.iit.service.micro.request.personne.EnseignantRequest;
import tn.iit.service.micro.request.personne.UtilisateurRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;

/**
 * Welcome to Personne MicroService
 */
@SpringBootApplication
@Slf4j
@EnableEurekaClient
@EnableFeignClients
public class PersonneMicroService {
    public static void main(String[] args) throws UnknownHostException {

        SpringApplication app = new SpringApplication(PersonneMicroService.class);
        Environment env = app.run(args).getEnvironment();

        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        log.info("""

                        ----------------------------------------------------------
                        \tApplication's name '{}' is running! Access URLs:
                        \tLocal: \t\t{}://localhost:{}
                        \tExternal: \t{}://{}:{}
                        \tProfile(s): \t{}
                        ----------------------------------------------------------""",
                env.getProperty("spring.application.name"),
                protocol,
                env.getProperty("server.port"),
                protocol,
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                env.getActiveProfiles());
    }



    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(String.class, LocalDate.class);
        modelMapper.addConverter(DateMapper.toLocalDateString);
        modelMapper.getTypeMap(String.class, LocalDate.class).setProvider(DateMapper.localDateProvider);
        return modelMapper;
    }

}
