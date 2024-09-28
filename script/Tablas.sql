CREATE TABLE usuario(
	us_idusuario INT PRIMARY KEY AUTO_INCREMENT,
    us_usuario VARCHAR(100) NOT NULL,
    us_password VARCHAR(100) NOT NULL,
    us_estado_registro TINYINT UNSIGNED NOT NULL DEFAULT 1,
    us_usuario_registro INT NULL,
    us_fecha_registro DATETIME NOT NULL DEFAULT NOW()
);

CREATE TABLE cdr_archivo(
	ac_idarchivo INT PRIMARY KEY AUTO_INCREMENT,
    ac_nombre VARCHAR(100) NOT NULL,
    ac_ruta VARCHAR(300) NOT NULL,
    ac_estado_registro TINYINT UNSIGNED NOT NULL DEFAULT 1,
    ac_usuario_registro INT NOT NULL,
    ac_fecha_registro DATETIME NOT NULL DEFAULT NOW(),
    UNIQUE KEY unique_file (ac_nombre, ac_ruta, ac_fecha_registro),
    FOREIGN KEY (ac_usuario_registro) REFERENCES usuario(us_idusuario)
);

CREATE TABLE cdr_llamada(
	ll_idllamada INT PRIMARY KEY AUTO_INCREMENT,
    ll_idarchivo INT NOT NULL,
    ll_cuenta INT NOT NULL,
    ll_emisor VARCHAR(10) NOT NULL,
    ll_receptor VARCHAR(10) NOT NULL,
    ll_fecha_hora DATETIME NOT NULL,
    ll_duracion NUMERIC(8,2) NULL,
    ll_tarifa NUMERIC(8,2) NULL,
    ll_categoria VARCHAR(50) NULL,
	ll_estado_registro TINYINT UNSIGNED NOT NULL DEFAULT 1,
    ll_usuario_registro INT NOT NULL,
    ll_fecha_registro DATETIME NOT NULL DEFAULT NOW(),
    FOREIGN KEY (ll_idarchivo) REFERENCES cdr_archivo(ac_idarchivo),
    FOREIGN KEY (ll_usuario_registro) REFERENCES usuario(us_idusuario)
);