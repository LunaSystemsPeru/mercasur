/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import clases_autocomplete.cl_ac_productos;
import clases.cl_conectar;
import clases.cl_detalle_inventario;
import clases.cl_inventario;
import clases.cl_productos;
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
import nicon.notify.core.Notification;
import vistas.frm_ver_inventarios;

/**
 *
 * @author CALIDAD
 */
public class frm_reg_inventario extends javax.swing.JInternalFrame {

    cl_conectar c_conectar = new cl_conectar();
    cl_varios c_varios = new cl_varios();

    cl_productos c_producto = new cl_productos();
    cl_inventario c_inventario = new cl_inventario();
    cl_detalle_inventario c_detalle = new cl_detalle_inventario();

    DefaultTableModel detalle = null;

    TextAutoCompleter autocompletar = null;

    boolean producto_correcto = false;

    /**
     * Creates new form frm_reg_inventario
     */
    public frm_reg_inventario() {
        initComponents();

        cargar_productos();

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
        detalle.addColumn("P. Venta");
        detalle.addColumn("Und. Medida");
        detalle.addColumn("Ubicacion");
        detalle.addColumn("Actual");
        detalle.addColumn("Fisico");
        detalle.addColumn("Diferencia");
        t_detalle.setModel(detalle);
        t_detalle.getColumnModel().getColumn(0).setPreferredWidth(40);
        t_detalle.getColumnModel().getColumn(1).setPreferredWidth(350);
        t_detalle.getColumnModel().getColumn(2).setPreferredWidth(60);
        t_detalle.getColumnModel().getColumn(3).setPreferredWidth(60);
        t_detalle.getColumnModel().getColumn(4).setPreferredWidth(60);
        t_detalle.getColumnModel().getColumn(5).setPreferredWidth(50);
        t_detalle.getColumnModel().getColumn(6).setPreferredWidth(50);
        t_detalle.getColumnModel().getColumn(7).setPreferredWidth(50);
        c_varios.centrar_celda(t_detalle, 0);
        c_varios.derecha_celda(t_detalle, 2);
        c_varios.centrar_celda(t_detalle, 3);
        c_varios.centrar_celda(t_detalle, 4);
        c_varios.derecha_celda(t_detalle, 5);
        c_varios.derecha_celda(t_detalle, 6);
        c_varios.derecha_celda(t_detalle, 7);

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
                        c_producto.ver_datos_producto();
                        String descripcion = ((cl_ac_productos) itemSelected).getDescripcion();
                        String und_medida = ((cl_ac_productos) itemSelected).getUnd_medida();
                        txt_und_medida.setText(und_medida);
                        txt_nombre_producto.setText(descripcion.toUpperCase().trim());
                        txt_codigo_producto.setText(id + "");
                        txt_precio_producto.setText(c_varios.formato_numero(c_producto.getPrecio()));
                        txt_cantidad_actual.setText(c_varios.formato_numero(c_producto.getCantidad()));
                        txt_diferencia.setText(c_varios.formato_numero(c_producto.getCantidad()));

                        producto_correcto = true;
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
            String sql = "select p.id_producto, p.descripcion, p.cant_actual, u.descripcion as  und_medida, p.precio_venta, p.estado, m.nombre as marca "
                    + "from productos as p "
                    + "inner join und_medida as u on p.id_unidad = u.id_unidad "
                    + "inner join marcas as m on m.id_marca = p.id_marca "
                    + "order by m.nombre, p.descripcion asc";
            ResultSet rs = c_conectar.consulta(st, sql);
            while (rs.next()) {

                String descripcion = rs.getString("p.descripcion").trim();
                String marca = rs.getString("marca").trim();
                int id = rs.getInt("id_producto");
                double cantidad = rs.getDouble("p.cant_actual");
                String und_medida = rs.getString("und_medida").trim();
                double precio = rs.getDouble("p.precio_venta");
                autocompletar.addItem(new cl_ac_productos(id, descripcion + " " + marca, cantidad, und_medida, precio));
            }
            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
            autocompletar.setMode(0);
        } catch (SQLException ex) {
            JOptionPane.showInternalMessageDialog(this, ex.getLocalizedMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_buscar_producto = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txt_precio_producto = new javax.swing.JTextField();
        btn_add_producto = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        txt_diferencia = new javax.swing.JTextField();
        txt_und_medida = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txt_cantidad_fisico = new javax.swing.JTextField();
        txt_cantidad_actual = new javax.swing.JTextField();
        txt_codigo_producto = new javax.swing.JTextField();
        txt_nombre_producto = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txt_ubicacion = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        t_detalle = new javax.swing.JTable();
        btn_registrar = new javax.swing.JButton();
        btn_cerrar = new javax.swing.JButton();

        setTitle("Registrar Inventario de Productos");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Agregar Productos"));

        jLabel2.setText("Buscar:");

        txt_buscar_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_buscar_productoKeyPressed(evt);
            }
        });

        jLabel12.setText("Producto:");

        jLabel14.setText("Precio Venta:");

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

        jLabel16.setText("Diferencia:");

        txt_diferencia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_diferencia.setEnabled(false);
        txt_diferencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_diferenciaKeyPressed(evt);
            }
        });

        txt_und_medida.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_und_medida.setEnabled(false);

        jLabel17.setText("Cantidad actual:");

        jLabel18.setText("Cantidad Fisico:");

        txt_cantidad_fisico.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_cantidad_fisico.setEnabled(false);
        txt_cantidad_fisico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cantidad_fisicoKeyPressed(evt);
            }
        });

        txt_cantidad_actual.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_cantidad_actual.setEnabled(false);
        txt_cantidad_actual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cantidad_actualKeyPressed(evt);
            }
        });

        txt_codigo_producto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_codigo_producto.setEnabled(false);
        txt_codigo_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_codigo_productoKeyPressed(evt);
            }
        });

        txt_nombre_producto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_nombre_producto.setEnabled(false);
        txt_nombre_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_nombre_productoKeyPressed(evt);
            }
        });

        jLabel19.setText("Ubicacion:");

        txt_ubicacion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_ubicacion.setEnabled(false);
        txt_ubicacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_ubicacionKeyPressed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/add.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_precio_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_diferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(72, 72, 72)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_cantidad_fisico, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_cantidad_actual, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_und_medida, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)))
                        .addGap(81, 81, 81)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_add_producto)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_ubicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txt_codigo_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_nombre_producto))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txt_buscar_producto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_buscar_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_codigo_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nombre_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_precio_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_cantidad_actual, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_und_medida, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_ubicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_diferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_add_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_cantidad_fisico, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

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

        btn_registrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/add.png"))); // NOI18N
        btn_registrar.setText("Registrar");
        btn_registrar.setEnabled(false);
        btn_registrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_registrarActionPerformed(evt);
            }
        });

        btn_cerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/cancel.png"))); // NOI18N
        btn_cerrar.setText("Cerrar");
        btn_cerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_registrar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(89, 89, 89)
                        .addComponent(btn_cerrar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_registrar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_buscar_productoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscar_productoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_buscar_producto.getText().length() > 15) {
                if (producto_correcto == true) {
                    txt_buscar_producto.setText("");
                    txt_buscar_producto.setEnabled(false);
                    txt_cantidad_fisico.setEnabled(true);
                    txt_cantidad_fisico.selectAll();
                    txt_cantidad_fisico.requestFocus();
                } else {
                    Notification.show("INGRESO DE PRODUCTOS", "Error al Seleccionar Item");
                    txt_buscar_producto.setText("");
                    txt_buscar_producto.requestFocus();
                }
            }
        }
    }//GEN-LAST:event_txt_buscar_productoKeyPressed

    private void txt_precio_productoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_precio_productoKeyPressed

    }//GEN-LAST:event_txt_precio_productoKeyPressed

    private void btn_add_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_productoActionPerformed
        if (!txt_codigo_producto.getText().isEmpty()) {
            Object fila[] = new Object[8];
            fila[0] = txt_codigo_producto.getText().trim();
            fila[1] = txt_nombre_producto.getText();
            fila[2] = txt_precio_producto.getText();
            fila[3] = txt_und_medida.getText();
            fila[4] = txt_ubicacion.getText();
            fila[5] = txt_cantidad_actual.getText();
            fila[6] = txt_cantidad_fisico.getText();
            fila[7] = txt_diferencia.getText();
            detalle.addRow(fila);

        } else {
            Notification.show("Registrar Ingresos", "Error al Seleccionar el Producto");
        }

        txt_codigo_producto.setText("");
        txt_nombre_producto.setText("");
        txt_und_medida.setText("");
        txt_precio_producto.setText("");
        txt_diferencia.setText("");
        txt_cantidad_actual.setText("");
        txt_cantidad_fisico.setText("");
        txt_cantidad_fisico.setEnabled(false);
        txt_precio_producto.setEnabled(false);
        txt_diferencia.setEnabled(false);
        btn_add_producto.setEnabled(false);
        btn_registrar.setEnabled(true);
        txt_buscar_producto.setEnabled(true);
        txt_buscar_producto.requestFocus();
    }//GEN-LAST:event_btn_add_productoActionPerformed

    private void btn_add_productoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_add_productoKeyPressed

    }//GEN-LAST:event_btn_add_productoKeyPressed

    private void txt_diferenciaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_diferenciaKeyPressed

    }//GEN-LAST:event_txt_diferenciaKeyPressed

    private void txt_cantidad_fisicoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cantidad_fisicoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String cantidad = txt_cantidad_fisico.getText();
            double dcantidad = 0;
            double dactual = Double.parseDouble(txt_cantidad_actual.getText());
            double ddiferencia = 0;
            if (c_varios.esDecimal(cantidad)) {
                dcantidad = Double.parseDouble(cantidad);
                ddiferencia = dcantidad - dactual;
                txt_cantidad_fisico.setText(c_varios.formato_numero(dcantidad));
                txt_diferencia.setText(c_varios.formato_numero(ddiferencia));
                btn_add_producto.setEnabled(true);
                btn_add_producto.requestFocus();
            } else {
                txt_cantidad_fisico.setText("");
                txt_cantidad_fisico.requestFocus();
                JOptionPane.showMessageDialog(null, "NO HA INGRESADO CANTIDAD FISICA DEL PRODUCTO");
            }
        }
    }//GEN-LAST:event_txt_cantidad_fisicoKeyPressed

    private void txt_cantidad_actualKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cantidad_actualKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cantidad_actualKeyPressed

    private void txt_codigo_productoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_codigo_productoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_codigo_productoKeyPressed

    private void txt_nombre_productoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombre_productoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nombre_productoKeyPressed

    private void txt_ubicacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ubicacionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ubicacionKeyPressed

    private void t_detalleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_t_detalleMousePressed

    }//GEN-LAST:event_t_detalleMousePressed

    private void t_detalleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t_detalleKeyPressed

    }//GEN-LAST:event_t_detalleKeyPressed

    private void btn_registrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_registrarActionPerformed
        btn_registrar.setEnabled(false);
        int nro_filas = t_detalle.getRowCount();
        int confirmado = JOptionPane.showConfirmDialog(null, "¿Esta Seguro de Registrar el Inventario?");
        if (nro_filas > -1) {
            if (JOptionPane.OK_OPTION == confirmado) {
                c_inventario.setPeriodo(c_varios.obtener_periodo());
                c_inventario.setCodigo(c_inventario.obtener_codigo());
                c_inventario.setInventario(c_inventario.getPeriodo() + "" + c_varios.ceros_izquieda_numero(3, c_inventario.getCodigo()));
                c_inventario.setFecha(c_varios.getFechaActual());
                c_inventario.setUsuario(frm_menu.c_empleado.getId_empleado() + "");

                if (c_inventario.insertar()) {
                    c_detalle.setInventario(c_inventario.getInventario());
                    for (int i = 0; i < nro_filas; i++) {
                        c_detalle.setProducto(Integer.parseInt(t_detalle.getValueAt(i, 0).toString()));
                        c_detalle.setPrecio(Double.parseDouble(t_detalle.getValueAt(i, 2).toString()));
                        c_detalle.setUbicacion(t_detalle.getValueAt(i, 4).toString());
                        c_detalle.setCactual(Double.parseDouble(t_detalle.getValueAt(i, 5).toString()));
                        c_detalle.setCfisico(Double.parseDouble(t_detalle.getValueAt(i, 6).toString()));
                        c_detalle.insertar();
                    }

                    btn_cerrar.doClick();
                }
            } else {
                btn_registrar.setEnabled(true);
            }
        }
    }//GEN-LAST:event_btn_registrarActionPerformed

    private void btn_cerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cerrarActionPerformed
        this.dispose();
        frm_ver_inventarios ver_inventarios = new frm_ver_inventarios();
        c_varios.llamar_ventana(ver_inventarios);

    }//GEN-LAST:event_btn_cerrarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Frame f = JOptionPane.getRootFrame();
        frm_reg_producto dialog = new frm_reg_producto(f, true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btn_add_producto;
    private javax.swing.JButton btn_cerrar;
    public static javax.swing.JButton btn_registrar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable t_detalle;
    public static javax.swing.JTextField txt_buscar_producto;
    public static javax.swing.JTextField txt_cantidad_actual;
    public static javax.swing.JTextField txt_cantidad_fisico;
    public static javax.swing.JTextField txt_codigo_producto;
    public static javax.swing.JTextField txt_diferencia;
    public static javax.swing.JTextField txt_nombre_producto;
    public static javax.swing.JTextField txt_precio_producto;
    public static javax.swing.JTextField txt_ubicacion;
    private javax.swing.JTextField txt_und_medida;
    // End of variables declaration//GEN-END:variables
}
