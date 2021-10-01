CREATE TABLE usuario(
    id int PRIMARY KEY auto_increment,
    nome varchar (255),
    email varchar (255),
    senha varchar (255)
);

INSERT INTO usuario VALUES (1, 'Fillipi', 'fillipi@fillpi.com', 'fillipi123');
INSERT INTO usuario VALUES (2, 'Gabriel', 'Gabriel@gabriel.com', 'gabriel123');
INSERT INTO usuario VALUES (3, 'Higor', 'Higor@higor.com', 'higor123');