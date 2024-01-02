package Controlador;

import Modelo.Movimiento;
import Modelo.MovimientoModelo;
import Vista.frmEntradas;
import Vista.frmPrincipal;
import Vista.frmProducto;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrincipalControl implements ActionListener {

    private frmProducto formProd;
    private frmPrincipal formPrin;

    public PrincipalControl(frmProducto formProd, frmPrincipal formPrin) {
        this.formProd = formProd;
        this.formPrin = formPrin;

        this.formPrin.btnProductos.addActionListener(this);
        this.formPrin.btnEntradas.addActionListener(this);
        this.formPrin.btnSalidas.addActionListener(this);

    }

    public void Iniciar() {
        formProd.setTitle("Productos");
        formPrin.setTitle("Inventario");
        formProd.setLocationRelativeTo(null);
        formPrin.setLocationRelativeTo(null);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == formPrin.btnProductos) {
            formProd.setVisible(true);
        }
        if (e.getSource() == formPrin.btnEntradas) {
            Movimiento mov = new Movimiento();
            MovimientoModelo modMov = new MovimientoModelo();
            frmEntradas formEnt = new frmEntradas();
            formEnt.lblEntrada.setText("ENTRADA");
            MovimientoControlador ctrMov = new MovimientoControlador(modMov, formEnt);
            formEnt.lblTitulo.setText("Ingresos");
            formEnt.setTitle("Entradas");
            formEnt.setLocationRelativeTo(null);
            formEnt.setVisible(true);
        }
        if (e.getSource() == formPrin.btnSalidas){
            
            Movimiento mov = new Movimiento();
            MovimientoModelo modMov = new MovimientoModelo();
            frmEntradas formEnt = new frmEntradas();
            formEnt.lblEntrada.setText("SALIDA");
            MovimientoControlador ctrMov = new MovimientoControlador(modMov, formEnt);
            formEnt.lblTitulo.setText("Salidas");
            formEnt.setTitle("Salidas");
            formEnt.setLocationRelativeTo(null);
            formEnt.setVisible(true);
        }
    }
}
