angular.module('asti.application').factory('apiService', [ '$http', '$q',
               function userService($http, $q){


	var getPlaces = function(){
		var p = $q.defer();
		$http.get('/api/v1/places').then(
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
		getPlaces : getPlaces
	}

}]);
