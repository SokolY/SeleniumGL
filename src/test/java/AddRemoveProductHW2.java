import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class AddRemoveProductHW2 extends TestBase{
    static WebDriver driver;
    WebElement userName;
    WebElement password;//
    WebElement lgnBtn;
    WebElement menuItem;
    WebElement subMenuItem;
    WebElement menuHeader;
    By itemsCountLabel = By.cssSelector(".badge.quantity");
    By openCart = By.cssSelector("#cart");
//    WebElement header = driver.findElement(By.cssSelector("#header > a"));
    static By cookiesNotice = new By.ById("box-cookie-notice");
    By emptyCart = By.xpath("//*[text()='There are no items in your cart.']");
    By removeProduct = By.cssSelector("[name='remove_cart_item']");
    By cartItemsTable = By.cssSelector(".items.list-unstyled");

    @BeforeAll
    public static void beforeAll() throws InterruptedException {
//        System.setProperty("webdriver.gecko.driver", "D:\\Yura\\SeleniumGL\\utils\\geckodriver-v0.29.1-win64\\geckodriver.exe");
//        System.setProperty("webdriver.gecko.driver", "D:\\Yura\\SeleniumGL\\utils\\geckodriver-v0.29.1-win64\\geckodriver.exe");
//        WebDriverManager.chromedriver().setup();
//        System.out.println("Before Test");
        driver = new FirefoxDriver();
        driver.get("http://158.101.173.161");


//        WebElement cookiesNoticeLocator = wait.until(ExpectedConditions.presenceOfElementLocated(cookiesNotice));
        if (driver.findElement(cookiesNotice).isDisplayed()){
            driver.findElement(By.name("accept_cookies")).click();
        }

    }


    @Test
    public void addRemovePopularItem() throws InterruptedException {
        wait = new WebDriverWait(driver, 10);
        int productQuantity = 3;
        for(int j = 0; j<productQuantity; j++){
            addPopularItem();
            wait.until(textToBePresentInElementLocated(itemsCountLabel, String.valueOf(j+1)));
            driver.get("http://158.101.173.161");
            WebElement popularProductMenu = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("box-popular-products")));
        }
        assertThat(driver.findElement(itemsCountLabel).getText(), is(String.valueOf(productQuantity)));
        cleanUpCart();
        assertThat(driver.findElement(itemsCountLabel).getText(), is(""));
    }

    @AfterEach
    public void afterEach() {
        driver.quit();
        System.out.println("After Each Test");

    }

    public void addPopularItem() throws InterruptedException {
        wait = new WebDriverWait(driver, 10);
        //*[@id="box-popular-products"]/div/article[4]
        Random rand = new Random();
        int i = rand.nextInt(5) + 1;
        driver.findElement(By.xpath("//*[@id=\"box-popular-products\"]/div/article[" + i + "]")).click();
        WebElement addToCart = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn-success")));
        addToCart.click();
    }

    private void cleanUpCart() throws InterruptedException {

        wait.until(elementToBeClickable(openCart)).click();
        Thread.sleep(2000);

        System.out.println("Size is " + driver.findElements(emptyCart).size());
        while (driver.findElements(emptyCart).size()==0){
            WebElement table = wait.until(elementToBeClickable(cartItemsTable));
            wait.until(elementToBeClickable(removeProduct)).click();
            wait.until(stalenessOf(table));
        }
        driver.get("http://158.101.173.161");
    }


}


