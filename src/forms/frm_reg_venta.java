/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import clases.cl_cliente;
import clases.cl_conectar;
import clases.cl_productos;
import clases.cl_varios;
import clases.cl_zona;
import clases_autocomplete.cl_ac_clientes;
import clases_autocomplete.cl_ac_productos;
import com.mxrck.autocompleter.AutoCompleterCallback;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mercasur.frm_menu;
import models.cl_combobox;
import models.m_unidad_producto;
import models.m_zonas;
import nicon.notify.core.Notification;

/**
 *
 * @author CALIDAD
 */
public class frm_reg_venta extends javax.swing.JInternalFrame {

    cl_conectar c_conectar = new cl_conectar();
    cl_cliente c_cliente = new cl_cliente();

    cl_varios c_varios = new cl_varios();
    cl_zona c_zona = new cl_zona();
    cl_productos c_producto = new cl_productos();

    TextAutoCompleter autocompletar = null;

    int id_zona = frm_menu.c_zona.getId_zona();
    int id_empleado = frm_menu.c_empleado.getId_empleado();
    int id_producto;
    double costo_producto;
    boolean existe_producto = false;

    m_zonas m_zona = new m_zonas();
    m_unidad_producto m_unidades = new m_unidad_producto();
    DefaultTableModel detalle = null;

    /**
     * Creates new form frm_reg_venta
     */
    public frm_reg_venta() {
        initComponents();
        c_zona.setId_empleado(id_empleado);
        if (c_zona.validar_empleado()) {
            m_zona.cbx_zona(cbx_zona, id_empleado);
            if (id_zona == 0) {
                id_zona = 1;
            }
            c_zona.setId_zona(id_zona);
            if (c_zona.cargar_datos()) {
                txt_nombre_zona.setText(c_zona.getNombre());
            }
            cargar_clientes();
            txt_buscar_cliente.requestFocus();
        } else {
            JOptionPane.showMessageDialog(null, "USTED NO TIENE ZONAS ASIGNADAS");
            this.dispose();
        }

        modelo_detalle();
    }

