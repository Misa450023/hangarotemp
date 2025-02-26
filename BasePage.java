package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	
public WebDriver driver;
public WebDriverWait wait;
public WebDriverWait nwait;
public WebDriverWait wait2;


public Actions ac;

public BasePage(WebDriver driver) {
	this.driver=driver;
	wait=new WebDriverWait(driver,Duration.ofSeconds(5));
	wait2 = new WebDriverWait(driver,Duration.ofSeconds(5));
	nwait=new WebDriverWait(driver,Duration.ofMillis(900));
	ac=new Actions(driver);
}

public void waiting(By by) {
	wait.until(ExpectedConditions
			.visibilityOfElementLocated(by));
}

public void clickOn(By by) {
	wait.until(ExpectedConditions.elementToBeClickable(by));
	driver.findElement(by).click();
}

public String getTheText(By by){
	waiting(by);
	return driver.findElement(by).getText();
	
}

public void scrollToElement(By by) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    WebElement element = driver.findElement(by);
    js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
}

public void scrollToElement(WebElement element) {
    
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].scrollIntoView(true);", element);    
}

public void scrollAndClick(WebElement element) {
    try {
       
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
        Thread.sleep(250);  
        element.click();
        
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error: Unable to scroll and click.");
    }
}

public void zoomPage(double zoomFactor) {
    
    if (zoomFactor < 0.1 || zoomFactor > 3) {
        System.out.println("Zoom factor must be between 0.1 and 3.");
        return;
    }

    JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
    String zoomScript = "document.body.style.zoom = '" + zoomFactor + "';";
    jsExecutor.executeScript(zoomScript);
    
    System.out.println("Page zoomed to: " + zoomFactor);
}



}
