package Controlador;

import Modelo.Movimiento;
import Modelo.MovimientoModelo;
import Modelo.Producto;
import Modelo.ProductoModelo;
import Vista.frmEntradas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class MovimientoControlador implements ActionListener, FocusListener {

    private final MovimientoModelo modelo;
    private final frmEntradas vista;
    private DefaultTableModel model;

    public MovimientoControlador(MovimientoModelo modelo, frmEntradas vista) {
        this.modelo = modelo;
        this.vista = vista;

        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnCancelar.addActionListener(this);
        this.vista.btnAgregar.addActionListener(this);
        this.vista.txtCodigo.addFocusListener(this);
        this.vista.btnNuevo.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        // Agregar otros listeners según los botones u otros componentes que necesites
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Todas las celdas no son editables
            }
        };
        this.vista.tblMovimiento.setModel(model);
        model.addColumn("Codigo");
        model.addColumn("Descripcion");
        model.addColumn("Cant");
        model.addColumn("Medida");
        model.addColumn("Estante");
        model.addColumn("Fila");
        model.addColumn("Observacion");
        
        // Obtiene el modelo de columnas de la tabla
        TableColumnModel columnModel = vista.tblMovimiento.getColumnModel();
        // Establece el ancho preferido para cada columna
        columnModel.getColumn(0).setPreferredWidth(70);
        columnModel.getColumn(1).setPreferredWidth(300);
        columnModel.getColumn(2).setPreferredWidth(40);
        columnModel.getColumn(3).setPreferredWidth(50);
        columnModel.getColumn(4).setPreferredWidth(50);
        columnModel.getColumn(5).setPreferredWidth(40);
        columnModel.getColumn(6).setPreferredWidth(250);
        
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        vista.tblMovimiento.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        vista.tblMovimiento.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        vista.tblMovimiento.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
        obtenerNumMov();

        Date fechaActual = new Date();
        // Formatear la fecha en el formato deseado
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MMMM-yyyy");
        String fechaFormateada = formatoFecha.format(fechaActual);
        // Mostrar la fecha formateada en el JLabel
        this.vista.lblFecha.setText(fechaFormateada);

        
        this.vista.btnNuevo.setEnabled(false);
        this.vista.txtDescripcion.setEditable(false);
        this.vista.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnGuardar) {

            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "No hay datos para registrar");
                return;
            }
            JOptionPane.showMessageDialog(null, "Movimiento registrado");
            limpiar();
            vista.txtCantidad.setEditable(false);
            vista.txtCodigo.setEditable(false);
            vista.txtEstante.setEditable(false);
            vista.txtFila.setEditable(false);
            vista.txtObservaciones.setEditable(false);
            vista.btnAgregar.setEnabled(false);
            vista.btnLimpiar.setEnabled(false);
            vista.btnGuardar.setEnabled(false);
            vista.btnNuevo.setEnabled(true);
            vista.btnCancelar.setEnabled(false);
        } else if (e.getSource() == vista.btnCancelar) {
            cancelarOperacion();
        } else if (e.getSource() == vista.btnAgregar) {
            if ("".equals(vista.txtCodigo.getText())) {
                JOptionPane.showMessageDialog(null, "Coloque el codigo del producto");
                vista.txtCodigo.requestFocus();
                return;
            }
            if ("".equals(vista.txtDescripcion.getText())) {
                JOptionPane.showMessageDialog(null, "No hay descripcion");
                vista.txtCodigo.requestFocus();
                return;
            }
            if ("".equals(vista.txtCantidad.getText())) {
                JOptionPane.showMessageDialog(null, "Digite la cantidad");
                vista.txtCantidad.requestFocus();
                return;
            }
            if ("".equals(vista.txtEstante.getText())) {
                JOptionPane.showMessageDialog(null, "Coloque el número del estante");
                vista.txtEstante.requestFocus();
                return;
            }
            if ("".equals(vista.txtFila.getText())) {
                JOptionPane.showMessageDialog(null, "Coloque el numero de la fila");
                vista.txtFila.requestFocus();
                return;
            }
            if ("".equals(vista.txtObservaciones.getText())) {
                vista.txtObservaciones.setText("ok");
            }
            Object[] fila = new Object[7];

            fila[0] = vista.txtCodigo.getText();
            fila[1] = vista.txtDescripcion.getText();
            fila[2] = vista.txtCantidad.getText();
            fila[3] = vista.cmbMedida.getSelectedItem();
            fila[4] = vista.txtEstante.getText();
            fila[5] = vista.txtFila.getText();
            fila[6] = vista.txtObservaciones.getText();

            model.addRow(fila);
            guardarMovimiento();
            limpiar();
        } else if (e.getSource() == vista.btnNuevo) {
            reiniciar();
        } else if (e.getSource() == vista.btnLimpiar) {
            limpiar();
        }
        // Agregar otros if/else según los botones u otros componentes que necesites manejar

    }

    private void guardarMovimiento() {
        // Obtener los datos del formulario
        int numeroMov = Integer.parseInt(vista.lblNumero.getText());
        String tipoMov = vista.lblEntrada.getText(); // Ejemplo, podrías obtener el tipo de movimiento de otro componente
        int codigoProducto = Integer.parseInt(vista.txtCodigo.getText());
        int cantidad = Integer.parseInt(vista.txtCantidad.getText());
        String unidadProducto = (String) vista.cmbMedida.getSelectedItem(); // Obtener el valor seleccionado del JComboBox
        int numeroEstante = Integer.parseInt(vista.txtEstante.getText());
        int numeroFila = Integer.parseInt(vista.txtFila.getText());
        Date fechaMov = new Date();
        String observa = vista.txtObservaciones.getText();

        // Crear un objeto de tipo Movimiento con los datos obtenidos
        Movimiento movimiento = new Movimiento(numeroMov, tipoMov, codigoProducto, cantidad, unidadProducto, numeroEstante, numeroFila, fechaMov, observa);

        // Llamar al método en el modelo para guardar el movimiento
        try {
            modelo.agregarMovimiento(movimiento);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar el movimiento: " + ex.getMessage());
        }
    }

    private void limpiar() {
        vista.txtCodigo.setText("");
        vista.txtDescripcion.setText("");
        vista.txtCantidad.setText("");
        vista.txtEstante.setText("");
        vista.txtFila.setText("");
        vista.txtObservaciones.setText("");
    }

    private void obtenerNumMov() {
        // Supongamos que tienes un método en el modelo que realiza esta consulta, por ejemplo:
        try {
            int ultimoNumero = modelo.obteneNumero(vista.lblEntrada.getText()); // Suponiendo que quieres el último número para movimientos de tipo "ENTRADA"
            // Incrementar el número obtenido en 1 y mostrarlo en lblNumero
            if (ultimoNumero == 0) {
                if ("ENTRADA".equals(vista.lblEntrada.getText())) {
                    vista.lblNumero.setText(String.valueOf(ultimoNumero + 1));
                }
                if ("SALIDA".equals(vista.lblEntrada.getText())) {
                    vista.lblNumero.setText(String.valueOf(ultimoNumero + 2));
                }
            } else {
                vista.lblNumero.setText(String.valueOf(ultimoNumero + 2));
            }
        } catch (SQLException ex) {
            // Manejo de la excepción, por ejemplo, mostrar un mensaje de error
            JOptionPane.showMessageDialog(null, "Error al obtener el último número de movimiento: " + ex.getMessage());
        }
    }

    private void cancelarOperacion() {
        // Lógica para cancelar la operación, por ejemplo, limpiar los campos del formulario
        if (model.getRowCount() == 0) {
            
        } else {
            int opcion = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea cancelar?", "Perdera lo que ha realizado", JOptionPane.YES_NO_OPTION);

            // Verificar la opción seleccionada por el usuario
            if (opcion == JOptionPane.YES_OPTION) {
                int numMov = Integer.parseInt(vista.lblNumero.getText());
                try {
                    modelo.eliminarMovimiento(numMov);
                    JOptionPane.showMessageDialog(null, "Movimiento cancelado");
                    reiniciar();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error al buscar el producto:" + e.getMessage());
                }

                
            } else {
                // Si el usuario selecciona "No", no hacer nada o realizar alguna otra acción
                // ...
            }
        }
    }
    private void reiniciar(){
        model.setRowCount(0);
            vista.txtCantidad.setEditable(true);
            vista.txtCodigo.setEditable(true);
            vista.txtEstante.setEditable(true);
            vista.txtFila.setEditable(true);
            vista.txtObservaciones.setEditable(true);
            vista.btnAgregar.setEnabled(true);
            vista.btnLimpiar.setEnabled(true);
            vista.btnGuardar.setEnabled(true);
            vista.btnNuevo.setEnabled(false);
            vista.btnCancelar.setEnabled(true);
            obtenerNumMov();
    }
    

    public void Iniciar() {
        vista.setTitle("Ingreesos");
        vista.setLocationRelativeTo(null);
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == vista.txtCodigo) {
            vista.txtDescripcion.setText("");
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == vista.txtCodigo) {
            if ("".equals(vista.txtCodigo.getText())) {
                return;
            }
            int codigo = Integer.parseInt(vista.txtCodigo.getText());
            Producto modProd = new Producto();
            ProductoModelo operProd = new ProductoModelo();
            modProd.setCodigo(codigo);
            try {
                if (operProd.searchProd(modProd)) {
                    vista.txtDescripcion.setText(modProd.getDescripcion());
                    vista.txtCantidad.requestFocus();
                } else {

                    vista.txtDescripcion.setText("");
                    vista.txtCodigo.requestFocus();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al buscar el producto:" + ex.getMessage());
            }
        }
    }
}
