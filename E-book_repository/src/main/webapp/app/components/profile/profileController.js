(function() {
	'use strict';

	angular
		.module('ebookApp')
		.controller('ProfileCtrl', ProfileCtrl);

	ProfileCtrl.$inject = ['$scope', '$rootScope', 'UserService', 'localStorageService', '$window', '$state'];
	function ProfileCtrl($scope, $rootScope, UserService, localStorageService, $window, $state) {
		var vm = this;

		vm.currentUser = localStorageService.get("currentUser"); 

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
	   		user = {username: vm.currentUser.username, password: vm.currentUser.password, firstName: vm.currentUser.firstName, lastName: vm.currentUser.lastName};

			UserService.modifyUser(user)
						.then(function(response) {
							console.log("Modificated user");
							console.log(response.data);
							var currentUser = {username: vm.currentUser.username, password: vm.currentUser.password, firstName: vm.currentUser.firstName, lastName: vm.currentUser.lastName,
              					userType: vm.currentUser.userType, userCategory: vm.currentUser.userCategory, userId: vm.currentUser.id}
							localStorageService.set("currentUser", currentUser);
							
						})
						.catch(function (response) {
							$rootScope.error = response.data.msg;
						});	
		};  

		vm.profilePasswordChange = function() {
			if (vm.passwordNew == vm.passwordConfirm && vm.passwordNew != "") {
				console.log("passwords do match");
				vm.hasError = false;
				vm.currentUser.password = vm.passwordNew;
				vm.passwordNew = "";
				vm.passwordConfirm = "";
				var user = {};
	   			user = {username: vm.currentUser.username, password: vm.currentUser.password, firstName: vm.currentUser.firstName, lastName: vm.currentUser.lastName};

				UserService.modifyUser(user)
							.then(function(response) {
								console.log('Modificated password');
								console.log(response.data);
								var currentUser = {username: vm.currentUser.username, password: vm.currentUser.password, firstName: vm.currentUser.firstName, lastName: vm.currentUser.lastName,
              					userType: vm.currentUser.userType, userCategory: vm.currentUser.userCategory, userId: vm.currentUser.id}
								localStorageService.set("currentUser", currentUser);
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