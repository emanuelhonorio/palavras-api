CREATE TABLE categoria(
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	created_at DATETIME NOT NULL,
	updated_at DATETIME NOT NULL,
	nome VARCHAR(100) NOT NULL
)Engine = InnoDB DEFAULT CHARSET = utf8;


CREATE TABLE palavra(
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	created_at DATETIME NOT NULL,
	updated_at DATETIME NOT NULL,
	dificuldade VARCHAR(30) NOT NULL,
	nome VARCHAR(100) NOT NULL,
	categoria_id BIGINT(20) NOT NULL,
	FOREIGN KEY(categoria_id) REFERENCES categoria(id)
)Engine = InnoDB DEFAULT CHARSET = utf8;