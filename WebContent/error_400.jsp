<%@ page import="java.lang.String" %>
<%
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Invalid Request</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="<%= ctx %>/assets/css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
      body {
        padding-top: 60px;
        padding-bottom: 40px;
      }
    </style>
    <link href="<%= ctx %>/assets/css/bootstrap-responsive.css" rel="stylesheet">

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- Fav and touch icons -->
    <link rel="shortcut icon" href="<%= ctx %>/assets/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="<%= ctx %>/assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="<%= ctx %>/assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="<%= ctx %>/assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="<%= ctx %>/assets/ico/apple-touch-icon-57-precomposed.png">
  </head>

  <body>

    <div class="container-fluid">
      <div class="row-fluid">
        <div class="alert alert-error">
          <strong>Invalid request!</strong> <%= request.getAttribute("javax.servlet.error.message") %>
        </div>
      </div>
      
      <hr>

      <footer>
        <p>&copy; Company 2012</p>
      </footer>

    </div><!--/.fluid-container-->

    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="<%= ctx %>/assets/js/bootstrap.min.js"></script>
  </body>
</html>
