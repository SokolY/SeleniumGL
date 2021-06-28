import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class ListenerHW3 extends TestBase{
    static WebDriver driver;
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
    static EventFiringWebDriver erd;
    static WebDriverWait wait;

    @BeforeAll
    public static void beforeAll() throws InterruptedException {
        erd = new EventFiringWebDriver(new FirefoxDriver());
        erd.register(new Listener());
        wait = new WebDriverWait(erd, 10);
        erd.get("http://158.101.173.161");


//        WebElement cookiesNoticeLocator = wait.until(ExpectedConditions.presenceOfElementLocated(cookiesNotice));
        if (erd.findElement(cookiesNotice).isDisplayed()){
            erd.findElement(By.name("accept_cookies")).click();
        }

    }


    @Test
    public void addRemovePopularItem() throws InterruptedException {
        wait = new WebDriverWait(erd, 10);
        int productQuantity = 3;
        for(int j = 0; j<productQuantity; j++){
            addPopularItem();
            wait.until(textToBePresentInElementLocated(itemsCountLabel, String.valueOf(j+1)));
            erd.get("http://158.101.173.161");
            WebElement popularProductMenu = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("box-popular-products")));
        }
        assertThat(erd.findElement(itemsCountLabel).getText(), is(String.valueOf(productQuantity)));
        cleanUpCart();
        assertThat(erd.findElement(itemsCountLabel).getText(), is(""));
    }

    @AfterEach
    public void afterEach() {
        erd.quit();
        System.out.println("After Each Test");

    }

    public void addPopularItem() throws InterruptedException {
        wait = new WebDriverWait(erd, 3);
        //*[@id="box-popular-products"]/div/article[4]
        Random rand = new Random();
        int i = rand.nextInt(5) + 1;
        erd.findElement(By.xpath("//*[@id=\"box-popular-products\"]/div/article[" + i + "]")).click();
        WebElement addToCart = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn-successs")));
        addToCart.click();
    }

    private void cleanUpCart() throws InterruptedException {

        wait.until(elementToBeClickable(openCart)).click();
        Thread.sleep(2000);

        System.out.println("Size is " + erd.findElements(emptyCart).size());
        while (erd.findElements(emptyCart).size()==0){
            WebElement table = wait.until(elementToBeClickable(cartItemsTable));
            wait.until(elementToBeClickable(removeProduct)).click();
            wait.until(stalenessOf(table));
        }
        erd.get("http://158.101.173.161");
    }


}
