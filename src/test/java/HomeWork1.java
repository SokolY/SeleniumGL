import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class HomeWork1 {

    static WebDriver driver;
    WebElement userName;
    WebElement password;//
    WebElement lgnBtn;
    WebElement menuItem;
    WebElement subMenuItem;
    WebElement menuHeader;

    boolean isElementPresent(WebDriver driver, By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }
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
        password.sendKeys("");
        Thread.sleep(2000);
        lgnBtn.click();

    }

    @Test
    public void prt() throws InterruptedException {
        Thread.sleep(2000);
        ArrayList<WebElement> menuItems = new ArrayList<WebElement>(driver.findElements(By.cssSelector("#box-apps-menu>li"))); //find all menu items
        menuHeader = driver.findElement(By.cssSelector("div.panel-heading"));
//        System.out.println("Menu length - " + menuItems.size());

        for (int i = 1; i<=menuItems.size(); i++){
            menuItem = driver.findElement(By.xpath("//*[@id=\"box-apps-menu\"]/li[" + i + "]"));
            menuItem.click();
            Thread.sleep(1000);
            ArrayList<WebElement> subMenuItems = new ArrayList<>(driver.findElements(By.cssSelector("#box-apps-menu > li.app.selected > ul > li"))); //find quantity of submenu items for each menu
//            System.out.println("SubMenu length " + subMenuItems.size());
            for (int j = 1; j<=subMenuItems.size(); j++){
                menuItem = driver.findElement(By.xpath("//*[@id=\"box-apps-menu\"]/li[" + i + "]"));
                subMenuItem = menuItem.findElement(By.xpath(".//ul/li[" + j + "]/a"));
                subMenuItem.click();
                Thread.sleep(1000);
                Assertions.assertTrue(driver.findElement(By.cssSelector("div.panel-heading")).isDisplayed());
            }
            Thread.sleep(1000);
            Assertions.assertTrue(driver.findElement(By.cssSelector("div.panel-heading")).isDisplayed());
        }

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



//    @Test
//    public void prt2(){
//        System.out.println("Hello World2");
//    }
}
