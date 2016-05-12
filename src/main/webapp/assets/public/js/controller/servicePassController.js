angular.module('asti.application').controller('servicePassCtrl', ['apiService', 'operatorService', '$state',
              function servicePassCtrl(apiService, operatorService, $state){
	
	var self = this;
	operatorService.reset();
	self.showButton = false;
	var selected=[];

	apiService.getPlacesToSell().then(
			function(response){
				self.places = response;
				for(var i=0; self.places && i<self.places.length; i++){
					self.places[i].selected = false;
				}
			},
			function(reason){
				console.log("Errore nel recuperare i places");
			}
	);
	
	self.check = function(){
		self.showButton = false;
		for(var i=0; self.places && i<self.places.length; i++){
			self.showButton = self.showButton || self.places[i].selected;
		}
	}
	
	self.goAhead = function(){
		for(var i=0; self.places && i<self.places.length; i++){
			if(self.places[i].selected)
				selected.push(self.places[i]);
		}
		operatorService.setPlaces(selected);
		operatorService.setNumTickets(1);
		operatorService.setTip("SERVICE");
		var info = {};
		info.gender = "male";
		info.age = "middleAge";
		operatorService.setInfo(info);
		$state.go("logged.selectPlace.associateTicket");
	}
}]);