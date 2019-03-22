import org.junit.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class Baidu {
    /**
     * 访问百度示例一下
     */
    @Test
    public void testGetHtml() {
        given()
                .log().all().get("http://www.baidu.com")
                .then().log().all().statusCode(200);
    }

    @Test
    public void testMp3() {
        given()
                .queryParam("wd","mp3")
            .when()
                .get("http://www.baidu.com/s")
            .then()
                .log().all()
                .statusCode(200);
    }
}
