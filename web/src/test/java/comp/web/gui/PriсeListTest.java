/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp.web.gui;

import java.util.List;
import static org.junit.Assert.assertFalse;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.Test;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.junit.Ignore;

/**
 * Test gui using Selenium
 * @author Ivan
 */
@Ignore
public class PriсeListTest {

    static WebDriver driver = new HtmlUnitDriver();

    /* need init : run embedded to mem db, insert data to memdb,
        after testPriceList().
    OR
        run tomcat from gradle task to test db,
        after testPriceList().
    OR
        ???????
        testPriceList()
    */

    @Test
    public void testPriceList() {
        driver.get("http://localhost:8080/web/");
        //set filter
        driver.findElement(By.name("cat")).sendKeys("Принт");

        //filtering
        driver.findElement(By.name("cat")).submit();

        new WebDriverWait(driver, 2);
        WebElement tabl = driver.findElement(By.id("price-list"));

        List<WebElement> rows = tabl.findElements(By.tagName("tr"));

        assertFalse(rows.size() > 1); // first row is header

        driver.quit();
    }
}
