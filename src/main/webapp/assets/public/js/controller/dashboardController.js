angular.module('asti.supervisor').controller('dashboardCtrl', ['$state', 'apiService', 'userService',
              function dashboardCtrl($state, apiService, userService){
	
	var self = this;
	userService.getName().then(
			function(data){
				self.name = data;
			}
	);
	self.today = new Date();
	self.info = {};
	
	apiService.dashboardInfo().then(
			function(data){
				self.info = data;
				console.log(self.info);
			},
			function(reason){
				console.log(reason);
			}
	);
	
	
}]);