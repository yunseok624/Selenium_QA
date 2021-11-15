package ru.stqa;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class TestCase1 {
    private WebDriver driver;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void correctUnPw() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\parky\\Documents\\GitHub\\Making_Infrastructure\\scenario-login\\chromedriver.exe");
        driver.get("http://localhost/litecart/admin/login.php?redirect_url=%2Flitecart%2Fadmin%2F");
        WebElement username = driver.findElement(By.name("username"));
        username.sendKeys("admin");
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}