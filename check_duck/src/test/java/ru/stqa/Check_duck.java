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

        String[] statType = {"box-most-popular", "box-campaigns", "box-latest-products"};

        for (String type : statType) {
            System.out.println("*** " + type + " ***");
            String locator = "div#" + type + " li.product";
            int numDuck = driver.findElements(By.cssSelector(locator)).size();

            for (int i = 0; i < numDuck; i++) {
                List<WebElement> elementsDuck = driver.findElements(By.cssSelector(locator));
                WebElement elementDuck = elementsDuck.get(i);
                int countDuck = elementDuck.findElements(By.cssSelector(".product")).size();
                String stickerName = elementDuck.findElement(By.cssSelector("div.name")).getText();
                if (countDuck == 1) {
                    String stat = elementDuck.findElement(By.cssSelector("div.sticker")).getText();
                    System.out.println("The item " + stickerName + " has one " + stat.toUpperCase() + " sticker");
                } else if (countDuck > 1) {
                    System.out.println("The item " + stickerName + " has more than one sticker");
                } else {
                    System.out.println("The item " + stickerName + " has no sticker");
                }
            }
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
