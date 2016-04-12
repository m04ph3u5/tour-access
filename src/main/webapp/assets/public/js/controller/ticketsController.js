angular.module('asti.supervisor').controller('ticketsCtrl', [ 'apiService', '$state', '$filter',
              function ticketsCtrl(apiService, $state, $filter){
	
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
		self.dateEndOptions = {
				"regional" : "it",
				"minDate" : a,
	            "numberOfMonths": 1
		};	
	}
	
	self.updateData = function(){
		if(self.dateStart>self.dateEnd){
			self.period = "0";
			self.dateEnd = new Date();
			self.dateStart = angular.copy(self.dateEnd);
			self.dateStart.addDays(-7);
		}else{
			apiService.statisticsInfo(self.dateStart.getTime(), self.dateEnd.getTime()).then(
				function(data){
					self.statistics = data;
					self.pie1 = [self.statistics.totSingleTickets, self.statistics.totGroupTickets];
					self.pie1Labels = ["Biglietti singoli", "Biglietti di gruppo"];
					self.pie2 = [self.statistics.totChildren, self.statistics.totElderly];
					self.pie2Labels = ["Gruppi con bambini", "Gruppi con anziani"];
					self.pie3 = [self.statistics.totMale, self.statistics.totFemale];
					self.pie3Labels = ["Uomini", "Donne"];
					self.pie4 = [self.statistics.young, self.statistics.middleAge, self.statistics.elderly];
					self.pie4Labels = ["Giovani", "Mezza età", "Anziani"];
				},
				function(reason){
					console.log(reason);
				}
			);
		}
	}
	
	self.updateSeries = function(){
		apiService.statisticsSeries(self.dateStart.getTime(), self.dateEnd.getTime()).then(
				function(data){
					self.series = data;
					self.lineChart = {};
					self.lineChart.labels = [];
					self.lineChart.data = [];
					var tickets = [];
					var accesses = [];
					self.lineChart.series = ["Ticket venduti", "Ingressi"];
					for (var e in self.series) {
						 self.lineChart.labels.push($filter('date')(e, 'shortDate'));
						 tickets.push(self.series[e].totTickets);
						 accesses.push(self.series[e].totAccesses);
					}
					self.lineChart.data[0] = tickets;
					self.lineChart.data[1] = accesses;
				},
				function(reason){
					console.log(reason);
				}
		);
		
		apiService.poiRank(self.dateStart.getTime(), self.dateEnd.getTime()).then(
				function(data){
					self.rank = {};
					self.rank.labels = [];
					self.rank.data = [];

					var access = [];
					var name = [];
					for(var i=0;i<data.length;i++){
						self.rank.labels[i] = data[i].name;
						access[i] = data[i].totAccess;
					}
					
					self.rank.data[0] = access;
				},
				function(reason){
					console.log(reason);
				}
		);
	}
	
	self.period = "0";
	self.dateEnd = new Date();
	self.dateStart = angular.copy(self.dateEnd);
	self.dateStart.addDays(-7);
	
	self.selectPeriod = function(){
		self.dateEnd = new Date();
		self.dateStart = angular.copy(self.dateEnd);
		if(self.period=="3"){
			;
		}
		else if(self.period=="0"){
			self.dateStart.addDays(-7);
		}else if(self.period=="1"){
			self.dateStart.addDays(-30);
		}else if(self.period=="2"){
			self.dateStart.addDays(-90);
		}

		if(self.period!="3"){
			self.updateData();
			self.updateSeries();
		}
	}
	
	self.updateData();
	self.updateSeries();
	
	self.elaborate = function(){
		self.updateSeries();
		self.updateData();

	}
	
	
	
}]);