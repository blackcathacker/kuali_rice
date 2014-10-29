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
package org.kuali.rice.krad.demo.uif.library.collections;

import org.junit.Test;

import org.kuali.rice.testtools.selenium.WebDriverLegacyITBase;
import org.openqa.selenium.By;

/**
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public class LibraryCollectionFeaturesAddLineAft extends WebDriverLegacyITBase {

    /**
     * /kr-krad/kradsampleapp?viewId=Demo-CollectionAddLineView
     */
    public static final String BOOKMARK_URL = "/kr-krad/kradsampleapp?viewId=Demo-CollectionAddLineView";

    @Override
    protected String getBookmarkUrl() {
        return BOOKMARK_URL;
    }

    @Override
    protected void navigate() throws Exception {
        waitAndClickLibraryLink();
        waitAndClickByLinkText("Collection Features");
        waitAndClickByLinkText("Add Line");
    }

    protected void testCollectionFeaturesDefaultAddLine() throws Exception {
        selectByName("exampleShown","Collection Default Add Line");
        waitAndTypeByXpath("//section[@id='Demo-CollectionAddLine-Example1']/section/div/div/table/tbody/tr[1]/td[2]/div/input","12");
        waitAndTypeByXpath("//section[@id='Demo-CollectionAddLine-Example1']/section/div/div/table/tbody/tr[1]/td[3]/div/input","5");
        waitAndClickButtonByText("Add");
        waitForElementPresentByXpath("//input[@name='collection1[0].field1' and @value='12']");
        assertElementPresentByXpath("//input[@name='collection1[0].field2' and @value='5']");
    }

    protected void testCollectionFeaturesDefaultAddLineDuplicatePropertyName() throws Exception {
        selectByName("exampleShown","Collection Add Line w duplicateLinePropertyNames");
        waitAndTypeByXpath("//section[@id='Demo-CollectionAddLine-Example6']/section/div/div/table/tbody/tr[1]/td[2]/div/input","13");
        waitAndTypeByXpath("//section[@id='Demo-CollectionAddLine-Example6']/section/div/div/table/tbody/tr[1]/td[3]/div/input","14");
        waitAndClickByXpath("//section[@id='Demo-CollectionAddLine-Example6']/section/div/div/table/tbody/tr[1]/td[4]/div/fieldset/div/button");
        waitForElementPresentByXpath("//li[@class='uif-errorMessageItem' and contains(text(),'Duplicate Default Add Line with duplicateLinePropertyNames property configured')]");
    }

    protected void testCollectionFeaturesDefaultAddBlankLine() throws Exception {
        selectByName("exampleShown","Collection Add Blank Line");
        waitAndClickByXpath("//section[@id='Demo-CollectionAddLine-Example3']/section/div/button");
        assertElementPresentByXpath("//input[@name='collection1_6[0].field1' and @value]");
        assertElementPresentByXpath("//input[@name='collection1_6[0].field2' and @value]");
    }

    protected void testCollectionFeaturesDefaultAddViaLightbox() throws Exception {
        selectByName("exampleShown","Collection Add with Dialog");
        jGrowl("Click Add Line");
        waitAndClickByXpath("//section[@data-parent=\"Demo-CollectionAddLine-Example2\"]/div/button");
        waitAndTypeByName("newCollectionLines['collection1_2'].field1", "");
        waitAndTypeByName("newCollectionLines['collection1_2'].field2", "");
        fireMouseOverEventByName("newCollectionLines['collection1_2'].field1");
        waitIsVisible("//div[@class='jquerybubblepopup jquerybubblepopup-kr-error-cs' and @style='margin: 0px 0px 0px 72.16666662693024px; opacity: 1; top: 61px; left: 834px; position: absolute; display: block;']");
        waitAndTypeByName("newCollectionLines['collection1_2'].field1", "42");
        waitAndTypeByName("newCollectionLines['collection1_2'].field2", "55");
        jGrowl("Click Add");
        waitAndClickByXpath("//div[@class=\"modal-footer\"]/button[@data-loadingmessage=\"Adding Line...\"]");
        waitForElementPresentByXpath("//input[@name='collection1_2[0].field1' and @value='42']");
        waitForElementPresentByXpath("//input[@name='collection1_2[0].field2' and @value='55']");
    }

    protected void testCollectionFeaturesAddLineWithDialogAndCustomActions() throws Exception {
        selectByName("exampleShown","Collection Add Line with Dialog and Custom Actions");

        // Add Income Cancel
        waitAndClickByXpath("//section[@data-parent='Demo-CollectionAddLine-Example4']/button/span[@class='icon-plus']");
        waitAndTypeByName("newCollectionLines['collection1_7'].field3","999");
        waitAndTypeByName("newCollectionLines['collection1_7'].field1","999");
        waitAndTypeByName("newCollectionLines['collection1_7'].field2","999");
        waitAndClickByXpath("//section[@style='display: block;']/div/div/div[@class='modal-footer']/button[contains(text(),'Cancel')]");
        waitForElementNotPresent(By.xpath("//section[@style='display: block;']"));
        waitForElementNotPresent(By.xpath("//input[@value='999']"));

        //Add Income and Verify
        waitAndClickByXpath("//section[@data-parent='Demo-CollectionAddLine-Example4']/button/span[@class='icon-plus']");
        waitAndTypeByName("newCollectionLines['collection1_7'].field3","999");
        waitAndTypeByName("newCollectionLines['collection1_7'].field1","999");
        waitAndTypeByName("newCollectionLines['collection1_7'].field2","999");
        waitAndClickByXpath("//section[@style='display: block;']/div/div/div[@class='modal-footer']/button[contains(text(),'Cancel')]");
        waitForElementPresentByXpath("//input[@value='999']");

        //Trash Check
        waitAndClickByXpath("//button[@class='btn btn-default btn-xs uif-action uif-boxLayoutHorizontalItem icon-trash']");
        waitForElementNotPresent(By.xpath("//input[@value='999']"));
        waitForTextPresent("You have deleted an item from Project Income.");
    }

    protected void testCollectionFeaturesAddLineWithCustomActions() throws Exception {
        selectByName("exampleShown","Collection Add Line with Custom Actions");

        // Add Income Verify
        waitAndTypeByName("newCollectionLines['collection1_8'].field1","999");
        waitAndTypeByName("newCollectionLines['collection1_8'].field2","999");
        waitAndClickByXpath("//section[@data-parent='Demo-CollectionAddLine-Example5']/div/table/tbody/tr/td[4]/div/fieldset/div/button[contains(text(),'Add')]");
        waitForElementPresentByXpath("//input[@value='999']");

        //Trash Check
        waitAndClickByXpath("//button[@class='btn btn-default btn-xs uif-action uif-boxLayoutHorizontalItem icon-trash']");
        waitForElementNotPresent(By.xpath("//input[@value='999']"));
        waitForTextPresent("You have deleted an item from Project Income.");
    }

    @Test
    public void testCollectionFeaturesAddLineBookmark() throws Exception {
        testCollectionFeaturesDefaultAddLine();
        testCollectionFeaturesDefaultAddLineDuplicatePropertyName();
        testCollectionFeaturesDefaultAddBlankLine();
        testCollectionFeaturesDefaultAddViaLightbox();
        testCollectionFeaturesAddLineWithDialogAndCustomActions();
        testCollectionFeaturesAddLineWithCustomActions();
        passed();
    }

    @Test
    public void testCollectionFeaturesAddLineNav() throws Exception {
        testCollectionFeaturesDefaultAddLine();
        testCollectionFeaturesDefaultAddLineDuplicatePropertyName();
        testCollectionFeaturesDefaultAddBlankLine();
        testCollectionFeaturesDefaultAddViaLightbox();
        testCollectionFeaturesAddLineWithDialogAndCustomActions();
        testCollectionFeaturesAddLineWithCustomActions();
        passed();
    }

}
