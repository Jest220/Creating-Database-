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
            statement.executeUpdate("drop table if exists base.Street");
            statement.executeUpdate("drop table if exists base.TypeStreet");

            statement.executeUpdate("CREATE TABLE `base`.`TypeStreet` (\n" +
                    "  `ID_TypeStreet` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `TypeStreet` VARCHAR(45) NULL,\n" +
                    "  PRIMARY KEY (`ID_TypeStreet`));");
            statement.executeUpdate("CREATE TABLE `base`.`Street` (\n" +
                    "  `ID_Street` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `Street` VARCHAR(45) NULL,\n" +
                    "  `ID_TypeStreet` INT NOT NULL,\n" +
                    "  `ID_Settlement` INT,\n" +
                    "  `ID_District` INT NOT NULL,\n" +
                    "  `CODE` VARCHAR(45) NULL,\n" +
                    "  PRIMARY KEY (`ID_Street`),\n" +
                    "  FOREIGN KEY (ID_TypeStreet) REFERENCES base.TypeStreet(ID_TypeStreet),\n" +
                    "  FOREIGN KEY (ID_District) REFERENCES base.District(ID_District),\n" +
                    "  FOREIGN KEY (ID_Settlement) REFERENCES base.Settlement(ID_Settlement));");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
