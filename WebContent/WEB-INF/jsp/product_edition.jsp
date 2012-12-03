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
    <%
        String designation = "";
        String weight = "";
        String width = "";
        String depth = "";
        String height = "";
        String form_url = "products";
    
        Product product = (Product) request.getAttribute("product");
        
        if (product != null) {
            designation = product.designation; 
            weight = product.weight; 
            width = product.width; 
            depth = product.depth; 
            height = product.height; 
            
            form_url += "/" + product.reference;
        }
    
    %>

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
              <li class="active"><a href="<%= ctx %>/">Description</a></li>
              <li><a href="<%= ctx %>/series">Series</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container-fluid">
      <div class="row-fluid">
        <form action="<%= form_url %>" method="post">
          <fieldset>
            <legend>Product's description</legend>
            <label>Reference</label>
            <% 
              if (product == null) {
            %>
            <input name="reference" type="text" placeholder="XYZ789..."></input>
            <% 
              } else {
            %>
            <p><%= product.reference %></p>
            <% 
              }
            %>
            <label>Designation</label>
            <input name="designation" type="text" placeholder="Solution for roses' health..."><%= designation %></input>
            <label>Weight</label>
            <input name="weight" type="text" placeholder="10 Kg..."><%= weight %></input>
            <legend>Dimensions</legend>
            <label>Width</label>
            <input name="width" type="text" placeholder="50''..."><%= width %></input>
            <label>Depth</label>
            <input name="depth" type="text" placeholder="10''..."><%= depth %></input>
            <label>Height</label>
            <input name="height" type="text" placeholder="90''..."><%= height %></input>
            <p></p>
            <button type="submit" class="btn">Save</button>
          </fieldset>
        </form>      
            
      </div>
      
      <hr>

      <footer>
        <p>&copy; Company 2012</p>
      </footer>

    </div><!--/.fluid-container-->

    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="<%= ctx %>/assets/js/bootstrap.js"></script>

  </body>
</html>
