angular.module('asti.application').controller('loginCtrl', ['userService', '$state',
              function loginCtrl(userService, $state){
	
	var self = this;
	self.usernameInserted = false;
	self.showError = false;
	self.username;
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
					$state.go("operatorDashboard");
				},
				function(reason){
					self.showError = true;
				}
		);
	}
}]);