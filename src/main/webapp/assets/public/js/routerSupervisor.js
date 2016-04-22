angular.module('asti.supervisor')
	.config(['$stateProvider', '$urlRouterProvider', '$locationProvider', '$httpProvider', '$provide',
	         function($stateProvider, $urlRouterProvider, $locationProvider, $httpProvider, $provide){
	
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
				pageTitle : 'AstiMusei - Soci',
				permissions: {
					only: ['anonymous'],
					redirectTo: 'logged.dashboard'
				}
			}
		})
		.state('logged',{
			templateUrl: 'assets/public/partials/operator-logged.html',
			abstract: true,
			data : {
				permissions: {
					only: ['supervisor'],
					redirectTo: 'notLogged.login'
				}
			}
		})
		.state('logged.dashboard',{
			url : '/supervisor/dashboard',
			views : {
				"header@logged" : {
					templateUrl : "assets/public/partials/navbarSupervisor.html",
					controller : 'navbarSupervisorCtrl',
					controllerAs : 'navbar'
				},
				"body" : {
					templateUrl : "assets/public/partials/dashboard.html",
					controller : 'dashboardCtrl',
					controllerAs : 'dashboard'
				}
			},
			data : {
				pageTitle : 'AstiMusei - Soci'
			}
		})
		.state('logged.tickets',{
			url : '/supervisor/tickets',
			views : {
				"header@logged" : {
					templateUrl : "assets/public/partials/navbarSupervisor.html",
					controller : 'navbarSupervisorCtrl',
					controllerAs : 'navbar'
				},
				"body" : {
					templateUrl : "assets/public/partials/tickets.html",
					controller : 'ticketsCtrl',
					controllerAs : 'tickets'
				}
			},
			data : {
				pageTitle : 'AstiMusei - Biglietti'
			}
		})
		.state('logged.mobile',{
			url : '/supervisor/mobile',
			views : {
				"header@logged" : {
					templateUrl : "assets/public/partials/navbarSupervisor.html",
					controller : 'navbarSupervisorCtrl',
					controllerAs : 'navbar'
				},
				"body" : {
					templateUrl : "assets/public/partials/mobile.html",
					controller : 'mobileCtrl',
					controllerAs : 'mobile'
				}
			},
			data : {
				pageTitle : 'AstiMusei - Applicazione mobile'
			}
		})
		.state('logged.sensorLog',{
			url : '/supervisor/sensors/{idSite}',
			views : {
				"header@logged" : {
					templateUrl : "assets/public/partials/navbarSupervisor.html",
					controller : 'navbarSupervisorCtrl',
					controllerAs : 'navbar'
				},
				"body" : {
					templateUrl : "assets/public/partials/sensorLog.html",
					controller : 'sensorLogCtrl',
					controllerAs : 'sensorLog'
				}
			},
			params : {
				idSite : null
			},
			data : {
				pageTitle : 'AstiMusei - Monitoraggio ambientale'
			}
		})
		.state('logged.changePassword',{
			url : '/supervisor/password',
			views : {
				"header@logged" : {
					templateUrl : "assets/public/partials/navbarSupervisor.html",
					controller : 'navbarSupervisorCtrl',
					controllerAs : 'navbar'
				},
				"body" : {
					templateUrl : "assets/public/partials/changePassword.html",
					controller : 'changePasswordSupervisorCtrl',
					controllerAs : 'changePassword'
				}
			},
			data : {
				pageTitle : 'AstiMusei - Modifica password'
			}
		})
	
		
		$urlRouterProvider.otherwise(function($injector,$location){
			var state = $injector.get('$state');
			state.go('login');
		});
	  
		$locationProvider.html5Mode(true);
		
		$provide.decorator('$locale', ['$delegate', function ($delegate) {
	        $delegate.NUMBER_FORMATS.DECIMAL_SEP = '.';
	        return $delegate;
	    }]);
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

	
