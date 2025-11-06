import java.sql.*;

public class JDBCDemo{
    public static void main(String[] args) throws Exception {
        /*
            import package
            load and register
            create connection
            execute connection
            process the results
            close
        */

        String url = "jdbc:postgresql://localhost:5433/Demo";
        String user = "postgres";
        String password = "1234";

        String sql = "SELECT sname FROM public.\"Student\" where sid = 1 ";

        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(url,user,password);
        System.out.println("Connection Established !!!");

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        System.out.println(rs.next());

        con.close();

        System.out.println("Connection Closed");

    }
}