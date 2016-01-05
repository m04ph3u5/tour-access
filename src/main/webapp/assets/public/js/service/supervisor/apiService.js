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
	
	var getPlacesToSell = function(){
		var p = $q.defer();
		$http.get('/api/v1/poiToSell').then(
				function(response){
					p.resolve(response.data);
				},
				function(reason){
					p.reject(reason);
				}
		);
		return p.promise;
	}

	var orderTicket = function(ticketRequest){
		var p = $q.defer();
		$http.post('/api/v1/tickets', ticketRequest).then(
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
		getPlacesToSell : getPlacesToSell,
		orderTicket : orderTicket
	}

}]);
