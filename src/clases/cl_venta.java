
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

/**
 *
 * @author CALIDAD
 */
public class cl_venta {

    cl_conectar c_conectar = new cl_conectar();
    cl_varios c_varios = new cl_varios();

    private String periodo;
    private int id_venta;
    private String fecha;
    private int id_cliente;
    private int id_zona;
    private int id_documento;
    private String serie_doc;
    private int numero_doc;
    private double total;
    private double pagado;
    private int estado;
    private String fecha_registro;
    private int id_empleado;

    public cl_venta() {
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_zona() {
        return id_zona;
    }

    public void setId_zona(int id_zona) {
        this.id_zona = id_zona;
    }

    public int getId_documento() {
        return id_documento;
    }

    public void setId_documento(int id_documento) {
        this.id_documento = id_documento;
    }

    public String getSerie_doc() {
        return serie_doc;
    }

    public void setSerie_doc(String serie_doc) {
        this.serie_doc = serie_doc;
    }

    public int getNumero_doc() {
        return numero_doc;
    }

    public void setNumero_doc(int numero_doc) {
        this.numero_doc = numero_doc;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getPagado() {
        return pagado;
    }

    public void setPagado(double pagado) {
        this.pagado = pagado;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public boolean insertar() {
        boolean grabado = false;
        Statement st = c_conectar.conexion();
        String query = "insert into ventas "
                + "Values ('" + periodo + "', '" + id_venta + "', '" + fecha + "', '" + id_cliente + "', '" + id_zona + "', '" + id_documento + "', "
                + "'" + serie_doc + "', '" + numero_doc + "', '" + total + "', '0', '1', current_time(), "
                + "'" + id_empleado + "')";
        int resultado = c_conectar.actualiza(st, query);

        if (resultado > -1) {
            grabado = true;
        }

        c_conectar.cerrar(st);

        return grabado;
    }

    public int obtener_codigo() {
        int resultado = 0;

        try {
            Statement st = c_conectar.conexion();
            String query = "select ifnull(max(id_venta) + 1, 1) as codigo "
                    + "from ventas "
                    + "where periodo = '" + periodo + "' ";
            ResultSet rs = c_conectar.consulta(st, query);
            System.out.println(query);
            while (rs.next()) {
                id_venta = rs.getInt("codigo");
            }

            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

        return resultado;
    }

    public boolean eliminar() {
        boolean grabado = false;
        Statement st = c_conectar.conexion();
        String query = "update ventas set estado = '2', total = '0' "
                + "where periodo = '" + periodo + "' and id_venta = '" + id_venta + "'";
        int resultado = c_conectar.actualiza(st, query);

        if (resultado > -1) {
            grabado = true;
        }

        c_conectar.cerrar(st);

        return grabado;
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

            mostrar.addColumn("Codigo"); //periodo + id
            mostrar.addColumn("Fecha");
            mostrar.addColumn("Documento");
            mostrar.addColumn("Zona");
            mostrar.addColumn("Cliente");
            mostrar.addColumn("Monto");
            mostrar.addColumn("Estado");
            mostrar.addColumn("periodo");
            mostrar.addColumn("id_venta");
            //Creando las filas para el JTable
            while (rs.next()) {
                total += rs.getDouble("total");
                Object[] fila = new Object[9];
                fila[0] = rs.getString("periodo") + c_varios.ceros_izquieda_numero(4, rs.getInt("id_venta"));
                fila[1] = c_varios.formato_fecha_vista(rs.getString("fecha_venta"));
                fila[2] = rs.getString("abreviado") + " / " + c_varios.ceros_izquieda_letras(4, rs.getString("serie_doc")) + " - " + c_varios.ceros_izquieda_numero(7, rs.getInt("nro_doc"));
                fila[3] = rs.getString("zona");
                fila[4] = rs.getString("documento") + " | " + rs.getString("nom_cliente");
                fila[5] = c_varios.formato_totales(rs.getDouble("total"));
                if (rs.getString("estado").equals("1")) {
                    fila[6] = "PAGADO";
                }
                if (rs.getString("estado").equals("0")) {
                    fila[6] = "PENDIENTE";
                }
                if (rs.getString("estado").equals("2")) {
                    fila[6] = "ANULADO";
                }
                fila[7] = rs.getString("periodo");
                fila[8] = rs.getInt("id_venta");
                mostrar.addRow(fila);
            }
            c_conectar.cerrar(st);
            c_conectar.cerrar(rs);
            tabla.setModel(mostrar);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(90);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(90);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(140);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(120);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(350);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(6).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(7).setPreferredWidth(0);
            tabla.getColumnModel().getColumn(8).setPreferredWidth(0);
            tabla.setDefaultRenderer(Object.class, new render_tables.render_ventas());
        } catch (SQLException e) {
            System.out.print(e);
        }
        return total;
    }

    public void datos_venta() {
        try {
            Statement st = c_conectar.conexion();
            String query = "select * "
                    + "from ventas "
                    + "where periodo = '" + periodo + "' and id_venta = '" + id_venta + "'";
            ResultSet rs = c_conectar.consulta(st, query);
            while (rs.next()) {
                fecha = rs.getString("fecha_venta");
                id_cliente = rs.getInt("id_cliente");
                id_zona = rs.getInt("id_zona");
                id_empleado = rs.getInt("id_empleado");
                id_documento = rs.getInt("id_documento");
                serie_doc = rs.getString("serie_doc");
                numero_doc = rs.getInt("nro_doc");
                total = rs.getDouble("total");
                estado = rs.getInt("estado");
                
            }

            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }
    }
}
