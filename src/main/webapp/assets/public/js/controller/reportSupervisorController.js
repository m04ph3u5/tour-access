angular.module('asti.supervisor').controller('reportSupervisorCtrl', ['$state', 'apiService',
              function reportSupervisorCtrl($state, apiService){
	
	var self = this;
	
	self.report = {};
	self.report.baseTicketUrl = "report/tickets";
	self.report.baseEntranceUrl = "report/entrances";
	self.report.baseEnvironmentUrl = "report/environment";

	self.dateStartOptions = {
			"regional" : "it",
			"minDate" : new Date(2015,11,1,0,0,0,0),
		    "numberOfMonths": 1
	};
	self.dateEndOptions = {
			"regional" : "it",
			"minDate" : new Date(2015,11,1,0,0,0,0),
            "numberOfMonths": 1
	};
	
	self.setDate = function(a){
		console.log("setDate");
		self.dateEndOptions = {
				"regional" : "it",
				"minDate" : a,
	            "numberOfMonths": 1
		};	
		self.report.ticketUrl = self.report.baseTicketUrl+'?start='+self.dateStart.getTime()+'&end='+self.dateEnd.getTime();
		self.report.entranceUrl = self.report.baseEntranceUrl+'?start='+self.dateStart.getTime()+'&end='+self.dateEnd.getTime();
		self.report.environmentUrl = self.report.baseEnvironmentUrl+'?start='+self.dateStart.getTime()+'&end='+self.dateEnd.getTime();
	}
	self.period = "0";
	self.dateEnd = new Date();
	self.dateStart = angular.copy(self.dateEnd);
	self.report.ticketUrl = self.report.baseTicketUrl+'?start='+self.dateStart.getTime()+'&end='+self.dateEnd.getTime();
	self.report.entranceUrl = self.report.baseEntranceUrl+'?start='+self.dateStart.getTime()+'&end='+self.dateEnd.getTime();
	self.report.environmentUrl = self.report.baseEnvironmentUrl+'?start='+self.dateStart.getTime()+'&end='+self.dateEnd.getTime();

	var monthAgo = angular.copy(self.dateEnd);
	monthAgo.addDays(-30);
	
	
	self.updateData = function(){
		switch(self.period){
		case "0":{
			self.periodString = "oggi";
			break;
		}
		case "1":{
			self.periodString = "ultima settimana";
			break;
		}
		case "2": {
			self.periodString = "ultimo mese";
			break;
		}
		case "3": {
			self.periodString = "ultimi tre mesi";
			break;
		}
		case "4": {
			self.periodString="dal "+$filter('date')(self.dateStart, "d MMMM")+" al "+$filter('date')(self.dateEnd, "d MMMM");
			break;
		}
		}
	}
	
	self.periodString = "oggi";
	self.selectPeriod = function(){
		if(self.period!="4"){
			self.dateEnd = new Date();
			self.dateStart = angular.copy(self.dateEnd);
		
			if(self.period=="1"){
				self.dateStart.addDays(-7);
			}else if(self.period=="2"){
				self.dateStart.addDays(-30);
			}else if(self.period=="3"){
				self.dateStart.addDays(-90);
			}
		}
		
		self.report.ticketUrl = self.report.baseTicketUrl+'?start='+self.dateStart.getTime()+'&end='+self.dateEnd.getTime();
		self.report.entranceUrl = self.report.baseEntranceUrl+'?start='+self.dateStart.getTime()+'&end='+self.dateEnd.getTime();
		self.report.environmentUrl = self.report.baseEnvironmentUrl+'?start='+self.dateStart.getTime()+'&end='+self.dateEnd.getTime();
	}
	
	self.updateData();
}]);