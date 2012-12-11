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
        String ref = "";
        String designation = "";
        String weight = "";
        String width = "";
        String depth = "";
        String height = "";
        String form_url = ctx + "/products";
    
        Product product = (Product) request.getAttribute("product");
        
        if (product != null) {
            ref = product.reference;
            designation = product.designation; 
            weight = product.weight; 
            width = product.width; 
            depth = product.depth; 
            height = product.height; 
            
            form_url += "/" + ref;
        }

        String frameId = (String) request.getParameter("frameId");
    %>

    <div id="products_toolbar" class="navbar navbar-inverse navbar-fixed-top" style="display:none;">
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
              
                <% 
                  if (product == null) {
                %>
                <li class="active"><a href="<%= ctx %>/products/new">New product</a></li>
                <% 
                  } else {
                %>
                <li class="active"><a href="<%= ctx %>/products/<%=ref%>">Product <%=ref%></a></li>
                <li><a href="<%= ctx %>/products/<%=ref%>/pictures">Product <%=ref%>'s pictures</a></li>
                <% 
                  }
                %>
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
            <p><%=ref%></p>
            <% 
              }
            %>
            <label>Designation</label>
            <input name="designation" type="text" placeholder="Solution for roses' health..." value="<%= designation %>" /> 
            <label>Weight</label>
            <input name="weight" type="text" placeholder="10 kg..." value="<%= weight %>" />
            <legend>Dimensions</legend>
            <label>Width</label>
            <input name="width" type="text" placeholder="50''..." value="<%= width %>" />
            <label>Height</label>
            <input name="height" type="text" placeholder="90''..." value="<%= height %>" />
            <label>Depth</label>
            <input name="depth" type="text" placeholder="10''..." value="<%= depth %>" />
            <input name="frameId" type="hidden" value="<%= frameId %>" />
            <p></p>
            <button type="submit" class="btn">Save</button>
          </fieldset>
        </form>    
      </div>
      
      <div id="pictures_link" class="row-fluid" style="display:none;">
          <p></p>  
          <button class="btn btn-inverse" onclick="showPictures()">See pictures</button>        
      </div>
      
      <hr>

      <footer>
        <p>&copy; Company 2012</p>
      </footer>

    </div><!--/.fluid-container-->

    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="<%= ctx %>/assets/js/bootstrap.js"></script>
    <script src="<%= ctx %>/pgu/pgu.js"></script>
    <script type="text/javascript">
    function page_init() {
    	
    	console.log(window.location);
    	
    	if (is_in_portal()) {
        	document.getElementById('products_toolbar').style.display = 'none';
        	document.getElementById('pictures_link').style.display = '';
    		
    	} else {
        	document.getElementById('products_toolbar').style.display = '';
        	document.getElementById('pictures_link').style.display = 'none';
    		
    	}
    	
    	
    	if (!is_in_portal()) {
    		console.log("!! is not in portal !!");
    		return;
    	}

    	<%if (product != null) {%>
    	    window.product_ref = '<%=ref%>';
        	if (window.frame_id && window.frame_id.indexOf('product_new') > -1) {
        		replaceMenuNewForMenuProduct(product_ref);
            	sendTitleToPortal(product_ref);
        	}
        	
           
    	<%} else {%>
    	   document.getElementById('pictures_link').style.display = 'none';
    	<%}%>

    }
    </script>

  </body>
</html>
