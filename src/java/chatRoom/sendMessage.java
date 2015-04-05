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
public class sendMessage extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int amtread;
        JSONObject jo_in;
        char[] cbuf = new char[200];
        String clean_userid = new String(),
                message = new String();
        String bookid = new String();
        StringBuffer invalue = new StringBuffer();
        StringBuffer dialog = new StringBuffer();

        BufferedReader in = request.getReader();
        while ((amtread = in.read(cbuf)) != -1) {
            invalue.append(cbuf, 0, amtread);
        }
        try {
            Matcher matcher;
            jo_in = new JSONObject(invalue.toString());
            // Compile regular expression
            Pattern pattern = Pattern.compile("[;:,#&'\".!?]");
            // Replace all occurrences of pattern in input
            matcher = pattern.matcher(jo_in.getString("user"));
            clean_userid = matcher.replaceAll("|");
            message = jo_in.getString("message");
            bookid = jo_in.getString("bookid");

            //System.out.println("dialog="+jo_in.getString("dialog"));
            dialog.append(jo_in.getString("dialog"));
        } catch (Exception e) {
            System.out.println("JSON Error on input");
            e.printStackTrace();
        }

        //open the DBMS and insert the record of new messages
        Statement st;
        Connection conn;
        Date now = new Date();
        int new_messageID = 0;
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Boolean valid = false;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String connectionURL = "jdbc:derby://localhost:1527/onlineBookStore";
            conn = DriverManager.getConnection(connectionURL, "TEST", "TEST");

            st = conn.createStatement();
            String q1 = new String("SELECT MAX(messageid) AS messageid FROM message");
            ResultSet rs = st.executeQuery(q1);
            if (rs.next()) {
                new_messageID = rs.getInt("messageid") + 1;
            } else {
                new_messageID = 1;
            }
            System.out.println("messageID=" + new_messageID);
            String q2 = new String("INSERT INTO message (userid, messageid, mtime, message, bookid)"
                    + " VALUES ('"
                    + clean_userid + "',"
                    + new_messageID + ",'"
                    + df.format(now) + "', '"
                    + message + "', '"
                    + bookid + "')");
            st.execute(q2);
            valid = true;

            rs.close();
            st.close();
            conn.close();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (SQLException se) {
            se.printStackTrace();
        }

        PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=UTF-8");

        if (new_messageID > 0) {
            try {
                JSONObject jo_out = new JSONObject();
                jo_out.append("messageid", new_messageID);
                dialog.append(clean_userid + " thinks book[" + bookid + "] " + "is: " + message + "\n");  //add new message to the chat box
                jo_out.append("dialog", dialog.toString());
                out.println(jo_out.toString());
            } catch (Exception e) {
                System.out.println("JSON Error on input");
                e.printStackTrace();
            }
        } else {
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
