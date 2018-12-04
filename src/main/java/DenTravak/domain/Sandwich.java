package DenTravak.domain;

import java.math.BigDecimal;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;


// https://spring.io/guides/gs/accessing-data-jpa/
@Entity
public class Sandwich {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    private String name;
    private String ingredients;
    private BigDecimal price;

    // @Convert(converter = JpaJsonConverter) -> enkel nodig indien lijst of object

    public Sandwich(){

    }

    // getter setter for uuid

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public static class SandwichBuilder{

        private String name;
        private String ingredients;
        private BigDecimal price;

        private SandwichBuilder(){}

        public static SandwichBuilder aSandwich() {
            return new SandwichBuilder();
        }

        public SandwichBuilder withName(String name) {
            this.name = name; return this;
        }

        public SandwichBuilder withIngredients(String ingredients) {
            this.ingredients = ingredients; return this;
        }

        public SandwichBuilder withPrice(BigDecimal price) {
            this.price = price; return this;
        }

        public SandwichBuilder withPrice(double price) {
            this.price = BigDecimal.valueOf(price); return this;
        }

        public Sandwich build(){
            Sandwich s = new Sandwich();
            s.name = this.name;
            s.ingredients = this.ingredients;
            s.price = this.price;
            return s;
        }
    }

}
