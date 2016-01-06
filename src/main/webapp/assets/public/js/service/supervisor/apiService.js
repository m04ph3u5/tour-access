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
	
	
	return {
		validateCredential : validateCredential,
		dashboardInfo : dashboardInfo
	}

}]);
