package DenTravak.controller;

import DenTravak.domain.BreadType;
import DenTravak.domain.CSVGenerator;
import DenTravak.domain.Order;
import DenTravak.db.OrderRepository;
import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@RestController
public class OrderController {

    private OrderRepository repository;

    public OrderController(OrderRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public Order order(@RequestBody Order order) {
        repository.save(order);
        return order;
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public Iterable<Order> getOrder() {
        return repository.findAll();
    }

    @RequestMapping(value = "/breadtypes", method = RequestMethod.GET)
    public BreadType[] getBreadTypes(){
        return BreadType.values();
    }

    @RequestMapping(value = "/csv", method = RequestMethod.GET)
    public void getcsv(HttpServletResponse response) throws IOException {
        CSVGenerator g = new CSVGenerator(Lists.newArrayList(repository.findAll()),response);
        g.GenerateCsv();
    }

}