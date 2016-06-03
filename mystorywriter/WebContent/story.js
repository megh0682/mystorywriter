/**
 * Story content post forms using jquery ajax and validation of UI inputs.
 */
 $(document).ready(function(){
	 

	 $.ajaxSetup({'cache':true});
/**
 * Author: Megha Iyer
 * Function: Update profile button click event on homepage.jsp page opens a profile.jsp page
 */

$("#editprofile").click(function(){	 
	
	var jsppage = "profile.jsp";
	window.location.href = jsppage;
	 
});	

/*******************************************************************************************************/
/**
 * Author: Megha Iyer
 * Function: Upload profile picture on the server profile.jsp page
 */
$("#uploadpic").click(function(e){
	e.preventDefault();
	var fd = new FormData(document.querySelector("form"));
	$.ajax({
	  type: "POST",
	  url: "StoryServlet",
      data: fd,
	  processData: false,
      contentType: false,
	  success: function(data){
		  $("#picsucess").text(data.filename + " " + "uploaded sucessfully!");
	      $("#picsucess").fadeIn();
	      $("#picsucess").fadeOut(10000);
	  }	 
	}).done(function(data) {
	   //done block
	}).fail(function(jqXHR, textStatus) {
        $("#fadein").text('File upload failed ...');
        $("#fadein").fadeIn();
        $("#fadein").fadeOut(10000);
    }).complete(function(jqXHR, textStatus) {
    	location.reload();
       
    });   
    
});



/******************************************************************************************************/
/**
 * Author: Megha Iyer
 * Function: Edit profile details on profile.jsp page and save it on the server.
 */ 
$("#submitform").click(function(){

	var fn = $("input[name=fn]").val();
	var ln= $("input[name=ln]").val();
	var email= $("input[name=email]").val();
	//var pic =$("input[name=file]").val();
	var profileid = $("#profileid").val();
	var action = "editprofile";
	$.ajax({
	  type: "POST",
	  url: "StoryServlet",
      data: {action:action,fn:fn,ln:ln,email:email,profileid:profileid},
	  dataType: "json",
	  success: function(json){
		
		if (json.profileurl) {
        // data.redirect contains the string URL to redirect to
		//alert("Sucessfully updated the profile!") ;
        $("input[name=fn]").val(fn);
        $("input[name=ln]").val(ln);
        $("input[name=email]").val(email);
        $("#profsuccess").text( "Your profile is updated sucessfully!");
	    $("#profsuccess").fadeIn();
	    $("#profsuccess").fadeOut(10000);
        //window.location.href = json.profileurl;
       
        }else{
        // data.form contains the HTML for the replacement form
        $("input[name=fn]").val(fn);
        $("input[name=ln]").val(ln);
        $("input[name=email]").val(email);
      //  alert(json.error);
        }
	    },
	    error: function(){
			  console.log("fail") ; 
       },
	 
	});
});
/*************************************************************************************/
/**
 * Author: Megha Iyer
 * Function:Send username and password data on login.jsp to server for validation and authentication.
 */ 

	$("#myname input[value=Login]").click(function(){
	var name = $("#myname input[type=text]").val();
	var password = $("#myname input[type=password]").val();
	if(name=="")
	  alert("Please enter your name!");
	else if(password=="")
      alert("Please enter your password!");
	else
	  $.ajax({
		  type: "POST",
		  url: "StoryServlet",
		  data: {name:name, password:password},
		  dataType: "json",
		  success: function(json){
			//alert("Success")  
			if (json.redirect==="main.jsp") {
            // data.redirect contains the string URL to redirect to
            window.location.href = json.redirect;
            }else{
            // data.form contains the HTML for the replacement form
            alert(json.error);
            $("#myname input[type=text]").val("");	
            $("#myname input[type=password]").val("");
            }
           
			},
		   error: function(){
 			  console.log("fail") ; 
	       },
		 
		});
     });
	
	$("#myname input[value=Cancel]").click(function(){
		$("#myname input[type=text]").val("");	
	 });
 
	
/**********************************************************************/
/** Author: Megha Iyer
* Function: Save story title on CreateStory.jsp.
* 
*/ 
	
	$("#formTitle input[value=Save]").click(function(){
		var formTitle = $("#formTitle textarea").val();
		if(formTitle=="")
		  alert("Please enter the title of your story!");
		else
		  $.ajax({
			  type: "POST",
			  url: "StoryServlet",
			  data: {storyTitle:formTitle,action:"passtitle"},
			  dataType: "json",
			  success: function(json){
				$("#titlesuccess").show("slow", function() {
					  $(this).css("color","green");
				      $(this).text("Story Title saved successfully.");
				    });
				$("#titlesuccess").hide("slow", function() {
					  $(this).text("");
				    });
				$("#titlesuccess").show(5,function() {
					  $(this).text("");
				    });
			   },
			   error: function(){
	 			  console.log("fail") ; 
		       },
			 
			});
	     });
		
		$("#formTitle input[value=Cancel]").click(function(){
			$("#formTitle textarea").val("");	
		 });
	
    /**********************************************************************/
		/** Author: Megha Iyer
		* Function: Save story beginning on CreateStory.jsp.
		* 
		*/ 
		$("#formbgn input[value=Save]").click(function(){
			var formbgn = $("#formbgn textarea").val();
			if(formbgn=="")
			  alert("Please enter the beginning of your story!");
			else
			  $.ajax({
				  type: "POST",
				  url: "StoryServlet",
				  data: {storyBegin:formbgn,action:"passbegin"},
				  dataType: "json",
				  success: function(json){
					$("#bgnsuccess").show("slow", function() {
						  $(this).css("color","green");
					      $(this).text("Form beginning saved successfully.");
					    });
					$("#bgnsuccess").hide("slow", function() {
						  $(this).text("");
					    });
					$("#bgnsuccess").show(5,function() {
						  $(this).text("");
					    });
				   },
				   error: function(){
		 			  console.log("fail") ; 
			       },
				 
				});
		     });
			
			$("#formbgn input[value=Cancel]").click(function(){
				$("#formbgn textarea").val("");	
			 });	
		
   /**********************************************************************/	
			/** Author: Megha Iyer
			* Function: Save story middle on CreateStory.jsp.
			* 
			*/ 
			$("#formmiddle input[value=Save]").click(function(){
				var formmiddle = $("#formmiddle textarea").val();
				if(formmiddle=="")
					 alert("Please enter the middle of your story!");
				else
				  $.ajax({
					  type: "POST",
					  url: "StoryServlet",
					  data: {storyMiddle:formmiddle,action:"passmiddle"},
					  dataType: "json",
					  success: function(json){
						$("#middlesuccess").show("slow", function() {
							  $(this).css("color","green");
						      $(this).text("Story middle content saved successfully.");
						    });
						$("#middlesuccess").hide("slow", function() {
							  $(this).text("");
						    });
						$("#middlesuccess").show(5,function() {
							  $(this).text("");
						    });
					   },
					   error: function(){
			 			  console.log("fail") ; 
				       },
					 
					});
			     });
				
				$("#formmiddle input[value=Cancel]").click(function(){
					$("#formmiddle textarea").val("");	
				 });	
			
	 /**********************************************************************/	
				/** Author: Megha Iyer
				* Function: Save story end on CreateStory.jsp.
				* 
				*/ 
			
				$("#formend input[value=Save]").click(function(){
					var formend = $("#formend textarea").val();
					if(formend=="")
						 alert("Please enter the end of your story!");
					else
					  $.ajax({
						  type: "POST",
						  url: "StoryServlet",
						  data: {storyEnd:formend,action:"passend"},
						  dataType: "json",
						  success: function(json){
							$("#endsuccess").show("slow", function() {
								  $(this).css("color","green");
							      $(this).text("Story end contents saved successfully.");
							    });
							$("#endsuccess").hide("slow", function() {
								  $(this).text("");
							    });
							$("#endsuccess").show(5,function() {
								  $(this).text("");
							    });
						   },
						   error: function(){
				 			  console.log("fail") ; 
					       },
						 
						});
				     });
					
					$("#formend input[value=Cancel]").click(function(){
						$("#formend textarea").val("");	
					 });	
				
	
								
/**********************************************************************/	
					/** Author: Megha Iyer
					* Function: Save story canvas picture on draw.jsp on the server.
					* 
					*/ 
				   	$("#btndownload").click(function(){
						 //alert("upload canvas jquery");
						 var hiddenid = $("#hiddenstoryid").val();
						 var canvas = document.getElementById('canvas');
						 var dataURL = canvas.toDataURL();
						 console.log(dataURL);
						 
						//alert(dataURL);
						 
						 $.ajax({
							  type: "POST",
							  url: "StoryServlet",
							  data: {action:"storypic", contents: dataURL,storyid:hiddenid},
							  dataType: "json",
							  success: function(json){
								//  alert("Success I am on story jsp page");
								  if (json.storyjsp) {
							            // data.redirect contains the string URL to redirect to
							            window.location.href = "story.jsp";
							      }else{
							            // data.form contains the HTML for the replacement form
							            alert(json.error);
							      }
								  
								  },
							  error: function(){
						 			  console.log("fail") ; 
							       },
								 
								});
					});
				   	
				   	
/**************************************************************************************************************************************************************/
				   	/** Author: Megha Iyer
				   	* Function: Clear canvas function to redraw the canvas on draw.jsp.
				   	* 
				   	*/ 
				   	$("#btnclearcanvas").click(function(){
						// alert("clearcanvas button clicked");
						 var context = canvas.getContext("2d");
						 context.clearRect(0, 0, canvas.width, canvas.height);;
						 newCanvas();
						 
					});

/**************************************************************************************************************************************************************/
				   	
				   	$('textarea').on('input', function() {
				   	  $(this).outerHeight(38).outerHeight(this.scrollHeight);
				   	});
				   	
/**************************************************************************************************************************************************************/
				   	/** Author: Megha Iyer
				   	* Function: Submit and upload the story on server on CreateStory.jsp.
				   	* 
				   	*/ 
				   	
				   	
				   	$("#lnktosubmit").click(function(){
						// alert("submitting a story and on my way to draw jsp");
						 var formTitle = $("#formTitle textarea").val();
						 var formbgn = $("#formbgn textarea").val();
						 var formmiddle =$("#formmiddle textarea").val();
						 var formend = $("#formend textarea").val();
						 
					if (formTitle!="" && formbgn!="" && formmiddle!="" && formend!="")
					  {
						
						 $.ajax({
							  type: "POST",
							  url: "StoryServlet",
							  data: {action:"createStory", storyTitle:formTitle, storyBegin:formbgn, storyMiddle:formmiddle, storyEnd:formend},
							  dataType: "json",
							  success: function(json){
								//alert("Success: I am on draw jsp page") ;
								if (json.redirect) {
					            // data.redirect contains the string URL to redirect to
					            window.location.href = json.redirect;
					            }else{
					            // data.form contains the HTML for the replacement form
					            //alert(json.error);
					            $("#formTitle textarea").val(formTitle);	
					            $("#formbgn textarea").val(formbgn);	
					            $("#formmiddle textarea").val(formmiddle);	
					            $("#formend textarea").val(formend);	
					            }
					           
								},
							   error: function(){
					 			  console.log("fail") ; 
						       },
							 
							});
						
					}else{
						  
						alert("Story title, beginning , middle and end should be filled and saved successfully!")  
					  }
					
					
					});   	

/***********************************************************************************************************************/
				   	/** Author: Megha Iyer
				   	* Function: Validate the form fields on register.jsp.
				   	* 
				   	*/ 
					
function validateForm(){
var flag=true;
if($ (".error").length){
		$(".error").html("");	
		$(".error").hide();
		}	
 var firstname = $("#fnlabel").val();
 var lastname = $("#lnlabel").val();
 var password = $("#pwdlabel").val();
 var email = $("#emaillabel").val();
 var username = $("#unlabel").val(); 
 
 var nameReg = /^[A-Za-z]{1,12}$/;
 var passwordReg =  /^[A-Za-z0-9_]{6,12}$/;
 var emailReg = /(.+)@(.+){2,}\.(.+){2,}/;
 var usernameReg = /^[A-Za-z0-9_]+$/;
 
 //alert ("fn" + nameReg.test(firstname));
 //alert ("ln" + nameReg.test(lastname));
 //alert ("pwd" + passwordReg.test(password));
 //alert ("un" + usernameReg.test(username));
 //alert ("email" + emailReg.test(email));
 
 var inputVal = new Array(firstname, lastname, password, email, username);
 var inputMessage = new Array("firstname", "lastname", "password", "email", "username");
 
 if(inputVal[0] == ""){
	    $('#fnlabel').after('<span class="error" style="color:red"> Please enter your ' + inputMessage[0] + '</span>');
	    flag = false;
	    }else if(!nameReg.test(firstname)){
    	$('#fnlabel').after('<span class="error" style="color:red"> Letters only between 1 to 12 characters</span>');
    	flag = false;
	    }
 
 if(inputVal[1] == ""){
		$('#lnlabel').after('<span class="error" style="color:red"> Please enter your ' + inputMessage[1] + '</span>');
		flag = false;		
        }else if(!nameReg.test(lastname)){
        flag = false;
        $('#lnlabel').after('<span class="error" style="color:red"> Letters only with minimum 1 and maximum 12 characters</span>');
		}
 
 if(inputVal[2] == ""){
       $('#pwdlabel').after('<span class="error" style="color:red"> Please enter your ' + inputMessage[2] + '</span>');
        flag = false;
        }else if(!passwordReg.test(password)){
        flag = false;
        $('#pwdlabel').after('<span class="error" style="color:red"> Letter,numbers and underscore only with minimum 6 and maximum 15 characers.</span>');
		}
 
 if(inputVal[3] == ""){
     $('#emaillabel').after('<span class="error" style="color:red"> Please enter your ' + inputMessage[3] + '</span>');
        flag = false;
        }else if(!emailReg.test(email)){
        flag = false;
		$('#emaillabel').after('<span class="error" style="color:red">Enter a valid email.</span>');
		} 
 
 if(inputVal[4] == ""){
     $('#unlabel').after('<span class="error" style="color:red"> Please enter your ' + inputMessage[4] + '</span>');
        flag = false;
        }else if(!usernameReg.test(username)){
        	flag = false;
        	$('#unlabel').after('<span class="error" style="color:red">Letter,numbers and underscore only with minimum 6 and maximum 12 characers.</span>');
		}  

 return flag;

}

/**************************************************************************************************************/
/** Author: Megha Iyer
* Function: Send register.jsp form fields to server.
* 
*/ 

$("#registerform input[value=Register]").click(function(){
	
	 var flag = validateForm();
	 	 
	 var firstname = $("#fnlabel").val();
	 var lastname = $("#lnlabel").val();
	 var password = $("#pwdlabel").val();
	 var email = $("#emaillabel").val();
	 var username = $("#unlabel").val();
	 
if(firstname!="" && lastname!="" && password!="" && email!="" && username!=""&& flag==true){
		 
	 
     $.ajax({
		  type: "POST",
		  url: "StoryServlet",
		  data: {"firstname":firstname,"lastname":lastname,"password":password,"username":username,"email":email,"action":"register"},
		  dataType: "json",
		  success: function(json){
			//alert("Success")  
			if (json.redirect) {
            // data.redirect contains the string URL to redirect to
            window.location.href = json.redirect;
            }else{
            // data.form contains the HTML for the replacement form
            //alert(json.error);
            }
           
			},
		   error: function(){
 			  console.log("fail") ; 
	       },
		 
		});
 }else{
	 alert("firstname, lastname,username,password,email fields cannot be blank or invalid!")
 }
	 
     });

	
	$("#registerform input[value=Cancel]").click(function(){
		if($(".error").length){
		$(".error").html("");	
		$(".error").hide();
		}
		$("#registerform input[type=text]").val("");	
		$("#registerform input[type=password]").val("");	
		$("#registerform input[type=email]").val("");	
	 });
	
	
/***************************************************************GET REQUEST AJAX CALLS***********************************************************/
	/**
	 * Author: Megha Iyer
	 * Function: Register button click event on Login.jsp page opens a register.jsp page
	 */
		 
$("#gotoregister").click(function(){	
		
		 $.ajax({
			  type: "GET",
			  url: "StoryServlet",
		      data: {"action":"register"},
			  dataType: "json",
			  success: function(json){
				//alert(json.redirect) ;
				if (json.redirect) {
					window.location.href = json.redirect;		
		         }else{
		           // alert(json.error);          
		        }
			    },
			  error: function(){
					  console.log("fail") ; 
		       },
			 
			});
		    
});		 
		 
/******************************************************************************************************/	
	
$("#reglogin").click(function(){	
	
	 $.ajax({
		  type: "GET",
		  url: "StoryServlet",
	      data: {"action":"login"},
		  dataType: "json",
		  success: function(json){
			//alert(json.redirect) ;
			if (json.redirect) {
				window.location.href = json.redirect;		
	         }else{
	            //alert(json.error);          
	        }
		    },
		  error: function(){
				  console.log("fail") ; 
	       },
		 
		});
	    
});
 
/*****************************************************************************************************/

$("#hplogout").click(function(){	
	
	 $.ajax({
		  type: "GET",
		  url: "StoryServlet",
	      data: {"action":"logout"},
		  dataType: "json",
		  success: function(json){
			//alert(json.redirect) ;
			if (json.redirect) {
				window.location.href = json.redirect;		
	         }else{
	          //  alert(json.error);          
	        }
		    },
		  error: function(){
				  console.log("fail") ; 
	       },
		 
		});
	    
});

/*****************************************************************************************************/

$("#hplogin").click(function(){	
	
	 $.ajax({
		  type: "GET",
		  url: "StoryServlet",
	      data: {"action":"login"},
		  dataType: "json",
		  success: function(json){
			//alert(json.redirect) ;
			if (json.redirect) {
				window.location.href = json.redirect;		
	         }else{
	            //alert(json.error);          
	        }
		    },
		  error: function(){
				  console.log("fail") ; 
	       },
		 
		});
	    
});
	 

/*****************************************************************************************************/	 
	 
	 $("#hpregister").click(function(){	
			
		 $.ajax({
			  type: "GET",
			  url: "StoryServlet",
		      data: {"action":"register"},
			  dataType: "json",
			  success: function(json){
				//alert(json.redirect) ;
				if (json.redirect) {
					window.location.href = json.redirect;		
		         }else{
		            //alert(json.error);          
		        }
			    },
			  error: function(){
					  console.log("fail") ; 
		       },
			 
			});
		    
	});
	 

	 /*****************************************************************************************************/
	 $("#hpcreateStory").click(function(){	
			
		 $.ajax({
			  type: "GET",
			  url: "StoryServlet",
		      data: {"action":"createStory"},
			  dataType: "json",
			  success: function(json){
				//alert(json.redirect) ;
				if (json.redirect) {
					window.location.href = json.redirect;		
		         }else{
		            //alert(json.error);          
		        }
			    },
			  error: function(){
					  console.log("fail") ; 
		       },
			 
			});
		    
	}); 
					
	 /*****************************************************************************************************/
	 $("li.homepage").each(function (){
		 $(this).click(function(){
		  var link = $(this).text();
		  //alert(link);
		  var sid = $(this).children("#hpstoryid").val();
				 // alert(sid);
				  $.ajax({
					  type: "GET",
					  url: "StoryServlet",
				      data: {"action":"storyid","id":sid},
					  dataType: "json",
					  success: function(json){
						//alert(json.redirect) ;
						if (json.redirect) {
							$(this).children("#hpgetStory").attr("href", json.redirect);
							//alert($(this).children("#hpgetStory").attr("href"));
							window.location.href = json.redirect;	
							
						}else{
				            //alert(json.error);          
				        }
					    },
					  error: function(){
							  console.log("fail") ; 
				       },
					 
					});
				  
			  });
			 
		  });	  
	
	
 /*****************************************************************************************************/
/*	$("img.homepage").one("load", function(){
		 var pid = $("#hpprofileid").val();  
		 $.ajax({
			  type: "GET",
			  url: "StoryServlet",
		      data: {"action":"image","for":pid},
			  dataType: "json",
			  success: function(json){
				alert(json.filepath) ;
				if (json.filepath) {
					$("#hpprofileimage").attr('src',json.filepath);	
					$("#hpprofileimageheader").attr('src',json.filepath);	
		         }
			    },
			  error: function(){
					  console.log("fail") ; 
		       },
			 
			});
		 
		}).each(function() {
		  if(this.complete) $(this).load();
		});
*/
	 

	 
/*****************************************************************************************************/	 
	$("#pplogout").click(function(){	
		
		 $.ajax({
			  type: "GET",
			  url: "StoryServlet",
		      data: {"action":"logout"},
			  dataType: "json",
			  success: function(json){
				//alert(json.redirect) ;
				if (json.redirect) {
					window.location.href = json.redirect;		
		         }else{
		            //alert(json.error);          
		        }
			    },
			  error: function(){
					  console.log("fail") ; 
		       },
			 
			});
		    
	});

	/*****************************************************************************************************/

	$("#pplogin").click(function(){	
		
		 $.ajax({
			  type: "GET",
			  url: "StoryServlet",
		      data: {"action":"login"},
			  dataType: "json",
			  success: function(json){
				//alert(json.redirect) ;
				if (json.redirect) {
					window.location.href= json.redirect;		
		         }else{
		            //alert(json.error);          
		        }
			    },
			  error: function(){
					  console.log("fail") ; 
		       },
			 
			});
		    
	});
/****************************************************************************************************************/
	
	$("#ppregister").click(function(){	
		
		 $.ajax({
			  type: "GET",
			  url: "StoryServlet",
		      data: {"action":"register"},
			  dataType: "json",
			  success: function(json){
				//alert(json.redirect) ;
				if (json.redirect) {
					window.location.href = json.redirect;		
		         }else{
		          //  alert(json.error);          
		        }
			    },
			  error: function(){
					  console.log("fail") ; 
		       },
			 
			});
		    
});		 
	 
	/*****************************************************************************************************/
	
	$("#pphomepage").click(function(){	
		
		 $.ajax({
			  type: "GET",
			  url: "StoryServlet",
		      data: {"action":"main"},
			  dataType: "json",
			  success: function(json){
				//alert(json.redirect) ;
				if (json.redirect) {
					window.location.href = json.redirect;		
		         }else{
		          //  alert(json.error);          
		        }
			    },
			  error: function(){
					  console.log("fail") ; 
		       },
			 
			});
		    
});		 
	 
	/*****************************************************************************************************/
	$("#cslogout").click(function(){	
		
		 $.ajax({
			  type: "GET",
			  url: "StoryServlet",
		      data: {"action":"logout"},
			  dataType: "json",
			  success: function(json){
				//alert(json.redirect) ;
				if (json.redirect) {
					window.location.href= json.redirect;		
		         }else{
		            //alert(json.error);          
		        }
			    },
			  error: function(){
					  console.log("fail") ; 
		       },
			 
			});
		    
	});

	/*****************************************************************************************************/
	$("#cshomepage").click(function(){	
		
		 $.ajax({
			  type: "GET",
			  url: "StoryServlet",
		      data: {"action":"main"},
			  dataType: "json",
			  success: function(json){
				//alert(json.redirect) ;
				if (json.redirect) {
					window.location.href = json.redirect;		
		         }else{
		           // alert(json.error);          
		        }
			    },
			  error: function(){
					  console.log("fail") ; 
		       },
			 
			});
		    
});		 
	 

 /*****************************************************************************************************/
 $("#dplogout").click(function(){	
		
	 $.ajax({
		  type: "GET",
		  url: "StoryServlet",
	      data: {"action":"logout"},
		  dataType: "json",
		  success: function(json){
			//alert(json.redirect) ;
			if (json.redirect) {
				window.location.href = json.redirect;		
	         }else{
	            //alert(json.error);          
	        }
		    },
		  error: function(){
				  console.log("fail") ; 
	       },
		 
		});
	    
});

/*****************************************************************************************************/
$("#dphomepage").click(function(){	
	
	 $.ajax({
		  type: "GET",
		  url: "StoryServlet",
	      data: {"action":"main"},
		  dataType: "json",
		  success: function(json){
		//	alert(json.redirect) ;
			if (json.redirect) {
				window.location.href= json.redirect;		
	         }else{
	        //    alert(json.error);          
	        }
		    },
		  error: function(){
				  console.log("fail") ; 
	       },
		 
		});
	    
});		 
 
/*****************************************************************************************************/
$("img.drawpage").one("load", function(){
	 var pid = $("#dpprofileid").val();  
	 $.ajax({
		  type: "GET",
		  url: "StoryServlet",
	      data: {"action":"image","for":pid},
		  dataType: "json",
		  success: function(json){
			//alert(json.filepath) ;
			if (json.filepath) {
			   $("#dpprofileimageheader").attr('src',json.filepath);	
	         }
		    },
		  error: function(){
				  console.log("fail") ; 
	       },
		 
		});
	 
	}).each(function() {
	  if(this.complete) $(this).load();
	});
	 		  

/*****************************************************************************************************/
$("#splogout").click(function(){	
		
	 $.ajax({
		  type: "GET",
		  url: "StoryServlet",
	      data: {"action":"logout"},
		  dataType: "json",
		  success: function(json){
			//alert(json.redirect) ;
			if (json.redirect) {
				window.location.href = json.redirect;		
	         }else{
	         //   alert(json.error);          
	        }
		    },
		  error: function(){
				  console.log("fail") ; 
	       },
		 
		});
	    
});

/*****************************************************************************************************/
$("#sphomepage").click(function(){	
	
	 $.ajax({
		  type: "GET",
		  url: "StoryServlet",
	      data: {"action":"main"},
		  dataType: "json",
		  success: function(json){
			//alert(json.redirect) ;
			if (json.redirect) {
				window.location.href = json.redirect;		
	         }else{
	           // alert(json.error);          
	        }
		    },
		  error: function(){
				  console.log("fail") ; 
	       },
		 
		});
	    
});		 

/*****************************************************************************************************/


$("#splogin").click(function(){	
	
	 $.ajax({
		  type: "GET",
		  url: "StoryServlet",
	      data: {"action":"login"},
		  dataType: "json",
		  success: function(json){
			//alert(json.redirect) ;
			if (json.redirect) {
				window.location.href = json.redirect;		
	         }else{
	        //    alert(json.error);          
	        }
		    },
		  error: function(){
				  console.log("fail") ; 
	       },
		 
		});
	    
});
/****************************************************************************************************************/

$("#spregister").click(function(){	
	
	 $.ajax({
		  type: "GET",
		  url: "StoryServlet",
	      data: {"action":"register"},
		  dataType: "json",
		  success: function(json){
			//alert(json.redirect) ;
			if (json.redirect) {
				window.location.href = json.redirect;		
	         }else{
	            //alert(json.error);          
	        }
		    },
		  error: function(){
				  console.log("fail") ; 
	       },
		 
		});
	    
});		 
 
/*****************************************************************************************************/
$("img.profilepicture").each(function (){	
	//e.preventDefault();
	//var imgContainer = $('#imgContainer');
	var profileid = $("#profileid").val();
    var action = "image";
    var self =$(this);
    getprofileimage(profileid, action,self);
    
});

function getprofileimage(  profileid,  action,  self){
	   
    $.ajax({
  	  type: "GET",
  	  url: "StoryServlet",
      data: {'action' :action, 'for':profileid ,'timestamp' : new Date().getTime() },
      dataType: 'json',
      success: function(data){
    	 self.attr('src',data.base64String);
    	 //alert(data.base64String);
    	 self.attr('title','profilepic');
         }
    })	  .done(function() {
    	  //alert("done");
    	  
    	  })
    	  .fail(function() {
    	//    alert( "error" );
    	  })
    	  .always(function() {
    	//    alert( "complete" );
    	  });
    
    
    
}




});