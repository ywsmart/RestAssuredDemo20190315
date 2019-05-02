import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class XueqiuTest {
    public static String code;
    // 可以定义请求时的规范
    public static RequestSpecification requestSpecification;
    // 可以定义响应时的规范
    public static ResponseSpecification responseSpecification;

    @BeforeClass
    public static void testLogin() {
        useRelaxedHTTPSValidation();
        // 全局变量
//        RestAssured.proxy("127.0.0.1", 8888);
        // 全局baseURI未生效
//        baseURI="https://api.xueqiu.com";
        requestSpecification = new RequestSpecBuilder().build();
        requestSpecification.cookie("testerhome_id","hogwarts");
        requestSpecification.port(80);
        requestSpecification.header("User-Agent", "XueqiuTest Android 11.17");

        responseSpecification = new ResponseSpecBuilder().build();
        responseSpecification.statusCode(200);
        responseSpecification.body("code",equalTo(1));
//        loginXueqiu();
    }

   private static void loginXueqiu() {
        // 获取参数值code
        code = given()

                // 注释非必须参数
//                .queryParam("_t", "1GENYMOTION104132f9a273926d6367d52d3bbb8e90.5728543081.1552744395315.1552744631971")
//                .queryParam("_s", "454c15")
                .cookie("xq_a_token", "7b222918ff5a087a0284d435eacaa77a244a4d07")
                .cookie("u", "5728543081")
                .formParam("grant_type", "password")
                .formParam("telephone", "15158098948")
                .formParam("password", "fcea920f7412b5da7be0cf42b8c93759")
                .formParam("grant_type", "password")
                // 注释非必须参数
//                .formParam("sid","1GENYMOTION104132f9a273926d6367d52d3bbb8e90")
//                .formParam("areacode","86")
//                .formParam("captcha", "")
                .formParam("client_id", "JtXbaMn7eP")
                .formParam("client_secret","txsDfr9FphRSPov5oQou74")
                .when()
                .post("/provider/oauth/token")
                .then()
//                .log().all()
                .statusCode(400)
                // 请求过多被限制，暂时注释断言密码错误码
//                .body("error_code",equalTo("20082"))
                // 利用extract方法获取某参数
                .extract().path("error_code")
        ;
        System.out.println(code);


        given()
                .queryParam("sort","relevance")
                .queryParam("count","5")
                .queryParam("page","1")
                .queryParam("q","sogo")
                .cookie("xq_a_token","7b222918ff5a087a0284d435eacaa77a244a4d07")
                .cookie("u","5728543081")
                // 利用上一个接口的某个值引用到下一个接口，用code示例
                .cookie("uid",code)
                .when()
                .get("/statuses/search.json")
                .then()
                .statusCode(200)
                .body("list.find { it.title = '搜狗（SOGO.US）：低调耕耘15载，押注AI赛道的高光时刻' }.id", equalTo(116339635))
        ;
    }

    @Test
    public void testSearch() {
        // 信任有疑问的证书
        useRelaxedHTTPSValidation();
        // given开头表示输入数据
        given()
                // 可以配置代理
//                .proxy("127.0.0.1",8888)
                // query请求
                .queryParam("code", "sogo")
                // 头信息，需要一个Cookie
                .header("Cookie", "aliyungf_tc=AQAAAJiYphInfAEAUuPgetgkCh/thtXH; xq_a_token=8309c28a83ae5d20f26b7fcc22debbcd459794bd; xq_a_token.sig=ekfY9a_we8nNlhOpvhWeZz85MrU; xq_r_token=d55d09822791a788916028e59055668bed1b7018; xq_r_token.sig=h9qWLLwRXV-QxfHHukEC2U76ZDA; _ga=GA1.2.140106029.1552611418; _gid=GA1.2.142404918.1552611418; u=531552611418180; Hm_lvt_1db88642e346389874251b5a1eded6e3=1552611418; device_id=dcbaf653e72a20f231c1fca23c929e73; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1552611510")
                // 表示触发条件
                .when()
                .get("/stock/search.json")
                // 对结果断言
                .then()
                // 状态码断言
                .statusCode(403)
                // 字段断言
                .body("stocks.name", hasItem("搜狗"))
                .body("stocks.code", hasItem("SOGO"))
                .body("stocks.find {it.code = 'SOGO' }.name", equalTo("搜狗"))
        ;

    }

    @Test
    public void testLogin2() {
        useRelaxedHTTPSValidation();
        Response response = given()
                .header("User-Agent", "XueqiuTest Android 11.17")
//                .queryParam("_t", "1GENYMOTION104132f9a273926d6367d52d3bbb8e90.5728543081.1552744395315.1552744631971")
//                .queryParam("_s", "454c15")
                .cookie("xq_a_token", "7b222918ff5a087a0284d435eacaa77a244a4d07")
                .cookie("u", "5728543081")
                .formParam("grant_type", "password")
                .formParam("telephone", "15158098948")
                .formParam("password", "fcea920f7412b5da7be0cf42b8c93759")
                .formParam("grant_type", "password")
//                .formParam("sid","1GENYMOTION104132f9a273926d6367d52d3bbb8e90")
//                .formParam("areacode","86")
//                .formParam("captcha", "")
                .formParam("client_id", "JtXbaMn7eP")
                .formParam("client_secret","txsDfr9FphRSPov5oQou74")
                .when()
                .post("/provider/oauth/token")
                .then()
//                .log().all()
                .statusCode(403)
//                .body("error_code",equalTo("20082"))
                .extract().response()
                ;
        System.out.println(response.header("server"));
    }

    // RestAssured支持直接传入map来转json
    @Test
    public void testPostJson(){
        HashMap<String, Object> map=new HashMap<String, Object>();
        map.put("a",1);
        map.put("b","testerhome");
        given().log().all()
                .spec(requestSpecification)
                .contentType(ContentType.JSON)
                .body(map)
                // 等价于下面text
//                .body("{\"a\":1,\"b\":\"testerhome\"}")
                .when().post("http://www.baidu.com")
                .then().log().all().time(lessThan(500L));
//                .spec(responseSpecification);
    }
    @Test
    public void testSchemaInClassPath2() {
        given()
                .header("Cookie", "_ga=GA1.2.140106029.1552611418; device_id=dcbaf653e72a20f231c1fca23c929e73; xq_a_token=e4825b3d30583c1ee9dd6a030e01f888f4742ceb; xq_a_token.sig=xbDd2FH09OHm1CM0icsPi_wZGqk; xq_r_token=ac687cdb4b59093a43845a952a7728af7ed42176; xq_r_token.sig=TLDfn7bgRrm_PLi6OaO2aqQmWW0; _gid=GA1.2.1259207486.1556766540; u=721556766540635; Hm_lvt_1db88642e346389874251b5a1eded6e3=1556766541; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1556768036")
                .when().log().all().get("https://stock.xueqiu.com/v5/stock/batch/quote.json?symbol=SOGO")
                .then().log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schema/sogoSchema.json"))
        ;
    }

}
