/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tiendainformaticaAlejandro2024;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author alu15d
 */
/**
 *
 * @author alu15d
 */
public class TiendaMirandaAlejandro {

    Scanner sc = new Scanner(System.in);

    private ArrayList<Pedido> pedidos;
    private HashMap<String, Cliente> clientes;
    private HashMap<String, Articulo> articulos;
    LocalDate hoy = LocalDate.now();

    //Método constructor de la aplicación
    public TiendaMirandaAlejandro() {
        this.clientes = new HashMap();
        this.pedidos = new ArrayList();
        this.articulos = new HashMap();
    }

    public static void main(String[] args) {
        TiendaMirandaAlejandro miTienda = new TiendaMirandaAlejandro();
        miTienda.importarDatos();
        //miTienda.cargaDatos();
        miTienda.menu();
    }

    public void cargaDatos() {
        clientes.put("11111111R", new Cliente("11111111R", "ANA ", "658111111", "ana@gmail.com"));
        clientes.put("22222222H", new Cliente("22222222H", "LOLA", "649222222", "lola@gmail.com"));
        clientes.put("33333333F", new Cliente("33333333F", "JUAN", "652333333", "juan@gmail.com"));

        articulos.put("1-11", new Articulo("1-11", "RATON LOGITECH ST ", 14, 15));
        articulos.put("1-22", new Articulo("1-22", "TECLADO STANDARD  ", 9, 18));
        articulos.put("2-11", new Articulo("2-11", "HDD SEAGATE 1 TB  ", 16, 80));
        articulos.put("2-22", new Articulo("2-22", "SSD KINGSTOM 256GB", 9, 70));
        articulos.put("2-33", new Articulo("2-33", "SSD KINGSTOM 512GB", 0, 200));
        articulos.put("3-22", new Articulo("3-22", "EPSON PRINT XP300 ", 5, 80));
        articulos.put("4-11", new Articulo("4-11", "ASUS  MONITOR  22 ", 5, 100));
        articulos.put("4-22", new Articulo("4-22", "HP MONITOR LED 28 ", 5, 180));
        articulos.put("4-33", new Articulo("4-33", "SAMSUNG ODISSEY G5", 12, 580));

        LocalDate hoy = LocalDate.now();
        pedidos.add(new Pedido("11111111R-001/2024", clientes.get("11111111R"), hoy.minusDays(1), new ArrayList<>(List.of(new LineaPedido("1-11", 3), new LineaPedido("2-11", 1)))));
        pedidos.add(new Pedido("11111111R-002/2024", clientes.get("11111111R"), hoy.minusDays(2), new ArrayList<>(List.of(new LineaPedido("4-11", 3), new LineaPedido("4-22", 2), new LineaPedido("4-33", 4)))));
        pedidos.add(new Pedido("33333333F-001/2024", clientes.get("33333333F"), hoy.minusDays(3), new ArrayList<>(List.of(new LineaPedido("4-22", 1), new LineaPedido("2-22", 3)))));
        pedidos.add(new Pedido("33333333f-002/2024", clientes.get("33333333F"), hoy.minusDays(5), new ArrayList<>(List.of(new LineaPedido("4-33", 3), new LineaPedido("2-11", 3)))));
        pedidos.add(new Pedido("22222222H-001/2024", clientes.get("22222222H"), hoy.minusDays(4), new ArrayList<>(List.of(new LineaPedido("2-11", 5), new LineaPedido("2-33", 3), new LineaPedido("4-33", 2)))));

    }

