
CREATE SEQUENCE VISIT_SEQ
    MINVALUE 1
    INCREMENT BY 1
    START WITH 1
    NOCACHE
NOORDER
NOCYCLE;



CREATE TABLE VISIT_TB(
                         SEQ NUMBER PRIMARY KEY,
                         SESSION_ID VARCHAR2(500),
                         INPT_DTTM DATE,
                         USER_IP VARCHAR2(30)
);

