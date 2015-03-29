
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;

public class UserManagementDispatcher extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("GBK");
        response.setContentType("text/html; charset=GBK");

        UserDAO dao = new UserDAO();
        String command = null;

        try {
            PrintWriter out = response.getWriter();
            command = request.getParameter("command");

            if (command.equals("Register")) { // sign up a new user
                String id = request.getParameter("id");
                String pwd1 = request.getParameter("pwd1");
                String pwd2 = request.getParameter("pwd2");
                String email = request.getParameter("email");

                if (pwd1.equals(pwd2)) {
                    // create an object named user to pass parameters
                                    System.out.println("==");
                    User user = new User();
                    user.setId(id);
                    user.setPwd(pwd1);
                    user.setEmail(email);

                    //check whether this id has existed in ths database
                    ResultSet rst = dao.findBy(id);
                    if (!rst.next()) {
                        //create a new user
                        boolean suc = dao.append(user);
                        System.out.println("reg suc=" + suc);
                        if (suc)// register successful
                        {
                            HttpSession hts = request.getSession(true); // create a session
                            hts.setAttribute("isLogin", "true");
                            hts.setAttribute("user", id);

                            request.getRequestDispatcher("Service?command=home").forward(request, response);
                        } else // register failed
                        {
                            response.sendRedirect("index.jsp");
                        }
                    }
                } else {
                    response.sendRedirect("index.jsp");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
