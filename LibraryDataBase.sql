/* 
     ??κ΄? κ΄?λ¦? ?λ‘κ·Έ?¨ DB
     
     * TABLE λͺ©λ‘
     
     - admins                 κ΄?λ¦¬μ μ§μ ? λ³?
     - members                ?? ? λ³?
     - books                  ??? λ³?
     - locations              ?? ?μΉ? ? λ³?
     - check_out_info         ??μΆ? ? λ³?
     - readingroom            ?΄??€ ? λ³?
     - seat_use_details       ?΄??€ ?¬?©?΄?­
     - image_information      ?λ‘κ·Έ?¨? ?¬?©?? ?΄λ―Έμ? ? λ³?
     
     * SEQUENCE
          
     - admin_num_seq          κ΄?λ¦¬μ λ²νΈ
     - mem_num_seq            ?? λ²νΈ
     - book_id_seq            ?±λ‘? λ²νΈ
     - check_out_id_seq       ??μΆλ΄?­ ??΄? 
     - user_detail_id_seq     ?΄??€ μ’μ ?¬?©?΄?­ ??΄?
     
*/
SELECT * FROM tabs;
SELECT * FROM user_sequences;

-- κ΄?λ¦¬μ μ§μ ? λ³? ??΄λΈ?
CREATE TABLE admins(
     admin_num      NUMBER(11)  CONSTRAINT ad_ad_num_pk  PRIMARY KEY,      -- ?¬? λ²νΈ  (PK)
     admin_name     VARCHAR2(255)  CONSTRAINT ad_ad_name_nn NOT NULL,      -- ?¬? ?΄λ¦?
     admin_pw       VARCHAR2(255)  CONSTRAINT ad_ad_pw_nn NOT NULL,        -- ?¬? λΉλ?λ²νΈ
     admin_phone    VARCHAR2(255)  CONSTRAINT ad_ad_phone_uk UNIQUE,       -- ?¬? ?°?½μ²? (UK)
     admin_email    VARCHAR2(255)  CONSTRAINT ad_ad_email_nn NOT NULL,     -- ?¬? ?΄λ©μΌ
     admin_address  VARCHAR2(255)  CONSTRAINT ad_ad_address_nn NOT NULL,   -- ?¬? μ£Όμ
     admin_registrationdate  VARCHAR2(255)  DEFAULT sysdate,               -- ?¬? ?±λ‘μΌ
     admin_updatedate    VARCHAR2(255),                                     -- ?¬? ?? ?Ό
     admin_note     VARCHAR2(255)                                          -- λΉκ³ 
);

-- ?? ? λ³? ??΄λΈ?
CREATE TABLE members (
     mem_num        NUMBER(11) CONSTRAINT m_m_num_pk  PRIMARY KEY,    -- ?? λ²νΈ  (PK)
     mem_name       VARCHAR2(255) CONSTRAINT m_m_name_nn NOT NULL,    -- ?? ?΄λ¦?
     mem_id         VARCHAR2(255) CONSTRAINT m_m_id_uk UNIQUE,        -- ?? ??΄? (UK)
     mem_pw         VARCHAR2(255) CONSTRAINT m_m_pw_nn NOT NULL,      -- ?? λΉλ?λ²νΈ
     mem_birthday   CHAR(6) CONSTRAINT m_m_birthday_nn NOT NULL,      -- ?? ????Ό
     mem_sex        CHAR(1) CONSTRAINT m_m_sex_boolean CHECK(mem_sex IN ('0', '1')), -- ?? ?±λ³? (0,1 λ‘? κ΅¬λΆ)
     mem_phone      VARCHAR2(255) CONSTRAINT m_m_phone_uk UNIQUE,     -- ?? ?°?½μ²? (UK)
     mem_email      VARCHAR2(255) CONSTRAINT m_m_email_nn NOT NULL,   -- ?? ?΄λ©μΌ
     mem_address    VARCHAR2(255) CONSTRAINT m_m_address_nn NOT NULL, -- ?? μ£Όμ
     mem_registrationdate VARCHAR2(255)     DEFAULT sysdate,          -- ?? ?±λ‘μΌ
     mem_updatedate VARCHAR2(255)                                     -- ?? ? λ³? ?? ?Ό
     mem_note       VARCHAR2(255)                                     -- λΉκ³ 
);

-- ?? ? λ³? ??΄λΈ?
CREATE TABLE books(
     book_id        VARCHAR2(255)  CONSTRAINT b_b_id_pk  PRIMARY KEY,      -- ?? ?±λ‘λ²?Έ (PK)
     book_title     VARCHAR2(255)  CONSTRAINT b_b_title_nn NOT NULL,       -- ?? ? λͺ?
     book_author    VARCHAR2(255)  CONSTRAINT b_b_author_nn NOT NULL,      -- ?? ???
     book_publisher VARCHAR2(255)  CONSTRAINT b_b_publisher_nn NOT NULL,   -- ?? μΆν?¬
     book_isbn  VARCHAR2(255) CONSTRAINT b_b_isbn_uk UNIQUE,               -- ?? ISBN λ²νΈ  (UK)
     book_bias      NUMBER(11) DEFAULT 1,                                  -- ?? ?ΈκΆμ°¨ (κΈ°λ³Έκ°? 1)
     book_duplicates NUMBER(11) DEFAULT 1,                                 -- ?? λ³΅κΆ? (κΈ°λ³Έκ°? 1)
     book_price     NUMBER(11),                                            -- ?? κ°?κ²?
     location_id    CHAR(1) CONSTRAINT b_loc_id_fk REFERENCES locations(location_id), -- ?? ?μΉ? (FK)
     book_registrationdate   VARCHAR2(255) DEFAULT sysdate,               -- ?? ?±λ‘μΌ
     book_updatedate     VARCHAR2(255)                                     -- ?? ? λ³? 
     book_note      VARCHAR2(255)                                          -- λΉκ³ 
);

