package biorimp.storage.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionJDBC {

    //private final String URL = "jdbc:mysql://104.197.73.237:3306/biorimpdb";
    //private final String USUARIO = "biorimp";
    //private final String PASSWORD = "biorimp.2015";

    //private final String URL = "jdbc:mysql://localhost:3306/biorimpdb";
    //private final String USUARIO = "root";
    //private final String PASSWORD = "funcion17..";

    private static ConnectionJDBC instance;
    private static Connection conexion;
    private final String URL = "jdbc:mysql://localhost:3306/biorimpdb";
    private final String USUARIO = "biorimp";
    private final String PASSWORD = "biorimp2016";


    private ConnectionJDBC() {
    }

    public static ConnectionJDBC getInstance() throws Exception {
        if (instance == null) {
            instance = new ConnectionJDBC();
        }
        return instance;
    }

    public Connection obtenerConexion() throws Exception {
        if (conexion != null)
            return conexion;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new Exception("No se pudo inicializar Driver de BD");
        }

        try {
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
        } catch (SQLException e) {
            throw new Exception("No se ha podido establecer conexion con la base de datos");
        }

        return conexion;
    }

}