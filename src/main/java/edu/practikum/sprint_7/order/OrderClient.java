package edu.practikum.sprint_7.order;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class OrderClient {
    private static String CREATE_ORDER_URL = "/api/v1/orders";
    private static String GET_ORDERS_URL = "/api/v1/orders";
    private static String CANCEL_ORDERS_URL = "/api/v1/orders/cancel";

    @Step("Создание заказа c Color = {order.color}")
    public Response createOrder(Order order) {
        return   given()
                .header("Content-type", "application/json; charset=utf-8")
                .and()
                .body(order)
                .when()
                .post(CREATE_ORDER_URL);
    }

    @Step("Получение списка заказов ")
    public Response getOrders() {
        return   given()
                .when()
                .get(GET_ORDERS_URL);

    }

    @Step("Проверить статус код")
    public void checkStatusCode(Response response, int statusCode) {
        response.then().assertThat()
                .statusCode(statusCode);
    }

    @Step("Проверить, что тело ответа не пустое")
    public void checkResponseBodyNotEmpty(Response response, String key ) {
        response.then().assertThat()
                .body(key, notNullValue());
    }

    @Step("Вывести тело ответа в консоль")
    public void printResponseBodyToConsole(Response response) {
        System.out.println(response.body().asString());
    }


    @Step("Отменить заказ {order}")
    public Response cancelOrder(String id) {
        return given()
                .when()
                .queryParam("track", id)
                .put(CANCEL_ORDERS_URL);
    }
}