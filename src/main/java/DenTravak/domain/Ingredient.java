package DenTravak.domain;

public class Ingredient {

    private String name;

    public Ingredient() {}

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public static class IngredientBuilder{
        private String name;
        private IngredientBuilder(){}

        public static IngredientBuilder anIngredient(){
            return new IngredientBuilder();
        }

        public IngredientBuilder withName(String name){
            this.name = name; return this;
        }

        public Ingredient build(){
            Ingredient i = new Ingredient();
            i.name = this.name;
            return i;
        }
    }
}
