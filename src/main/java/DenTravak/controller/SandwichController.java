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
    @RequestMapping("/sandwich")
    public Iterable<Sandwich> sandwich() {
        // lijst van sandwiches
        return repository.findAll();
    }

    @RequestMapping(value="/sandwich", method= RequestMethod.POST)
    public Sandwich createSandwich(@RequestBody Sandwich s){
        repository.save(s);
        return s;
    }

    @RequestMapping(value="/sandwich/{id}", method= RequestMethod.PUT)
        public Sandwich updateSandwich(@RequestBody Sandwich s, @PathVariable UUID id) {

        if(!s.getId().equals(id)){

            System.out.println(s.getId());
            System.out.println(id);
            System.out.println("nok");
            return null;}
            Sandwich sand = repository.findById(id).get();

            if(sand != null) {


                    sand.setIngredients(s.getIngredients());
                    sand.setName(s.getName());
                    sand.setPrice(s.getPrice());

                    repository.save(sand);

            }
            return sand;
        }

        @RequestMapping(value="/sandwich/{id}", method= RequestMethod.DELETE)
        public void deleteSandwich(@PathVariable UUID id){

        repository.deleteById(id);

        }

        @RequestMapping(value="/sandwich/{id}", method= RequestMethod.GET)
        public Sandwich getSandwich(@PathVariable UUID id){

            return repository.findById(id).get();
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
