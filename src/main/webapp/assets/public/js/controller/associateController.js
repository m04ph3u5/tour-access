angular.module('asti.application').controller('associateCtrl', ['places', 'numTicket', 'info', '$state', 'apiService',
              function associateCtrl(places, numTicket, info, $state, apiService){
	
	var self = this;
	
	self.places = places;
	self.numTicket = numTicket;
	self.info = info;
	self.t = "";
	self.tickets = new Array();
	self.validation = true;
	self.load = false;
	self.error = false;
	
	self.validateTicket = 1;
	
	self.addTicket = function(){
		if(self.t.length==10){
			self.tickets.push(angular.copy(self.t));
			self.t="";
			self.validateTicket++;

			if(self.validateTicket>(self.numTicket)){
				self.validateTicket--;
				self.validation = false;
				self.load = true;
				
				var ticketRequest = {};
				ticketRequest.ticketNumbers = self.tickets;
				ticketRequest.placesId = new Array();
				if(self.selected){
					for(var i=0; i<self.selected.length; i++){
						ticketRequest.placesId.push(self.selected[i].id);
					}
				}
				ticketRequest.info = info;
				
				apiService.orderTicket(ticketRequest).then(
						function(response){
							self.load = false;
						},
						function(reason){
							self.error=true;
							self.load = false;
							console.log("Impossibile acquistare i biglietti selezionati");
							console.log(reason);
						}
				);
			}
		
		}else{
			self.t="";
		}
	}
}]);