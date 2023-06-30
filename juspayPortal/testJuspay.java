package juspayPortal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class testJuspay {
	public WebDriver driver;
	@BeforeClass
	public void beforeClass() {
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.get("https://sandbox.portal.juspay.in/");
	}
	
	@BeforeMethod
	public void beforeMethod(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        System.out.println("Executing test case: " + testName);
    }


	@Test(enabled=true,priority=1,description="Login with both user and password")
	public void check_loginS1() throws InterruptedException {
		try {
		System.out.println("Checking login with correct combination of username and password");
		WebDriverWait wait=new WebDriverWait(driver,90);
		driver.findElement(By.name("username")).sendKeys("testdata001");
		driver.findElement(By.name("password")).sendKeys("testn02");
		By loginBtn=By.xpath("//button[contains(@data-button-for,'securelyLogin')]");
		driver.findElement(loginBtn).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("2FA_OTP")));
		if(driver.findElement(By.name("2FA_OTP")).isDisplayed()) {
			System.out.println("OTP FIeld displayed! Entering otp...");

			driver.findElement(By.name("2FA_OTP")).sendKeys("012453");	
			
		}
		driver.findElement(loginBtn).click();
		
		if(!driver.findElement(loginBtn).isEnabled()) {
			System.out.println("Error message displayed, incorrect username/ password/ otp");
			Assert.fail("Assert Error");
		}
		System.out.println("As expected, homepage displayed");
		}
		
		
		catch(Exception e) {
			System.out.println(e);
			Assert.fail();
		}
	}

	@Test(priority=2,description="Login with  username and no password")
	public void check_loginS2() throws InterruptedException {
		try {
			WebDriverWait wait=new WebDriverWait(driver,90);
			System.out.println("Checking login by entering just username");
			driver.navigate().refresh();
			driver.findElement(By.name("username")).sendKeys("002test");
			driver.findElement(By.name("password")).click();
			driver.findElement(By.xpath("//div[contains(@data-component,'loginDetails')]")).click();
			By loginBtn=By.xpath("//button[contains(@data-button-for,'securelyLogin')]");
			
			if(!driver.findElement(loginBtn).isEnabled()) {
				System.out.println("As expected, login button is disabled as password is empty");
				Assert.assertEquals(driver.findElement(By.xpath("//div[contains(text(),'Password cannot be empty')]")).isDisplayed(),true,"Password cannot be empty not displayed");
				
				
			}
			else {
				System.out.println("Scenario failed");
				Assert.fail("Homepage not displayed");
			}
			
			
		}
		catch(Exception e) {
		System.out.println(e);
		Assert.fail();
	}
	}
	@Test(priority=3,description="Login with password and no username")
	public void check_loginS3() throws InterruptedException {
		try {

			System.out.println("Checking login by entering just password");
			driver.navigate().refresh();
			driver.findElement(By.name("password")).sendKeys("test1n01");
			driver.findElement(By.name("username")).click();

			driver.findElement(By.xpath("//div[contains(@data-component,'loginDetails')]")).click();
			By loginBtn=By.xpath("//button[contains(@data-button-for,'securelyLogin')]");
			if(!driver.findElement(loginBtn).isEnabled()) {
				System.out.println("As expected, login button is disabled as username is empty");
				Assert.assertEquals(driver.findElement(By.xpath("//div[contains(text(),'Username cannot be empty')]")).isDisplayed(),true,"Username cannot be empty not displayed");
			}
			else {
				System.out.println("Scenario failed");
				Assert.fail();
			}
			
			
		}
		catch(Exception e) {
		System.out.println(e);
		Assert.fail();
	}
	}
	@Test(priority=4,description="Login with no password and no username")
	public void check_loginS4() throws InterruptedException {

		System.out.println("Checking login by empty username and password");
		driver.navigate().refresh();
		By loginBtn=By.xpath("//button[contains(@data-button-for,'securelyLogin')]");
		driver.findElement(loginBtn).click();
		if(driver.findElement(By.xpath("//div[contains(text(),'Username cannot be empty')]")).isDisplayed() &&
				!driver.findElement(By.xpath("//div[contains(text(),'Password cannot be empty')]")).isDisplayed()) {
			System.out.println("Error message not displayed");
			Assert.fail();
		}
		System.out.println("As expected, Error message  displayed");
		
	}
	@AfterMethod
    public void afterMethod(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        int testStatus = result.getStatus();
        String status = testStatus == ITestResult.SUCCESS ? "PASS" : "FAIL";
        System.out.println("Test case '" + testName + "' status: " + status);
    }
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
}
