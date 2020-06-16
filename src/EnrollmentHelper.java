package EnrollmentHelper;

import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

public class EnrollmentHelper {
	String account, password, session, course, catalogue;

	public EnrollmentHelper(String account, String password, String course, String catalogue) {
		this.account = account;
		this.password = password;
		this.course = course;
		this.catalogue = catalogue;
	}

	public void get() {
		// ChromeOptions option = new ChromeOptions();
		// option.addArguments("--headless");

		// Open YorkU login site
		WebDriver driver = new ChromeDriver();
		driver.get("https://wrem.sis.yorku.ca/Apps/WebObjects/REM.woa/wa/DirectAction/rem");

		// YorkU login site
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		try {
			driver.findElement(By.id("mli")).sendKeys(this.account);
			driver.findElement(By.id("password")).sendKeys(this.password);
			driver.findElement(By.name("dologin")).click();
		} catch (NoSuchElementException e) {
			driver.quit();
			JOptionPane.showMessageDialog(null, "Slow internet connection, program terminated");
			throw new NoSuchElementException("");
		}

		// Enrollment Module select session site
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		try {
			Select selectSession = new Select(driver.findElement(By.name("5.5.1.27.1.11.0")));
			selectSession.selectByValue("3");
			driver.findElement(By.name("5.5.1.27.1.13")).click();
		} catch (NoSuchElementException e) {
			driver.quit();
			JOptionPane.showMessageDialog(null, "Login failed");
			throw new NoSuchElementException("");
		}

		// Enrollment Module main site
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		try {
			driver.findElement(By.name("5.1.27.1.23")).click();
		} catch (NoSuchElementException e) {
			driver.quit();
			JOptionPane.showMessageDialog(null,
					"Login successful, but something unexpected happened, might because of slow internet connection, program terminated");
			throw new NoSuchElementException("");
		}

		// Enrollment Module add course site
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		try {
			driver.findElement(By.name("5.1.27.7.7")).sendKeys(this.catalogue);
			driver.findElement(By.name("5.1.27.7.9")).click();
		} catch (NoSuchElementException e) {
			driver.quit();
			JOptionPane.showMessageDialog(null,
					"Login successful, but something unexpected happened, might because of slow internet connection, program terminated");
			throw new NoSuchElementException("");
		}

		// Enrollment Module add course confirm site
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		try {
			if (driver.findElement(By.name("5.1.27.11.9")).isDisplayed()) {
				driver.findElement(By.name("5.1.27.11.9")).click();
			} else {
				driver.findElement(By.name("5.1.27.15.9")).click();
			}
		} catch (NoSuchElementException e) {
			//driver.quit();
			JOptionPane.showMessageDialog(null,
					"Login successful, but something unexpected happened, might because of wrong catalogue number or slow internet connection, program terminated");
			throw new NoSuchElementException("");
		}

		// Enrollment Module result of adding site
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		try {
			String result = driver.findElement(By.xpath(
					"//body/form/div[1]/table/tbody/tr[4]/td[2]/table//tbody/tr/td/table[2]/tbody/tr[1]/td[2]/span/font/b"))
					.getText();
			driver.quit();
			if (result.equals("The course has been successfully added.")) {
				JOptionPane.showMessageDialog(null, "Congratulation!, " + this.course + "has been added");
				throw new IllegalArgumentException();
			} else {
				System.out.println("[" + this.course + "] " + result);
			}
		} catch (NoSuchElementException e) {
			driver.quit();
			JOptionPane.showMessageDialog(null,
					"Login successful, but something unexpected happened, might because of slow internet connection, program terminated");
			throw new NoSuchElementException("");
		}
	}

	Runnable run = new Runnable() {
		public void run() {
			get();
		}
	};

	public static void main(String args[]) {

	}
}
