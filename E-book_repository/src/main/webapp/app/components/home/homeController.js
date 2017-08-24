(function() {
	'use strict';

	angular
		.module('ebookApp')
		.controller('HomeCtrl', HomeCtrl);

	HomeCtrl.$inject = ['$scope', '$rootScope', 'HomeService', 'localStorageService' ];
	function HomeCtrl($scope, $rootScope, HomeService, localStorageService) {
		var vm = this;

		vm.userType = "administrator"
		 	
	}
})();