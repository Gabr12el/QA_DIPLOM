package ru.netology.test.api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.ApiHelper;
import ru.netology.data.CardInfo;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.data.entity.OrderEntity;
import ru.netology.data.entity.PaymentEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.ApiHelper.payByCard;

public class ApiTest {
    CardInfo approvedCardInfo = DataHelper.getApprovedCard();
    CardInfo declinedCardInfo = DataHelper.getDeclinedCard();
    String debitPath = "/payment";
    String creditPath = "/credit";

    @BeforeAll
    public static void setUp() {
        SQLHelper.databaseCleanUp();
        RestAssured.filters(
                new RequestLoggingFilter(),
                new ResponseLoggingFilter(),
                new AllureRestAssured());
    }

    @BeforeEach
    public void prepare() {
        //SQLHelper.databaseCleanUp();
    }


    @DisplayName("Проверка сервиса на дебетовую покупку с валидными данными")
    @Test
    public void checkDebitPaymentValidCard() {
        CardInfo cardData = DataHelper.getApprovedCard();
        ApiHelper.payByCard(cardData, debitPath);
        OrderEntity order = SQLHelper.getOrderInfo();
        PaymentEntity payment = SQLHelper.getPaymentInfo();
        assertEquals(order.getPayment_id(), payment.getTransaction_id());
        assertEquals("APPROVED", payment.getStatus());
    }

    @DisplayName("Проверка сервиса на кредитную покупку с валидными данными")
    @Test
    public void checkCreditPaymentValidCard() {
        CardInfo cardData = DataHelper.getApprovedCard();
        ApiHelper.payByCard(cardData, creditPath);
        OrderEntity order = SQLHelper.getOrderInfo();
        PaymentEntity payment = SQLHelper.getPaymentInfo();
        assertEquals(order.getPayment_id(), payment.getTransaction_id());
        assertEquals("APPROVED", payment.getStatus());
    }

    @DisplayName("Проверка сервиса на дебетовую покупку с невалидными данными")
    @Test
    public void checkDebitPaymentInvalidCard() {
        CardInfo cardData = DataHelper.getDeclinedCard();
        ApiHelper.payByCard(cardData, debitPath);
        PaymentEntity payment = SQLHelper.getPaymentInfo();
        assertEquals("DECLINED", payment.getStatus());
    }

    @DisplayName("Проверка сервиса на кредитную покупку с невалидными данными")
    @Test
    public void checkCreditPaymentInvalidCard() {
        CardInfo cardData = DataHelper.getDeclinedCard();
        ApiHelper.payByCard(cardData, creditPath);
        PaymentEntity payment = SQLHelper.getPaymentInfo();
        assertEquals("DECLINED", payment.getStatus());
    }
}