angular.module('asti.application')
	.config(['$stateProvider', '$urlRouterProvider', '$locationProvider', '$httpProvider',
	         function($stateProvider, $urlRouterProvider, $locationProvider, $httpProvider){
	
		$stateProvider
		
		.state('notLogged',{
			templateUrl : 'assets/public/partials/notLoggedTemplate.html',
			abstract: true
		})
		.state('notLogged.login',{
			url : '/operator',
			views : {
				"header" : {
					templateUrl : "assets/public/partials/header.html",
				},
				"body" : {
					templateUrl : "assets/public/partials/loginOperator.html",
					controller : 'loginOperatorCtrl',
					controllerAs : 'login'
				}
			},
			data : {
				pageTitle : 'AstiMusei - Operatore'
			}
		})
		.state('logged',{
			templateUrl: 'assets/public/partials/operator-logged.html',
			abstract: true
		})
		.state('logged.selectPlace',{
			url : '/operator/places',
			views : {
				"header@logged" : {
					templateUrl : "assets/public/partials/navbar.html",
					controller : 'navbarCtrl',
					controllerAs : 'navbar'
				},
				"body@logged" : {
					templateUrl : "assets/public/partials/selectPlace.html",
					controller : 'selectPlaceCtrl',
					controllerAs : 'selectPlace'
				}
			},
			data : {
				pageTitle : 'AstiMusei - Punti di interesse'
			}
		})
		.state('logged.selectPlace.infoTicket',{
			url : '/operator/info',
			views : {
				"body@logged" : {
					templateUrl : "assets/public/partials/infoTicket.html", 
					controller : 'infoCtrl',
					controllerAs : 'info'
				}
			},
			data : {
				pageTitle : 'AstiMusei - Info acquirenti'
			}
		})
		.state('logged.selectPlace.associateTicket',{
			url : '/operator/validate',
			views : {
				"body@logged" : {
					templateUrl : "assets/public/partials/associateTicket.html", 
					controller : 'associateCtrl',
					controllerAs : 'associate'
				}
			},
			data : {
				pageTitle : 'AstiMusei - Validazione ticket'
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

	
