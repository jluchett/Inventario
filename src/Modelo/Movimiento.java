
package Modelo;

import java.util.Date;


public class Movimiento {
    private int numeroMov;
    private String tipoMov;
    private int codigoProducto;
    private int cantidadProducto;
    private String unidadProducto;
    private int numeroEstante;
    private int numeroFila;
    private Date fechaMov;
    private String observaciones;
    
    public Movimiento(){
        
    }

    public Movimiento(int numeroMov, String tipoMov, int codigoProducto, int cantidadProducto, String unidadProducto, int numeroEstante, int numeroFila, Date fechaMov, String observaciones) {
        this.numeroMov = numeroMov;
        this.tipoMov = tipoMov;
        this.codigoProducto = codigoProducto;
        this.cantidadProducto = cantidadProducto;
        this.unidadProducto = unidadProducto;
        this.numeroEstante = numeroEstante;
        this.numeroFila = numeroFila;
        this.fechaMov = fechaMov;
        this.observaciones = observaciones;
    }

    public int getNumeroMov() {
        return numeroMov;
    }

    public void setNumeroMov(int numeroMov) {
        this.numeroMov = numeroMov;
    }

    public String getTipoMov() {
        return tipoMov;
    }

    public void setTipoMov(String tipoMov) {
        this.tipoMov = tipoMov;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public String getUnidadProducto() {
        return unidadProducto;
    }

    public void setUnidadProducto(String unidadProducto) {
        this.unidadProducto = unidadProducto;
    }

    public int getNumeroEstante() {
        return numeroEstante;
    }

    public void setNumeroEstante(int numeroEstante) {
        this.numeroEstante = numeroEstante;
    }

    public int getNumeroFila() {
        return numeroFila;
    }

    public void setNumeroFila(int numeroFila) {
        this.numeroFila = numeroFila;
    }

    public Date getFechaMov() {
        return fechaMov;
    }

    public void setFechaMov(Date fechaMov) {
        this.fechaMov = fechaMov;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    
}
