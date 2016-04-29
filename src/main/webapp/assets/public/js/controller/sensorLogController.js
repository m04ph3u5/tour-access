angular.module('asti.supervisor').controller('sensorLogCtrl', [ 'apiService', '$stateParams', '$filter',
              function sensorLogCtrl(apiService, $stateParams, $filter){
	
	var self = this;
	var idSite = $stateParams.idSite;
	
	var oneDayMillis = 86400000;
	var threeMonthsMillis = 7776000000;
	
	self.chartOptions = {
	   responsive: false,
	   maintainAspectRatio: false
	}
	
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
		self.dateEnd = a;
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
	var getInfoSite = function(){
		apiService.getInfoSite(self.dateStart, self.dateEnd, idSite).then(
				function(data){
					self.info = data;
					getTimeSeries();
					
				},
				function(reason){
					console.log(reason);
				}
		);
	}
	getInfoSite();
	
	/*Return integer that represents granularity of time series charts.
	 * 0 - hour granularity
	 * 1 - day granularity
	 * 2 - week granularity*/
	var calculateGranularity = function(){
		var diff = self.dateEnd.getTime()-self.dateStart.getTime();
		if(diff<oneDayMillis)
			return 0;
		else if(diff<threeMonthsMillis)
			return 1;
		else
			return 2;
	}
	
	var createLabels = function(){
		
		var granularity = calculateGranularity();
		
		var start = angular.copy(self.dateStart);
		switch(granularity){
		case 0: {
			/*Period of 1 day which allows to see hourly data granularity*/
			start.setHours(0,0,0,0);
			var end = angular.copy(start);
			end.addDays(1);
			while(start<end){
				self.charts.labels.push($filter('date')(start, 'HH:mm'));
				start.setHours(start.getHours()+1);
			}
			break;
		}
		case 1: {
			/*Period that has duration greater than 1 day but less than 3 month.
			 * It allows to see daily data granularity*/
			start.setHours(0,0,0,0);
			var end = angular.copy(self.dateEnd);
			while(start<end){
				self.charts.labels.push($filter('date')(start, 'shortDate'));
				start.addDays(1);
			}
			break;
		}
		case 2: {
			start.setHours(0,0,0,0);
			var end = angular.copy(self.dateEnd);
			while(start<end){
				self.charts.labels.push($filter('date')(start, 'shortDate'));
				start.addDays(1);
			}
			break;
		}
		}
		var start = angular.copy(self.dateStart);
		start.setHours(0,0,0,0);
		
	}
	
//	var calcTime = function (date) {
//
////		var d = new Date();
////	    var utc = d.getTime() + (d.getTimezoneOffset() * 60000);
////
////	    
////	    var nd = new Date(utc + (3600000*d.getTimezoneOffset()));
////
////	    
////	    return nd.toLocaleString();
//		
//	}
	
	self.tempCharts = {};
	self.humCharts = {};
	var getTimeSeries = function(){
		apiService.environmentSeries(self.dateStart, self.dateEnd, idSite).then(
				function(data){
					
					self.series = data;
					
					self.charts = {};
					self.charts.series = [];
					self.charts.labels = [];
					self.charts.idSeries = [];
					self.tempCharts.data = [];
					self.humCharts.data = [];
					createLabels();
					var granularity = calculateGranularity();
					if(self.series){
					
						for(var i=0; self.info && self.info.sonde && i<self.info.sonde.length; i++){
							self.charts.series.push(self.info.sonde[i].name);
							self.charts.idSeries.push(self.info.sonde[i].idSonda);
							self.tempCharts.data[i] = Array.apply(null, Array(self.charts.labels.length)).map(Number.prototype.valueOf,0);
							self.humCharts.data[i] = Array.apply(null, Array(self.charts.labels.length)).map(Number.prototype.valueOf,0);
							
						}
						
						var index = 0;
				
						for(var d in self.series){
							switch(granularity){
								case 0: {
									index = self.charts.labels.indexOf($filter('date')(d, 'HH:mm'));
									
									break;
								}
								case 1: {
									index = self.charts.labels.indexOf($filter('date')(d, 'shortDate'));
									
									break;
								}
								case 2: {
									//TODO weekly granularity
									index = self.charts.labels.indexOf($filter('date')(d, 'shortDate'));
									break;
								}
							}
						
							var list = self.series[d];
							for(var j=0; list && j<list.length; j++){
								var indexSonda = self.charts.idSeries.indexOf(list[j].idSonda);
								self.tempCharts.data[indexSonda][index] = parseFloat($filter('number')(list[j].avgTemp,2));
								self.humCharts.data[indexSonda][index] = parseFloat($filter('number')(list[j].avgHum,2));
							}
						}
					}
				},
				function(reason){
					console.log("Error retreaving time series for sonda ");
				}
		);
	}
	
	self.elaborate = function(){
		switch(self.period){
			//OGGI
			case "0": {
				self.periodString="giornaliere";
				getInfoSite();
				break;
			}
			//SELEZIONA UN GIORNO
			case "1": {
				
				self.periodString="del "+$filter('date')(self.dateStart, "d MMMM");
				getInfoSite();
				break;
			}
			//ULTIMA SETTIMANA
			case "2" : {
				self.periodString="dell'ultima settimana ("+$filter('date')(self.dateStart, "d MMMM")+" - "+$filter('date')(self.dateEnd, "d MMMM")+")";
				getInfoSite();
				break;
			}
			//ULTIMO MESE
			case "3" : {
				self.periodString="dell'ultimo mese ("+$filter('date')(self.dateStart, "d MMMM")+" - "+$filter('date')(self.dateEnd, "d MMMM")+")";
				getInfoSite();
				break;
			}
			case "4" : {
				self.periodString="dal "+$filter('date')(self.dateStart, "d MMMM")+" al "+$filter('date')(self.dateEnd, "d MMMM");
				getInfoSite();
				break;
			}
			
		}

	}

}]);