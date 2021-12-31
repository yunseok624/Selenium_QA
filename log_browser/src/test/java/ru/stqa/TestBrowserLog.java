package ru.stqa;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class TestBrowserLog extends TestBase {
    @Test
    public void testBrowserLogMessages() {
        // login to product page as admin
        driver.navigate().to("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        //Click on Catalog button
        List<WebElement> elementsMenu = driver.findElements(By.cssSelector("ul#box-apps-menu li#app-"));
        WebElement elementMenu = elementsMenu.get(1);
        elementMenu.click();
        //Click on Category link
        driver.findElement(By.xpath(".//table[@class='dataTable']//td[3]/a")).click();
        //Main function
        checkLog();
    }

    private void checkLog() {
        WebElement duck = driver.findElement(By.cssSelector("table.dataTable tbody"));
        List<WebElement> list = duck.findElements(By.xpath(".//tr/td[3]/a"));
        for (int n = 1; n < list.size(); n++) {
            driver.findElements(By.xpath(".//tr/td[3]/a")).get(n).click();
            implicitlyWaitOff();
            implicitlyWaitOn();
            driver.navigate().back();
            printBrowserLog();
        }
    }

    private void printBrowserLog() {
        List<LogEntry> logList = driver.manage().logs().get("browser").getAll();
        if (logList.size()!= 0) {
            for (LogEntry l : logList)
                System.out.println(l);
        }
    }
}
