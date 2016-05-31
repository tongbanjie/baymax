package jdbc.frame;

import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sidawei on 16/1/30.
 *
 * jdbc测试工具类
 */
public class Jdbc {

    @Autowired
    private DataSource dataSource;

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet resultSet;
    private int effectCount;

    public Jdbc(DataSource dataSource) throws SQLException {
        this.dataSource = dataSource;
        conn = dataSource.getConnection();
    }

    public interface DoInTransaction{
        void call() throws SQLException, InterruptedException;
    }

    public void doInTransaction(DoInTransaction call) throws SQLException {
        try {
            conn.setAutoCommit(false);
            call.call();
            conn.commit();
        }catch(Exception e){
            conn.rollback();
        }
    }

    public Jdbc executeUpdate(String s) throws SQLException {
        executeUpdate(s, null);
        return this;
    }

    public Jdbc executeUpdate(String sql, PrepareSetting setting) throws SQLException {
        stmt = conn.prepareStatement(sql);
        if (setting != null){
            setting.set(stmt);
        }
        effectCount = stmt.executeUpdate();
        return this;
    }

    public int getEffectCount(){
        return effectCount;
    }

    public Jdbc executeSelect(String sql, PrepareSetting setting) throws SQLException {
        conn = dataSource.getConnection();
        stmt = conn.prepareStatement(sql);
        if (setting != null){
            setting.set(stmt);
        }
        this.resultSet = stmt.executeQuery();

        return this;
    }

    public Jdbc executeSelect(String sql) throws SQLException {
        conn = dataSource.getConnection();
        stmt = conn.prepareStatement(sql);
        resultSet = stmt.executeQuery();
        return this;
    }

    public Jdbc close() throws SQLException {
        conn.setAutoCommit(true);
        stmt.close();
        conn.close();
        return this;
    }


    public interface PrepareSetting{
        void set(PreparedStatement statement) throws SQLException;
    }

    public Jdbc printSet() throws SQLException {
        System.out.println();
        System.out.println("------------------------------------------------------------");
        int count = resultSet.getMetaData().getColumnCount();
        for (int i = 1; i <= count; i++) {
            String leble = resultSet.getMetaData().getColumnLabel(i);
            System.out.print(leble);
            System.out.print(getSpan(leble));
        }
        System.out.println();
        while (resultSet.next()){
            for (int i = 1; i <= count; i++) {
                Object obj = resultSet.getObject(i);
                System.out.print(obj);
                System.out.print(getSpan(obj));
            }
            System.out.println();
        }
        return this;
    }

    private String getSpan(Object obj){
        int length = obj == null ? 4 : obj.toString().length();
        StringBuffer sb = new StringBuffer();
        for (;length < 15; length ++){
            sb.append(" ");
        }
        return sb.toString();
    }
}
