package ru.netology.data;

import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;


public class DataHelperSQL {
    @Step("Запрашиваем данные карты из БД")
    public static String getCreditId() {
        var creditIdSQL = "SELECT credit_id FROM order_entity WHERE created = (SELECT max(created) FROM order_entity);";
        return getValue(creditIdSQL);
    }
    @Step("Запрашиваем статус платежа из БД")
    public static String getCreditStatus() {
        var creditStatusSQL = "SELECT status FROM credit_request_entity WHERE created = (SELECT max(created) FROM credit_request_entity);";
        return getValue(creditStatusSQL);
    }
    @Step("Запрашиваем статус платежа из БД")
    public static String getPaymentStatus() {
        var paymentStatusSQL = "SELECT status FROM payment_entity WHERE created = (SELECT max(created) FROM payment_entity);";
        return getValue(paymentStatusSQL);
    }

    @SneakyThrows
    public static String getValue(String request) {
        var runner = new QueryRunner();
        var value = new String();
        try (var conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/db", "user", "pass");
        ) {
            var result = runner.query(conn, request, new ScalarHandler<>());
            value = String.valueOf(result);
        }
        return value;
    }
}
