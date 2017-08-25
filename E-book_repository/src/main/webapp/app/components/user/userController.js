(function() {
	'use strict';

	angular
		.module('ebookApp')
		.controller('UserCtrl', UserCtrl);

	UserCtrl.$inject = ['$scope', '$rootScope', 'UserService', 'localStorageService', '$state', 'CategoryService' ];
	function UserCtrl($scope, $rootScope, UserService, localStorageService, $state, CategoryService) {
		var vm = this;

		vm.isEditing = false;
		vm.hasAddError = false;
		vm.hasEditError = false;

		//made userTypes
		var UserType = function(type){
		   this.type = type;
		};
		var subscriber = new UserType('subscriber');
		var administrator = new UserType('administrator');
		vm.userTypes = [];
		vm.userTypes.push(subscriber);
		vm.userTypes.push(administrator);
		console.log(vm.userTypes);


		UserService.getAllUsers().then(function(response) {
        	console.log(response.data);
        	vm.users = response.data;
        });

        CategoryService.getAllCategories().then(function(response) {
        	console.log(response.data);
        	vm.categories = response.data;
        });

        vm.editUser = function(userId) {
			vm.isEditing = true;
			vm.userId = userId;		
		};

		vm.editUserCancel = function() {
			vm.isEditing = false;
			/*vm.hasError = false;
			vm.errors = [];	*/	
		};

		vm.modiflyUserByAdmin = function(userId, usernameOld) {
			vm.isEditing = false;
			console.log("USER JE");
			console.log(vm.username);
			console.log("OLD USERNAME: " + usernameOld)

			var user = {};
			if (vm.username === usernameOld) {
				console.log("SAME USERNAME");
				user = {firstName: vm.userFirstName, lastName: vm.userLastName, userId: userId, categoryId: vm.categorySelected, password: vm.userPassword, userType: vm.userTypeSelected, username: "sameUsername"};
			} else {
		   		user = {firstName: vm.userFirstName, lastName: vm.userLastName, userId: userId, categoryId: vm.categorySelected, password: vm.userPassword, userType: vm.userTypeSelected, username: vm.username};
			}
			UserService.modiflyUserByAdmin(user)
						.then(function(response) {
							console.log('Modificated user');
							console.log(response.data);
								if (response.status == 409) {
					   				vm.hasEditError = true;
					   				vm.error = "User with that username already exists!";
					   			} else if (response.status == 406) {
					   				vm.hasEditError = true;
					   				vm.error = "Username can not be empty and need to be unique!";
					   			} else {
									UserService.getAllUsers().then(function(response) {
							        	console.log(response.data);
							        	vm.users = response.data;
					      			 });
								}
							
							
						})
						.catch(function (response) {
							console.log('dOSLO DO CATCH');
						});
		} 

		vm.addUser = function() {
			$state.go('userAdd');
		}
		vm.addNewUserCancel = function() {
			$state.go('users');
		}

		vm.addNewUser = function() {

			if (!vm.newUsername) {	//if string is empty
				vm.hasAddError = true;
				vm.error = "Username can not be empty!";
			} else if (!vm.newUserTypeSelected) {
				vm.hasAddError = true;
				vm.error = "User Type need to be selected!";
			} else {
				var newUser = {};
				newUser = {firstName: vm.newFirstName, lastName: vm.newLastName, userId: 0, categoryId: vm.newCategorySelected, password: vm.newPassword, userType: vm.newUserTypeSelected, username: vm.newUsername};
				UserService.addNewUser(newUser)
						.then(function(response) {
							if (response.status == 409) {
					   				vm.hasAddError = true;
					   				vm.error = "User with that username already exists!";
					   		} else {
								console.log('Added new user');
								console.log(response.data);
								$state.go('users');
							}
						})
						.catch(function (response) {
							console.log('dOSLO DO CATCH');
						});

			}
			
		}

		vm.closeAddAlert = function() {
			vm.hasAddError = false;
			vm.hasEditError = false;
		}               
		 	
	}
})();