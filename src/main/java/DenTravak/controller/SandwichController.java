package DenTravak.controller;

import DenTravak.db.SandwichRepository;
import DenTravak.domain.BreadType;
import DenTravak.domain.Order;
import DenTravak.domain.Sandwich;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static DenTravak.domain.Order.OrderBuilder.anOrder;

import static DenTravak.domain.Ingredient.IngredientBuilder.anIngredient;

@RestController
public class SandwichController {

    //private List<Sandwich> sandwiches = new ArrayList<Sandwich>();


    private SandwichRepository repository;
    public SandwichController(SandwichRepository repository){
        this.repository = repository;
    }
    @RequestMapping("/sandwiches")
    public Iterable<Sandwich> sandwich() {
        // lijst van sandwiches
        return repository.findAll();
    }

    @RequestMapping(value="/sandwiches", method= RequestMethod.POST)
    public void createSandwich(@RequestBody Sandwich s){
        repository.save(s);
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
        //TODO save to DB
        return o;
    }

}
