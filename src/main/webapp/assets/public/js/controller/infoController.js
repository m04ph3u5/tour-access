angular.module('asti.application').controller('infoCtrl', ['$state', 'operatorService',
              function infoCtrl($state, operatorService){
	
	var self = this;
	var info = operatorService.getInfo();
	self.ticketDate = new Date();
	
	if(!info)
		info = {};
	
	
	self.ticketTipology = operatorService.getTicketTipology();
	if(self.ticketTipology == null){
		operatorService.setTicketTipology="DAILY_VISITOR";
		self.ticketTipology = "DAILY_VISITOR";
	}
	


	self.places = operatorService.getPlaces();
	self.numTicket = operatorService.getNumTickets();
	
	if(!self.places || !self.numTicket || self.places.length==0 || self.numTicket==0)
		$state.go("logged.selectPlace");

	self.dateOptions = {
			"regional" : "it",
			"minDate" : "0",
	};
	
	if(!info){
		self.gender = "male";
		self.age = "middleAge";
		
		self.withChildren=false;
		self.withElderly=false;
	}else{
		if(info.gender)
			self.gender = info.gender;
		else
			self.gender = "male";
		if(info.age)
			self.age = info.age;
		else
			self.age = "middleAge";
		
		if(info.withChildren)
			self.withChildren = info.withChildren;
		else
			self.withChildren=false;
		
		if(info.withElderly)
			self.withElderly = info.withElderly;
		else
			self.withElderly=false;
	}
	
	self.next = function(){
		if(self.numTicket==1){
			info.gender = self.gender;
			info.age = self.age;
		}else{
			info.withChildren = self.withChildren;
			info.withElderly = self.withElderly;
		}
		operatorService.setTip(self.ticketTipology);
		
		operatorService.setInfo(info);
		
		
		$state.go("logged.selectPlace.associateTicket");
	}
	
	self.toSelectPlace = function(){
		
		if(self.numTicket==1){
			info.gender = self.gender;
			info.age = self.age;
		}else{
			info.withChildren = self.withChildren;
			info.withElderly = self.withElderly;
		}
		operatorService.setTip(self.ticketTipology);
		
		operatorService.setInfo(info);
		$state.go("logged.selectPlace");
	}
	
	self.remove = function(){
		
		operatorService.reset();
		
		
		$state.go("logged.selectPlace");
	}
	
	
}]);