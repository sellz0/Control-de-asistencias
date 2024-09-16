package cl.ipst.sgi.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

/**
 *
 * @author rober
 */
public class ConexionBBDD {

    private Connection conn;
    String url = "jdbc:mysql://localhost/ipst_idc_tienda";
    String username = "root";
    String password = "";

    private boolean registrar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private boolean obtenerConexion() {
        try {
            conn = DriverManager.getConnection(url, username, password);
            return true;
        } catch (SQLException e) {
            System.out.println("Error al conectar a la BBDD: " + e.getMessage());
            return false;
        }
    }

    public Statement crearSentencia() {
        try {
            if (conn != null && !conn.isClosed()) {
                return conn.createStatement();
            }
        } catch (SQLException e) {
            System.out.println("Error al crear la sentencia: " + e.getMessage());
        }
        return null;
    }

    public PreparedStatement prepararSentencia(String query) {
        try {
            if (conn != null && !conn.isClosed()) {
                return conn.prepareStatement(query);
            }
        } catch (SQLException e) {
            System.out.println("Error al preparar la sentencia: " + e.getMessage());
        }
        return null;
    }

    public ConexionBBDD() {
        registrar();
        if (!obtenerConexion()) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos.", "ERROR", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public String Desconectar() {
        try {
            if (conn != null) {
                conn.close();
                return "Desconectado";
            } else {
                return "No existe una conexion activa";
            }
        } catch (SQLException e) {
            return "Error al desconectar";
        }
    }

    public boolean probarConexion() {
        registrar();
        if (obtenerConexion()) {
            JOptionPane.showMessageDialog(null, "Base de datos conectada exitosamente.", "EXITO", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos.", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
