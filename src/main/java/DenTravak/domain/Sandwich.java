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


// https://spring.io/guides/gs/accessing-data-jpa/
//@Entity
public class Sandwich {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID id;
    private String name;
    private List<Ingredient> ingredients = new ArrayList<Ingredient>();
    private BigDecimal price;

    // @Convert(converter = JpaJsonConverter)

    public Sandwich(){

    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    private void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public BigDecimal getPrice() {
        return price;
    }

    private void setPrice(BigDecimal price) {
        this.price = price;
    }

    public static class SandwichBuilder{

        private String name;
        private List<Ingredient> ingredients = new ArrayList<Ingredient>();
        private BigDecimal price;

        private SandwichBuilder(){}

        public static SandwichBuilder aSandwich() {
            return new SandwichBuilder();
        }

        public SandwichBuilder withName(String name) {
            this.name = name; return this;
        }

        public SandwichBuilder withIngredients(List<Ingredient> ingredients) {
            this.ingredients = ingredients; return this;
        }

        public SandwichBuilder withIngredient(Ingredient ingredient){
            this.ingredients.add(ingredient); return this;
        }

        public SandwichBuilder withIngredient(Ingredient.IngredientBuilder ingredientBuilder){
            this.ingredients.add(ingredientBuilder.build()); return this;
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
