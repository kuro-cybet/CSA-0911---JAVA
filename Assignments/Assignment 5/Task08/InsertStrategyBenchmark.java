package Task08;
import java.sql.*;
import java.util.*;

/** Compares Statement, PreparedStatement and batched PreparedStatement on generated rows. */
public class InsertStrategyBenchmark {
    static void clear(Connection c)throws SQLException{try(Statement s=c.createStatement()){s.executeUpdate("DELETE FROM bus_pass");}}
    static long statement(Connection c,List<String[]> rows)throws SQLException{
        clear(c);long start=System.nanoTime();
        try(Statement s=c.createStatement()){
            for(String[] r:rows){
                String sql="INSERT INTO bus_pass(pass_id,reg_no,name,route_no,amount) VALUES('"+
                    r[0]+"','"+r[1]+"','"+r[2]+"',"+Integer.parseInt(r[3])+","+Double.parseDouble(r[4])+")";
                s.executeUpdate(sql);
            }
        }return System.nanoTime()-start;
    }
    static long prepared(Connection c,List<String[]> rows)throws SQLException{
        clear(c);long start=System.nanoTime();
        try(PreparedStatement ps=c.prepareStatement("INSERT INTO bus_pass(pass_id,reg_no,name,route_no,amount) VALUES(?,?,?,?,?)")){
            for(String[] r:rows){bind(ps,r);ps.executeUpdate();}
        }return System.nanoTime()-start;
    }
    static long batch(Connection c,List<String[]> rows)throws SQLException{
        clear(c);long start=System.nanoTime();boolean old=c.getAutoCommit();c.setAutoCommit(false);
        try(PreparedStatement ps=c.prepareStatement("INSERT INTO bus_pass(pass_id,reg_no,name,route_no,amount) VALUES(?,?,?,?,?)")){
            int count=0;
            for(String[] r:rows){bind(ps,r);ps.addBatch();if(++count%1000==0)ps.executeBatch();}
            if(count%1000!=0)ps.executeBatch();c.commit();
        }catch(SQLException e){c.rollback();throw e;}finally{c.setAutoCommit(old);}
        return System.nanoTime()-start;
    }
    static void bind(PreparedStatement ps,String[] r)throws SQLException{
        ps.setString(1,r[0]);ps.setString(2,r[1]);ps.setString(3,r[2]);
        ps.setInt(4,Integer.parseInt(r[3]));ps.setDouble(5,Double.parseDouble(r[4]));
    }
    static List<String[]> generate(int n){
        List<String[]> rows=new ArrayList<>(n);
        for(int i=0;i<n;i++)rows.add(new String[]{"BENCH"+i,"REG"+i,"Student"+i,
            Integer.toString(i%20+1),"4500.00"});
        return rows;
    }
    public static void main(String[] args)throws Exception{
        int[] sizes={1000,10000,100000};
        try(Connection c=DriverManager.getConnection(System.getenv("DB_URL"),System.getenv("DB_USER"),System.getenv("DB_PASSWORD"))){
            for(int n:sizes){List<String[]> rows=generate(n);
                System.out.printf("%d rows | Statement %.2f ms | Prepared %.2f ms | Batch %.2f ms%n",n,
                    statement(c,rows)/1e6,prepared(c,rows)/1e6,batch(c,rows)/1e6);
            }
        }
    }
}
