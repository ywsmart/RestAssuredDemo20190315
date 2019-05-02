import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
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
                .queryParam("wd", "mp3")
                .when()
                .get("http://www.baidu.com/s")
                .then()
//                .log().all()
                .statusCode(200)
                .body("html.head.title", equalTo("mp3_百度搜索"));

    }

    @Test
    public void testSchema() {
        given().when().get("https://testerhome.com/api/v3/topics/6040.json")
                .then()
                .statusCode(200)
        // 下面用绝对路径，不太合适
//                .body(matchesJsonSchema(new File("/Users/ywsmart/Desktop/tmp/json.schema")))
        ;
    }

    @Test
    public void testSchemaInClassPath() {
        given().when().get("https://testerhome.com/api/v3/topics/6040.json")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schema/json.schema"))
        ;
    }

    @Test
    public void testFilterResponse(){
        given()
                // req请求，res响应，ctx上下文
                .filter((req,res,ctx)->{
                    // code
                    // filter request
                    System.out.println(req.getURI());
                    System.out.println(res);
                    // request real
                    Response resNew = ctx.next(req,res);
                    // response real
                    // filter response
                    // return response
                    // then
                    System.out.println(resNew.getStatusLine());
                    return resNew;
                })
                .when().get("https://testerhome.com/api/v3/topics/6040.json")
                .then().statusCode(200);

    }

    @Test
    public void testFilterAddCooike(){
        given()
                .queryParam("symbol","SOGO")
                .cookie("_ga=GA1.2.140106029.1552611418; device_id=dcbaf653e72a20f231c1fca23c929e73; xq_a_token=e4825b3d30583c1ee9dd6a030e01f888f4742ceb; xq_a_token.sig=xbDd2FH09OHm1CM0icsPi_wZGqk; xq_r_token=ac687cdb4b59093a43845a952a7728af7ed42176; xq_r_token.sig=TLDfn7bgRrm_PLi6OaO2aqQmWW0; _gid=GA1.2.1259207486.1556766540; u=721556766540635; Hm_lvt_1db88642e346389874251b5a1eded6e3=1556766541; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1556768036")
                .when().get("https://stock.xueqiu.com/v5/stock/batch/quote.json?symbol=SOGO")
                .then().statusCode(200)
                ;
    }

}
