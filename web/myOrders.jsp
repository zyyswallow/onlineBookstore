<%-- 
    Document   : myOrders
    Created on : Mar 28, 2015, 11:56:40 PM
    Author     : yanyanzhou
--%>

<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String user = (String) session.getAttribute("user");
    ResultSet rst = (ResultSet) session.getAttribute("orders");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Online Bookstore History Orders Page</title>

        <!-- CSS -->
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/customize.css">

        <!-- js -->
        <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>

    </head>
    <body>
        <!-- navigation bar -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand">Online Bookstore</a>
                </div>
                <div id="navbar" class="navbar-collapse collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <li><a><u>Hello, <%=user%> </u></a></li>
                        <li><a href="CommodityManager?command=home">Home</a></li>
                        <li class="active"><a href="CommodityManager?command=orders">My Orders</a></li>
                        <li><a href="Service?command=chatRoom">Chat Room</a></li>
                        <li><a href="CommodityManager?command=showCart">Shopping Cart</a></li>
                        <li><a href="Logout">Log out</a></li>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="container-fluid">
            <div class="row-main">
                <div class="col-sm-10 col-sm-offset-1">
                    <h4>Your order history:</h4>
                    <br>
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <td>Book ID</td>
                                <td>Book</td>
                                <td>Book Name</td>
                                <td>Price</td>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                double a = 0;
                                rst.beforeFirst();
                                while (rst.next()) {
                                    String commodityId = rst.getString(1);
                                    String commodityName = rst.getString(2);
                                    double commodityPrice = rst.getDouble(3);
                                    a = a + commodityPrice;
                                    out.println("<tr><td>" + commodityId + "</td>");
                                    out.println("<td>" + "<img width=90 src=images/" + commodityId + ".png>" + "</td>");
                                    out.println("<td>" + commodityName + "</td>");
                                    out.println("<td>$ " + commodityPrice + "</td>");
                                    out.println("</tr>");
                                }

                            %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <br><br><br>     
    </body>
</html>

