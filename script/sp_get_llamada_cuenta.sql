CREATE DEFINER=`root`@`%` PROCEDURE `sp_get_llamada_cuenta`(
	IN p_cuenta INT,
    OUT p_total_tiempo DECIMAL(8,2),
    OUT p_total_tarifa DECIMAL(8,2),
    OUT p_total_tarifa_desc VARCHAR(100)
)
BEGIN

	 IF (SELECT COUNT(*)
        FROM information_schema.tables 
        WHERE table_schema = DATABASE() 
        AND table_name = 'ttp_cuentas') = 0 THEN
		
        CREATE TEMPORARY TABLE ttp_cuentas AS
		SELECT
			ll_cuenta as cuenta, ll_emisor as emisor, ll_receptor as receptor, ll_fecha_hora as fecha, ll_duracion as duracion, ll_tarifa as tarifa, ll_categoria as categoria, 
			ac_nombre as archivo
		FROM cdr_llamada ll
		INNER JOIN cdr_archivo a ON
			ll.ll_idarchivo = a.ac_idarchivo
		WHERE ll_cuenta = p_cuenta
		ORDER BY ll_fecha_hora DESC;
	ELSE
		INSERT INTO ttp_cuentas
		SELECT
			ll_cuenta , ll_emisor, ll_receptor, ll_fecha_hora, ll_duracion, ll_tarifa , ll_categoria, 
			ac_nombre
		FROM cdr_llamada ll
		INNER JOIN cdr_archivo a ON
			ll.ll_idarchivo = a.ac_idarchivo
		WHERE ll_cuenta = p_cuenta
		ORDER BY ll_fecha_hora DESC;
	END IF;
	
    
    SELECT 
		SUM(duracion), SUM(duracion * tarifa)
	INTO 
		p_total_tiempo, p_total_tarifa
    FROM ttp_cuentas;
    
    SET p_total_tarifa_desc = CONCAT('Tarifa Total Q', ROUND(p_total_tarifa, 2));
    
    SELECT * FROM ttp_cuentas;
    
    DROP TEMPORARY TABLE IF EXISTS ttp_cuentas;
    
END