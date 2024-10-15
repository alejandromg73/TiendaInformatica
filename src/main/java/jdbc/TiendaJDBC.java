/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alu15d
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jdbc;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tiendainformaticaAlejandro2024.Articulo;
import tiendainformaticaAlejandro2024.Cliente;
import tiendainformaticaAlejandro2024.LineaPedido;
import tiendainformaticaAlejandro2024.Pedido;

/* SI EL PROYECTO SE HACE CON MAVEN, HAY QUE AÑADIR AL ARCHIVO pom.xml, en Project Files,
  las dependencias para que Maven nos incorpore al proyecto el driver JDBC para MySQL
 
    <dependencies>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.22</version>
        </dependency>
    </dependencies>
    
    SI EL PROYECTO SE HACE EN JAVA "NORMAL", tendremos que descargar nosotros el archivo .jar del driver JDBC para MYSQL
    (buscar en google) e incorporarlo con la opción add .jar file en la seccion LIBRARIES del proyecto

 */
        
public class TiendaJDBC {
    
    private ArrayList<Pedido> pedidos;
    private HashMap <String, Articulo> articulos;
    private HashMap <String, Cliente> clientes;
   
    public TiendaJDBC() {
        pedidos = new ArrayList();
        articulos= new HashMap();
        clientes = new HashMap();
    }
    
    public void cargaDatos(){
       clientes.put("11111111R",new Cliente("11111111R","ANA ","658111111","ana@gmail.com"));
       clientes.put("22222222H",new Cliente("22222222H","LOLA","649222222","lola@gmail.com"));
       clientes.put("33333333F",new Cliente("33333333F","JUAN","652333333","juan@gmail.com"));
              
       articulos.put("1-11",new Articulo("1-11","RATON LOGITECH",14,15));
       articulos.put("1-22",new Articulo("1-22","TECLADO STANDARD",9,18));
       articulos.put("2-11",new Articulo("2-11","HDD SEAGATE 1TB",16,80));
       articulos.put("2-22",new Articulo("2-22","SSD KINGSTOM 256GB",9,70));
       articulos.put("2-33",new Articulo("2-33","SSD KINGSTOM 512GB",15,120));
       articulos.put("3-11",new Articulo("3-11","EPSON ET2800     ",0,200));
       articulos.put("3-22",new Articulo("3-22","EPSON XP200      ",5,80));
       articulos.put("4-11",new Articulo("4-11","ASUS LED 22     ",5,100));
       articulos.put("4-22",new Articulo("4-22","HP LED 28      ",5,180));
       articulos.put("4-33",new Articulo("4-33","SAMSUNG ODISSEY G5",12,580));
       
       LocalDate hoy = LocalDate.now();
       pedidos.add(new Pedido("11111111R-001/2024",clientes.get("11111111R"),hoy.minusDays(1), new ArrayList<>
        (List.of(new LineaPedido("1-11",1),new LineaPedido("2-11",1)))));                                                                                                                                                               
       pedidos.add(new Pedido("11111111R-002/2024",clientes.get("11111111R"),hoy.minusDays(2), new ArrayList<>
        (List.of(new LineaPedido("4-11",14),new LineaPedido("4-22",4),new LineaPedido("4-33",4)))));
       pedidos.add(new Pedido("33333333F-001/2024",clientes.get("33333333F"),hoy.minusDays(3), new ArrayList<>
        (List.of(new LineaPedido("3-22",3),new LineaPedido("2-22",3)))));
       pedidos.add(new Pedido("33333333f-002/2024",clientes.get("33333333F"),hoy.minusDays(5), new ArrayList<>
        (List.of(new LineaPedido("4-33",3),new LineaPedido("2-11",3)))));
       pedidos.add(new Pedido("22222222H-001/2024",clientes.get("22222222H"),hoy.minusDays(4), new ArrayList<>
        (List.of(new LineaPedido("2-11",2),new LineaPedido("2-33",2),new LineaPedido("4-33",2)))));
    } 
    
