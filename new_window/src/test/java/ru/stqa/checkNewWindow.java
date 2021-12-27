package ru.stqa;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class checkNewWindow {
    private WebDriver driver;
    private WebDriverWait wait;
    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void shouldAnswerWithTrue() {
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(titleIs("Countries | My Store"));

        driver.findElement(By.cssSelector("table.dataTable tr.row:nth-child(2) > td:nth-child(5) > a:nth-child(1)")).
                click();
        wait.until(titleIs("Edit Country | My Store"));

        String mainWindow = driver.getWindowHandle();
        Set<String> windows = driver.getWindowHandles();
        List<WebElement> links = driver.findElements(By.className("fa-external-link"));

        for (WebElement link : links) {
            link.click();
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));
            String newWindow = wait.until(anyWindowOtherThan(windows));
            driver.switchTo().window(newWindow);
            driver.close();
            driver.switchTo().window(mainWindow);
        }
    }

    public ExpectedCondition<String> anyWindowOtherThan (Set<String> windows) {
        return d -> {
            assert d != null;
            Set<String> handles = d.getWindowHandles();
            handles.removeAll(windows);
            return  handles.size()>0 ? handles.iterator().next() : null;
        };
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
