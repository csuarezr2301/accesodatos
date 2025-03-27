package org.example;

import javax.swing.plaf.nimbus.State;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.PreparedStatement;

public class Main {
    public static void main(String[] args) {
        Scanner tec = new Scanner(System.in);// Crear un objeto Scanner para leer la entrada del usuario
        Conexion con = new Conexion();
        PreparedStatement st = null;
        ResultSet rs = null;

        System.out.println("categorias: CIE-Ciencias, GEO-Geografía, HIS-Historia, SER-Series TV, MUS-Música, DEP-Deportes.");


        if (con != null) {
            Servicios.textopreguntas();

        } else {
            System.out.println("ERROR al realizar la conexión");

        }
    }
}
