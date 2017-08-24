(function() {
	'use strict';

	angular
		.module('ebookApp')
		.controller('LoginCtrl', LoginCtrl);

	LoginCtrl.$inject = ['$scope', '$rootScope', 'LoginService', 'localStorageService', '$state' ];
	function LoginCtrl($scope, $rootScope, LoginService, localStorageService, $state) {
		var vm = this;

		/*vm.username = "";
		vm.password = "";*/

        vm.login = function() {

           /* var encodedToken = LoginService.encodeUser(vm.username, vm.password);


            LoginService.login(encodedToken, vm.username).then(function(response) {
            	localStorageService.set("encodedToken", encodedToken);
	            localStorageService.set("username", vm.username);
	            localStorageService.set("password", vm.password);
	            localStorageService.set("firstName", response.data.data[0].firstName);
	            localStorageService.set("lastName", response.data.data[0].lastName);
            	$state.go('home');
            });         */
        }

	}
})();