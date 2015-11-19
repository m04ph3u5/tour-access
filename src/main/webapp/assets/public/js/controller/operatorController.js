angular.module('asti.application').controller('operatorCtrl', [ 'apiService',
              function operatorCtrl(apiService){
	
	var self = this;
	self.places = [];
	self.selected = new Array();
	self.phase1 = true;
	self.phase2 = false;
	self.numFullTicket=1;
	self.numHalfTicket=0;
	self.t = "";
	
	self.tickets = new Array();
	
	self.toPhase2 = function(){
		self.phase1 = false;
		self.phase2 = true;
		self.numberTicket = 1;
	}
	
	apiService.getPlaces().then(
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
	
	var toPhase3 = function(){};
	
	self.addTicket = function(){
		if(self.t.length==1){
			self.tickets.push(angular.copy(self.t));
			self.t="";
			self.numberTicket++;
			if(self.numberTicket>(self.numFullTicket+self.numHalfTicket)){
				self.numberTicket--;
				toPhase3();
				console.log("FASE 3");
			}
		}else{
			self.t="";
		}
	}
	
	
}]);