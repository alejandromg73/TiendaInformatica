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
public class Cliente implements Comparable <Cliente>, Serializable{
     //Clase cliente
    private String dni;
    private String nombre;
    private String telefono;
    private String email;

    public Cliente(String dni, String nombre, String telefono, String email) {
        
        this.dni = dni;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Cliente{" + "dni=" + dni + ", nombre=" + nombre + ", telefono=" + telefono + ", email=" + email + '}';
    }
    
    @Override
    public int compareTo(Cliente c) {
        //Método compareTo para ordenar por orden alfabético
        return this.getNombre().compareTo(c.getNombre());
    }
    
    
}
