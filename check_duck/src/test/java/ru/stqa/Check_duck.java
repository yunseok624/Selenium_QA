package ru.stqa;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;


public class Check_duck {
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
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
    }

    @Test
    public void adminSectionsTest() {
        driver.navigate().to("http://localhost/litecart/en/");

        List<WebElement> elementsDuck = driver.findElements(By.cssSelector("li.product"));

        for (WebElement element : elementsDuck) {
            int countDuck = element.findElements(By.cssSelector(".sticker")).size();
            String stickerName = element.findElement(By.cssSelector("div.name")).getText();
            if (countDuck == 1) {
                String stat = element.findElement(By.cssSelector("div.sticker")).getText();
                System.out.println("The item " + stickerName + " has one " + stat.toUpperCase() + " sticker");
            } else if (countDuck > 1) {
                System.out.println("The item " + stickerName + " has more than one sticker");
            } else {
                System.out.println("The item " + stickerName + " has no sticker");
            }
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
