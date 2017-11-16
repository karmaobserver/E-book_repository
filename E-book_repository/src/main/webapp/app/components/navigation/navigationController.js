(function() {
	'use strict';

	angular
		.module('ebookApp')
		.controller('NavigationCtrl', NavigationCtrl);

	NavigationCtrl.$inject = ['$scope', '$rootScope', 'localStorageService', '$state'];
	function NavigationCtrl($scope, $rootScope, localStorageService, $state) {
		var vm = this;

		vm.currentUser = localStorageService.get("currentUser");

		vm.logout = function() {
			console.log("LOGOUT");
		 	localStorageService.clearAll();
		 	
		 	if ($state.is('home')) {
		 		$state.reload();
		 	} else {
		 		$state.go('home'); 
		 	}	 	         
        }

        vm.login = function() {
        	$state.go('login');
        }
        
	}
})();