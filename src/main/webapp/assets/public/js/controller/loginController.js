angular.module('asti.application').controller('loginCtrl', ['userService', '$state',
              function loginCtrl(userService, $state){
	
	var self = this;
	self.usernameInserted = false;
	self.showError = false;
	self.username="";
	self.password;
	
	/*ADDDD*/

	self.postUsername = function(){
		userService.getNameFromUsername(self.username).then(
				function(data){
					console.log(data);
					self.usernameInserted = true;
					self.name = data.name;
				},
				function(reason){
					self.showError = true;
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
				}
		);
	}
	
	
//	self.keyPress = function(event){
//		console.log(event);
//		switch(event.key){
//		case "0": {
//			self.username+=event.key;
//			break;
//		}
//		case "1": {
//			self.username+=event.key;
//			break;
//		}
//		case "2": {
//			self.username+=event.key;
//			break;
//		}
//		case "3": {
//			self.username+=event.key;
//			break;
//		}
//		case "4": {
//			self.username+=event.key;
//			break;
//		}
//		case "5": {
//			self.username+=event.key;
//			break;
//		}
//		case "6": {
//			self.username+=event.key;
//			break;
//		}
//		case "7": {
//			self.username+=event.key;
//			break;
//		}
//		case "8": {
//			self.username+=event.key;
//			break;
//		}
//		case "9": {
//			self.username+=event.key;
//			break;
//		}
//		case "Enter" : {
//			if(self.username.length==10)
//				self.postUsername();
//			break;
//		}
//		default: {
//			self.username="";
//		}
//		}
//	}
	
	var buffer = new Array();
	self.keyPress = function(event){
		event.preventDefault();
		console.log("KEYPRESS");
		if(event.keyCode!=13){
			buffer.push(event.keyCode);
		}else{
			elaborateBuffer();
		}
	}
	
	var validateUsername = function(){
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