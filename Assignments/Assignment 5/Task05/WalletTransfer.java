package Task05;
import java.sql.*;

public class WalletTransfer {
    public static void transfer(Connection c,int fromId,int toId,double amount)throws SQLException{
        if(amount<=0)throw new IllegalArgumentException("Amount must be positive");
        boolean old=c.getAutoCommit();c.setAutoCommit(false);
        try(PreparedStatement debit=c.prepareStatement("UPDATE wallet SET balance=balance-? WHERE wallet_id=? AND balance>=?");
            PreparedStatement credit=c.prepareStatement("UPDATE wallet SET balance=balance+? WHERE wallet_id=?")){
            debit.setDouble(1,amount);debit.setInt(2,fromId);debit.setDouble(3,amount);
            if(debit.executeUpdate()!=1)throw new SQLException("Insufficient funds or source wallet missing");
            credit.setDouble(1,amount);credit.setInt(2,toId);
            if(credit.executeUpdate()!=1)throw new SQLException("Destination wallet missing");
            c.commit();
        }catch(SQLException|RuntimeException e){c.rollback();throw e;}
        finally{c.setAutoCommit(old);}
    }
}
