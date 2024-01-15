package Modelo;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductoModelo extends ConexionBD {

    public boolean AddProducto(Producto prod) throws SQLException {
        PreparedStatement ps = null;
        Connection con = obtenerConexion();
        String sql = "INSERT INTO productos (codigo, descripcion) VALUES (?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, prod.getCodigo());
            ps.setString(2, prod.getDescripcion());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al agregar a la base de datos: " + e.getMessage());
            return false;
        } finally {
            cerrarConexion(con);
        }
    }

    public boolean editProducto(Producto prod) throws SQLException {
        PreparedStatement ps = null;
        Connection con = obtenerConexion();
        String sql = "UPDATE productos SET descripcion=? WHERE codigo=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, prod.getDescripcion());
            ps.setInt(2, prod.getCodigo());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al modificar en la base de datos: " + e.getMessage());
            return false;
        } finally {
            cerrarConexion(con);
        }
    }

    public boolean delProduucto(Producto prod) throws SQLException {
        PreparedStatement ps = null;
        Connection con = obtenerConexion();
        String sql = "DELETE FROM productos WHERE codigo=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, prod.getCodigo());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar en la base de datos: " + e.getMessage());
            return false;
        } finally {
            cerrarConexion(con);
        }
    }

    public boolean searchProd(Producto prod) throws SQLException {
        PreparedStatement ps = null;
        Connection con = obtenerConexion();
        ResultSet rs = null;
        String sql = "SELECT * FROM productos WHERE codigo=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, prod.getCodigo());

            rs = ps.executeQuery();
            if (rs.next()) {
                prod.setCodigo(Integer.parseInt(rs.getString("codigo")));
                prod.setDescripcion(rs.getString("descripcion"));
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error al consultar en la base de datos: " + e.getMessage());
            return false;
        } finally {
            cerrarConexion(con);
        }
    }

    public List<Producto> obtenerTodosLosProductos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String consulta = "SELECT * FROM productos";
        Connection con = obtenerConexion();
        try (
                 PreparedStatement ps = con.prepareStatement(consulta);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Producto producto = new Producto(
                        rs.getInt("codigo"),
                        rs.getString("descripcion")
                );
                productos.add(producto);
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar en la base de datos: " + e.getMessage());
        } finally {
            cerrarConexion(con);
        }
        return productos;
    }

    public List<Producto> buscarProductosPorDescripcion(String descripcion) throws SQLException {
        List<Producto> productosEncontrados = new ArrayList<>();
        String consulta = "SELECT * FROM productos WHERE LOWER(descripcion) LIKE LOWER(?)";
        Connection con = obtenerConexion();
        try (
                 PreparedStatement ps = con.prepareStatement(consulta)) {
            ps.setString(1, "%" + descripcion.toLowerCase() + "%");
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Producto producto = new Producto(
                            rs.getInt("codigo"),
                            rs.getString("descripcion")
                    );
                    productosEncontrados.add(producto);
                }
            } catch (SQLException e) {
                System.err.println("Error al consultar en la base de datos: " + e.getMessage());
            } finally {
                cerrarConexion(con);
            }
        }
        return productosEncontrados;
    }
}
