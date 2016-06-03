/**
 * It contains HTML event calls to javascript functions defined here.
 */

    var ctx, color ="#000";	
	// function to setup a new canvas for drawing
	function newCanvas(){
	//alert("Inside newCanvas function");
	  // setup canvas
	  if (canvas.getContext){
	  ctx = canvas.getContext("2d");
	  ctx.strokeStyle = color;
	  ctx.lineWidth = 2;	
	  ctx.beginPath();
	  
	    /*ctx.arc(75,75,50,0,Math.PI*2,true); // Outer circle
	    ctx.moveTo(110,75);
	    ctx.arc(75,75,35,0,Math.PI,false);  // Mouth (clockwise)
	    ctx.moveTo(65,65);
	    ctx.arc(60,65,5,0,Math.PI*2,true);  // Left eye
	    ctx.moveTo(95,65);
	    ctx.arc(90,65,5,0,Math.PI*2,true);  // Right eye
	    ctx.stroke();*/
		
		// setup to trigger drawing on mouse or touch
	    drawTouch();
	    drawPointer();
		drawMouse();
	    /*$('title').each(function(index, value){
	     alert($(this).attr('title'));
	     });*/
	   }else{
		   console.log( "canvas not supported" );
	   }
	}
	/**************************************************/
	// prototype to	start drawing on mouse using canvas moveTo and lineTo
	var drawMouse = function() {
		var clicked = 0;
		var start = function(e) {
			clicked = 1;
			ctx.beginPath();
			x = e.pageX;
			y = e.pageY;
			ctx.moveTo(x,y);
		};
		var move = function(e) {
			if(clicked){
				x = e.pageX;
				y = e.pageY;
				ctx.lineTo(x,y);
				ctx.stroke();
			}
		};
		var stop = function(e) {
			clicked = 0;
		};
	    document.getElementById("canvas").addEventListener("mousedown", start, false);
		document.getElementById("canvas").addEventListener("mousemove", move, false);
		document.addEventListener("mouseup", stop, false);
	};
	
	/**************************************************/
	
	// prototype to	start drawing on touch using canvas moveTo and lineTo
	var drawTouch = function() {
		var start = function(e) {
			ctx.beginPath();
			x = e.changedTouches[0].pageX;
			y = e.changedTouches[0].pageY ;
			ctx.moveTo(x,y);
		};
		var move = function(e) {
			e.preventDefault();
			x = e.changedTouches[0].pageX;
			y = e.changedTouches[0].pageY ;
			ctx.lineTo(x,y);
			ctx.stroke();
		};
	    document.getElementById("canvas").addEventListener("touchstart", start, false);
		document.getElementById("canvas").addEventListener("touchmove", move, false);
	}; 
	    
	/**************************************************/
	
	// prototype to	start drawing on pointer(microsoft ie) using canvas moveTo and lineTo
	var drawPointer = function() {
		var start = function(e) {
	        e = e.originalEvent;
			ctx.beginPath();
			x = e.pageX;
			y = e.pageY ;
			ctx.moveTo(x,y);
		};
		var move = function(e) {
			e.preventDefault();
	        e = e.originalEvent;
			x = e.pageX;
			y = e.pageY ;
			ctx.lineTo(x,y);
			ctx.stroke();
	    };
	    document.getElementById("canvas").addEventListener("MSPointerDown", start, false);
		document.getElementById("canvas").addEventListener("MSPointerMove", move, false);
	};        
	
	/********************************************************/

	 function dataURLtoBlob(dataURL) {
		        var BASE64_MARKER = ';base64,';
			    if (dataURL.indexOf(BASE64_MARKER) == -1) {
			      var parts = dataURL.split(',');
			      var contentType = parts[0].split(':')[1];
			      var raw = decodeURIComponent(parts[1]);

			      return new Blob([raw], {type: contentType});
			    }

			    var parts = dataURL.split(BASE64_MARKER);
			    var contentType = parts[0].split(':')[1];
			    var raw = window.atob(parts[1]);
			    var rawLength = raw.length;

			    var uInt8Array = new Uint8Array(rawLength);

			    for (var i = 0; i < rawLength; ++i) {
			      uInt8Array[i] = raw.charCodeAt(i);
			    }

			    return new Blob([uInt8Array], {type: contentType});
		 }
	/**************************************************/
	  
	 function uploadcanvas(){
		 var canvas = document.getElementById('canvas');
		 var dataURL = canvas.toDataURL();
		 console.log(dataURL);
		 //alert(dataURL);
		 
		 $.ajax({
			  type: "POST",
			  url: "uploadcanvas",
			  data: {contents: dataURL,}
			}).done(function(o) {
			  console.log('saved'); 
			  // If you want the file to be visible in the browser 
			  // - please modify the callback in javascript. All you
			  // need is to return the url to the file, you just saved 
			  // and than put the image in your browser.
			});
		 	 	  
	 }
	
		/**************************************************/
	 
	//document.addEventListener( "DOMContentLoaded", function(){
		// setup a new canvas for drawing wait for device init
	  //  setTimeout(function(){
		//   newCanvas();
	    //}, 1000);
	//}, false );

	
	
	
   /***************************************************************************************************************/
    function saveCanvas(){
    	var imageURL=document.getElementById("canvas").toDataURL();
    	document.getElementById("lnkcanvassave").href=imageURL;
    	document.getElementById("lnkcanvassave").download="canvasid.png"
    }
	
   /**************************************************/
   
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    
