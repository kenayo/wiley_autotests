package com.wiley.webdriver.tests;

import org.junit.*;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;


public class MainTest {
    static ChromeDriver driver;
    static Actions action;
    final int waitTime = 10;

    @BeforeClass
    public static void loadDriver() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        action = new Actions(driver);
    }

    @AfterClass
    public static void quitBrowser() {
        driver.close();
    }

    @Test
    public void runTest() {
        getMainPage();
        checkResourcesSubheaders();
        clickStudents();
        clickEducation();
        clickWileyLogo();
        clickSearchButton1();
        inputMath();
        ArrayList<String> productsList = clickSearchButton2();
        clickSearchButton3(productsList);
    }

    private void closeUndetectedCountryWindow() {
        /* Change to US site if undetected country window is shown */
        if (driver.findElement(By.xpath("//form[@class='country-form']")).isDisplayed()) {
            WebElement yesButton = driver.findElement(By.
                    xpath("//div[@class='modal-content']/form/div[@class='modal-footer']/" +
                            "button[@class='changeLocationConfirmBtn button large button-teal']"));
            action.moveToElement(yesButton).click().build().perform();
        }
    }

    private void checkPage() {
        /* wait for page to load by image*/

        new WebDriverWait(driver, waitTime).until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//img[@class='js-responsive-image']")));
    }

    private void getMainPage() {
        driver.get("http://www.wiley.com/WileyCDA/");
        checkPage();

        closeUndetectedCountryWindow();

        // find element RESOURCES
        Assert.assertTrue("Element RESOURCES not found.", driver.findElementById("navigationNode_00000RS2").isEnabled());

        // find element SUBJECT
        Assert.assertTrue("Element SUBJECT not found.", driver.findElementById("navigationNode_00000RS5").isEnabled());

        // find element ABOUT
        Assert.assertTrue("Element ABOUT not found.", driver.findElementById("navigationNode_00000RS3").isEnabled());
    }

    private void checkResourcesSubheaders() {
        final List<WebElement> resourcesSubheadersList = driver.
                findElements(By.xpath("//div[@id='navigationNode_00000RS2']/div/h3/a"));
        final List<String> expectedSubheadersList = new ArrayList<String>() {{
            add("Authors");
            add("Corporations");
            add("Institutions");
            add("Instructors");
            add("Journalists");
            add("Librarians");
            add("Professionals");
            add("Researchers");
            add("Resellers");
            add("Societies");
            add("Students");
        }};

        // check for correct number of items
        Assert.assertEquals("Number of items under Resources header is incorrect",
                expectedSubheadersList.size(), resourcesSubheadersList.size());

        // check for correct item titles
        String subheaderString;
        for (WebElement subheader : resourcesSubheadersList) {
            subheaderString = subheader.getAttribute("title");
            Assert.assertTrue("Item " + subheaderString + " not found",
                    expectedSubheadersList.contains(subheaderString));
        }
    }

    private void clickStudents() {
        final String expectedUrl = "https://www.wiley.com/en-us/students";
        final String expectedArticleHeader = "STUDENTS";
        final String expectedHref = "http://wileyplus.wiley.com/";

        WebElement resourcesMenu = driver.findElementByXPath("//div[@class='main-navigation-menu']/ul/li/a[@href='#']");
        WebElement studentsButton = driver.
                findElementByXPath("//div[@id='navigationNode_00000RS2']/div/h3/a[@title='Students']");

        action.moveToElement(resourcesMenu).moveToElement(studentsButton).click().build().perform();
        checkPage();

        // check for url
        Assert.assertEquals("Incorrect url. ", expectedUrl, driver.getCurrentUrl());

        // check for header
        String articleHeader = driver.findElementByXPath("//ul[@class='breadcrumbs']/li[@class='active']").getText();
        Assert.assertEquals("Incorrect article header. ", expectedArticleHeader, articleHeader);

        // check for link
        Assert.assertEquals("Link on WileyPLUS logo is incorrect. ", expectedHref,
                driver.findElementByLinkText("WileyPLUS").getAttribute("href"));
    }

    private void clickEducation() {
        final String expectedHeader = "Education";
        final List<String> expectedSubjectsList = new ArrayList<String>() {{
            add("Information & Library Science");
            add("Education & Public Policy");
            add("K-12 General");
            add("Higher Education General");
            add("Vocational Technology");
            add("Conflict Resolution & Mediation (School settings)");
            add("Curriculum Tools- General");
            add("Special Educational Needs");
            add("Theory of Education");
            add("Education Special Topics");
            add("Educational Research & Statistics");
            add("Literacy & Reading");
            add("Classroom Management");
        }};

        WebElement subjectsMenu = driver.
                findElementByXPath("//div[@class='main-navigation-menu']/ul/li/a[@href='/en-us/subjects']");
        WebElement EtoLButton = driver.findElementByXPath("//div[@class='dropdown-items-wrapper']/h3/a[@title='E-L']");
        WebElement EducationButton = driver.findElementByXPath("//li[@class='yCmsComponent']/a[@title='Education']");

        action.moveToElement(subjectsMenu).moveToElement(EtoLButton)
                .moveToElement(EducationButton).click().build().perform();

        checkPage();

        // check header
        String header = driver.
                findElementByXPath("//div[@class='wiley-slogan']/h1[@style='text-align: justify;']/span").getText();
        Assert.assertEquals("Incorrect article header. ", expectedHeader, header);

        // check number of items
        final List<WebElement> subjectsList = driver.findElements(By.xpath("//div[@class='side-panel']/ul/li/a"));
        Assert.assertEquals("Number of items in Subjects list is incorrect. ",
                expectedSubjectsList.size(), subjectsList.size());

        // check for correct item titles
        String subheaderString;
        for (WebElement subheader : subjectsList) {
            subheaderString = subheader.getText();
            Assert.assertTrue("Item " + subheaderString + " not found",
                    expectedSubjectsList.contains(subheaderString));
        }
    }

    private void clickWileyLogo() {
        final String expectedtitle = "Homepage | Wiley";
        WebElement wileyLogo = driver.
                findElementByXPath("//div[@class='main-navigation-menu']/div[@class='yCmsContentSlot logo']");

        action.moveToElement(wileyLogo).click().build().perform();

        checkPage();

        // check page title
        Assert.assertEquals("Page title is incorrect.", expectedtitle, driver.getTitle());
    }

    private void clickSearchButton1() {
        final String expectedtitle = "Homepage | Wiley";
        WebElement searchButton = driver.findElementByXPath("//span[@class='input-group-btn']/button");

        action.moveToElement(searchButton).click().build().perform();

        // check page title
        Assert.assertEquals("Page title is incorrect.", expectedtitle, driver.getTitle());
    }

    private void inputMath() {
        final String searchText = "math";
        final int expectedAmountOfWordsOnLeft = 4;
        final int expectedAmountOfWordsOnRight = 4;

        WebElement inputField = driver.findElementByXPath("//div[@class='input-group']/input");

        inputField.sendKeys(searchText);

        new WebDriverWait(driver, waitTime).until(ExpectedConditions.
                stalenessOf(driver.findElement(By.xpath("//div[@class='search-list']"))));

        // check for related content under search header
        Assert.assertTrue("Related content not found.", driver.findElement
                (By.xpath("//div[@class='search-related-content']")).
                isDisplayed());

        // check for words on the left side
        final List<WebElement> leftSideWordsList = driver.findElements(By.xpath("//div[@class='search-list']/div/a"));

        int numberOfWordStartWithMath = 0;
        for (WebElement subheader : leftSideWordsList) {
            if (subheader.getText().toLowerCase().startsWith(searchText)) {
                numberOfWordStartWithMath++;
            }
        }

        Assert.assertEquals("Number of words on the left side of search window is incorrect. ",
                expectedAmountOfWordsOnLeft, numberOfWordStartWithMath);

        // check for number of tiles on the right side of the window
        final List<WebElement> rightSideWordsList = driver.findElements(
                By.xpath("//div[@class='search-related-content']" +
                "/section/div[@class='related-content-products']/section/" +
                "div[@class='product-content ui-menu-item-wrapper']/h3/a"));

        int numberOfWordContainingMath = 0;
        for (WebElement subheader : rightSideWordsList) {
            if (subheader.getText().toLowerCase().contains(searchText)) {
                numberOfWordContainingMath++;
            }
        }

        Assert.assertEquals("Number of words on the right side of search window is incorrect. ",
                expectedAmountOfWordsOnRight, numberOfWordContainingMath);
    }

    private ArrayList<String> clickSearchButton2() {
        final String expectedText = "math";
        final int expectedAmountOfProducts = 10;
        WebElement searchButton = driver.findElementByXPath("//span[@class='input-group-btn']/button");

        action.moveToElement(searchButton).click().build().perform();

        checkPage();

        final List<WebElement> productsList = driver.findElements(By.xpath("//div[@class='products-list']/" +
                "section[@class='product-item ']/div[@class='product-content']"));

        // check tiles amount
        Assert.assertEquals("Number of found products in incorrect. ", expectedAmountOfProducts, productsList.size());

        for (WebElement product : productsList) {
            String title = product.findElement(By.xpath("h3[@class='product-title']/a")).getText();
            String author = product.findElement(By.xpath("div[@class='product-author']")).getText();

            // check for math word in product title or author string
            if (!title.toLowerCase().contains(expectedText) && !author.toLowerCase().contains(expectedText)) {
                Assert.fail("Not every tile contains word math.");
            }

            // check add to cart button
            Assert.assertTrue("Not every tile contains Add to cart button.", product.findElement(
                    By.xpath("div[@class='product-table-flexible']/div/div[@class='product-table-body']" +
                            "/div/div/div[@class='product-button']/div/form")).isEnabled());
        }

        // collecting links as the most unique info of each product
        ArrayList<String> productLinksListString = new ArrayList<String>();
        final List<WebElement> productLinksListWeb = driver.findElements(By.xpath("//div[@class='products-list']" +
                "/section/div[@class='product-content']/h3/a"));
        for (WebElement product : productLinksListWeb) {
            productLinksListString.add(product.getAttribute("href"));
        }

        return productLinksListString;
    }

    private void clickSearchButton3(ArrayList<String> productsList) {
        final String searchText = "Math";
        WebElement searchButton = driver.findElementByXPath("//span[@class='input-group-btn']/button");
        WebElement inputField = driver.findElementByXPath("//div[@class='input-group']/input");

        inputField.sendKeys(searchText);
        action.moveToElement(searchButton).click().build().perform();

        checkPage();

        // collecting links as the most unique info of each product
        ArrayList<String> newProductsListString = new ArrayList<String>();
        final List<WebElement> newProductsListWeb = driver.findElements(By.xpath("//div[@class='products-list']" +
                "/section/div[@class='product-content']/h3/a"));
        for (WebElement product : newProductsListWeb) {
            newProductsListString.add(product.getAttribute("href"));
        }

        // check if product list is the same as on last search
        Assert.assertEquals("Product lists are not equal. ", newProductsListString, productsList);
    }
}
