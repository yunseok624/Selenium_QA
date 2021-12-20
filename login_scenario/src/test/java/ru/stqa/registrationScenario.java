package ru.stqa;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.Random;
import java.util.UUID;

public class registrationScenario {
    private WebDriver driver;

    @Before
    public void start() {
        driver = new ChromeDriver();
        //driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void registration() throws InterruptedException {
        driver.get("http://localhost/litecart/en/");

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String randomEmail = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        String email = randomEmail + "@gmail.com";
        String password = String.valueOf(new Random().nextInt(1000));

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
        String name = UUID.randomUUID().toString();
        driver.findElement(By.name("firstname")).sendKeys(name);
        String surname = UUID.randomUUID().toString();
        driver.findElement(By.name("lastname")).sendKeys(surname);
        driver.findElement(By.name("address1")).sendKeys(new Random().nextInt(1000) +
                UUID.randomUUID().toString() + "st.");
        driver.findElement(By.name("postcode")).sendKeys("0" + new Random().
                nextInt(10000));
        driver.findElement(By.name("city")).sendKeys("Boston");
        //Country
        Select country = new Select(driver.findElement(By.cssSelector("select[name='country_code']")));
        JavascriptExecutor js1 = (JavascriptExecutor) driver;
        js1.executeScript("arguments[0].selectedIndex = 224; arguments[0].dispatchEvent(new Event('change'))",
                country);
        //State
        Select zone = new Select(driver.findElement(By.cssSelector("select[name=zone_code]")));
        JavascriptExecutor js2 = (JavascriptExecutor) driver;
        js2.executeScript("arguments[0].selectedIndex = 31; arguments[0].dispatchEvent(new Event('change'))",
                zone);
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
