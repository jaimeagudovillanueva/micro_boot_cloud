INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('jaime.agudo','1234',1, 'Jaime', 'Agudo','jaimeagudovillanueva@gmail.com');
INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('admin','1234',1, 'Cristina', 'Blanco','yavesyaveo@hotmail.com');

INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO roles_usuarios (usuario_id, rol_id) VALUES (1, 1);
INSERT INTO roles_usuarios (usuario_id, rol_id) VALUES (2, 2);
INSERT INTO roles_usuarios (usuario_id, rol_id) VALUES (2, 1);
