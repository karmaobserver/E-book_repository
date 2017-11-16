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
          if (response.data.category == null) {
            var currentUser = {username: response.data.username, password: response.data.password, firstName: response.data.firstName, lastName: response.data.lastName,
              userType: response.data.userType, userCategory: response.data.category, userId: response.data.id}
          } else {
            var currentUser = {username: response.data.username, password: response.data.password, firstName: response.data.firstName, lastName: response.data.lastName,
              userType: response.data.userType, userCategory: response.data.category.name, userId: response.data.id}
          }
          localStorageService.set("currentUser", currentUser);
   				$state.go('home');
   			}
   		});    	
    }

	}
})();