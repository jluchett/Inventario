package Modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class MovimientoModelo extends ConexionBD {

    // Método para agregar un movimiento a la base de datos
    public void agregarMovimiento(Movimiento movimiento) throws SQLException {
        PreparedStatement ps = null;
        Connection con = obtenerConexion();
        String consulta = "INSERT INTO movimientos (numeroMov, tipoMov, codigoProducto, cantidadProducto, "
                + "unidadProducto, numeroEstante, numeroFila, fechaMov, observaciones) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            ps = con.prepareStatement(consulta);
            ps.setInt(1, movimiento.getNumeroMov());
            ps.setString(2, movimiento.getTipoMov());
            ps.setInt(3, movimiento.getCodigoProducto());
            ps.setInt(4, movimiento.getCantidadProducto());
            ps.setString(5, movimiento.getUnidadProducto());
            ps.setInt(6, movimiento.getNumeroEstante());
            ps.setInt(7, movimiento.getNumeroFila());
            ps.setDate(8, new Date(movimiento.getFechaMov().getTime()));
            ps.setString(9, movimiento.getObservaciones());
            ps.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Movimiento guardado exitosamente");
        } catch (SQLException e) {
            // Manejo de la excepción: enviar mensaje de error o tomar acciones correctivas
            JOptionPane.showMessageDialog(null, "Error al ejecutar la operación en la base de datos " + e.getMessage());
            System.err.println("Error al ejecutar la operación en la base de datos: " + e.getMessage());
        } finally {
            // Cerrar la conexión para liberar recursos
            cerrarConexion(con);
        }
    }

    // Método para obtener todos los movimientos
    public List<Movimiento> obtenerTodosLosMovimientos() throws SQLException {
        List<Movimiento> movimientos = new ArrayList<>();
        String consulta = "SELECT * FROM movimientos";
        PreparedStatement ps = null;
        Connection con = obtenerConexion();

        try {
            ps = con.prepareStatement(consulta);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Movimiento movimiento = new Movimiento(
                            rs.getInt("numeroMov"),
                            rs.getString("tipoMov"),
                            rs.getInt("codigoProducto"),
                            rs.getInt("cantidadProducto"),
                            rs.getString("unidadProducto"),
                            rs.getInt("numeroEstante"),
                            rs.getInt("numeroFila"),
                            rs.getDate("fechaMov"),
                            rs.getString("observaciones")
                    );
                    movimientos.add(movimiento);
                }
            }
        } catch (SQLException e) {
            // Manejo de la excepción: enviar mensaje de error o tomar acciones correctivas
            System.err.println("Error al ejecutar la operación en la base de datos: " + e.getMessage());
        } finally {
            // Cerrar la conexión para liberar recursos
            cerrarConexion(con);
        }
        return movimientos;
    }

    // Método para obtener movimientos por tipo (ENTRADA o SALIDA)
    public List<Movimiento> obtenerMovimientosPorTipo(String tipo) throws SQLException {
        List<Movimiento> movimientos = new ArrayList<>();
        String consulta = "SELECT * FROM movimientos WHERE tipoMov = ?";
        PreparedStatement ps = null;
        Connection con = obtenerConexion();

        try {
            ps = con.prepareStatement(consulta);
            ps.setString(1, tipo);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Movimiento movimiento = new Movimiento(
                            rs.getInt("numeroMov"),
                            rs.getString("tipoMov"),
                            rs.getInt("codigoProducto"),
                            rs.getInt("cantidadProducto"),
                            rs.getString("unidadProducto"),
                            rs.getInt("numeroEstante"),
                            rs.getInt("numeroFila"),
                            rs.getDate("fechaMov"),
                            rs.getString("observaciones")
                    );
                    movimientos.add(movimiento);
                }
            }
        } catch (SQLException e) {
            // Manejo de la excepción: enviar mensaje de error o tomar acciones correctivas
            System.err.println("Error al ejecutar la operación en la base de datos: " + e.getMessage());
        } finally {
            // Cerrar la conexión para liberar recursos
            cerrarConexion(con);
        }
        return movimientos;
    }

    // Método para actualizar un movimiento por su número de movimiento
    // los movimientos no se pueden modificar

    // Método para eliminar un movimiento por su número de movimiento
    public void eliminarMovimiento(int numeroMov) throws SQLException {
        String consulta = "DELETE FROM movimientos WHERE numeroMov = ?";
        PreparedStatement ps = null;
        Connection con = obtenerConexion();
        try {
            ps = con.prepareStatement(consulta);
            ps.setInt(1, numeroMov);
            ps.executeUpdate();
        } catch (SQLException e) {
            // Manejo de la excepción: enviar mensaje de error o tomar acciones correctivas
            System.err.println("Error al ejecutar la operación en la base de datos: " + e.getMessage());
        } finally {
            // Cerrar la conexión para liberar recursos
            cerrarConexion(con);
        }
    }

    public int obteneNumero(String tipoMovimiento) throws SQLException {
        int ultimoNumero = 0;
        String consulta = "SELECT MAX(numeroMov) AS ultimoNumero FROM movimientos WHERE tipoMov = ?";
        PreparedStatement ps = null;
        Connection con = obtenerConexion();
        try {
            ps = con.prepareStatement(consulta);
            ps.setString(1, tipoMovimiento);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ultimoNumero = rs.getInt("ultimoNumero");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el último número de movimiento: " + e.getMessage());
            throw e; // Propagar la excepción para que sea manejada en un nivel superior
        } finally {
            // Cerrar la conexión para liberar recursos
            cerrarConexion(con);
        }
        return ultimoNumero;
    }
}
