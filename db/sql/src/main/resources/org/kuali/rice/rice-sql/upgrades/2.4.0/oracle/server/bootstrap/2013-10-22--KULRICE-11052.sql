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

-- KULRICE-11052 - Adding a static date to prevent impex from updating it every time

UPDATE KRIM_ROLE_T SET LAST_UPDT_DT = TO_DATE( '20121128143720', 'YYYYMMDDHH24MISS' ) WHERE ROLE_ID = 'KR1001'
/
