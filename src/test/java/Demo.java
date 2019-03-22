import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class Demo {
    @Test
    public void testJson() {
        given()
                .when().get("http://127.0.0.1:8000/testerhome.json")
                .then()
                .body("lotto.winners.winnerId", hasItems(23, 54))
                .body("lotto.lottoId", equalTo(5))
        // **不支持json
//                .body("**.find {it.winnerId == 23}.winnerId", equalTo(23))
        ;
    }

    @Test
    public void testJson2() {
        when().get("http://127.0.0.1:8000/testerhome2.json")
                .then()
                .body("store.book.category", hasItems("reference"))
                .body("store.book[0].author", equalTo("Nigel Rees"))
                .body("store.book.findAll { book -> book.price == 8.95f }.title[0]", equalTo("Sayings of the Century"))
                .body("store.book.findAll { book -> book.price == 8.95f }.price[0]", equalTo(8.95f))
        ;
    }

    @Test
    public void testXml() {
        when().get("http://127.0.0.1:8000/testerhome.xml")
                .then().body("shopping.category.item[0].name", equalTo("Chocolate"))
                .body("shopping.category.item.size()", equalTo(5))
                .body("shopping.category.findAll { it.@type == 'groceries' }.size()",equalTo(1))
                .body("shopping.category.item.findAll { it.price == 20 }.name",equalTo("Coffee"))
                .body("**.findAll { it.price == 20 }.name", equalTo("Coffee"))
        ;
    }
}
