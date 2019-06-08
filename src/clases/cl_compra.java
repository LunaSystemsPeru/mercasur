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
public class cl_compra {

    cl_conectar c_conectar = new cl_conectar();
    cl_varios c_varios = new cl_varios();

    private int codigo;
    private String periodo;
    private String cod_compra;
    private String proveedor;
    private String fecha;
    private int documento;
    private String serie;
    private int numero;
    private int moneda;
    private double tc_compra;
    private double total;
    private double pagado;
    private String estado;
    private String usuario;
    private String glosa;
    private String fecha_registro;

    public cl_compra() {
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
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

    public String getCod_compra() {
        return cod_compra;
    }

    public void setCod_compra(String cod_compra) {
        this.cod_compra = cod_compra;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
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

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getMoneda() {
        return moneda;
    }

    public void setMoneda(int moneda) {
        this.moneda = moneda;
    }

    public double getTc_compra() {
        return tc_compra;
    }

    public void setTc_compra(double tc_compra) {
        this.tc_compra = tc_compra;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }

    public double getPagado() {
        return pagado;
    }

    public void setPagado(double pagado) {
        this.pagado = pagado;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public boolean comprobar_documento() {
        boolean existe = false;

        try {
            Statement st = c_conectar.conexion();
            String c_placas = "select c.periodo,c.id_compra\n"
                    + "from compras as c\n"
                    + "inner join proveedores as p on c.id_proveedor = p.id_proveedor \n"
                    + "inner join tipo_documento as td on c.id_documento = td.id_documento \n"
                    + "where p.ruc_pro='" + proveedor + "' and td.nombre='" + documento + "' AND c.serie='" + serie + "' and c.numero='" + numero + "'";
            ResultSet rs = c_conectar.consulta(st, c_placas);

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

    public void obtener_codigo() {

        try {
            Statement st = c_conectar.conexion();
            String query = "select ifnull(max(id_compra) + 1, 1) as id_compra "
                    + "from compras "
                    + "where periodo = '" + periodo + "' ";
            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                codigo = rs.getInt("id_compra");
            }

            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

    }

    public void datos_compra() {
        try {
            Statement st = c_conectar.conexion();
            String query = "select * "
                    + "from compras "
                    + "where concat (periodo, lpad (id_compra, 3, 0)) = '" + cod_compra + "'";
            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                codigo = rs.getInt("id_compra");
                periodo = rs.getString("periodo");
                fecha = rs.getString("fecha");
                proveedor = rs.getString("id_proveedor");
                moneda = rs.getInt("id_moneda");
                tc_compra = rs.getDouble("tc");
                documento = rs.getInt("id_documento");
                serie = rs.getString("serie");
                numero = rs.getInt("numero");
                total = rs.getDouble("total");
                estado = rs.getString("estado");
                usuario = rs.getString("id_empleado");
                glosa = rs.getString("glosa");
                fecha_registro = rs.getString("f_registro");
            }

            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }
    }

    public boolean insertar() {
        boolean grabado = false;
        Statement st = c_conectar.conexion();
        String query = "INSERT INTO compras VALUES('" + periodo + "','" + codigo + "','" + fecha + "','" + moneda + "'"
                + ",'" + tc_compra + "','" + glosa + "','" + proveedor + "','" + documento + "','" + serie + "','" + numero + "',"
                + "'" + total + "','" + pagado + "','2',current_date(),'" + usuario + "')";
        int resultado = c_conectar.actualiza(st, query);

        if (resultado > -1) {
            grabado = true;
        }

        c_conectar.cerrar(st);

        return grabado;
    }

    public boolean eliminar() {
        boolean grabado = false;
        Statement st = c_conectar.conexion();
        String query = "delete from compras where concat(periodo, lpad(idcompra, 3, 0)) = '" + cod_compra + "' ";
        int resultado = c_conectar.actualiza(st, query);

        if (resultado > -1) {
            grabado = true;
        }

        c_conectar.cerrar(st);

        return grabado;
    }

    public double ver_compras(JTable tabla, String query) {
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
            mostrar.addColumn("Fec. Com.");
            mostrar.addColumn("Documento");
            mostrar.addColumn("Proveedor");
            mostrar.addColumn("Moneda");
            mostrar.addColumn("total");
            mostrar.addColumn("Pendiente");
            mostrar.addColumn("TC.");
            mostrar.addColumn("SubTotal");
            mostrar.addColumn("igv");
            mostrar.addColumn("Total");
            mostrar.addColumn("Estado");

            Object fila[] = new Object[12];
            while (rs.next()) {
                fila[0] = rs.getString("periodo") + c_varios.ceros_izquieda_numero(3, rs.getInt("id_compra"));
                fila[1] = c_varios.formato_fecha_vista(rs.getString("fecha"));
                fila[2] = rs.getString("abreviado") + " / " + c_varios.ceros_izquieda_letras(4, rs.getString("serie")) + " - " + c_varios.ceros_izquieda_numero(7, rs.getInt("numero"));
                fila[3] = rs.getString("ruc_pro") + " | " + rs.getString("raz_soc_pro");
                fila[4] = rs.getString("moneda");
                fila[5] = c_varios.formato_totales(rs.getDouble("total"));
                fila[6] = c_varios.formato_numero(rs.getDouble("total") - rs.getDouble("pagado"));
                fila[7] = c_varios.formato_tc(rs.getDouble("tc"));
                Double base = rs.getDouble("total") * rs.getDouble("tc");
                total = total + base;
                fila[8] = c_varios.formato_totales(base / 1.18);
                fila[9] = c_varios.formato_totales(base / 1.18 * 0.18);
                fila[10] = c_varios.formato_totales(base);

                if (rs.getString("estado").equals("0")) {
                    fila[11] = "PENDIENTE";
                } else {
                    fila[11] = "PAGADO";
                }
                mostrar.addRow(fila);
            }
            tabla.setModel(mostrar);

        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        tabla.getColumnModel().getColumn(0).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(80);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(400);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(90);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(80);
        tabla.getColumnModel().getColumn(6).setPreferredWidth(80);
        tabla.getColumnModel().getColumn(7).setPreferredWidth(80);
        tabla.getColumnModel().getColumn(8).setPreferredWidth(80);
        tabla.getColumnModel().getColumn(9).setPreferredWidth(80);
        tabla.getColumnModel().getColumn(10).setPreferredWidth(80);
        tabla.getColumnModel().getColumn(11).setPreferredWidth(65);
        tabla.setDefaultRenderer(Object.class, new render_tables.render_compras());

        return total;
    }

}
