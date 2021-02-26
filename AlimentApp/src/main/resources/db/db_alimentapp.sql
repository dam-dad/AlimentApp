CREATE TABLE IF NOT EXISTS profile (
  id int(11) AUTO_INCREMENT NOT NULL,
  name_profile varchar(50)  NOT NULL,
  name varchar(50)  NOT NULL,
  surname varchar(100)  NOT NULL,
  age int(3) NOT NULL,
  weight int(3) NOT NULL,
  height int(3) NOT NULL,
  imc DECIMAL(5,2),
  gender int(11) NOT NULL,
  image_profile text  NULL,
  PRIMARY KEY (id),
  UNIQUE KEY profile_name_unique (name_profile)
) ;

CREATE TABLE IF NOT EXISTS diets (
  id int(11) AUTO_INCREMENT NOT NULL,
  name varchar(50)  NOT NULL,
  profile_id int(11) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_diets_profile FOREIGN KEY (profile_id) REFERENCES profile (id)
) ;

CREATE TABLE IF NOT EXISTS menu (
  id int(11) AUTO_INCREMENT NOT NULL,
  name varchar(50)  NOT NULL,
  profile_id int(11) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_menu_profile FOREIGN KEY (profile_id) REFERENCES profile (id)
) ;

CREATE TABLE IF NOT EXISTS diets_menus (
  id_diets int(11) NOT NULL,
  id_menu int(11) NOT NULL,
  id_weekday int(11) NOT NULL,
  PRIMARY KEY (id_diets, id_menu, id_weekday),
  CONSTRAINT fk_diets_menu_id_diets FOREIGN KEY (id_diets) REFERENCES diets (id),
  CONSTRAINT fk_diets_menu_id_menu FOREIGN KEY (id_menu) REFERENCES menu (id)
) ;

CREATE TABLE IF NOT EXISTS origin (
  id int(11) AUTO_INCREMENT NOT NULL,
  name varchar(50)  NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY origin_name_unique (name)
) ;

CREATE TABLE IF NOT EXISTS type (
  id int(11) AUTO_INCREMENT NOT NULL,
  name varchar(50)  NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY type_name_unique (name)
) ;

CREATE TABLE IF NOT EXISTS product (
  id int(11) AUTO_INCREMENT NOT NULL,
  nombre varchar(50)  NOT NULL,
  kcal int(4) NOT NULL,
  hydrates int(4) NOT NULL,
  fats int(4) NOT NULL,
  protein int(4) NOT NULL,
  fibres int(4) NOT NULL,
  id_origin int(11) NOT NULL,
  id_type int(11) NOT NULL,
  image text  NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY product_name_unique (nombre),
  CONSTRAINT fk_product_id_origin FOREIGN KEY (id_origin) REFERENCES origin (id),
  CONSTRAINT fk_product_id_type FOREIGN KEY (id_type) REFERENCES type (id)
) ;

CREATE TABLE IF NOT EXISTS menu_product (
  id_menu int(11) NOT NULL,
  id_product int(11) NOT NULL,
  id_moment_day int(11) NOT NULL,
  PRIMARY KEY (id_menu, id_product, id_moment_day),
  CONSTRAINT fk_menu_product_id_menu FOREIGN KEY (id_menu) REFERENCES menu (id),
  CONSTRAINT fk_menu_product_id_product FOREIGN KEY (id_product) REFERENCES product (id)
) ;

INSERT INTO origin (name) VALUES ('Vegetal');
INSERT INTO origin (name) VALUES ('Animal');
INSERT INTO origin (name) VALUES ('Mineral');

INSERT INTO type (name) VALUES ('Frutas');
INSERT INTO type (name) VALUES ('Verduras');
INSERT INTO type (name) VALUES ('Hidratos');
INSERT INTO type (name) VALUES ('Proteicos');
INSERT INTO type (name) VALUES ('Grasas');
INSERT INTO type (name) VALUES ('Lacteos');

INSERT INTO profile (name_profile, name, surname, age, weight, height, imc, gender) VALUES
('User_Profile','User','Test', 21, 70, 170, 24.0, 1);

INSERT INTO diets (name, profile_id) VALUES
('Dieta inicial', 1);

INSERT INTO menu (name, profile_id) VALUES
('Menú Lunes', 1);

INSERT INTO diets_menus (id_diets, id_menu, id_weekday) VALUES
(1, 1, 1);

INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Leche con miel', '131', '14', '5', '5', '0', '2', '6', 'https://alimentapp.diginalia.com/images/1.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Tortilla francesa', '104', '1', '8', '7', '0', '2', '4', 'https://alimentapp.diginalia.com/images/2.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Kiwi', '61', '15', '1', '1', '3', '1', '1', 'https://alimentapp.diginalia.com/images/3.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Plátano', '89', '23', '0', '1', '3', '1', '1', 'https://alimentapp.diginalia.com/images/4.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Tostadas con jamón', '179', '15', '5', '9', '2', '2', '4', 'https://alimentapp.diginalia.com/images/5.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Yogur con avena', '138', '17', '5', '5', '2', '2', '3', 'https://alimentapp.diginalia.com/images/6.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Yogur con frutas', '97', '15', '3', '3', '0', '2', '6', 'https://alimentapp.diginalia.com/images/7.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Sandwich pavo', '277', '29', '7', '13', '4', '2', '3', 'https://alimentapp.diginalia.com/images/8.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Sandwich vegetal', '148', '11', '7', '6', '7', '2', '3', 'https://alimentapp.diginalia.com/images/9.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Tostadas con queso de untar', '90', '8', '82', '14', '0', '2', '5', 'https://alimentapp.diginalia.com/images/10.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Requesón con miel y nueces', '376', '26', '25', '13', '1', '2', '4', 'https://alimentapp.diginalia.com/images/11.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Frutos secos', '500', '4', '9', '4', '2', '1', '3', 'https://alimentapp.diginalia.com/images/12.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Tortitas de arroz', '112', '23', '1', '3', '1', '1', '3', 'https://alimentapp.diginalia.com/images/13.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Manzana', '72', '19', '0', '0', '3', '1', '1', 'https://alimentapp.diginalia.com/images/14.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Piña', '74', '20', '0', '1', '2', '1', '1', 'https://alimentapp.diginalia.com/images/15.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Pera', '96', '26', '0', '1', '5', '1', '1', 'https://alimentapp.diginalia.com/images/16.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Yogur natural', '63', '7', '2', '5', '0', '2', '6', 'https://alimentapp.diginalia.com/images/17.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Frutas desecadas', '359', '86', '3', '1', '0', '1', '1', 'https://alimentapp.diginalia.com/images/18.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Zumo de naranja', '112', '26', '1', '2', '1', '1', '1', 'https://alimentapp.diginalia.com/images/19.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Arroz', '204', '44', '0', '4', '1', '2', '3', 'https://alimentapp.diginalia.com/images/20.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Ensalada', '135', '6', '12', '4', '2', '1', '2', 'https://alimentapp.diginalia.com/images/21.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Atún', '127', '0', '1', '28', '0', '2', '4', 'https://alimentapp.diginalia.com/images/22.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Salmón a la plancha', '191', '0', '12', '21', '0', '2', '4', 'https://alimentapp.diginalia.com/images/23.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Sardinas', '213', '0', '15', '19', '0', '2', '4', 'https://alimentapp.diginalia.com/images/24.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Pasta integral', '334', '65', '2', '11', '6', '1', '3', 'https://alimentapp.diginalia.com/images/25.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Pollo a la plancha', '111', '0', '2', '22', '0', '2', '4', 'https://alimentapp.diginalia.com/images/26.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Pechuga de pavo', '104', '4', '2', '18', '1', '2', '4', 'https://alimentapp.diginalia.com/images/27.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Ternera a la plancha', '109', '0', '3', '20', '0', '2', '4', 'https://alimentapp.diginalia.com/images/28.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Yogur con almendras', '86', '8', '5', '2', '0', '2', '6', 'https://alimentapp.diginalia.com/images/29.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Tostadas con aguacate', '149', '19', '6', '6', '7', '1', '3', 'https://alimentapp.diginalia.com/images/30.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Brochetas de fruta', '72', '18', '1', '1', '3', '1', '1', 'https://alimentapp.diginalia.com/images/31.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Huevos duros', '78', '1', '5', '6', '0', '2', '4', 'https://alimentapp.diginalia.com/images/32.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Leche con cereales', '244', '34', '8', '9', '1', '1', '6', 'https://alimentapp.diginalia.com/images/33.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Jamón serrano', '221', '0', '12', '28', '0', '2', '4', 'https://alimentapp.diginalia.com/images/34.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Sopa de pescado', '290', '5', '19', '24', '2', '2', '3', 'https://alimentapp.diginalia.com/images/35.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Potaje de berros', '269', '27', '12', '12', '6', '1', '2', 'https://alimentapp.diginalia.com/images/36.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Kefir', '52', '7', '1', '4', '0', '2', '6', 'https://alimentapp.diginalia.com/images/37.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Sandwich de tortilla francesa', '277', '24', '13', '15', '3', '2', '4', 'https://alimentapp.diginalia.com/images/38.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Pechuga de pollo al limón', '161', '0', '4', '29', '0', '2', '4', 'https://alimentapp.diginalia.com/images/39.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Tortilla de papas', '300', '12', '3', '6', '0', '2', '4', 'https://alimentapp.diginalia.com/images/40.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Judías verdes', '31', '7', '0', '2', '3', '1', '2', 'https://alimentapp.diginalia.com/images/41.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Salmón al vapor', '191', '0', '12', '20', '0', '2', '4', 'https://alimentapp.diginalia.com/images/42.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Guisantes', '81', '14', '0', '5', '5', '1', '2', 'https://alimentapp.diginalia.com/images/43.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Calamares', '175', '8', '7', '18', '0', '2', '4', 'https://alimentapp.diginalia.com/images/44.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Crema de verduras', '64', '6', '3', '2', '0', '1', '2', 'https://alimentapp.diginalia.com/images/45.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Crema de calabaza', '109', '11', '7', '3', '3', '1', '2', 'https://alimentapp.diginalia.com/images/46.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Lasaña vegetal', '123', '12', '6', '4', '2', '1', '2', 'https://alimentapp.diginalia.com/images/47.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Conejo asado', '39', '0', '2', '6', '0', '2', '4', 'https://alimentapp.diginalia.com/images/48.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Potaje de lentejas', '73', '7', '2', '4', '4', '1', '4', 'https://alimentapp.diginalia.com/images/49.jpg');
INSERT INTO product (nombre, kcal, hydrates, fats, protein, fibres, id_origin, id_type, image) VALUES ('Spaguetti de calabacín', '23', '2', '1', '2', '0', '1', '2', 'https://alimentapp.diginalia.com/images/50.jpg');

INSERT INTO menu_product (id_menu, id_product, id_moment_day) VALUES
(1, 1, 1),
(1, 4, 3);