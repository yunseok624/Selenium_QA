package ru.stqa;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.Random;

public class addProduct {

    private WebDriver driver;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void Product() throws InterruptedException {
        //Login
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        //page Catalog
        driver.findElements(By.cssSelector("ul#box-apps-menu li#app-")).get(1).click();
        Thread.sleep(1000);

        //button Add New Product
        driver.findElements(By.cssSelector("td#content a.button")).get(1).click();
        Thread.sleep(1000);

        String newItem = "Pirate Duck" + new Random().nextInt(10);
        String relativePath = "./src/test/java/resources/Pirate Duck.png";
        Path filePath = Paths.get(relativePath);
        String absolutePath = filePath.normalize().toAbsolutePath().toString();

        //filling General
        fillTabGeneral(newItem, absolutePath);

        //filling Information
        driver.findElements(By.cssSelector("div.tabs li")).get(1).click();
        Thread.sleep(1000);
        fillTabInformation();

        //filling Price
        driver.findElements(By.cssSelector("div.tabs li")).get(3).click();
        Thread.sleep(1000);
        fillTabPrices();

        //Save
        driver.findElement(By.cssSelector("button[name=save]")).click();
        Thread.sleep(1000);

        //Check
        checkNewItem(newItem);
    }

    private void fillTabGeneral(String item, String path){
        //Name
        driver.findElement(By.name("name[en]")).sendKeys(item);
        //Status
        driver.findElement(By.cssSelector("input[name=status][value='1']")).click();
        //Code
        driver.findElement(By.name("code")).sendKeys("rp001");
        //Categories
        driver.findElement(By.cssSelector("input[type=checkbox][value='0']")).click();
        driver.findElement(By.cssSelector("input[type=checkbox][value='1']")).click();
        //Quantity
        driver.findElement(By.name("quantity")).sendKeys(Keys.CONTROL + "a" + Keys.DELETE );
        driver.findElement(By.name("quantity")).sendKeys("50");
        //Upload file
        driver.findElement(By.name("new_images[]")).sendKeys(path);
    }

    private void fillTabInformation() {
        //Manufacturer
        Select manufacturer = new Select(driver.findElement(By.name("manufacturer_id")));
        manufacturer.selectByVisibleText("ACME Corp.");
        //Short Description
        driver.findElement(By.name("short_description[en]")).sendKeys("It's a Pirate Duck");
        //Description
        driver.findElement(By.className("trumbowyg-editor")).sendKeys("Very very dangerous duck!");
    }

    private void fillTabPrices() {
        // Purchase Price
        driver.findElement(By.name("purchase_price")).sendKeys(Keys.CONTROL + "a" + Keys.DELETE);
        driver.findElement(By.name("purchase_price")).sendKeys("11");
        Select currentCode = new Select(driver.findElement(By.name("purchase_price_currency_code")));
        currentCode.selectByVisibleText("US Dollars");
        // Price
        driver.findElement(By.name("prices[USD]")).sendKeys("20");
    }

    private void checkNewItem(String item) {
        String res = "not";
        String name;
        WebElement root = driver.findElement(By.cssSelector("table.dataTable tbody"));
        List<WebElement> list = root.findElements(By.xpath(".//tr/td[3]/a"));
        for (WebElement duck : list) {
            name = duck.getText();
            if (name.equals(item) ) {
                res = "successfully";
                break;
            }
        }
        System.out.println("New item " + item + " " + res + " added");
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
