import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {

    private static final String URL ="jdbc:postgresql://ec2-52-31-2-97.eu-west-1.compute.amazonaws.com:5432/d9h2c7ba3ctpql";
    private static final String USERNAME = "pxixxzwfnjyabx";
    private static final String PASSWORD = "cdf738c76afc5311a3507476e73ff571ab1e56de53f4745cd5e63fcf86895154";


    public static void main(String[] args) {
        try {

            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();

            if (connection != null) {
                System.out.println("Povezava s podatkovno bazo uspešno vzpostavljena.");

                DatabaseConnection.closeConnection(connection);
            } else {
                System.out.println("Povezava s podatkovno bazo ni bila uspešno vzpostavljena.");
            }
        } catch (SQLException e) {
            System.out.println("Napaka pri vzpostavljanju povezave s podatkovno bazo: " + e.getMessage());
        }
    }


    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }


    public static void closeConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }


    public CallableStatement prepareCall(String sql, Connection connection) throws SQLException {

        if (connection != null) {
            return connection.prepareCall(sql);
        } else {
            throw new SQLException("Connection is not initialized.");
        }
    }


}
