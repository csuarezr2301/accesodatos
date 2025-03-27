package org.example;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;



public class Main {
    public static void main(String[] args) {
        List<String> lineas = null;
        String script="";
        try{
            script = Files.readString(Paths.get("datos_coches.sql"));
            lineas = Files.readAllLines(Paths.get("datos_coches.sql"),
                    StandardCharsets.UTF_8);
        }catch (IOException e){
            System.err.println("error de lectura en fichero");
        }
    if(lineas!=null){
        //ya tenemos la cadena con el script, si todo va bien
        for(String linea : lineas){
            script=linea;
        }
        Connection con = Conexion.getConexion();
    }
        try {
            //establecemos el estado transaccional para que quede ejecutado todo el scrpti o bien anule todo lo que se hubiera ejecutado si hay algun fallo
            con.setAutoComit(false);
            Statement st = createStatement();
            st.executeUpdate(script);
            con.comit();
            System.out.println("se ha terminado al ejecucion del script");
            System.out.println("se ha creado la base de datos datoscoches")
            st.close();
            con.close();
        }catch (SQLException ex){
            try{
                //algo ha falladoo en la ejecucion del script, se anula la transiccion
                con.rollBack();
                System.out.println("ERROR"+ex.getErrorCode()+""+ex.getMessage());

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}