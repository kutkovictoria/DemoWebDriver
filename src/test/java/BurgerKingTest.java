import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;

public class BurgerKingTest {
    WebDriver chromeDriver;
    WebElement inputEmailAddress;
    WebElement signUpSignInButton;

    @BeforeEach
    public void beforeTest() {
        chromeDriver = new ChromeDriver();
        chromeDriver.get("https://www.bk.com/");
        chromeDriver.manage().window().maximize();
        chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebElement closeCookiesBanner = chromeDriver.findElement(By.xpath("//*[@id=\"onetrust-close-btn-container\"]"));
        closeCookiesBanner.click();
    }

    @Test
    public void testOpenHomePage() {
        WebElement copyRight = chromeDriver.findElement(By.xpath("//*[@id=\"scroll-container\"]/div/div/div/div/div[4]/div/div[1]/div"));
        String actual = copyRight.getText();
        String expected = "TM & Copyright 2023 Burger King Corporation. All Rights Reserved.";
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void testOpenSignUpSingInPage() {
        signUpSignInButton = chromeDriver.findElement(By.xpath("//div[@data-testid='mobile-nav-signup-link']"));
        signUpSignInButton.click();
    }

    @Test
    public void testEnterIncorrectEmail(){
        signUpSignInButton = chromeDriver.findElement(By.xpath("//div[@data-testid='mobile-nav-signup-link']"));
        signUpSignInButton.click();
        inputEmailAddress = chromeDriver.findElement(By.xpath("//input[@data-testid='signin-email-input']"));
        inputEmailAddress.sendKeys("test");
        signUpSignInButton = chromeDriver.findElement(By.xpath("//div[@data-testid='signin-button']"));
        signUpSignInButton.click();
        WebElement errorMessage = chromeDriver.findElement(By.xpath("//div[@class='css-1rynq56 r-17aj29q r-anxyqk r-1gkfh8e r-oxtfae r-dhbnww']"));
        String actualErrorMessage = errorMessage.getText();
        String expectedErrorMessage = "That doesn't look like a valid email.";
        Assertions.assertEquals(expectedErrorMessage, actualErrorMessage);
    }

   @Test
    public void testEmptyEmailField(){
        signUpSignInButton = chromeDriver.findElement(By.xpath("//div[@data-testid='mobile-nav-signup-link']"));
        signUpSignInButton.click();
        signUpSignInButton = chromeDriver.findElement(By.xpath("//div[@data-testid='signin-button']"));
        signUpSignInButton.click();
        WebElement errorMessage = chromeDriver.findElement(By.xpath("//div[@class='css-1rynq56 r-17aj29q r-anxyqk r-1gkfh8e r-oxtfae r-dhbnww']"));
        String actualErrorMessage = errorMessage.getText();
        String expectedErrorMessage = "Email is a required field.";
        Assertions.assertEquals(expectedErrorMessage, actualErrorMessage);
    }

   @Test
    public void testEnterCorrectEmail(){
        String email = "test3@test.com";
        signUpSignInButton = chromeDriver.findElement(By.xpath("//div[@data-testid='mobile-nav-signup-link']"));
        signUpSignInButton.click();
        inputEmailAddress = chromeDriver.findElement(By.xpath("//input[@data-testid='signin-email-input']"));
        inputEmailAddress.sendKeys(email);
        signUpSignInButton = chromeDriver.findElement(By.xpath("//div[@data-testid='signin-button']"));
        signUpSignInButton.click();
        WebElement errorMessage = chromeDriver.findElement(By.xpath("//div[@class='css-1rynq56 r-17l9mgj r-anxyqk r-1b43r93 r-oxtfae r-135wba7']"));
        String actualErrorMessage = errorMessage.getText();
        String expectedErrorMessage = "We sent an email with login instructions to " + email;
        Assertions.assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @AfterEach
    public void afterTest() {
        chromeDriver.quit();
    }
}
