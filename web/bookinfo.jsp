<%-- 
    Document   : bookinfo
    Created on : Mar 27, 2015, 3:49:34 PM
    Author     : yanyanzhou
--%>

<%@page import="java.sql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String user = (String) session.getAttribute("user");
    String bookid = request.getParameter("bid");
    String[] book = (String[]) request.getAttribute(bookid);
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
                        <li><a href="CommodityManager?command=home">Home</a></li>
                        <li><a href="CommodityManager?command=orders">My Orders</a></li>
                        <li><a href="chatRoom.jsp">Chat Room</a></li>
                        <li><a href="CommodityManager?command=showCart">Shopping Cart</a></li>
                        <li><a href="Logout">Log out</a></li>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="container-fluid">
            <div class="row-main text-center">
                <%
                    out.print("<div class=\"col-sm-3 col-sm-offset-1\">\n");

                    out.print("<img height = \"200\" src=\"images/" + book[0] + ".png\">\n");
                    out.print("</div>\n");
                    out.print("<div class=\"col-sm-6\">\n");
                    out.print("<p><strong>" + book[1] + "</strong></p>\n");
                    out.print("<p>By " + book[3] + "</p>\n");
                    out.print("<p>Price: $" + book[2] + "</p>\n");
                    out.print("<p>" + book[4] + "</p>\n");
                    out.print("<button class=\"btn-primary\" type=\"submit\"><a href=\"CommodityManager?command=buy&pno=" + bookid + "\">Add to Cart</a></button>\n");
                    out.print("</div>\n");

                %>
            </div>
        </div>
    </body>
</html>
