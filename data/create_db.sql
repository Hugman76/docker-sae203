DROP DATABASE IF EXISTS menu;
CREATE DATABASE menu;

USE menu;

CREATE TABLE menu_items (
    id MEDIUMINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name CHAR(100),
    details VARCHAR(4000),
    price FLOAT
);

INSERT INTO menu_items (name, details, price) VALUES ('BBQ Bacon Burger', 'I love barbeque bbq burgers', 5.0);
INSERT INTO menu_items (name, details, price) VALUES ('Sandwich manese', 'stek, formage', 19.0);

CREATE USER 'sae'@'%' IDENTIFIED BY 'sae';
GRANT ALL PRIVILEGES ON *.* TO 'sae'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;