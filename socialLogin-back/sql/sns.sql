CREATE TABLE MEMBER(
  USER_NO NUMBER PRIMARY KEY,               
  EMAIL VARCHAR2(30) NOT NULL UNIQUE,   
  USER_PWD VARCHAR2(100) NOT NULL,  
  NICK_NAME VARCHAR2(15) NOT NULL,    
  ENROLL_DATE DATE DEFAULT SYSDATE,
  STATUS VARCHAR2(1) DEFAULT 'Y' CHECK (STATUS IN('Y', 'N')),
  PROFILE VARCHAR2(200)
);
CREATE SEQUENCE SEQ_UNO NOCACHE;

create table "AUTHORITY"(
    USER_NO NUMBER not null,                               -- 회원번호
    AUTHORITY varchar2(20) not null,                       -- 권한(일반, 관리자 등)
    constraint PK_AUTHORITY primary key (USER_NO, AUTHORITY),
    constraint FK_AUTHORITY_USER_NO foreign key(USER_NO) references MEMBER
);

CREATE TABLE MEMBER_SOCIAL(
    SOCIAL_ID VARCHAR2(50CHAR) PRIMARY KEY, -- 각 플랫폼내의 ID값 저장
    USER_NO NUMBER NOT NULL, -- 
    SOCIAL_TYPE VARCHAR2(50CHAR) NOT NULL, -- 카카오, 네이버, 구글등 소셜타입저장.
    constraint FK_MEMBER_SOCIAL_USER_NO foreign key(USER_NO) references MEMBER
);
