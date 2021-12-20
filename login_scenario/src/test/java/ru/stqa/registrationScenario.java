package ru.stqa;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.Random;

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

        String email = randomGenerator(7) + "@gmail.com";
        String password = String.valueOf(new Random().nextInt(1000000));

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
        driver.findElement(By.name("firstname")).sendKeys(randomGenerator(7));
        driver.findElement(By.name("lastname")).sendKeys(randomGenerator(14));
        driver.findElement(By.name("address1")).sendKeys(new Random().nextInt(1000) + " " +
                randomGenerator(10) + " st.");
        driver.findElement(By.name("postcode")).sendKeys("0" + new Random().
                nextInt(10000));
        String city = randomGenerator(6);
        driver.findElement(By.name("city")).sendKeys(city);
        //Country
        Select country = new Select(driver.findElement(By.cssSelector("select[name='country_code']")));
        JavascriptExecutor js1 = (JavascriptExecutor) driver;
        js1.executeScript("arguments[0].selectedIndex = 224; arguments[0].dispatchEvent(new Event('change'))",
                country);
        //State
        Random rand = new Random();
        String list = String.valueOf(rand.nextInt(64));
        Select zone = new Select(driver.findElement(By.cssSelector("select[name=zone_code]")));
        JavascriptExecutor js2 = (JavascriptExecutor) driver;
        js2.executeScript("arguments[0].selectedIndex =" + list + "; arguments[0].dispatchEvent(new " +
                "Event('change'))", zone);
        //Registration information
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("phone")).sendKeys("+" + new Random().nextLong(100000000000L));
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

    static final String SOURCE = "abcdefghijklmnopqrstuvwxyz";
    static SecureRandom secureRnd = new SecureRandom();

    private String randomGenerator(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(SOURCE.charAt(secureRnd.nextInt(SOURCE.length())));
        return sb.toString();
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
