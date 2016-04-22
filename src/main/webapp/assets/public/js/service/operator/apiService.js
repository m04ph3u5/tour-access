angular.module('asti.application').factory('apiService', [ '$http', '$q',
               function userService($http, $q){


	var validateCredential = function(credential){
		var p = $q.defer();
		$http.post('/api/v1/loginOperator', credential).then(
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

	var checkService = function(){
		var p = $q.defer();
		$http.get('/api/v1/pingService').then(
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
		$http.post('/api/v1/buyTickets', ticketRequest).then(
				function(response){
					p.resolve(response.data);
				},
				function(reason){
					p.reject(reason);
				}
		);
		return p.promise;
	}
	
	var removeTicket = function(ticketId){
		var p = $q.defer();
		$http.delete('/api/v1/deleteTicket/'+ticketId).then(
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
		orderTicket : orderTicket,
		checkService : checkService,
		removeTicket : removeTicket
	}

}]);
