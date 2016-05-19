angular.module('asti.application').controller('navbarCtrl', ['userService', '$state', 'operatorService', 'apiService', '$interval', '$scope',
                                                             function navbarCtrl(userService, $state, operatorService, apiService, $interval, $scope){

	var self = this;
	self.name = "";
	self.available = true;
	var numFailedTry=0;
	
	self.classBuy="active";
	self.classCancel="";

	var check = function(){
		apiService.checkService().then(
				function(data){
					numFailedTry=0;
					self.available = true;
				},
				function(reason){
					numFailedTry++;
					if(numFailedTry>1)
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