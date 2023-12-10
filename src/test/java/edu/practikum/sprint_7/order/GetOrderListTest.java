package edu.practikum.sprint_7.order;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

public class GetOrderListTest {
    public static String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    @Before
    public void SetUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @DisplayName("Получение списка заказов без courierId")
    @Description("Успешное получение списка заказов без courierId. Запрос возвращает код ответа - 200 и список заказов \"orders\"")
    public void getOrderList() {
        String key = "orders";
        int statusCode = 200;

        OrderClient orderClient = new OrderClient();

        Response response = orderClient.getOrders();

        orderClient.checkStatusCode(response, statusCode);

        orderClient.checkResponseBodyNotEmpty(response,key);

        orderClient.printResponseBodyToConsole(response);

    }

}
