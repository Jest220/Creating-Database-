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
            statement.executeUpdate("drop table if exists base.Settlement");
            statement.executeUpdate("drop table if exists base.TypeSettlement");

            statement.executeUpdate("drop table if exists base.District");
            statement.executeUpdate("drop table if exists base.TypeDist");
            statement.executeUpdate("CREATE TABLE `base`.`TypeDist` (\n" +
                    "  `ID_Type_Dist` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `TypeDist` VARCHAR(45) NULL,\n" +
                    "  PRIMARY KEY (`ID_Type_Dist`));");
            statement.executeUpdate("CREATE TABLE `base`.`District` (\n" +
                    "  `ID_District` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `District` VARCHAR(45) NULL,\n" +
                    "  `ID_Type_Dist` INT,\n" +
                    "  CODE VARCHAR(45),\n" +
                    "  PRIMARY KEY (`ID_District`), \n" +
                    "  FOREIGN KEY (ID_Type_Dist) REFERENCES base.TypeDist(ID_Type_Dist));");
            statement.executeUpdate("CREATE TABLE `base`.`TypeSettlement` (\n" +
                    "  `ID_TypeSettlement` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `TypeSettlement` VARCHAR(45) NULL,\n" +
                    "  PRIMARY KEY (`ID_TypeSettlement`));");
            statement.executeUpdate("CREATE TABLE `base`.`Settlement` (\n" +
                    "  `ID_Settlement` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `Settlement` VARCHAR(45) NULL,\n" +
                    "  `ID_Type_Settlement` INT NOT NULL,\n" +
                    "  `ID_District` INT NOT NULL,\n" +
                    "  `CODE` VARCHAR(45) NULL,\n" +
                    "  PRIMARY KEY (`ID_Settlement`),\n" +
                    "  FOREIGN KEY (ID_Type_Settlement) REFERENCES base.TypeSettlement(ID_TypeSettlement),\n" +
                    "  FOREIGN KEY (ID_District) REFERENCES base.District(ID_District));");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
