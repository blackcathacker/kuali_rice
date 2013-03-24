/**
 * Copyright 2005-2013 The Kuali Foundation
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
package edu.samplu.admin.test;

import edu.samplu.common.Failable;
import edu.samplu.common.FreemarkerSTBase;
import edu.samplu.common.ITUtil;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import org.openqa.selenium.By;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Tests uploads of new users and group.
 *
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */

public abstract class XMLIngesterAbstractSmokeTestBase extends FreemarkerSTBase implements Failable {

    // File generation
    private String PROPS_LOCATION = System.getProperty("xmlingester.props.location", null);
    private String DEFAULT_PROPS_LOCATION = "XML/xmlingester.properties";

    // Templates for File Generation
    private static final String DIR_TMPL = "/XML/";
    private static final String TMPL_USER_CONTENT = "SimpleUserContent.ftl";
    private static final String TMPL_GROUP_CONTENT = "SimpleGroupContent.ftl";

    /**
     *
     * @param name of temp file to create
     * @return tempFile
     * @throws IOException
     */
    protected abstract File newTempFile(String name) throws IOException;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        // generated load users and group resources
        cfg = new Configuration();
        cfg.setTemplateLoader(new ClassTemplateLoader(getClass().getClassLoader().getClass(), DIR_TMPL));
    }

    @Override
    public String getTestUrl() {
        return ITUtil.PORTAL;
    }

    @Override
    public String getUserName() {
        return "admin"; // xml ingestion requires admin permissions
    }

    /**
     * Navigate to the page under test and call {@link #testIngestion}
     *
     * @param failable {@link edu.samplu.common.Failable}
     * @throws Exception
     */
    public void testNavIngestion(Failable failable) throws Exception {
        navigate(failable);
        testIngestion(failable);
    }

    /**
     * go to the getMenuLinkLocator() Menu and click the getLinkLocator()
     */
    protected void navigate(Failable failable) throws Exception {
        selectTopFrame();
        waitAndClickByLinkText("Administration", failable);
        waitForTitleToEqualKualiPortalIndex();
        waitAndClickByLinkText("XML Ingester", failable);
        selectFrameIframePortlet();
        checkForIncidentReport("XML Ingester", failable, "");
    }


    protected List<File> buildFileUploadList() throws Exception {
        List<File> fileUploadList = new ArrayList<File>();
        try {
            // update properties with timestamp value if includeDTSinPrefix is true
            Properties props = loadProperties(PROPS_LOCATION, DEFAULT_PROPS_LOCATION);
            if(props.get("userIncludeDTSinPrefix") != null
                    && "true".equalsIgnoreCase((String) props.get("userIncludeDTSinPrefix"))) {
                props.setProperty("userPrefix", "" + props.get("userPrefix") + ITUtil.DTS);
            }
            systemPropertiesOverride(props, "XMLIngester");

            // build files and add to array
            fileUploadList.add(
                    writeTemplateToFile(newTempFile("loadtest-users.xml"), cfg.getTemplate(TMPL_USER_CONTENT), props));
            fileUploadList.add(
                    writeTemplateToFile(newTempFile("loadtest-group.xml"), cfg.getTemplate(TMPL_GROUP_CONTENT), props));
        } catch( Exception e) {
            throw new Exception("Unable to generate files for upload", e);
        }
        return fileUploadList;
    }

    /**
     * Based on load user and groups manual tests; dynamically generates user and group file
     * and loads into the xml ingester screen
     *
     */
    public void testIngestion(Failable failable) throws Exception {
        List<File> fileUploadList = buildFileUploadList();
        int cnt = 0;
        for(File file : fileUploadList) {
            String path = file.getAbsolutePath().toString();
            driver.findElement(By.name("file[" + cnt + "]")).sendKeys(path);
            cnt++;
        }
        waitAndClickByXpath("//*[@id='imageField']");

        // confirm all files were uploaded successfully
        for(File file: fileUploadList) {
            assertTextPresent("Ingested xml doc: " + file.getName());
        }
        passed();
    }
}
