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

public class checkSite {
    private WebDriver driver;
    public WebDriverWait wait;

    @Before
    public void start(){
        driver = new ChromeDriver();
        //driver = new FirefoxDriver();
        //driver = new InternetExplorerDriver();
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
    public void compareProductPrice() throws Exception{
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
    public void RegularProductStyle() throws Exception{
        driver.get("http://localhost/litecart");
        WebElement ProductPriceMP = driver.findElement(By.cssSelector("#box-campaigns li.product .regular-price"));
        String RegPriceColorMP = ProductPriceMP.getCssValue("color");
        String RegPriceColorMPPreCut = RegPriceColorMP.substring(RegPriceColorMP.indexOf("(")+1);
        String RegPriceColorMPCut = RegPriceColorMPPreCut.substring(0,RegPriceColorMPPreCut.length()-1);
        String[] RPC1 = RegPriceColorMPCut.split(", ");
        int c1 = Integer.parseInt(RPC1[0]);
        for(int i = 1; i < 3; i++){
            if (Integer.parseInt(RPC1[i]) != c1){
                throw new Exception("Color of regular price in the main page is not gray");
            }
        }
        String RegPriceLineMP = ProductPriceMP.getCssValue("text-decoration");
        if (!RegPriceLineMP.contains("line-through")){
            throw new Exception("Regular price in the main page is not crossed");
        }

        ProductPriceMP.click();
        WebElement ProductPricePP = driver.findElement(By.cssSelector("#box-product .regular-price"));
        String RegPriceColorPP = ProductPricePP.getCssValue("color");
        String RegPriceColorPPPreCut = RegPriceColorPP.substring(RegPriceColorPP.indexOf("(")+1);
        String RegPriceColorPPCut = RegPriceColorPPPreCut.substring(0,RegPriceColorPPPreCut.length()-1);
        String[] RPC2 = RegPriceColorPPCut.split(", ");
        int c2 = Integer.parseInt(RPC2[0]);
        for(int i = 1; i < 3; i++){
            if (Integer.parseInt(RPC2[i]) != c2){
                throw new Exception("Color of regular price in the main page is not gray");
            }
        }
        String RegPriceLinePP = ProductPricePP.getCssValue("text-decoration");
        if (!RegPriceLinePP.contains("line-through")){
            throw new Exception("Regular price in the product page is not crossed");
        }
    }

    @Test
    public void CampaignProductStyle() throws Exception{
        driver.get("http://localhost/litecart");
        WebElement ProductPriceMP = driver.findElement(By.cssSelector("#box-campaigns li.product .campaign-price"));
        String CamPriceColorMP = ProductPriceMP.getCssValue("color");
        String CamPriceColorMPPreCut = CamPriceColorMP.substring(CamPriceColorMP.indexOf("(")+1);
        String CamPriceColorMPCut = CamPriceColorMPPreCut.substring(0,CamPriceColorMPPreCut.length()-1);
        String[] CPC1 = CamPriceColorMPCut.split(", ");
        if (Integer.parseInt(CPC1[1]) != 0 || Integer.parseInt(CPC1[2]) != 0 ){
            throw new Exception("Color of regular price in the main page is not gray");
        }
        String CamPriceLineMP = ProductPriceMP.getCssValue("font-weight");
        if (Integer.parseInt(CamPriceLineMP) < 700){
            throw new Exception("Campaign price in the main page is not in bold");
        }

        ProductPriceMP.click();
        WebElement ProductPricePP = driver.findElement(By.cssSelector("#box-product .campaign-price"));
        String CamPriceColorPP = ProductPricePP.getCssValue("color");
        String CamPriceColorPPPreCut = CamPriceColorPP.substring(CamPriceColorPP.indexOf("(")+1);
        String CamPriceColorPPCut = CamPriceColorPPPreCut.substring(0,CamPriceColorPPPreCut.length()-1);
        String[] CPC2 = CamPriceColorPPCut.split(", ");
        if (Integer.parseInt(CPC2[1]) != 0 || Integer.parseInt(CPC2[2]) != 0 ){
            throw new Exception("Color of regular price in the main page is not gray");
        }
        String CamPriceLinePP = ProductPricePP.getCssValue("font-weight");
        if (Integer.parseInt(CamPriceLinePP) < 700){
            throw new Exception("Campaign price in the product page is not crossed");
        }
    }

    @Test
    public void priceSizeComparison() throws Exception{
        driver.get("http://localhost/litecart");
        WebElement RegPriceMP = driver.findElement(By.cssSelector("#box-campaigns li.product .regular-price"));
        WebElement CamPriceMP = driver.findElement(By.cssSelector("#box-campaigns li.product .campaign-price"));
        String RegPriceFontMP = RegPriceMP.getCssValue("font-size");
        String CamPriceFontMP = CamPriceMP.getCssValue("font-size");
        String RegPriceFontMPCut = RegPriceFontMP.substring(0,RegPriceFontMP.length()-2);
        String CamPriceFontMPCut = CamPriceFontMP.substring(0, CamPriceFontMP.length()-2);
        if (Double.parseDouble(RegPriceFontMPCut) >= (Double.parseDouble(CamPriceFontMPCut))) {
            throw new Exception("In the main page size of the regular price is bigger than or same with the " +
                    "campaign price");
        }

        RegPriceMP.click();
        WebElement RegPricePP = driver.findElement(By.cssSelector("#box-product .regular-price"));
        WebElement CamPricePP = driver.findElement(By.cssSelector("#box-product .campaign-price"));
        String RegPriceFontPP = RegPricePP.getCssValue("font-size");
        String CamPriceFontPP = CamPricePP.getCssValue("font-size");
        String RegPriceFontPPCut = RegPriceFontPP.substring(0,RegPriceFontPP.length()-2);
        String CamPriceFontPPCut = CamPriceFontPP.substring(0, CamPriceFontPP.length()-2);
        if (Double.parseDouble(RegPriceFontPPCut) >= (Double.parseDouble(CamPriceFontPPCut))) {
            throw new Exception("In the product page size of the regular price is bigger than or same with the " +
                    "campaign price");
        }
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
