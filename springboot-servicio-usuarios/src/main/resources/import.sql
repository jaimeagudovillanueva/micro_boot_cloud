INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('jaime.agudo','$2a$10$ykhXmCAam5FUEF9GN.4Z8OwwWJidvMii6VFYe77cmS2X6oF6p4W86', true, 'Jaime', 'Agudo','jaimeagudovillanueva@gmail.com');
INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('admin','$2a$10$qGyDfZLBB.SgLv7GCP3uZe3oM38fVtr58T1iZ1LNOvO61loNUAAaK', true, 'Cristina', 'Blanco','yavesyaveo@hotmail.com');

INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO roles_usuarios (usuario_id, rol_id) VALUES (1, 1);
INSERT INTO roles_usuarios (usuario_id, rol_id) VALUES (2, 2);
INSERT INTO roles_usuarios (usuario_id, rol_id) VALUES (2, 1);
