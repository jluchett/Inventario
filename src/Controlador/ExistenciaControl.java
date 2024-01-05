package Controlador;

import Modelo.ExistenciaModelo;
import Vista.frmExistencia;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ExistenciaControl implements ActionListener {

    private ExistenciaModelo modelo;
    private frmExistencia vista;
    private DefaultTableModel model;

    public ExistenciaControl(ExistenciaModelo modelo, frmExistencia vista) {
        this.modelo = modelo;
        this.vista = vista;

        this.vista.btn_buscar.addActionListener(this);
        this.vista.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Todas las celdas no son editables
            }
        };
        this.vista.tblExisten.setModel(model);
        model.addColumn("Codigo");
        model.addColumn("Descripcion");
        model.addColumn("Cant");
        model.addColumn("Medida");
        model.addColumn("Estante");
        model.addColumn("Fila");

        cargarTabla();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btn_buscar) {
            buscarPorDescripcion();
        }
    }

    private void cargarTabla() {
        try {
            List<Map<String, Object>> existen = modelo.obtenerExistencias();
            mostrarExistenciasEnTabla(existen);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al cargar tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void buscarPorDescripcion() {
        String descripcion = vista.txt_producto.getText().trim();
        if (!descripcion.isEmpty()) {
            try {
                List<Map<String, Object>> existencias = modelo.ExistenPorDescrip(descripcion);
                mostrarExistenciasEnTabla(existencias);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, "Error al buscar existencias: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            cargarTabla();
        }
    }

    private void mostrarExistenciasEnTabla(List<Map<String, Object>> existencias) {
        model.setRowCount(0); // Limpiar la tabla antes de mostrar los resultados

        for (Map<String, Object> existencia : existencias) {
            Object[] row = {
                existencia.get("codigo"),
                existencia.get("descripcion"),
                existencia.get("existencia"),
                existencia.get("unidadProducto"),
                existencia.get("numeroEstante"),
                existencia.get("numeroFila")
            };
            model.addRow(row);
        }
    }
}
