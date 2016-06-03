<%@page contentType="text/html" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css">
<link rel="stylesheet" href="cis.css" type="text/css">
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script type="text/javascript"
	src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script type="text/javascript" src="story.js"></script>
<script type="text/javascript" src="cis.js"></script>
<title>${story.title}</title>
</head>
<body onload = "getCanvas();">

<header>
<img class ="profilepicture" id = "sphid" src ="#"/>
<input type ="hidden" id="profileid" class="form-control" name="profileid" value="${profile.id}"/>
<h1>My Story Board</h1>
</header>

<div class="container">
<div class="row">

<c:choose>

<c:when test="${(user ne null) && (story ne null)}">
<b><center>${story.title}</center></b>
<p>${story.firstPart}</p>
<p>${story.middlePart}</p>
<p>${story.lastPart}</p>
<p><c:choose>
     <c:when test="${not empty story.storypic}">
     <img src="StoryServlet?action=canvas&for=${story.id}"/>
     </c:when>
     <c:otherwise>
     <img src="StoryServlet?action=canvas&for=${story.id}"/>
     </c:otherwise>
  </c:choose>
</p>	
<p><a href="StoryServlet?action=storypdf&for=${story.id}">Download as pdf</a></p>
</c:when>
<c:otherwise>
<p><a href="#" id="splogin" name="splogin" class="storypage">Log in to Storyboard</a> 
 <a href="#" id="spregister" name="spregister" class="storypage">I'm sold. Sign me up!</a></p>
</c:otherwise>
</c:choose>

<a href="#" id="sphomepage" name="sphomepage" class="storypage">Homepage</a> |
<a href="#" id="splogout" name="splogout" class="storypage">Log Out</a></p>
			
</div>
</div>
<footer>Read, Write and Share</footer>
</body>
</html>