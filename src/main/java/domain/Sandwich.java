package domain;

import java.util.List;

public class Sandwich {

    public Sandwich(){

    }

    private String name;
    private List<Ingredients> ingredients;
    private double price;

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    private void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public double getPrice() {
        return price;
    }

    private void setPrice(double price) {
        this.price = price;
    }


    public static class SandwichBuilder{

    }

}
