package DenTravak.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "SandwichOrder")
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    private UUID sandwichId;
    private String name;
    private BreadType breadType;
    private LocalDateTime creationDate;
    private BigDecimal price;
    private String mobilePhoneNumber;

    public Order (){}


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public BreadType getBreadType() {
        return breadType;
    }
    public void setBreadType(BreadType breadType) {
        this.breadType = breadType;
    }

    public UUID getSandwichId() {
        return sandwichId;
    }
    public void setSandwichId(UUID sandwichId) {
        this.sandwichId = sandwichId;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }
    public void setMobilePhoneNumber(String mobilePhone) {
        this.mobilePhoneNumber = mobilePhone;
    }

    public static class OrderBuilder{
        private UUID sandwichId;
        private String name;
        private BreadType breadType;
        private LocalDateTime creationDate;
        private BigDecimal price;
        private String mobilePhoneNumber;

        private OrderBuilder(){}

        public static OrderBuilder anOrder(){
            return new OrderBuilder();
        }


        public OrderBuilder withName(String name) {
            this.name = name; return this;
        }

        public OrderBuilder withCreationDate(LocalDateTime creationDate) {
            this.creationDate = creationDate; return this;
        }

        public OrderBuilder withPrice(BigDecimal price) {
            this.price = price; return this;
        }

        public OrderBuilder withBreadType(BreadType breadType) {
            this.breadType = breadType; return this;
        }

        public OrderBuilder withSandwichId(UUID sandwichId) {
            this.sandwichId = sandwichId; return this;
        }

        public OrderBuilder withMobilePhoneNumber(String mobilePhone) {
            this.mobilePhoneNumber = mobilePhone; return this;
        }

        public Order build(){
            Order o = new Order();
            o.sandwichId = this.sandwichId;
            o.breadType = this.breadType;
            o.creationDate = this.creationDate;
            o.name = this.name;
            o.price = this.price;
            o.mobilePhoneNumber = this.mobilePhoneNumber;
            return o;
        }
    }
}
