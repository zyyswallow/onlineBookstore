package chatRoom;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.regex.*;
import java.sql.*;

import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

/**
 *
 * @author yanyanzhou
 * @version
 */
public class checkMessage extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("checkMessages!");
        
        int amtread;
        JSONObject jo_in;
        String user = "";
        String dia = null;
        char[] cbuf = new char[200];
        StringBuffer invalue = new StringBuffer();
        StringBuffer dialog = new StringBuffer();
        
        BufferedReader in = request.getReader();
        while ((amtread = in.read(cbuf)) != -1) {
            invalue.append(cbuf, 0, amtread);
        }
        try {
            jo_in = new JSONObject(invalue.toString());
            user = jo_in.getString("user");
            dia = jo_in.getString("dialog");
            dialog.append(dia);
            
        } catch (Exception e) {
            System.out.println("JSON Error on input");
            e.printStackTrace();
        }
        //open the DBMS and insert the record that one user has load one message from others
        Statement st;
        Connection conn;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String connectionURL = "jdbc:derby://localhost:1527/onlineBookStore";
            conn = DriverManager.getConnection(connectionURL, "TEST", "TEST");
            st = conn.createStatement();

            // find the new messages that were not loaded before
            String q = new String("select * from MESSAGE "
                    + " where userid!='" + user
                    + "' and messageid not in("
                    + " select messageid from GETMESSAGE"
                    + " where userid='"
                    + user + "')");
            System.out.println("q=" + q);
            ResultSet rs = st.executeQuery(q);

            // add a record of loading to the GETMESSAGE table
            String q2 = null;
            while (rs.next()) {
                dialog.append(rs.getString("userid") + " thinks book[" + rs.getString("bookid") + "] " + "is: " + rs.getString("message") + "\n");
                q2 = new String("INSERT INTO getmessage (userid, messageid,status)"
                        + " VALUES ('"
                        + user + "',"
                        + Integer.parseInt(rs.getString("messageid")) + ","
                        + "'read')");
                System.out.println("q2=" + q2);
                st.execute(q2);
            }
            rs.close();
            st.close();
            conn.close();

            //System.out.println("awatingresp=" + awaitingresp);
            System.out.println("***did check chat***");
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        
        PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=UTF-8");
        
        try {
            JSONObject jo_out = new JSONObject();
            //jo_out.append("chatid", existchatID);
            jo_out.append("status", "ACTIVE");
            //dialog.append("<p>You can go on send messages.</p>");
            jo_out.append("dialog", dialog.toString());
            out.println(jo_out.toString());
        } catch (Exception e) {
            System.out.println("JSON Error");
            e.printStackTrace();
        }
        
        out.close();
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
