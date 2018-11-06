package DenTravak.controller;

import DenTravak.domain.Ingredient;
import DenTravak.domain.Sandwich;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SandwichController {

    private List<Sandwich> sandwiches = new ArrayList<Sandwich>();

    @RequestMapping("/sandwich")
    public Sandwich sandwich() {
        Sandwich test = Sandwich.SandwichBuilder.aSandwich()
                .withName("test")
                .withIngredient(Ingredient.IngredientBuilder.anIngredient()
                    .withName("test"))
                .withPrice(2)
                .build();
        return test;

    }

}
