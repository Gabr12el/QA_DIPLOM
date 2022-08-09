package ru.netology.test.ui;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;
import static ru.netology.data.SQLHelper.*;

public class PayCreditCard {
    PaymentPage paymentPage = new PaymentPage();


    @BeforeAll
    public static void setUpAll() {

        SelenideLogger.addListener("allure", new AllureSelenide()); }

    @BeforeEach
    public void openPage() {

        open("http://localhost:8080");
    }

    @AfterEach
    void cleanDB() {

        SQLHelper.databaseCleanUp();
    }

    @AfterAll
    public static void tearDownAll() {

        SelenideLogger.removeListener("allure");
    }

    @Test
    @SneakyThrows
    @DisplayName("Покупка в кредит")
    void shouldApproveCreditCard() {
        paymentPage.buyCreditCard();
        var info = getApprovedCard();
        paymentPage.sendingValidData(info);
        paymentPage.bankApproved();
        var expected = DataHelper.getStatusFirstCard();
        var creditRequest = getCreditRequestInfo();
        var orderInfo = getOrderInfo();
        assertEquals(expected, getCreditRequestInfo().getStatus());
        assertEquals(orderInfo.getPayment_id(), creditRequest.getBank_id());
    }

    @Test
    @SneakyThrows
    @DisplayName("Покупка в кредит невалидной картой")
    void shouldPayCreditDeclinedCard() {
        paymentPage.buyCreditCard();
        var info = DataHelper.getDeclinedCard();
        paymentPage.sendingNotValidData(info);
        paymentPage.bankDeclined();
        var expected = getStatusSecondCard();
        var paymentInfo = getPaymentInfo().getStatus();
        assertEquals(expected, paymentInfo);
    }

    @Test
    @DisplayName("Покупка в кредит: все поля пустые")
    void shouldEmptyFormWithCredit() {
        paymentPage.buyCreditCard();
        paymentPage.pressButtonForContinue();
        paymentPage.emptyForm();
    }

    @Test
    @DisplayName("Покупка в кредит: поле карты пусто, остальные поля - валидные данные")
    public void shouldEmptyFieldCardWithCredit() {
        paymentPage.buyCreditCard();
        var info = getEmptyCardNumber();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldCardNumberError();
    }

    @Test
    @DisplayName("Покупка в кредит: поле карты одной цифрой, остальные поля - валидные данные")
    public void shouldOneNumberInFieldCardNumberWithCredit() {
        paymentPage.buyCreditCard();
        var info = getOneNumberCardNumber();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldCardNumberError();
    }

    @Test
    @DisplayName("Покупка в кредит: поле карты 15 цифр, остальные поля - валидные данные")
    public void shouldFifteenNumberInFieldCardNumberWithCredit() {
        paymentPage.buyCreditCard();
        var info = getFifteenNumberCardNumber();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldCardNumberError();
    }

    @Test
    @DisplayName("Покупка в кредит: карты нет в БД, остальные поля - валидные данные")
    public void shouldFakerCardInFieldCardNumberWithCredit() {
        paymentPage.buyCreditCard();
        var info = getFakerNumberCardNumber();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFakerCardNumber();
    }

    @Test
    @DisplayName("Покупка в кредит: поле месяц пусто, остальные поля - валидные данные")
    public void shouldEmptyFieldMonthWithCredit() {
        paymentPage.buyCreditCard();
        var info = getEmptyMonth();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldMonthError();
    }

    @Test
    @DisplayName("Покупка в кредит: поле месяц одной цифрой, остальные поля - валидные данные")
    public void shouldOneNumberInFieldMonthWithCredit() {
        paymentPage.buyCreditCard();
        var info = getOneNumberMonth();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldMonthError();
    }

    @Test
    @DisplayName("Покупка в кредит: в поле месяц предыдущий от текущего, остальные поля - валидные данные")
    public void shouldFieldWithPreviousMonthWithCredit() {
        paymentPage.buyCreditCard();
        var info = getPreviousMonthInField();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldMonthError();
    }

