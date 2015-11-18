angular.module('asti.application').factory('userService', [ '$http', '$q',
               function userService($http, $q){


	

	var logout = function(){
		var log = $q.defer()
		$http({
			url: '/apiLogout',
			method: 'POST',
			headers: {
				'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
			}
		}).then(
				function(data){
					console.log("logged out");
					log.resolve(data);
				},
				function(reason){
					console.log("Logout failed: "+reason);
					log.reject(reason);
				}
			);
		return log.promise;
	}
	
	var login = function(email, password){
		var log = $q.defer();
		var e=encodeURIComponent(email);
		var p=encodeURIComponent(password);
//		"&_spring_security_remember_me=true"+
		$http({
			url: '/apiLogin',
			method: 'POST',
			data: 'j_username='+e+'&j_password='+p+"&submit=",
			headers: {
				'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
			}
		}).then(
				function(data){
					console.log("Autenticato!")
					log.resolve(data);
				},
				function(reason){
					console.log("Authentication failed: ");
					console.log(reason);
					log.reject(reason);
				}
		);
		return log.promise;
	}
	
	var getNameFromUsername = function(username){
		var p = $q.defer();
		var u=encodeURIComponent(username);
		$http.get('/api/v1/name?username='+u).then(
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
		login: login,
		logout: logout,
		getNameFromUsername : getNameFromUsername
	}

}]);
