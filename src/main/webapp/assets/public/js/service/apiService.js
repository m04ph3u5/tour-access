angular.module('asti.application').factory('apiService', [ '$http', '$q',
               function userService($http, $q){


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
		getPlacesToSell : getPlacesToSell,
		orderTicket : orderTicket
	}

}]);
