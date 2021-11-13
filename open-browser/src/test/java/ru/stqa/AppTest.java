package ru.stqa;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AppTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void firstTest() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\parky\\Documents\\GitHub\\Making_Infrastructure\\open-browser\\chromedriver.exe");
        driver.get("https://yandex.ru/");
        WebElement element = driver.findElement(By.name("text"));
        element.sendKeys("webdriver");
        element.submit();
        wait.until(titleIs("webdriver — Яндекс: нашлось 711 тыс. результатов"));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
