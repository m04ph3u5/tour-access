angular.module('asti.application').controller('operatorCtrl', [ 'apiService',
              function operatorCtrl(apiService){
	
	var self = this;
	self.places = [];
	
	apiService.getPlaces().then(
			function(response){
				self.places = response;
				console.log(self.places);
			},
			function(reason){
				console.log("Errore nel recuperare i places");
			}
	);
	
}]);