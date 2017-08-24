(function() {
	'use strict';

	angular
		.module('ebookApp')
		.controller('ProfileCtrl', ProfileCtrl);

	ProfileCtrl.$inject = ['$scope', '$rootScope', 'UserService', 'localStorageService', '$window' ];
	function ProfileCtrl($scope, $rootScope, UserService, localStorageService, $window) {
		var vm = this;

		vm.username = localStorageService.get("username");
		vm.firstName = localStorageService.get("firstName");
		vm.lastName = localStorageService.get("lastName");
		vm.userType = localStorageService.get("userType");
		vm.userCategory = localStorageService.get("userCategory");
		vm.password = localStorageService.get("password");  

		vm.isEditing = false;
		vm.isChangingPassword = false;

		vm.errors = [];
		vm.hasError = false;

		vm.closeAlert = function (index) {
		    vm.errors.splice(index, 1);
		}

		vm.editProfile = function() {
			vm.isEditing = true;		
		};

		vm.editProfileCancle = function() {
			vm.isEditing = false;
			vm.isChangingPassword = false;
			vm.hasError = false;
			vm.errors = [];		
		};

		vm.passwordChange = function() {
			vm.isChangingPassword = true;
		}

		vm.modifyProfile = function() {
			vm.isEditing = false;

			if (vm.isChangingPassword) {
				console.log("promena sifre");
				if (vm.passwordNew == vm.passwordConfirm) {
					console.log("sifre se poklapaju");
					vm.isChangingPassword = false;
					vm.hasError = false;
					vm.errors = [];
					vm.password = vm.passwordNew;
					vm.passwordNew = "";
					vm.passwordConfirm = "";
					$window.alert("Password has been changed successfu!");

					var user = {};
		   			user = {username: vm.username, password: vm.password, firstName: vm.firstName, lastName: vm.lastName};

					UserService.modifyUser(user)
								.then(function(response) {
									console.log('Modificated password');
									console.log(response.data);
									localStorageService.set("password", vm.password);
									
								})
								.catch(function (response) {
									console.log('dOSLO DO CATCH');
									$rootScope.error = response.data.msg;
								});

				} else {
					console.log("sifre se NE poklapaju");
					vm.hasError = true;
   					vm.errors.push("Passwords do no match!");
   					vm.passwordNew = "";
					vm.passwordConfirm = "";
				}
			} else {

				var user = {};
		   			user = {username: vm.username, password: vm.password, firstName: vm.firstName, lastName: vm.lastName};

					UserService.modifyUser(user)
								.then(function(response) {
									console.log("Modificated user");
									console.log(response.data);
									localStorageService.set("firstName", vm.firstName);
									localStorageService.set("lastName", vm.lastName);
									
								})
								.catch(function (response) {
									console.log('dOSLO DO CATCH');
									$rootScope.error = response.data.msg;
								});

			}
			
			
		};      
		 	
	}
})();