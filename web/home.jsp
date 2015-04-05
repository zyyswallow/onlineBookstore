<%@page import="java.sql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String user = (String) session.getAttribute("user");
    ResultSet rst = (ResultSet) session.getAttribute("books");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Online Bookstore Home Page</title>

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
                        <li class="active"><a href="CommodityManager?command=home">Home</a></li>
                        <li><a href="CommodityManager?command=orders">My Orders</a></li>
                        <li><a href="Service?command=chatRoom">Chat Room</a></li>
                        <li><a href="CommodityManager?command=showCart">Shopping Cart</a></li>
                        <li><a href="Logout">Log out</a></li>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="container-fluid">
            <div class="row-main text-center">
                <%
                    while (rst.next()) {
                        String bid = rst.getString(1);
                        String bname = rst.getString(2);
                        String bdescription = rst.getString("BDESCRIPTION");
                        out.print("<div class=\"col-sm-4\">");

                        out.print("<a href=\"CommodityManager?command=bookinfo&bid=" + bid + "\"><img height = \"150\" src=\"images/" + bid + ".png\"></a>");
                        out.print("<p></p>");
                        out.print("<p><strong><a href=\"CommodityManager?command=bookinfo&bid=" + bid + "\">" + bname + "</a></strong></p>");
                        out.print("<p>" + bdescription + "</p>");
                        out.print("</div>");

                    }
                %>
            </div>

        </div>
        <br><br>
    </body>
</html>
