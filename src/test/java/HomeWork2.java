import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class HomeWork2 extends TestBase{
    static WebDriver driver;
    WebElement userName;
    WebElement password;//
    WebElement lgnBtn;
    WebElement menuItem;
    WebElement subMenuItem;
    WebElement menuHeader;
    @BeforeAll
    public static void beforeAll() {
//        System.setProperty("webdriver.gecko.driver", "D:\\Yura\\SeleniumGL\\utils\\geckodriver-v0.29.1-win64\\geckodriver.exe");
//        System.setProperty("webdriver.gecko.driver", "D:\\Yura\\SeleniumGL\\utils\\geckodriver-v0.29.1-win64\\geckodriver.exe");
//        WebDriverManager.chromedriver().setup();
        System.out.println("Before Test");
        driver = new FirefoxDriver();

    }

    @BeforeEach
    public void setUp() throws InterruptedException {
        System.out.println("Before Each Test");
        driver.get("http://158.101.173.161/admin/login.php?redirect_url=%2Fadmin%2F");
        Thread.sleep(3000);
        WebElement userName = driver.findElement(By.name("username"));
        WebElement password = driver.findElement(By.name("password"));//
        WebElement lgnBtn = driver.findElement(By.cssSelector(".btn-default"));
        userName.sendKeys("");
        password.sendKeys("23");
        Thread.sleep(2000);
        lgnBtn.click();

    }

    @Test
    public void prt() throws InterruptedException {
        Thread.sleep(2000);
        ArrayList<WebElement> menuItems = new ArrayList<WebElement>(driver.findElements(By.cssSelector("#box-apps-menu>li"))); //find all menu items


    }
    @AfterAll
    public static void afterAll() {
        System.out.println("After Test");
    }

    @AfterEach
    public void afterEach() {
        driver.close();
        System.out.println("After Each Test");

    }



}


