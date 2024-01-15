package Controlador;

import Modelo.Producto;
import Modelo.ProductoModelo;
import Vista.frmBuscarProd;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class ListaProdsControl implements ActionListener {

    private ProductoModelo prodMod;
    private frmBuscarProd vista;
    private DefaultTableModel modTable;

    public ListaProdsControl(ProductoModelo prodMod, frmBuscarProd vista) {
        this.prodMod = prodMod;
        this.vista = vista;

        vista.btnBuscarProd.addActionListener(this);
        vista.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        modTable = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Todas las celdas no son editables
            }
        };
        vista.tblListProds.setModel(modTable);
        modTable.addColumn("Codigo");
        modTable.addColumn("Descripcion");
        // Obtiene el modelo de columnas de la tabla
        TableColumnModel columnModel = vista.tblListProds.getColumnModel();
        // Establece el ancho preferido para cada columna
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(300);
        // Orientar el texto de la tercera columna hacia la derecha
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        vista.tblListProds.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
        cargarTabla();
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.btnBuscarProd){
            buscarPorDescripcion();
        }
    }

    private void cargarTabla() {
        try {
            List<Producto> productos = prodMod.obtenerTodosLosProductos();
            mostrarExistenciasEnTabla(productos);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(vista, "Error al cargar tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void mostrarExistenciasEnTabla(List<Producto> productos) {
        modTable.setRowCount(0); // Limpiar la tabla antes de mostrar los resultados

        for (Producto producto : productos) {
            Object[] row = {
                producto.getCodigo(),
                producto.getDescripcion()
            };
            modTable.addRow(row);
        }
    }
    private void buscarPorDescripcion() {
        String descripcion = vista.txtNomProd.getText().trim();
        if (!descripcion.isEmpty()) {
            try {
                List<Producto> productos = prodMod.buscarProductosPorDescripcion(descripcion);
                mostrarExistenciasEnTabla(productos);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(vista, "Error al buscar existencias: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            cargarTabla();
        }
    }
    
}
