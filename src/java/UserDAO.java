
import java.sql.*;
import java.io.*;
import java.util.*;

public class UserDAO {

    private String url;

    public UserDAO() {
        try {
            String driverName = "org.apache.derby.jdbc.ClientDriver";
            String userName = "TEST";
            String userPasswd = "TEST";
            String dbName = "onlineBookStore";
            url = "jdbc:derby://localhost:1527/" + dbName
                    + ";" + "user="
                    + userName + ";password=" + userPasswd;
            System.out.println("url=" + url);
            Class.forName(driverName).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //find users by username
    public ResultSet findBy(String id) throws Exception {
        ResultSet rst = null;
        String sqlSelect = "SELECT  uno,upwd,uemail,udate"
                + " FROM userlist where uno='" + id + "' order by udate desc";

        Connection conn = DriverManager.getConnection(url);

        if (conn == null) {
            System.out.println("conn==null");
            return null;
        }

        System.out.println("___________sql=" + sqlSelect);
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        if (stmt == null) {
            System.out.println("stmt==null");
            return null;
        }
        rst = stmt.executeQuery(sqlSelect);
        return rst;
    }

    //append new user into the database
    public boolean append(User user) {
        String userId = user.id;
        String userPwd = user.pwd;
        String userAddr = user.email;

        PreparedStatement stmt = null;

        String sql = "insert into userlist(uno,upwd,uemail,udate) values (?,?,?,?)";
        try {
            Connection conn = DriverManager.getConnection(url);
            if (conn == null) {
                System.out.println("conn==null");
                return false;
            }
            System.out.println("___________sql=" + sql);
            stmt = conn.prepareStatement(sql);
            if (stmt == null) {
                System.out.println("stmt==null");
                return false;
            }
            stmt.setString(1, userId);
            stmt.setString(2, userPwd);
            stmt.setString(3, userAddr);

            // get the date of registration
            java.util.Date now = new java.util.Date();
            java.sql.Timestamp sqldate = new java.sql.Timestamp(now.getTime());

            stmt.setTimestamp(4, sqldate);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
