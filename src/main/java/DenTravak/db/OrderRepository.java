package DenTravak.db;


// https://spring.io/guides/gs/accessing-data-jpa/

import DenTravak.domain.Order;
import DenTravak.domain.Sandwich;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OrderRepository extends CrudRepository<Order, UUID> {
    //List<Sandwich> findByName(String name);
}
