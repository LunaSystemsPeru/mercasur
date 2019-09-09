/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import clases_autocomplete.cl_ac_productos;
import clases_autocomplete.cl_ac_proveedor;
import clases.cl_conectar;
import clases.cl_detalle_ingreso;
import clases.cl_documento_tienda;
import clases.cl_ingreso;
import clases.cl_productos;
import clases.cl_proveedor;
import clases.cl_tipo_documento;
import clases.cl_varios;
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
import models.m_tipo_documento;
import nicon.notify.core.Notification;
import vistas.frm_ver_ingresos;

/**
 *
 * @author CALIDAD
 */
public class frm_reg_ingreso extends javax.swing.JInternalFrame {

    cl_conectar c_conectar = new cl_conectar();
    cl_varios c_varios = new cl_varios();

    m_tipo_documento m_tido = new m_tipo_documento();

    cl_documento_tienda c_documento = new cl_documento_tienda();
    cl_proveedor c_proveedor = new cl_proveedor();
    cl_productos c_producto = new cl_productos();

    cl_ingreso c_ingreso = new cl_ingreso();
    cl_detalle_ingreso c_detalle = new cl_detalle_ingreso();

    TextAutoCompleter autocompletar = null;
    TextAutoCompleter tac_proveedores = null;

    DefaultTableModel detalle = null;

    boolean producto_correcto = false;
    double total_ingreso = 0;
    int total_items = 0;

    Integer nro_fila;

    /**
     * Creates new form frm_reg_ingreso
     */
    public frm_reg_ingreso() {
        initComponents();

        modelo_detalle();

        txt_fecha.setText(c_varios.formato_fecha_vista(c_varios.getFechaActual()));

        txt_periodo.setText(c_varios.ver_periodo_actual());

        m_tido.cbx_documentos(cbx_tido);

        cbx_tido.requestFocus();

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
        detalle.addColumn("P.Compra ");
        detalle.addColumn("Parcial");
        t_detalle.setModel(detalle);
        t_detalle.getColumnModel().getColumn(0).setPreferredWidth(10);
        t_detalle.getColumnModel().getColumn(1).setPreferredWidth(350);
        t_detalle.getColumnModel().getColumn(2).setPreferredWidth(30);
        t_detalle.getColumnModel().getColumn(3).setPreferredWidth(30);
        t_detalle.getColumnModel().getColumn(4).setPreferredWidth(50);
        t_detalle.getColumnModel().getColumn(5).setPreferredWidth(50);
        t_detalle.getColumnModel().getColumn(6).setPreferredWidth(50);
        c_varios.centrar_celda(t_detalle, 0);
        c_varios.derecha_celda(t_detalle, 2);
        c_varios.centrar_celda(t_detalle, 3);
        c_varios.derecha_celda(t_detalle, 4);
        c_varios.derecha_celda(t_detalle, 5);
        c_varios.derecha_celda(t_detalle, 6);

    }

