/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import mercasur.frm_menu;

/**
 *
 * @author STEVEN
 */
public class cl_venta_manual {

    cl_conectar c_conectar = new cl_conectar();
    cl_varios c_varios = new cl_varios();

    private int codigo;
    private String periodo;
    private String fecha;
    private int documento;
    private int serie;
    private int numero;
    private int cliente;
    private double total;
    private String concepto;

    public cl_venta_manual() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getDocumento() {
        return documento;
    }

    public void setDocumento(int documento) {
        this.documento = documento;
    }

    public int getSerie() {
        return serie;
    }

    public void setSerie(int serie) {
        this.serie = serie;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public double ver_ventas(JTable tabla, String query) {
        double total = 0;
        try {
            DefaultTableModel mostrar = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };

            Statement st = c_conectar.conexion();
            ResultSet rs = c_conectar.consulta(st, query);

            RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(mostrar);
            tabla.setRowSorter(sorter);

            mostrar.addColumn("Codigo");
            mostrar.addColumn("Fecha.");
            mostrar.addColumn("Documento");
            mostrar.addColumn("Cliente");
            mostrar.addColumn("Total S/");

            Object fila[] = new Object[5];
            while (rs.next()) {
                fila[0] = rs.getString("periodo") + c_varios.ceros_izquieda_numero(3, rs.getInt("codigo"));
                fila[1] = c_varios.formato_fecha_vista(rs.getString("fecha"));
                fila[2] = rs.getString("documento") + " | " + c_varios.ceros_izquieda_letras(4, rs.getString("serie")) + " - " + c_varios.ceros_izquieda_numero(7, rs.getInt("numero"));
                fila[3] = rs.getString("doc_cliente") + " | " + rs.getString("nombre_cliente");
                fila[4] = c_varios.formato_totales(rs.getDouble("total"));
                total = total + rs.getDouble("total");
                mostrar.addRow(fila);
            }
            tabla.setModel(mostrar);
            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        tabla.getColumnModel().getColumn(0).setPreferredWidth(70);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(70);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(110);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(450);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(47);
        c_varios.centrar_celda(tabla, 0);
        c_varios.centrar_celda(tabla, 1);
        c_varios.centrar_celda(tabla, 2);
        c_varios.derecha_celda(tabla, 4);

        return total;
    }

    public boolean comprobar_documento() {
        boolean existe = false;

        try {
            Statement st = c_conectar.conexion();
            String query = "select periodo, codigo "
                    + "from ventas_manuales "
                    + "where tipo_documento = '" + documento + "' and serie = '" + serie + "' and numero = '" + numero + "'";
            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                existe = true;
            }

            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

        return existe;
    }

    public int obtener_codigo() {
        int resultado = 0;

        try {
            Statement st = c_conectar.conexion();
            String query = "select ifnull(max(codigo) + 1, 1) as codigo from ventas_manuales where periodo = '" + periodo + "' ";
            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                resultado = rs.getInt("codigo");
            }

            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

        return resultado;
    }

    public boolean insertar() {
        boolean grabado = false;
        Statement st = c_conectar.conexion();
        String query = "insert into ventas_manuales "
                + "Values ('" + periodo + "', '" + codigo + "', '" + fecha + "', '" + documento + "', '" + serie + "', '" + numero + "', "
                + "'" + cliente + "', '" + total + "', current_time(), '" + frm_menu.c_empleado.getId_empleado() + "', '" + concepto + "')";
        int resultado = c_conectar.actualiza(st, query);

        if (resultado > -1) {
            grabado = true;
        }

        c_conectar.cerrar(st);

        return grabado;
    }
}
