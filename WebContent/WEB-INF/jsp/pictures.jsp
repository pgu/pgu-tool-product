<%@ page import="pgu.Product" %>
<%@ page import="java.lang.String" %>
<%@ page import="java.util.LinkedHashSet" %>

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
      #dropbox {
        width: 300px;
        height: 200px;
        border: 1px solid gray;
        border-radius: 5px;
        padding: 5px;
        color: gray;
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
        Product product = (Product) request.getAttribute("product");
        LinkedHashSet<String> pictures = product.pictures;
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
              <li><a href="<%= ctx %>/products">Products</a></li>
              <li><a href="<%= ctx %>/products/<%=product.reference%>">Product <%=product.reference%></a></li>
              <li class="active"><a href="<%= ctx %>/products/<%=product.reference%>/pictures">Product <%=product.reference%>'s pictures</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container-fluid">
      <div class="row-fluid">
          <div id="dropbox">Drag and drop a file here...</div>
          <div id="status"></div>
      </div>
      <div class="row-fluid">
      <%
      for (String picture_name : pictures) {
      %>
        <img src="<%= ctx %>/products/<%=product.reference%>/pictures/<%=picture_name%>" />
      <%
      }
      %>
      </div>
      
      <hr>

      <footer>
        <p>&copy; Company 2012</p>
      </footer>

    </div><!--/.fluid-container-->

    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="<%= ctx %>/assets/js/bootstrap.js"></script>

    <script type="text/javascript">
    
    window.onload = function() {
        var dropbox = document.getElementById("dropbox");
        dropbox.addEventListener("dragenter", noop, false);
        dropbox.addEventListener("dragexit", noop, false);
        dropbox.addEventListener("dragover", noop, false);
        dropbox.addEventListener("drop", dropUpload, false);
    };
    
    function noop(event) {
        event.stopPropagation();
        event.preventDefault();
    }

    function dropUpload(event) {
        noop(event);
        var files = event.dataTransfer.files;

        for (var i = 0; i < files.length; i++) {
            upload(files[i]);
        }
    }    
    
    function upload(file) {
        document.getElementById("status").innerHTML = "Uploading " + file.name;

        var formData = new FormData();
        formData.append("file", file);

        var xhr = new XMLHttpRequest();
        xhr.upload.addEventListener("progress", uploadProgress, false);
        xhr.addEventListener("load", uploadComplete, false);
        xhr.open("POST", "<%= ctx %>/products/<%=product.reference%>/pictures", false); // If async=false, then you'll miss progress bar support.
        xhr.send(formData);
    }
    
    function uploadProgress(event) {
        // Note: doesn't work with async=false.
        var progress = Math.round(event.loaded / event.total * 100);
        document.getElementById("status").innerHTML = "Progress " + progress + "%";
    }
    
    function uploadComplete(event) {
        document.getElementById("status").innerHTML = event.target.responseText;
    }    
    </script>
  </body>
</html>
