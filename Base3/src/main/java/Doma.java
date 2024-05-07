import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class Doma {
    private Connection connection;

    public Doma(Connection connection) {
        this.connection = connection;
    }

    public void execute(){
        int countOfDomaId = 1;
        try (Statement statement1 = connection.createStatement();){
            ResultSet resultSet1 = statement1.executeQuery("select NAME, CODE from kladr.table_doma_sql where CODE like '24%'");
            while (resultSet1.next()) {
                Statement statement2 = connection.createStatement();
                ResultSet resultSet2 = statement2.executeQuery("SELECT count(ID_Street) FROM base.Street where CODE='" +
                        resultSet1.getString(2).substring(0, 15) + "00';");
                resultSet2.next();
                if (resultSet2.getInt(1) != 0) {
                    resultSet2 = statement2.executeQuery("SELECT ID_Street FROM base.Street where CODE='" +
                            resultSet1.getString(2).substring(0, 15) + "00';");
                    resultSet2.next();
                    String House = resultSet1.getString(1);
                    House = House.replace(String.valueOf("\\"), String.valueOf("\\\\"));
                    int idStreet = resultSet2.getInt(1);
                    String code = resultSet1.getString(2);
                    statement2.executeUpdate("insert into base.Doma(House, ID_Street, CODE) values (" +
                            "'" + House + "'," +
                            " " + idStreet + "," +
                            " '" + code + "');");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
