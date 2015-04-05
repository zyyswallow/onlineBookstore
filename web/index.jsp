<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Welcome to online bookstore!</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <link rel="stylesheet" href="css/customize.css">
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/login.css">    
        <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>       
        <script src="js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="navbar">
            <div class="container">
                <div class="navbar-header">

                    <div class="header-logo-container navbar-brand">
                        <h1>Online Bookstore</h1>
                    </div>

                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <div class="navbar-collapse collapse pull-right">
                        <ul class="nav navbar-nav pull-right">
                            
                        </ul>
                    </div>

                </div>
            </div>
        </div>
        <section class="section-first" id="section-first">
            <div class="container">
                <div class="head_text col-md-12">
                    <h1>
                        Books, Textbooks, Magazines, ebooks & More
                    </h1>

                    <h2 class="col-sm-10 col-sm-push-1">
                        Here is more than a bookstore.
                    </h2>
                </div>

                <div id="forms_container" class="form_cont col-lg-4 col-sm-4 col-xs-12 pull-right">
                    <form class="dark_form" method="post" action="Service" novalidate>
                        <legend class="clearfix">
                            <div class="pull-left">
                                <span class="glyphicon glyphicon-lock"></span> Enter Online Bookstore
                            </div>
                        </legend>


                        <fieldset class="register-form-fields">
                            <div class="input">
                                <input type="text" id="email" name="id" class="form-control" placeholder="Username">
                            </div>
                            <div class="input" data-showpassword="">
                                <input type="password" name="pwd" class="form-control" placeholder="Password">
                            </div>
                        </fieldset>

                        <fieldset>
                            <button id="submit" type="submit" class="fbtn blue" name="command" value="Log in">
                                <span class="txt">LOG IN</span>
                            </button>
                        </fieldset>
                    </form>
                    <form class="dark_form">
                        <legend class="clearfix">
                            <div class="pull-left">
                                <span class="glyphicon glyphicon-user"></span> Don't Have an Account?
                            </div>
                        </legend>
                        <fieldset>
                            <button type="button" class="fbtn blue" data-toggle="modal" data-target="#sigup">
                                <span class="txt">SIGN UP</span>
                            </button>
                        </fieldset>
                    </form>

                </div>

                <div id="video" class="col-sm-7">
                    <div id="placeholder">
                        <div id="play_btn" class="play_btn"></div>
                    </div>
                    <div id="video_placeholder_mobile"></div>
                </div>
            </div>
        </section>
      
        <!-- madal  (sign up)
        ======================================================================-->
        <div class="modal fade" id="sigup" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                        <h4 class="modal-title" id="myModalLabel">Sign Up</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal ng-pristine ng-valid" role="form" method="POST" action="User">
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Name</label>
                                <div class="col-sm-6">
                                    <input name="id" class="form-control" placeholder="Name">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Email</label>
                                <div class="col-sm-6">
                                    <input name="email" class="form-control" placeholder="Email">
                                </div>
                            </div>
         
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Password</label>
                                <div class="col-sm-6">
                                    <input type="password" name="pwd1" class="form-control" placeholder="Password">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Confirm Password</label>
                                <div class="col-sm-6">
                                    <input type="password" name="pwd2" class="form-control" placeholder="Password">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-3 col-sm-6">
                                    <div class="checkbox">
                                        <label>
                                            <input type="checkbox" name="remember"> I agree to the Terms of Service.
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-5 col-sm-3">
                                    <button type="submit" class="btn btn-primary btn-block" name="command" value="Register">
                                        <i class="fa fa-sign-in"></i>
                                        Sign Up
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
         <!-- modal end-->

    </body>
</html>
