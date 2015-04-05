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
        <title>Online Bookstore Chat Room</title>

        <!-- CSS -->
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/customize.css">

        <!-- js -->
        <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>

        <script type="text/javascript" src="includes/json2.js"></script>
        <script type="text/javascript" src="includes/JSON_CHAT_Utilities.js"></script>
    </head>
    <body>
        <input id="user" value="<%=user%>">
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
                        <li class="active"><a href="Service?command=chatRoom">Chat Room</a></li>
                        <li><a href="CommodityManager?command=showCart">Shopping Cart</a></li>
                        <li><a href="Logout">Log out</a></li>
                    </ul>
                </div>
            </div>
        </nav>

        <!-- ==== main content ==== -->
        <div class="container-fluid">
            <div class="row-main" style="margin-top:8%">


                <div class="col-sm-8 col-sm-offset-2">
                    <!-- ==== book select panel ==== -->
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Book Selection
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" role="form">
                                <div class="form-group">
                                    <h4><label class="col-sm-5 col-sm-offset-1">Let's select a book to discuss!</label></h4>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Book</label>
                                    <div class="col-sm-5">
                                        <select id="bookid">
                                            <%
                                                rst.beforeFirst();
                                                while (rst.next()) {
                                                    String bid = rst.getString(1);
                                                    String bname = rst.getString(2);
                                                    out.print("<option value=\"" + bid + "\">" + "[" + bid + "] " + bname + "</option>");
                                                }

                                            %>
                                        </select>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>


                    <!-- ==== Chat Box panel ==== -->
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Chat Box
                        </div>
                        <div class="panel-body">
                            <form role="form">
                                <input type ="hidden" id ="chatStatus" value="active" />
                                <div class="form-group">
                                    <textarea id="dialog" class="form-control" rows="14" placeholder="Please send a new message to see other reviews." readonly></textarea>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-12">Message</label>
                                    <div class="col-sm-9">
                                        <input type="text" id="message" class="form-control">
                                    </div>
                                    <div class="col-sm-2 col-sm-offset-1">
                                        <button  class="btn btn-primary btn-block" onmouseup="sendMessage()" type="button">Send</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>


                </div>

            </div>
        </div>
        <br><br>
    </body>
</html>
