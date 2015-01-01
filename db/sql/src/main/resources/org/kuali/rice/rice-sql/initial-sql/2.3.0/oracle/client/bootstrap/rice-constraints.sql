--
-- Copyright 2005-2015 The Kuali Foundation
--
-- Licensed under the Educational Community License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
-- http://www.opensource.org/licenses/ecl2.php
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

ALTER TABLE KRNS_ATT_T
    ADD CONSTRAINT KRNS_ATT_TR1 FOREIGN KEY (NTE_ID)
    REFERENCES KRNS_NTE_T (NTE_ID)
/

ALTER TABLE KRNS_MAINT_DOC_ATT_LST_T
    ADD CONSTRAINT KRNS_MAINT_DOC_ATT_LST_FK1 FOREIGN KEY (DOC_HDR_ID)
    REFERENCES KRNS_MAINT_DOC_T (DOC_HDR_ID)
/

ALTER TABLE KRNS_MAINT_DOC_T
    ADD CONSTRAINT KRNS_MAINT_DOC_TR1 FOREIGN KEY (DOC_HDR_ID)
    REFERENCES KRNS_DOC_HDR_T (DOC_HDR_ID)
/

ALTER TABLE KRNS_NTE_T
    ADD CONSTRAINT KRNS_NTE_TR1 FOREIGN KEY (NTE_TYP_CD)
    REFERENCES KRNS_NTE_TYP_T (NTE_TYP_CD)
/

ALTER TABLE KRSB_QRTZ_BLOB_TRIGGERS
    ADD CONSTRAINT KRSB_QRTZ_BLOB_TRIGGERS_TR1 FOREIGN KEY (TRIGGER_NAME, TRIGGER_GROUP)
    REFERENCES KRSB_QRTZ_TRIGGERS (TRIGGER_NAME, TRIGGER_GROUP)
/

ALTER TABLE KRSB_QRTZ_CRON_TRIGGERS
    ADD CONSTRAINT KRSB_QRTZ_CRON_TRIGGERS_TR1 FOREIGN KEY (TRIGGER_NAME, TRIGGER_GROUP)
    REFERENCES KRSB_QRTZ_TRIGGERS (TRIGGER_NAME, TRIGGER_GROUP)
/

ALTER TABLE KRSB_QRTZ_JOB_LISTENERS
    ADD CONSTRAINT KRSB_QUARTZ_JOB_LISTENERS_TR1 FOREIGN KEY (JOB_NAME, JOB_GROUP)
    REFERENCES KRSB_QRTZ_JOB_DETAILS (JOB_NAME, JOB_GROUP)
/

ALTER TABLE KRSB_QRTZ_SIMPLE_TRIGGERS
    ADD CONSTRAINT KRSB_QRTZ_SIMPLE_TRIGGERS_TR1 FOREIGN KEY (TRIGGER_NAME, TRIGGER_GROUP)
    REFERENCES KRSB_QRTZ_TRIGGERS (TRIGGER_NAME, TRIGGER_GROUP)
/

ALTER TABLE KRSB_QRTZ_TRIGGER_LISTENERS
    ADD CONSTRAINT KRSB_QRTZ_TRIGGER_LISTENE_TR1 FOREIGN KEY (TRIGGER_NAME, TRIGGER_GROUP)
    REFERENCES KRSB_QRTZ_TRIGGERS (TRIGGER_NAME, TRIGGER_GROUP)
/