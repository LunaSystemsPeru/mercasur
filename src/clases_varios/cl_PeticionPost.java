/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
hola mundo
 */
package clases_varios;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author luis-d
 */
public class cl_PeticionPost {

    private static final String USER_AGENT = "Mozilla/5.0";

    public void excutePost(String mensaje, String numero1) {
        try {
            // open a connection to the site
            URL url = new URL("http://lunasystemsperu.com/consultas_json/composer/sms_mercasur.php");
            URLConnection con = url.openConnection();
            // activate the output
            con.setDoOutput(true);
            PrintStream ps = new PrintStream(con.getOutputStream());
            // send your parameters to your site
            ps.print("mensaje=" + mensaje);
            ps.print("&numero1=" + numero1);

            // we have to get the input stream in order to actually send the request
            con.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }

            // close the print stream
            ps.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] enviar_documento(String id_venta, String periodo, int tipo_documento) {
        String SERVER_PATH = "http://lunasystemsperu.com/clientes/mercasur/intranet/greenter/generates/";
        String url = null;
        String[] datos = new String[6];
        // open a connection to the site
        if (tipo_documento == 3) {
            url = SERVER_PATH + "boleta.php?id_venta=" + id_venta + "&periodo=" + periodo;
        }
        if (tipo_documento == 4) {
            url = SERVER_PATH + "factura.php?id_venta=" + id_venta + "&periodo=" + periodo;
        }
        StringBuffer response = null;

        try {
            //Creamos un nuevo objeto URL con la url donde pedir el JSON
            URL obj = new URL(url);
            //Creamos un objeto de conexión
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            //Añadimos la cabecera
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            // Enviamos la petición por POST
            con.setDoOutput(true);
            //Capturamos la respuesta del servidor
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            //if (responseCode != 200) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            //Mostramos la respuesta del servidor por consola
            System.out.println("Respuesta del servidor: " + response);
            //cerramos la conexión
            in.close();
            // }

            System.out.println("INFORMACIÓN OBTENIDA DE LA BASE DE DATOS:");

            JSONParser Jparser = new JSONParser();
            JSONObject jsonObject = (JSONObject) Jparser.parse(response.toString());
            boolean estatus = (Boolean) jsonObject.get("success");
            //https://examples.javacodegeeks.com/core-java/json/java-json-parser-example/
            JSONObject result = (JSONObject) jsonObject.get("resultado");

            datos[0] = result.get("nombre_archivo").toString();
            datos[1] = result.get("direccion_xml").toString();
            datos[2] = result.get("direccion_qr").toString();
            datos[3] = result.get("hash").toString();
            datos[4] = result.get("descripcion_cdr").toString();
            if (estatus) {
                datos[5] = "aceptado";
            } else {
                datos[5] = "error";
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return datos;
    }
}
