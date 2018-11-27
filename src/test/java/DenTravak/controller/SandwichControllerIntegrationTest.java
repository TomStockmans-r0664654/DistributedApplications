package DenTravak.controller;




import DenTravak.Application;
import DenTravak.db.SandwichRepository;
import DenTravak.domain.Sandwich;
import static DenTravak.domain.SandwichTestBuilder.aSandwich;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.UUID;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SandwichControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @Autowired
    private SandwichRepository sandwichRepository;

    @Before
    public void setUpASavedSandwich() {
        sandwichRepository.deleteAll();
    }

    @Test
    public void testGetSandwiches_NoSavedSandwiches_EmptyList() throws JSONException {
        String actualSandwiches = httpGet("/sandwiches");
        String expectedSandwiches = "[]";

        assertThatJson(actualSandwiches).isEqualTo(expectedSandwiches);
    }

    @Test
    public void testPostSandwich() throws JSONException {
        Sandwich sandwich = aSandwich().withName("Americain").withIngredients("Vlees").withPrice(4.0).build();

        String actualSandwichAsJson = httpPost("/sandwiches", sandwich);
        String expectedSandwichAsJson = "{\"id\":\"${json-unit.ignore}\",\"name\":\"Americain\",\"ingredients\":\"Vlees\",\"price\":4}";

        assertThatJson(actualSandwichAsJson).isEqualTo(expectedSandwichAsJson);
    }

    @Test
    public void testPutSandwich() throws JSONException {
        Sandwich sandwich = aSandwich().withName("Americain").withIngredients("Vlees").withPrice(4.0).build();
        sandwichRepository.save(sandwich);

        UUID id = sandwich.getId();
        Sandwich s2 = sandwich;
        s2.setName("tonijn");
        s2.setIngredients("vis");
        s2.setPrice(new BigDecimal(3.0));

        String actualSandwichAsJson = httpPut("/sandwiches/"+id, s2);
        String expectedSandwichAsJson = "{\"id\":\"${json-unit.ignore}\",\"name\":\"tonijn\",\"ingredients\":\"vis\",\"price\":3}";

        assertThatJson(actualSandwichAsJson).isEqualTo(expectedSandwichAsJson);
    }

    @Test
    public void testGetSandwiches_WithSavedSandwiches_ListWithSavedSandwich() throws JSONException {
        Sandwich s1 = aSandwich().withName("Americain").withIngredients("Vlees").withPrice(4.0).build();
        Sandwich s2 = aSandwich().withName("tonijn").withIngredients("vis").withPrice(3.0).build();
        Sandwich s3 = aSandwich().withName("gezond").withIngredients("groenten").withPrice(5.0).build();
        sandwichRepository.save(s1);
        sandwichRepository.save(s2);
        sandwichRepository.save(s3);
        String actualSandwiches = httpGet("/sandwiches");
        String expectedSandwiches = "[" +
                "{\"id\":\"${json-unit.ignore}\",\"name\":\"Americain\",\"ingredients\":\"Vlees\",\"price\":4.0}," +

                "{\"id\":\"${json-unit.ignore}\",\"name\":\"tonijn\",\"ingredients\":\"vis\",\"price\":3.0}," +

                "{\"id\":\"${json-unit.ignore}\",\"name\":\"gezond\",\"ingredients\":\"groenten\",\"price\":5.0}" +

                "]";

        assertThatJson(actualSandwiches).isEqualTo(expectedSandwiches);
    }
}

