angular.module('asti.application')
	.config(['$stateProvider', '$urlRouterProvider', '$locationProvider', '$httpProvider',
	         function($stateProvider, $urlRouterProvider, $locationProvider, $httpProvider){
	
		$stateProvider
		.state('login',{
			templateUrl: 'assets/public/partials/login.html',
			url : '/',
			controller : 'loginCtrl',
			controllerAs : 'login',
			data : {
				pageTitle : 'Asti - Login operatore'
			}
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
			},
			data : {
				pageTitle : 'Asti - Punti di interesse'
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
			data : {
				pageTitle : 'Asti - Info acquirenti'
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
			data : {
				pageTitle : 'Asti - Validazione ticket'
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

	
