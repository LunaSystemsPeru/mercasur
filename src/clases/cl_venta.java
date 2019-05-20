
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
    private int codigo;
    private String cod_venta;
    private String fecha_venta;
    private String fecha_pago;
    private String placa;
    private int tipo_documento;
    private int serie;
    private int numero;
    private String usuario;
    private int cliente;
    private String nombre_cliente;
    private int kilometraje;
    private double total;
    private double pagado;
    private double descuento;
    private String estado;

    public cl_venta() {
    }

    public String getCod_venta() {
        return cod_venta;
    }

    public void setCod_venta(String cod_venta) {
        this.cod_venta = cod_venta;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(String fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public String getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(String fecha_venta) {
        this.fecha_venta = fecha_venta;
    }

    public int getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(int kilometraje) {
        this.kilometraje = kilometraje;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getSerie() {
        return serie;
    }

    public void setSerie(int serie) {
        this.serie = serie;
    }

    public int getTipo_documento() {
        return tipo_documento;
    }

    public void setTipo_documento(int tipo_documento) {
        this.tipo_documento = tipo_documento;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public double getPagado() {
        return pagado;
    }

    public void setPagado(double pagado) {
        this.pagado = pagado;
    }

    public boolean insertar() {
        boolean grabado = false;
        Statement st = c_conectar.conexion();
        String query = "insert into ventas "
                + "Values ('" + periodo + "', '" + codigo + "', '" + placa + "', '" + fecha_venta + "', '" + fecha_pago + "', '" + tipo_documento + "', "
                + "'" + serie + "', '" + numero + "', '" + cliente + "', '" + nombre_cliente + "', '" + kilometraje + "', '" + descuento + "', "
                + "'" + total + "', 0,'" + usuario + "', '" + estado + "', current_time())";
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
            String query = "select ifnull(max(id) + 1, 1) as codigo from ventas where periodo = '" + periodo + "' ";
            ResultSet rs = c_conectar.consulta(st, query);
            System.out.println(query);
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

    public boolean eliminar() {
        boolean grabado = false;
        Statement st = c_conectar.conexion();
        String query = "update ventas set estado = '2', total = '0', descuento = '0' "
                + "where periodo = '" + periodo + "' and id = '" + codigo + "'";
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
            mostrar.addColumn("Placa");
            mostrar.addColumn("Cliente");
            mostrar.addColumn("Monto");
            mostrar.addColumn("Deuda");
            mostrar.addColumn("Estado");
            //Creando las filas para el JTable
            while (rs.next()) {
                total = total + (rs.getDouble("total") - rs.getDouble("descuento"));
                double deuda = rs.getDouble("total") - rs.getDouble("descuento") - rs.getDouble("pagado");
                Object[] fila = new Object[8];
                fila[0] = rs.getString("periodo") + c_varios.ceros_izquieda_numero(5, rs.getInt("id"));
                fila[1] = c_varios.formato_fecha_vista(rs.getString("fecha_venta"));;
                fila[2] = rs.getString("documento") + " / " + c_varios.ceros_izquieda_letras(4, rs.getString("serie_doc")) + " - " + c_varios.ceros_izquieda_numero(7, rs.getInt("nro_doc"));
                fila[3] = rs.getString("nro_placa");
                fila[4] = rs.getString("doc_cliente") + " | " + rs.getString("nombre_cliente") + " | Km: " + rs.getString("kilometraje");
                fila[5] = c_varios.formato_totales(rs.getDouble("total") - rs.getDouble("descuento"));
                fila[6] = c_varios.formato_numero(deuda);
                if (rs.getString("estado").equals("1")) {
                    fila[7] = "PAGADO";
                }
                if (rs.getString("estado").equals("0")) {
                    fila[7] = "PENDIENTE";
                }
                if (rs.getString("estado").equals("2")) {
                    fila[7] = "ANULADO";
                }
                mostrar.addRow(fila);
            }
            c_conectar.cerrar(st);
            c_conectar.cerrar(rs);
            tabla.setModel(mostrar);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(65);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(130);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(410);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(6).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(7).setPreferredWidth(70);
            tabla.setDefaultRenderer(Object.class, new render_tables.render_ventas());
        } catch (SQLException e) {
            System.out.print(e);
        }
        return total;
    }

    public void datos_venta() {
        try {
            Statement st = c_conectar.conexion();
            String query = "select periodo, id, nro_placa, fecha_venta, tipo_documento, serie_doc, nro_doc, cliente, nombre_cliente, kilometraje, descuento, total, estado "
                    + "from ventas "
                    + "where concat (periodo, lpad (id, 5, 0)) = '" + cod_venta + "'";
            ResultSet rs = c_conectar.consulta(st, query);
            while (rs.next()) {
                periodo = rs.getString("periodo");
                codigo = rs.getInt("id");
                placa = rs.getString("nro_placa");
                kilometraje = rs.getInt("kilometraje");
            }

            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }
    }

    public boolean actualizar_kilometraje() {
        boolean grabado = false;
        Statement st = c_conectar.conexion();
        String query = "update ventas "
                + "set kilometraje = '" + kilometraje + "' "
                + "where concat (periodo, lpad (id, 5, 0)) = '" + cod_venta + "'";
        System.out.println(query);
        int resultado = c_conectar.actualiza(st, query);

        if (resultado > -1) {
            grabado = true;
        }

        c_conectar.cerrar(st);

        return grabado;
    }
}
