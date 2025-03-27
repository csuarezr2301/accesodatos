package org.example;

import java.sql.*;

//FALTA CONENEXION CON LA BASE DE DATOS  METODO CONEXION

public class ejercicio3 {
    public static void main(String[] args) {
        Connection con = Conexion.getConexion();

        if (con != null) {
            servicio.numjornadamay();

        } else {
            System.out.println("ERROR al realizar la conexión");

        }
    }
}











 /*
        try {
            Statement st = con.createStatement();
            ResultSet r = st.executeQuery("SELECT max(numjornada) FROM partidos");
            r.next();
            int numMax = r.getInt(1);

            //llamar al metodo siguientejornada(int numjar)
            String sql = "{call siguientejornada(?)}";
            CallableStatement callSt = con.prepareCall(sql);
            callSt.executeUpdate();

            System.out.println("se ha generado la jornada" + (numMax+1));

            PreparedStatement prst = con.prepareStatement("SELECT log.nombre,vis.nombre FROM partidos " +
                    "INNER JOIN equipos AS loc ON loc.codeg=equals" +
                    "INNER JOIN equipos AS vis ON vis.codeg=equals WHERE numjornada=?");
            prst.setInt(1, numMax = 1);
            r = prst.executeQuery();
            while (r.next()) {
                System.out.println(r.getString(1) + "-" + r.getString(2));
            }

            st.close();
            callSt.close();
            prst.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
*/
