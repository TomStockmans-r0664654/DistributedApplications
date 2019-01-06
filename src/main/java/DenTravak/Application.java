package DenTravak;

import DenTravak.db.OrderRepository;
import DenTravak.db.SandwichRepository;
import DenTravak.domain.Order;
import DenTravak.domain.Sandwich;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import static DenTravak.domain.Sandwich.SandwichBuilder.aSandwich;
// REQUIRES CONSUL
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
// REQUIRES CONSUL
@EnableDiscoveryClient
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(SandwichRepository srepository, OrderRepository orepository) {
        return (args) -> {
            Sandwich s = aSandwich().withPrice(1).withName("test1").withIngredients("testingr").build();
            srepository.save(s);
            s = aSandwich().withPrice(5).withName("test5").withIngredients("kaas").build();
            srepository.save(s);
            s = aSandwich().withPrice(2).withName("test2").withIngredients("kaas").build();
            srepository.save(s);
            s = aSandwich().withPrice(4).withName("test4").withIngredients("kaas").build();
            srepository.save(s);
            s = aSandwich().withPrice(3).withName("test3").withIngredients("kaas").build();
            srepository.save(s);
        };
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Configuration
    public class WebConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD")
            ;
        }
    }

}
