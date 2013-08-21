--
-- Copyright 2005-2013 The Kuali Foundation
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

DROP TABLE TRV_ACCT_TYPE
/
DROP TABLE TRV_ACCT
/
DROP TABLE TRV_ACCT_FO
/
DROP SEQUENCE TRV_FO_ID_S
/
DROP TABLE trv_acct_ext
/
CREATE TABLE TRV_ACCT_TYPE  ( 
	ACCT_TYPE     	VARCHAR2(3),
	ACCT_TYPE_NAME	VARCHAR2(40),
	OBJ_ID        	VARCHAR2(36) NOT NULL,
	VER_NBR       	NUMBER(8,0) DEFAULT 1 NOT NULL,
    PRIMARY KEY( ACCT_TYPE )
)
/
INSERT INTO TRV_ACCT_TYPE(ACCT_TYPE, ACCT_TYPE_NAME, OBJ_ID, VER_NBR)
  VALUES('CAT', 'Clearing', 'CAT', 1)
/
INSERT INTO TRV_ACCT_TYPE(ACCT_TYPE, ACCT_TYPE_NAME, OBJ_ID, VER_NBR)
  VALUES('EAT', 'Expense', 'EAT', 1)
/
INSERT INTO TRV_ACCT_TYPE(ACCT_TYPE, ACCT_TYPE_NAME, OBJ_ID, VER_NBR)
  VALUES('IAT', 'Income', 'IAT', 1)
/
COMMIT
/
CREATE TABLE TRV_ACCT  ( 
	ACCT_NUM      	VARCHAR(10),
	ACCT_NAME     	VARCHAR(40),
	ACCT_TYPE     	VARCHAR(3),
	ACCT_FO_ID    	VARCHAR(40),
	OBJ_ID        	VARCHAR(36) NOT NULL,
	VER_NBR       	NUMBER(8,0) DEFAULT 1 NOT NULL,
	SUB_ACCT      	VARCHAR(10),
	SUB_ACCT_NAME 	VARCHAR(40),
	CREATE_DT     	DATE,
	SUBSIDIZED_PCT	NUMBER(5,2),
    PRIMARY KEY ( ACCT_NUM ),
    CONSTRAINT TRV_ACCT_TR1 FOREIGN KEY ( ACCT_TYPE ) REFERENCES TRV_ACCT_TYPE ( ACCT_TYPE )
)   
/
INSERT INTO TRV_ACCT(ACCT_NUM, ACCT_NAME, ACCT_TYPE, ACCT_FO_ID, OBJ_ID, VER_NBR, SUB_ACCT, SUB_ACCT_NAME, CREATE_DT, SUBSIDIZED_PCT)
  VALUES('a1', 'Travel Account 1', 'IAT', 'fred', 'a1', 1, NULL, NULL, NULL, NULL)
/
INSERT INTO TRV_ACCT(ACCT_NUM, ACCT_NAME, ACCT_TYPE, ACCT_FO_ID, OBJ_ID, VER_NBR, SUB_ACCT, SUB_ACCT_NAME, CREATE_DT, SUBSIDIZED_PCT)
  VALUES('a14', 'Travel Account 14', 'CAT', 'fran', 'a14', 1, NULL, NULL, NULL, NULL)
/
INSERT INTO TRV_ACCT(ACCT_NUM, ACCT_NAME, ACCT_TYPE, ACCT_FO_ID, OBJ_ID, VER_NBR, SUB_ACCT, SUB_ACCT_NAME, CREATE_DT, SUBSIDIZED_PCT)
  VALUES('a2', 'Travel Account 2', 'EAT', 'fran', 'a2', 1, 'SACC', 'One With A Sub Account', NULL, NULL)
/
INSERT INTO TRV_ACCT(ACCT_NUM, ACCT_NAME, ACCT_TYPE, ACCT_FO_ID, OBJ_ID, VER_NBR, SUB_ACCT, SUB_ACCT_NAME, CREATE_DT, SUBSIDIZED_PCT)
  VALUES('a3', 'Travel Account 3', 'IAT', 'frank', 'a3', 1, NULL, NULL, NULL, 20.0)
/
INSERT INTO TRV_ACCT(ACCT_NUM, ACCT_NAME, ACCT_TYPE, ACCT_FO_ID, OBJ_ID, VER_NBR, SUB_ACCT, SUB_ACCT_NAME, CREATE_DT, SUBSIDIZED_PCT)
  VALUES('a6', 'Travel Account 6', 'CAT', 'fran', 'a6', 1, NULL, NULL, NULL, NULL)
/
INSERT INTO TRV_ACCT(ACCT_NUM, ACCT_NAME, ACCT_TYPE, ACCT_FO_ID, OBJ_ID, VER_NBR, SUB_ACCT, SUB_ACCT_NAME, CREATE_DT, SUBSIDIZED_PCT)
  VALUES('a8', 'Travel Account 8', 'EAT', 'fran', 'a8', 1, NULL, NULL, NULL, NULL)
/
INSERT INTO TRV_ACCT(ACCT_NUM, ACCT_NAME, ACCT_TYPE, ACCT_FO_ID, OBJ_ID, VER_NBR, SUB_ACCT, SUB_ACCT_NAME, CREATE_DT, SUBSIDIZED_PCT)
  VALUES('a9', 'Travel Account 9', 'CAT', 'fran', 'a9', 1, NULL, NULL, NULL, NULL)
/
COMMIT
/