--CREATE TABLE callsign(
--     book_callsign  VARCHAR2(255)  CONSTRAINT c_b_cs_pk  PRIMARY KEY,
--     cs_classification_code VARCHAR2(255),
--     cs_sign           VARCHAR2(255),
--     cs_bias           NUMBER(11),
--     cs_duplicates     NUMBER(11)
--);

-- ?? λΆλ₯ / ?μΉ? ??΄λΈ?
CREATE TABLE locations(
     location_id    CHAR(1) CONSTRAINT l_l_id_pk  PRIMARY KEY,        -- ?? ?μΉ? ??΄?   (PK)
     location_name  VARCHAR2(255) CONSTRAINT l_l_name_nn NOT NULL     -- ?? ?μΉ? ?΄λ¦?
);

-- ??μΆ? ?΄?­ ??΄λΈ?
CREATE TABLE check_out_info(
     check_out_id   NUMBER(11) CONSTRAINT coi_coi_id_pk PRIMARY KEY,                 -- ???¬ ??΄? (PK)
     book_id        VARCHAR2(255) CONSTRAINT coi_b_id_fk REFERENCES books(book_id),  -- ???¬ ??   (FK)
     mem_num        NUMBER(11) CONSTRAINT coi_m_num_fk REFERENCES members(mem_num),  -- ?? λ²νΈ   (FK)
     check_out_date VARCHAR2(255)           DEFAULT sysdate,                         -- ???¬ ? μ§?
     expect_return_date VARCHAR2(255)       DEFAULT sysdate + (INTERVAL '7' DAY),    -- λ°λ© ??  ? μ§?
     check_in_date  VARCHAR2(255)                                                    -- λ°λ© ? μ§?
);

-- ?΄??€ ? λ³? ??΄λΈ?
CREATE TABLE readingroom(
     seat_num       NUMBER(11) CONSTRAINT r_s_num_pk PRIMARY KEY,     -- μ’μ λ²νΈ  (PK)
     table_divider  CHAR(1),                                          -- μΉΈλ§?΄ ?¬λΆ?
);

-- μ’μ ?΄?© ? λ³? ??΄λΈ?
CREATE TABLE seat_use_details(
     use_id     NUMBER(11) CONSTRAINT sud_u_id_pk PRIMARY KEY,                        -- ?΄??€ ?¬?©?΄?­ ??΄?     (PK)
     mem_num    NUMBER(11) CONSTRAINT sud_m_num_fk REFERENCES members(mem_num),       -- ??? λ³?   (FK)
     seat_num   NUMBER(11) CONSTRAINT sud_s_num_fk REFERENCES readingroom(seat_num),  -- μ’μ λ²νΈ  (FK)
     start_time VARCHAR2(255) DEFAULT to_char(sysdate, 'yyyy.mm.dd hh24:mi'),         -- ?¬?© ?? ?κ°?
     end_time   VARCHAR2(255)                                                         -- ?¬?© μ’λ£ ?κ°?
);

-- ?΄λ―Έμ? ? λ³? ??΄λΈ?
CREATE TABLE IMAGE_INFORMATION(
     image_id  NUMBER(11) CONSTRAINT i_i_id_pk PRIMARY KEY,                          -- ?΄λ―Έμ? ??΄?     (PK)
     image_name     VARCHAR2(255) CONSTRAINT i_i_name_nn NOT NULL,                   -- ?΄λ―Έμ? ?΄λ¦?
     image_byte_info      BLOB CONSTRAINT i_i_b_info_nn NOT NULL                     -- ?΄λ―Έμ? byte ? λ³?
);

-- ?? λ²νΈ ????€
CREATE SEQUENCE mem_num_seq
     INCREMENT BY 1
     START WITH 1
     MINVALUE 1
     MAXVALUE 99999999
     CYCLE
     NOCACHE;
     
-- ?¬? λ²νΈ ????€
CREATE SEQUENCE admin_num_seq
     INCREMENT BY 1
     START WITH 1
     MINVALUE 1
     MAXVALUE 99999999
     CYCLE
     NOCACHE;

-- ?? ?±λ‘λ²?Έ ????€
CREATE SEQUENCE book_id_seq
     INCREMENT BY 1
     START WITH 1
     MINVALUE 1
     MAXVALUE 999999999
     CYCLE
     NOCACHE;

-- ?΄??€ ?¬?© ?΄?­ ??΄? ????€
CREATE SEQUENCE user_detail_id_seq
     INCREMENT BY 1
     START WITH 1
     MINVALUE 1
     MAXVALUE 99999999
     CYCLE
     NOCACHE;

-- ???¬ ??΄? ????€
CREATE SEQUENCE check_out_id_seq
     INCREMENT BY 1
     START WITH 1
     MINVALUE 1
     MAXVALUE 99999999
     CYCLE
     NOCACHE;
