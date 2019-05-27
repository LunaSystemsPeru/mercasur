/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import clases.cl_cliente;
import clases.cl_json_entidad;
import clases.cl_varios;
import java.awt.event.KeyEvent;
import java.security.SecureRandom;
import javax.swing.JOptionPane;
import mercasur.frm_menu;
import nicon.notify.core.Notification;
import org.json.simple.parser.ParseException;

/**
 *
 * @author CALIDAD
 */
public class frm_reg_cliente extends javax.swing.JDialog {

    public static cl_cliente c_cliente = new cl_cliente();
    cl_varios c_varios = new cl_varios();

    public static String accion = "registrar";
    public static String origen = "cliente";

    /**
     * Creates new form frm_reg_cliente
     */
    public frm_reg_cliente(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        if (accion.equals("registrar")) {
            c_cliente.setId_cliente(0);
        }

        if (c_cliente.getId_cliente() != 0) {
            btn_reg.setText("Modificar");
            System.out.println(accion);
            c_cliente.obtener_datos();
            txt_ndoc.setText(c_cliente.getDocumento());
            txt_nom.setText(c_cliente.getNombre());
            txt_dir.setText(c_cliente.getDireccion());
            txt_tel.setText(c_cliente.getTelefono() + "");
            txt_tel1.setText(c_cliente.getCelular() + "");
            txt_ndoc.setEnabled(false);
            txt_nom.setEnabled(true);
            txt_dir.setEnabled(true);
            txt_tel.setEnabled(true);
            txt_tel1.setEnabled(true);
            txt_nom.requestFocus();
            btn_reg.setEnabled(true);
        }
    }

