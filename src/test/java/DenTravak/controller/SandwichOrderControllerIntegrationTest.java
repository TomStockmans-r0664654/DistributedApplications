package DenTravak.controller;



import DenTravak.Application;
import DenTravak.db.OrderRepository;
import DenTravak.db.SandwichRepository;
import DenTravak.domain.BreadType;
import DenTravak.domain.Order;
import DenTravak.domain.Sandwich;
import DenTravak.domain.SandwichTestBuilder;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static DenTravak.domain.OrderTestBuilder.aOrder;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SandwichOrderControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @Autowired
    private SandwichRepository sandwichRepository;
    @Autowired
    private OrderRepository sandwichOrderRepository;

    private Sandwich savedSandwich;

    @Before
    public void setUpASavedSandwich() {
        sandwichRepository.deleteAll();
        sandwichOrderRepository.deleteAll();
        savedSandwich = sandwichRepository.save(SandwichTestBuilder.aSandwich().withName("Americain").withIngredients("vlees").withPrice(3.5).build());
    }

    @Test
    public void testGetSandwichOrders_NoOrdersSaved_EmptyList() throws JSONException {
        String actualSandwiches = httpGet("/orders");
        String expectedSandwiches = "[]";

        assertThatJson(actualSandwiches).isEqualTo(expectedSandwiches);
    }

    @Test
    public void testPostSandwichOrder() throws JSONException {
        Order sandwichOrder = aOrder().forSandwich(savedSandwich).withBreadType(BreadType.BOTERHAMMEKES).withMobilePhoneNumber("0487/123456").build();
        String actualSandwiches = httpPost("/orders", sandwichOrder);
        String expectedSandwiches = "{\"id\":\"${json-unit.ignore}\",\"sandwichId\":\"" + savedSandwich.getId() + "\",\"name\":\"Americain\",\"breadType\":\"BOTERHAMMEKES\",\"creationDate\":\"${json-unit.ignore}\",\"price\":3.5,\"mobilePhoneNumber\":\"0487/123456\"}";

        assertThatJson(actualSandwiches).isEqualTo(expectedSandwiches);
    }

    @Test
    public void testGetSandwichOrders_WithOrdersSaved_ReturnsListWithOrders() throws JSONException {
        Order r1 = aOrder().forSandwich(savedSandwich).withBreadType(BreadType.BOTERHAMMEKES).withMobilePhoneNumber("0487/123456").build();
        Order r2 = aOrder().forSandwich(savedSandwich).withBreadType(BreadType.TURKISH_BREAD).withMobilePhoneNumber("0496/209967").build();
        Order r3 = aOrder().forSandwich(savedSandwich).withBreadType(BreadType.WRAP).withMobilePhoneNumber("016/258173").build();
        sandwichOrderRepository.save(r1);
        sandwichOrderRepository.save(r2);
        sandwichOrderRepository.save(r3);

        String actualSandwiches = httpGet("/orders");
        String expectedSandwiches = "[" +
                "{\"id\":\"${json-unit.ignore}\",\"sandwichId\":\"" + savedSandwich.getId() + "\",\"name\":\"Americain\",\"breadType\":\"BOTERHAMMEKES\",\"creationDate\":\"${json-unit.ignore}\",\"price\":3.5,\"mobilePhoneNumber\":\"0487/123456\"}," +
                "{\"id\":\"${json-unit.ignore}\",\"sandwichId\":\"" + savedSandwich.getId() + "\",\"name\":\"Americain\",\"breadType\":\"TURKISH_BREAD\",\"creationDate\":\"${json-unit.ignore}\",\"price\":3.5,\"mobilePhoneNumber\":\"0496/209967\"}," +
                "{\"id\":\"${json-unit.ignore}\",\"sandwichId\":\"" + savedSandwich.getId() + "\",\"name\":\"Americain\",\"breadType\":\"WRAP\",\"creationDate\":\"${json-unit.ignore}\",\"price\":3.5,\"mobilePhoneNumber\":\"016/258173\"}" +
                "]";

        assertThatJson(actualSandwiches).isEqualTo(expectedSandwiches);
    }

}

