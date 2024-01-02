package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import Modelo.Producto;
import Modelo.ProductoModelo;
import Vista.frmProducto;
import java.awt.HeadlessException;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import javax.swing.JFrame;

public class ProductoControlador implements ActionListener, FocusListener {

    private Producto modProd;
    private ProductoModelo operProd;
    private frmProducto formProd;

    public ProductoControlador(Producto modProd, ProductoModelo operProd, frmProducto formProd) {

        this.modProd = modProd;
        this.operProd = operProd;
        this.formProd = formProd;

        this.formProd.btnElimProd.addActionListener(this);
        this.formProd.btnModifProd.addActionListener(this);
        this.formProd.btnNuevoProd.addActionListener(this);
        this.formProd.btnGuardarProd.addActionListener(this);
        this.formProd.btnBuscarProd.addActionListener(this);
        this.formProd.txtCodigoProd.addFocusListener(this);

        this.formProd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        bloquear();

    }

    public final void bloquear() {
        formProd.btnElimProd.setEnabled(false);
        formProd.btnGuardarProd.setEnabled(false);
        formProd.btnModifProd.setEnabled(false);
        formProd.txtCodigoProd.setEditable(false);
        formProd.txtDescProd.setEditable(false);
    }

    public void desbloquear() {
        formProd.btnElimProd.setEnabled(true);
        formProd.btnGuardarProd.setEnabled(true);
        formProd.btnModifProd.setEnabled(true);
        formProd.txtCodigoProd.setEditable(true);
        formProd.txtDescProd.setEditable(true);
    }

    public void Limpiar() {
        formProd.txtCodigoProd.setText("");
        formProd.txtDescProd.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == formProd.btnGuardarProd) {
            if ("".equals(formProd.txtCodigoProd.getText())) {
                JOptionPane.showMessageDialog(null, "Coloque El codigo del producto");
                formProd.txtCodigoProd.requestFocus();
                return;
            }
            if ("".equals(formProd.txtDescProd.getText())) {
                JOptionPane.showMessageDialog(null, "Coloque la descripcion");
                return;
            }
            try {
                int codigo = Integer.parseInt(formProd.txtCodigoProd.getText());
                String descripcion = formProd.txtDescProd.getText();

                modProd.setCodigo(codigo);
                modProd.setDescripcion(descripcion);

                if (operProd.AddProducto(modProd)) {
                    JOptionPane.showMessageDialog(null, "Registro Guardado");
                    bloquear();
                    formProd.btnNuevoProd.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al guardar");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Error: Ingresa un código válido");
            } catch (HeadlessException | SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al guardar el producto: " + ex.getMessage());
            }
        } else if (e.getSource() == formProd.btnElimProd) {
            try {
                int opcion = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar este producto?", "Eliminar Producto", JOptionPane.YES_NO_OPTION);
                // Verificar la opción seleccionada por el usuario
                if (opcion == JOptionPane.YES_OPTION) {
                    int codigo = Integer.parseInt(formProd.txtCodigoProd.getText());
                    modProd.setCodigo(codigo);
                    if (operProd.delProduucto(modProd)) {
                        JOptionPane.showMessageDialog(null, "Producto eliminado");
                        Limpiar();
                        formProd.txtDescProd.setEditable(false);
                        formProd.btnElimProd.setEnabled(false);
                        formProd.btnModifProd.setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al eliminar el producto");
                    }

                } else {
                    // Si el usuario selecciona "No", no hacer nada o realizar alguna otra acción
                    // ...
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Error: Ingresa un código válido");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al eliminar el producto: " + ex.getMessage());
            }
        } else if (e.getSource() == formProd.btnModifProd) {
            try {
                int codigo = Integer.parseInt(formProd.txtCodigoProd.getText());
                String nuevaDescripcion = formProd.txtDescProd.getText();

                modProd.setCodigo(codigo);
                modProd.setDescripcion(nuevaDescripcion);

                if (operProd.editProducto(modProd)) {
                    JOptionPane.showMessageDialog(null, "Producto actualizado");
                    formProd.txtDescProd.setEditable(false);
                    formProd.btnModifProd.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al actualizar el producto");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Error: Ingresa un código válido");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al actualizar el producto: " + ex.getMessage());
            }
        } else if (e.getSource() == formProd.btnNuevoProd) {
            Limpiar();
            desbloquear();
            formProd.btnNuevoProd.setEnabled(false);
            formProd.btnElimProd.setEnabled(false);
            formProd.btnModifProd.setEnabled(false);
            formProd.txtCodigoProd.requestFocus();

        } else if (e.getSource() == formProd.btnBuscarProd) {

        }
    }

    @Override
    public void focusLost(FocusEvent fe) {
        if ("".equals(formProd.txtCodigoProd.getText())) {
            return;
        }
        if (fe.getSource() == formProd.txtCodigoProd) {
            try {
                int codigo = Integer.parseInt(formProd.txtCodigoProd.getText());
                modProd.setCodigo(codigo);

                if (operProd.searchProd(modProd)) {
                    formProd.txtDescProd.setText(modProd.getDescripcion());
                    formProd.txtDescProd.requestFocus();
                    formProd.btnModifProd.setEnabled(true);
                    formProd.btnGuardarProd.setEnabled(false);
                    formProd.btnElimProd.setEnabled(true);
                    formProd.btnNuevoProd.setEnabled(true);
                    formProd.txtCodigoProd.setEditable(false);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al buscar el producto: " + ex.getMessage());
            }
        }
    }

    @Override
    public void focusGained(FocusEvent fe) {
        if (fe.getSource() == formProd) {
            desbloquear();
            formProd.txtDescProd.setText("Aqui estoy");
            JOptionPane.showMessageDialog(null, "estoy en la descripcion");
        }
    }
}