    private void cargar_proveedores() {
        if (tac_proveedores != null) {
            tac_proveedores.removeAllItems();
        }
        tac_proveedores = new TextAutoCompleter(txt_razon_social, new AutoCompleterCallback() {
            @Override
            public void callback(Object selectedItem) {
                Object itemSelected = selectedItem;
                if (itemSelected instanceof cl_ac_proveedor) {
                    c_proveedor.setRuc(((cl_ac_proveedor) itemSelected).getRuc());
                    c_proveedor.cargar_datos_proveedor();
                    txt_ruc_proveedor.setText(c_proveedor.getRuc());
                    txt_direccion.setText(c_proveedor.getDireccion());
                } else {
                    System.out.println("El item es de un tipo desconocido");
                }
            }
        });
        tac_proveedores.setMode(0);
        try {
            Statement st = c_conectar.conexion();
            String query = "select id_proveedor, ruc_pro, raz_soc_pro, dir_pro "
                    + "from proveedores "
                    + "order by ruc_pro asc";
            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                tac_proveedores.addItem(new cl_ac_proveedor(rs.getInt("id_proveedor"), rs.getString("ruc_pro"), rs.getString("raz_soc_pro")));
            }
            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
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
                            String und_medida = ((cl_ac_productos) itemSelected).getUnd_medida();
                            txt_und_medida.setText(und_medida);
                            txt_nombre_producto.setText(descripcion.toUpperCase().trim());
                            txt_codigo_producto.setText(id + "");
                            txt_precio_producto.setText(c_varios.formato_numero(c_producto.getPrecio()));
                            txt_compra_producto.setText(c_varios.formato_numero(c_producto.getCosto()));
                            txt_cantidad_producto.setText("1.00");
                            producto_correcto = true;
                        } else {
                            JOptionPane.showMessageDialog(null, "YA SE HA INGRESADO ESTE PRODUCTO");
                        }
                    } else {
                        System.out.println("El item es de un tipo desconocido");
                        Notification.show("Ingreso de Mercaderia", "Error al seleccionar el producto");
                        producto_correcto = false;
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
                double costo = rs.getDouble("p.costo_compra");
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

    private void activar_botones() {
        btn_cambiar_cantidad.setEnabled(true);
        btn_cambiar_precio.setEnabled(true);
        btn_eliminar.setEnabled(true);
    }

    private void desactivar_botones() {
        btn_cambiar_cantidad.setEnabled(false);
        btn_cambiar_precio.setEnabled(false);
        btn_eliminar.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jd_ver_productos = new javax.swing.JDialog();
        btn_cambiar_precio = new javax.swing.JButton();
        btn_cambiar_cantidad = new javax.swing.JButton();
        txt_tot = new javax.swing.JTextField();
        btn_eliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        t_detalle = new javax.swing.JTable();
        btn_registrar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        btn_cerrar = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txt_numero_doc = new javax.swing.JTextField();
        txt_serie_doc = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txt_razon_social = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txt_direccion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cbx_tido = new javax.swing.JComboBox();
        btn_buscar_proveedor = new javax.swing.JButton();
        txt_ruc_proveedor = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_fecha = new javax.swing.JFormattedTextField();
        btn_crear_proveedor = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_buscar_producto = new javax.swing.JTextField();
        btn_crear_producto = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txt_codigo_producto = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txt_nombre_producto = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txt_compra_producto = new javax.swing.JTextField();
        txt_precio_producto = new javax.swing.JTextField();
        btn_add_producto = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        txt_cantidad_producto = new javax.swing.JTextField();
        txt_und_medida = new javax.swing.JTextField();
        btn_recargar_productos = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        lbl_total_items = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_periodo = new javax.swing.JTextField();

        javax.swing.GroupLayout jd_ver_productosLayout = new javax.swing.GroupLayout(jd_ver_productos.getContentPane());
        jd_ver_productos.getContentPane().setLayout(jd_ver_productosLayout);
        jd_ver_productosLayout.setHorizontalGroup(
            jd_ver_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 922, Short.MAX_VALUE)
        );
        jd_ver_productosLayout.setVerticalGroup(
            jd_ver_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 432, Short.MAX_VALUE)
        );

        setTitle("Registrar Ingreso de Mercaderia");

        btn_cambiar_precio.setText("S/. - Cambiar Precio");
        btn_cambiar_precio.setEnabled(false);
        btn_cambiar_precio.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btn_cambiar_precio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cambiar_precioActionPerformed(evt);
            }
        });

        btn_cambiar_cantidad.setText("+/- Cambiar Cantidad");
        btn_cambiar_cantidad.setEnabled(false);
        btn_cambiar_cantidad.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btn_cambiar_cantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cambiar_cantidadActionPerformed(evt);
            }
        });

        txt_tot.setEditable(false);
        txt_tot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        btn_eliminar.setText("X - Eliminar");
        btn_eliminar.setEnabled(false);
        btn_eliminar.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });

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
        t_detalle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                t_detalleMousePressed(evt);
            }
        });
        t_detalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                t_detalleKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(t_detalle);

        btn_registrar.setText("Registrar");
        btn_registrar.setEnabled(false);
        btn_registrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_registrarActionPerformed(evt);
            }
        });

        jLabel10.setText("Total");

        btn_cerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/cancel.png"))); // NOI18N
        btn_cerrar.setText("Cerrar");
        btn_cerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cerrarActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Generales de Compra"));

        jLabel7.setText("Numero");

        txt_numero_doc.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_numero_doc.setEnabled(false);
        txt_numero_doc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_numero_docKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_numero_docKeyTyped(evt);
            }
        });

        txt_serie_doc.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_serie_doc.setEnabled(false);
        txt_serie_doc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_serie_docKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_serie_docKeyTyped(evt);
            }
        });

        jLabel1.setText("Proveedor");

        txt_razon_social.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txt_razon_social.setEnabled(false);
        txt_razon_social.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_razon_socialKeyPressed(evt);
            }
        });

        jLabel6.setText("Serie:");

        txt_direccion.setFocusable(false);

        jLabel5.setText("Tipo de Documento");

        cbx_tido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbx_tidoKeyPressed(evt);
            }
        });

        btn_buscar_proveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/arrow_redo.png"))); // NOI18N
        btn_buscar_proveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscar_proveedorActionPerformed(evt);
            }
        });

        txt_ruc_proveedor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_ruc_proveedor.setEnabled(false);
        txt_ruc_proveedor.setFocusable(false);
        txt_ruc_proveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_ruc_proveedorKeyPressed(evt);
            }
        });

        jLabel3.setText("Fecha de Compra:");

        try {
            txt_fecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_fecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_fecha.setEnabled(false);
        txt_fecha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_fechaKeyPressed(evt);
            }
        });

        btn_crear_proveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/add.png"))); // NOI18N
        btn_crear_proveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crear_proveedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbx_tido, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_numero_doc)
                    .addComponent(txt_fecha, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                    .addComponent(txt_serie_doc))
                .addGap(38, 38, 38)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_ruc_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_crear_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_buscar_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_razon_social, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbx_tido, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_ruc_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_buscar_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_crear_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_serie_doc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_numero_doc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txt_razon_social, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Datos de Compra", jPanel3);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Agregar Items de Compra"));

        jLabel2.setText("Buscar:");

        txt_buscar_producto.setEnabled(false);
        txt_buscar_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_buscar_productoKeyPressed(evt);
            }
        });

        btn_crear_producto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/add.png"))); // NOI18N
        btn_crear_producto.setEnabled(false);
        btn_crear_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crear_productoActionPerformed(evt);
            }
        });

        jLabel12.setText("Codigo:");

        txt_codigo_producto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_codigo_producto.setEnabled(false);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Descripcion:");

        txt_nombre_producto.setEnabled(false);

        jLabel14.setText("Precio Venta:");

        jLabel15.setText("Precio Compra:");

        txt_compra_producto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_compra_producto.setEnabled(false);
        txt_compra_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_compra_productoKeyPressed(evt);
            }
        });

        txt_precio_producto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_precio_producto.setEnabled(false);
        txt_precio_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_precio_productoKeyPressed(evt);
            }
        });

        btn_add_producto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/add.png"))); // NOI18N
        btn_add_producto.setText("Agregar Producto");
        btn_add_producto.setEnabled(false);
        btn_add_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_productoActionPerformed(evt);
            }
        });
        btn_add_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn_add_productoKeyPressed(evt);
            }
        });

        jLabel16.setText("Cantidad");

        txt_cantidad_producto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_cantidad_producto.setEnabled(false);
        txt_cantidad_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cantidad_productoKeyPressed(evt);
            }
        });

        txt_und_medida.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_und_medida.setEnabled(false);

        btn_recargar_productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/arrow_redo.png"))); // NOI18N
        btn_recargar_productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_recargar_productosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txt_buscar_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_recargar_productos, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_crear_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_codigo_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(txt_compra_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(65, 65, 65)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(18, 18, 18)
                                .addComponent(txt_precio_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_cantidad_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_add_producto))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_nombre_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_und_medida, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_crear_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_buscar_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_recargar_productos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_codigo_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nombre_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_und_medida, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_compra_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_precio_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_add_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(txt_cantidad_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel17.setText("Total Items");

        lbl_total_items.setText("jLabel18");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(46, 46, 46)
                        .addComponent(lbl_total_items)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(lbl_total_items))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Items de Compra", jPanel4);

        jLabel8.setText("SELECCIONAR DOCUMENTO DE INGRESO DE MERCADERIA Y PRESIONA ENTER");

        jLabel9.setText("Periodo:");

        txt_periodo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_periodo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_periodoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(39, 39, 39)
                        .addComponent(txt_tot, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btn_cambiar_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cambiar_precio, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67)
                        .addComponent(btn_registrar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_cerrar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_periodo, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txt_periodo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_tot, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_cambiar_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_cambiar_precio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_cerrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_registrar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Datos de la compra");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_cambiar_precioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cambiar_precioActionPerformed

    }//GEN-LAST:event_btn_cambiar_precioActionPerformed

    private void btn_cambiar_cantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cambiar_cantidadActionPerformed

    }//GEN-LAST:event_btn_cambiar_cantidadActionPerformed

    private void btn_buscar_proveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscar_proveedorActionPerformed
        cargar_proveedores();
        txt_razon_social.requestFocus();
    }//GEN-LAST:event_btn_buscar_proveedorActionPerformed

    private void cbx_tidoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbx_tidoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cl_tipo_documento c_tido = (cl_tipo_documento) cbx_tido.getSelectedItem();
            int id_tido = c_tido.getId();
            if (id_tido == 6) {
                c_documento.setId(id_tido);
                c_documento.datos_documento();
                txt_serie_doc.setText(c_documento.getSerie() + "");
                txt_numero_doc.setText(c_documento.getNumero() + "");
                txt_fecha.setEnabled(true);
                txt_fecha.requestFocus();
            } else {
                txt_serie_doc.setEnabled(true);
                txt_serie_doc.requestFocus();
            }
        }
    }//GEN-LAST:event_cbx_tidoKeyPressed

    private void txt_fechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_fechaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_fecha.getText().length() == 10) {
                cargar_proveedores();
                txt_razon_social.setEnabled(true);
                txt_razon_social.requestFocus();
            } else {
                Notification.show("Registrar Ingresos", "Falta Ingresar Fecha del Documento");
                txt_fecha.setText("");
                txt_fecha.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_fechaKeyPressed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        detalle.removeRow(nro_fila);
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void btn_add_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_productoActionPerformed
        if (!txt_codigo_producto.getText().isEmpty()) {
            double compra = Double.parseDouble(txt_compra_producto.getText());
            double cantidad = Double.parseDouble(txt_cantidad_producto.getText());
            total_ingreso = total_ingreso + (compra * cantidad);
            txt_tot.setText(c_varios.formato_totales(total_ingreso));

            Object fila[] = new Object[7];
            fila[0] = txt_codigo_producto.getText();
            fila[1] = txt_nombre_producto.getText();
            fila[2] = c_varios.formato_numero(cantidad);
            fila[3] = txt_und_medida.getText();
            fila[4] = txt_precio_producto.getText();
            fila[5] = c_varios.formato_numero(compra);
            fila[6] = c_varios.formato_numero(compra * cantidad);

            total_items++;
            lbl_total_items.setText(total_items + "");
            detalle.addRow(fila);
        } else {
            Notification.show("Registrar Ingresos", "Error al Seleccionar el Producto");
        }

        txt_codigo_producto.setText("");
        txt_nombre_producto.setText("");
        txt_und_medida.setText("");
        txt_precio_producto.setText("");
        txt_compra_producto.setText("");
        txt_cantidad_producto.setText("1.00");
        txt_precio_producto.setEnabled(false);
        txt_compra_producto.setEnabled(false);
        txt_cantidad_producto.setEnabled(false);
        btn_add_producto.setEnabled(false);
        btn_registrar.setEnabled(true);
        txt_buscar_producto.setEnabled(true);
        txt_buscar_producto.requestFocus();

    }//GEN-LAST:event_btn_add_productoActionPerformed

    private void btn_add_productoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_add_productoKeyPressed

    }//GEN-LAST:event_btn_add_productoKeyPressed

    private void txt_serie_docKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_serie_docKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_serie_doc.getText().length() > 0) {
                txt_numero_doc.setEnabled(true);
                txt_numero_doc.requestFocus();
            } else {
                Notification.show("Registrar Ingresos", "Falta Ingresar Serie del Documento");
                txt_serie_doc.setText("");
                txt_serie_doc.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_serie_docKeyPressed

    private void t_detalleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_t_detalleMousePressed
        nro_fila = t_detalle.getSelectedRow();
        activar_botones();
    }//GEN-LAST:event_t_detalleMousePressed

    private void t_detalleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t_detalleKeyPressed

    }//GEN-LAST:event_t_detalleKeyPressed

    private void txt_numero_docKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numero_docKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_numero_doc.getText().length() > 0) {
                txt_fecha.setEnabled(true);
                txt_fecha.selectAll();
                txt_fecha.requestFocus();
            } else {
                Notification.show("Registrar Ingresos", "Falta Ingresar Numero del Documento");
                txt_numero_doc.setText("");
                txt_numero_doc.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_numero_docKeyPressed

    private void btn_registrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_registrarActionPerformed
        btn_registrar.setEnabled(false);
        int confirmado = JOptionPane.showConfirmDialog(null, "¿Esta Seguro de Registrar el Documento de Compra?");

        if (JOptionPane.OK_OPTION == confirmado) {

            cl_tipo_documento c_tido = (cl_tipo_documento) cbx_tido.getSelectedItem();
            int id_tido = c_tido.getId();

            c_ingreso.setPeriodo(txt_periodo.getText());
            c_ingreso.setId_ingreso(c_ingreso.obtener_codigo());
            c_ingreso.setFecha(c_varios.formato_fecha_mysql(txt_fecha.getText()));
            c_ingreso.setNumero(Integer.parseInt(txt_numero_doc.getText()));
            c_ingreso.setSerie(txt_serie_doc.getText());
            c_ingreso.setId_documento(id_tido);
            c_ingreso.setId_proveedor(c_proveedor.getId() + "");
            c_ingreso.setTotal(total_ingreso);
            c_ingreso.setId_empleado(frm_menu.c_empleado.getId_empleado() + "");

            if (c_ingreso.insertar()) {
                Notification.show("Ingreso de Mercaderia", "El ingreso se realizo correctamente", Notification.OK_MESSAGE, Notification.NICON_LIGHT_THEME);

                c_documento.setId(id_tido);
                if (c_documento.getId() == 6) {
                    c_documento.actualizar_numero();
                }

                c_detalle.setPeriodo(c_ingreso.getPeriodo());
                c_detalle.setIngreso(c_ingreso.getId_ingreso());

                int nro_filas = t_detalle.getRowCount();
                for (int i = 0; i < nro_filas; i++) {
                    c_detalle.setProducto(Integer.parseInt(t_detalle.getValueAt(i, 0).toString()));
                    c_detalle.setCantidad(Double.parseDouble(t_detalle.getValueAt(i, 2).toString()));
                    c_detalle.setCosto(Double.parseDouble(t_detalle.getValueAt(i, 5).toString()));
                    c_detalle.setPrecio(Double.parseDouble(t_detalle.getValueAt(i, 4).toString()));
                    c_detalle.setId_unidad(1);

                    c_detalle.insertar();
                }

                frm_ver_ingresos ver_ingresos = new frm_ver_ingresos();
                c_varios.llamar_ventana(ver_ingresos);
                this.dispose();
            }
        } else {
            btn_registrar.setEnabled(true);
        }
    }//GEN-LAST:event_btn_registrarActionPerformed

    private void btn_cerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btn_cerrarActionPerformed

    private void btn_crear_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crear_productoActionPerformed
        Frame f = JOptionPane.getRootFrame();
        frm_reg_producto dialog = new frm_reg_producto(f, true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_btn_crear_productoActionPerformed

    private void txt_serie_docKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_serie_docKeyTyped
        c_varios.limitar_caracteres(evt, txt_serie_doc, 12);
    }//GEN-LAST:event_txt_serie_docKeyTyped

    private void txt_numero_docKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numero_docKeyTyped
        c_varios.limitar_caracteres(evt, txt_numero_doc, 7);
    }//GEN-LAST:event_txt_numero_docKeyTyped

    private void txt_ruc_proveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ruc_proveedorKeyPressed

    }//GEN-LAST:event_txt_ruc_proveedorKeyPressed

    private void txt_buscar_productoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscar_productoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_buscar_producto.getText().length() > 15) {
                if (producto_correcto == true) {
                    txt_compra_producto.setEnabled(true);
                    txt_buscar_producto.setText("");
                    txt_buscar_producto.setEnabled(false);
                    txt_compra_producto.selectAll();
                    txt_compra_producto.requestFocus();
                } else {
                    Notification.show("INGRESO DE PRODUCTOS", "Error al Seleccionar Item");
                    txt_buscar_producto.setText("");
                    txt_buscar_producto.requestFocus();
                }
            }
        }
    }//GEN-LAST:event_txt_buscar_productoKeyPressed

    private void txt_compra_productoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_compra_productoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            double costo = Double.parseDouble(txt_compra_producto.getText());
            if (costo >= 0) {
                txt_precio_producto.setEnabled(true);
                txt_precio_producto.selectAll();
                txt_precio_producto.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "NO HA INGRESADO DATOS EN COSTO DEL PRODUCTO");
                txt_compra_producto.selectAll();
                txt_compra_producto.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_compra_productoKeyPressed

    private void txt_precio_productoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_precio_productoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            double precio = Double.parseDouble(txt_precio_producto.getText());
            if (precio >= 0) {
                txt_cantidad_producto.setEnabled(true);
                txt_cantidad_producto.selectAll();
                txt_cantidad_producto.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "NO HA INGRESADO PRECIO DE VENTA DEL PRODUCTO");
                txt_precio_producto.selectAll();
                txt_precio_producto.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_precio_productoKeyPressed

    private void txt_cantidad_productoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cantidad_productoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            double cantidad = Double.parseDouble(txt_cantidad_producto.getText());
            if (cantidad >= 0) {
                btn_add_producto.setEnabled(true);
                btn_add_producto.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "NO HA INGRESADO CANTIDAD DEL PRODUCTO");
                txt_cantidad_producto.selectAll();
                txt_cantidad_producto.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_cantidad_productoKeyPressed

    private void btn_recargar_productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_recargar_productosActionPerformed
        cargar_productos();
    }//GEN-LAST:event_btn_recargar_productosActionPerformed

    private void btn_crear_proveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crear_proveedorActionPerformed
        Frame f = JOptionPane.getRootFrame();
        frm_reg_proveedor dialog = new frm_reg_proveedor(f, true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_btn_crear_proveedorActionPerformed

    private void txt_periodoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_periodoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_periodo.getText().length() == 6) {
                jTabbedPane1.setSelectedIndex(0);
                cbx_tido.setEnabled(true);
                cbx_tido.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_periodoKeyPressed

    private void txt_razon_socialKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_razon_socialKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_razon_social.getText().length() > 11) {
                cl_tipo_documento c_tido = (cl_tipo_documento) cbx_tido.getSelectedItem();
                c_ingreso.setId_proveedor(txt_ruc_proveedor.getText());
                c_ingreso.setNumero(Integer.parseInt(txt_numero_doc.getText()));
                c_ingreso.setSerie(txt_serie_doc.getText());
                c_ingreso.setId_documento(c_tido.getId());
                if (c_ingreso.comprobar_documento()) {
                    JOptionPane.showMessageDialog(null, "ESTE DOCUMENTO YA ESTA REGISTRADO");
                    cbx_tido.requestFocus();
                } else {
                    cargar_productos();
                    jTabbedPane1.setSelectedIndex(1);
                    btn_crear_producto.setEnabled(true);
                    txt_buscar_producto.setEnabled(true);
                    txt_buscar_producto.requestFocus();
                }
            }
        }
    }//GEN-LAST:event_txt_razon_socialKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btn_add_producto;
    private javax.swing.JButton btn_buscar_proveedor;
    private javax.swing.JButton btn_cambiar_cantidad;
    private javax.swing.JButton btn_cambiar_precio;
    private javax.swing.JButton btn_cerrar;
    private javax.swing.JButton btn_crear_producto;
    private javax.swing.JButton btn_crear_proveedor;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_recargar_productos;
    public static javax.swing.JButton btn_registrar;
    public static javax.swing.JComboBox cbx_tido;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JDialog jd_ver_productos;
    private javax.swing.JLabel lbl_total_items;
    public static javax.swing.JTable t_detalle;
    public static javax.swing.JTextField txt_buscar_producto;
    public static javax.swing.JTextField txt_cantidad_producto;
    public static javax.swing.JTextField txt_codigo_producto;
    public static javax.swing.JTextField txt_compra_producto;
    private javax.swing.JTextField txt_direccion;
    public static javax.swing.JFormattedTextField txt_fecha;
    public static javax.swing.JTextField txt_nombre_producto;
    public static javax.swing.JTextField txt_numero_doc;
    private javax.swing.JTextField txt_periodo;
    public static javax.swing.JTextField txt_precio_producto;
    public static javax.swing.JTextField txt_razon_social;
    private javax.swing.JTextField txt_ruc_proveedor;
    public static javax.swing.JTextField txt_serie_doc;
    public static javax.swing.JTextField txt_tot;
    private javax.swing.JTextField txt_und_medida;
    // End of variables declaration//GEN-END:variables
}
