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
				pageTitle : 'AstiMusei - Operatore',
				permissions: {
					only: ['anonymous'],
					redirectTo: 'logged.selectPlace'
				}
			}
		})
		.state('logged',{
			templateUrl: 'assets/public/partials/operator-logged.html',
			abstract: true,
			data : {
				permissions: {
					only: ['operator'],
					redirectTo: 'notLogged.login'
				}
			}
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
				pageTitle : 'AstiMusei - Punti di interesse',
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
				pageTitle : 'AstiMusei - Info acquirenti'
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
				pageTitle : 'AstiMusei - Validazione ticket'
			}
		})
		.state('logged.cancelTicket',{
			url : '/operator/cancelTicket',
			views : {
				"header@logged" : {
					templateUrl : "assets/public/partials/navbar.html",
					controller : 'navbarCtrl',
					controllerAs : 'navbar'
				},
				"body@logged" : {
					templateUrl : "assets/public/partials/cancelTicket.html",
					controller : 'cancelTicketCtrl',
					controllerAs : 'cancelTicket'
				}
			},
			data : {
				pageTitle : 'AstiMusei - Invalida biglietto',
			}
		})
		$urlRouterProvider.otherwise(function($injector,$location){
			var state = $injector.get('$state');
			state.go('notLogged.login');
		});
	  
		$locationProvider.html5Mode(true);
	}])
	.run(function (Permission, $rootScope, $stateParams, $state, $q, userService) {
		
		$rootScope.$state = $state;
        $rootScope.$stateParams = $stateParams;
        
        $rootScope.$on('$stateChangeError', 
   			 function(event, toState, toParams, fromState, fromParams, error){
   		 		event.preventDefault();	
   		 		console.log("RESOLVE FAILED");
   		 		$state.go("logged.selectPlace");
        });	
        
        Permission.defineRole('anonymous',function(stateParams){
    		  console.log("check anonymous");
    		  var deferred = $q.defer();
    		  userService.isLogged().then(
    				  function(data){
    					  deferred.reject();
    				  },
    				  function(reason){
    					  deferred.resolve();
    				  }
    		  );
    		  return deferred.promise;

          });
          
          Permission.defineRole('operator',function(stateParams){
      		  console.log("check operator");
      		  var deferred = $q.defer();
      		  userService.isLogged().then(
      				  function(data){
      					  deferred.resolve();
      				  },
      				  function(reason){
      					  deferred.reject();
      				  }
      		  );
      		  return deferred.promise;

      	  });
        
    });                                

	
