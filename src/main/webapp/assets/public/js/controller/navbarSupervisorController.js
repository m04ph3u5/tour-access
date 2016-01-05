angular.module('asti.supervisor').controller('navbarSupervisorCtrl', ['userService', '$state', 'apiService',
                                                             function navbarSupervisorCtrl(userService, $state, apiService){

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
					$state.go("notLogged.login");
				},
				function(reason){
					self.showError = true;
				}
		);
	}
}]);