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
		.state('operatorDashboard',{
			templateUrl: 'assets/public/partials/operator.html',
			url : '/',
			controller : 'operatorCtrl',
			controllerAs : 'operator'
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
        
    });                                

	
