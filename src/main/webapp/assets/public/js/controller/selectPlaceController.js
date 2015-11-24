angular.module('asti.application').controller('selectPlaceCtrl', [ 'apiService', '$state', 'operatorService',
              function selectPlaceCtrl(apiService, $state, operatorService){
	
	var self = this;
	self.places = [];
	self.selected = operatorService.getPlaces();
	if(!self.selected)
		self.selected = new Array();
	
	self.numFullTicket = operatorService.getNumTickets();
	if(!self.numFullTicket)
		self.numFullTicket=1;
	self.t = "";
	
	self.tickets = new Array();
	
	self.toInfo = function(){
		if(self.selected.length>0 && self.numFullTicket>0){
			operatorService.setPlaces(self.selected);
			operatorService.setNumTickets(self.numFullTicket);
			$state.go(".infoTicket");
		}
	}
	

	apiService.getPlacesToSell().then(
			function(response){
				self.places = response;
				console.log(self.places);
				for(var i=0; self.places && i<self.places.length; i++){
					for(var j=0; j<self.selected.length; j++){
						if(self.places[i].id==self.selected[j].id){
							self.places[i].selected = true;
						}
					}
				}
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