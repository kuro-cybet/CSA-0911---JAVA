package Task07;
import java.sql.*;
/** Requires MySQL test database; configure DB_URL, DB_USER and DB_PASSWORD environment variables. */
public class LoginSecurityDemo {
    static Connection connect()throws SQLException{
        return DriverManager.getConnection(System.getenv("DB_URL"),System.getenv("DB_USER"),System.getenv("DB_PASSWORD"));
    }
    public static boolean unsafeLogin(Connection c,String id,String pwd)throws SQLException{
        String sql="SELECT COUNT(*) FROM users WHERE id='"+id+"' AND pwd='"+pwd+"'";
        try(Statement st=c.createStatement();ResultSet rs=st.executeQuery(sql)){rs.next();return rs.getInt(1)>0;}
    }
    public static boolean safeLogin(Connection c,String id,String pwd)throws SQLException{
        String sql="SELECT COUNT(*) FROM users WHERE id=? AND pwd=?";
        try(PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1,id);ps.setString(2,pwd);
            try(ResultSet rs=ps.executeQuery()){rs.next();return rs.getInt(1)>0;}
        }
    }
    public static void main(String[] args)throws Exception{
        try(Connection c=connect()){
            try(Statement s=c.createStatement()){
                s.executeUpdate("CREATE TABLE IF NOT EXISTS users(id VARCHAR(80) PRIMARY KEY,pwd VARCHAR(100) NOT NULL)");
                s.executeUpdate("DELETE FROM users");
                s.executeUpdate("INSERT INTO users(id,pwd) VALUES('student1','correct-pass')");
            }
            String attack="' OR '1'='1' -- ";
            System.out.println("Unsafe accepts crafted input: "+unsafeLogin(c,attack,"x"));
            System.out.println("PreparedStatement accepts crafted input: "+safeLogin(c,attack,"x"));
        }
    }
}