    public void menu() {//Método menú principal
        int opcion = 0;
        do {
            System.out.println("\t\t\t\t1-RANKING ARTICULOS");
            System.out.println("\t\t\t\t2-LISTADO ARTICULOS");
            System.out.println("\t\t\t\t3-TOTAL PEDIDO");
            System.out.println("\t\t\t\t4-GASTO CLIENTE");
            System.out.println("\t\t\t\t5-COPIA SEGURIDAD");
            System.out.println("\t\t\t\t6-LISTADO NORMAL ARTICULOS");
            System.out.println("\t\t\t\t9-SALIR");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1: {
                    rankingArticulos();
                    break;
                }
                case 2: {
                    listadoArticulos();
                    break;
                }
                case 3: {
                    totalPedido();
                    break;
                }
                case 4: {
                    gastoCliente();
                }
                case 5: {
                    exportarDatos();
                }
                case 6: {
                    listadonormalArticulos();
                }
            }
        } while (opcion != 9);
    }

//1
    public int unidadesVendidas(String idArticulo) {
        int cont = 0;
        for (Pedido p : pedidos) {//Recorro todo el ArrayList de pedidos 
            for (LineaPedido l : p.getCestaCompra()) {//Recorro todas las lineas de pedido del pedido(Cestacompra)
                if (l.getIdArticulo().equals(idArticulo)) {
                    cont += l.getUnidades();
                }
            }
        }
        return cont;
    }

    public void rankingArticulos() { //Método para listar los articulos ordenado por unidades vendidas de - a +
        System.out.println("LISTADO ARTICULOS UNIDADES VENDIDAS: ");
        articulos.values().stream().sorted(Comparator.comparing(a -> unidadesVendidas(a.getIdArticulo()))).forEach(a -> System.out.println(a.getDescripcion() + "    Unidades vendidas: " + unidadesVendidas(a.getIdArticulo())));
    }

//2
    public void listadoArticulos() { //Lista los articulos de cada seccion ordenados por pvp de articulo
        System.out.println("PERIFERICOS");
        articulos.values().stream().filter(a -> a.getIdArticulo().startsWith("1")).sorted(new ComparaArticuloPorPrecio()).forEach(System.out::println);
        System.out.println("ALMACENAMIENTO");
        articulos.values().stream().filter(a -> a.getIdArticulo().startsWith("2")).sorted(new ComparaArticuloPorPrecio()).forEach(System.out::println);
        System.out.println("IMPRESORAS");
        articulos.values().stream().filter(a -> a.getIdArticulo().startsWith("3")).sorted(new ComparaArticuloPorPrecio()).forEach(System.out::println);
        System.out.println("MONITORES");
        articulos.values().stream().filter(a -> a.getIdArticulo().startsWith("4")).sorted(new ComparaArticuloPorPrecio()).forEach(System.out::println);
    }

//3
    public void totalPedido() { //Método para calcular el total de un pedido, incluyendo subtotales de lineas de pedido con iva y sin iva, además del importe total
        sc.nextLine();
        System.out.println("INTRODUCE ID DE PEDIDO PARA CALCULAR EL TOTAL: ");
        String idPedido = sc.nextLine();
        System.out.println("LISTADO DE ARTICULOS DEL PEDIDO " + idPedido + ":");
        double total = 0;
        double totalConIva;
        double iva;
        DecimalFormat df = new DecimalFormat("#.00");
        for (Pedido p : pedidos) {//Recorro todo el ArrayList de pedidos y con el if busco el pedido que tiene id de pedido igual al introducido por teclado
            if (p.getIdPedido().equals(idPedido)) {
                for (LineaPedido l : p.getCestaCompra()) {//Recorro todas las lineas de pedido del pedido introducido por teclado (Cestacompra)
                    System.out.println(articulos.get(l.getIdArticulo()).getDescripcion() + " : " + articulos.get(l.getIdArticulo()).getPvp() + "€ " + " * " + l.getUnidades() + " unidades" + " = " + articulos.get(l.getIdArticulo()).getPvp() * l.getUnidades() + "€");
                    total = total + (articulos.get(l.getIdArticulo()).getPvp()) * l.getUnidades();//Variable total para calcular el total del pedido
                }
            }
        }
        totalConIva = total * 1.21;//Multiplico por 1,21 para calcular el iva del 21%
        System.out.println("El total del pedido " + idPedido + " es: " + total + "€");
        System.out.println("El total del pedido con IVA es: " + totalConIva + "€");
    }

