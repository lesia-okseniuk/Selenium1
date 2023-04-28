package org.example;

import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.Duration;
import java.util.List;
import java.util.Properties;


import static org.assertj.core.api.ClassBasedNavigableIterableAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
public class SearchTest {

    Properties properties;
    WebDriver driver = null;

    @BeforeClass
    @SneakyThrows
    public void beforeClass() {
        properties = new Properties();
        try (BufferedReader reader = new BufferedReader(new FileReader("config.properties"))) {
            properties.load(reader);
            File file = new File(properties.getProperty("path"));
            String driverName = properties.getProperty("driver");
            System.setProperty(driverName, file.getAbsolutePath());

            if (driverName.contains("chrome")) {
                driver = new ChromeDriver();
            } else if (driverName.contains("firefox")) {
                driver = new FirefoxDriver();
            }
        }
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }

    @Test
    @SneakyThrows
    public void openBrowserInTest() {
        /*File file = new File(properties.getProperty("path"));
        String driverName = properties.getProperty("driver");
        System.setProperty(driverName, file.getAbsolutePath());
        WebDriver driver = null;
        if (driverName.contains("chrome")) {
            driver = new ChromeDriver();
        } else if (driverName.contains("firefox")) {
            driver = new FirefoxDriver();
        }*/
        assertThat(driver).isNotNull();

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30)); // Таймаути
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        driver.navigate().to("https://www.google.com/"); // Піти на сторінку

        System.out.println(driver.getTitle());

        WebElement queryString = driver.findElement(By.name("q")); // Знайти елемент на сторінці
        queryString.sendKeys("Selenium Java"); // Ввести в знайдене поле

        WebElement searchButton = driver.findElement(By.name("btnK"));
        searchButton.click();

        //queryString.sendKeys("Selenium Java\n"); // альтернативний варіант з символом переведення рядка
        //queryString.sendKeys(Keys.ENTER); // Натиснути ENTER

        System.out.println(driver.getTitle());

        WebElement firstResult = driver.findElement(By.cssSelector("#rso > div:nth-child(1) > div > div > div > div > div > div:nth-child(2) > div"));
        String firstResultText = firstResult.getText();
        assertThat(firstResultText)
                .isNotEmpty()
                .contains(
                        "Selenium","WebDriver","Java")
                .doesNotContain("restAssured", "protractor");

        driver.navigate().back();
        Thread.sleep(1000);
        System.out.println(driver.getTitle());

        WebElement gmailLink = driver.findElement(By. xpath("//a[@class='gb_t']"));
        System.out.println("href attribute contains value: " +
                gmailLink.getAttribute( "href"));
        System.out.println("element contains text: " +
                gmailLink.getText());
    }

    @Test
    public void openYouTube() {
        File file = new File(properties.getProperty("path"));
        String driverName = properties.getProperty("driver");
        System.setProperty(driverName, file.getAbsolutePath());
        WebDriver driver = null;
        if (driverName.contains("chrome")) {
            driver = new ChromeDriver();
        } else if (driverName.contains("firefox")) {
            driver = new FirefoxDriver();
        }
        assertThat(driver).isNotNull();

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10)); // Таймаути
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://youtube.com/"); // Відкриваємо YouTube
        driver.navigate().refresh(); // Оновлюємо сторінку
        System.out.println(driver.getTitle()); // Виводимо назву сторінки
        driver.close(); // Закриваємо сторінку
    }

    @Test
    @SneakyThrows
    public void openBerkut () {
        assertThat(driver).isNotNull();

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.navigate().to("http://berkut.mk.ua/test2022/");
        //driver.get("http://berkut.mk.ua/logictest/"); // Тест на логіку
        System.out.println(driver.getTitle());

        WebElement name = driver.findElement(By.name("fio"));
        name.sendKeys("Student");
        Thread.sleep(1000);

        WebElement group = driver.findElement(By.id("group"));
        group.sendKeys("1111");
        Thread.sleep(1000);

        WebElement button = driver.findElement(By.className("accept"));
        button.click();
        Thread.sleep(1000);

        List<WebElement> answers = driver.findElements(By.className("answer"));
        //answers.forEach(System.out::println);
        for (WebElement answer : answers) {
            System.out.println(answer.getText());
        }
    }

    @Test
    @SneakyThrows
    public void cssAndXpathPractise() {
        assertThat(driver).isNotNull();

        driver.navigate().to("https://youtube.com");
        System.out.println(driver.getTitle());

        WebElement searchFieldXpath = driver.findElement(By.xpath("//input[@id='search']"));
        searchFieldXpath.sendKeys("vielleicht vielleicht");
        WebElement searchButtonXpath = driver.findElement(By.xpath("//button[@id='search-icon-legacy']"));
        searchButtonXpath.click();
        System.out.println(driver.getTitle());
        Thread.sleep(1000);
        searchFieldXpath.clear();
        driver.navigate().back();

        WebElement searchFieldCss = driver.findElement(By.cssSelector("input#search"));
        searchFieldCss.sendKeys("поплава");
        WebElement searchButtonCss = driver.findElement(By.cssSelector("button#search-icon-legacy\n"));
        searchButtonCss.click();
        System.out.println(driver.getTitle());
        Thread.sleep(1000);
        searchFieldXpath.clear();
        driver.navigate().back();

        WebElement leftMenu = driver.findElement(By.cssSelector("ytd-mini-guide-renderer")); // Обираємо елементи лівого меню
        for (WebElement item : leftMenu.findElements(By.tagName("a"))) { // Виводимо пункти меню в консоль
            System.out.println(item.getText());
        }

        WebElement typeImage = driver.findElement(By.xpath("//*[contains(@type, 'image')]"));
        driver.close();
    }

    @Test
    @SneakyThrows
    public void practiseWithGoogle() {
        assertThat(driver).isNotNull();

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.navigate().to("https://www.google.com/");

        System.out.println(driver.getTitle());

        WebElement queryString = driver.findElement(By.name("q"));
        queryString.clear();
        queryString.sendKeys("пес Патрон");

        WebElement searchButton = driver.findElement(By.name("btnK"));
        searchButton.sendKeys(Keys. ENTER);

        System.out.println(driver.getTitle());

        WebElement resultStats = driver.findElement(By.id("result-stats"));
        System.out.println(resultStats.getText());

        driver.navigate().back();

        WebElement feelingLuckyButton =
                driver.findElement(By.name("btnI"));
        System.out.println(feelingLuckyButton.getAttribute("value"));
        assertTrue(feelingLuckyButton.isEnabled(),
                "I'm Feeling Lucky button is disabled!");

        Assert.assertEquals(feelingLuckyButton.getAttribute( "value"),
                "Мені пощастить",
                "Wrong text has been displayed!");

        WebElement pageHeader = driver.findElement(By.className("lnXdpd"));
        assertThat(pageHeader.getAttribute("alt"))
                .as("Page have a wrong title!")
                .contains("Google");

        driver.close();
    }

}
