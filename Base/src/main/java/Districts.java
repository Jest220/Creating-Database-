import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class Districts {
    private Connection connection;

    public Districts(Connection connection) {
        this.connection = connection;
    }

    public void execute(){
        HashMap<String, Integer> districtTypeToId = new HashMap<>();
        int countOfDistrictId = 1;
        try (Statement statement = connection.createStatement();){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM kladr.table_kladr_sql where CODE like '24%00000000';");
            while (resultSet.next()) {
                if (!resultSet.getString("CODE").equals("2400000000000")) {
                    Statement state = connection.createStatement();
                    String sql;

                    String district = resultSet.getString(2);
                    if (!districtTypeToId.containsKey(district)) {
                        districtTypeToId.put(resultSet.getString(2), countOfDistrictId);
                        sql = "insert into base.TypeDist(TypeDist) values('" + district + "')";
                        state.executeUpdate(sql);
                        countOfDistrictId++;
                    }
                    sql = "insert into base.District(District, ID_Type_Dist, CODE) " +
                            "values ('" + resultSet.getString(1) + "', " +
                            districtTypeToId.get(resultSet.getString(2)) + ", '" +
                            resultSet.getString("CODE") + "')";
                    state.executeUpdate(sql);
                }
            }
            resultSet = statement.executeQuery("SELECT * FROM kladr.table_kladr_sql where CODE like '24000%00000';");
            while (resultSet.next()) {
                if (!resultSet.getString("CODE").equals("2400000000000")) {
                    Statement state = connection.createStatement();
                    String sql;

                    String district = resultSet.getString(2);
                    if (!districtTypeToId.containsKey(district)) {
                        districtTypeToId.put(resultSet.getString(2), countOfDistrictId);
                        sql = "insert into base.TypeDist(TypeDist) values('" + district + "')";
                        state.executeUpdate(sql);
                        countOfDistrictId++;
                    }
                    sql = "insert into base.District(District, ID_Type_Dist, CODE) " +
                            "values ('" + resultSet.getString(1) + "', " +
                            districtTypeToId.get(resultSet.getString(2)) + ", '" +
                            resultSet.getString("CODE") + "')";
                    state.executeUpdate(sql);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}