    @Test
    @DisplayName("Покупка в кредит: в поле месяц нулевой (не существующий) месяц" +
            " остальные поля - валидные данные")
    public void shouldFieldWithZeroMonthWithCredit() {
        paymentPage.buyCreditCard();
        var info = getZeroMonthInField();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldMonthError();
    }

    @Test
    @DisplayName("Покупка в кредит: в поле месяц тринадцатый (не существующий) месяц" +
            " остальные поля - валидные данные")
    public void shouldFieldWithThirteenMonthWithCredit() {
        paymentPage.buyCreditCard();
        var info = getThirteenMonthInField();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldMonthError();
    }

    @Test
    @DisplayName("Покупка в кредит: поле год пусто, остальные поля - валидные данные")
    public void shouldEmptyFieldYearWithCredit() {
        paymentPage.buyCreditCard();
        var info = getEmptyYear();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldYearError();
    }

    @Test
    @DisplayName("Покупка в кредит: поле год предыдущий год от текущего" +
            " остальные поля - валидные данные")
    public void shouldPreviousYearFieldYearWithCredit() {
        paymentPage.buyCreditCard();
        var info = getPreviousYearInField();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldYearError();
    }

    @Test
    @DisplayName("Покупка в кредит: поле год на шесть лет больше чем текущий" +
            " остальные поля - валидные данные")
    public void shouldPlusSixYearFieldYearWithCredit() {
        paymentPage.buyCreditCard();
        var info = getPlusSixYearInField();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldYearError();
    }

    @Test
    @DisplayName("Покупка в кредит: поле владелец пустое, остальные поля - валидные данные")
    public void shouldEmptyFieldNameWithCredit() {
        paymentPage.buyCreditCard();
        var info = getApprovedCard();
        paymentPage.sendingEmptyNameValidData(info);
        paymentPage.sendingValidDataWithFieldNameError();
    }

    @Test
    @DisplayName("Покупка в кредит: поле владелец спец.символами" +
            " остальные поля - валидные данные")
    public void shouldSpecialSymbolInFieldNameWithCredit() {
        paymentPage.buyCreditCard();
        var info = getSpecialSymbolInFieldName();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldNameError();
    }

    @Test
    @DisplayName("Покупка в кредит: поле владелец цифрами" +
            " остальные поля - валидные данные")
    public void shouldNumberInFieldNameWithCredit() {
        paymentPage.buyCreditCard();
        var info = getNumberInFieldName();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldNameError();
    }

    @Test
    @DisplayName("Покупка в кредит: поле владелец рус. буквами" +
            " остальные поля - валидные данные")
    public void shouldRussianNameInFieldNameWithCredit() {
        paymentPage.buyCreditCard();
        var info = getRusName();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldNameError();
    }

    @Test
    @DisplayName("Покупка в кредит: поле владелец одно слово" +
            " остальные поля - валидные данные")
    public void shouldOnlySurnameInFieldNameWithCredit() {
        paymentPage.buyCreditCard();
        var info = getOnlySurnameInFieldName();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldNameError();
    }

    @Test
    @DisplayName("Покупка в кредит: поле CVV пусто" +
            " остальные поля - валидные данные")
    public void shouldEmptyCVVInFieldCVVWithCredit() {
        paymentPage.buyCreditCard();
        var info = getEmptyCVVInFieldCVV();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldCVVError();
    }

    @Test
    @DisplayName("Покупка в кредит: поле CVV одним числом" +
            " остальные поля - валидные данные")
    public void shouldOneNumberInFieldCVVWithCredit() {
        paymentPage.buyCreditCard();
        var info = getOneNumberInFieldCVV();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldCVVError();
    }

    @Test
    @DisplayName("Покупка в кредит: поле CVV два числа" +
            " остальные поля - валидные данные")
    public void shouldTwoNumberInFieldCVVWithCredit() {
        paymentPage.buyCreditCard();
        var info = getOTwoNumberInFieldCVV();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldCVVError();
    }
}