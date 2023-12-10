package edu.practikum.sprint_7.courier;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;

public class CourierClient {
    private static String CREATE_COURIER_URL = "/api/v1/courier";
    private static String LOGIN_COURIER_URL = "/api/v1/courier/login";
    private static String DELETE_COURIER_URL = "/api/v1/courier/";

    @Step("Создание курьера {courier}")
    public Response createCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(CREATE_COURIER_URL);

    }

    @Step("Авторизация курьером {courier}")
    public Response loginCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(LOGIN_COURIER_URL);
    }

    @Step("Удаление курьера")
    public Response deleteCourier(String id) {
        return given()
                .when()
                .delete(DELETE_COURIER_URL+id);
    }

    @Step("Проверить статус код")
    public void checkStatusCode(Response response, int statusCode) {
        response.then().assertThat()
                .statusCode(statusCode);
    }

    @Step("Проверить тело ответа")
    public void checkResponseBody(Response response, String key, String value ) {
        response.then().assertThat()
                .body(key, equalTo(value));
    }


    @Step("Проверить тело ответа")
    public void checkResponseBody(Response response, String key, Boolean value ) {
        response.then().assertThat()
                .body(key, equalTo(value));
    }

    @Step("Проверить тело ответа")
    public void checkResponseBody(Response response, String key, int value ) {
        response.then().assertThat()
                .body(key, equalTo(value));
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

}
