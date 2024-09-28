package controller;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.Types;
import java.sql.SQLException;

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
}
