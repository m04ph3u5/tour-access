angular.module('asti.supervisor').controller('mobileCtrl', ['$state', 'apiService', 'userService', '$filter',
                                                            function mobileCtrl($state, apiService, userService, $filter){

	var self = this;
	
	self.chartOptions = {
			responsive: false,
			maintainAspectRatio: false
	}
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
	}
	
	apiService.dashboardInfo().then(
			function(data){
				self.info = data;
				console.log(self.info);
			},
			function(reason){
				console.log(reason);
			}
	);
	
	self.period = "0";
	self.dateEnd = new Date();
	self.dateStart = angular.copy(self.dateEnd);
	
	var monthAgo = angular.copy(self.dateEnd);
	monthAgo.addDays(-30);
	apiService.appSeries(monthAgo.getTime(), self.dateEnd.getTime()).then(
			function(data){
				console.log(data);
				self.series = data;
				self.lineChart = {};
				self.lineChart.labels = [];
				self.lineChart.data = [];
				var install = [];
				var accesses = [];
				self.lineChart.series = ["Installazioni", "Accessi"];
				for (var e in self.series) {
					 self.lineChart.labels.push($filter('date')(e, 'shortDate'));
					 install.push(self.series[e].totInstall);
					 accesses.push(self.series[e].totAccesses);
				}
				self.lineChart.data[0] = install;
				self.lineChart.data[1] = accesses;
			},
			function(reason){
				console.log(reason);
			}
	);		/*TODO update data*/

	
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
		apiService.appInfo(self.dateStart.getTime(),self.dateEnd.getTime()).then(
				function(data){
					self.table = data;
				},
				function(reason){
					console.log(reason);
				}
		);
	}
	
	self.periodString = "oggi";
	self.selectPeriod = function(){
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
	
	self.elaborate = function(){
		self.updateData();
	}
	
	self.updateData();

}]);