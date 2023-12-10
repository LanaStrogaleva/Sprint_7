package edu.practikum.sprint_7.order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static edu.practikum.sprint_7.Utils.randomString;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    public static String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    String id;

    @Before
    public void SetUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Parameterized.Parameter
    public static String[] color;

    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GRAY"}},
                {new String[]{"BLACK", "GRAY"}},
                {null}
        };
    }

        @Test
        @DisplayName("Создание заказа c Color.")
        @Description("Создание заказа c разными параметрами поля Color. Запрос возвращает код ответа - 201 и track")
        public void createNewOrder() {
            String key = "track";
            int statusCode = 201;

            Order order = new Order()
                    .withFirstName(randomString(6))
                    .withLastName(randomString(6))
                    .withAddress(randomString(10))
                    .withMetroStation(randomString(10))
                    .withPhone("+78003553535")
                    .withRentTime(5)
                    .withDeliveryDate("2023-12-10")
                    .withComment(randomString(10))
                    .withColor(color);
            System.out.println(order.getColor());

            OrderClient orderClient = new OrderClient();
            Response response = orderClient.createOrder(order);

            id = Integer.toString(response.path(key));

            orderClient.checkStatusCode(response, statusCode);

            orderClient.checkResponseBodyNotEmpty(response, key);

            orderClient.printResponseBodyToConsole(response);

        }

    @After
    public void TearDown() {
        OrderClient orderClient = new OrderClient();
        Response response = orderClient.cancelOrder(id);
        System.out.println("Отмена заказа (" + id + ") :");
        orderClient.printResponseBodyToConsole(response);
    }

    }


