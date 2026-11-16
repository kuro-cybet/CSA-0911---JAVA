package Task03;
import java.sql.*;
import java.util.*;

public class StudentDao implements Dao<StudentDao.Student,String> {
    public record Student(String regNo,String name,String email) {}
    private final Connection connection;
    public StudentDao(Connection connection){this.connection=connection;}
    public int insert(Student s){
        String sql="INSERT INTO student(reg_no,name,email) VALUES(?,?,?)";
        try(PreparedStatement ps=connection.prepareStatement(sql)){
            ps.setString(1,s.regNo());ps.setString(2,s.name());ps.setString(3,s.email());return ps.executeUpdate();
        }catch(SQLException e){throw new IllegalStateException(e);}
    }
    public Optional<Student> findById(String id){
        try(PreparedStatement ps=connection.prepareStatement("SELECT reg_no,name,email FROM student WHERE reg_no=?")){
            ps.setString(1,id);try(ResultSet rs=ps.executeQuery()){
                return rs.next()?Optional.of(new Student(rs.getString("reg_no"),rs.getString("name"),rs.getString("email"))):Optional.empty();
            }
        }catch(SQLException e){throw new IllegalStateException(e);}
    }
    public List<Student> findAll(){
        List<Student> out=new ArrayList<>();
        try(Statement st=connection.createStatement();ResultSet rs=st.executeQuery("SELECT reg_no,name,email FROM student")){
            while(rs.next())out.add(new Student(rs.getString(1),rs.getString(2),rs.getString(3)));return out;
        }catch(SQLException e){throw new IllegalStateException(e);}
    }
    public int update(Student s){
        try(PreparedStatement ps=connection.prepareStatement("UPDATE student SET name=?,email=? WHERE reg_no=?")){
            ps.setString(1,s.name());ps.setString(2,s.email());ps.setString(3,s.regNo());return ps.executeUpdate();
        }catch(SQLException e){throw new IllegalStateException(e);}
    }
    public int delete(String id){
        try(PreparedStatement ps=connection.prepareStatement("DELETE FROM student WHERE reg_no=?")){
            ps.setString(1,id);return ps.executeUpdate();
        }catch(SQLException e){throw new IllegalStateException(e);}
    }
}
