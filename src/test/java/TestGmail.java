import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

@CucumberOptions(features = {"src/main/resources/features/"})
public class TestGmail {

    /*
     * In this case, using of ThreadLocal class is not necessary and WebDriver can be created in method.
     * But one of Homework tasks was implement ThreadLocal to store WebDriver objects
     * */
    public static final ThreadLocal<WebDriver> threadLocal = new ThreadLocal<>();
    private static final Logger LOG = LogManager.getLogger(TestGmail.class.getName());
    private static final String IS_SENT = "//span[@class='bAq']";
    private TestNGCucumberRunner testRunner;


    @BeforeClass
    public void setDriverPath() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver_win32/chromedriver.exe");
        testRunner = new TestNGCucumberRunner(TestGmail.class);
        if (System.getProperty("to") == null) {
            //set default receiver email.
            System.setProperty("to", "evilzluj@mail.ru");
        }
    }

    @BeforeMethod
    public void setDriver() {
        WebDriver driver = new ChromeDriver();
        LOG.info("Set new WebDriver");
        threadLocal.set(driver);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.get("https://www.gmail.com/");
    }

    @Test(dataProvider = "features")
    public void testGmail(CucumberFeatureWrapper cFeature) {
        testRunner.runCucumber(cFeature.getCucumberFeature());
        Assert.assertTrue(new WebDriverWait(threadLocal.get(), 10).until(d -> d.findElement(By.xpath(IS_SENT)).isDisplayed()));
        LOG.info("TEST FINISHED");
    }

    @AfterMethod
    public void closeDriver() {
        threadLocal.get().close();

    }

    @DataProvider(name = "features", parallel = true)
    public Object[][] getFeatures() {
        return testRunner.provideFeatures();
    }

    @AfterClass
    public void tearDown() {
        testRunner.finish();
    }
}
