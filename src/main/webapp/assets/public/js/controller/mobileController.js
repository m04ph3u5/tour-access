angular.module('asti.supervisor').controller('mobileCtrl', ['$state', 'apiService', 'userService',
                                                            function mobileCtrl($state, apiService, userService){

	var self = this;
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
	
	
	
	self.period = "0";
	self.dateEnd = new Date();
	self.dateStart = angular.copy(self.dateEnd);
	
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

		/*TODO update data*/
		console.log("SELECT PERIOD "+self.period);
	}
	
	/*TODO update data*/

	self.elaborate = function(){
		/*TODO update data*/
		console.log("ELABORATE "+self.period);

	}

}]);