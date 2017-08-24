(function() {
	'use strict';

	angular
		.module('ebookApp')
		.controller('UserCtrl', UserCtrl);

	UserCtrl.$inject = ['$scope', '$rootScope', 'UserService', 'localStorageService' ];
	function UserCtrl($scope, $rootScope, UserService, localStorageService) {
		var vm = this;

		vm.userType = "administrator";

		UserService.getAllUsers().then(function(response) {
        	console.log(response.data);
        	vm.users = response.data;
        });         
		 	
	}
})();