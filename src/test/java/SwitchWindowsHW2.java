import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class SwitchWindowsHW2 extends TestBase{
    static WebDriver driver;
    WebElement userName;
    WebElement password;//
    WebElement lgnBtn;
    WebElement addNenCountry;

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
        wait = new WebDriverWait(driver, 10);
        driver.get("http://158.101.173.161/admin/login.php?redirect_url=%2Fadmin%2F");
//        Thread.sleep(3000);
        WebElement userName = driver.findElement(By.name("username"));
        WebElement password = driver.findElement(By.name("password"));//
        WebElement lgnBtn = driver.findElement(By.cssSelector(".btn-default"));
        wait.until(visibilityOfElementLocated(By.name("password")));
        userName.sendKeys("testadmin");
        password.sendKeys("R8MRDAYT_test");

        wait.until(elementToBeClickable(lgnBtn)).click();
//        lgnBtn.click();

    }

    @Test
    public void newWindowTest() throws InterruptedException {
        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"box-apps-menu\"]/li[3]"))).click();
//        addNenCountry = driver.findElement(By.cssSelector(".panel-action"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".panel-action"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".fa-external-link")));
//        List<WebElement> externalLinks = driver.findElements(By.cssSelector(".fa-external-link"));
//        System.out.println("Quantity of links - " + externalLinks.size());
        for (WebElement exteLnk : driver.findElements(By.cssSelector(".fa-external-link"))){
            String originalWin = driver.getWindowHandle();
            Set<String> allWin = driver.getWindowHandles();
            exteLnk.click();
            String newW  = wait.until(anyWindowOtherThan(allWin));
            driver.switchTo().window(newW);
            Thread.sleep(1500);
            assertThat(driver.getTitle(), containsString("Wikipedia"));
            driver.close();
            driver.switchTo().window(originalWin);
            assertThat(driver.getTitle(), containsString("Add New Country"));
        }
    }
    @AfterEach
    public void afterEach() throws InterruptedException {
        Thread.sleep(7000);
        driver.quit();
        System.out.println("After Each Test");

    }

    public ExpectedCondition<String> anyWindowOtherThan(Set<String> windows) {
        return input -> {
            Set<String> handles = driver.getWindowHandles();
            handles.removeAll(windows);
            return handles.size() > 0 ? handles.iterator().next() : null;
        };
    }

}
