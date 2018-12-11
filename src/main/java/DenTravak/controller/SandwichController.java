package DenTravak.controller;

import DenTravak.db.OrderRepository;
import DenTravak.db.SandwichRepository;
import DenTravak.domain.BreadType;
import DenTravak.domain.Order;
import DenTravak.domain.Sandwich;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.UUID;
import DenTravak.domain.SandwichPreferences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import javax.naming.ServiceUnavailableException;
import java.net.URI;
import java.util.Optional;

import static DenTravak.domain.Order.OrderBuilder.anOrder;

@RestController
public class SandwichController {

    //private List<Sandwich> sandwiches = new ArrayList<Sandwich>();
    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private SandwichRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    //private OrderRepository orepository;

//    public SandwichController(SandwichRepository repository, OrderRepository orepository) {
//        this.repository = repository;
//        this.orepository = orepository;
//    }


    @RequestMapping("/sandwiches")
    public Iterable<Sandwich> sandwich() {
        // lijst van sandwiches
        //return repository.findAll();
        try {
            SandwichPreferences preferences = getPreferences("ronald.dehuysser@ucll.be");
            //TODO: sort allSandwiches by float in preferences
            Iterable<Sandwich> allSandwiches = repository.findAll();
            return allSandwiches;
        } catch (ServiceUnavailableException e) {
            return repository.findAll();
        }
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

    // why comment: for testing
    @GetMapping("/getpreferences/{emailAddress}")
    public SandwichPreferences getPreferences(@PathVariable String emailAddress) throws RestClientException, ServiceUnavailableException {
        URI service = recommendationServiceUrl()
                .map(s -> s.resolve("/recommend/" + emailAddress))
                .orElseThrow(ServiceUnavailableException::new);
        return restTemplate
                .getForEntity(service, SandwichPreferences.class)
                .getBody();
    }

    public Optional<URI> recommendationServiceUrl() {
        return discoveryClient.getInstances("recommendation")
                .stream()
                .map(si -> si.getUri())
                .findFirst();
    }
}
