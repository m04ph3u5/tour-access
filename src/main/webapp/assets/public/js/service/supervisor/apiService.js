angular.module('asti.supervisor').factory('apiService', [ '$http', '$q',
               function userService($http, $q){


	var validateCredential = function(credential){
		var p = $q.defer();
		$http.post('/api/v1/loginSupervisor', credential).then(
				function(response){
					p.resolve(response.data);
				},
				function(reason){
					p.reject(reason);
				}
		);
		return p.promise;
	}
	
	var dashboardInfo = function(date){
		var p = $q.defer();
		if(!date){
			$http.get('/api/v1/statistics/dashboardInfo').then(
					function(response){
						p.resolve(response.data);
					},
					function(reason){
						p.reject(reason);
					}
			);
		}else{
			$http.get('/api/v1/statistics/dashboardInfo?start='+date).then(
					function(response){
						p.resolve(response.data);
					},
					function(reason){
						p.reject(reason);
					}
			);
		}
		return p.promise;
	}
	
	var statisticsInfo = function(start, end){
		var p = $q.defer();
		$http.get('/api/v1/statistics/statisticsInfo?start='+start+'&end='+end).then(
				function(response){
					p.resolve(response.data);
				},
				function(reason){
					p.reject(reason);
				}
		);
		
		return p.promise;
	}
	
	var statisticsSeries = function(start, end){
		var p = $q.defer();
		$http.get('/api/v1/statistics/statisticsSeries?start='+start+'&end='+end).then(
				function(response){
					p.resolve(response.data);
				},
				function(reason){
					p.reject(reason);
				}
		);
		
		return p.promise;
	}
	
	var poiRank = function(start, end){
		var p = $q.defer();
		$http.get('/api/v1/statistics/poiRank?start='+start+'&end='+end).then(
				function(response){
					p.resolve(response.data);
				},
				function(reason){
					p.reject(reason);
				}
		);
		
		return p.promise;
	}
	
	var appSeries = function(start, end){
		var p = $q.defer();
		$http.get('/api/v1/statistics/appSeries?start='+start+'&end='+end).then(
				function(response){
					p.resolve(response.data);
				},
				function(reason){
					p.reject(reason);
				}
		);
		
		return p.promise;
	}
	
	var appInfo = function(start, end){
		var p = $q.defer();
		$http.get('/api/v1/statistics/appInfo?start='+start+'&end='+end).then(
				function(response){
					p.resolve(response.data);
				},
				function(reason){
					p.reject(reason);
				}
		);
		
		return p.promise;
	}
	
	
	return {
		validateCredential : validateCredential,
		dashboardInfo : dashboardInfo,
		statisticsInfo : statisticsInfo,
		statisticsSeries : statisticsSeries,
		poiRank : poiRank,
		appSeries : appSeries,
		appInfo: appInfo
	}

}]);
