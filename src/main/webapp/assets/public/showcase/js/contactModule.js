$(document).ready(function() {
	$("#sendMessage").click(function(){
		var alertName = document.getElementById("alert-name");
		alertName.style.display = 'none';
		var alertEmail = document.getElementById("alert-email");
		alertEmail.style.display = 'none';
		var alertMessage = document.getElementById("alert-message");
		alertMessage.style.display = 'none';
		var alertSuccess = document.getElementById("alert-success");
		alertSuccess.style.display = 'none';
		var alertWarning = document.getElementById("alert-warning");
		alertWarning.style.display = 'none';

		if(validate()){
			var userMessage={};
			userMessage.name = $("#name").val();
			userMessage.email = $("#email").val();
			userMessage.message = $("#message").val();
			$.ajax({
				type: "POST",
				url: "api/v1/contactUs",
				data: JSON.stringify(userMessage),
				contentType: "application/json; charset=utf-8",
				success: function(msg)
				{
					console.log(msg);
					$("#alert-success").alert();
					$("#alert-success").fadeTo(5000, 500).slideUp(500, function(){
						$("#alert-success").alert('close');
					});         	        	
					var nameInput = document.getElementById("name");
					nameInput.value="";
					var emailInput = document.getElementById("email");
					emailInput.value="";
					var messageInput = document.getElementById("message");
					messageInput.value="";
				},
				error: function(reason)
				{
					alertWarning.style.display = 'block';
				}
			});
		}
	});
});
var validate = function(){
	var name = $("#name").val();
	if(!name || name.length<2){
		var alertName = document.getElementById("alert-name");
		alertName.style.display = 'block';
		return false;
	}

	var email = $("#email").val();
	if(!email || !validateEmail(email)){
		var alertEmail = document.getElementById("alert-email");
		alertEmail.style.display = 'block';
		return false;
	}

	var message = $("#message").val();
	if(!message || message.length<3){
		var alertMessage = document.getElementById("alert-message");
		alertMessage.style.display = 'block';
		return false;
	}

	return true;
}

function validateEmail(email) {
	var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return re.test(email);
}