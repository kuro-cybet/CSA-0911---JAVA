package Task08;
import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.util.*;

public class DatabaseMigration {
    static Connection connect()throws SQLException{
        return DriverManager.getConnection(System.getenv("DB_URL"),System.getenv("DB_USER"),System.getenv("DB_PASSWORD"));
    }
    public static void main(String[] args)throws Exception{
        Path file=Path.of(args.length>0?args[0]:"bus_pass.csv");
        List<String[]> valid=new ArrayList<>();List<String> failed=new ArrayList<>();
        try(BufferedReader r=Files.newBufferedReader(file)){
            String line;int row=0;
            while((line=r.readLine())!=null){row++;String[] f=line.split(",",-1);
                try{
                    if(f.length!=5)throw new IllegalArgumentException("Expected 5 fields");
                    Integer.parseInt(f[3]);double amount=Double.parseDouble(f[4]);
                    if(amount<0)throw new IllegalArgumentException("Negative amount");
                    valid.add(f);
                }catch(RuntimeException ex){failed.add("Line "+row+": "+ex.getMessage()+" | "+line);}
            }
        }
        try(Connection c=connect()){
            int inserted=PassLoader.loadAll(c,valid);
            System.out.println("Inserted: "+inserted);
        }
        Files.write(Path.of("migration_failures.txt"),failed);
        System.out.println("Rejected records: "+failed.size()+" (migration_failures.txt)");
        try(Connection c=connect()){
            DatabaseMetaData md=c.getMetaData();
            System.out.println("Database: "+md.getDatabaseProductName()+" "+md.getDatabaseProductVersion());
            System.out.println("Driver: "+md.getDriverName()+" "+md.getDriverVersion());
            try(ResultSet rs=md.getTables(c.getCatalog(),null,"%",new String[]{"TABLE"})){
                while(rs.next())System.out.println("Table: "+rs.getString("TABLE_NAME"));
            }
        }
    }
}
