import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DropTables {
    Connection connection;
    public DropTables(Connection connection) {
        this.connection = connection;
    }

    public void execute() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("drop table if exists base.Doma");

            statement.executeUpdate("CREATE TABLE `base`.`Doma` (\n" +
                    "  `ID_House` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `House` VARCHAR(45) NULL,\n" +
                    "  `ID_Street` INT NOT NULL,\n" +
                    "  `CODE` VARCHAR(45) NULL,\n" +
                    "  PRIMARY KEY (`ID_House`),\n" +
                    "  FOREIGN KEY (ID_Street) REFERENCES base.Street(ID_Street));");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
