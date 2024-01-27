package com.example.demo.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class SeleniumTests {

    @Test
    public void checkHomePageLookAndFeel() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");
        EdgeDriver driver = new EdgeDriver(options);

        driver.get("http://localhost:4200/");
        driver.manage().window().maximize();
        driver.findElement(By.id("sidebar__app-accordion")).click();
        driver.findElement(By.id("court-link")).isDisplayed();
        driver.findElement(By.id("sidebar__info-accordion")).click();
        driver.findElement(By.id("home-link")).isDisplayed();
        driver.findElement(By.id("about-link")).isDisplayed();
        driver.findElement(By.id("home__title")).isDisplayed();
        driver.findElement(By.id("home__image")).isDisplayed();
        driver.findElement(By.id("home__description")).isDisplayed();
        driver.close();
    }

    @Test
    public void checkCourtPageLookAndFeel() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");
        EdgeDriver driver = new EdgeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("http://localhost:4200/");
        driver.manage().window().maximize();
        driver.findElement(By.id("sidebar__app-accordion")).click();
        driver.findElement(By.id("court-link")).click();

        Assertions.assertEquals("Sud", driver.findElement(By.id("court__title")).getText());
        driver.findElement(By.id("court__search")).isDisplayed();
        var table = driver.findElement(By.id("courts__table"));
        var columnHeaders = table.findElements(By.xpath("//mat-table[@id='courts__table']//div[contains(@class,'mat-sort-header-content')]"));
        Assertions.assertEquals("ID", columnHeaders.get(0).getText());
        Assertions.assertEquals("Naziv", columnHeaders.get(1).getText());
        Assertions.assertEquals("Adresa", columnHeaders.get(2).getText());

        driver.close();
    }

    @Test
    public void checkAboutPageLookAndFeel() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");
        EdgeDriver driver = new EdgeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("http://localhost:4200/");
        driver.manage().window().maximize();
        driver.findElement(By.id("sidebar__info-accordion")).click();
        driver.findElement(By.id("about-link")).click();

        driver.findElement(By.xpath("//iframe[contains(@src,'google.com/maps/embed')]"));

        driver.close();
    }

    @Test
    public void checkDisplayedDataInTable() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");
        EdgeDriver driver = new EdgeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("http://localhost:4200/sud");
        driver.manage().window().maximize();
        var elementsInTable = driver.findElements(By.xpath("//mat-row"));
        Assertions.assertEquals(5, elementsInTable.size());

        driver.close();
    }

    @Test
    public void checkSorting() throws InterruptedException {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");
        EdgeDriver driver = new EdgeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("http://localhost:4200/sud");
        driver.manage().window().maximize();
        driver.findElement(By.xpath("//div[@class='mat-mdc-paginator-container']//mat-form-field")).click();
        driver.findElement(By.xpath("//mat-option[2]/span")).click();

        driver.findElement(By.xpath("//mat-header-row/mat-header-cell[1]")).click(); //sort by id

        var idColumns = driver.findElements(By.xpath("//mat-row//mat-cell[1]"));
        int id = 0;
        for (var idColumn : idColumns) {
            var idAsNumber = Integer.parseInt(idColumn.getText());
            System.out.println(idAsNumber);
            Assertions.assertTrue(idAsNumber > id);
            id = idAsNumber;
        }

        driver.close();
    }

    @Test
    public void checkPagination() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");
        EdgeDriver driver = new EdgeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("http://localhost:4200/sud");
        driver.manage().window().maximize();
        driver.findElement(By.xpath("//div[@class='mat-mdc-paginator-container']//mat-form-field")).click();
        driver.findElement(By.xpath("//mat-option[2]/span")).click();

        var elementsInTable = driver.findElements(By.xpath("//mat-row"));
        Assertions.assertEquals(7, elementsInTable.size());

        driver.findElement(By.xpath("//div[@class='mat-mdc-paginator-container']//mat-form-field")).click();
        driver.findElement(By.xpath("//mat-option[1]/span")).click();

        elementsInTable = driver.findElements(By.xpath("//mat-row"));
        Assertions.assertEquals(5, elementsInTable.size());

        var nextPage = driver.findElement(By.xpath("(//span[@class='mat-mdc-button-touch-target'])[2]"));
        nextPage.click();
        elementsInTable = driver.findElements(By.xpath("//mat-row"));
        Assertions.assertEquals(2, elementsInTable.size());

        var previousPage = driver.findElement(By.xpath("(//span[@class='mat-mdc-button-touch-target'])[1]"));
        previousPage.click();
        elementsInTable = driver.findElements(By.xpath("//mat-row"));
        Assertions.assertEquals(5, elementsInTable.size());

        driver.close();
    }

    @Test
    public void checkFiltering() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");
        EdgeDriver driver = new EdgeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("http://localhost:4200/sud");
        driver.manage().window().maximize();

        var search = driver.findElement(By.id("court__search"));
        search.sendKeys("visi");

        var elementsInTable = driver.findElements(By.xpath("//mat-row"));
        Assertions.assertEquals(4, elementsInTable.size());

        driver.close();
    }

    @Test
    public void checkAddNewCourt() throws InterruptedException {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");
        EdgeDriver driver = new EdgeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("http://localhost:4200/sud");
        driver.manage().window().maximize();

        driver.findElement(By.id("add_court_icon")).click();
        Assertions.assertTrue(driver.findElement(By.xpath("//app-sud-dialog")).isDisplayed());

        Assertions.assertFalse(driver.findElement(By.id("button_add_court")).isEnabled());

        driver.findElement(By.id("new_court_name")).sendKeys("Novi sud");
        driver.findElement(By.id("new_court_address")).sendKeys("Nova adresa");
        driver.findElement(By.id("button_add_court")).click();

        driver.findElement(By.xpath("//div[@class='mat-mdc-paginator-container']//mat-form-field")).click();
        driver.findElement(By.xpath("//mat-option[2]/span")).click();

        var elementsInTable = driver.findElements(By.xpath("//mat-row"));
        Assertions.assertEquals(8, elementsInTable.size());

        driver.close();
    }

    @Test
    public void checkDeleteCourt() throws InterruptedException {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");
        EdgeDriver driver = new EdgeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("http://localhost:4200/sud");
        driver.manage().window().maximize();

        driver.findElement(By.xpath("//div[@class='mat-mdc-paginator-container']//mat-form-field")).click();
        driver.findElement(By.xpath("//mat-option[2]/span")).click();

        var elementsInTable = driver.findElements(By.xpath("//mat-row"));
        var deleteIcon = elementsInTable.get(elementsInTable.size() - 1).findElement(By.xpath("//mat-icon[@aria-label='Delete']"));
        deleteIcon.click();

        Assertions.assertTrue(driver.findElement(By.xpath("//app-sud-dialog")).isDisplayed());
        driver.findElement(By.id("button_delete_court")).click();

        Thread.sleep(1000);

        elementsInTable = driver.findElements(By.xpath("//mat-row"));
        Assertions.assertEquals(7, elementsInTable.size());

        driver.close();
    }

    @Test
    public void checkModifyCourt() throws InterruptedException {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");
        EdgeDriver driver = new EdgeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("http://localhost:4200/sud");
        driver.manage().window().maximize();

        driver.findElement(By.xpath("//div[@class='mat-mdc-paginator-container']//mat-form-field")).click();
        driver.findElement(By.xpath("//mat-option[2]/span")).click();

        driver.findElement(By.xpath("//mat-header-row/mat-header-cell[1]")).click(); //sort by id

        Thread.sleep(500);

        var allNames = driver.findElements(By.xpath("//mat-row/mat-cell[2]"));
        var allAddresses = driver.findElements(By.xpath("//mat-row/mat-cell[3]"));
        var lastAddr = allAddresses.get(allAddresses.size() - 1).getText();
        var lastName = allNames.get(allNames.size() - 1).getText();
        var allModifyIcons = driver.findElements(By.xpath("//mat-icon[@aria-label='Edit']"));
        var modifyIcon = allModifyIcons.get(allModifyIcons.size() - 1);
        modifyIcon.click();

        Assertions.assertTrue(driver.findElement(By.xpath("//app-sud-dialog")).isDisplayed());

        var nameInDialog = driver.findElement(By.id("new_court_name")).getAttribute("value");
        var addressInDialog = driver.findElement(By.id("new_court_address")).getAttribute("value");

        Assertions.assertEquals(lastName, nameInDialog);
        Assertions.assertEquals(lastAddr, addressInDialog);

        driver.findElement(By.id("new_court_name")).clear();
        driver.findElement(By.id("new_court_name")).sendKeys("Izmenjeni sud");
        driver.findElement(By.id("new_court_address")).clear();
        driver.findElement(By.id("new_court_address")).sendKeys("Izmenjena adresa");

        driver.findElement(By.id("button_modify_court")).click();

        Thread.sleep(1000);

        var courtsNamesCells = driver.findElements(By.xpath("//mat-row//mat-cell[2]"));
        var lastCourtName = courtsNamesCells.get(courtsNamesCells.size() - 1).getText();
        var courtsAddressesCells = driver.findElements(By.xpath("//mat-row//mat-cell[3]"));
        var lastCourtAddress = courtsAddressesCells.get(courtsAddressesCells.size() - 1).getText();

        Assertions.assertEquals("Izmenjeni sud", lastCourtName);
        Assertions.assertEquals("Izmenjena adresa", lastCourtAddress);

        Thread.sleep(1000);

        driver.close();
    }
}
