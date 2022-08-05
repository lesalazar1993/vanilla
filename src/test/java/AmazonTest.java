import amazon.config.EnvFactory;
import amazon.factories.DriverFactory;
import com.typesafe.config.Config;
import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AmazonTest {
    private static Config config = EnvFactory.getInstance().getConfig();
    private static final String HOME_PAGE_URL = config.getString("HOME_PAGE_URL");
    private WebDriver driver = DriverFactory.getDriver();

    @DisplayName("Should be able to find 'About this Item' title exists")
    @Test
    void assertThatAboutThisItemExists() {
        driver.get(HOME_PAGE_URL);
       Actions actions = new Actions(driver);

        WebElement burgerMenu = driver.findElement(By.id("nav-hamburger-menu"));
        Assert.isNonEmpty(burgerMenu);
        burgerMenu.click();

        WebElement appliancesItem = driver.findElement(By.cssSelector("[data-ref-tag=\"nav_em_1_1_1_14\"]"));
        actions.moveToElement(appliancesItem);
        appliancesItem.click();

        WebElement televisionsItem = driver.findElement(By.cssSelector("[data-menu-id=\"9\"]"))
                .findElement(By.xpath("//*[text()='Televisions']"));
        televisionsItem.click();

      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        WebElement sideNav = driver.findElement(By.className("apb-browse-left-nav"));
        // "//span[text()='Brands']/ancestor::div[@class='a-section a-spacing-small']/following-sibling::ul[@class='a-unordered-list a-nostyle a-vertical a-spacing-medium']
        WebElement brandsList = sideNav.findElement(By.xpath("//span[text()='Brands']/ancestor::div/following-sibling::ul"));
        actions.moveToElement(brandsList);
        WebElement samsungBrand = brandsList.findElement(By.xpath("//span[text()='Samsung']/ancestor::a[@class='a-link-normal']"));
        samsungBrand.click();

        Select sortSelect = new Select(driver.findElement(By.id("s-result-sort-select")));
        sortSelect.selectByVisibleText("Price: High to Low");

        WebElement secondHighest = driver.findElement(By.cssSelector(".s-result-list [data-index=\"2\"]"));
        secondHighest.click();

        Set<String> windows = driver.getWindowHandles();
        String last = (String) (windows.toArray())[windows.size() - 1];
        driver.switchTo().window(last);

        WebElement aboutThis = driver.findElement(By.cssSelector("#feature-bullets > h1.a-text-bold"));
        System.out.println(aboutThis.getText());
        assertEquals("About this item", aboutThis.getText());

    }
}
