
import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class Service extends HttpServlet {

    private String jdbcUrl;

    public void init() throws ServletException {
        super.init();
        try {
            String driverName = "org.apache.derby.jdbc.ClientDriver";
            String userName = "IS2560";
            String userPasswd = "IS2560";
            String dbName = "IS2560";
            jdbcUrl = "jdbc:derby://localhost:1527/" + dbName
                    + ";" + "user="
                    + userName + ";password=" + userPasswd;
            System.out.println("url=" + jdbcUrl);
            Class.forName(driverName).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(jdbcUrl);
            request.setCharacterEncoding("GBK");
            response.setContentType("text/html; charset=GBK");
            PrintWriter out = response.getWriter();
            String cmd = request.getParameter("command");

            if (cmd == null) {
                request.getRequestDispatcher("login.jsp").forward(request,
                        response);
                // 
                return;
            }
            if (cmd.equals("Log in")) {
                boolean b = checkUser(request, response, conn); // check username and password
                if (!b) { // if wrong
                    request.getRequestDispatcher("index.jsp").forward(request,
                            response); // back to log_in page
                    return;
                } else { // sucessful! create session, go to products page
                    String id = request.getParameter("id");

                    HttpSession hts = request.getSession(true); // create a session
                    hts.setAttribute("isLogin", "true");
                    hts.setAttribute("user", id);

                    request.getRequestDispatcher("Service?command=home").forward(request, response);
                }

            } else if (cmd.equals("home")) {
                HttpSession hts = request.getSession(false); //get session
                if (hts != null) {
                    String isLogin = (String) hts.getAttribute("isLogin");
                    if (isLogin.equals("true")) {
                        System.out.println("Log in succeed!");
                        request.getRequestDispatcher("CommodityManager?command=home")
                                .forward(request, response);
                    } else {
                        request.getRequestDispatcher("index.jsp").forward(
                                request, response);
                    }
                } else {
                    request.getRequestDispatcher("index.jsp").forward(request,
                            response);
                }
            } else {
                request.getRequestDispatcher("index.jsp").forward(request,
                        response);
            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    boolean checkUser(HttpServletRequest request, HttpServletResponse response,
            Connection conn) throws IOException {
        String user = request.getParameter("id");
        String pwd = request.getParameter("pwd");
        Statement stmt = null;
        ResultSet rst = null;
        String sqlSelect = "SELECT " + "uno," + "upwd"
                + " FROM userlist where uno='" + user + "'" + "and upwd='" + pwd
                + "'";
        try {
            System.out.println("sqlSelect=" + sqlSelect);
            stmt = conn.createStatement();
            rst = stmt.executeQuery(sqlSelect);
            if (rst.next()) { // username and password are right
                System.out.println("Username and password are right");
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
