/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package render_tables;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author lubricante
 */
public class render_productos extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
        // SI EN CADA FILA DE LA TABLA LA CELDA 5 ES IGUAL A ACTIVO COLOR AZUL
        if (String.valueOf(table.getValueAt(row, 7)).equals("NORMAL")) {
            setBackground(Color.white);
            setForeground(Color.black);
        }
        if (String.valueOf(table.getValueAt(row, 7)).equals("POR TERMINAR")) {
            setBackground(Color.GREEN);
            setForeground(Color.white);
        }
        if (String.valueOf(table.getValueAt(row, 7)).equals("NO DISPONIBLE")) {
            setBackground(Color.RED);
            setForeground(Color.WHITE);
        }
        
        if (String.valueOf(table.getValueAt(row, 7)).equals("NO ACTIVO")) {
            setBackground(Color.BLACK);
            setForeground(Color.WHITE);
        }
        
        if (column == 0) {
            setHorizontalAlignment(SwingConstants.CENTER);
        }
        if (column == 1) {
            setHorizontalAlignment(SwingConstants.LEFT);
        }
        if (column == 2) {
            setHorizontalAlignment(SwingConstants.CENTER);
        }
        if (column == 3) {
            setHorizontalAlignment(SwingConstants.RIGHT);
        }
        if (column == 4) {
            setHorizontalAlignment(SwingConstants.CENTER);
        }
        if (column == 5) {
            setHorizontalAlignment(SwingConstants.RIGHT);
        }
        if (column == 6) {
            setHorizontalAlignment(SwingConstants.CENTER);
        }
        
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        return this;
    }

}
