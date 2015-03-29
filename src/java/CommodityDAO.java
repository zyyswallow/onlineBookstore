


import java.sql.*;
import java.io.*;
import java.util.*;

public class CommodityDAO {

    private String url;

    public CommodityDAO() {
        try {
            String driverName = "org.apache.derby.jdbc.ClientDriver";
            String userName = "IS2560";
            String userPasswd = "IS2560";
            String dbName = "IS2560";
            url = "jdbc:derby://localhost:1527/" + dbName
                    + ";" + "user="
                    + userName + ";password=" + userPasswd;
            System.out.println("url=" + url);
            Class.forName(driverName).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//show the items in cart
    public ResultSet findCart(String user) throws Exception {
        ResultSet rst = null;
        Statement stmt = null;
        String sqlSelect = "SELECT  O.pno, P.pname, P.pprice"
                + " FROM orderlist O,product P where O.paid=false and O.uno='" + user + "'and O.pno=P.pno order by pno "; //��ʾδ�������Ʒ
        System.out.println("___________sql=" + sqlSelect);
        Connection conn = DriverManager.getConnection(url);
        if (conn == null) {
            System.out.println("conn==null");
            return null;
        }
        // if connection succeeds, execute the query
        stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        if (stmt == null) {
            System.out.println("stmt==null");
            return null;
        }
        rst = stmt.executeQuery(sqlSelect);
        return rst;
    }

    //show the order history
    public ResultSet findOrder(String user) throws Exception {
        ResultSet rst = null;
        Statement stmt = null;
        String sqlSelect = "SELECT  O.pno, P.pname, P.pprice"
                + " FROM orderlist O,product P where O.paid=true and O.uno='" + user + "' and O.pno=P.pno order by pno ";
        System.out.println("___________sql=" + sqlSelect);
        Connection conn = DriverManager.getConnection(url);
        if (conn == null) {
            System.out.println("conn==null");
            return null;
        }
        //if connection succeeds, execute the query
        stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        if (stmt == null) {
            System.out.println("stmt==null");
            return null;
        }
        rst = stmt.executeQuery(sqlSelect);
        return rst;
    }

    //add products into cart
    public void buy(String user, String pno) throws Exception {
        PreparedStatement stmt = null;
        System.out.println(user + pno);
        String sql = "insert into orderlist values(?,?,?)";
        Connection conn = DriverManager.getConnection(url);
        if (conn == null) {
            System.out.println("conn==null");
            return;
        }
        System.out.println("___________sql=" + sql);
        stmt = conn.prepareStatement(sql);
        if (stmt == null) {
            System.out.println("stmt==null");
            return;
        }
        stmt.setString(1, user);
        stmt.setString(2, pno);
        stmt.setBoolean(3, false);
        stmt.executeUpdate();
        conn.close();
    }

//check out, put itmes in the order history
    public void pay(String user) throws Exception {
        PreparedStatement stmt = null;
        String sql = "update orderlist set paid=? where uno=?";
        Connection conn = DriverManager.getConnection(url);
        if (conn == null) {
            System.out.println("conn==null");
            return;
        }
        System.out.println("___________sql=" + sql);
        stmt = conn.prepareStatement(sql);
        if (stmt == null) {
            System.out.println("stmt==null");
            return;
        }
        // use "(boolean)paid" attribute to distinguish items in cart or purchased items
        stmt.setBoolean(1, true);
        stmt.setString(2, user);
        stmt.executeUpdate();
        conn.close();
    }

    // get all the book info
    public ResultSet getAllBooks() throws Exception {
        ResultSet rst = null;
        String sqlSelect = "SELECT * FROM book order by bid";

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

    // get book by id
    public ResultSet getBookById(String bid) throws Exception {
        ResultSet rst = null;
        String sqlSelect = "SELECT * FROM book WHERE bid='" + bid + "'";

        Connection conn = DriverManager.getConnection(url);

        if (conn == null) {
            System.out.println("conn==null");
            return null;
        }

        System.out.println("___________sql=" + sqlSelect);
        PreparedStatement stmt = conn.prepareStatement(sqlSelect);
        if (stmt == null) {
            System.out.println("stmt==null");
            return null;
        }
        rst = stmt.executeQuery();
        System.out.println("rst="+rst);
        return rst;
    }

}
