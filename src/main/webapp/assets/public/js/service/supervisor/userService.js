angular.module('asti.supervisor').factory('userService', ['$http', '$q', '$cookies', 'apiService',
                                                            function userService($http, $q, $cookies, apiService){


    var username = null;
    var name = $cookies.get('asti.nameSupervisor');
    console.log(name);
	var encodedCredential = $cookies.get('asti.supervisor');
	if(encodedCredential){
		$http.defaults.headers.common['Authorization'] = 'xBasic ' + encodedCredential;
	}
	
	var isLogged = function(){
		var p = $q.defer();
		if($cookies.get('asti.supervisor'))
			p.resolve();
		else
			p.reject();
		return p.promise;
	}

	var logout = function(){
		var log = $q.defer()
		$cookies.remove('asti.supervisor');
		$cookies.remove('asti.nameSupervisor');
		encodedCredential = null;
		$http.defaults.headers.common['Authorization'] = 'xBasic ' + encodedCredential;
		apiService.validateCredential(encodedCredential).then(
				function(data){
					log.reject();
				},
				function(reason){
					name = null;
					$http.defaults.headers.common['Authorization'] = undefined;
					log.resolve();
				}
		);
		return log.promise;
	}

	var login = function(email, password){
		var log = $q.defer();
		var credential = {
				username : email,
				password : password
		}
		username = email;
		
		apiService.validateCredential(credential).then(
				function(data){
					encodedCredential = Base64.encode(credential.username+":"+credential.password);
					$cookies.put('asti.supervisor',encodedCredential);
					$http.defaults.headers.common['Authorization'] = 'xBasic ' + encodedCredential;
					log.resolve();
				},
				function(reason){
					log.reject();
				}
		);
		return log.promise;
	}

	var getNameFromUsername = function(username){
		var p = $q.defer();
		var u=encodeURIComponent(username);
		$http.get('/api/v1/name?username='+u).then(
				function(response){
					name = response.data;
					$cookies.put('asti.nameSupervisor',name);
					p.resolve(name);
				},
				function(reason){
					p.reject(reason);
				}
		);
		return p.promise;
	}
	
	var getName = function(){
		var p = $q.defer();
		if(name){
			p.resolve(name);
		}
		else{
			var u=encodeURIComponent(username);
			$http.get('/api/v1/name?username='+u).then(
					function(response){
						name = response.data.name;
						$cookies.put('asti.nameSupervisor',name);
						p.resolve(name);
					},
					function(reason){
						p.reject(reason);
					}
			);
		}
		return p.promise;
	}

	var Base64 = {
			// private property
			_keyStr : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",

			// public method for encoding
			encode : function (input) {
				var output = "";
				var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
				var i = 0;

				input = Base64._utf8_encode(input);

				while (i < input.length) {

					chr1 = input.charCodeAt(i++);
					chr2 = input.charCodeAt(i++);
					chr3 = input.charCodeAt(i++);

					enc1 = chr1 >> 2;
					enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
					enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
					enc4 = chr3 & 63;

					if (isNaN(chr2)) {
						enc3 = enc4 = 64;
					} else if (isNaN(chr3)) {
						enc4 = 64;
					}

					output = output +
					Base64._keyStr.charAt(enc1) + Base64._keyStr.charAt(enc2) +
					Base64._keyStr.charAt(enc3) + Base64._keyStr.charAt(enc4);

				}

				return output;
			},

			// public method for decoding
			decode : function (input) {
				var output = "";
				var chr1, chr2, chr3;
				var enc1, enc2, enc3, enc4;
				var i = 0;

				input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

				while (i < input.length) {

					enc1 = Base64._keyStr.indexOf(input.charAt(i++));
					enc2 = Base64._keyStr.indexOf(input.charAt(i++));
					enc3 = Base64._keyStr.indexOf(input.charAt(i++));
					enc4 = Base64._keyStr.indexOf(input.charAt(i++));

					chr1 = (enc1 << 2) | (enc2 >> 4);
					chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
					chr3 = ((enc3 & 3) << 6) | enc4;

					output = output + String.fromCharCode(chr1);

					if (enc3 != 64) {
						output = output + String.fromCharCode(chr2);
					}
					if (enc4 != 64) {
						output = output + String.fromCharCode(chr3);
					}

				}

				output = Base64._utf8_decode(output);

				return output;

			},

			// private method for UTF-8 encoding
			_utf8_encode : function (string) {
				string = string.replace(/\r\n/g,"\n");
				var utftext = "";

				for (var n = 0; n < string.length; n++) {

					var c = string.charCodeAt(n);

					if (c < 128) {
						utftext += String.fromCharCode(c);
					}
					else if((c > 127) && (c < 2048)) {
						utftext += String.fromCharCode((c >> 6) | 192);
						utftext += String.fromCharCode((c & 63) | 128);
					}
					else {
						utftext += String.fromCharCode((c >> 12) | 224);
						utftext += String.fromCharCode(((c >> 6) & 63) | 128);
						utftext += String.fromCharCode((c & 63) | 128);
					}

				}

				return utftext;
			},

			// private method for UTF-8 decoding
			_utf8_decode : function (utftext) {
				var string = "";
				var i = 0;
				var c = c1 = c2 = 0;

				while ( i < utftext.length ) {

					c = utftext.charCodeAt(i);

					if (c < 128) {
						string += String.fromCharCode(c);
						i++;
					}
					else if((c > 191) && (c < 224)) {
						c2 = utftext.charCodeAt(i+1);
						string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
						i += 2;
					}
					else {
						c2 = utftext.charCodeAt(i+1);
						c3 = utftext.charCodeAt(i+2);
						string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
						i += 3;
					}

				}
				return string;
			}
	}



	return {
		login: login,
		logout: logout,
		getNameFromUsername : getNameFromUsername,
		isLogged: isLogged,
		getName : getName
	}

}]);
