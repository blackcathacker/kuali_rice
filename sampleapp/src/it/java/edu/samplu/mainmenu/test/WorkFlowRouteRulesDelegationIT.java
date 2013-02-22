/**
 * Copyright 2005-2011 The Kuali Foundation
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
package edu.samplu.mainmenu.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import edu.samplu.common.ITUtil;
import edu.samplu.common.UpgradedSeleniumITBase;
import org.junit.Test;

/**
 * tests creating and cancelling new and edit Routing Rule Delegation maintenance screens
 * 
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public class WorkFlowRouteRulesDelegationIT extends UpgradedSeleniumITBase {
    @Override
    public String getTestUrl() {
        return ITUtil.PORTAL;
    }

    @Test
    /**
     * tests that a new Routing Rule Delegation maintenance document can be cancelled
     */
    public void testCreateNew() throws Exception {
        assertEquals("Kuali Portal Index", getTitle());
        waitAndClick("link=Routing Rules Delegation");
        waitForPageToLoad();
        assertEquals("Kuali Portal Index", getTitle());
        selectFrame("iframeportlet");
        waitAndClick("//img[@alt='create new']");
//        selectFrame("relative=up");
        waitForPageToLoad();
        waitAndClick("name=methodToCall.performLookup.(!!org.kuali.rice.kew.rule.RuleBaseValues!!).(((id:parentRuleId))).((``)).((<>)).(([])).((**)).((^^)).((&&)).((//)).((~~)).(::::;;::::).anchor");
        waitForPageToLoad();
        waitAndClick("css=td.infoline > input[name=\"methodToCall.search\"]");
        waitForPageToLoad();
        waitAndClick("css=a[title=\"return valueRule Id=1046 \"]");
        waitForPageToLoad();
        waitAndClick("name=parentResponsibilityId");
        waitAndClick("name=methodToCall.createDelegateRule");
        waitForPageToLoad();
        assertTrue(isElementPresent("methodToCall.cancel"));
        waitAndClick("methodToCall.cancel");
        waitForPageToLoad();
        waitAndClick("methodToCall.processAnswer.button0");
        waitForPageToLoad();
    }

//    @Test // There are no results from the search so no edit link to click on
    /**
     * tests that a Routing Rule Delegation maintenance document is created for an edit operation originating from a lookup screen
     */
    public void testEditRouteRulesDelegation() throws Exception {
        assertEquals("Kuali Portal Index", getTitle());
        waitAndClick("link=Routing Rules Delegation");
        waitForPageToLoad();
        assertEquals("Kuali Portal Index", getTitle());
        selectFrame("iframeportlet");
        setSpeed("2000");
        waitAndClick("//input[@name='methodToCall.search' and @value='search']");
        waitForPageToLoad();
        waitAndClick("link=edit");
        waitForPageToLoad();
        assertTrue(isElementPresent("methodToCall.cancel"));
        waitAndClick("methodToCall.cancel");
        waitForPageToLoad();
        waitAndClick("methodToCall.processAnswer.button0");
        waitForPageToLoad();
              
    }
    
    @Test
    public void testCreateNewRRDTravelRequestDestRouting() throws Exception {
        waitAndClick("link=Routing Rules Delegation");
        waitForPageToLoad();
        selectFrame("iframeportlet");
        waitAndClick("css=img[alt=\"create new\"]");
        waitForPageToLoad();
        waitAndClick("name=methodToCall.performLookup.(!!org.kuali.rice.kew.rule.RuleBaseValues!!).(((id:parentRuleId))).((``)).((<>)).(([])).((**)).((^^)).((&&)).((//)).((~~)).(::::;;::::).anchor");
        waitForPageToLoad();
        waitAndClick("css=td.infoline > input[name=\"methodToCall.search\"]");
        waitForPageToLoad();
        waitAndClick("css=a[title=\"return valueRule Id=1046 \"]");
        waitForPageToLoad();
        waitAndClick("name=parentResponsibilityId");
        waitAndClick("name=methodToCall.createDelegateRule");
        waitForPageToLoad();
        waitAndClick("name=methodToCall.cancel");
        waitForPageToLoad();
        waitAndClick("name=methodToCall.processAnswer.button0");
        waitForPageToLoad();
        selectWindow("null");
        waitAndClick("xpath=(//input[@name='imageField'])[2]");
        waitForPageToLoad();
    }
}