    public static void main(String[] args) {
       
        TiendaJDBC t = new TiendaJDBC();
               
        /* PARA PROBAR QUE FUNCIONA BIEN SE RECOMIENDA EJECUTAR EL cargaDatos() CON LAS TABLAS DE LA BASE DE DATOS
           VACÍAS, Y A CONTINUACIÓN EL MÉTODO exportarDatos(), manteniendo el método importarDatos() comentado
        
        OBSERVAREMOS COMO LOS DATOS HAN SIDO EXPORTADOS A LA BASE DE DATOS
        Y LAS TABLAS CONTIENEN LOS DATOS DE LAS COLECCIONES 
        
        EN LA SIGUIENTE EJECUCIÓN YA SE PUEDE COMENTAR EL cargadatos() Y DESCOMENTAR EL MÉTODO importardatos() PARA VER SI 
        SE IMPORTAN CORECTAMENTE LOS DATOS DESDE LA BASE DE DATOS*/
        
        t.importarDatos();
        //t.cargaDatos();
        t.exportarDatos();
    }
    

    
    /******************************************************************************************
     *  MÉTODO PARA PASAR LOS DATOS DESDE LAS COLECCIONES JAVA A LAS TABLAS DE LA TIENDA MYSQL
    *******************************************************************************************/
    public void exportarDatos(){

        String consulta;
        
        /* ARTICULOS Y CLIENTES ES SENCILLO LLEVARLOS A LA BASES DE DATOS
        PORQUE PASAMOS LAS COLECCIONES OBJETO A OBJETO,HACIENDO UN INSERT PARA
        TRANSFORMAR CADA OBJETO EN UNA TUPLA DE LA TABLA CORRESPONDIENTE EN LA BD */
       
        for (Articulo a:articulos.values()){
            consulta = "INSERT INTO `articulos` (`idArticulo`, `descripcion`, `existencias`, `pvp`)"
                    + " VALUES ('" + a.getIdArticulo()+"', '"+a.getDescripcion()+"', '"+a.getExistencias()+"', '" + a.getPvp()+"')";
            try {
                PreparedStatement ps = Conexion.obtener().prepareStatement(consulta);
                ps.executeUpdate();
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println(e.toString());
            }
        }

        for (Cliente c:clientes.values()){
            consulta= "INSERT INTO `clientes` (`dni`, `nombre`, `telefono`, `email`)"
                    + " VALUES ('" + c.getDni()+"', '"+c.getNombre()+"','"+c.getTelefono()+"' , '" +c.getEmail()+"')";
            try {
                PreparedStatement ps = Conexion.obtener().prepareStatement(consulta);
                ps.executeUpdate();
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println(e.toString());
            }
        }

        /* PEDIDOS ES MÁS COMPLEJO PUES HAY QUE "ROMPER" LA COLECCIÓN PEDIDOS EN 2 TABLAS:
        pedidos y lineaspedido

        LOS OBJETOS SON MÁS RICOS QUE EL MODELO RELACIONAL, Y NUESTRA CLASE PEDIDO LA HEMOS HECHO ASÍ PARA 
        EXPLICAR Y ENTENDER LAS RELACIONES DE AGREGACIÓN Y COMPOSICIÓN ENTRE CLASES.
        PARA LLEVAR NUESTROS PEDIDOS "java" A UNA BD RELACIONAL, Y GUARDAR TODA LA INFO DE CADA PEDIDO 
        DEL ARRAYLIST JAVA HEMOS DE UTILIZAR 2 TABLAS POR SEPARADO EN LA BD */

        for (Pedido p:pedidos){
            String consulta1= "INSERT INTO `pedidos` (`idPedido`, `clientePedido`, `fechaPedido`)"
                    + " VALUES ('" + p.getIdPedido()+"', '"+p.getClientePedido().getDni()+"','"+p.getFechaPedido()+"')";
            for (LineaPedido l:p.getCestaCompra()){
                String consulta2= "INSERT INTO `lineaspedido` (`idPedido`, `idArticulo`, `unidades`)"
                    + " VALUES ('" + p.getIdPedido()+"', '"+l.getIdArticulo()+"','"+l.getUnidades()+"')";
                try {
                    PreparedStatement ps = Conexion.obtener().prepareStatement(consulta2);
                    ps.executeUpdate();
                } catch (ClassNotFoundException | SQLException e) {
                    System.out.println(e.toString());
                }
            }
            try {
                PreparedStatement ps = Conexion.obtener().prepareStatement(consulta1);
                ps.executeUpdate();
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println(e.toString());
            }
        }
    }   
    /******************************************************************************************
     *  MÉTODO PARA TRAER LOS DATOS DESDE LAS TABLAS DE LA TIENDA MYSQL A LAS COLECCIONES JAVA
    *******************************************************************************************/    
    public void importarDatos()
    {
        String consulta;
        Statement sentencia,sentencia2;

        /*LEER ARTICULOS DESDE LA BD Y CREAR EL HASHMAP ARTÍCULOS - "SELECT * FROM articulos" */
        
        consulta ="SELECT * FROM articulos";      
        try {
            sentencia = Conexion.obtener().createStatement();
            ResultSet rs=sentencia.executeQuery(consulta);
            while (rs.next())
            {
                articulos.put(rs.getString(1),
                new Articulo(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getDouble(4)));
            }
        }catch (ClassNotFoundException | SQLException e) {
                System.out.println(e.toString());
        }
        
        /* MOSTRAMOS LOS ARTÍCULOS POR PANTALLA PARA VERIFICAR QUE SE HAN IMPORTADO CORRECTAMENTE */
        for (Articulo a : articulos.values()) {
            System.out.println(a);
        }

        /*LEER CLIENTES DESDE LA BD Y CREAR EL HASHMAP CLIENTES - "SELECT * FROM clientes" */
        consulta="SELECT * FROM clientes";      
        try {
            sentencia = Conexion.obtener().createStatement();
            ResultSet rs=sentencia.executeQuery(consulta);
            while (rs.next())
            {
                clientes.put(rs.getString(1),
                new Cliente(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
            }
        }catch (ClassNotFoundException | SQLException e) {
                System.out.println(e.toString());
        }
        
        /* MOSTRAMOS LOS CLIENTES POR PANTALLA PARA VERIFICAR QUE SE HAN IMPORTADO CORRECTAMENTE */
        for (Cliente c : clientes.values()) {
            System.out.println(c);
        }
        /*LEER LOS PEDIDOS DESDE LA BD Y CREAR EL ARRAYLIST PEDIDOS. 
        ES MÁS COMPLICADO PUES LA INFORMACIÓN ESTÁ EN 2 TABLAS  */
        ArrayList <LineaPedido> cestaCompra = new ArrayList();
        String consulta1 ="SELECT * FROM pedidos";
        try {
            sentencia = Conexion.obtener().createStatement();
            ResultSet rs1=sentencia.executeQuery(consulta1);
            while (rs1.next())
            {
                String consulta2 ="SELECT * FROM lineaspedido WHERE idPedido='"+ rs1.getString(1)+"'";
                cestaCompra.clear();
                sentencia2 = Conexion.obtener().createStatement();
                ResultSet rs2=sentencia2.executeQuery(consulta2);
                while (rs2.next())
                {
                   cestaCompra.add(new LineaPedido(rs2.getString(2),rs2.getInt(3)));
                }
                pedidos.add(new Pedido(rs1.getString(1),clientes.get(rs1.getString(2)),LocalDate.parse(rs1.getString(3)),cestaCompra));
            }
        }catch (ClassNotFoundException | SQLException e) {
                System.out.println(e.toString());
        }
        /* MOSTRAMOS LOS PEDIDOS POR PANTALLA PARA VERIFICAR QUE SE HAN IMPORTADO CORRECTAMENTE */
        for (Pedido p: pedidos) {
            System.out.println(p);
            for (LineaPedido l:p.getCestaCompra()){
                 System.out.println(l);
            }
            System.out.println();
        }
    }

}
