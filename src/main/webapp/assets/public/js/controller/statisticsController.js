angular.module('asti.supervisor').controller('statisticsCtrl', [ 'apiService', '$state',
              function statisticsCtrl(apiService, $state){
	
	var self = this;
	self.totalInterval="week";
	self.groupInterval="week";
	self.singleInterval="week";
	
	self.totalTicketSelled = 436;
	self.totalIngress = 338;
	
	self.groupTicketSelled = 218;
	self.singleTicketSelled = 218;
	
	
	self.labelsTotal = ["Lunedi", "Martedi", "Mercoledi", "Giovedi", "Venerdi", "Sabato", "Domenica"];
	self.seriesTotal = ['Ticket venduti', 'Ingressi'];
	self.dataTotal = [
	    [65, 59, 80, 81, 56, 55, 40],
	    [28, 48, 40, 19, 86, 27, 90]
	  ];
	
	self.labelsGroup = ["Lunedi", "Martedi", "Mercoledi", "Giovedi", "Venerdi", "Sabato", "Domenica"];
	self.seriesGroup = ['Ticket di gruppo venduti'];
	self.dataGroup = [
	    [15, 9, 13, 27, 6, 25, 14]
	  ];
	
	self.labelsSingle = ["Lunedi", "Martedi", "Mercoledi", "Giovedi", "Venerdi", "Sabato", "Domenica"];
	self.seriesSingle = ['Ticket di gruppo venduti'];
	self.dataSingle = [
	    [15, 9, 13, 27, 6, 25, 14]
	  ];
	
	self.onClickTotal = function (points, evt) {
	    console.log(points, evt);
	  };
	 self.onClickGroup = function (points, evt) {
	    console.log(points, evt);
	 };
	 self.onClickSingle = function (points, evt) {
		    console.log(points, evt);
	 };
		  
	/*
	------------* Funzioni per il ricalcolo dei valori a seconda del nuovo intervallo selezionato  --------------------
    * */	  
	  
	 self.changeIntervalTotal = function(){
		 
		 if(self.totalInterval=="month"){
			 self.labelsTotal = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
			                     "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"]; 
			 self.dataTotal = [
			           	    [35, 89, 80, 81, 56, 55, 80, 66, 59, 33, 81, 26, 55, 40, 35, 89, 80, 81, 56, 55, 80, 66, 59, 33, 81, 26, 55, 40, 88, 54, 33],
			           	    [28, 48, 40, 19, 26, 23, 90, 62, 59, 20, 81, 16, 55, 43, 35, 29, 80, 51, 56, 55, 80, 66, 39, 23, 55, 26, 25, 40, 33, 44, 66]
			           	  ]; 
			 self.totalTicketSelled = 1004;
			 self.totalIngress = 986;
		 }
		 else if(self.totalInterval=="three_month"){
			 self.labelsTotal = ["Settimana 1", "Settimana 2", "Settimana 3", "Settimana 4", "Settimana 5", "Settimana 6", "Settimana 7", "Settimana 8", "Settimana 9", "Settimana 10", "Settimana 11", "Settimana 12"]; 
			 self.dataTotal = [
				           	    [88, 99, 80, 101, 156, 102, 100, 106, 109, 103, 151, 96],
				           	    [68, 78, 60, 119, 126, 93, 108, 98, 100, 102, 99, 119]
				           	  ]; 
			 self.totalTicketSelled = 3004;
			 self.totalIngress = 2986;
		 }
		 else if(self.totalInterval=="year"){
			 self.labelsTotal = ["Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Settembre", "Ottobre", "Novembre", "Dicembre"]; 
			 self.dataTotal = [
			           	    [195, 259, 350, 331, 406, 255, 330, 166, 259, 133, 381, 166],
			           	    [188, 248, 240, 219, 336, 223, 130, 162, 259, 200, 181, 156]
			           	  ]; 
			 self.totalTicketSelled = 10036;
			 self.totalIngress = 10007;
		 }
		 else if(self.totalInterval=="week"){
			 self.labelsTotal = ["Lunedi", "Martedi", "Mercoledi", "Giovedi", "Venerdi", "Sabato", "Domenica"];
				self.dataTotal = [
				    [65, 59, 80, 81, 56, 55, 40],
				    [28, 48, 40, 19, 86, 27, 90]
				  ];
				self.totalTicketSelled = 436;
				self.totalIngress = 338;
		 }
		 
	 }
	 
	 self.changeIntervalGroup = function(){
		 
		 if(self.groupInterval=="month"){
			 self.labelsGroup = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
			                     "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"];
			 self.dataGroup = [
			                   [25, 49, 40, 21, 12, 15, 4, 66, 59, 33, 81, 26, 55, 30, 35, 12, 8, 21, 56, 55, 12, 6, 59, 33, 81, 26, 15, 4, 88, 54, 33]
			           	    ]; 
		 
		 }
		 else if(self.groupInterval=="three_month"){
			 self.labelsGroup = ["Settimana 1", "Settimana 2", "Settimana 3", "Settimana 4", "Settimana 5", "Settimana 6", "Settimana 7", "Settimana 8", "Settimana 9", "Settimana 10", "Settimana 11", "Settimana 12"]; 
			 self.dataGroup = [
				           	    [88, 69, 80, 81, 56, 35, 20, 66, 59, 33, 51, 26]
				           	  ]; 
		 }
		 else if(self.groupInterval=="year"){
			 self.labelsGroup = ["Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Settembre", "Ottobre", "Novembre", "Dicembre"]; 
			 self.dataGroup = [
			           	    [35, 59, 50, 31, 56, 55, 40, 66, 59, 33, 81, 66]
			           	  ]; 
		 }
		 else if(self.totalInterval=="week"){
			 self.labelsGroup = ["Lunedi", "Martedi", "Mercoledi", "Giovedi", "Venerdi", "Sabato", "Domenica"];
			 self.dataGroup = [
			           	    [15, 9, 13, 27, 6, 25, 14]
			           	  ];
		 }
		 
	 }
	 
	 self.changeIntervalSingle = function(){
		 
		 if(self.singleInterval=="month"){
			 self.labelsSingle = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
			                     "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"];
			 self.dataSingle = [
			                   [25, 49, 40, 21, 12, 15, 4, 66, 59, 33, 81, 26, 55, 30, 35, 12, 8, 21, 56, 55, 12, 6, 59, 33, 81, 26, 15, 4, 88, 54, 33]
			           	    ]; 
		 
		 }
		 else if(self.singleInterval=="three_month"){
			 self.labelsSingle = ["Settimana 1", "Settimana 2", "Settimana 3", "Settimana 4", "Settimana 5", "Settimana 6", "Settimana 7", "Settimana 8", "Settimana 9", "Settimana 10", "Settimana 11", "Settimana 12"]; 
			 self.dataSingle = [
				           	    [88, 69, 80, 81, 56, 35, 20, 66, 59, 33, 51, 26]
				           	  ]; 
		 }
		 else if(self.singleInterval=="year"){
			 self.labelsSingle = ["Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Settembre", "Ottobre", "Novembre", "Dicembre"]; 
			 self.dataSingle = [
			           	    [35, 59, 50, 31, 56, 55, 40, 66, 59, 33, 81, 66]
			           	  ]; 
		 }
		 else if(self.singleInterval=="week"){
			 self.labelsSingle = ["Lunedi", "Martedi", "Mercoledi", "Giovedi", "Venerdi", "Sabato", "Domenica"];
			 self.dataSingle = [
			           	    [15, 9, 13, 27, 6, 25, 14]
			           	  ];
		 }
		 
	 }
	
}]);