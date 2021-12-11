package ru.stqa;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.Objects;

public class Chrome {
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
        tlDriver.set(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(1));
    }

    @Test
    public void compareProductName() throws Exception{
        driver.get("http://localhost/litecart");
        WebElement product = driver.findElement(By.cssSelector("#box-campaigns li.product"));
        String NameMP = product.findElement(By.cssSelector("div.name")).getText();
        product.click();
        String NamePP = driver.findElement(By.cssSelector("h1.title")).getText();
        if(NameMP.compareTo(NamePP) != 0){
            throw new Exception("Name in the main page and product page does not match");
        }
    }

    @Test
    public void compareProductPrice() throws Exception {
        driver.get("http://localhost/litecart");
        WebElement product = driver.findElement(By.cssSelector("#box-campaigns li.product"));
        String RegPriceMP = product.findElement(By.cssSelector(".regular-price")).getText();
        String CamPriceMP = product.findElement(By.cssSelector(".campaign-price")).getText();
        product.click();
        String RegPricePP = driver.findElement(By.cssSelector("#box-product .regular-price")).getText();
        String CamPricePP = driver.findElement(By.cssSelector("#box-product .campaign-price")).getText();
        if(RegPriceMP.compareTo(RegPricePP) != 0){
            throw new Exception("Regular price in the main and product page does not match");
        }
        if(CamPriceMP.compareTo(CamPricePP) != 0){
            throw new Exception("Campaign price in the main and product page does not match");
        }
    }

    @Test
    public void RegularProductStyle() throws Exception {
        driver.get("http://localhost/litecart");
        WebElement ProductPriceMP = driver.findElement(By.cssSelector("#box-campaigns li.product .regular-price"));
        String RegPriceColorMP = ProductPriceMP.getCssValue("color");
        String hexColorMP = Color.fromString(RegPriceColorMP).asHex();
        if (!Objects.equals(hexColorMP, "#777777")) {
            throw new Exception("Color of regular price in the main page is not gray");
        }
        String RegPriceLineMP = ProductPriceMP.getCssValue("text-decoration-line");
        if (!Objects.equals(RegPriceLineMP, "line-through")){
            throw new Exception("Regular price in the main page is not crossed");
        }

        ProductPriceMP.click();
        WebElement ProductPricePP = driver.findElement(By.cssSelector("#box-product .regular-price"));
        String RegPriceColorPP = ProductPricePP.getCssValue("color");
        String hexColorPP = Color.fromString(RegPriceColorPP).asHex();
        if (!Objects.equals(hexColorPP, "#666666")) {
            throw new Exception("Color of regular price in the product page is not gray");
        }
        String RegPriceLine = ProductPricePP.getCssValue("text-decoration-line");
        if (!Objects.equals(RegPriceLine, "line-through")){
            throw new Exception("Regular price in the product page is not crossed");
        }
    }

    @Test
    public void CampaignProductStyle() throws Exception {
        driver.get("http://localhost/litecart");
        WebElement ProductPriceMP = driver.findElement(By.cssSelector("#box-campaigns li.product .campaign-price"));
        String CamPriceColorMP = ProductPriceMP.getCssValue("color");
        String hexColor = Color.fromString(CamPriceColorMP).asHex();
        if (!Objects.equals(hexColor, "#cc0000")) {
            throw new Exception("Color of regular price in the main page is not red");
        }
        String CamPriceLine = ProductPriceMP.getCssValue("font-weight");
        if (!Objects.equals(CamPriceLine, "700")){
            throw new Exception("Campaign price in the main page is not in bold");
        }

        ProductPriceMP.click();
        WebElement ProductPricePP = driver.findElement(By.cssSelector("#box-product .campaign-price"));
        String RegPriceColorPP = ProductPricePP.getCssValue("color");
        String hexColorPP = Color.fromString(RegPriceColorPP).asHex();
        if (!Objects.equals(hexColorPP, "#cc0000")) {
            throw new Exception("Color of campaign price in the product page is not gray");
        }
        String RegPriceLine = ProductPricePP.getCssValue("font-weight");
        if (!Objects.equals(RegPriceLine, "700")){
            throw new Exception("Campaign price in the product page is not crossed");
        }
    }

    @Test
    public void priceSizeComparison() throws Exception {
        driver.get("http://localhost/litecart");
        WebElement RegPriceMP = driver.findElement(By.cssSelector("#box-campaigns li.product .regular-price"));
        WebElement CamPriceMP = driver.findElement(By.cssSelector("#box-campaigns li.product .campaign-price"));
        String RegPriceFontMP = RegPriceMP.getCssValue("font-size");
        String CamPriceFontMP = CamPriceMP.getCssValue("font-size");
        String RegPriceFontMPCut = RegPriceFontMP.substring(0,RegPriceFontMP.length()-2);
        String CamPriceFontMPCut = CamPriceFontMP.substring(0, CamPriceFontMP.length()-2);
        if (RegPriceFontMPCut.compareTo(String.valueOf(Double.parseDouble(CamPriceFontMPCut))) > 0) {
            throw new Exception("Size of prices differ or are the same in the main page");
        }

        RegPriceMP.click();
        WebElement RegPricePP = driver.findElement(By.cssSelector("#box-product .regular-price"));
        WebElement CamPricePP = driver.findElement(By.cssSelector("#box-product .campaign-price"));
        String RegPriceFontPP = RegPricePP.getCssValue("font-size");
        String CamPriceFontPP = CamPricePP.getCssValue("font-size");
        String RegPriceFontPPCut = RegPriceFontPP.substring(0,RegPriceFontPP.length()-2);
        String CamPriceFontPPCut = CamPriceFontPP.substring(0, CamPriceFontPP.length()-2);
        if (RegPriceFontPPCut.compareTo(String.valueOf(Double.parseDouble(CamPriceFontPPCut))) > 0) {
            throw new Exception("Size of prices differ or are the same in the product page");
        }
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
