package DenTravak.controller;

import DenTravak.db.OrderRepository;
import DenTravak.db.SandwichRepository;
import DenTravak.domain.BreadType;
import DenTravak.domain.Order;
import DenTravak.domain.Sandwich;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.UUID;

import static DenTravak.domain.Order.OrderBuilder.anOrder;

@RestController
public class SandwichController {

    //private List<Sandwich> sandwiches = new ArrayList<Sandwich>();

    private SandwichRepository repository;
    private OrderRepository orepository;

    public SandwichController(SandwichRepository repository, OrderRepository orepository) {
        this.repository = repository;
        this.orepository = orepository;
    }

    @RequestMapping("/sandwiches")
    public Iterable<Sandwich> sandwich() {
        // lijst van sandwiches
        return repository.findAll();
    }

    @RequestMapping(value="/sandwiches", method= RequestMethod.POST)
    public Sandwich createSandwich(@RequestBody Sandwich s){
        repository.save(s);
        return s;
    }

    @RequestMapping(value = "/sandwiches/{id}", method = RequestMethod.PUT)
    public Sandwich updateSandwich(@RequestBody Sandwich s, @PathVariable UUID id) {
        if (!s.getId().equals(id)) {
            System.out.println(s.getId());
            System.out.println(id);
            System.out.println("nok");
            return null;
        }
        Sandwich sand = repository.findById(id).get();
        if (sand != null) {
            sand.setIngredients(s.getIngredients());
            sand.setName(s.getName());
            sand.setPrice(s.getPrice());
            repository.save(sand);
        }
        return sand;
    }

    @RequestMapping(value = "/sandwiches/{id}", method = RequestMethod.DELETE)
    public void deleteSandwich(@PathVariable UUID id) {
        repository.deleteById(id);
    }

    @RequestMapping(value = "/sandwiches/{id}", method = RequestMethod.GET)
    public Sandwich getSandwich(@PathVariable UUID id) {
        return repository.findById(id).get();
    }


    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public Order order(@RequestBody Order order) {
        orepository.save(order);
        return order;
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public Iterable<Order> getOrder() {
        return orepository.findAll();
    }

    @RequestMapping(value = "/breadtypes", method = RequestMethod.GET)
    public BreadType[] getBreadTypes(){
        return BreadType.values();
    }
}
