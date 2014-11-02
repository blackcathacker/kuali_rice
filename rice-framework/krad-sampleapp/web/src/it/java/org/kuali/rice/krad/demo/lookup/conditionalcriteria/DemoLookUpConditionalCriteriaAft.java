/**
 * Copyright 2005-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.krad.demo.lookup.conditionalcriteria;

import org.junit.Ignore;
import org.kuali.rice.krad.demo.ViewDemoAftBase;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public class DemoLookUpConditionalCriteriaAft extends ViewDemoAftBase {

    /**
     * /kr-krad/lookup?methodToCall=start&viewId=LookupSampleViewConditionalCriteria&hideReturnLink=true
     */
    public static final String BOOKMARK_URL = "/kr-krad/lookup?methodToCall=start&viewId=LookupSampleViewConditionalCriteria&hideReturnLink=true";
    
    /**
     *  lookupCriteria[number]
     */
    private static final String LOOKUP_CRITERIA_NUMBER_NAME="lookupCriteria[number]";
    
    /**
     *  lookupCriteria[rangeLowerBoundKeyPrefix_createDate]
     */
    private static final String LOOKUP_CRITERIA_DATE_LOWER_BOUND_NAME="lookupCriteria[rangeLowerBoundKeyPrefix_createDate]";

    /**
     *  lookupCriteria[createDate]
     */
    private static final String LOOKUP_CRITERIA_DATE_UPPER_BOUND_NAME="lookupCriteria[createDate]";
    
    @Override
    public String getBookmarkUrl() {
        return BOOKMARK_URL;
    }

    @Override
    protected void navigate() throws Exception {
        waitAndClickDemoLink();
        waitAndClickByLinkText("Lookup Conditional Criteria");
    }

    protected void testLookUpConditionalCriteria() throws InterruptedException {
        //Case 1 - Date field required by number a1
        waitAndTypeByName(LOOKUP_CRITERIA_NUMBER_NAME, "a1");
        fireEvent(LOOKUP_CRITERIA_NUMBER_NAME, "blur");
        waitAndClickSearch3();
        waitForElementPresentByClassName("uif-requiredMessage");
        assertTrue(isElementPresentByName(LOOKUP_CRITERIA_DATE_LOWER_BOUND_NAME));
        assertTrue(isElementPresentByName(LOOKUP_CRITERIA_DATE_UPPER_BOUND_NAME));

        clearTextByName(LOOKUP_CRITERIA_NUMBER_NAME);

        //Case 2 - Date field read only by number a2
        waitAndTypeByName(LOOKUP_CRITERIA_NUMBER_NAME, "a2");
        fireEvent(LOOKUP_CRITERIA_NUMBER_NAME, "focus");
        fireEvent(LOOKUP_CRITERIA_NUMBER_NAME, "blur");
        waitAndClickSearch3();
        waitForElementNotPresent(By.name(LOOKUP_CRITERIA_DATE_LOWER_BOUND_NAME));
        assertFalse(isElementPresentByName(LOOKUP_CRITERIA_DATE_UPPER_BOUND_NAME));

        clearTextByName(LOOKUP_CRITERIA_NUMBER_NAME);

        //Case 3 - Date field hide by number a3
        waitAndTypeByName(LOOKUP_CRITERIA_NUMBER_NAME, "a3");
        fireEvent(LOOKUP_CRITERIA_NUMBER_NAME, "focus");
        fireEvent(LOOKUP_CRITERIA_NUMBER_NAME, "blur");
        waitAndClickSearch3();
        waitForElementPresentByName(LOOKUP_CRITERIA_DATE_LOWER_BOUND_NAME);
        assertTrue(isNotVisible(By.name(LOOKUP_CRITERIA_DATE_LOWER_BOUND_NAME)));
        waitForElementPresentByName(LOOKUP_CRITERIA_DATE_UPPER_BOUND_NAME);
        assertTrue(isNotVisible(By.name(LOOKUP_CRITERIA_DATE_UPPER_BOUND_NAME)));
    }

    protected void testAutoTruncateColumns() throws InterruptedException {
        waitForElementPresentByName(LOOKUP_CRITERIA_NUMBER_NAME);
        jiraAwareClearAndTypeByName(LOOKUP_CRITERIA_NUMBER_NAME, "a3");
        waitAndClickSearch3();

        // verify auto truncate is enabled
        jGrowl("Verifying auto truncate is enabled");
        assertTrue(getElementByAttributeValue("id", "resultsName_line0").getAttribute("class").contains("uif-truncate"));

        // verify text not truncated
        jGrowl("Verifying text is not truncated");
        assertEquals("Travel Account 3", getElementByAttributeValue("id", "resultsName_line0_control").getText());

        // verify tooltip not present
        jGrowl("Fire MouseOver event to show tooltip, and validate tooltip is not present");
        fireMouseOverEventById("resultsName_line0_control");
        assertTrue(waitForElementPresent("[class='popover top in']") == null);

        jGrowl("Resizing the window to 560 by 600");
        driver.manage().window().setSize(new Dimension(560, 600));

        // cannot verify that the text is truncated since DOM always returns the original
        // text and not what's displayed :{

        // verify tooltip present
        jGrowl("Fire MouseOver event to show tooltip, and validate tooltip is present");
        fireMouseOverEventById("resultsName_line0_control");
        waitForToolTipTextPresent("Travel Account 3");
    }

    @Ignore("https://jira.kuali.org/browse/KULRICE-13981 AFT Failure DemoLookUpConditionalCriteriaAft testAutoTruncateColumns poppup not detected in CI")
    @Test
    public void testAutoTruncateColumnsBookmark() throws Exception {
        testAutoTruncateColumns();
        passed();
    }

    @Ignore("https://jira.kuali.org/browse/KULRICE-13981 AFT Failure DemoLookUpConditionalCriteriaAft testAutoTruncateColumns poppup not detected in CI")
    @Test
    public void testAutoTruncateColumnsNav() throws Exception {
        testAutoTruncateColumns();
        passed();
    }

    @Ignore("https://jira.kuali.org/browse/KULRICE-13981 AFT Failure DemoLookUpConditionalCriteriaAft testLookUpConditionalCriteria poppup not detected in CI")
    @Test
    public void testLookUpConditionalCriteriaBookmark() throws Exception {
        testLookUpConditionalCriteria();
        passed();
    }

    @Ignore("https://jira.kuali.org/browse/KULRICE-13981 AFT Failure DemoLookUpConditionalCriteriaAft testLookUpConditionalCriteria poppup not detected in CI")
    @Test
    public void testLookUpConditionalCriteriaNav() throws Exception {
        testLookUpConditionalCriteria();
        passed();
    }
}
