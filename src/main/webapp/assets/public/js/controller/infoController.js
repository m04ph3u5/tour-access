angular.module('asti.application').controller('infoCtrl', ['places', 'numTicket', '$state',
              function infoCtrl(places, numTicket, $state){
	
	var self = this;
	
	self.places = places;
	self.numTicket = numTicket;

	self.ticketDate = new Date();
	self.dateOptions = {
			"regional" : "it",
			"minDate" : "0",
	};
	
	self.gender = "male";
	self.age = "middleAge";
	
	self.withChildren=false;
	self.withElderly=false;
	
	self.next = function(){
		var info = {};
		if(numTicket==1){
			info.gender = self.gender;
			info.age = self.age;
		}else{
			info.withChildren = self.withChildren;
			info.withElderly = self.withElderly;
		}
		console.log(info);
		$state.go("logged.selectPlace.associateTicket", {places: self.places, numTicket: self.numTicket, info: info})
	}
	
	
}]);