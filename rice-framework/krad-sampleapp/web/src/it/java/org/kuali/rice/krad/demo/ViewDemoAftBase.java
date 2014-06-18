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
package org.kuali.rice.krad.demo;

import org.junit.Test;
import org.kuali.rice.testtools.selenium.WebDriverLegacyITBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */

public abstract class ViewDemoAftBase extends WebDriverLegacyITBase{

    protected void assertHelp() throws InterruptedException {
        WebElement help = waitForElementPresent(By.xpath("//button[@class='uif-iconOnly uif-helpAction icon-question']"));
        jGrowl("Click Help.");
        help.click();
        switchToWindow("Online Help");
        waitForTextPresent("Help");
        switchToWindow("Kuali");
    }

    private void testHelp() throws InterruptedException {
        assertHelp();
        passed();
    }

    @Test
    public void testHelpNav() throws Exception {
        testHelp();
    }

    @Test
    public void testHelpBookmark() throws Exception {
        testHelp();
    }
}
