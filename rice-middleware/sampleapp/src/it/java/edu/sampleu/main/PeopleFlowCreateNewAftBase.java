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
package edu.sampleu.main;

import org.kuali.rice.testtools.common.JiraAwareFailable;
import org.kuali.rice.testtools.selenium.AutomatedFunctionalTestUtils;
import org.kuali.rice.testtools.selenium.WebDriverUtils;

/**
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public class PeopleFlowCreateNewAftBase extends MainTmplMthdSTNavBase{

    /**
     * ITUtil.PORTAL + "?channelTitle=People%20Flow&channelUrl="
     *  + WebDriverUtils.getBaseUrlString() + ITUtil.KRAD_LOOKUP_METHOD
     *  + "org.kuali.rice.kew.impl.peopleflow.PeopleFlowBo"
     *  + "&returnLocation=" + ITUtil.PORTAL_URL + ITUtil.SHOW_MAINTENANCE_LINKS;
     */
    public static final String BOOKMARK_URL = AutomatedFunctionalTestUtils.PORTAL + "?channelTitle=People%20Flow&channelUrl="
            + WebDriverUtils.getBaseUrlString() + AutomatedFunctionalTestUtils.KRAD_LOOKUP_METHOD
            + "org.kuali.rice.kew.impl.peopleflow.PeopleFlowBo"
            + "&returnLocation=" + AutomatedFunctionalTestUtils.PORTAL_URL + AutomatedFunctionalTestUtils.SHOW_MAINTENANCE_LINKS;

    @Override
    protected String getBookmarkUrl() {
        return BOOKMARK_URL;
    }

    /**
     * {@inheritDoc}
     * People Flow
     * @return
     */
    @Override
    protected String getLinkLocator() {
        return "People Flow";
    }

    public void testPeopleFlowBlanketApproveBookmark(JiraAwareFailable failable) throws Exception {
        testPeopleFlowBlanketApprove();
        passed();
    }
    public void testPeopleFlowBlanketApproveNav(JiraAwareFailable failable) throws Exception {
        testPeopleFlowBlanketApprove();
        passed();
    }
    
    public void testPeopleFlowDuplicateEntryBookmark(JiraAwareFailable failable) throws Exception {
    	testPeopleFlowDuplicateEntry();
    	passed();
    }
    
    public void testPeopleFlowDuplicateEntryNav(JiraAwareFailable failable) throws Exception {
    	testPeopleFlowDuplicateEntry();
    	passed();
    }
    
    private void testPeopleFlowDuplicateEntry() throws Exception {
    	selectFrameIframePortlet();
        waitAndClickByLinkText("Create New");
        clearTextByName("document.documentHeader.documentDescription");
        waitAndTypeByName("document.documentHeader.documentDescription", "Description for Duplicate");
        waitAndSelectByName("document.newMaintainableObject.dataObject.namespaceCode", "KUALI - Kuali Systems");
        clearTextByName("document.newMaintainableObject.dataObject.name");
        String tempValue=AutomatedFunctionalTestUtils.createUniqueDtsPlusTwoRandomChars();
        waitAndTypeByName("document.newMaintainableObject.dataObject.name", "Document Name"+tempValue);
        waitAndClickButtonByText("submit");
        waitForTextPresent("Document was successfully submitted.");
        selectTopFrame();
        waitAndClickByLinkText("Main Menu");
        waitAndClickByLinkText("People Flow");
        selectFrameIframePortlet();
        waitAndClickByLinkText("Create New");
        clearTextByName("document.documentHeader.documentDescription");
        waitAndTypeByName("document.documentHeader.documentDescription", "Description for Duplicate");
        waitAndSelectByName("document.newMaintainableObject.dataObject.namespaceCode", "KUALI - Kuali Systems");
        clearTextByName("document.newMaintainableObject.dataObject.name");
        waitAndTypeByName("document.newMaintainableObject.dataObject.name", "Document Name"+tempValue);
        waitAndClickButtonByText("submit");
        waitForTextPresent("A PeopleFlow already exists with the name");
    }
}
