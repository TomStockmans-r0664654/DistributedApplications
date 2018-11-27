package DenTravak.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class OrderTestBuilder {

    private UUID sandwichId;
    private String name;
    private BreadType breadType;
    private LocalDateTime creationDate;
    private BigDecimal price;
    private String mobilePhoneNumber;

    private OrderTestBuilder() {
    }

    public static OrderTestBuilder aOrder() {
        return new OrderTestBuilder();
    }

    public OrderTestBuilder forSandwich(Sandwich sandwich) {
        this.sandwichId = sandwich.getId();
        this.name = sandwich.getName();
        this.price = sandwich.getPrice();
        return this;
    }

    public OrderTestBuilder withSandwichId(UUID sandwichId) {
        this.sandwichId = sandwichId;
        return this;
    }

    public OrderTestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public OrderTestBuilder withBreadType(BreadType breadType) {
        this.breadType = breadType;
        return this;
    }

    public OrderTestBuilder withCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public OrderTestBuilder withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public OrderTestBuilder withMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
        return this;
    }

    public Order build() {
        Order order = new Order();
        order.setSandwichId(sandwichId);
        order.setName(name);
        order.setBreadType(breadType);
        order.setCreationDate(creationDate);
        order.setMobilePhoneNumber(mobilePhoneNumber);
        order.setPrice(price);
        return order;
    }
}