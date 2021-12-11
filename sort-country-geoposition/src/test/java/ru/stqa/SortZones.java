package ru.stqa;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class SortZones extends TestBase{
    @Test
    public void AlphaListZones() {

        driver.navigate().to("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        List<WebElement> numCountries = driver.findElements(By.cssSelector("tr.row"));
        for(int i = 0; i < numCountries.size(); i++) {
            numCountries.get(i).findElement(By.cssSelector("td a:not([title])")).click();
            wait.until(titleIs("Edit Geo Zone | My Store"));

            List<WebElement> numZones = driver.findElements(By.xpath(("//*[@id=\"table-zones\"]//tr")));
            for(int j = 1; j < numZones.size() - 2 ; j++) {
                String currentZoneName = numZones.get(j).findElements(By.tagName("td")).get(2).getText();
                String nextZoneName  = numZones.get(j + 1).findElements(By.tagName("td")).get(2).getText();
                if (currentZoneName.compareTo(nextZoneName) > 0) {
                    System.out.println("The list is not in alphabetic order");
                    break;
                }
            }
            driver.navigate().back();
            numCountries = driver.findElements(By.cssSelector("tr.row"));
        }
    }
}