//4
    public double dineroGastado(String dni) {
        double total = 0;
        for (Pedido p : pedidos) {
                for (LineaPedido l : p.getCestaCompra()) {
                    if(p.getClientePedido().getDni().equals(dni)){
                        total+=articulos.get(l.getIdArticulo()).getPvp() * l.getUnidades();
                    }
                }
        }
        return total;
    }
    
    public void gastoCliente() { //Método que ordena los clientes de menor a mayor importe gastado en la tienda.
        System.out.println("CLIENTES ORDENADOS POR TOTAL GASTADO: ");
        clientes.values().stream().sorted(Comparator.comparing(c -> dineroGastado(c.getDni()))).forEach(c -> System.out.println(c.getDni() + c.getNombre() + c.getTelefono() + c.getEmail() + "-------->" + "TOTAL GASTADO: " + dineroGastado(c.getDni())));
    }

//5
    public void exportarDatos() { //Método para hacer las copias de seguridad por sección
        try (ObjectOutputStream oosPerifericos = new ObjectOutputStream(new FileOutputStream("PERIFERICOS.dat")); ObjectOutputStream oosAlmacenamiento = new ObjectOutputStream(new FileOutputStream("ALMACENAMIENTO.dat")); ObjectOutputStream oosImpresoras = new ObjectOutputStream(new FileOutputStream("IMPRESORAS.dat")); ObjectOutputStream oosMonitores = new ObjectOutputStream(new FileOutputStream("MONITORES.dat"))) {
            for (Articulo a : articulos.values()) {
                switch (a.getIdArticulo().charAt(0)) {
                    case '1': {
                        oosPerifericos.writeObject(a);
                        System.out.println(a);
                        break;
                    }
                    case '2': {
                        oosAlmacenamiento.writeObject(a);
                        System.out.println(a);
                        break;

                    }
                    case '3': {
                        oosImpresoras.writeObject(a);
                        System.out.println(a);
                        break;

                    }
                    case '4': {
                        oosMonitores.writeObject(a);
                        System.out.println(a);
                        break;

                    }

                }
            }
            System.out.println("Copia de seguridad de los articulos realizada por sección con éxito.");
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public void importarDatos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("PERIFERICOS.dat"))) {
            Articulo a = null;
            while ((a = (Articulo) ois.readObject()) != null) {
                articulos.put(a.getIdArticulo(), a);
                System.out.println(a);
            }
            System.out.println("Perifericos importados con éxito.");
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.toString());
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("ALMACENAMIENTO.dat"))) {
            Articulo a = null;
            while ((a = (Articulo) ois.readObject()) != null) {
                articulos.put(a.getIdArticulo(), a);
                System.out.println(a);

            }
            System.out.println("Almacenamiento importados con éxito.");
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.toString());
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("IMPRESORAS.dat"))) {
            Articulo a = null;
            while ((a = (Articulo) ois.readObject()) != null) {
                articulos.put(a.getIdArticulo(), a);
                System.out.println(a);
            }
            System.out.println("Impresoras importados con éxito.");

        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.toString());
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("MONITORES.dat"))) {
            Articulo a = null;
            while ((a = (Articulo) ois.readObject()) != null) {
                articulos.put(a.getIdArticulo(), a);
                System.out.println(a);
            }
            System.out.println("Monitores importados con éxito.");
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.toString());
        }
        System.out.println("Articulos importados con éxito sección a sección");

    }

    public void listadonormalArticulos() { //Método para proporcionar un listado de los articulos para probar la persistencia
        System.out.println("LISTADO DE ARTICULOS:");
        for(Articulo a: articulos.values()){
            System.out.println(a);
        }

    }
}
