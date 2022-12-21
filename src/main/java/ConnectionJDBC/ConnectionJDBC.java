package ConnectionJDBC;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionJDBC {
    public Connection connection;
    public void TestNomDriver(String NomDriver) throws ClassNotFoundException {
        try{
            Class.forName(NomDriver);
        }
        catch(ClassNotFoundException CNFE){
            CNFE.printStackTrace();
            throw new ClassNotFoundException();
        }
    }

    public ConnectionJDBC(String nomDuDriverJDBC, String url, String login, String mdp) throws SQLException, ClassNotFoundException{
        try{
            TestNomDriver(nomDuDriverJDBC);
            connection = DriverManager.getConnection(url, login, mdp);
        }
        catch(SQLException SQLE){
            SQLE.printStackTrace();
            throw new SQLException(SQLE);
        }
        catch (ClassNotFoundException CNFE){
            CNFE.printStackTrace();
            throw new ClassNotFoundException();
        }
    }

}
