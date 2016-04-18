angular.module('asti.supervisor').controller('sensorLogCtrl', [ 'apiService', '$stateParams', '$filter',
              function sensorLogCtrl(apiService, $stateParams, $filter){
	
	var self = this;
	var idSite = $stateParams.idSite;
	
	var today = new Date();
	
	self.dateStartOptions = {
			"regional" : "it",
			"minDate" : new Date(2016,0,1,0,0,0,0),
			"maxDate" : today,
		    "numberOfMonths": 1
	};
	self.dateEndOptions = {
			"regional" : "it",
			"minDate" : new Date(2016,0,1,0,0,0,0),
			"maxDate" : today,
            "numberOfMonths": 1
	};
	
	self.setDateStart = function(a){
		self.dateEndOptions = {
				"regional" : "it",
				"minDate" : a,
	            "numberOfMonths": 1
		};	
	}
	
	var resetMinDate =function(){
		self.dateStartOptions = {
				"maxDate" : today
		};
	}
	
	self.setDateEnd = function(a){
		self.dateStartOptions = {
				"regional" : "it",
				"maxDate" : a,
	            "numberOfMonths": 1
		};	
	}
	
	self.setOneDate = function(a){
		self.setEndDate = a;
	}
	
	self.period = "0";
	self.periodString="giornaliere";
	self.showTwoCal = false;
	self.showOneCal = false;
	self.dateStart = angular.copy(today);
	self.dateEnd = self.dateStart;
	self.selectPeriod = function(){
		switch(self.period){
		//OGGI
		case "0": {
			self.showTwoCal = false;
			self.showOneCal = false;
			resetMinDate();
			self.dateStart = angular.copy(today);
			self.dateEnd = self.dateStart;
			break;
		}
		//SELEZIONA UN GIORNO
		case "1": {
			self.showTwoCal = false;
			self.showOneCal = true;
			resetMinDate();
			self.dateStart = angular.copy(today);
			break;
		}
		//ULTIMA SETTIMANA
		case "2" : {
			self.showTwoCal = false;
			self.showOneCal = false;
			resetMinDate();
			self.dateEnd = angular.copy(today);
			self.dateStart = angular.copy(self.dateEnd);
			self.dateStart.addDays(-7);
			break;
		}
		//ULTIMO MESE
		case "3" : {
			self.showTwoCal = false;
			self.showOneCal = false;
			resetMinDate();
			self.dateEnd = angular.copy(today);
			self.dateStart = angular.copy(self.dateEnd);
			self.dateStart.addDays(-30);
			break;
		}
		case "4" : {
			self.showTwoCal = true;
			self.showOneCal = false;
			resetMinDate();
			break;
		}
		}
	}
	
	self.elaborate = function(){
		switch(self.period){
		//OGGI
		case "0": {
			self.periodString="giornaliere";
			break;
		}
		//SELEZIONA UN GIORNO
		case "1": {
			self.periodString="del "+$filter('date')(self.dateStart, "d MMMM");
			break;
		}
		//ULTIMA SETTIMANA
		case "2" : {
			self.periodString="dell'ultima settimana ("+$filter('date')(self.dateStart, "d MMMM")+" - "+$filter('date')(self.dateEnd, "d MMMM")+")";
			break;
		}
		//ULTIMO MESE
		case "3" : {
			self.periodString="dell'ultimo mese ("+$filter('date')(self.dateStart, "d MMMM")+" - "+$filter('date')(self.dateEnd, "d MMMM")+")";
			break;
		}
		case "4" : {
			self.periodString="dal "+$filter('date')(self.dateStart, "d MMMM")+" al "+$filter('date')(self.dateEnd, "d MMMM");
			break;
		}
		}

	}

}]);