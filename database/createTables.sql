-- create table donor
create table donor(
id  int not null ,
firstname varchar(20),
lastname varchar(20),
dateb date,
phone varchar(20),
address varchar(20),
weight varchar(15),
bloodPressure varchar(5),
gender varchar(10),
bloodType varchar(10),
sufer_from_disease varchar(5),
bankName varchar(15),
primary key (id)
);
-- create table patient
create table patient (
id int not null,
firstname varchar(20),
lastname varchar(20),
dateb date,
phone varchar(20),
address varchar(20),
bloodPressure varchar(5),
bloodType varchar(10),
bankName varchar(15),
primary key (id)
);
-- create table bloodbank
CREATE TABLE bloodbank (
id_blood INT,
id_d INT,
bloodBank_name VARCHAR(15),
bloodType VARCHAR(5),
PRIMARY KEY (id_blood),
FOREIGN KEY (id_d) REFERENCES donor(id) ON DELETE SET NULL
);
-- create table bloodDeleviry
create table bloodDeleviry(
bloodBank_id INT,
id_p int ,
foreign key (id_p) references patient(id),
foreign key (bloodBank_id) references bloodbank(id_blood)
);
-- create table donate
create table donate(
id INT,
id_blood INT,
FOREIGN KEY (id) REFERENCES donor(id),
foreign key (id_blood) references bloodbank(id_blood)
);
-- create table register
create table register(
email varchar(50),
password varchar(20),
confirmPassword varchar(20),
role varchar(10)
);

delimiter $$
CREATE PROCEDURE searchPatient(IN idSearch INT) 
BEGIN 
SELECT * FROM patient WHERE id = idSearch;
END $$
delimiter ;



