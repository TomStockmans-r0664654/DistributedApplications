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

import static DenTravak.domain.Sandwich.SandwichBuilder.aSandwich;


@SpringBootApplication
public class Application {

    //private SandwichRepository srepository;
    //private OrderRepository orepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(SandwichRepository srepository, OrderRepository orepository) {
        return (args) -> {
            Sandwich s = aSandwich().withPrice(2).withName("test").withIngredients("testingr").build();
            srepository.save(s);
            //Order o = Order.OrderBuilder.anOrder().build();
            //orepository.save(o);
        };
    }

    @Configuration
    public class WebConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**");
        }
    }

}
