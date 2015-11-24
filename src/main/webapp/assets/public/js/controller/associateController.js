angular.module('asti.application').controller('associateCtrl', ['$state', 'apiService', 'operatorService',
              function associateCtrl($state, apiService, operatorService){
	
	var self = this;
	
	self.places = operatorService.getPlaces();
	self.numTicket = operatorService.getNumTickets();
	self.info = operatorService.getInfo();
	self.date = operatorService.getDate();
	
	if(!self.places || !self.numTicket || !self.info || !self.date)
		$state.go("logged.selectPlace");
	
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
				ticketRequest.ticketsNumber = self.tickets;
				ticketRequest.placesId = new Array();
				if(self.places){
					for(var i=0; i<self.places.length; i++){
						ticketRequest.placesId.push(self.places[i].id);
					}
				}
				ticketRequest.info = self.info;
				ticketRequest.startDate = self.date;
				
				apiService.orderTicket(ticketRequest).then(
						function(response){
							self.load = false;
							operatorService.reset();
						},
						function(reason){
							self.error=true;
							self.load = false;
							self.validation = false;
							console.log("Impossibile acquistare i biglietti selezionati");
							console.log(reason);
							operatorService.reset();
						}
				);
			}
		
		}else{
			self.t="";
		}
	}
}]);