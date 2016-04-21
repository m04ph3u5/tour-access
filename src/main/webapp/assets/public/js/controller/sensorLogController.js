angular.module('asti.supervisor').controller('sensorLogCtrl', [ 'apiService', '$stateParams', '$filter',
              function sensorLogCtrl(apiService, $stateParams, $filter){
	
	var self = this;
	var idSite = $stateParams.idSite;
	
	var oneDayMillis = 86400000;
	var threeMonthsMillis = 2592000000;
	
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
					console.log(self.info);
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
				self.tempCharts.labels.push($filter('date')(start, 'HH:mm'));
				self.humCharts.labels.push($filter('date')(start, 'HH:mm'));
				start.setHours(start.getHours()+1);
			}
			break;
		}
		case 1: {
			/*Period that has duration greater than 1 day but less than 3 month.
			 * It allows to see daily data granularity*/
			start.setHours(0,0,0,0);
			var end = angular.copy(start);
			while(start<end){
				self.tempCharts.labels.push($filter('date')(start, 'shortDate'));
				self.humCharts.labels.push($filter('date')(start, 'shortDate'));
				start.addDays(1);
			}
			break;
		}
		case 2: {
			start.setHours(0,0,0,0);

			break;
		}
		}
		var start = angular.copy(self.dateStart);
		start.setHours(0,0,0,0);
		
	}
	
	self.tempCharts = {};
	self.humCharts = {};
	var getTimeSeries = function(){
		apiService.environmentSeries(self.dateStart, self.dateEnd, idSite).then(
				function(data){
					var series = data;
					if(series){
						
						self.tempCharts.labels = [];
						self.humCharts.labels = [];
						createLabels();
						self.tempCharts.data = [];
						self.humCharts.data = [];
						
						self.charts = {};
						self.charts.series = [];
						for(var idSonda in series){
							
							if(self.info && self.info.sonde){
								for(var j=0; j<self.info.sonde.length; j++){
									if(self.info.sonde[j].idSonda==idSonda)
										self.charts.series.push(self.info.sonde[j].name);
								}
							}
							for(var e in series[idSonda]){
								
							}
							self.lineCharts[i].data[0] = temp;
							self.lineCharts[i].data[1] = hum;
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