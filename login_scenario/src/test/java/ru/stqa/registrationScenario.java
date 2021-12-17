package ru.stqa;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;

public class registrationScenario {
    private WebDriver driver;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void registration() throws InterruptedException {
        driver.get("http://localhost/litecart/en/");

        String email = "malaka@gmail.com";
        String password = "13579";

        createAccount(email, password);
        Thread.sleep(1000);

        logout();
        Thread.sleep(1000);

        login(email, password);
        Thread.sleep(1000);

        logout();
        Thread.sleep(1000);
    }

    private void createAccount(String email, String password) {
        driver.findElement(By.cssSelector("form[name='login_form'] table tr:last-child")).click();
        driver.findElement(By.name("firstname")).sendKeys("Paninis");
        driver.findElement(By.name("lastname")).sendKeys("Daniels");
        driver.findElement(By.name("address1")).sendKeys("160 Webster st.");
        driver.findElement(By.name("postcode")).sendKeys("02128");
        driver.findElement(By.name("city")).sendKeys("Boston");
        //Country
        Select country = new Select(driver.findElement(By.name("country_code")));
        country.selectByVisibleText("United States");
        //State
        Select zone = new Select(driver.findElement(By.cssSelector("select[name='zone_code']")));
        zone.selectByVisibleText("Massachusetts");
        //*****
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("phone")).sendKeys("+18452245869");
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("confirmed_password")).sendKeys(password);
        driver.findElement(By.name("create_account")).click();
    }

    private void login(String email, String password) {
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("login")).click();
    }

    private void logout() {
        driver.findElement(By.cssSelector("div#box-account div.content li:last-child a")).click();
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
