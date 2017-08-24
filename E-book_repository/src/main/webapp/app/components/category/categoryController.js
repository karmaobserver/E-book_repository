(function() {
	'use strict';

	angular
		.module('ebookApp')
		.controller('CategoryCtrl', CategoryCtrl);

	CategoryCtrl.$inject = ['$scope', '$rootScope', 'CategoryService', 'localStorageService' ];
	function CategoryCtrl($scope, $rootScope, CategoryService, localStorageService) {
		var vm = this;

		vm.userType = "administrator";

		CategoryService.getAllCategories().then(function(response) {
        	console.log(response.data);
        	vm.categories = response.data;
        });         
		 	
	}
})();