package DenTravak.controller;

import DenTravak.db.SandwichRepository;
import DenTravak.domain.*;
import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import javax.naming.ServiceUnavailableException;
import java.net.URI;

@RestController
public class SandwichController {

    //private List<Sandwich> sandwiches = new ArrayList<Sandwich>();
//    @Autowired
//    private DiscoveryClient discoveryClient;

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
            Preferences preferences = getPreferences("0496");
            for (UUID key : preferences.keySet()) {
                System.out.println(key);
            }
            System.out.println("");
            //TODO: sort allSandwiches by float in preferences

            List<Sandwich> allSandwichesList = Lists.newArrayList(repository.findAll());
            for (Sandwich key : allSandwichesList) {
                System.out.println(key.getId());

            }

            List<Sandwich> sortable = new ArrayList();
            List<Sandwich> unsortable = new ArrayList();
            for (Sandwich s : allSandwichesList)
            {
                System.out.println(preferences.getRatingForSandwich(s.getId()));
                if (preferences.getRatingForSandwich(s.getId()) != null) sortable.add(s);
                else unsortable.add(s);
            }

            for(Sandwich s: sortable){
                System.out.println(s.getPrice());
            }

            Collections.sort(sortable, (e1, e2) -> Float.compare(
                    preferences.getRatingForSandwich(e2.getId()),
                    preferences.getRatingForSandwich(e1.getId())
            ));
            sortable.addAll(unsortable);

            for(Sandwich s: sortable){
                System.out.println(s.getPrice());
            }

            return sortable;


        } catch (ServiceUnavailableException e) {
            System.out.println(e.getMessage());
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
    public Preferences getPreferences(@PathVariable String emailAddress) throws RestClientException, ServiceUnavailableException {
        URI service = URI.create("http://localhost:8082" + "/recommendation/recommend/" + emailAddress);//recommendationServiceUrl()
        System.out.println(service.toString());
                //.map(s -> s.resolve("/recommend/" + emailAddress))
                //.orElseThrow(ServiceUnavailableException::new);
        return restTemplate
                .getForEntity(service, Preferences.class)
                .getBody();
    }

//    public Optional<URI> recommendationServiceUrl() {
//        return discoveryClient.getInstances("recommendation")
//                .stream()
//                .map(si -> si.getUri())
//                .findFirst();
//    }
}
