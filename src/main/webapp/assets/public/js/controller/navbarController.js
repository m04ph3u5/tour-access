angular.module('asti.application').controller('navbarCtrl', ['userService', '$state', 'operatorService', 'apiService', '$interval', '$scope',
                                                             function navbarCtrl(userService, $state, operatorService, apiService, $interval, $scope){

	var self = this;
	self.name = "";
	self.avialable = true;
	
	self.classBuy="active";
	self.classCancel="";

	var check = function(){
		apiService.checkService().then(
				function(data){
					self.available = true;
				},
				function(reason){
					self.available = false;
				}
		);
	}

	var timer = $interval(check, 30000);

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


	$scope.$on("$destroy", function() {
		if (timer) {
			$interval.cancel(timer);
		}
	});
}]);