<#--

    Copyright 2005-2014 The Kuali Foundation

    Licensed under the Educational Community License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.opensource.org/licenses/ecl2.php

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

-->
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
package ${package};

import edu.samplu.common.NavTemplateMethodAftBase;

/**
 * blanket approving a new document, results in a final document
 *
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public abstract class ${className}TmplMthdBlanketAppSTNavBase extends NavTemplateMethodSTBase {

    @Override
    protected String getMenuLinkLocator() {
        return ${menuLinkLocator};
    }

    @Override
    protected String getCreateNewLinkLocator() {
        return ${createNewLinkLocator};
    }
}
