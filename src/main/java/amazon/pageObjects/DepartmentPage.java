package amazon.pageObjects;

import org.apache.logging.log4j.core.util.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class DepartmentPage {
    private WebDriver driver;
    private Actions actions;

    public DepartmentPage(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(this.driver);
    }

    public void scrollToElement(By selector) {
        WebElement element = this.driver.findElement(selector);
        actions.moveToElement(element);
    }

    public void clickOn(By selector) {
        WebElement element = this.driver.findElement(selector);
        Assert.isNonEmpty(element);
        element.click();
    }

    public void sortBy(By selectItemSelector, String selectedOption) {
        Select sortSelect = new Select(driver.findElement(selectItemSelector));
        sortSelect.selectByVisibleText(selectedOption);
    }

}
