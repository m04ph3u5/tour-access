angular.module('asti.application').controller('associateCtrl', ['$state', 'apiService', 'operatorService',
              function associateCtrl($state, apiService, operatorService){
	
	var self = this;
	var buffer = new Array();
	
	self.places = operatorService.getPlaces();
	self.numTicket = operatorService.getNumTickets();
	self.info = operatorService.getInfo();
	self.date = operatorService.getDate();
	self.ticketTipology = operatorService.getTicketTipology();
	
	if(!self.places || !self.numTicket || !self.info || !self.date || !self.ticketTipology)
		$state.go("logged.selectPlace");
	
	self.t = "";
	self.tickets = new Array();
	self.validation = true;
	self.load = false;
	self.error = false;
	
	self.validateTicket = 1;
	
	self.addTicket = function(){
		buffer.splice(0, buffer.length);
		if(self.t.length==10){
			var founded=false;
			for(var i=0; i<self.tickets.length;i++){
				if(self.tickets[i]==self.t){
					founded=true;
					break;
				}
			}

			if(!founded){
				self.tickets.push(angular.copy(self.t));
				self.validateTicket++;
				self.t="";

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
					ticketRequest.tipology = self.ticketTipology;
					
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
								self.errorMessage = reason.data.message;
							}
					);
				}
			}else{
				self.t="";
			}
		
		}else{
			self.t="";
		}
	}
	
//	self.keyPress = function(event){
//		console.log(event);
//		switch(event.key){
//		case "0": {
//			self.t+=event.key;
//			break;
//		}
//		case "1": {
//			self.t+=event.key;
//			break;
//		}
//		case "2": {
//			self.t+=event.key;
//			break;
//		}
//		case "3": {
//			self.t+=event.key;
//			break;
//		}
//		case "4": {
//			self.t+=event.key;
//			break;
//		}
//		case "5": {
//			self.t+=event.key;
//			break;
//		}
//		case "6": {
//			self.t+=event.key;
//			break;
//		}
//		case "7": {
//			self.t+=event.key;
//			break;
//		}
//		case "8": {
//			self.t+=event.key;
//			break;
//		}
//		case "9": {
//			self.t+=event.key;
//			break;
//		}
//		case "Enter" : {
//			if(self.t.length==10)
//				self.addTicket();
//			break;
//		}
//		default: {
//			self.t="";
//		}
//		}
//	}
	
	
	self.keyPress = function(event){
		event.preventDefault();
		console.log("KEYPRESS");
		if(event.keyCode!=13){
			buffer.push(event.keyCode);
		}else{
			elaborateBuffer();
		}
	}
	
	var validateTicket = function(){
		var regex = /^[0-9]{10}$/;
		return regex.test(self.t);
	}	
	
	var elaborateBuffer = function(){
		console.log("ELABORATE");
		var codeRead = new Array();
		for(var i=0; i<buffer.length; i++){
			if(buffer[i]!=18){
				codeRead.push(buffer[i])
			}else{
				if(codeRead.length>0){
					var n=0;
					n=(10*(codeRead[2]-96))+(codeRead[3]-96);
					self.t+=n-48;
					codeRead.splice(0,codeRead.length);
				}
			}
		}
		n=(10*(codeRead[2]-96))+(codeRead[3]-96);
		self.t+=n-48;
		
		console.log(self.t);
		if(validateTicket())
			self.addTicket();
		else{
			self.t="";
			buffer.splice(0,buffer.length);
			alert("Si e' verificato un errore. Ti preghiamo di riprovare");
		}

	}
	
	
	
}]);