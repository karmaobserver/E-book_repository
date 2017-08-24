(function() {
	'use strict';

	angular
		.module('ebookApp')
		.controller('ProfileCtrl', ProfileCtrl);

	ProfileCtrl.$inject = ['$scope', '$rootScope', 'UserService', 'localStorageService' ];
	function ProfileCtrl($scope, $rootScope, UserService, localStorageService) {
		var vm = this;

		vm.userType = "administrator";

		UserService.getUser(vm.username).then(function(response) {
        	console.log(response.data);
        	vm.user = response.data;
        });         
		 	
	}
})();