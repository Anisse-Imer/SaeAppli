package ConnectionJDBC;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionA {
    public Connection connection;
    public void TestNomDriver(String NomDriver) throws ClassNotFoundException {
        try{
            Class.forName(NomDriver);
            System.out.println("Bon Driver :");
        }
        catch(ClassNotFoundException IPE){
            IPE.printStackTrace();
            throw new ClassNotFoundException();
        }
    }

    private ConnectionA(String nomDuDriverJDBC, String url, String login, String mdp) throws SQLException, ClassNotFoundException{
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
