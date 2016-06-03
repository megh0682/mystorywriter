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
<title>Draw on Canvas</title>
</head>
<body onload="newCanvas();">

	<canvas id="canvas" width="1300" height="500" title="Draw your story on canvas" alt="Draw your story"
		style="border: 2px solid grey; cursor: crosshair; background-color: #fff;"></canvas>
	<br>
	<br>
	
	<input type="hidden" id ="hiddenstoryid" value= "${story.id}" />
	<input type="button" id="btndownload" value="Save and Submit" />
	<span class="saveCanvasbutton"> <a id="lnkcanvassave" href="#"
		onclick="saveCanvas();"><input type="button" value="Download" /></a></span><span><input type="button" id="btnclearcanvas" value="Clear Canvas" onclick="newCanvas();"/></span><br><br>
    <a href="#" id="dphomepage" name="dphomepage" class="drawpage">Homepage</a> |
    <a href="#" id="dplogout" name="dplogout" class="drawpage">Log Out</a></p>
	<footer>Read, Write and Share</footer>
	
</body>
</html>
