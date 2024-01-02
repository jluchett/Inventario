
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    // Configura tus credenciales y detalles de la base de datos
    private static final String URL = "jdbc:postgresql://localhost:5432/inventario2";
    private static final String USUARIO = "postgres";
    private static final String CONTRASENA = "12345";

    public static Connection obtenerConexion() throws SQLException {
        try {
            // Registra el driver de PostgreSQL
            Class.forName("org.postgresql.Driver");
            
            // Establece la conexión
            return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        } catch (ClassNotFoundException | SQLException ex) {
            throw new SQLException("Error al conectar a la base de datos: " + ex.getMessage());
        }
    }
    
    public static void cerrarConexion(Connection conexion) {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
