package homework3;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

	public class CRMTestNG {
	
	WebDriver driver;
	String browser;
	String url;
	
//  Storing element using By class
	//	Locate web element
	By username_Field = By.xpath("//*[@id=\"username\"]");
	By password_Field = By.xpath("//*[@id=\"password\"]");
	By signin_Field = By.xpath("/html/body/div/div/div/form/div[3]/button");
	By DASHBOARD_HEADER_FIELD = By.xpath("//*[@id=\"page-wrapper\"]/div[2]/div/h2");
	
	By customer_Menu_FIELD = By.xpath("//span[contains(text(), 'Customers')]");
	By addCustomer_Menu_FIELD = By.xpath("//a[contains(text(), 'Add Customer')]");
	
	By ADD_CUSTOMER_HEADER_FIELD = By.xpath("//*[@id=\"rform\"]/div[1]/div[1]/div[1]/label");
	By fullName_FIELD= By.xpath("//input[@id='account']");
	By company_List_FIELD = By.xpath("//*[@id='cid']");
	By email_FIELD = By.xpath("//*[@id=\"email\"]");
	By phoneNumber_FIELD = By.xpath("//*[@id=\"phone\"]");
	By address_FIELD= By.xpath("//*[@id=\"address\"]");
	By city_FIELD= By.xpath("//*[@id=\"city\"]");
	By state_FIELD = By.xpath("//*[@id=\"state\"]");
	By zipcode_FIELD = By.xpath("//*[@id=\"zip\"]");
	By country_Menu_FIELD = By.xpath("//span[@id='select2-country-container']");
	//By country_FIELD = By.xpath("//span[(text()= 'United States')]");
	By saveButton_FIELD = By.xpath("//*[@id=\"submit\"]");
	
	// Login Data
			String userName = "demo@techfios.com";
			String password = "abc123";
				
				// Test or Mock Data
			String fullName = "Selenium Automation";
			String company = "Techfios";
			String email = "Selenium.j@techfios.com";
			String phoneNumber = "214-975-8";
			String address= "3509 techfios ave";
			String city = "Tester";
			String state = "TX";
			String zipcode = "73754";
			String country = "United States";
				
	@BeforeTest
	public void readConfig() {
					// Scanner //BufferedReader //InputStream //FileReader

					Properties prop = new Properties();
					try {
						InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
						prop.load(input);
						browser = prop.getProperty("browser");
						System.out.println("Browser used: " + browser);
						url = prop.getProperty("url");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

	@BeforeMethod
	public void init () {
	
		if (browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
			driver = new ChromeDriver();
		} 
		else if (browser.equalsIgnoreCase("Firefox")) {
			System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

		driver.manage().deleteAllCookies();
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}
	
	@Test(priority = 2)
	public void loginTest() {
		driver.findElement(username_Field ).sendKeys(userName);
		driver.findElement(password_Field ).sendKeys(password);
		driver.findElement(signin_Field).click();
		String dashBoardheader = driver.findElement(DASHBOARD_HEADER_FIELD).getText();
		Assert.assertEquals(dashBoardheader, "Dashboard", "Dashboard Page not available!");
		
}
	
	@Test(priority = 1)
	public void addContact()  {
		

		//operation 
		driver.findElement(username_Field ).sendKeys(userName);
		driver.findElement(password_Field ).sendKeys(password);
		driver.findElement(signin_Field).click();
		
		driver.findElement(customer_Menu_FIELD ).click();
		driver.findElement(addCustomer_Menu_FIELD ).click();
		//Explicit Wait 
		waitForElement (driver, 15, ADD_CUSTOMER_HEADER_FIELD);
		//Validation
		String addCustomerHeader = driver.findElement(ADD_CUSTOMER_HEADER_FIELD).getText();
		Assert.assertEquals(addCustomerHeader, "Full Name*", "Add Contact page not found!");
		
		Random rnd = new Random();
		int generatedNum = rnd.nextInt(999);
		driver.findElement(fullName_FIELD).sendKeys("" + generatedNum);
		
		driver.findElement(fullName_FIELD ).sendKeys(fullName);
		driver.findElement(email_FIELD).sendKeys(generatedNum + email);
		//Dropdown wait declaration
		waitForElement (driver, 15, company_List_FIELD);
		doSelectByVisibleText(company_List_FIELD , company);
		
		driver.findElement(phoneNumber_FIELD).sendKeys(phoneNumber + generatedNum);
		driver.findElement(address_FIELD).sendKeys(address);
		driver.findElement(city_FIELD).sendKeys(city);
		driver.findElement(state_FIELD).sendKeys(state);
		driver.findElement(zipcode_FIELD).sendKeys(zipcode);
		
		//Methodfor Dropdown for Country List
	    driver.findElement(country_Menu_FIELD ).click();
	   
		WebElement dropDown = driver.findElement(By.xpath("//span[(text()= 'United States')]"));
		Actions action = new Actions(driver);
		action.keyDown(Keys.CONTROL).click(dropDown).build().perform();
		driver.findElement(saveButton_FIELD).click();	
}

	//Method for explicit wait
			private void waitForElement(WebDriver driver, int time, By Locator) {
				WebDriverWait wait = new WebDriverWait(driver,15);
				WebElement e =  wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
					
		}
			//Methods for doprdown menu
	private  WebElement getElement(By locator) {
				return driver.findElement(locator);
			}
	//Methods for doprdown menu
	private void doSelectByVisibleText(By locator, String value) {
					Select sel = new Select(driver.findElement(company_List_FIELD));
					sel.selectByVisibleText(value);
					System.out.println(sel.getFirstSelectedOption().getText());
				}
		

			//driver.findElement(By.xpath("//span[(text()= 'United States')]"));
			
			//sel.selectByVisibleText(country);
			//System.out.println(sel.getFirstSelectedOption().getText());

		//@AfterMethod
	public void tearDown() {
			driver.close();
			driver.quit();
}
	
}