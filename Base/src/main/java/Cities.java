import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class Cities {
    private Connection connection;

    public Cities(Connection connection) {
        this.connection = connection;
    }

    public void execute(){
        HashMap<String, Integer> CityTypeToId = new HashMap<>();
        int countOfCitiesId = 1;
        try (Statement statement1 = connection.createStatement();){
            ResultSet resultSet1 = statement1.executeQuery("select CODE, ID_District from base.district where CODE like '24%00000000'");
            while (resultSet1.next()) {
                Statement statement2 = connection.createStatement();
                ResultSet resultSet2 = statement2.executeQuery("select NAME, SOCR, CODE from kladr.table_kladr_sql where " +
                        "CODE like '"+ resultSet1.getString(1).substring(0, 5) + "%00'");
                while (resultSet2.next()) {
                    if (!resultSet2.getString(3).equals(resultSet1.getString(1))) {
                        Statement statement3 = connection.createStatement();

                        String city = resultSet2.getString(2);
                        if (!CityTypeToId.containsKey(city)) {
                            CityTypeToId.put(city, countOfCitiesId);
                            statement3.executeUpdate("insert into base.TypeSettlement(TypeSettlement) values('" + city + "')");
                            countOfCitiesId++;
                        }

                        statement3.executeUpdate("insert into base.Settlement(Settlement, ID_Type_Settlement, " +
                                "ID_District, CODE) " +
                                "values ('" + resultSet2.getString(1) + "', "+
                                CityTypeToId.get(resultSet2.getString(2)) + ", " +
                                resultSet1.getString(2) + ", '" +
                                resultSet2.getString(3) + "')");
                    }
                }
            }

            resultSet1 = statement1.executeQuery("select CODE, ID_District from base.district where CODE like '24000%00000'");
            while (resultSet1.next()) {
                Statement statement2 = connection.createStatement();
                ResultSet resultSet2 = statement2.executeQuery("select NAME, SOCR, CODE from kladr.table_kladr_sql where " +
                        "CODE like '"+ resultSet1.getString(1).substring(0, 8) + "%00'");
                while (resultSet2.next()) {
                    Statement statement3 = connection.createStatement();

                    String city = resultSet2.getString(2);
                    if (!CityTypeToId.containsKey(city)) {
                        CityTypeToId.put(city, countOfCitiesId);
                        statement3.executeUpdate("insert into base.TypeSettlement(TypeSettlement) values('" + city + "')");
                        countOfCitiesId++;
                    }

                    statement3.executeUpdate("insert into base.Settlement(Settlement, ID_Type_Settlement, " +
                            "ID_District, CODE) " +
                            "values ('" + resultSet2.getString(1) + "', "+
                            CityTypeToId.get(resultSet2.getString(2)) + ", " +
                            resultSet1.getString(2) + ", '" +
                            resultSet2.getString(3) + "')");
                    /*if (!resultSet2.getString(3).equals(resultSet1.getString(1))) {
                        Statement statement3 = connection.createStatement();

                        String city = resultSet2.getString(2);
                        if (!CityTypeToId.containsKey(city)) {
                            CityTypeToId.put(city, countOfCitiesId);
                            statement3.executeUpdate("insert into base.TypeSettlement(TypeSettlement) values('" + city + "')");
                            countOfCitiesId++;
                        }

                        statement3.executeUpdate("insert into base.Settlement(Settlement, ID_Type_Settlement, " +
                                "ID_District, CODE) " +
                                "values ('" + resultSet2.getString(1) + "', "+
                                CityTypeToId.get(resultSet2.getString(2)) + ", " +
                                resultSet1.getString(2) + ", '" +
                                resultSet2.getString(3) + "')");
                    }*/
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
