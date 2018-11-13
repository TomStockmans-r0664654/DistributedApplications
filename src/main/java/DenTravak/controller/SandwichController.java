package DenTravak.controller;

import DenTravak.domain.BreadType;
import DenTravak.domain.Order;
import DenTravak.domain.Sandwich;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static DenTravak.domain.Order.OrderBuilder.anOrder;
import static DenTravak.domain.Sandwich.SandwichBuilder.aSandwich;
import static DenTravak.domain.Ingredient.IngredientBuilder.anIngredient;

@RestController
public class SandwichController {




    @RequestMapping("/sandwich")
    public ArrayList<Sandwich> sandwich() {
        // lijst van sandwiches
        ArrayList<Sandwich> sandwiches = new ArrayList<Sandwich>();
        sandwiches.add(aSandwich()
                .withName("test")
                .withIngredients("testingredienten")
                .withPrice(2)
                .build());
        sandwiches.add(aSandwich()
                        .withName("test2")
                        .withIngredients("testingredientjes")
                        .withPrice(3)
                        .build());
        return sandwiches;
    }

    @RequestMapping(value="/order", method= RequestMethod.POST)
    public Order order(@RequestParam(value="sandwichId") UUID sandwichId,
                      @RequestParam(value="breadType") BreadType breadType,
                      @RequestParam(value="MobilePhone") String mobilePhone){
        Order o = anOrder()
                .withBreadType(breadType)
                .withSandwichId(sandwichId)
                .withMobilePhone(mobilePhone)
                .build();

        return o;
    }

}
