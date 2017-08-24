(function() {
	'use strict';

	angular
		.module('ebookApp')
		.controller('NavigationCtrl', NavigationCtrl);

	NavigationCtrl.$inject = ['$scope', '$rootScope', 'localStorageService', '$state'];
	function NavigationCtrl($scope, $rootScope, localStorageService, $state) {
		var vm = this;

		vm.username = localStorageService.get("username");
		vm.firstName = localStorageService.get("firstName");
		vm.lastName = localStorageService.get("lastName");
		vm.encodedToken = localStorageService.get("encodedToken");
		vm.groups = "";
		vm.doktorant =false;
		
		vm.logout = function() {
		 	localStorageService.clearAll();
		 	$state.go('home');           
        }

        vm.login = function() {
        	$state.go('login');
        }
        
	}
})();