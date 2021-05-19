package ru.netology.web;


import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;


import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;


class DeliveryNewTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @BeforeEach
    void shouldOpenWeb() {
        open("http://localhost:9999");


    }

    @Test
    void shouldAcceptInformation() {
        $("[data-test-id=city] input").setValue(DataHelper.getNewCity());
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        String dateFirst = DataHelper.getNewDate(3);
        $("[data-test-id='date'] input").setValue(dateFirst);
        $("[data-test-id='name'] input").setValue(DataHelper.getNewName());
        $("[data-test-id='phone'] input").setValue(DataHelper.getNewPhoneNumber());
        $("[data-test-id=agreement]").click();
        $$("button").get(1).click();
        $(withText("Встреча успешно"))
                .shouldBe(visible, Duration.ofSeconds(15));
        $(withText(dateFirst))
                .shouldBe(visible);
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        String dateSecond = DataHelper.getNewDate(5);
        $("[data-test-id='date'] input").setValue(dateSecond);
        $$("button").get(1).click();
        $(withText("У вас уже запланирована встреча на другую дату."))
                .shouldBe(visible, Duration.ofSeconds(15));
        $(withText("Перепланировать")).click();
        $(withText("Встреча успешно ")).shouldBe(visible);
        $(withText(dateSecond)).shouldBe(visible);

    }

}

