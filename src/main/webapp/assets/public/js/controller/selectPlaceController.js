angular.module('asti.application').controller('selectPlaceCtrl', [ 'apiService', '$state',
              function selectPlaceCtrl(apiService, $state){
	
	var self = this;
	self.places = [];
	self.selected = new Array();
	self.numFullTicket=1;
	self.t = "";
	
	self.tickets = new Array();
	
	self.toInfo = function(){
		if(self.selected.length>0 && self.numFullTicket>0)
			$state.go(".infoTicket", {places: self.selected, numTicket: self.numFullTicket});
	}
	

	apiService.getPlacesToSell().then(
			function(response){
				self.places = response;
				console.log(self.places);
			},
			function(reason){
				console.log("Errore nel recuperare i places");
			}
	);
	
	self.addToSelected = function(place){
		if(self.selected.indexOf(place)==-1){
			self.selected.push(place);
			place.selected = true;
		}
	}
	
	self.removeToSelected = function(place){
		var i = self.selected.indexOf(place);
		if(i!=-1){
			self.selected.splice(i,1);
			place.selected = false;
		}
	}
}]);