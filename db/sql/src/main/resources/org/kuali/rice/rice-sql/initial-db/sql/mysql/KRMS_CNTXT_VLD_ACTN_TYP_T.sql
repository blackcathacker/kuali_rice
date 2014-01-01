--
-- Copyright 2005-2014 The Kuali Foundation
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

TRUNCATE TABLE KRMS_CNTXT_VLD_ACTN_TYP_T
/
INSERT INTO KRMS_CNTXT_VLD_ACTN_TYP_T (ACTN_TYP_ID,CNTXT_ID,CNTXT_VLD_ACTN_ID,VER_NBR)
  VALUES ('T1002','CONTEXT1','T1000',1)
/
INSERT INTO KRMS_CNTXT_VLD_ACTN_TYP_T (ACTN_TYP_ID,CNTXT_ID,CNTXT_VLD_ACTN_ID,VER_NBR)
  VALUES ('1000','CONTEXT1','T1001',1)
/
INSERT INTO KRMS_CNTXT_VLD_ACTN_TYP_T (ACTN_TYP_ID,CNTXT_ID,CNTXT_VLD_ACTN_ID,VER_NBR)
  VALUES ('1001','CONTEXT1','T1002',1)
/
INSERT INTO KRMS_CNTXT_VLD_ACTN_TYP_T (ACTN_TYP_ID,CNTXT_ID,CNTXT_VLD_ACTN_ID,VER_NBR)
  VALUES ('1003','CONTEXT1','T1003',1)
/
