<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
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
<script type="text/javascript" src="cis.js"></script>
<title>Edit Your Profile</title>
</head>
<body>
<header>
<!--  
<c:choose>
     <c:when test="${not empty profile.profpic}">
     <img src="StoryServlet?action=image&for=${profile.id}"/>
     </c:when>
     <c:otherwise>
     <img src="StoryServlet?action=image&for=${profile.id}"/>
     </c:otherwise>
 </c:choose>
 -->
<img class ="profilepicture" id = "pphid" src ="#"/>
<h1>Edit Your Profile</h1>
</header>
<div class="container">
<div class="row">
<div class="col-md-6" class = "form-group">
<h1>Hello ${user.username}! </h1>
<c:choose>
<c:when test="${user ne null}">
<label for="fn"> First-Name: </label><input type="text" class="form-control" name="fn" value = "${profile.firstname}"/>
<label for="ln"> Last-Name: </label><input type="text" class="form-control" name="ln"  value = "${profile.lastname}"/>
<label for="email"> Email: </label><input type="email" class="form-control" name="email" value = "${profile.email}"/>
<input type ="hidden" id="profileid" class="form-control" name="profileid" value="${profile.id}"/>
<br><input  type="submit" value="Submit" id="submitform" /><br>
<div id="profsuccess" style="display:none;color:green; font-size: 15px;"></div>
<br>
</c:when>
<c:otherwise>
<p><a href="#" id="pplogin" name="pplogin" class="profilepage">Log in to Storyboard</a> 
<a href="#" id="ppregister" name="ppregister" class="profilepage">I'm sold. Sign me up!</a></p>
</p>
</c:otherwise>
</c:choose>
<a href="#" id="pphomepage" name="pphomepage" class="profilepage">Homepage</a> |
<a href="#" id="pplogout" name="pplogout" class="profilepage">Log Out</a></p>
</div>
<div class="col-md-6" class = "form-group"><br><br><br><br>
<img class ="profilepicture" id = "ppbid" src ="#"/>
<input type ="hidden" name="profileid" id="profileid" value="${profile.id}"/>
<form action="StoryServlet" method="post" enctype="multipart/form-data">
    <input type="text" name="description" />
    <input type="hidden" id="action" name="action" value="editprofilepic"/>
    <input type ="hidden" id="profilepicid" name="profilepicid" value="${profile.id}"/>
    <input type="file" name="file" id="fileupload" /><div id="fadein" style="display:none;color:red font-size: 5px;"></div>
    <input type="submit" id="uploadpic" value="Upload" />
</form>
<div id="picsucess" style="display:none;color:green; font-size: 15px;"></div>
<div id="imgContainer"></div>
</div>
     
</div>
</div>
</body>
</html>
