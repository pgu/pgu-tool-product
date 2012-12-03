<%@ page import="java.util.ArrayList" %>
<%@ page import="pgu.Product" %>
<%@ page import="java.lang.String" %>

<%
    String ctx = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Products tool</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href="<%= ctx %>/assets/css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
      body {
        padding-top: 60px;
        padding-bottom: 40px;
      }
      .sidebar-nav {
        padding: 9px 0;
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

    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container-fluid">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="<%= ctx %>/">Products tool</a>
          <div class="nav-collapse collapse">
            <ul class="nav">
              <li class="active"><a href="<%= ctx %>/products">Products</a></li>
              <li><a href="<%= ctx %>/products/new">New product</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container-fluid">
      <div class="row-fluid">
        <a href="<%= ctx %>/products/new" class="btn btn-primary"><i class="icon-plus icon-white"></i></a>      
      </div>
      <p></p>
      <div class="row-fluid">
        <table class="table table-bordered table-striped">
            <colgroup>
              <col class="span2">
              <col class="span2">
              <col class="span1">
              <col class="span1">
              <col class="span1">
              <col class="span1">
              <col class="span1">
            </colgroup>
            <thead>
              <tr>
                <th>Reference</th>
                <th>Designation</th>
                <th>Weight</th>
                <th>Width</th>
                <th>Depth</th>
                <th>Height</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
            <%
                ArrayList<Product> products = (ArrayList<Product>) request.getAttribute("products");
            
                for (Product product : products) {
            %>
              <tr>
                <td><%= product.reference %></td>
                <td><%= product.designation %></td>
                <td><%= product.weight %></td>
                <td><%= product.width %></td>
                <td><%= product.depth %></td>
                <td><%= product.height %></td>
                <td>
                    <div class="row-fluid">  
                        <div class="span6">
                            <a href="<%= ctx %>/products/<%= product.reference %>" class="btn btn-success"><i class="icon-pencil icon-white"></i></a>
                        </div>
                        <div class="span6">
                            <form action="<%= ctx %>/products/<%= product.reference %>" method="post">
                              <input type="hidden" name="method" value="delete" />
                              <button class="btn btn-danger" type="submit"><i class="icon-trash icon-white"></i></button>
                            </form>
                        </div>
                    </div>
                </td>
              </tr>
            <%
                }
            %>
            </tbody>
          </table>      
      </div>
      
      <hr>

      <footer>
        <p>&copy; Company 2012</p>
      </footer>

    </div><!--/.fluid-container-->

    <script src="http://code.jquery.com/jquery-latest.js"></script>
<!--     <script src="js/bootstrap.min.js"></script> -->
    <script src="<%= ctx %>/assets/js/bootstrap.js"></script>

  </body>
</html>