    private void llenar() {
        c_cliente.setDocumento(txt_ndoc.getText());
        c_cliente.setCelular(Integer.parseInt(txt_tel1.getText()));
        c_cliente.setTelefono(Integer.parseInt(txt_tel.getText()));
        c_cliente.setNombre(txt_nom.getText().trim().toUpperCase());
        c_cliente.setDireccion(txt_dir.getText().trim().toUpperCase());
        c_cliente.setN_comercial(txt_nombre_comercial.getText().trim().toUpperCase());
        c_cliente.setId_zona(frm_menu.c_zona.getId_zona());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        txt_ndoc = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_nom = new javax.swing.JTextField();
        txt_dir = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_tel = new javax.swing.JTextField();
        txt_tel1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btn_reg = new javax.swing.JButton();
        btn_cer = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_nombre_comercial = new javax.swing.JTextField();
        txt_zona = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registrar Cliente");

        jLabel3.setText("Nombre o Razon Social:");

        txt_ndoc.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_ndoc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_ndocKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_ndocKeyPressed(evt);
            }
        });

        jLabel4.setText("Direccion:");

        jLabel7.setText("Telefono 2 :");

        txt_nom.setEnabled(false);
        txt_nom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_nomKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nomKeyTyped(evt);
            }
        });

        txt_dir.setEnabled(false);
        txt_dir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_dirKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_dirKeyTyped(evt);
            }
        });

        jLabel5.setText("Telefono:");

        txt_tel.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_tel.setEnabled(false);
        txt_tel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_telKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_telKeyTyped(evt);
            }
        });

        txt_tel1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_tel1.setEnabled(false);
        txt_tel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_tel1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_tel1KeyTyped(evt);
            }
        });

        jLabel2.setText("Nro de Doc:");

        btn_reg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/add.png"))); // NOI18N
        btn_reg.setText("Registrar");
        btn_reg.setEnabled(false);
        btn_reg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_regActionPerformed(evt);
            }
        });
        btn_reg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn_regKeyPressed(evt);
            }
        });

        btn_cer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/cancel.png"))); // NOI18N
        btn_cer.setText("Cerrar");
        btn_cer.setFocusable(false);
        btn_cer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cerActionPerformed(evt);
            }
        });

        jLabel1.setText("Zona:");

        jLabel6.setText("Nombre Comercial");

        txt_nombre_comercial.setEnabled(false);

        txt_zona.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_dir, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
                            .addComponent(txt_nom)
                            .addComponent(txt_nombre_comercial)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_tel, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_tel1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_ndoc, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txt_zona, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_cer))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_reg)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_zona, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_ndoc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nom, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_nombre_comercial, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_dir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_tel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_tel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_reg, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_ndocKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ndocKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String documento = txt_ndoc.getText();
            if (c_varios.esDecimal(documento)) {
                boolean existe_cliente = false;
                c_cliente.setDocumento(documento);
                existe_cliente = c_cliente.buscar_cliente_documento();

                if (existe_cliente == false) {
                    if (documento.length() == 8) {
                        //dni
                        try {
                            String json = cl_json_entidad.getJSONDNI(documento);
                            //Lo mostramos
                            String datos = cl_json_entidad.showJSONDNI(json);
                            txt_nom.setText(datos.trim());
                            txt_ndoc.setEnabled(false);
                            txt_nom.setEnabled(false);
                            txt_dir.setEnabled(true);
                            txt_dir.requestFocus();

                        } catch (ParseException e) {
                            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
                        }

                    }
                    if (documento.length() == 11) {
                        //ruc
                        try {
                            String json = cl_json_entidad.getJSONRUC(documento);
                            //Lo mostramos
                            String[] datos = cl_json_entidad.showJSONRUC(json);
                            txt_nom.setText(datos[0].trim());
                            txt_dir.setText(datos[1].trim());
                            txt_ndoc.setEnabled(false);
                            txt_nom.setEnabled(false);
                            txt_dir.setEnabled(false);
                            txt_tel.setEnabled(true);
                            txt_tel.requestFocus();

                        } catch (ParseException e) {
                            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "los datos ingresados ya existen\nEste cliente ya esta registrado");
                    accion = "";
                    origen = "";
                    c_cliente.setId_cliente(0);
                    this.dispose();
                }
                existe_cliente = false;
            } else {
                JOptionPane.showMessageDialog(null, "POR FAVOR INGRESE CORRECTAMENTE NUMEROS");
                txt_ndoc.setText("");
                txt_ndoc.requestFocus();
            }
            
            if (txt_ndoc.getText().length() == 0) {
                SecureRandom sr = new SecureRandom();
                String codigo = "SD" + (sr.nextInt(99999) + 1000);
                txt_ndoc.setText(codigo);
                txt_ndoc.setEnabled(false);
                txt_nom.setEnabled(true);
                txt_nom.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_ndocKeyPressed

    private void txt_ndocKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ndocKeyTyped
        char car = evt.getKeyChar();
        if ((car < '0' || car > '9')) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_ndocKeyTyped

    private void txt_nomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nomKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_nom.getText().length() > 0) {
                txt_dir.setEnabled(true);
                txt_dir.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_nomKeyPressed

    private void txt_nomKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nomKeyTyped
        if (txt_nom.getText().length() == 245) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_nomKeyTyped

    private void txt_dirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dirKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_dir.getText().length() > 0) {
                txt_tel.setEnabled(true);
                txt_tel.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_dirKeyPressed

    private void txt_dirKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dirKeyTyped
        if (txt_dir.getText().length() == 245) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_dirKeyTyped

    private void txt_telKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_tel.getText().length() > 0) {
                txt_tel1.setEnabled(true);
                txt_tel1.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_telKeyPressed

    private void txt_telKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telKeyTyped
        if (txt_tel.getText().length() == 9) {
            evt.consume();
        }
        char car = evt.getKeyChar();
        if ((car < '0' || car > '9')) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_telKeyTyped

    private void txt_tel1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_tel1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_tel1.getText().length() > 0) {
                btn_reg.setEnabled(true);
                btn_reg.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_tel1KeyPressed

    private void txt_tel1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_tel1KeyTyped
        if (txt_tel1.getText().length() == 9) {
            evt.consume();
        }
        char car = evt.getKeyChar();
        if ((car < '0' || car > '9')) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_tel1KeyTyped

    private void btn_regActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_regActionPerformed
        btn_reg.setEnabled(false);
        int confirmado = JOptionPane.showConfirmDialog(null, "¿Esta Seguro de Registrar al Cliente?");

        if (JOptionPane.OK_OPTION == confirmado) {
            llenar();

            if (accion.equals("registrar")) {
                c_cliente.setId_cliente(c_cliente.obtener_codigo());
                if (c_cliente.getDocumento().equals("")) {
                    c_cliente.setDocumento(c_cliente.getId_cliente() + "");
                }
                if (c_cliente.insertar()) {
                    Notification.show("Clientes", "CLIENTE REGISTRADO CORRECTAMENTE");
                    if (origen.equals("venta")) {
                        //frm_reg_venta.btn_cargar_clientes.doClick();
                    }
                    this.dispose();
                }
            }

            if (accion.equals("modificar")) {
                if (c_cliente.modificar()) {
                    Notification.show("Clientes", "CLIENTE MODIFICADO CORRECTAMENTE");
                    this.dispose();
                }
            }
            accion = "";
            origen = "";
        }
    }//GEN-LAST:event_btn_regActionPerformed

    private void btn_regKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_regKeyPressed

    }//GEN-LAST:event_btn_regKeyPressed

    private void btn_cerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cerActionPerformed
        this.dispose();

    }//GEN-LAST:event_btn_cerActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frm_reg_cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm_reg_cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm_reg_cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_reg_cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frm_reg_cliente dialog = new frm_reg_cliente(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cer;
    public static javax.swing.JButton btn_reg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    public static javax.swing.JTextField txt_dir;
    public static javax.swing.JTextField txt_ndoc;
    public static javax.swing.JTextField txt_nom;
    private javax.swing.JTextField txt_nombre_comercial;
    public static javax.swing.JTextField txt_tel;
    public static javax.swing.JTextField txt_tel1;
    private javax.swing.JTextField txt_zona;
    // End of variables declaration//GEN-END:variables
}