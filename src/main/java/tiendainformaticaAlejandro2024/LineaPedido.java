/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tiendainformaticaAlejandro2024;

import java.io.Serializable;

/**
 *
 * @author alu15d
 */
public class LineaPedido implements Serializable{
     private String idArticulo;
    private int unidades;

    public String getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(String idArticulo) {
        this.idArticulo = idArticulo;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public LineaPedido(String idArticulo, int unidades) {
        this.idArticulo = idArticulo;
        this.unidades = unidades;
    }

    @Override
    public String toString() {
        return "LineaPedido{" + "idArticulo=" + idArticulo + ", unidades=" + unidades + '}';
    }
}
