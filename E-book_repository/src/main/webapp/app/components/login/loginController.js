(function() {
	'use strict';

	angular
		.module('ebookApp')
		.controller('LoginCtrl', LoginCtrl);

	LoginCtrl.$inject = ['$scope', '$rootScope', 'LoginService', 'localStorageService', '$state', 'UserService' ];
	function LoginCtrl($scope, $rootScope, LoginService, localStorageService, $state, UserService) {
		var vm = this;

      vm.login = function() {

    	localStorageService.set("username", vm.username);
    	localStorageService.set("password", vm.password);

			vm.errors = [];
			vm.hasLoginError = false;
			vm.closeAlert = function (index) {
			    vm.errors.splice(index, 1);
			}
			
    	var credentials = {};
   		credentials = {username: vm.username, password: vm.password};

   		UserService.login(credentials).then(function(response) {
   			if (response.status == 404) {
   				vm.hasLoginError = true;
   				vm.errors.push("Username does not exists!");
   			} else if (response.status == 409) {
   				vm.hasLoginError = true;
   				vm.errors.push("Password and Username do not match!");
   			} else {
   				localStorageService.set("username", response.data.username);
   				localStorageService.set("userType", response.data.userType);
   				localStorageService.set("firstName", response.data.firstName);
   				localStorageService.set("lastName", response.data.lastName);
   				localStorageService.set("password", response.data.password);
   				localStorageService.set("userCategory", response.data.category.name);
          localStorageService.set("userId", response.data.id);
   				$state.go('home');
   			}
   		});    	
    }

	}
})();