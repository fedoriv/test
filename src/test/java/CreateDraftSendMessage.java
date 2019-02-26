import busnes_object.MessageBO;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import page.LoginPage;
import page.PasswordPage;

public class CreateDraftSendMessage {

    private WebDriver driver = TestGmail.threadLocal.get();
    private LoginPage loginPage = new LoginPage(driver);
    private PasswordPage pwPage = new PasswordPage(driver);
    private MessageBO messageBO = new MessageBO(driver);

    @Given("^Gmail start page, where we type ([^\"]*)$")
    public void typeEmail(String login) throws Throwable {
        loginPage.typeLogin(login);

    }
    @Then("^we clic next button$")
    public void clickNextAfterEmail() throws Throwable {
        loginPage.clickNext();
    }

    @When("^password field appears we type there (.*)$")
    public void typePassword(String pw) throws Throwable {
        pwPage.typePassword(pw);
    }

    @And("^after we click on next button$")
    public void clickNestAfterPassword() throws Throwable {
        pwPage.clickNext();
    }
    @When("^main page appears, create new message fill all fields and close window$")
    public void createNewMessage() throws Throwable {
        messageBO.writeAndDraft();
    }

    @When("^message is draft, open it and send$")
    public void openDraftedMessage() throws Throwable {
        messageBO.openDraftAndSend();
    }
}
