angular.module('asti.application').controller('navbarCtrl', ['userService', '$state', 'operatorService',
              function navbarCtrl(userService, $state, operatorService){
	
	var self = this;
	
	self.logout = function(){
		userService.logout().then(
				function(data){
					operatorService.reset();
					$state.go("login");
				},
				function(reason){
					self.showError = true;
				}
		);
	}
}]);