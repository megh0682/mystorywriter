<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet" href = "http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css">
<link rel="stylesheet" href="cis.css"  type="text/css">  
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script type="text/javascript" src = "http://code.jquery.com/jquery-1.10.2.js"></script>
<script type="text/javascript" src = "http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script type="text/javascript" src="story.js"></script>
<title>Registration Page</title>
</head>
<body>
<div class = "page-header">

   <h1 align ="center"><b> Sign Up on Storyboard </b> </h1>
    
</div>
<div class="container">
<div class="row">
 <div style="width: 200px; margin: 100px auto 0 auto;"> 
     <form id="registerform" role="form"  action="StoryServlet" method="post" >
	 <div class="col-md-16" class = "form-group">
	        <label for ="fn"></label><input type="text" name="firstname" id="fnlabel" placeholder="First name" required  class="form-control"/><p></p>
	        <label for ="ln"></label><input type="text" name="lastname" id="lnlabel" placeholder="Last name" required  class="form-control"/><p></p>
	        <label for ="un"></label><input type="text" name="username" id="unlabel" placeholder="Username" required  class="form-control"/><p></p>
	        <label for ="pwd"></label><input type="password" name="password" id="pwdlabel" placeholder="Password" required  class="form-control"/><p></p>
            <label for ="email"></label><input type="email" name="email" id="emaillabel" placeholder="Email" required  class="form-control"/><p></p>
            <input type="button"  id="submitregister" value="Register"/><span><input type="button" value="Cancel"/></span><p id="namesuccess"></p>
            <p><a href="#" id="reglogin" class="getlogin" name="reglogin" >Log in to Storyboard</a> 
     </div>  
	 </form> 
</div>
     
 </div>
</div>  
</body>
</html>