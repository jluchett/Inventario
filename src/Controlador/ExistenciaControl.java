
package Controlador;

import Modelo.ExistenciaModelo;
import Vista.frmExistencia;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExistenciaControl implements ActionListener{
    private ExistenciaModelo modelo;
    private frmExistencia vista;
    
    public ExistenciaControl(ExistenciaModelo modelo, frmExistencia vista)   {
        this.modelo = modelo;
        this.vista = vista;

        this.vista.btn_buscar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btn_buscar) {
            //buscarPorDescripcion();
        }
    }
}
