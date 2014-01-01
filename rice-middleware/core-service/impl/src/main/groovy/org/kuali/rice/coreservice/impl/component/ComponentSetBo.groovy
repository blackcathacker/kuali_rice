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
package org.kuali.rice.coreservice.impl.component;


import java.sql.Timestamp

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Version

@Entity
@Table(name="KRCR_CMPNT_SET_T")
public class ComponentSetBo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="CMPNT_SET_ID")
	String componentSetId
    @Column(name="LAST_UPDT_TS")
    Timestamp lastUpdateTimestamp
    @Column(name="CHKSM")
    String checksum
    @Column(name="VER_NBR")
	@Version
    Long versionNumber

}

