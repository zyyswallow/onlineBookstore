
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;

public class CommodityDispatcher extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CommodityDAO dao = new CommodityDAO();
        String command = null;
        try {
            request.setCharacterEncoding("GBK");
            response.setContentType("text/html; charset=GBK");
            PrintWriter out = response.getWriter();
            command = request.getParameter("command");
            HttpSession hts = request.getSession(false);
            hts.setMaxInactiveInterval(5000);

            //show user's order history
            if (command.equals("orders")) {
                //get session
                HttpSession hts1 = request.getSession(false);
                if (hts1 != null) {
                    String user = (String) hts.getAttribute("user");
                    //find orders in database
                    ResultSet rst = dao.findOrder(user);
                    hts1.setAttribute("orders", rst);
                    response.sendRedirect("./myOrders.jsp");
                } else {
                    response.sendRedirect("login.html");
                }
            } else if (command.equals("home")) {
                response.sendRedirect("./home.jsp");
                ResultSet rst = dao.getAllBooks();

                //get session
                HttpSession hts1 = request.getSession(false);
                if (hts1 != null) {
                    hts1.setAttribute("books", rst);
                } else {
                    response.sendRedirect("index.jsp");
                }
            } else if (command.equals("bookinfo")) {
                String bid = request.getParameter("bid");
                ResultSet rst = dao.getBookById(bid);
                while (rst.next()) {
                    String[] book = new String[5];
                    book[0] = rst.getString(1);
                    book[1] = rst.getString(2);
                    Double price = rst.getDouble(3);
                    book[2] = price.toString();
                    book[3] = rst.getString(4);
                    book[4] = rst.getString("BDESCRIPTION");

                    request.setAttribute(bid, book);
                }

                request.getRequestDispatcher("./bookinfo.jsp?bid=" + bid).forward(request, response);

            } else if (command.equals("buy")) { //add items into cart, and show the cart
                //get session
                HttpSession hts1 = request.getSession(false);
                if (hts1 != null) {
                    String user = (String) hts1.getAttribute("user");
                    String cID = request.getParameter("pno");
                    dao.buy(user, cID);// put items into cart

                    response.sendRedirect("CommodityManager?command=showCart");
                } else {
                    response.sendRedirect("login.html");
                }
            } else if (command.equals("showCart")) {//show the items in the cart
                //get session
                HttpSession hts1 = request.getSession(false);
                if (hts1 != null) {
                    String user = (String) hts1.getAttribute("user");
                    //search data of shopping cart in the database
                    ResultSet rst = dao.findCart(user);
                    hts1.setAttribute("cart", rst);
                    response.sendRedirect("./shoppingCart.jsp");
                } else {
                    response.sendRedirect("login.html");
                }

            } else if (command.equals("pay")) { // check out
                //get session
                HttpSession hts1 = request.getSession(false);
                if (hts1 != null) {
                    String user = (String) hts1.getAttribute("user");
                    dao.pay(user);  // clear the cart, and add purchase records
                    response.sendRedirect("CommodityManager?command=orders");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
