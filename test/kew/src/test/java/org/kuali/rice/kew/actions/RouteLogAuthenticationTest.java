/*
 * Copyright 2005-2007 The Kuali Foundation
 *
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
package org.kuali.rice.kew.actions;

import org.junit.Test;
import org.kuali.rice.edl.impl.WorkflowFunctions;
import org.kuali.rice.kew.service.WorkflowDocument;
import org.kuali.rice.kew.test.KEWTestCase;
import org.kuali.rice.kns.UserSession;
import org.kuali.rice.kns.util.GlobalVariables;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RouteLogAuthenticationTest extends KEWTestCase {

	public static final String DOCUMENT_TYPE_NAME = "BlanketApproveSequentialTest";

    protected void loadTestData() throws Exception {
        loadXmlFile("ActionsConfig.xml");
    }

	/**
     * Tests WorkflowFunctions.isUserRouteLogAuthenticated
     */
    @Test public void testUserRouteLogAuthenticated() throws Exception {
    	String user1PrincipalId = getPrincipalIdForName("user1");

    	WorkflowDocument document = new WorkflowDocument(user1PrincipalId, DOCUMENT_TYPE_NAME);
    	document.routeDocument("");

    	// ensure the UserSession is cleared out (could have been set up by other tests)
    	GlobalVariables.setUserSession(null);

        // false because we didn't set up the user session properly
        assertFalse(WorkflowFunctions.isUserRouteLogAuthenticated(document.getRouteHeaderId() + ""));

        // these two should be in the route log
        GlobalVariables.setUserSession(new UserSession("user1"));
        assertTrue(WorkflowFunctions.isUserRouteLogAuthenticated(document.getRouteHeaderId() + ""));
        GlobalVariables.setUserSession(new UserSession("bmcgough"));
        assertTrue(WorkflowFunctions.isUserRouteLogAuthenticated(document.getRouteHeaderId() + ""));

        // user2 should NOT be in the route log
        GlobalVariables.setUserSession(new UserSession("user2"));
        assertFalse(WorkflowFunctions.isUserRouteLogAuthenticated(document.getRouteHeaderId() + ""));
    }

}
