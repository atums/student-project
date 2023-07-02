DROP TABLE IF EXISTS jc_student_child;
DROP TABLE IF EXISTS jc_student_order;
DROP TABLE IF EXISTS jc_register_office;
DROP TABLE IF EXISTS jc_passport_office;
DROP TABLE IF EXISTS jc_country_struct;
DROP TABLE IF EXISTS jc_university;
DROP TABLE IF EXISTS jc_street;

CREATE TABLE jc_street(
	street_code int NOT NULL,
	street_name varchar(300),
	CONSTRAINT pk_jc_address_street_code PRIMARY KEY(street_code)
);

CREATE TABLE jc_university(
	university_id int NOT NULL,
	university_name varchar(300),
	CONSTRAINT pk_jc_university_university_id PRIMARY KEY(university_id)
);

CREATE TABLE jc_country_struct(
	area_id char(12) NOT NULL,
	area_name varchar(200),
	CONSTRAINT pk_jc_country_struct_area_id PRIMARY KEY(area_id)
);

CREATE TABLE jc_passport_office(
	p_office_id int NOT NULL,
	p_office_area_id char(12) NOT NULL,
	p_office_name varchar(200),
	CONSTRAINT pk_jc_passport_office_p_office_id PRIMARY KEY(p_office_id),
	CONSTRAINT fk_jc_passport_office_p_office_area_id FOREIGN KEY(p_office_area_id) 
	REFERENCES jc_country_struct(area_id) ON DELETE RESTRICT
);

CREATE TABLE jc_register_office(
	r_office_id int NOT NULL,
	r_office_area_id char(12) NOT NULL,
	r_office_name varchar(200),
	CONSTRAINT pk_jc_register_office_r_office_id PRIMARY KEY(r_office_id),
	CONSTRAINT fk_jc_register_office_r_office_area_id FOREIGN KEY(r_office_area_id) 
	REFERENCES jc_country_struct(area_id) ON DELETE RESTRICT
);

CREATE TABLE jc_student_order(
	student_order_id int GENERATED ALWAYS AS IDENTITY NOT NULL,
	student_order_status int NOT NULL,
	student_order_date timestamp NOT NULL,
	h_sur_name varchar(100) NOT NULL,
	h_given_name varchar(100) NOT NULL,
	h_patronymic varchar(100) NOT NULL,
	h_date_of_birth date NOT NULL,
	h_passport_seria varchar(10) NOT NULL,
	h_passport_number varchar(10) NOT NULL,
	h_passport_date date NOT NULL,
	h_passport_offece_id int NOT NULL, --FK
	h_post_index varchar(10),
	h_street_code int NOT NULL, --FK
	h_building varchar(10) NOT NULL,
	h_extension varchar(10),
	h_apartment varchar(10) NOT NULL,
	h_university_id int NOT NULL, --FK
	h_student_number varchar(30) NOT NULL,
	w_sur_name varchar(100) NOT NULL,
	w_given_name varchar(100) NOT NULL,
	w_patronymic varchar(100) NOT NULL,
	w_date_of_birth date NOT NULL,
	w_passport_seria varchar(10) NOT NULL,
	w_passport_number varchar(10) NOT NULL,
	w_passport_date date NOT NULL,
	w_passport_offece_id int NOT NULL, --FK
	w_post_index varchar(10),
	w_street_code int NOT NULL, --FK
	w_building varchar(10) NOT NULL,
	w_extension varchar(10),
	w_apartment varchar(10) NOT NULL,
	w_university_id int NOT NULL,
	w_student_number varchar(30) NOT NULL,
	certificate_id varchar(20) NOT NULL,
	register_office_id int NOT NULL,-- FK
	marriage_date date NOT NULL,
	CONSTRAINT pk_jc_student_order_student_order_id 
	PRIMARY KEY(student_order_id),
	CONSTRAINT fk_jc_student_order_h_street_code FOREIGN KEY(h_street_code) 
	REFERENCES jc_street(street_code) ON DELETE RESTRICT,
	CONSTRAINT fk_jc_student_order_w_street_code FOREIGN KEY(w_street_code) 
	REFERENCES jc_street(street_code) ON DELETE RESTRICT,
	CONSTRAINT fk_jc_student_order_register_office_id FOREIGN KEY(register_office_id) 
	REFERENCES jc_register_office(r_office_id) ON DELETE RESTRICT,
	CONSTRAINT fk_jc_student_order_h_passport_offece_id FOREIGN KEY(h_passport_offece_id) 
	REFERENCES jc_passport_office(p_office_id) ON DELETE RESTRICT,
	CONSTRAINT fk_jc_student_order_w_passport_offece_id FOREIGN KEY(w_passport_offece_id) 
	REFERENCES jc_passport_office(p_office_id) ON DELETE RESTRICT,	
	CONSTRAINT fk_jc_student_order_h_university_id FOREIGN KEY(h_university_id) 
	REFERENCES jc_university(university_id) ON DELETE RESTRICT,
	CONSTRAINT fk_jc_student_order_w_university_id FOREIGN KEY(w_university_id) 
	REFERENCES jc_university(university_id) ON DELETE RESTRICT
);

