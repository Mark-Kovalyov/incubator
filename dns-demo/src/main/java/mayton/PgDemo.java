package mayton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PgDemo {

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "scott", "tiger");
        Statement st=connection.createStatement();
        st.execute("create table test(id int)");
        connection.close();
    }

}
