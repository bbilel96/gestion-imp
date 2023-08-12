package tn.iit.service.micro;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.PageJacksonModule;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import tn.iit.service.micro.mapper.DateMapper;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@Slf4j
@EnableEurekaClient
@EnableFeignClients
public class TirageMicroService
{
    public static void main( String[] args )throws UnknownHostException
    {
        SpringApplication app = new SpringApplication(TirageMicroService.class);
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
    public ModelMapper modelMapper(){

        ModelMapper modelMapper =  new ModelMapper();
        modelMapper.createTypeMap(String.class, LocalDateTime.class);
        modelMapper.addConverter(DateMapper.toLocalDateTimeString);
        modelMapper.getTypeMap(String.class, LocalDateTime.class).setProvider(DateMapper.localDateTimeProvider);
        return modelMapper;
    }

}
