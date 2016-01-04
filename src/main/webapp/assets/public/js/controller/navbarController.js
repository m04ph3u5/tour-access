angular.module('asti.application').controller('navbarCtrl', ['userService', '$state', 'operatorService',
              function navbarCtrl(userService, $state, operatorService){
	
	var self = this;
	self.name = "";
	userService.getName().then(
			function(data){
				self.name = data;
			}
	);
	self.logout = function(){
		userService.logout().then(
				function(data){
					operatorService.reset();
					$state.go("notLogged.login");
				},
				function(reason){
					self.showError = true;
				}
		);
	}
}]);