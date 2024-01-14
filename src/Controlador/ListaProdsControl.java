
package Controlador;

import Modelo.ProductoModelo;
import Vista.frmBuscarProd;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
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
        columnModel.getColumn(0).setPreferredWidth(70);
        columnModel.getColumn(1).setPreferredWidth(250);
        // Orientar el texto de la tercera columna hacia la derecha
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        vista.tblListProds.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
        cargarTabla();
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
         }
    
    private void cargarTabla() {
        try {
            List<Map<String, Object>> existen = modelo.obtenerExistencias();
            mostrarExistenciasEnTabla(existen);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al cargar tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    
}
