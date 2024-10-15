package tiendainformaticaAlejandro2024;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author alu15d
 */
public class Pedido implements Serializable{ //Clase pedido
    private String idPedido;
    private Cliente clientePedido;
    private ArrayList <LineaPedido> cestaCompra;
    private LocalDate fechaPedido;
   

    //Método constructor para el pedido
    public Pedido(String idPedido, Cliente clientePedido, LocalDate fechaPedido, ArrayList<LineaPedido> cestaCompra) {
        this.idPedido = idPedido;
        this.clientePedido = clientePedido;
        this.fechaPedido = fechaPedido;
        this.cestaCompra = cestaCompra;
    }

    //Getters y setters
    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public Cliente getClientePedido() {
        return clientePedido;
    }

    public void setClientePedido(Cliente clientePedido) {
        this.clientePedido = clientePedido;
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDate fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public ArrayList<LineaPedido> getCestaCompra() {
        return cestaCompra;
    }

    public void setCestaCompra(ArrayList<LineaPedido> cestaCompra) {
        this.cestaCompra = cestaCompra;
    }



    //toString
    @Override
    public String toString() {
        return idPedido + " - " + clientePedido.getNombre() + " - " + fechaPedido;
    }
             
 }
