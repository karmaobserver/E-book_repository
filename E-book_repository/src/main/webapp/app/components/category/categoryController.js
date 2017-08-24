(function() {
	'use strict';

	angular
		.module('ebookApp')
		.controller('CategoryCtrl', CategoryCtrl);

	CategoryCtrl.$inject = ['$scope', '$rootScope', 'CategoryService', 'localStorageService' ];
	function CategoryCtrl($scope, $rootScope, CategoryService, localStorageService) {
		var vm = this;

		vm.isEditing = false;

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
			/*vm.hasError = false;
			vm.errors = [];	*/	
		};  


		vm.modiflyCategory = function(categoryId, categoryName) {
			vm.isEditing = false;
			console.log("CATEGORIJA JE");
			console.log(vm.categoryName);
			console.log(categoryName);
			var category = {};
		   	category = {name: vm.categoryName, categoryId: categoryId};

			CategoryService.modifyCategory(category)
						.then(function(response) {
							console.log('Modificated category');
							console.log(response.data);
							CategoryService.getAllCategories().then(function(response) {
					        	console.log(response.data);
					        	vm.categories = response.data;
					        });
							
							
						})
						.catch(function (response) {
							console.log('dOSLO DO CATCH');
							$rootScope.error = response.data.msg;
						});
		}       
		 	
	}
})();