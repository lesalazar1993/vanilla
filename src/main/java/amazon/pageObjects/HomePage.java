package amazon.pageObjects;

import org.apache.logging.log4j.core.util.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.Set;

public class HomePage {
    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    private void openBurgerMenu() {
        WebElement burgerMenu = driver.findElement(By.id("nav-hamburger-menu"));
        Assert.isNonEmpty(burgerMenu);
        burgerMenu.click();
    }

    public void selectMenuOption(Set<By> menuSelectors) {
        openBurgerMenu();
        Actions actions = new Actions(this.driver);
        for(By selector : menuSelectors) {
            WebElement element = this.driver.findElement(selector);
            Assert.isNonEmpty(element);
            actions.moveToElement(element);
            element.click();
        }
    }
}
