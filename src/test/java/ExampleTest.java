import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ExampleTest {

    static WebDriver driver;


    @BeforeAll
    public static void beforeAll() {
//        System.setProperty("webdriver.gecko.driver", "D:\\Yura\\SeleniumGL\\utils\\geckodriver-v0.29.1-win64\\geckodriver.exe");
//        System.setProperty("webdriver.gecko.driver", "D:\\Yura\\SeleniumGL\\utils\\geckodriver-v0.29.1-win64\\geckodriver.exe");
//        WebDriverManager.chromedriver().setup();
        System.out.println("Before Test");
        driver = new FirefoxDriver();

    }

    @BeforeEach
    public void setUp() {
        System.out.println("Before Each Test");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("After Test");
    }

    @AfterEach
    public void afterEach() {
        System.out.println("After Each Test");
    }

        @Test
    public void prt(){

        driver.get("https://google.com");
        driver.close();
    }

    @Test
    public void prt2(){
        System.out.println("Hello World2");
    }
}
