/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hilos;

import clases.cl_varios;
import clases_varios.cl_PeticionPost;
import clases_varios.cl_numero_a_letras;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author luis
 */
public class cl_enviar_venta extends Thread {

    cl_PeticionPost c_peticion = new cl_PeticionPost();
    cl_numero_a_letras c_letras = new cl_numero_a_letras();
    cl_varios c_varios = new cl_varios();

    private int id_venta;
    private String periodo;
    private int id_tido;
    private double total;
    private String letras_numeros = "";

    public cl_enviar_venta() {
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public void setId_tido(int id_tido) {
        this.id_tido = id_tido;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    private void enviar_sunat() {
        //enviar boleta
        String[] envio_sunat = c_peticion.enviar_documento(id_venta + "", periodo, id_tido);
        String nombre_archivo = envio_sunat[0];
        String url_codigo_qr = envio_sunat[2];
        String hash = envio_sunat[3];
        String estatus = envio_sunat[5];
        if (estatus.equals("error")) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error al recibir el comprobante");
        }

        letras_numeros = c_letras.Convertir(total + "", true) + " SOLES";

        File miDir = new File(".");
        try {
            Map<String, Object> parametros = new HashMap<>();
            String path = miDir.getCanonicalPath();
            String direccion = path + File.separator + "reports" + File.separator + "subreports" + File.separator;
            //String direccion = path + "\\reports\\subreports\\";
            System.out.println(direccion);
            parametros.put("SUBREPORT_DIR", direccion);
            parametros.put("p_periodo", periodo);
            parametros.put("p_id_venta", id_venta);
            parametros.put("p_letras_numero", letras_numeros);
            parametros.put("p_codigo_qr", url_codigo_qr);
            parametros.put("p_hash", hash);
            c_varios.imp_reporte("rpt_dos_documentos", parametros);
            //c_varios.ver_reporte("rpt_dos_documentos", parametros);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

    }

    public void imprimir_nota(int tipo) {
        letras_numeros = c_letras.Convertir(total + "", true) + " SOLES";

        File miDir = new File(".");
        try {
            Map<String, Object> parametros = new HashMap<>();
            String path = miDir.getCanonicalPath();
            String direccion = path + File.separator + "reports" + File.separator + "subreports" + File.separator;
            //String direccion = path + "\\reports\\subreports\\";
            System.out.println(direccion);
            parametros.put("SUBREPORT_DIR", direccion);
            parametros.put("p_periodo", periodo);
            parametros.put("p_id_venta", id_venta);
            parametros.put("p_letras_numero", letras_numeros);
            if (tipo == 1) {
                c_varios.imp_reporte("rpt_dos_documentos_nota", parametros);
            } else {
                c_varios.ver_reporte("rpt_dos_documentos_nota", parametros);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }
    }

    @Override
    public void run() {
        imprimir_nota(1);
    }
}
