package ConnectionJDBC;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Singleton de connexion.
public class ConnectionJDBC {
    private Connection connection;

    private static final ConnectionJDBC JDBC = new ConnectionJDBC("com.mysql.cj.jdbc.Driver"
                                                , "jdbc:mysql://127.0.0.1:3306/IUT"
                                                , "root"
                                                , "an56im18");

    public void TestNomDriver(String NomDriver) throws ClassNotFoundException {
        try{
            Class.forName(NomDriver);
        }
        catch(ClassNotFoundException CNFE){
            CNFE.printStackTrace();
            throw new ClassNotFoundException();
        }
    }

    private ConnectionJDBC(String nomDuDriverJDBC, String url, String login, String mdp){
        try{
            TestNomDriver(nomDuDriverJDBC);
            connection = DriverManager.getConnection(url, login, mdp);
        }
        catch(SQLException SQLE){
            SQLE.printStackTrace();
        }
        catch (ClassNotFoundException CNFE){
            CNFE.printStackTrace();
        }
    }

    public static ConnectionJDBC getInstance(){
        return JDBC;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }
}
