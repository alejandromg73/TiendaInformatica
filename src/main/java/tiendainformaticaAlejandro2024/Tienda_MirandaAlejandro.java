/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tiendainformaticaAlejandro2024;

import java.util.Arrays;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author alu15d
 */
public class Tienda_MirandaAlejandro {

    Scanner sc = new Scanner(System.in);

    private ArrayList<Pedido> pedidos;
    private HashMap<String, Cliente> clientes;
    private HashMap<String, Articulo> articulos;
    LocalDate hoy = LocalDate.now();

    //Método constructor de la aplicación
    public Tienda_MirandaAlejandro() {
        this.clientes = new HashMap();
        this.pedidos = new ArrayList();
        this.articulos = new HashMap();
    }

    public static void main(String[] args) {
        Tienda_MirandaAlejandro miTienda = new Tienda_MirandaAlejandro();
        miTienda.cargaDatos();
        miTienda.menuprincipal();
    }

    public void menuprincipal() {//Método menú principal
        int opcion = 0;
        do {
            System.out.println("\t\t\t\t1-OPCION 1");
            System.out.println("\t\t\t\t2-OPCION 2");
            System.out.println("\t\t\t\t3-OPCION 3");
            System.out.println("\t\t\t\t9-SALIR");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1: {
                    opcion1();
                    break;
                }
                case 2: {
                    opcion2();
                    break;
                }
                case 3: {
                    opcion3();
                    break;
                }
            }
        } while (opcion != 9);
    }

    
    //<editor-fold defaultstate="collapsed" desc="Métodos auxiliares">
    public int buscarDni(String dni){ //Método para buscar el dni de un cliente
        int buscarDni = -1;
        int i = 0;
        for (Cliente c : clientes.values()) {
            if (c.getDni().equals(dni)) {//Si el dni coincide
                buscarDni = i;
                break;
            }
            i++;
        }
        
        return buscarDni;
    }

    public int buscarId(String id){ //Método para buscar el id de un articulo
        int buscarId = -1;
        int i = 0;
        for (Articulo a : articulos.values()) {
            if (a.getIdArticulo().equals(id)) {//Si el id coincide con la posición
                buscarId = i;
                break;
            }
            i++;
        }
        
        return buscarId;
    }

    private boolean esInt(String s) {
        int n;
        try {
            n = Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private boolean esDouble(String s) {
        double n;
        try {
            n = Double.parseDouble(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    
    public void cargaDatos() {
        clientes.put("11111111R", new Cliente("11111111R", "ANA", "658111111", "ana@gmail.com"));
        clientes.put("22222222H", new Cliente("22222222H", "LOLA", "649222222", "lola@gmail.com"));
        clientes.put("33333333F", new Cliente("33333333F", "JUAN", "652333333", "juan@gmail.com"));

        articulos.put("1-11", new Articulo("1-11", "RATON LOGITECH", 14, 15));
        articulos.put("1-22", new Articulo("1-22", "TECLADO STANDARD", 9, 18));
        articulos.put("2-11", new Articulo("2-11", "HDD SEAGATE 1TB", 16, 80));
        articulos.put("2-22", new Articulo("2-22", "SSD KINGSTOM 256GB", 9, 70));
        articulos.put("2-33", new Articulo("2-33", "SSD KINGSTOM 512GB", 15, 120));
        articulos.put("3-11", new Articulo("3-11", "EPSON ET2800     ", 0, 200));
        articulos.put("3-22", new Articulo("3-22", "EPSON XP200      ", 5, 80));
        articulos.put("4-11", new Articulo("4-11", "ASUS LED 22     ", 5, 100));
        articulos.put("4-22", new Articulo("4-22", "HP LED 28      ", 5, 180));
        articulos.put("4-33", new Articulo("4-33", "SAMSUNG ODISSEY G5", 12, 580));

        LocalDate hoy = LocalDate.now();
        pedidos.add(new Pedido("11111111R-001/2024", clientes.get("11111111R"), hoy.minusDays(1), new ArrayList(List.of(new LineaPedido("1-11", 1), new LineaPedido("2-11", 1)))));
        pedidos.add(new Pedido("11111111R-002/2024", clientes.get("11111111R"), hoy.minusDays(2), new ArrayList(List.of(new LineaPedido("4-11", 14), new LineaPedido("4-22", 4), new LineaPedido("4-33", 4)))));
        pedidos.add(new Pedido("33333333F-001/2024", clientes.get("33333333F"), hoy.minusDays(3), new ArrayList(List.of(new LineaPedido("3-22", 3), new LineaPedido("2-22", 3)))));
        pedidos.add(new Pedido("33333333f-002/2024", clientes.get("33333333F"), hoy.minusDays(5), new ArrayList(List.of(new LineaPedido("4-33", 3), new LineaPedido("2-11", 3)))));
        pedidos.add(new Pedido("22222222H-001/2024", clientes.get("22222222H"), hoy.minusDays(4), new ArrayList(List.of(new LineaPedido("2-11", 2), new LineaPedido("2-33", 2), new LineaPedido("4-33", 2)))));

    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Opcion 1, opcion 2 y opcion 3">
    public void opcion1() {//Método que dado un identificador de pedido introducido por teclado, muestra el listado de artículos incluidos en ese pedido con los subtotales de cada LineaPedido, y el importe total de ese pedido, sin IVA y con IVA (+21%)
        double total = 0;
        double totalConIva;
        double iva;
        DecimalFormat df = new DecimalFormat("#.00");
        sc.nextLine();
        System.out.println("Introduce el id del pedido que quieres: ");
        String idPedido = sc.next();
        for (Pedido p : pedidos) {//Recorro todo el ArrayList de pedidos y con el if busco el pedido que tiene id de pedido igual al introducido por teclado
            if (p.getIdPedido().equals(idPedido)) {
                for (LineaPedido l : p.getCestaCompra()) {//Recorro todas las lineas de pedido del pedido introducido por teclado (Cestacompra)
                    System.out.println(articulos.get(l.getIdArticulo()).getDescripcion() + " : " + articulos.get(l.getIdArticulo()).getPvp() + "€");
                    total = total + (articulos.get(l.getIdArticulo()).getPvp())*l.getUnidades();//Variable total para calcular el total del pedido
                }
            }
        }
        totalConIva = total * 1.21;//Multiplico por 1,21 para calcular el iva del 21%
        System.out.println("El total del pedido " + idPedido + " es: " + total + "€");
        System.out.println("El total del pedido con IVA es: " + totalConIva + "€");
    }

    public void opcion2() {//Se solicita un número de unidades por teclado (No utilizar variables de tipo int) y se muestra un listado de los articulos que tiene en stock un número de unidades igual o inferior al introducido por teclado. Obligatorio hacerlo con el API de streams.
        System.out.println("Introduce número de unidades: ");
        String unidades = sc.next();
        //Paso la variable unidades que pedí como String a Int para poder compararla con las unidades
        System.out.println("Listado de articulos que tienen " + unidades + " unidades o menos: ");
        System.out.println();
        articulos.values().stream().filter(a -> a.getExistencias() <= Integer.parseInt(unidades)).sorted().forEach(System.out::println);//Imprimo por pantalla los articulos que tienen menos o esas unidades de stock
    }

    public void opcion3() {//Listado de clientes ordenados de Mayor a menor importe gastado en la tienda. El listado ha de mostrar los datos de cada cliente y el dinero que ha gastado en la tienda (El total de todos sus pedidos) 
        System.out.println("Listado de clientes: ");
        for(Cliente c: clientes.values()){
            System.out.println(c);
        }
        }
//</editor-fold>
    
    }

