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
	h_passport_offece_id int NOT NULL,
	h_post_index varchar(10),
	h_street_code int NOT NULL,
	h_building varchar(10) NOT NULL,
	h_extension varchar(10),
	h_apartment varchar(10) NOT NULL,
	h_university_id int NOT NULL,
	h_student_number varchar(30) NOT NULL,
	w_sur_name varchar(100) NOT NULL,
	w_given_name varchar(100) NOT NULL,
	w_patronymic varchar(100) NOT NULL,
	w_date_of_birth date NOT NULL,
	w_passport_seria varchar(10) NOT NULL,
	w_passport_number varchar(10) NOT NULL,
	w_passport_date date NOT NULL,
	w_passport_offece_id int NOT NULL,
	w_post_index varchar(10),
	w_street_code int NOT NULL,
	w_building varchar(10) NOT NULL,
	w_extension varchar(10),
	w_apartment varchar(10) NOT NULL,
	w_university_id int NOT NULL,
	w_student_number varchar(30) NOT NULL,
	certificate_id varchar(20) NOT NULL,
	register_office_id int NOT NULL,
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
	student_order_id int NOT NULL,
	c_sur_name varchar(100) NOT NULL,
	c_given_name varchar(100) NOT NULL,
	c_patronymic varchar(100) NOT NULL,
	c_date_of_birth date NOT NULL,
	c_certificate_number varchar(10) NOT NULL,
	c_certificate_date date NOT NULL,
	c_register_offece_id int NOT NULL,
	c_post_index varchar(10),
	c_street_code int NOT NULL,
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