package ru.stqa;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestBase {
    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    public WebDriver driver;
    public WebDriverWait wait;

    @Before
    public void start() {
        if (tlDriver.get() != null) {
            driver = tlDriver.get();
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            return;
        }

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public void implicitlyWaitOn() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public void implicitlyWaitOff() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
