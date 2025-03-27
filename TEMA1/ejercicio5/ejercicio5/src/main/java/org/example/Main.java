package org.example;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.example.Conexion.close_conexion;

public class Main {
    public static void main(String[] args) throws IOException {
        File fich = new File("./datos_coches.sql");
        String script = null;
        BufferedReader bf = new BufferedReader(new FileReader(fich));

        //Procesar el fichero para crear una cadena con el script
        String linea = bf.readLine();
        StringBuilder stringBuilder = new StringBuilder();
        while (linea != null) {
            stringBuilder.append(linea);
            linea = bf.readLine();
        }

        bf.close();
        script = stringBuilder.toString();


    //Ya tenemos la cadena con el script, si todo ha ido bien

        if(script!=null){
        //En la URL de conexión se pide, en un parámetro, la ejecución de scripts
        Connection con=Conexion.getConexion();
        try {
            con.setAutoCommit(false); //Establecemos el estado transaccional Para que quede ejecutado todo el script o bien se anule todo lo que se hubiera ejecutado si hay algún fallo

            Statement st=con.createStatement();//Ejecutar el script
            st.executeUpdate(script);

            //confirmar la transancción
            con.commit();
            System.out.println("Se ha realizado, terminado la ejecución del script");
            System.out.println("se ha creado la base de datos");

            st.close();
            close_conexion();
        } catch (SQLException ex) {
            try {
                // Algo ha fallado en la ejecución del script, se anula la transacción
                con.rollback();
                System.out.println("ERROR "+ex.getErrorCode()+":"+ex.getMessage());

            } catch (SQLException ex2) {
                System.out.println("ERROR "+ex.getErrorCode()+":"+ex.getMessage());
            }
        }
        try {
            con.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
            }

        }
        else{
        System.out.println("Error al leer el script");
        }

    }
}