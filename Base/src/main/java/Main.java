import java.lang.String;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {

        Connection connection = DatabaseConnect.getConnection();
        if (connection != null) {
            new DropTables(connection).execute();
            new Districts(connection).execute();
            new Cities(connection).execute();
        }
    }
}
