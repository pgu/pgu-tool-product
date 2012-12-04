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
        height: 200px;
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
          <div id="dropbox" class="well">
              <div class="row-fluid">
                  <div class="span4"></div>
                  <div class="span4">
                    Drag and drop a file here...
                  </div>
                  <div class="span4"></div>
              </div>
          </div>
          <div id="status"></div>
      </div>
      <div class="row-fluid">
        <div id="preview" style="border: 1px solid grey; height: 100px;"></div>
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
        upload(files);
    }    
    
    function upload(files) {
        
        var imageType = /image.*/;
        var preview = document.getElementById('preview');
        
        // preview
        for (var i = 0; i < files.length; i++) {
            
            var file = files[i];
            
            if (!file.type.match(imageType)) {
                continue;
            }
            
            var img = document.createElement('img');
            preview.appendChild(img);
            
            var reader = new FileReader();
            reader.onload = (function(aImg) {return function(e) { aImg.src = e.target.result; }; })(img);
            reader.readAsDataURL(file);
        }
        
        // upload
        for (var i = 0; i < files.length; i++) {
            var file = files[i];
            new FileUpload(file);
        }
    }
    
    function Status(file) {

        var status = document.createElement('div');
        var status_name = document.createElement('div');
        var status_pct = document.createElement('div');
        
        status_name.innerHTML = 'Uploading ' + file.name;
        status_pct.innerHTML = '0%';
        
        document.getElementById("status").appendChild(status);
        status.appendChild(status_name);
        status.appendChild(status_pct);

        this.file_name = file.name;
        this.status_pct = status_pct;
        
        var self = this;

        this.updateState = function(label) {
            console.log(' >> update state ' + label);
            self.status_pct.innerHTML = label;            
        };
        
        this.isOver = function(result) {
            self.status_name.title = result;
            self.status_name.innerHTML = self.file_name + ' uploaded!';
            self.status_pct.innerHTML = '';
        };
    }
    
    function FileUpload(file) {
        
        this.status = new Status(file);
        
        var self = this;
        
        var xhr = new XMLHttpRequest();
        this.xhr = xhr;

        this.xhr.addEventListener("progress", function(e) {
            if (e.lengthComputable) {
                var percentage = Math.round((e.loaded * 100) / e.total);
                self.status.updateState(percentage + '%');
            }
        }, false);
        
        this.xhr.addEventListener("load", function(e){
            self.status.updateState('100%');
            self.status.isOver(e.target.responseText);
            
//             setTimeout("location.reload(true);", 1000);
        }, false);
        
        this.xhr.open("POST", "<%= ctx %>/products/<%=product.reference%>/pictures", false); // If async=false, then you'll miss progress bar support.
        
        var formData = new FormData();
        formData.append("file", file);
        
        this.xhr.send(formData);
      }

    </script>
  </body>
</html>
