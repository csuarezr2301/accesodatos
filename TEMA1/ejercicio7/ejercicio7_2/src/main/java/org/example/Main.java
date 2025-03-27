package org.example;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner tec = new Scanner(System.in);// Crear un objeto Scanner para leer la entrada del usuario
        Conexion con = new Conexion();
        PreparedStatement st = null;
        ResultSet rs = null;

        if (con != null) {
            Servicios.pediruserycontrasena();

        } else {
            System.out.println("ERROR al realizar la conexión");

        }
    }
}

