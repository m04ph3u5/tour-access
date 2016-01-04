angular.module('asti.application').controller('loginOperatorCtrl', ['userService', '$state',
              function loginOperatorCtrl(userService, $state){
	
	var self = this;
	self.usernameInserted = false;
	self.showError = false;
	self.username="";
	self.password;
	

	self.postUsername = function(){
		userService.getNameFromUsername(self.username).then(
				function(data){
					console.log(data);
					self.usernameInserted = true;
					self.name = data.name;
					self.showError = false;
					
				},
				function(reason){
					self.showError = true;
					self.username = "";
					buffer.splice(0, buffer.length);
				}
		);
	}
	
	self.login = function(){
		userService.login(self.username, self.password).then(
				function(data){
					$state.go("logged.selectPlace");
				},
				function(reason){
					self.showError = true;
					self.password="";
				}
		);
	}
	
	

	
	var buffer = new Array();
	self.keyPress = function(event){
		event.preventDefault();
		console.log("KEYPRESS");
		console.log(event);
		if(event.keyCode!=13){
			buffer.push(event.keyCode);
		}else{
			elaborateBuffer();
		}
	}
	
	var validateUsername = function(){
		console.log("validate username ...");
		console.log(self.username);
		var regex = /^[0-9]{10}$/;
		return regex.test(self.username);
	}	
	
	var elaborateBuffer = function(){
		console.log("ELABORATE");
		var codeRead = new Array();
		for(var i=0; i<buffer.length; i++){
			if(buffer[i]!=18){
				codeRead.push(buffer[i])
			}else{
				if(codeRead.length>0){
					var n=0;
					n=(10*(codeRead[2]-96))+(codeRead[3]-96);
					self.username+=n-48;
					codeRead.splice(0,codeRead.length);
				}
			}
		}
		n=(10*(codeRead[2]-96))+(codeRead[3]-96);
		self.username+=n-48;
		
		
		
		if(validateUsername())
			self.postUsername();
		else{
			self.username="";
			buffer.splice(0,buffer.length);
			alert("Si e' verificato un errore. Ti preghiamo di riprovare");
		}

	}
}]);