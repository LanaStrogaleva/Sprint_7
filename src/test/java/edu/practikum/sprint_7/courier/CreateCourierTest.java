package edu.practikum.sprint_7.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static edu.practikum.sprint_7.Utils.randomString;

public class CreateCourierTest {
        public static String BASE_URL = "https://qa-scooter.praktikum-services.ru";
        String id;

        @Before
        public void SetUp() {
            RestAssured.baseURI = BASE_URL;
        }

        @Test
        @DisplayName("Создание курьера.")
        @Description("Создание курьера со случайными данными. Запрос возвращает код ответа - 201 и ok: true")
        public void createNewCourier() {
            String key = "ok";
            Boolean value = true;
            int statusCode = 201;

            Courier courier = new Courier()
                    .withLogin(randomString(6))
                    .withPassword(randomString(6))
                    .withFirstName(randomString(6));

            CourierClient courierClient = new CourierClient();
            Response response = courierClient.createCourier(courier);
            System.out.println(courier.toString());

            courierClient.checkStatusCode(response, statusCode);

            courierClient.checkResponseBody(response, key, value);

            courierClient.printResponseBodyToConsole(response);

            id = Integer.toString(courierClient.loginCourier(courier).body().path("id"));
        }

        @Test
        @DisplayName("Создание курьера без поля \"firstName\"")
        @Description("При создании курьера без поля \"firstName\" возвращается код 201 и ok: true\"")
        public void createNewCourierWithoutFirstName() {
            String key = "ok";
            Boolean value = true;
            int statusCode = 201;

            Courier courierWithoutFirstName = new Courier()
                    .withLogin(randomString(6))
                    .withPassword(randomString(6));

            CourierClient courierClient = new CourierClient();

            Response response = courierClient.createCourier(courierWithoutFirstName);

            courierClient.checkStatusCode(response, statusCode);

            courierClient.checkResponseBody(response, key, value);

            courierClient.printResponseBodyToConsole(response);
            id = Integer.toString(courierClient.loginCourier(courierWithoutFirstName).body().path("id"));
        }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("При создании курьера с повторяющимися данными возвращается код 409 и \"message\": \"Этот логин уже используется\"")
    public void createSameCourier() {
        String key = "message";
        String value = "Этот логин уже используется";
        int statusCode = 409;

        Courier courier = new Courier()
                .withLogin(randomString(6))
                .withPassword(randomString(6))
                .withFirstName(randomString(6));

        CourierClient courierClient = new CourierClient();
        courierClient.createCourier(courier);
        id = Integer.toString(courierClient.loginCourier(courier).body().path("id"));
        Courier createdCourier = courier;

        Response response = courierClient.createCourier(createdCourier);

        courierClient.checkStatusCode(response,statusCode);

        courierClient.checkResponseBody(response, key,value);

        courierClient.printResponseBodyToConsole(response);
    }

    @Test
    @DisplayName("Создание курьера с логином, который уже есть")
    @Description("При создании курьера с повторяющимися логином возвращается код 409 и \"message\": \"Этот логин уже используется")
    public void createNewCourierWithSameLogin() {
        String key = "message";
        String value = "Этот логин уже используется";
        int statusCode = 409;

        Courier courier = new Courier()
                .withLogin(randomString(6))
                .withPassword(randomString(6))
                .withFirstName(randomString(6));

        CourierClient courierClient = new CourierClient();
        courierClient.createCourier(courier);
        id = Integer.toString(courierClient.loginCourier(courier).body().path("id"));

        Courier courierWithSameLogin = new Courier()
                .withLogin(courier.getLogin())
                .withPassword(randomString(6))
                .withFirstName(randomString(6));

        Response response = courierClient.createCourier(courierWithSameLogin);

        courierClient.checkStatusCode(response,statusCode);

        courierClient.checkResponseBody(response, key,value);

        courierClient.printResponseBodyToConsole(response);
    }

    @Test
    @DisplayName("Создание курьера без поля \"login\"")
    @Description("При создании курьера без поля \"login\" возвращается код 400 и \"message\": \"Недостаточно данных для создания учетной записи")
    public void createNewCourierWithoutLogin() {
        String key = "message";
        String value = "Недостаточно данных для создания учетной записи";
        int statusCode = 400;

        Courier courierWithoutLogin = new Courier()
                .withPassword(randomString(6))
                .withFirstName(randomString(6));

        CourierClient courierClient = new CourierClient();

        Response response = courierClient.createCourier(courierWithoutLogin);

        courierClient.checkStatusCode(response,statusCode);

        courierClient.checkResponseBody(response, key,value);

        courierClient.printResponseBodyToConsole(response);
    }

    @Test
    @DisplayName("Создание курьера без поля \"password\"")
    @Description("При создании курьера без поля \"password\" возвращается код 400 и \"message\": \"Недостаточно данных для создания учетной записи")
    public void createNewCourierWithoutPassword() {
        String key = "message";
        String value = "Недостаточно данных для создания учетной записи";
        int statusCode = 400;

        Courier courierWithoutPassword = new Courier()
                .withLogin(randomString(6))
                .withFirstName(randomString(6));

        CourierClient courierClient = new CourierClient();

        Response response = courierClient.createCourier(courierWithoutPassword);

        courierClient.checkStatusCode(response,statusCode);

        courierClient.checkResponseBody(response, key,value);

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
