angular.module('asti.supervisor').controller('mobileCtrl', ['$state', 'apiService', 'userService',
                                                            function mobileCtrl($state, apiService, userService){

	var self = this;
	self.accessInterval="week";
	self.top10DevicesInterval="week";
	self.infoInterval = "week"
	self.info = "settimanale";
		
	self.top10labels = ["ID8", "ID3", "ID4", "ID6", "ID2", "ID10", "ID9", "ID5", "ID7", "ID1",];
	self.top10series = ['Top 10 device per accessi'];
	self.top10data = [
	                  [1120, 1003, 950, 500, 440, 430, 429, 300, 100, 99]
	                  ];

	self.accessLabels = ["Lunedi", "Martedi", "Mercoledi", "Giovedi", "Venerdi", "Sabato", "Domenica"];
	self.accessSeries = ['Numero di accessi'];
	self.accessData = [
	                   [1120, 1003, 1950, 2500, 1440, 2430, 1429]
	                   ];

	var date = new Date();
	date.setDate(date.getDate() - 7);
	apiService.dashboardInfo(date.getTime()).then(
			function(data){
				self.infoData = data;
			},
			function(reason){
				console.log(reason);
			}
	);
	
	
	self.changeIntervalAccess = function(){

		if(self.accessInterval=="month"){
			self.accessLabels = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
			                     "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"]; 
			self.accessData = [
			                   [1135, 989, 180, 981, 2256, 155, 380, 566, 859, 2233, 1181, 926, 1155, 2240, 1635, 1389, 1810, 381, 356, 355, 580, 566, 59, 1353, 2181, 226, 2255, 1440, 1288, 1054, 933],
			]; 
		
		}
		else if(self.accessInterval=="three_month"){
			self.accessLabels = ["Settimana 1", "Settimana 2", "Settimana 3", "Settimana 4", "Settimana 5", "Settimana 6", "Settimana 7", "Settimana 8", "Settimana 9", "Settimana 10", "Settimana 11", "Settimana 12"]; 
			self.accessData = [
			                   [2818, 3919, 1810, 2011, 3516, 4012, 5010, 5016, 6019, 7013, 8511, 3216],
			                   ]; 
		
		}
		else if(self.accessInterval=="year"){
			self.accessLabels = ["Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Settembre", "Ottobre", "Novembre", "Dicembre"]; 
			self.accessData = [
			                   [10095, 12259, 22250, 33331, 22406, 44255, 11330, 15166, 33259, 23133, 22381, 20166],
			                   ]; 
			self.totalTicketSelled = 10036;
			self.totalIngress = 10007;
		}
		else if(self.accessInterval=="week"){
			self.accessLabels = ["Lunedi", "Martedi", "Mercoledi", "Giovedi", "Venerdi", "Sabato", "Domenica"];
			self.accessData = [
			                   [1120, 1003, 1950, 2500, 1440, 2430, 1429]
			                   ];

		}

	}
	
	
	self.changeIntervalInfo = function(){
		if(self.infoInterval=="month"){
			self.info="mensile";
			var date = new Date();
			date.setDate(date.getDate() - 30);
			apiService.dashboardInfo(date.getTime()).then(
					function(data){
						self.infoData = data;
					},
					function(reason){
						console.log(reason);
					}
			);
		}
		else if(self.infoInterval=="three_month"){
			self.info="ultimi tre mesi";
			var date = new Date();
			date.setDate(date.getDate() - 90);
			apiService.dashboardInfo(date.getTime()).then(
					function(data){
						self.infoData = data;
					},
					function(reason){
						console.log(reason);
					}
			);
		}
		else if(self.infoInterval=="year"){
			self.info="annuale";
			var date = new Date();
			date.setDate(date.getDate() - 365);
			apiService.dashboardInfo(date.getTime()).then(
					function(data){
						self.infoData = data;
					},
					function(reason){
						console.log(reason);
					}
			);
		}
		else if(self.infoInterval=="week"){
			self.info="settimanale";
			var date = new Date();
			date.setDate(date.getDate() - 7);
			apiService.dashboardInfo(date.getTime()).then(
					function(data){
						self.infoData = data;
					},
					function(reason){
						console.log(reason);
					}
			);
		}

	}
}]);