/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tiendainformaticaAlejandro2024;

import java.util.Comparator;

/**
 *
 * @author alu15d
 */
public class ComparaArticuloPorPrecio implements Comparator<Articulo> {

    @Override
    public int compare(Articulo a1, Articulo a2) {
        return Double.compare(a1.getPvp(),a2.getPvp());

    }

}
