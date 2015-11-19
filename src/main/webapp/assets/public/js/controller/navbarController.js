angular.module('asti.application').controller('navbarCtrl', ['userService', '$state',
              function navbarCtrl(userService, $state){
	
	var self = this;
	
	self.logout = function(){
		userService.logout().then(
				function(data){
					$state.go("login");
				},
				function(reason){
					self.showError = true;
				}
		);
	}
}]);