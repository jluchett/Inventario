package inventario2;

import Controlador.PrincipalControl;
import Controlador.ProductoControlador;
import Modelo.Producto;
import Modelo.ProductoModelo;
import Vista.frmPrincipal;
import Vista.frmProducto;


public class Inventario2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Producto prod = new Producto();
        ProductoModelo modProd = new ProductoModelo();
        frmProducto formProd = new frmProducto();     
        
        ProductoControlador ctrProd = new ProductoControlador(prod, modProd, formProd);
        
        
        
        frmPrincipal formPrin = new frmPrincipal();
        PrincipalControl ctrPrin = new PrincipalControl(formProd, formPrin);
        ctrPrin.Iniciar();
        formPrin.setVisible(true);
    }

}
