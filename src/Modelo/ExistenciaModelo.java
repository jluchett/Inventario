package Modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class ExistenciaModelo extends ConexionBD {

    public List<Map<String, Object>> obtenerExistencias() throws Exception {
        List<Map<String, Object>> existenciasProductos = new ArrayList<>();
        String sql = "SELECT p.codigo, p.descripcion, "
                + "SUM(CASE WHEN m.tipoMov = 'ENTRADA' THEN m.cantidadProducto ELSE 0 END) "
                + "- SUM(CASE WHEN m.tipoMov = 'SALIDA' THEN m.cantidadProducto ELSE 0 END) AS existencia, "
                + "m.unidadProducto, m.numeroEstante, m.numeroFila "
                + "FROM productos p "
                + "LEFT JOIN movimientos m ON p.codigo = m.codigoProducto "
                + "GROUP BY p.codigo, p.descripcion, m.unidadProducto, m.numeroEstante, m.numeroFila";
        Connection con = obtenerConexion();
        try ( PreparedStatement ps = con.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int codigo = rs.getInt("codigo");
                String descripcion = rs.getString("descripcion");
                int existencia = rs.getInt("existencia");
                String unidadProducto = rs.getString("unidadProducto");
                int numeroEstante = rs.getInt("numeroEstante");
                int numeroFila = rs.getInt("numeroFila");

                Map<String, Object> producto = new HashMap<>();
                producto.put("codigo", codigo);
                producto.put("descripcion", descripcion);
                producto.put("existencia", existencia);
                producto.put("unidadProducto", unidadProducto);
                producto.put("numeroEstante", numeroEstante);
                producto.put("numeroFila", numeroFila);

                existenciasProductos.add(producto);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener Existencias: " + e.getMessage());
            throw e; // Propagar la excepción para que sea manejada en un nivel superior
        } finally {
            // Cerrar la conexión para liberar recursos
            cerrarConexion(con);
        }
        return existenciasProductos;
    }

    public List<Map<String, Object>> ExistenPorDescrip(String descripcion) throws Exception {
        List<Map<String, Object>> existencias = new ArrayList<>();
        String sql = "SELECT p.codigo, p.descripcion, "
                + "SUM(CASE WHEN m.tipoMov = 'ENTRADA' THEN m.cantidadProducto ELSE 0 END) "
                + "- SUM(CASE WHEN m.tipoMov = 'SALIDA' THEN m.cantidadProducto ELSE 0 END) AS existencia, "
                + "m.unidadProducto, m.numeroEstante, m.numeroFila "
                + "FROM productos p "
                + "LEFT JOIN movimientos m ON p.codigo = m.codigoProducto "
                + "WHERE LOWER(p.descripcion) LIKE LOWER(?) "
                + "GROUP BY p.codigo, p.descripcion, m.unidadProducto, m.numeroEstante, m.numeroFila";
        Connection con = obtenerConexion();
        try ( PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + descripcion.toLowerCase() + "%");
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> existencia = new HashMap<>();
                    existencia.put("codigo", rs.getInt("codigo"));
                    existencia.put("descripcion", rs.getString("descripcion"));
                    existencia.put("existencia", rs.getInt("existencia"));
                    existencia.put("unidadProducto", rs.getString("unidadProducto"));
                    existencia.put("numeroEstante", rs.getInt("numeroEstante"));
                    existencia.put("numeroFila", rs.getInt("numeroFila"));
                    existencias.add(existencia);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al obtener Existencias: " + e.getMessage());
            throw e; // Propagar la excepción para que sea manejada en un nivel superior
        } finally {
            // Cerrar la conexión para liberar recursos
            cerrarConexion(con);
        }
        return existencias;
    }
}
