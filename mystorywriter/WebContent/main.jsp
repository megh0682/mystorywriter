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
<title>Hello ${profile.firstname}</title>
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
<img class ="profilepicture" id = "hphid" src ="#"/>
<h1>My Story Board</h1>
</header>

<div class="container">
<div class="row">

<div class="col-md-4" class = "form-group">
<h1>Hello ${profile.firstname}! </h1>
<c:choose>
<c:when test="${user ne null}">
<label for="fn"> First-Name: </label><input name="firstname" id="firstname"type="text" disabled class="form-control" value = ${profile.firstname}>
<label for="ln"> Last-Name: </label><input name="lastname" id="lastname" type="text" disabled class="form-control" value = ${profile.lastname}>
<label for="email"> Email: </label><input type="email" id="email" name="email" disabled class="form-control"value = ${profile.email}>
<label for="profilepic"> Avatar: </label>
<input type ="hidden" id="profileid" class="form-control" name="profileid" value="${profile.id}"/>
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

<img class ="profilepicture" id = "hpbid" src ="#"/>
<input type="submit" id="editprofile" name="editprofile" value="Update"/>
</div>
<div class="col-md-4">
<h1>My Stories</h1>
<ol>
<c:forEach var="story" items="${stories}">

<li class="homepage"><a href="#" id="hpgetStory" name="hpgetStory" class="homepage">${story.title}</a><input type ="hidden" name="hpstoryid" id="hpstoryid" class="homepage" value="${story.id}"}/></li>    
</c:forEach>
</ol>
</div>   
<div class="col-md-4">
<a href="#" id ="hpcreateStory" name="createStory" class="homepage"><h1>Create a new Story!</h1></a>
</div> 
</div>
</div> 
<a href="#" id="hplogout" name="hplogout" class="homepage">Log Out</a></p>

</c:when>

<c:otherwise>

 <p><a href="#" id="hplogin" name="hplogin" class="homepage">Log in to Storyboard</a> 
 <a href="#" id="hpregister" name="hpregister" class="homepage">I'm sold. Sign me up!</a></p>
 
</c:otherwise>

</c:choose>

<footer> Read, Write and Share</footer>
</body>
</html>