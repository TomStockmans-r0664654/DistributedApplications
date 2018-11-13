package DenTravak.domain;

import java.util.UUID;

public class Order {
    private BreadType breadType;
    private UUID sandwichId;
    private String mobilePhone;

    public Order (){}

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

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public static class OrderBuilder{
        private BreadType breadType;
        private UUID sandwichId;
        private String mobilePhone;

        private OrderBuilder(){}

        public static OrderBuilder anOrder(){
            return new OrderBuilder();
        }

        public OrderBuilder withBreadType(BreadType breadType) {
            this.breadType = breadType; return this;
        }

        public OrderBuilder withSandwichId(UUID sandwichId) {
            this.sandwichId = sandwichId; return this;
        }

        public OrderBuilder withMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone; return this;
        }

        public Order build(){
            Order o = new Order();
            o.sandwichId = this.sandwichId;
            o.breadType = this.breadType;
            o.mobilePhone = this.mobilePhone;
            return o;
        }
    }
}
