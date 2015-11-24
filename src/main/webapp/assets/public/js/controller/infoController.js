angular.module('asti.application').controller('infoCtrl', ['$state', 'operatorService',
              function infoCtrl($state, operatorService){
	
	var self = this;
	var info = operatorService.getInfo();
	
	if(!info)
		info = {};
	
	self.ticketDate = operatorService.getDate();
	if(!self.ticketDate)
		self.ticketDate = new Date();



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
		console.log(info);
		operatorService.setInfo(info);
		operatorService.setDate(self.ticketDate);
		$state.go("logged.selectPlace.associateTicket");
	}
	
	
}]);