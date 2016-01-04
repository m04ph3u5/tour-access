angular.module('asti.supervisor').controller('loginSupervisorCtrl', ['userService', '$state',
              function loginSupervisorCtrl(userService, $state){
	
	var self = this;
	self.usernameInserted = false;
	self.showError = false;
	self.username="";
	self.password;
	

	
	
	self.login = function(){
		userService.login(self.username, self.password).then(
				function(data){
					
					$state.go("logged.dashboard");
				},
				function(reason){
					self.showError = true;
					self.password="";
				}
		);
	}
	
	
	
	
	
	var validateUsername = function(){
		console.log("validate username ...");
		console.log(self.username);
		var regex = /^[0-9]{10}$/;
		return regex.test(self.username);
	}	
	
	
}]);