package edu.practikum.sprint_7.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static edu.practikum.sprint_7.Utils.randomString;

public class LoginCourierTest {
    public static String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    String id;

    @Before
    public void SetUp() {
        RestAssured.baseURI = BASE_URL;
    }


    @Test
    @DisplayName("Авторизация курьера в системе.")
    @Description("Авторизация курьера в системе. Запрос возвращает код ответа - 200 и номер id")
    public void successLoginCourier() {
        String key = "id";
        int statusCode = 200;

        Courier courier = new Courier()
                .withLogin(randomString(6))
                .withPassword(randomString(6))
                .withFirstName(randomString(6));

        CourierClient courierClient = new CourierClient();
        courierClient.createCourier(courier);
        System.out.println(courier.toString());
        Response response = courierClient.loginCourier(courier);
        int idNumber = (courierClient.loginCourier(courier).body().path("id"));
        courierClient.checkStatusCode(response, statusCode);

        courierClient.checkResponseBody(response, key, idNumber);

        courierClient.printResponseBodyToConsole(response);

        id = Integer.toString(courierClient.loginCourier(courier).body().path("id"));
    }
    @Test
    @DisplayName("Авторизация курьера с неверным логином.")
    @Description("Авторизация курьера в системе. Запрос возвращает код ответа - 404 и и \"message\": \"Учетная запись не найдена\"")
    public void loginCourierWithInvalidLogin() {
        String key = "message";
        String value = "Учетная запись не найдена";
        int statusCode = 404;

        Courier courier = new Courier()
                .withLogin(randomString(6))
                .withPassword(randomString(6))
                .withFirstName(randomString(6));

        CourierClient courierClient = new CourierClient();
        courierClient.createCourier(courier);
        id = Integer.toString(courierClient.loginCourier(courier).body().path("id"));

        Courier courierCred = new Courier()
                .withLogin(courier.getLogin()+randomString(3))
                .withPassword(courier.getPassword());
        Response response = courierClient.loginCourier(courierCred);

        courierClient.checkStatusCode(response,statusCode);

        courierClient.checkResponseBody(response, key, value);

        courierClient.printResponseBodyToConsole(response);

    }

    @Test
    @DisplayName("Авторизация курьера с неверным паролем.")
    @Description("Авторизация курьера с неверным паролем. Запрос возвращает код ответа - 404 и и \"message\": \"Учетная запись не найдена\"")
    public void loginCourierWithInvalidPassword() {
        String key = "message";
        String value = "Учетная запись не найдена";
        int statusCode = 404;

        Courier courier = new Courier()
                .withLogin(randomString(6))
                .withPassword(randomString(6))
                .withFirstName(randomString(6));

        CourierClient courierClient = new CourierClient();
        courierClient.createCourier(courier);
        id = Integer.toString(courierClient.loginCourier(courier).body().path("id"));

        Courier courierCred = new Courier()
                .withLogin(courier.getLogin())
                .withPassword(courier.getPassword() + randomString(3));
        Response response = courierClient.loginCourier(courierCred);

        courierClient.checkStatusCode(response,statusCode);

        courierClient.checkResponseBody(response, key, value);

        courierClient.printResponseBodyToConsole(response);

    }

    @Test
    @DisplayName("Авторизация курьера под несуществующим пользователем.")
    @Description("Авторизация курьера под несуществующим пользователем. Запрос возвращает код ответа - 404 и и \"message\": \"Учетная запись не найдена\"")
    public void loginCourierWithInvalidUser() {
        String key = "message";
        String value = "Учетная запись не найдена";
        int statusCode = 404;

        Courier courier = new Courier()
                .withLogin(randomString(6))
                .withPassword(randomString(6))
                .withFirstName(randomString(6));

        CourierClient courierClient = new CourierClient();
        courierClient.createCourier(courier);
        id = Integer.toString(courierClient.loginCourier(courier).body().path("id"));

        Courier courierCred = new Courier()
                .withLogin(randomString(7))
                .withPassword(randomString(7));
        Response response = courierClient.loginCourier(courierCred);

        courierClient.checkStatusCode(response,statusCode);

        courierClient.checkResponseBody(response, key, value);

        courierClient.printResponseBodyToConsole(response);

    }

    @Test
    @DisplayName("Авторизация курьера без поля \"login\"")
    @Description("Авторизация курьера без поля \"login\". Запрос возвращает код ответа - 400 и и \"message\": \"Недостаточно данных для входа\"")
    public void loginCourierWithoutLogin() {
        String key = "message";
        String value = "Недостаточно данных для входа";
        int statusCode = 400;

        Courier courier = new Courier()
                .withLogin(randomString(6))
                .withPassword(randomString(6))
                .withFirstName(randomString(6));

        CourierClient courierClient = new CourierClient();
        courierClient.createCourier(courier);
        id = Integer.toString(courierClient.loginCourier(courier).body().path("id"));

        Courier courierCred = new Courier()
                .withPassword(courier.getPassword());
        Response response = courierClient.loginCourier(courierCred);

        courierClient.checkStatusCode(response,statusCode);

        courierClient.checkResponseBody(response, key, value);

        courierClient.printResponseBodyToConsole(response);

    }

    @Test
    @DisplayName("Авторизация курьера без поля \"password\"")
    @Description("Авторизация курьера без поля \"password\". Запрос возвращает код ответа - 400 и и \"message\": \"Недостаточно данных для входа\"")
    public void loginCourierWithoutPassword() {
        String key = "message";
        String value = "Недостаточно данных для входа";
        int statusCode = 400;

        Courier courier = new Courier()
                .withLogin(randomString(6))
                .withPassword(randomString(6))
                .withFirstName(randomString(6));

        CourierClient courierClient = new CourierClient();
        courierClient.createCourier(courier);
        id = Integer.toString(courierClient.loginCourier(courier).body().path("id"));

        Courier courierCred = new Courier()
                .withLogin(courier.getLogin());
        Response response = courierClient.loginCourier(courierCred);

        courierClient.checkStatusCode(response,statusCode);

        courierClient.checkResponseBody(response, key, value);

        courierClient.printResponseBodyToConsole(response);

    }

    @After
    public void TearDown() {
        CourierClient courierClient = new CourierClient();
        Response response = courierClient.deleteCourier(id);
        System.out.println("Удаление курьера (" + id + ") :");
        courierClient.printResponseBodyToConsole(response);
    }
}
