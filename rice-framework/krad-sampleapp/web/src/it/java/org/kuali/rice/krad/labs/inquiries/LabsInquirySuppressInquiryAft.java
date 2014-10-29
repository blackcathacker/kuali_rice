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
package org.kuali.rice.krad.labs.inquiries;

import org.junit.Test;

/**
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public class LabsInquirySuppressInquiryAft extends LabsInquiryBase {

    /**
     * /kr-krad/kradsampleapp?viewId=KradInquirySample-PageR6C1
     */
    public static final String BOOKMARK_URL = "/kr-krad/kradsampleapp?viewId=KradInquirySample-PageR6C1";

    @Override
    protected String getBookmarkUrl() {
        return BOOKMARK_URL;
    }

    @Override
    protected void navigate() throws Exception {
    	navigateToInquiry("Inquiry - Suppress Inquiry");
    }

    protected void testInquirySuppressInquiry() throws InterruptedException {

        waitAndClickByLinkText("The autogenerated inquiry on Primary Destination is not suppressed");

        String[][] labeledText = {{"Id:", "10000"},
                                  {"Travel Authorization Document:", "10000"},
                                  {"Primary Destination:", "10000"},
                                  {"Date of Use:", "/"}, // getDateToday()
                                  {"Breakfast Value:", "10"},
                                  {"Lunch Value:", "10"},
                                  {"Dinner Value:", "15"},
                                  {"Amount estimated for incidentals:", "20"},
                                  {"Mileage rate:", "DO"},
                                  {"Number of estimated miles:", "30"}};

        assertLabeledTextPresent(labeledText);
        assertTrue("Primary Destination should be inquirable",isElementPresentByXpath("//a[contains(text(), '1000')]"));
        waitAndClickButtonByText("< Back");

        waitAndClickByLinkText("The autogenerated inquiry on Primary Destination is suppressed");
        assertLabeledTextPresent(labeledText);
        assertFalse("Primary Destination should not be inquirable", isElementPresentByXpath(
                "//a[contains(text(), '1000')]"));
    }
 
    @Test
    public void testInquirySuppressInquiryBookmark() throws Exception {
       	testInquirySuppressInquiry();
        passed();
    }

    @Test
    public void testInquirySuppressInquiryNav() throws Exception {
       	testInquirySuppressInquiry();
        passed();
    }

}
