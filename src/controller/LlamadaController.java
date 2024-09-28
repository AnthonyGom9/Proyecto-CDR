package controller;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.Types;
import java.sql.SQLException;
import java.sql.ResultSet;
import models.LlamadaModel;
import models.ReporteModel;

/**
 *
 * @author KebFelipe
 */
public class LlamadaController {

    private CallableStatement callableStmt;
    private Connection conn;

    public LlamadaController() {
    }

    public void insertarLlamada(String nombre_archivo, String ruta, String fecha_archivo, int cuenta, String emisor, String receptor, String fecha_llamada, float duracion, float tarifa, String categoria) {
        try {
            Connection conn = new Conn().conectar();
            String sql = "{CALL sp_insert_archivo_cdr(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            callableStmt = conn.prepareCall(sql);
            callableStmt.setString(1, nombre_archivo);
            callableStmt.setString(2, ruta);
            callableStmt.setString(3, fecha_archivo);
            callableStmt.setInt(4, 1);
            callableStmt.setInt(5, cuenta);
            callableStmt.setString(6, emisor);
            callableStmt.setString(7, receptor);
            callableStmt.setString(8, fecha_llamada);
            callableStmt.setFloat(9, duracion);
            callableStmt.setFloat(10, tarifa);
            callableStmt.setString(11, categoria);
            callableStmt.registerOutParameter(12, Types.INTEGER);
            callableStmt.execute();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (callableStmt != null) {
                    callableStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }

    public ReporteModel getLlamadaCuenta(String cuenta) {
        ReporteModel reporte = new ReporteModel();
        ResultSet result = null;
        try {
            Connection conn = new Conn().conectar();
            String sql = "{CALL sp_get_llamada_cuenta(?, ?, ?, ?)}";
            callableStmt = conn.prepareCall(sql);
            callableStmt.setString(1, cuenta);
            callableStmt.registerOutParameter(2, Types.DECIMAL);
            callableStmt.registerOutParameter(3, Types.DECIMAL);
            callableStmt.registerOutParameter(4, Types.VARCHAR);
            result = callableStmt.executeQuery();
            
            reporte.setTotal_duracion(callableStmt.getFloat(2));
            reporte.setTotal_tarifa(callableStmt.getFloat(3));
            reporte.setDescripcion_tarifa(callableStmt.getString(4));
            
            while(result.next()){
                reporte.getLlamadas().add(new LlamadaModel(
                        result.getInt("cuenta"),
                        result.getString("emisor"),
                        result.getString("receptor"),
                        result.getString("fecha"),
                        result.getFloat("duracion"),
                        result.getFloat("tarifa"),
                        result.getString("categoria"),
                        result.getString("archivo")
                ));
            }
            
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return reporte;
        } finally {
            try {
                if (callableStmt != null) {
                    callableStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex);
                return reporte;
            }
            return reporte;
        }
    }
}
