package DenTravak.controller;

import DenTravak.db.SandwichRepository;
import DenTravak.domain.*;
import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import javax.naming.ServiceUnavailableException;
import java.net.URI;
// REQUIRES CONSUL
import org.springframework.cloud.client.discovery.DiscoveryClient;

@RestController
public class SandwichController {

    // REQUIRES CONSUL
    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private SandwichRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/sandwiches")
    public Iterable<Sandwich> sandwich() {
        try {
            // voorlopig hardcoded omdat de user niet wordt doorgegeven naar de server (cookies?)
            Preferences preferences = getPreferences("0496");

            List<Sandwich> allSandwichesList = Lists.newArrayList(repository.findAll());

            // indien sommige broodjes niet gerated zijn kan een rating dus null teruggeven
            // hiervoor zien we dus dat enkel de gerate broodjes worden gesorteerd, de rest komt erna
            List<Sandwich> sortable = new ArrayList();
            List<Sandwich> unsortable = new ArrayList();
            for (Sandwich s : allSandwichesList)
            {
                if (preferences.getRatingForSandwich(s.getId()) != null) sortable.add(s);
                else unsortable.add(s);
            }
            Collections.sort(sortable, (e1, e2) -> Float.compare(
                    preferences.getRatingForSandwich(e2.getId()),
                    preferences.getRatingForSandwich(e1.getId())
            ));
            sortable.addAll(unsortable);
            return sortable;

            // als recommendation server niet werkt, neem gewoon de ongesorteerde lijst
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

    // LOCAL
//    @GetMapping("/getpreferences/{emailAddress}")
//    public Preferences getPreferences(@PathVariable String emailAddress) throws RestClientException, ServiceUnavailableException {
//        URI service = URI.create("http://localhost:8082/recommendation/recommend/" + emailAddress);
//        return restTemplate
//                .getForEntity(service, Preferences.class)
//                .getBody();
//    }

    // REQUIRES CONSUL
    @GetMapping("/getpreferences/{emailAddress}")
    public Preferences getPreferences(@PathVariable String emailAddress) throws RestClientException, ServiceUnavailableException {
        URI service = recommendationServiceUrl()
                .map(s -> s.resolve("/recommend/" + emailAddress))
                .orElseThrow(ServiceUnavailableException::new);
        return restTemplate
                .getForEntity(service, Preferences.class)
                .getBody();
    }

    // REQUIRES CONSUL
    public Optional<URI> recommendationServiceUrl() {
        return discoveryClient.getInstances("recommendation")
                .stream()
                .map(si -> si.getUri())
                .findFirst();
    }
}
