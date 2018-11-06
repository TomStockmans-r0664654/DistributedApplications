package DenTravak.domain;

import java.util.List;

public class Sandwich {

    private String name;
    private List<Ingredient> ingredients;
    private double price;

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

    public double getPrice() {
        return price;
    }

    private void setPrice(double price) {
        this.price = price;
    }


    public static class SandwichBuilder{

        private String name;
        private List<Ingredient> ingredients;
        private double price;

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


        public SandwichBuilder withPrice(double price) {
            this.price = price; return this;
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
