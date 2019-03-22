import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class HttpTest {
    @BeforeClass
    public static void beforeClsaa() {
//        baseURI = "https://www.baidu.com";
    }

    @Test
    public void getHtml() {
        given()
//                .log().all()
                .queryParam("wd","mp3")
                .when()
                .get("http://www.baidu.com/s")
                .then()
//                .log().all()
                .statusCode(200)
                .body("html.head.title",equalTo("mp3_百度搜索"));

    }
}
