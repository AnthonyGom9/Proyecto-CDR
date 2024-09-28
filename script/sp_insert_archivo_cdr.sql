CREATE DEFINER=`root`@`%` PROCEDURE `sp_insert_archivo_cdr`(
	IN p_nombre_archivo VARCHAR(100),
    IN p_ruta VARCHAR(300),
    IN p_fecha VARCHAR(50),
    IN p_usuario INT,
	IN p_cuenta INT,
    IN p_emisor VARCHAR(10), 
    IN p_receptor VARCHAR(10), 
    IN p_fecha_llamada VARCHAR(50),
	IN p_duracion DECIMAL(8,2), 
    IN p_tarifa  DECIMAL(8,2), 
    IN p_categoria VARCHAR(50), 
    OUT p_id_llamada INT
)
BEGIN
	DECLARE v_id INT;
    DECLARE v_resultado_archivo INT;
    DECLARE v_fecha DATETIME;
    DECLARE v_fecha_llamada DATETIME;
    
	/*SET v_fecha = STR_TO_DATE(p_fecha, '%d-%m-%Y %H:%i:%s'); 
    SET v_fecha_llamada = STR_TO_DATE(p_fecha_llamada, '%d-%m-%Y %H:%i:%s'); */
    SET v_fecha = p_fecha; 
    SET v_fecha_llamada = p_fecha_llamada;
    
	SELECT ac_idarchivo INTO v_id
    FROM cdr_archivo 
    WHERE ac_nombre = p_nombre_archivo 
      AND ac_ruta = p_ruta
      AND ac_fecha_registro = v_fecha
	LIMIT 1;
    
    IF v_id IS NULL THEN
    
        INSERT IGNORE INTO cdr_archivo (
			ac_nombre, ac_ruta, ac_estado_registro, 
            ac_usuario_registro, ac_fecha_registro
        )VALUES (
			p_nombre_archivo, p_ruta, 1, 
            p_usuario, v_fecha
		);
        
        COMMIT;
        
		SET v_resultado_archivo = (SELECT ROW_COUNT());
       
		IF v_resultado_archivo = 0 THEN
			SELECT ac_idarchivo INTO v_id
			FROM cdr_archivo 
			WHERE ac_nombre = p_nombre_archivo 
			  AND ac_ruta = p_ruta
			  AND ac_fecha_registro = v_fecha
			LIMIT 1;
        ELSE
			 SET v_id = LAST_INSERT_ID();  -- Obtener el ID del nuevo registro insertado
        END IF;
       
    END IF;
    
	IF v_id IS NOT NULL AND v_id > 0 THEN
		
        INSERT INTO cdr_llamada(
			ll_idarchivo, ll_cuenta, ll_emisor, ll_receptor, ll_fecha_hora,
			ll_duracion, ll_tarifa, ll_categoria, ll_estado_registro, 
			ll_usuario_registro, ll_fecha_registro
		)VALUES(
			v_id, p_cuenta, p_emisor, p_receptor, v_fecha_llamada,
			p_duracion, p_tarifa, p_categoria, 1,
            p_usuario, NOW()
		);
		
		IF ROW_COUNT() > 0 THEN
			SET p_id_llamada = LAST_INSERT_ID();
		END IF;
    
	END IF;
    
END