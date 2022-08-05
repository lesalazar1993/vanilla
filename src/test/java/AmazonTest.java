import amazon.config.EnvFactory;
import amazon.factories.DriverFactory;
import amazon.pageObjects.DepartmentPage;
import amazon.pageObjects.HomePage;
import amazon.pageObjects.ProductPage;
import com.typesafe.config.Config;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AmazonTest {
    private static Config config = EnvFactory.getInstance().getConfig();
    private static final String HOME_PAGE_URL = config.getString("HOME_PAGE_URL");
    private WebDriver driver = DriverFactory.getDriver();


    @DisplayName("Should be able to find 'About this Item' title exists")
    @Test
    void assertThatAboutThisItemExists() {
        driver.get(HOME_PAGE_URL);

        HomePage homePage = new HomePage(driver);
        Set<By> menuItems = new HashSet<>();
        menuItems.add(By.cssSelector("[data-ref-tag=\"nav_em_1_1_1_14\"]"));
        menuItems.add(By.xpath("//*[text()='Televisions']"));
        homePage.selectMenuOption(menuItems);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        DepartmentPage departmentPage = new DepartmentPage(driver);
        departmentPage.scrollToElement(By.xpath("//span[text()='Brands']/ancestor::div/following-sibling::ul"));
        departmentPage.clickOn(By.xpath("//span[text()='Samsung']/ancestor::a[@class='a-link-normal']"));
        departmentPage.sortBy(By.id("s-result-sort-select"), "Price: High to Low");
        departmentPage.clickOn(By.cssSelector(".s-result-list [data-index=\"2\"]"));

        Set<String> windows = driver.getWindowHandles();
        String last = (String) (windows.toArray())[windows.size() - 1];
        driver.switchTo().window(last);

        ProductPage productPage = new ProductPage(driver);
        assertEquals("About this item", productPage.getElementText(By.cssSelector("#feature-bullets > h1.a-text-bold")));
    }

    @AfterAll
    void tearDown() {
        driver.quit();
    }
}