    private void modelo_detalle() {
        //formato de tabla detalle de venta
        detalle = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return columna == 2 || columna == 4;
            }
        };
        detalle.addColumn("Id");
        detalle.addColumn("Descripcion");
        detalle.addColumn("Cant.");
        detalle.addColumn("Und. Med");
        detalle.addColumn("P. Venta");
        detalle.addColumn("Parcial");
        detalle.addColumn("id und medida");
        detalle.addColumn("costo");
        t_detalle.setModel(detalle);
        t_detalle.getColumnModel().getColumn(0).setPreferredWidth(10);
        t_detalle.getColumnModel().getColumn(1).setPreferredWidth(350);
        t_detalle.getColumnModel().getColumn(2).setPreferredWidth(30);
        t_detalle.getColumnModel().getColumn(3).setPreferredWidth(30);
        t_detalle.getColumnModel().getColumn(4).setPreferredWidth(50);
        t_detalle.getColumnModel().getColumn(5).setPreferredWidth(50);
        t_detalle.getColumnModel().getColumn(6).setPreferredWidth(0);
        t_detalle.getColumnModel().getColumn(6).setMinWidth(0);
        t_detalle.getColumnModel().getColumn(6).setMaxWidth(0);
        t_detalle.getColumnModel().getColumn(7).setPreferredWidth(0);
        t_detalle.getColumnModel().getColumn(7).setMinWidth(0);
        t_detalle.getColumnModel().getColumn(7).setMaxWidth(0);
        c_varios.centrar_celda(t_detalle, 0);
        c_varios.derecha_celda(t_detalle, 2);
        c_varios.centrar_celda(t_detalle, 3);
        c_varios.derecha_celda(t_detalle, 4);
        c_varios.derecha_celda(t_detalle, 5);
        c_varios.derecha_celda(t_detalle, 6);

    }

    private void cargar_clientes() {
        try {
            //TextAutoCompleter autocompletar = new TextAutoCompleter(txt_consulta_productos);
            if (autocompletar != null) {
                autocompletar.removeAllItems();
            }
            autocompletar = new TextAutoCompleter(txt_buscar_cliente, new AutoCompleterCallback() {
                @Override
                public void callback(Object selectedItem) {
//                    System.out.println("El usuario seleccionó: " + selectedItem);
                    Object itemSelected = selectedItem;
                    if (itemSelected instanceof cl_ac_clientes) {
                        int id = ((cl_ac_clientes) itemSelected).getId_cliente();
                        c_cliente.setId_cliente(id);
                        c_cliente.setId_zona(id_zona);
                        c_cliente.obtener_datos();
                        txt_doc_cliente.setText(c_cliente.getDocumento());
                        txt_nom_cliente.setText(c_cliente.getNombre());
                        txt_dir_cliente.setText(c_cliente.getDireccion());
                    } else {
                        System.out.println("El item es de un tipo desconocido");
                        Notification.show("Registrar Venta", "Error al seleccionar el cliente, presione enter para seleccionar");
                        txt_buscar_cliente.setText("");
                        txt_buscar_cliente.requestFocus();
                    }
                }
            });

            Statement st = c_conectar.conexion();
            String query = "select id_cliente, nombre, documento, direccion "
                    + "from clientes "
                    + "where id_zona = '" + id_zona + "'";
            ResultSet rs_cliente = c_conectar.consulta(st, query);
            while (rs_cliente.next()) {

                String nombre = rs_cliente.getString("nombre");
                String documento = rs_cliente.getString("documento");
                String direccion = rs_cliente.getString("direccion");
                int id_cliente = rs_cliente.getInt("id_cliente");
                autocompletar.addItem(new cl_ac_clientes(id_cliente, nombre, direccion, documento));
            }
            c_conectar.cerrar(rs_cliente);
            c_conectar.cerrar(st);
            autocompletar.setMode(0);
        } catch (SQLException ex) {
            System.out.println(ex);
            //JOptionPane.showInternalMessageDialog(this, ex.getLocalizedMessage());
        }
    }

    private void cargar_productos() {
        try {
            //TextAutoCompleter autocompletar = new TextAutoCompleter(txt_consulta_productos);
            if (autocompletar != null) {
                autocompletar.removeAllItems();
            }
            autocompletar = new TextAutoCompleter(txt_buscar_producto, new AutoCompleterCallback() {
                @Override
                public void callback(Object selectedItem) {
//                    System.out.println("El usuario seleccionó: " + selectedItem);
                    Object itemSelected = selectedItem;
                    if (itemSelected instanceof cl_ac_productos) {
                        int id = ((cl_ac_productos) itemSelected).getId();
                        c_producto.setId_producto(id);
                        if (valida_tabla(id)) {
                            c_producto.ver_datos_producto();
                            String descripcion = ((cl_ac_productos) itemSelected).getDescripcion();
                            txt_nombre_producto.setText(descripcion.toUpperCase().trim());
                            id_producto = id;
                            txt_precio_producto.setText(c_varios.formato_numero(c_producto.getPrecio()));
                            costo_producto = c_producto.getCosto();
                            txt_cantidad_producto.setText("1");
                            m_unidades.llenar_combo(cbx_unidad_producto, id_producto);
                        } else {
                            JOptionPane.showMessageDialog(null, "YA SE HA INGRESADO ESTE PRODUCTO");
                            txt_buscar_producto.setText("");
                            txt_buscar_producto.requestFocus();
                        }
                    } else {
                        System.out.println("El item es de un tipo desconocido");
                        Notification.show("Ingreso de Mercaderia", "Error al seleccionar el producto");
                        txt_buscar_producto.setText("");
                        txt_buscar_producto.requestFocus();
                    }
                }
            });
            c_conectar.conectar();
            Statement st = c_conectar.conexion();
            String sql = "select p.id_producto, p.descripcion, p.costo_compra, p.precio_venta, p.cant_actual, m.nombre as marca "
                    + "from productos as p "
                    + "inner join marcas as m on m.id_marca = p.id_marca "
                    + "order by m.nombre asc, p.descripcion asc";
            ResultSet rs = c_conectar.consulta(st, sql);

            while (rs.next()) {
                String marca = rs.getString("marca");
                String descripcion = marca + " | " + rs.getString("p.descripcion").trim();
                int id = rs.getInt("id_producto");
                double cantidad = rs.getDouble("p.cant_actual");
                double precio = rs.getDouble("p.precio_venta");
                double costo = 0.00;
                autocompletar.addItem(new cl_ac_productos(id, descripcion, cantidad, "Cajas", precio, costo));
            }
            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
            autocompletar.setMode(0);
        } catch (SQLException ex) {
            JOptionPane.showInternalMessageDialog(this, ex.getLocalizedMessage());
        }

    }

    private boolean valida_tabla(int producto) {
        //estado de ingreso
        boolean agregar_datos = false;
        boolean ingresar = false;
        int cuenta_iguales = 0;

        //verificar fila no se repite
        int contar_filas = t_detalle.getRowCount();
        if (contar_filas == 0) {
            ingresar = true;
        }

        if (contar_filas > 0) {
            for (int j = 0; j < contar_filas; j++) {
                int id_producto_fila = Integer.parseInt(t_detalle.getValueAt(j, 0).toString());
                if (producto == id_producto_fila) {
                    ingresar = false;
                    cuenta_iguales++;
                    existe_producto = true;
                    JOptionPane.showMessageDialog(null, "El Producto a Ingresar ya existe en la lista");
                } else {
                    ingresar = true;
                }
            }
        }

        if (ingresar == true && cuenta_iguales == 0) {
            agregar_datos = true;
        }

        if (contar_filas == 29) {
            txt_buscar_producto.setEnabled(false);
            JOptionPane.showMessageDialog(null, "SE HA LLEGADO AL LIMITE DE 30 PRODUCTOS");
        }
        return agregar_datos;
    }

    private void limpiar_productos() {
        txt_nombre_producto.setText("");
        cbx_unidad_producto.removeAllItems();
        txt_cantidad_producto.setText("");
        txt_cantidad_producto.setEnabled(false);
        txt_precio_producto.setText("");
        txt_subtotal_producto.setText("");
        id_producto = 0;
        costo_producto = 0;
        btn_add_producto.setEnabled(false);
        txt_buscar_producto.setText("");
        txt_buscar_producto.requestFocus();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jd_zonas = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        cbx_zona = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        txt_nom_cliente = new javax.swing.JTextField();
        txt_dir_cliente = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_deuda_cliente = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_doc_cliente = new javax.swing.JTextField();
        btn_crear = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        btn_grabar = new javax.swing.JButton();
        txt_buscar_cliente = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txt_nombre_zona = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txt_buscar_producto = new javax.swing.JTextField();
        txt_nombre_producto = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_cantidad_producto = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cbx_unidad_producto = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        txt_precio_producto = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_subtotal_producto = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        t_detalle = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btn_add_producto = new javax.swing.JButton();

        jd_zonas.setTitle("Seleccionar Zona");

        jLabel1.setText("Cambiar Zona");

        cbx_zona.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/arrow_redo.png"))); // NOI18N
        jButton4.setText("Cambiar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jd_zonasLayout = new javax.swing.GroupLayout(jd_zonas.getContentPane());
        jd_zonas.getContentPane().setLayout(jd_zonasLayout);
        jd_zonasLayout.setHorizontalGroup(
            jd_zonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_zonasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jd_zonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jd_zonasLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cbx_zona, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jd_zonasLayout.createSequentialGroup()
                        .addGap(0, 264, Short.MAX_VALUE)
                        .addComponent(jButton4)))
                .addContainerGap())
        );
        jd_zonasLayout.setVerticalGroup(
            jd_zonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_zonasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbx_zona, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setTitle("Registrar Venta");

        txt_nom_cliente.setEnabled(false);

        txt_dir_cliente.setEnabled(false);

        jLabel3.setText("Deuda cliente:");

        txt_deuda_cliente.setEnabled(false);

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("total: S/ 0.00");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("suma pedido");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jLabel2.setText("Cliente");

        txt_doc_cliente.setEnabled(false);

        btn_crear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/add.png"))); // NOI18N
        btn_crear.setText("Crear");
        btn_crear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crearActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/cancel.png"))); // NOI18N
        jButton5.setText("Cerrar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        btn_grabar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/add.png"))); // NOI18N
        btn_grabar.setText("Grabar");
        btn_grabar.setEnabled(false);

        txt_buscar_cliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_buscar_clienteKeyPressed(evt);
            }
        });

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/find.png"))); // NOI18N

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/arrow_redo.png"))); // NOI18N
        jButton1.setText("Cambiar Zona");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel12.setText("Zona:");

        txt_nombre_zona.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_nombre_zona.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_dir_cliente)
                    .addComponent(txt_nom_cliente, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_grabar, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 87, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_doc_cliente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_crear))
                            .addComponent(txt_buscar_cliente, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_nombre_zona))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(txt_deuda_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addComponent(jButton1)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txt_nombre_zona, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_buscar_cliente)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_doc_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_crear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_nom_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_dir_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_deuda_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_grabar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel6.setText("Buscar Producto");

        txt_buscar_producto.setEnabled(false);
        txt_buscar_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_buscar_productoKeyPressed(evt);
            }
        });

        txt_nombre_producto.setEnabled(false);

        jLabel7.setText("Cantidad:");

        txt_cantidad_producto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_cantidad_producto.setText("1");
        txt_cantidad_producto.setEnabled(false);
        txt_cantidad_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cantidad_productoKeyPressed(evt);
            }
        });

        jLabel8.setText("Und. Medida.:");

        cbx_unidad_producto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "UND.", "PAQUETE.", "CAJA" }));
        cbx_unidad_producto.setEnabled(false);
        cbx_unidad_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbx_unidad_productoKeyPressed(evt);
            }
        });

        jLabel9.setText("Precio");

        txt_precio_producto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_precio_producto.setText("0.00");
        txt_precio_producto.setEnabled(false);

        jLabel10.setText("Sub total");

        txt_subtotal_producto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_subtotal_producto.setText("0.00");
        txt_subtotal_producto.setEnabled(false);

        t_detalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(t_detalle);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/application_edit.png"))); // NOI18N
        jButton3.setText("Modificar");
        jButton3.setEnabled(false);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bin_closed.png"))); // NOI18N
        jButton2.setText("Eliminar");
        jButton2.setEnabled(false);

        btn_add_producto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/add.png"))); // NOI18N
        btn_add_producto.setText("Agregar");
        btn_add_producto.setEnabled(false);
        btn_add_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_productoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_nombre_producto)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_buscar_producto))
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_cantidad_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbx_unidad_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(73, 73, 73)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_precio_producto, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                            .addComponent(txt_subtotal_producto))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                        .addComponent(btn_add_producto))
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_buscar_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_nombre_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txt_precio_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_unidad_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(txt_subtotal_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_add_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_cantidad_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_crearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crearActionPerformed
        Frame f = JOptionPane.getRootFrame();
        frm_reg_cliente.origen = "cliente";
        frm_reg_cliente.accion = "registrar";
        frm_reg_cliente dialog = new frm_reg_cliente(f, true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_btn_crearActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void txt_buscar_clienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscar_clienteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_buscar_cliente.getText().length() > 10) {
                txt_buscar_cliente.setText("");
                //cargar productos
                cargar_productos();

                //habilitar txt producto
                txt_buscar_producto.setEnabled(true);
                txt_buscar_producto.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_buscar_clienteKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jd_zonas.setSize(400, 147);
        jd_zonas.setModal(true);
        jd_zonas.setLocationRelativeTo(null);
        jd_zonas.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        cl_combobox c_combo = (cl_combobox) cbx_zona.getSelectedItem();
        id_zona = c_combo.getId();
        frm_menu.c_zona.setId_zona(id_zona);
        txt_nombre_zona.setText(cbx_zona.getSelectedItem().toString());
        cargar_clientes();
        jd_zonas.dispose();
        txt_buscar_cliente.setText("");
        txt_buscar_cliente.requestFocus();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txt_buscar_productoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscar_productoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_buscar_producto.getText().length() == 0) {
                int contar_filas = t_detalle.getRowCount();
                if (contar_filas > 0) {
                    btn_grabar.requestFocus();
                }
            }
            if (!existe_producto) {
                if (txt_buscar_producto.getText().length() > 20) {
                    txt_buscar_producto.setText("");
                    cbx_unidad_producto.setEnabled(true);
                    cbx_unidad_producto.requestFocus();
                }
            }
        }
    }//GEN-LAST:event_txt_buscar_productoKeyPressed

    private void cbx_unidad_productoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbx_unidad_productoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_cantidad_producto.setEnabled(true);
            txt_cantidad_producto.requestFocus();
        }
    }//GEN-LAST:event_cbx_unidad_productoKeyPressed

    private void txt_cantidad_productoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cantidad_productoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String numero = txt_cantidad_producto.getText();
            if (c_varios.isNumeric(numero)) {
                //multiplicar y hallar subtotal
                double cantidad = Double.parseDouble(txt_cantidad_producto.getText());
                double precio = Double.parseDouble(txt_precio_producto.getText());
                double parcial = cantidad * precio;
                txt_subtotal_producto.setText(parcial + "");
                btn_add_producto.setEnabled(true);
                btn_add_producto.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_cantidad_productoKeyPressed

    private void btn_add_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_productoActionPerformed
        //obtener id_unidad_medida
        cl_combobox c_combo = (cl_combobox) cbx_unidad_producto.getSelectedItem();
        //agregar fila
        Object fila[] = new Object[8];
        fila[0] = id_producto;
        fila[1] = txt_nombre_producto.getText();
        fila[2] = txt_cantidad_producto.getText();
        fila[3] = cbx_unidad_producto.getSelectedItem();
        fila[4] = txt_precio_producto.getText();
        fila[5] = txt_subtotal_producto.getText();
        fila[6] = c_combo.getId();
        fila[7] = costo_producto;
        detalle.addRow(fila);

        btn_grabar.setEnabled(true);

        limpiar_productos();
    }//GEN-LAST:event_btn_add_productoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add_producto;
    private javax.swing.JButton btn_crear;
    private javax.swing.JButton btn_grabar;
    private javax.swing.JComboBox cbx_unidad_producto;
    private javax.swing.JComboBox<String> cbx_zona;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JDialog jd_zonas;
    private javax.swing.JTable t_detalle;
    private javax.swing.JTextField txt_buscar_cliente;
    private javax.swing.JTextField txt_buscar_producto;
    private javax.swing.JTextField txt_cantidad_producto;
    private javax.swing.JTextField txt_deuda_cliente;
    private javax.swing.JTextField txt_dir_cliente;
    private javax.swing.JTextField txt_doc_cliente;
    private javax.swing.JTextField txt_nom_cliente;
    private javax.swing.JTextField txt_nombre_producto;
    private javax.swing.JTextField txt_nombre_zona;
    private javax.swing.JTextField txt_precio_producto;
    private javax.swing.JTextField txt_subtotal_producto;
    // End of variables declaration//GEN-END:variables
}
