import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Map;

public class TestBase {

    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    public WebDriver driver;
    public WebDriverWait wait;


    public String getBASE_URL() {
        return System.getenv("LITECART_BASE_URL");
    }

    public String getLOGIN_NAME() {
        return System.getenv("LITECART_ADMIN_USER");
    }

    public String getLOGIN_PASS() {
        return System.getenv("LITECART_ADMIN_PASSWORD");
    }


//    @BeforeEach
    public void start() throws MalformedURLException {

        if (tlDriver.get() != null) {
            driver = tlDriver.get();
            wait = new WebDriverWait(driver, 10);
            return;
        }

//        driver = new ChromeDriver();
        driver = new FirefoxDriver();
        // driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);

        tlDriver.set(driver);

//        Runtime.getRuntime().addShutdownHook(
//                new Thread(() -> {
//                    driver.quit();
//                    driver = null;
//                }));
    }

    public String getTestURL() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("test.html").getFile());
        return "file:///" +
                file.getAbsolutePath();
    }


    public boolean isElementPresent(By element) {
        return driver.findElements(element).size() > 0;
    }

    public void loginToAdminPane() {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        By username = By.cssSelector("input[name=username]");
        By password = By.cssSelector("input[name=password]");
        By loginBtn = By.cssSelector("button[name=login]");

        driver.get(getBASE_URL() + "/admin");

        if (isElementPresent(username)) {
            driver.findElement(username).sendKeys(getLOGIN_NAME());
            driver.findElement(password).sendKeys(getLOGIN_PASS());
            driver.findElement(loginBtn).click();
        }
        wait.until(ExpectedConditions.elementToBeClickable(By.id("box-apps-menu")));
        // pause(3);
    }
}