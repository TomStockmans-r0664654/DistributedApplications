package DenTravak;

import DenTravak.db.SandwichRepository;
import DenTravak.domain.Sandwich;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static DenTravak.domain.Sandwich.SandwichBuilder.aSandwich;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(SandwichRepository repository) {
        return (args) -> {
            Sandwich s = aSandwich().withPrice(2).withName("test").withIngredients("testingr").build();
            repository.save(s);
        };
    }

}
