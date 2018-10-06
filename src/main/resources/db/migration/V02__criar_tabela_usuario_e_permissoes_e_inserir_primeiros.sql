CREATE TABLE usuario (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	created_at DATETIME NOT NULL,
	updated_at DATETIME NOT NULL,
    email VARCHAR(255) NOT NULL,
    username VARCHAR(25) NOT NULL,
    senha VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# NAO E AUTO_INCREMENT
CREATE TABLE permissao (
	id BIGINT(20) PRIMARY KEY,
    descricao VARCHAR(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE usuario_permissao (
	usuario_id BIGINT(20) NOT NULL,
	permissao_id BIGINT(20) NOT NULL,
    FOREIGN KEY(usuario_id) REFERENCES usuario(id),
    FOREIGN KEY(permissao_id) REFERENCES permissao(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

## INSERIR PRIMEIRAS PERMISSOES
INSERT INTO permissao (id, descricao) VALUES (1, 'ROLE_READ_PALAVRA');
INSERT INTO permissao (id, descricao) VALUES (2, 'ROLE_WRITE_PALAVRA');
INSERT INTO permissao (id, descricao) VALUES (3, 'ROLE_READ_CATEGORIA');
INSERT INTO permissao (id, descricao) VALUES (4, 'ROLE_WRITE_CATEGORIA');

## INSERIR PRIMEIROS USUARIOS
# ADMIN
INSERT INTO usuario (id, created_at, updated_at, email, username, senha)
VALUES (1, NOW(), NOW(), 'emanuelh.dev@gmail.com', 'maninho2010', '$2a$10$csIH2LT1yl2.6/A4htNVVuw4V.ET/4wcx6NvglNK7zp0I4aQ0.i2a');
# USER
INSERT INTO usuario (id, created_at, updated_at, email, username, senha)
VALUES (2, NOW(), NOW(), 'emanueljp2014@gmail.com', 'maninho', '$2a$10$0mmCuBldgHlwQdvfQ5spyu2ZXlGjxf5wSPphYlsVH88yCgv0Ll0eu');

## PERMISSOES ADMIN
INSERT INTO usuario_permissao(usuario_id, permissao_id) VALUES (1, 1);
INSERT INTO usuario_permissao(usuario_id, permissao_id) VALUES (1, 2);
INSERT INTO usuario_permissao(usuario_id, permissao_id) VALUES (1, 3);
INSERT INTO usuario_permissao(usuario_id, permissao_id) VALUES (1, 4);

## PERMISSOES USER
INSERT INTO usuario_permissao(usuario_id, permissao_id) VALUES (2, 1);
INSERT INTO usuario_permissao(usuario_id, permissao_id) VALUES (2, 3);