CREATE TABLE jc_student_child(
	student_child_id int GENERATED ALWAYS AS IDENTITY NOT NULL,
	student_order_id int NOT NULL, --FK
	c_sur_name varchar(100) NOT NULL,
	c_given_name varchar(100) NOT NULL,
	c_patronymic varchar(100) NOT NULL,
	c_date_of_birth date NOT NULL,
	c_certificate_number varchar(10) NOT NULL,
	c_certificate_date date NOT NULL,
	c_register_offece_id int NOT NULL, --FK
	c_post_index varchar(10),
	c_street_code int NOT NULL, --FK
	c_building varchar(10) NOT NULL,
	c_extension varchar(10),
	c_apartment varchar(10) NOT NULL,
	CONSTRAINT pk_jc_student_child_student_child_id 
	PRIMARY KEY(student_child_id),
	CONSTRAINT fk_jc_student_child_student_order_id FOREIGN KEY(student_order_id) 
	REFERENCES jc_student_order(student_order_id) ON DELETE RESTRICT,
	CONSTRAINT fk_jc_student_child_c_street_code FOREIGN KEY(c_street_code) 
	REFERENCES jc_street(street_code) ON DELETE RESTRICT,
	CONSTRAINT fk_jc_student_child_c_register_office_id FOREIGN KEY(c_register_offece_id) 
	REFERENCES jc_register_office(r_office_id) ON DELETE RESTRICT
);

CREATE INDEX idx_student_order_status ON jc_student_order(student_order_status);

CREATE INDEX idx_student_order_id ON jc_student_child(student_order_id);

 -- 
 
INSERT INTO jc_street(street_code, street_name) VALUES
(1, 'улица Садовая'),
(2, 'Невский проспект'),
(3, 'улица Стахановцев'),
(4, 'улица Гороховая'),
(5, 'Проспект Ветеранов');

INSERT INTO jc_university(university_id, university_name) VALUES
(1, 'СПБГУ'),
(2, 'MIT'),
(3, 'Горный университет');

INSERT INTO jc_country_struct(area_id, area_name) VALUES
('010000000000', 'Город'),
('010010000000', 'Город Район 1'),
('010020000000', 'Город Район 2'),
('010030000000', 'Город Район 3'),
('010040000000', 'Город Район 4'),
('020000000000', 'Край'),
('020010000000', 'Край Область 1'),
('020010010000', 'Край Область 1 Район 1'),
('020010010001', 'Край Область 1 Район 1 Поселение 1'),
('020010010002', 'Край Область 1 Район 1 Поселение 2'),
('020010020000', 'Край Область 1 Район 2'),
('020010020001', 'Край Область 1 Район 2 Поселение 1'),
('020010020002', 'Край Область 1 Район 2 Поселение 2'),
('020010020003', 'Край Область 1 Район 2 Поселение 2'),
('020020000000', 'Край Область 2'),
('020020010000', 'Край Область 2 Район 1'),
('020020010001', 'Край Область 2 Район 1 Поселение 1'),
('020020010002', 'Край Область 2 Район 1 Поселение 2'),
('020020010003', 'Край Область 2 Район 1 Поселение 3'),
('020020020000', 'Край Область 2 Район 2'),
('020020020001', 'Край Область 2 Район 2 Поселение 1'),
('020020020002', 'Край Область 2 Район 2 Поселение 2');

INSERT INTO jc_passport_office(p_office_id, p_office_area_id, p_office_name) VALUES
(1, '010010000000', 'Пасспортный стол района 1 города'),
(2, '010020000000', 'Пасспортный стол 1 района 2 города'),
(3, '010020000000', 'Пасспортный стол 2 района 2 города'),
(4, '010010000000', 'Пасспортный стол района 3 города'),
(5, '020010010001', 'Пасспортный стол Область 1 поселение 1'),
(6, '020010010002', 'Пасспортный стол Область 1 поселение 2'),
(7, '020020010000', 'Пасспортный стол Область 1 район 1'),
(8, '020020020000', 'Пасспортный стол Область 1 район 2');

INSERT INTO jc_register_office(r_office_id, r_office_area_id, r_office_name) VALUES
(1, '010010000000', 'ЗАГС 1 района 1 города'),
(2, '010010000000', 'ЗАГС 2 района 1 города'),
(3, '010020000000', 'ЗАГС района 2 города'),
(4, '020010010001', 'ЗАГС Область 1 поселение 1'),
(5, '020010020002', 'ЗАГС Область 1 поселение 2'),
(6, '020020010000', 'ЗАГС Область 2 район 1'),
(7, '020020020000', 'ЗАГС Область 2 район 2');

