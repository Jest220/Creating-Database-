import com.mysql.cj.protocol.Resultset;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class Streets {
    private Connection connection;

    public Streets(Connection connection) {
        this.connection = connection;
    }

    public void execute(){
        HashMap<String, Integer> streetTypeToId = new HashMap<>();
        int countOfStreetId = 1;
        try (Statement statement1 = connection.createStatement();){
            ResultSet resultSet1 = statement1.executeQuery("SELECT NAME, SOCR, CODE FROM kladr.table_street_sql where CODE like '24___000000%00';");
            while (resultSet1.next()) {
                Statement statement2 = connection.createStatement();
                //запись типов улиц
                String street = resultSet1.getString(2);
                if(!streetTypeToId.containsKey(street)) {
                    streetTypeToId.put(street, countOfStreetId);
                    statement2.executeUpdate("insert into base.TypeStreet(TypeStreet) values ('" + street + "')");
                    countOfStreetId++;
                }

                //запись улиц
                String streetName = resultSet1.getString(1);
                int streetTypeID = streetTypeToId.get(street);
                ResultSet resultSet2 = statement2.executeQuery("select ID_District from base.District where CODE='" + resultSet1.getString(3).substring(0, 11) + "00'");
                resultSet2.next();
                String districtID = resultSet2.getString(1);
                String code = resultSet1.getString(3);

                statement2.executeUpdate("insert into base.Street(Street, ID_TypeStreet, ID_District, CODE) values " +
                        "('" + streetName + "'," +
                        " " + streetTypeID + "," +
                        " " + districtID + "," +
                        " '" + code + "')");
            }
            resultSet1 = statement1.executeQuery("SELECT NAME, SOCR, CODE FROM kladr.table_street_sql where NOT CODE like '24___000000%00' and CODE like '24%00';");
            while (resultSet1.next()) {
                Statement statement2 = connection.createStatement();

                //принадлежит ли какому нибудь субъекту
                ResultSet resultSet2 = statement2.executeQuery("SELECT count(ID_Settlement) from base.Settlement where CODE='" +
                        resultSet1.getString(3).substring(0, 11) + "00'");
                resultSet2.next();
                if (resultSet2.getInt(1) != 0) {
                    //запись типов улиц
                    String street = resultSet1.getString(2);
                    if(!streetTypeToId.containsKey(street)) {
                        streetTypeToId.put(street, countOfStreetId);
                        statement2.executeUpdate("insert into base.TypeStreet(TypeStreet) values ('" + street + "')");
                        countOfStreetId++;
                    }
                    //запись улиц
                    String streetName = resultSet1.getString(1);
                    int streetTypeID = streetTypeToId.get(street);
                    resultSet2 = statement2.executeQuery("SELECT ID_Settlement, ID_District from base.Settlement where CODE='" +
                            resultSet1.getString(3).substring(0, 11) + "00'");
                    resultSet2.next();
                    String settlementID = resultSet2.getString(1);
                    String districtID = resultSet2.getString(2);
                    String code = resultSet1.getString(3);
                    statement2.executeUpdate("insert into base.Street(Street, ID_TypeStreet, ID_Settlement, ID_District, CODE) values " +
                            "('" + streetName + "'," +
                            " " + streetTypeID + "," +
                            " " + settlementID + "," +
                            " " + districtID + "," +
                            " '" + code + "')");
                } else {
                    System.out.println(resultSet1.getString(3).substring(0, 11));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
