(function() {
	'use strict';

	angular
		.module('ebookApp')
		.controller('ProfileCtrl', ProfileCtrl);

	ProfileCtrl.$inject = ['$scope', '$rootScope', 'UserService', 'localStorageService', '$window', '$state'];
	function ProfileCtrl($scope, $rootScope, UserService, localStorageService, $window, $state) {
		var vm = this;

		vm.username = localStorageService.get("username");
		vm.firstName = localStorageService.get("firstName");
		vm.lastName = localStorageService.get("lastName");
		vm.userType = localStorageService.get("userType");
		vm.userCategory = localStorageService.get("userCategory");
		vm.password = localStorageService.get("password");  

		vm.isEditing = false;
		vm.hasError = false;
		vm.passwordNew = "";
		 vm.passwordConfirm = "";

		vm.closeEditError = function () {
		    vm.hasError = false;
		}

		vm.editProfile = function() {
			vm.isEditing = true;		
		};

		vm.editProfileCancle = function() {
			vm.isEditing = false;
			vm.hasError = false;		
		};

		vm.passwordChange = function() {
			$state.go('passwordChange');
		}

		vm.modifyProfile = function() {
			vm.isEditing = false;
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
							$rootScope.error = response.data.msg;
						});	
		};  

		vm.profilePasswordChange = function() {
			if (vm.passwordNew == vm.passwordConfirm && vm.passwordNew != "") {
				console.log("passwords do match");
				vm.hasError = false;
				vm.password = vm.passwordNew;
				vm.passwordNew = "";
				vm.passwordConfirm = "";
				var user = {};
	   			user = {username: vm.username, password: vm.password, firstName: vm.firstName, lastName: vm.lastName};

				UserService.modifyUser(user)
							.then(function(response) {
								console.log('Modificated password');
								console.log(response.data);
								localStorageService.set("password", vm.password);
								$window.alert("Password has been changed successfully!");
								$state.go('profile');
							})
							.catch(function (response) {
								$rootScope.error = response.data.msg;
							});
			} else if (vm.passwordNew == vm.passwordConfirm && vm.passwordNew == "") {
				console.log("password can not be empty!");
				vm.hasError = true;
				vm.error = "Password can not be empty!"
			} else {
				console.log("passwords do not match");
				vm.hasError = true;
				vm.error = "Passwords do not match!";
				vm.passwordNew = "";
				vm.passwordConfirm = "";
			}	 	
		}
	}
})();