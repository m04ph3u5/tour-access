angular.module('asti.application').controller('loginCtrl', ['userService', '$state',
              function loginCtrl(userService, $state){
	
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
	
	self.keyPress = function(event){
		console.log(event);
		switch(event.key){
		case "0": {
			self.username+=event.key;
			break;
		}
		case "1": {
			self.username+=event.key;
			break;
		}
		case "2": {
			self.username+=event.key;
			break;
		}
		case "3": {
			self.username+=event.key;
			break;
		}
		case "4": {
			self.username+=event.key;
			break;
		}
		case "5": {
			self.username+=event.key;
			break;
		}
		case "6": {
			self.username+=event.key;
			break;
		}
		case "7": {
			self.username+=event.key;
			break;
		}
		case "8": {
			self.username+=event.key;
			break;
		}
		case "9": {
			self.username+=event.key;
			break;
		}
		case "Enter" : {
			if(self.username.length==10)
				self.postUsername();
			break;
		}
		default: {
			self.username="";
		}
		}
	}
}]);