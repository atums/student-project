DROP TABLE IF EXISTS cr_address_person;
DROP TABLE IF EXISTS cr_person;
DROP TABLE IF EXISTS cr_address;
DROP TABLE IF EXISTS cr_street;
DROP TABLE IF EXISTS cr_district;

CREATE TABLE cr_district
(
    district_code int NOT NULL,
    district_name varchar(300),
    CONSTRAINT pk_cr_district_district_code PRIMARY KEY (district_code)
);

CREATE TABLE cr_street
(
    street_code int NOT NULL,
    street_name varchar(300),
    CONSTRAINT pk_cr_street_street_code PRIMARY KEY (street_code)
);

CREATE TABLE cr_address
(
    address_id    int GENERATED ALWAYS AS IDENTITY NOT NULL,
    district_code int                              NOT NULL,
    street_code   int                              NOT NULL,
    building      varchar(10)                      NOT NULL,
    extension     varchar(10),
    apartment     varchar(10)                      NOT NULL,
    CONSTRAINT pk_cr_address_address_code PRIMARY KEY (address_id),
    CONSTRAINT fk_cr_address_district_code FOREIGN KEY (district_code)
        REFERENCES cr_district (district_code) ON DELETE RESTRICT,
    CONSTRAINT fk_cr_address_street_code FOREIGN KEY (street_code)
        REFERENCES cr_street (street_code) ON DELETE RESTRICT
);

CREATE TABLE cr_person
(
    person_id          int GENERATED ALWAYS AS IDENTITY NOT NULL,
    sur_name           varchar(100)                     NOT NULL,
    given_name         varchar(100)                     NOT NULL,
    patronymic         varchar(100)                     NOT NULL,
    date_of_birth      date                             NOT NULL,
    passport_seria     varchar(10),
    passport_number    varchar(10),
    passport_date      date,
    certificate_number varchar(10),
    certificate_date   date,
    CONSTRAINT pk_cr_person_person_id PRIMARY KEY (person_id)
);

CREATE TABLE cr_address_person
(
    person_address_id int GENERATED ALWAYS AS IDENTITY NOT NULL,
    address_id        int                              NOT NULL,
    person_id         int                              NOT NULL,
    start_date        date                             NOT NULL,
    end_date          date,
    CONSTRAINT pk_cr_address_person_person_address_id PRIMARY KEY (person_address_id),
    CONSTRAINT fk_cr_address_person_address_id FOREIGN KEY (address_id)
        REFERENCES cr_address (address_id),
    CONSTRAINT fk_cr_address_person_person_id FOREIGN KEY (person_id)
        REFERENCES cr_person (person_id)
);


INSERT INTO cr_district(district_code, district_name) VALUES
(1, 'Vancouver');

INSERT INTO cr_street(street_code, street_name) VALUES
(1, 'Bellevue Ave');

INSERT INTO cr_address(district_code, street_code, building, extension, apartment) VALUES
(1, 1, '2842', '', '1');

INSERT INTO cr_person(sur_name, given_name, patronymic, date_of_birth, passport_seria, passport_number,
                      passport_date, certificate_number, certificate_date) VALUES
('Pyshniak', 'Aleksandr', ' ', '1978-01-27', '4004', '427593', '2003-06-01', null, null),
('Pyshniak', 'Marina', ' ', '1976-04-24', '4002', '123456', '2022-04-01', null, null),
('Pyshniak', 'Nika', ' ', '2020-03-05', null, null, null, '11121314', '2020-08-01'),
('Pyshniak', 'Max', ' ', '2024-12-13', null, null, null, '41312111', '2025-01-21');

INSERT INTO cr_address_person (address_id, person_id, start_date, end_date) VALUES
(1, 1, '2024-06-01', null),
(1, 2, '2024-06-01', null),
(1, 3, '2024-06-01', null),
(1, 4, '2025-01-22', null);