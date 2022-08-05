package amazon.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Set;

public class ProductPage {
    private WebDriver driver;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getElementText(By selector) {
        WebElement element = this.driver.findElement(selector);
        String text = element.getText();
        System.out.println(text);
        return text;
    }
}
