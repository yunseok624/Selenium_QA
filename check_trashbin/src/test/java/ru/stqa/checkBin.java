package ru.stqa;


import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class checkBin {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void shouldAnswerWithTrue() {
        driver.get("http://localhost/litecart/en/");
        wait.until(titleIs("Online Store | My Store"));
        for (int i = 0; i < 3; i++) {
            addItemToCart();
        }
        driver.findElement(By.cssSelector("div#cart a.link")).click();
        clearCart();
    }

    private void addItemToCart() {
        int number = Integer.parseInt(driver.findElement(By.cssSelector("span.quantity")).getText());
        WebElement element = driver.findElement(By.cssSelector("div.content div.name"));
        Assert.assertNotNull("No products found on main page", element);
        element.click();
        if (!isElementNotPresent(driver, By.cssSelector("td.options")) ) {
            Select size = new Select(driver.findElement(By.cssSelector("select[name='options[Size]']")));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].selectedIndex = 1; arguments[0].dispatchEvent(new Event('change'))",
                    size);
        }
        implicitlyWaitOff();
        driver.findElement(By.cssSelector("button[name='add_cart_product']")).click();
        wait.until(textToBePresentInElement(driver.findElement(By.cssSelector("span.quantity")),
                String.valueOf(number + 1)));
        implicitlyWaitOn();
        driver.navigate().back();
    }

    private void clearCart(){
        WebElement findElement;
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[name='remove_cart_item']")));
        while(driver.findElements(By.cssSelector("[name='remove_cart_item']")).size() != 0){
            findElement = driver.findElement(By.cssSelector("[name='remove_cart_item']"));
            findElement.click();
            implicitlyWaitOff();
            wait.until(ExpectedConditions.stalenessOf(findElement));
            implicitlyWaitOn();
        }
    }

    private void implicitlyWaitOn() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    private void implicitlyWaitOff() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
    }

    boolean isElementNotPresent(WebDriver driver, By locator) {
        try {
            implicitlyWaitOff();
            return driver.findElements(locator).size() == 0;
        } finally {
            implicitlyWaitOn();
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
