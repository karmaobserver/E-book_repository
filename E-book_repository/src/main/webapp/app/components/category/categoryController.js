(function() {
	'use strict';

	angular
		.module('ebookApp')
		.controller('CategoryCtrl', CategoryCtrl);

	CategoryCtrl.$inject = ['$scope', '$rootScope', 'CategoryService', 'localStorageService', '$state' ];
	function CategoryCtrl($scope, $rootScope, CategoryService, localStorageService, $state) {
		var vm = this;

		vm.isEditing = false;

		vm.hasAddError = false;

		CategoryService.getAllCategories().then(function(response) {
        	console.log(response.data);
        	vm.categories = response.data;
        });


        vm.editCategory = function(categoryId) {
			vm.isEditing = true;
			vm.categoryId = categoryId;		
		};

		vm.editCategoryCancel = function() {
			vm.isEditing = false;
		};  


		vm.modiflyCategory = function(categoryId, categoryNameOld) {
			vm.isEditing = false;

			if (!(vm.categoryName === categoryNameOld)) {	//ako je isti naziv kategorije kao i stari preskoci sve
				console.log("CATEGORIJA JE");
				console.log(vm.categoryName);
				var category = {};
			   	category = {name: vm.categoryName, categoryId: categoryId};

				CategoryService.modifyCategory(category)
							.then(function(response) {
								console.log('Modificated category');
								console.log(response.data);
								if (response.status == 409) {
						   				vm.hasEditError = true;
						   				vm.error = "Category with that name already exists!";
						   			} else if (response.status == 406) {
						   				vm.hasEditError = true;
						   				vm.error = "Category can not be empty and need to be unique!";
						   			} else {
										CategoryService.getAllCategories().then(function(response) {
								        	console.log(response.data);
								        	vm.categories = response.data;
								        });
									}			
							})
							.catch(function (response) {
								console.log('dOSLO DO CATCH');
							});
			}
		}

		vm.deleteCategory = function(categoryId) {
			CategoryService.removeCategory(categoryId)
						.then(function(response) {
							console.log('Deleted category');
							CategoryService.getAllCategories().then(function(response) {
					        	console.log(response.data);
					        	vm.categories = response.data;
					        });

						})
						.catch(function(response) {
							console.log('dOSLO DO CATCH');
						});
		}

		vm.addCategory = function() {
			$state.go('categoryAdd');
		}
		vm.addNewCategoryCancel = function() {
			$state.go('categories');
		}

		vm.addNewCategory = function() {

			if (!vm.categoryNewName) {	//if string is empty
				console.log("prazno ime");
				vm.hasAddError = true;
				vm.error = "Category need to have name!";
			} else {
				CategoryService.addNewCategory(vm.categoryNewName)
						.then(function(response) {
							console.log('Added new category');
							console.log(response.data);
							$state.go('categories');
						})
						.catch(function (response) {
							console.log('dOSLO DO CATCH');
						});

			}
			
		}

		vm.closeEditError = function() {
			vm.hasEditError = false;
		}    
		 	
	}
})();