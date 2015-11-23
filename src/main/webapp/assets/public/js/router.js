angular.module('asti.application')
	.config(['$stateProvider', '$urlRouterProvider', '$locationProvider', '$httpProvider',
	         function($stateProvider, $urlRouterProvider, $locationProvider, $httpProvider){
	
		$stateProvider
		.state('login',{
			templateUrl: 'assets/public/partials/login.html',
			url : '/',
			controller : 'loginCtrl',
			controllerAs : 'login'
		})
		.state('logged',{
			templateUrl: 'assets/public/partials/operator-logged.html',
			abstract: true
		})
		.state('logged.selectPlace',{
			url : '/places',
			views : {
				"body" : {
					templateUrl : "assets/public/partials/selectPlace.html",
					controller : 'selectPlaceCtrl',
					controllerAs : 'selectPlace'
				}
			}
		})
		.state('logged.selectPlace.infoTicket',{
			url : '/info',
			views : {
				"body@logged" : {
					templateUrl : "assets/public/partials/infoTicket.html", 
					controller : 'infoCtrl',
					controllerAs : 'info'
				}
			},
			params : {
				places : null,
				numTicket : null,
			},
			resolve : {
				places : function($stateParams, $q) {
					var deferred = $q.defer();
					if($stateParams.places && $stateParams.places.length>0)
						deferred.resolve($stateParams.places);
					else
						deferred.reject();
					
                    return deferred.promise;
                },
                numTicket : function($stateParams, $q) {
                	var deferred = $q.defer();
					if($stateParams.numTicket && $stateParams.numTicket>0)
						deferred.resolve($stateParams.numTicket);
					else
						deferred.reject();
                    return deferred.promise;                }
			}
		})
		.state('logged.selectPlace.associateTicket',{
			url : '/validate',
			views : {
				"body@logged" : {
					templateUrl : "assets/public/partials/associateTicket.html", 
					controller : 'associateCtrl',
					controllerAs : 'associate'
				}
			},
			params : {
				places : null,
				numTicket : null,
				info : null
			},
			resolve : {
				places : function($stateParams, $q) {
					var deferred = $q.defer();
					if($stateParams.places && $stateParams.places.length>0)
						deferred.resolve($stateParams.places);
					else
						deferred.reject();
					
                    return deferred.promise;
                },
                numTicket : function($stateParams, $q) {
                	var deferred = $q.defer();
					if($stateParams.numTicket && $stateParams.numTicket>0)
						deferred.resolve($stateParams.numTicket);
					else
						deferred.reject();
                    return deferred.promise;                
                },
                info : function($stateParams, $q) {
                	var deferred = $q.defer();
					if($stateParams.info)
						deferred.resolve($stateParams.info);
					else
						deferred.reject();
                    return deferred.promise;                
                }
			}
		})
		
		$urlRouterProvider.otherwise(function($injector,$location){
			var state = $injector.get('$state');
			state.go('login');
		});
	  
		$locationProvider.html5Mode(true);
	}])
	.run(function ($rootScope, $stateParams, $state) {
		
		$rootScope.$state = $state;
        $rootScope.$stateParams = $stateParams;
        
        $rootScope.$on('$stateChangeError', 
   			 function(event, toState, toParams, fromState, fromParams, error){
   		 		event.preventDefault();	
   		 		console.log("RESOLVE FAILED");
   		 		$state.go("logged.selectPlace");
   	 });	
        
    });                                

	
