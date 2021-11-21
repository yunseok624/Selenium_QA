package ru.stqa;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Sections {
    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    public WebDriver driver;
    public WebDriverWait wait;

    void isElementPresent(By locator) {
        try {
            driver.findElement(locator);
        } catch (NoSuchElementException ignored) {
        }
    }

    @Before
    public void start() {
        if (tlDriver.get() != null) {
            driver = tlDriver.get();
            wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            return;
        }

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
    }
    @Test
    public void adminSectionsTest() {
        driver.navigate().to("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        List<WebElement> elementsMenu = driver.findElements(By.cssSelector("ul#box-apps-menu li#app-"));
        int numMenu = elementsMenu.size();
        for (int i = 0; i < numMenu; i++) {
            elementsMenu = driver.findElements(By.cssSelector("ul#box-apps-menu li#app-"));
            WebElement elementMenu = elementsMenu.get(i);
            elementMenu.click();
            isElementPresent(By.cssSelector("td#content>h1"));

            List<WebElement> elementsSubmenu = driver.findElements(By.xpath("//ul[contains(@class, 'docs')]//li"));
            int numSubMenu = elementsSubmenu.size();
            if (numSubMenu > 0) {
                for (int j = 0; j < numSubMenu; j++) {
                    elementsSubmenu = driver.findElements(By.xpath("//ul[contains(@class, 'docs')]//li"));
                    WebElement elementSubmenu = elementsSubmenu.get(j);
                    elementSubmenu.click();
                    isElementPresent(By.cssSelector("td#content>h1"));
                }
            } else {
                isElementPresent(By.cssSelector("td#content>h1"));
            }
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
