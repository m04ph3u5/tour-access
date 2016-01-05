angular.module('asti.supervisor')
	.config(['$stateProvider', '$urlRouterProvider', '$locationProvider', '$httpProvider',
	         function($stateProvider, $urlRouterProvider, $locationProvider, $httpProvider){
	
		$stateProvider
		
		.state('notLogged',{
			templateUrl : 'assets/public/partials/notLoggedTemplate.html',
			abstract: true
		})
		.state('notLogged.login',{
			url : '/supervisor',
			views : {
				"header" : {
					templateUrl : "assets/public/partials/header.html",
				},
				"body" : {
					templateUrl : "assets/public/partials/loginSupervisor.html",
					controller : 'loginSupervisorCtrl',
					controllerAs : 'login'
				}
			},
			data : {
				pageTitle : 'Asti - Supervisore',
				permissions: {
					only: ['anonymous'],
					redirectTo: 'logged.statistics'
				}
			}
		})
		.state('logged',{
			templateUrl: 'assets/public/partials/operator-logged.html',
			abstract: true
		})
		.state('logged.statistics',{
			url : '/supervisor/statistics',
			views : {
				"header@logged" : {
					templateUrl : "assets/public/partials/navbarSupervisor.html",
					controller : 'navbarSupervisorCtrl',
					controllerAs : 'navbar'
				},
				"body" : {
					templateUrl : "assets/public/partials/statistics.html",
					controller : 'statisticsCtrl',
					controllerAs : 'statistics'
				}
			},
			data : {
				pageTitle : 'Asti - Soci',
				permissions: {
					only: ['supervisor'],
					redirectTo: 'notLogged.login'
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
	.run(function (Permission, $rootScope, $stateParams, $state, userService, $q) {
		
		$rootScope.$state = $state;
        $rootScope.$stateParams = $stateParams;
        
        
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
        
        Permission.defineRole('supervisor',function(stateParams){
    		  console.log("check supervisor");
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

	
