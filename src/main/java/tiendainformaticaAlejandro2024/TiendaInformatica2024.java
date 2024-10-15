package tiendainformaticaAlejandro2024;

/**
 *
 * @author alu15d
 */
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class TiendaInformatica2024 {

    Scanner sc = new Scanner(System.in);
    private ArrayList<Pedido> pedidos;
    private HashMap<String, Cliente> clientes;
    private HashMap<String, Articulo> articulos;
    LocalDate hoy = LocalDate.now();

    //Método constructor de la aplicación
    public TiendaInformatica2024() {
        this.clientes = new HashMap();
        this.pedidos = new ArrayList();
        this.articulos = new HashMap();
    }

    public static void main(String[] args) {
        TiendaInformatica2024 miTienda = new TiendaInformatica2024();
        //miTienda.importarObjetos();
        miTienda.cargaDatos();
        miTienda.menuprincipal();
    }

    //<editor-fold defaultstate="collapsed" desc="MENÚS">
    public void menuprincipal() {//Método menú principal
        int opcion = 0;
        do {
            System.out.println("\t\t\t\t1-GESTIÓN DE ARTICULOS");
            System.out.println("\t\t\t\t2-GESTIÓN DE CLIENTES");
            System.out.println("\t\t\t\t3-GESTIÓN DE PEDIDOS");
            System.out.println("\t\t\t\t4-COPIA DE SEGURIDAD");
            System.out.println("\t\t\t\t9-SALIR");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1: {
                    menuArticulos();
                    break;
                }
                case 2: {
                    menuClientes();
                    break;
                }
                case 3: {
                    menuPedidos();
                    break;
                }
                case 4: {
                    //copiaSeguridad();
                }
            }
        } while (opcion != 9);
    }

    public void menuArticulos() { //Método submenú articulos
        int opcion = 0;
        do {
            System.out.println("\t\t\t\tGESTIÓN DE ARTICULOS");
            System.out.println("\t\t\t\t1-AÑADIR ARTICULOS");
            System.out.println("\t\t\t\t2-BORRAR ARTICULOS");
            System.out.println("\t\t\t\t3-LISTAR ARTICULOS");
            System.out.println("\t\t\t\t4-REPONER ARTICULOS");
            System.out.println("\t\t\t\t9-VOLVER");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1: {
                    altaArticulo();
                    break;
                }
                case 2: {
                    borraArticulo();
                    break;
                }
                case 3: {
                    listarArticulos();
                    break;
                }
                case 4: {
                    reponeArticulo();
                    break;
                }
            }
        } while (opcion != 9);
    }

    public void menuClientes() { //Método submenú clientes
        int opcion = 0;
        do {
            System.out.println("\t\t\t\tGESTIÓN DE CLIENTES");
            System.out.println("\t\t\t\t1-AÑADIR CLIENTES");
            System.out.println("\t\t\t\t2-BORRAR CLIENTES");
            System.out.println("\t\t\t\t3-LISTAR CLIENTES");
            System.out.println("\t\t\t\t4-MODIFICAR CLIENTES");
            System.out.println("\t\t\t\t9-VOLVER");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1: {
                    altaCliente();
                    break;
                }
                case 2: {
                    borraCliente();
                    break;
                }
                case 3: {
                    listaClientes();
                    break;
                }
                case 4: {
                    modificaCliente();
                    break;
                }
            }
        } while (opcion != 9);
    }

    public void menuPedidos() { //Método submenú pedidos
        int opcion = 0;
        do {
            System.out.println("\t\t\t\tGESTIÓN DE PEDIDOS");
            System.out.println("\t\t\t\t1-ALTA PEDIDO");
            System.out.println("\t\t\t\t2-LISTADOS DE PEDIDOS");
            System.out.println("\t\t\t\t9-VOLVER");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1: {
                    altaPedido();
                    break;
                }
                case 2: {
                    listarPedidos();
                    break;
                }

            }
        } while (opcion != 9);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="MÉTODOS AUXILIARES">
    public int buscarDni(String dni) throws DniNoEncontrado{ //Método para buscar el dni de un cliente
        int buscarDni = -1;
        int i = 0;
        for (Cliente c : clientes.values()) {
            if (c.getDni().equals(dni)) {//Si el dni coincide
                buscarDni = i;
                break;
            }
            i++;
        }
        if (i == 0) {
            throw new DniNoEncontrado("No se ha encontrado el dni: " + clientes.get(dni).getDni());
        
    }
        return buscarDni;
    }

    /*Si el DNI no existe, el método devuelve un -1
        Si el DNI existe, el método devuelve un 0
     */
    public int buscarId(String id) { //Método para buscar el id de un articulo
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
        /*Si el id no existe, el método devuelve un -1
        Si el id existe, el método devuelve un 0
         */
    }

    //Métodos para la validación de datos de tipo int y double
    /*Estos métodos son útiles para la entrada de datos al sistema
    Se pide un String por teclado, y se valida que el dato sea de un tipo correcto, en este caso, o int o Double
     */
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

    /*Método cargaDatos para tener en la aplicación datos para probar las funcionalidades.
    Los datos ahora se guardan en archivos gracias a la persistencia, por lo que este método cargaDatos ya no se usa
     */
    public void cargaDatos() {
        clientes.put("58433121B", new Cliente("58433121B", "ALEJANDRO", "644749230", "alejandromg@educastur.es"));
        clientes.put("10894650X", new Cliente("10894650X", "PEPE", "649869204", "pepe@gmail.com"));
        clientes.put("10884992J", new Cliente("10884992J", "JUAN", "606750732", "juan@gmail.com"));
        clientes.put("23944933R", new Cliente("23944933R", "EDUARDO", "676767676", "eduardo@educastur.es"));
        clientes.put("85750999F", new Cliente("85750999F", "ANGEL", "767676767", "angel@gmail.com"));
        clientes.put("79511448B", new Cliente("79511448B", "HUGO", "654456654", "hugo@gmail.com"));
        clientes.put("34293429S", new Cliente("34293429S", "PELAYO", "611223344", "pelayo@gmail.com"));

        articulos.put("1-11", new Articulo("1-11", "RATON LOGITECH", 14, 15));
        articulos.put("1-22", new Articulo("1-22", "TECLADO STANDARD", 9, 18));
        articulos.put("2-11", new Articulo("2-11", "HDD SEAGATE 1TB", 16, 80));
        articulos.put("2-22", new Articulo("2-22", "SSD KINGSTOM 256GB", 9, 70));
        articulos.put("2-33", new Articulo("2-33", "SSD KINGSTOM 512GB", 15, 120));
        articulos.put("3-11", new Articulo("3-11", "EPSON ET2800     ", 0, 200));
        articulos.put("3-22", new Articulo("3-22", "EPSON XP200      ", 5, 80));
        articulos.put("4-11", new Articulo("4-11", "ASUS LED 22     ", 9, 100));
        articulos.put("4-22", new Articulo("4-22", "HP LED 28      ", 7, 180));
        articulos.put("4-33", new Articulo("4-33", "SAMSUNG ODISSEY G5", 12, 580));
        articulos.put("5-11", new Articulo("5-11", "INTEL I9 10th GEN", 9, 780));
        articulos.put("1-33", new Articulo("1-33", "ALTAVOCES GENIUS", 11, 50));
        articulos.put("5-22", new Articulo("5-22", "RYZEN 9", 9, 810));
        articulos.put("1-44", new Articulo("1-44", "TECLADO LED", 10, 24));
        articulos.put("5-33", new Articulo("5-33", "INTEL CELERON", 0, 310));

        LocalDate hoy = LocalDate.now();
        pedidos.add(new Pedido("58433121B-001/2024", clientes.get("58433121B"), hoy.minusDays(1), new ArrayList<>(List.of(new LineaPedido("1-11", 1), new LineaPedido("2-11", 1)))));
        pedidos.add(new Pedido("10894650X-002/2024", clientes.get("10894650X"), hoy.minusDays(2), new ArrayList<>(List.of(new LineaPedido("4-11", 14), new LineaPedido("4-22", 4), new LineaPedido("4-33", 4)))));
        pedidos.add(new Pedido("85750999F-001/2024", clientes.get("85750999F"), hoy.minusDays(3), new ArrayList<>(List.of(new LineaPedido("3-22", 3), new LineaPedido("2-22", 3)))));
        pedidos.add(new Pedido("10884992J-002/2024", clientes.get("10884992J"), hoy.minusDays(5), new ArrayList<>(List.of(new LineaPedido("4-33", 3), new LineaPedido("2-11", 3)))));
        pedidos.add(new Pedido("79511448B-001/2024", clientes.get("79511448B"), hoy.minusDays(4), new ArrayList<>(List.of(new LineaPedido("2-11", 2), new LineaPedido("2-33", 2), new LineaPedido("4-11", 2)))));
        pedidos.add(new Pedido("23944933R-001/2024", clientes.get("23944933R"), hoy.minusDays(4), new ArrayList<>(List.of(new LineaPedido("1-11", 2), new LineaPedido("2-33", 1), new LineaPedido("4-33", 4)))));

    }

    public static boolean validarDNI(String dni) { //Método para verificar que el DNI tiene un formato válido
        if (dni.isBlank() || !dni.matches("\\d{8}[A-HJ-NP-TV-Z]")) {
            return false;
        }

        // Extraer el número y la letra del DNI
        String numeroStr = dni.substring(0, 8);
        char letra = dni.charAt(8);

        // Calcular la letra correspondiente al número del DNI
        char letraCalculada = calcularLetraDNI(Integer.parseInt(numeroStr));

        // Comparar la letra calculada con la letra proporcionada
        return letra == letraCalculada;
    }

    private static char calcularLetraDNI(int numero) { //Método para calcular la letra del DNI que corresponde
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        return letras.charAt(numero % 23);
    }

    public void stock(int unidadesPed, String id) throws StockAgotado, StockInsuficiente {
        /*Método para comprobar si hay stock de los articulos al realizar un pedido
        Se lanzan dos excepciones: stock agotado y stock insuficiente
        Tras lanzarse la opcion de stock insuficiente, se da la opcion al usuario de seguir comprando las unidades que haya disponibles
         */
        int n = articulos.get(id).getExistencias();
        if (n == 0) {
            throw new StockAgotado("Stock agotado para el articulo: " + articulos.get(id).getDescripcion());
        } else if (n < unidadesPed) {
            throw new StockInsuficiente("No hay stock suficiente para el articulo" + articulos.get(id).getDescripcion() + "Me pide " + unidadesPed + " y solo se dispone de : " + n);
        }
    }

    public double totalPedido(Pedido p) { //Método para calcular el total de un pedido
        //Uso de DecimalFormat para controlar el número de decimales de los totales de pedido.
        DecimalFormat df = new DecimalFormat("#.00");
        double total = 0;
        for (LineaPedido l : p.getCestaCompra()) {
            total += (articulos.get(l.getIdArticulo()).getPvp()) * l.getUnidades();
        }
        return total;
    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GESTIÓN DE ARTICULOS">
    public void altaArticulo() { //Método para dar de alta a un articulo nuevo
        sc.nextLine();
        String idArticulo, existencias, pvp, descripcion;
        System.out.println("ALTA DE UN ARTICULO");
        do {
            System.out.println("IdArticulo: ");
            idArticulo = sc.nextLine();
        } while (!idArticulo.matches("[1-5][-][0-9][0-9]") || articulos.containsKey(idArticulo)); //Expresión regular idArticulo
        System.out.println("descripción: ");
        descripcion = sc.nextLine();
        //Las existencias se piden como String al usuario, y solo acepta si es int
        do {
            System.out.println("existencias:");
            existencias = sc.nextLine();
        } while (!esInt(existencias));

        //El pvp se pide como String al usuario, y solo lo acepta si es Double
        do {
            System.out.println("PVP:");
            pvp = sc.nextLine();
        } while (!esDouble(pvp));
        articulos.put(idArticulo, new Articulo(idArticulo, descripcion, Integer.parseInt(existencias), Double.parseDouble(pvp)));
        System.out.println("Articulo añadido.");
    }

    public void borraArticulo() { //Método para eliminar un articulo
        sc.nextLine();
        System.out.println("BAJA DE UN ARTICULO");
        String idArticulo;
        do {
            System.out.println("IdArticulo que quieres eliminar: ");
            idArticulo = sc.nextLine();
        } while (!idArticulo.matches("[1-5][-][0-9][0-9]")); //Expresión regular idArticulo
        //Si el idArticulo se encuentra en alguno de los articulos del HashMap, el articulo que corresponda, se elimina
        if (articulos.containsKey(idArticulo)) {
            articulos.remove(idArticulo);
            System.out.println("Articulo eliminado.");
        } else {
            //Si no existe, se muestra un mensaje de que no se puede borrar
            System.out.println("No existe articulo con ese id, no se puede borrar.");
        }
    }

    public void listarArticulos() { //Método para listar los articulos
        sc.nextLine();
        String opcion;
        do {
            System.out.println("\n\n\n\n\n\t\t\t\tLISTAR ARTICULOS\n");
            System.out.println("\t\t\t\t0 - TODOS LOS ARTICULOS");
            System.out.println("\t\t\t\t1 - PERIFERICOS");
            System.out.println("\t\t\t\t2 - ALMACENAMIENTO");
            System.out.println("\t\t\t\t3 - IMPRESORAS");
            System.out.println("\t\t\t\t4 - MONITORES");
            System.out.println("\t\t\t\t5 - COMPONENTES");
            System.out.println("\t\t\t\t6 - SALIR");
            do {
                opcion = sc.next();
            } while (!opcion.matches("[0-6]"));
            if (!opcion.equals("6")) {
                listados(opcion);
            }
        } while (!opcion.equals("6"));
    }

    public void listados(String seccion) { //Método que lista los articulos según su seccion haciendo uso de Streams
        String[] secciones = {"TODAS", "PERIFERICOS", "ALMACENAMIENTO", "IMPRESORAS", "MONITORES", "COMPONENTES"};
        sc.nextLine();
        System.out.println("Pulsa return para mostrarlo por orden normal (idArticulo). Para ordenar por precio: < a >(-) > a <(+)");
        String opcion = sc.nextLine();
        if (seccion.equals("0")) {
            if (opcion.isBlank()) {
                articulos.values().stream().sorted().forEach(System.out::println);

            } else if (opcion.equals("-")) {
                articulos.values().stream().sorted(new ComparaArticuloPorPrecio()).forEach(System.out::println);
            } else if (opcion.equals("+")) {
                articulos.values().stream().sorted(new ComparaArticuloPorPrecio().reversed()).forEach(System.out::println);
            }
        } else {
            System.out.println("LISTADO DE LOS ARTICULOS DE LA SECCION (" + secciones[Integer.parseInt(seccion)] + ")");
            if (opcion.isBlank()) {
                articulos.values().stream().filter(a -> a.getIdArticulo().startsWith(seccion)).sorted().forEach(System.out::println);
            } else if (opcion.equals("-")) {
                articulos.values().stream().filter(a -> a.getIdArticulo().startsWith(seccion))
                        .sorted(new ComparaArticuloPorPrecio()).forEach(System.out::println);
            } else if (opcion.equals("+")) {
                articulos.values().stream().filter(a -> a.getIdArticulo().startsWith(seccion))
                        .sorted(new ComparaArticuloPorPrecio().reversed()).forEach(System.out::println);
            }

        }
    }

    public void reponeArticulo() {//Método para reponer unidades de artículos. Primero muestra un listado de los articulos de los que no hay disponibilidad.
        //Lo primero que hace el método es mostrar una lista con los articulos de los que no hay stock
        sc.nextLine();
        String idArticulo, udrep;
        System.out.println("Listado de articulos con 0 unidades: ");
        System.out.println();
        for (Articulo a : articulos.values()) {
            if (a.getExistencias() == 0) {
                System.out.println(a);
            }
        }
        System.out.println();
        do {
            System.out.println("IdArticulo del articulo a reponer: ");
            idArticulo = sc.nextLine();
        } while (!idArticulo.matches("[1-5][-][0-9][0-9]") || !articulos.containsKey(idArticulo));

        //Las unidades a reponer se piden como String y se verifica si lo que introduce el cliente es un int
        do {
            System.out.println("Número de unidades a reponer: ");
            udrep = sc.nextLine();
        } while (!esInt(udrep));
        if (udrep.length() == 0) {
            return;
        }
        //Se convierten las udrep a int y se actualiza el atributo existencias
        articulos.get(idArticulo).setExistencias(articulos.get(idArticulo).getExistencias() + Integer.parseInt(udrep));
        System.out.println("Articulo repuesto. Ahora tenemos " + articulos.get(idArticulo).getExistencias() + " unidades " + "del artículo " + idArticulo);
    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GESTIÓN DE CLIENTES">
    public void altaCliente() { //Método para dar de alta a un cliente nuevo
        sc.nextLine();
        System.out.println("ALTA DE UN CLIENTE");
        String dni;
        do {
            System.out.println("Introduce dni: ");
            dni = sc.nextLine();
        } while (!validarDNI(dni)); //Expresión regular validar dnis españoles
        System.out.println("Introduce nombre: ");
        String nombre = sc.nextLine();
        String telefono;
        do {
            System.out.println("Introduce número de teléfono: ");
            telefono = sc.nextLine();
        } while (!telefono.matches("[6-7-9][0-9]{8}")); //Expresión regular validar telefonos

        String email;
        do {
            System.out.println("Introduce email: ");
            email = sc.nextLine();
        } while (!email.matches("^(.+)@(.+)$")); //Expresión regular validar emails
        clientes.put(dni, new Cliente(dni, nombre, telefono, email));
        System.out.println("Cliente añadido.");

    }

    public void borraCliente() { //Método para dar eliminar a un cliente
        sc.nextLine();
        System.out.println("BAJA DE UN CLIENTE");
        String dni;
        do {
            System.out.println("Introduce dni del cliente a borrar: ");
            dni = sc.nextLine();
        } while (!validarDNI(dni)); //Expresión regular validar dnis españoles
        /*Si hay algún cliente con el dni que se ha introducido por teclado, se elimina ese cliente del HashMap
        Sino, se muestra un mensaje de que el cliente no está en la aplicación.
         */
        if (clientes.containsKey(dni)) {
            clientes.remove(dni);
            System.out.println("Cliente eliminado.");
        } else {
            System.out.println("No existe el cliente.");
        }

    }

    public void listaClientes() { //Método para proporcionar un listado de los clientes
        //Se usa el API de Streams
        System.out.println("LISTADO DE CLIENTES:");
        clientes.values().stream().sorted().forEach(System.out::println);

    }

    public void modificaCliente() { //Método para modificar el telefono o el email del cliente
        /*Lo primero que se pregunta es el atributo que se quiere modificar, el email o el telefono
        Luego el método, según lo que le haya indicado el cliente, continua por una rama u otra del case
         */
        sc.nextLine();
        String dni;
        do {
            System.out.println("Introduce dni del cliente a modificar: ");
            dni = sc.nextLine();
        } while (!clientes.containsKey(dni));
        System.out.println("Pulsa: 1-MODIFICAR EL TELEFONO. 2-MODIFICAR EL EMAIL: ");
        int opcion = sc.nextInt();
        switch (opcion) {
            case 1: {
                String NuevoTelefono;
                do {
                    System.out.println("Introduce el nuevo número de teléfono: ");
                    NuevoTelefono = sc.nextLine();
                } while (!NuevoTelefono.matches("[6-7-9][0-9]{8}")); //Expresión regular validar telefonos
                clientes.get(dni).setTelefono(NuevoTelefono);
                System.out.println("Nuevo telefono guardado correctamente");
                break;
            }
            case 2: {
                String NuevoEmail;
                do {
                    System.out.println("Introduce el nuevo email: ");
                    NuevoEmail = sc.nextLine();
                } while (!NuevoEmail.matches("^(.+)@(.+)$")); //Expresión regular validar emails
                clientes.get(dni).setEmail(NuevoEmail);
                System.out.println("Nuevo email guardado correctamente");
                break;
            }

        }

    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GESTIÓN DE PEDIDOS">
    public void altaPedido() { //Método para hacer un pedido
        Scanner sc = new Scanner(System.in);
        ArrayList<LineaPedido> pedidoAux = new ArrayList<>();
        LocalDate hoy = LocalDate.now();
        String dni;
        String unidades;
        int uds = 0; //Variable unidades para luego transformarlas a int
        do {
            System.out.println("Introduce dni de cliente: ");
            dni = sc.nextLine();
        } while (!validarDNI(dni) || !clientes.containsKey(dni)); //Expresión regular para validar DNI españoles

        System.out.println("Se van a introducir los artículos 1 A 1: ");
        String idArticulo;
        do {
            System.out.println("Introduce código de artículo (99 PARA TERMINAR): ");
            idArticulo = sc.nextLine();
            if (idArticulo.equals("99")) {
                break;
            }
            while (!articulos.containsKey(idArticulo)) {
                System.out.println("El código de artículo no existe. Introduce uno válido: ");
                idArticulo = sc.nextLine();
            }
            try {
                do {
                    System.out.println("Cuántas unidades quieres?:");
                    unidades = sc.nextLine();
                } while (!esInt(unidades)); //Las unidades solo pueden ser int
                //Paso las unidades a int
                uds = Integer.parseInt(unidades);
                //Llamo al método de comprobar el stock pasándole las unidades y el id del artículo a comprobar
                stock(uds, idArticulo);

                pedidoAux.add(new LineaPedido(idArticulo, uds));
                //Modifico las unidades tras hacer pedido
                articulos.get(idArticulo).setExistencias(articulos.get(idArticulo).getExistencias() - uds);
            } catch (StockAgotado e) {
                System.out.println(e.getMessage());
            } catch (StockInsuficiente e) {
                System.out.println(e.getMessage());
                int disponibles = articulos.get(idArticulo).getExistencias();
                System.out.println("¿Quieres las " + disponibles + " unidades disponibles? (S/N)");
                String opcion = sc.nextLine();
                if (opcion.equalsIgnoreCase("S")) {
                    pedidoAux.add(new LineaPedido(idArticulo, disponibles));
                    //Modifico las unidades tras hacer pedido
                    articulos.get(idArticulo).setExistencias(articulos.get(idArticulo).getExistencias() - disponibles);
                }
            }
        } while (!idArticulo.equals("99"));

        //Recorro toda la cesta de la compra e imprimo los detalles del pedido
        System.out.println("PEDIDO REALIZADO CON ÉXITO.");

        for (LineaPedido l : pedidoAux) {
            System.out.println(articulos.get(l.getIdArticulo()).getDescripcion() + " ----> " + l.getUnidades() + " unidades");
        }
        hoy = LocalDate.now();
        pedidos.add(new Pedido(generaIdPedido(dni), clientes.get(dni), hoy, pedidoAux));

    }

    public String generaIdPedido(String idCliente) { //Método para generar número de pedido de forma automática
        int contador = 0;
        String nuevoId;
        for (Pedido p : pedidos) {
            if (p.getClientePedido().getDni().equalsIgnoreCase(idCliente)) {
                contador++;
            }
        }
        contador++;
        nuevoId = idCliente + "-" + String.format("%03d", contador) + "/" + LocalDate.now().getYear();
        return nuevoId;
    }

    public void listarPedidos() { //Método para listar los pedidos de distintas formas
        int opcion = 0;
        do {
            System.out.println("\t\t\t\tLISTADOS DE PEDIDOS");
            System.out.println("\t\t\t\t1-PEDIDOS ORDENADOS POR IMPORTE TOTAL");
            System.out.println("\t\t\t\t2-PEDIDOS ORDENADOS PARA UN CLIENTE CONCRETO");
            System.out.println("\t\t\t\t3-PEDIDOS ORDENADOS POR FECHA");
            System.out.println("\t\t\t\t4-PEDIDOS ORDENADOS A PARTIR DE UN CIERTO IMPORTE");
            System.out.println("\t\t\t\t9-VOLVER");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1: {
                    pedidosOrdenadosImporteTotal();
                    break;
                }
                case 2: {
                    pedidosOrdenadosParaClienteConcreto();
                    break;
                }
                case 3: {
                    pedidosOrdenadosFecha();
                    break;
                }
                case 4: {
                    pedidosApartirImporte();
                    break;
                }
            }
        } while (opcion != 9);
    }

    public void pedidosOrdenadosImporteTotal() { //Método que ordena los pedidos por importe total usando Streams (ordenados de mayor precio a menor precio)
        pedidos.stream().sorted(Comparator.comparing(p -> totalPedido((Pedido) p)).reversed()).forEach(p -> System.out.println("IMPORTE TOTAL: " + " - " + totalPedido(p) + "€" + "  -  " + p.getIdPedido() + "  -  " + p.getFechaPedido() + "  -  " + p.getClientePedido().getNombre()));
    }

    public void pedidosOrdenadosParaClienteConcreto() {//Método que lista los pedidos para un cliente concreto introducido por teclado ordenados por total de pedido usando Streams
        //Aunque no se pedía este método, me pareció interesante añadirlo para practicar los listados de pedidos usando streams
        sc.nextLine();
        System.out.println("Introduce nombre del cliente: ");
        String dni = sc.nextLine();
        pedidos.stream().filter(p -> p.getClientePedido().getNombre().equalsIgnoreCase(dni)).sorted(Comparator.comparingDouble(p -> totalPedido(p))).forEach(p -> System.out.println(p + " - Total del pedido: " + totalPedido(p) + "€"));
    }

    public void pedidosApartirImporte() { //Método que lista todos los pedidos a partir de un importe usando Streams
        String min;
        //La cantidad mínima se pide como String
        do {
            System.out.println("Introduce el importe mínimo en € para listar los pedidos a partir de ese importe: ");
            min = sc.nextLine();
        } while (!esDouble(min));
        //Solo se permite la entrada del valor al sistema si es un Double.

        double minDouble = Double.parseDouble(min);
        /*
        Convierto el importe a Double. Esto lo hago porque para poder usar la variable en el Stream
        para comparar con el total del pedido, la variable tiene que ser un Double sí o sí, al ser el pedido un Double.
        No se pueden comparar Doubles y Strings, ambos tienen que ser el mismo tipo de dato.
         */

        pedidos.stream().filter(p -> totalPedido(p) >= minDouble).sorted(Comparator.comparingDouble(p -> totalPedido(p))).forEach(p -> System.out.println("Total del pedido: " + totalPedido(p) + "€" + "   -   " + p.getIdPedido() + "   -   " + p.getFechaPedido() + "   -   " + p.getClientePedido().getNombre()));
    }

    public void pedidosOrdenadosFecha() { //Método que lista los pedidos ordenados por fecha usando Streams
        //Los pedidos están ordenados por fecha de más recientes a menos recientes
        pedidos.stream().sorted(Comparator.comparing(Pedido::getFechaPedido).reversed()).forEach(p -> System.out.println(p.getFechaPedido() + "  -  " + p.getIdPedido() + "   -   " + p.getClientePedido().getNombre() + "   -   " + "IMPORTE TOTAL: " + " - " + totalPedido(p) + "€"));
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="PERSISTENCIA">
    public void copiaSeguridad() { //Método para realizar una copia de seguridad. 
        try (ObjectOutputStream oosPerifericos = new ObjectOutputStream(new FileOutputStream("PERIFERICOS.dat")); ObjectOutputStream oosComponentes = new ObjectOutputStream(new FileOutputStream("COMPONENTES.dat")); ObjectOutputStream oosAlmacenamiento = new ObjectOutputStream(new FileOutputStream("ALMACENAMIENTO.dat")); ObjectOutputStream oosImpresoras = new ObjectOutputStream(new FileOutputStream("IMPRESORAS.dat")); ObjectOutputStream oosMonitores = new ObjectOutputStream(new FileOutputStream("MONITORES.dat"))) {
            for (Articulo a : articulos.values()) {
                switch (a.getIdArticulo().charAt(0)) {
                    case '1': {
                        oosPerifericos.writeObject(a);
                    }
                    case '2': {
                        oosAlmacenamiento.writeObject(a);
                    }
                    case '3': {
                        oosImpresoras.writeObject(a);
                    }
                    case '4': {
                        oosMonitores.writeObject(a);
                    }
                    case '5': {
                        oosComponentes.writeObject(a);
                    }
                }
            }
            System.out.println("Copia de seguridad de articulos realizada con éxito.");
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        } catch (IOException e) {
            System.out.println(e.toString());
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("clientes.dat"))) {
            oos.writeObject(clientes);
            System.out.println("Copia de seguridad de clientes realizada con éxito.");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        clientesCsv();
        /*Llamada al método clientesCsv, para que, además de guardar los clientes en los archivos binarios, 
        los guarde en el archivo csv separado por comas para manejarlos con aplicaciones ofimáticas*/
         

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("pedidos.dat"))) {
            oos.writeObject(pedidos);
            System.out.println("Copia de seguridad de pedidos realizada con éxito.");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public void importarObjetos() { //Método para traer de vuelta a la aplicación los datos que estaban almacenados en los archivos.
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("pedidos.dat"))) {
            pedidos = (ArrayList<Pedido>) ois.readObject();
            System.out.println("Pedidos importados con éxito.");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("clientes.dat"))) {
            clientes = (HashMap<String, Cliente>) ois.readObject();
            System.out.println("Clientes importados con éxito.");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("PERIFERICOS.dat"))) {
            articulos = (HashMap<String, Articulo>) ois.readObject();
            System.out.println("Perifericos importados con éxito.");
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.toString());
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("ALMACENAMIENTO.dat"))) {
            articulos = (HashMap<String, Articulo>) ois.readObject();
            System.out.println("Almacenamiento importados con éxito.");
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.toString());
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("IMPRESORAS.dat"))) {
            articulos = (HashMap<String, Articulo>) ois.readObject();
            System.out.println("Impresoras importados con éxito.");

        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.toString());
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("MONITORES.dat"))) {
            articulos = (HashMap<String, Articulo>) ois.readObject();
            System.out.println("Monitores importados con éxito.");
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.toString());
        }
        System.out.println("Articulos importados con éxito sección a sección");
    
    }

    public void clientesCsv() { //Método que guarda los clientes en un archivo .csv (dentro de una subcarpeta clientes)
        String rutaArchivo = "clientes/clientes.csv";
        try (BufferedWriter bfwClientes = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Cliente c : clientes.values()) {
                bfwClientes.write(c.getDni() + "," + c.getNombre() + "," + c.getEmail() + "," + c.getTelefono()); //Los datos del cliente separados por comas
                bfwClientes.newLine();
            }
            bfwClientes.close();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
    
    

//</editor-fold>
}
