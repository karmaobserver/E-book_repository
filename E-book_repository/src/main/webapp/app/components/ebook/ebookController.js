(function() {
	'use strict';

	angular
		.module('ebookApp')
		.controller('EbookCtrl', EbookCtrl);

	EbookCtrl.$inject = ['$scope', '$rootScope', 'EbookService', 'localStorageService', 'CategoryService' ];
	function EbookCtrl($scope, $rootScope, EbookService, localStorageService, CategoryService) {
		var vm = this;

		CategoryService.getAllCategories().then(function(response) {
			console.log(response.data);
			vm.categories = response.data;
		});

		EbookService.getAllEbooks().then(function(response) {
        	console.log(response.data);
        	vm.ebooks = response.data;
        });

         
        vm.ebookByCategory = function(categoryId) {
        	console.log("CATEGORY ID" + categoryId);
        	EbookService.getAllEbooksByCategory(categoryId).then(function(response) {
        		console.log("Dobio listu !!!");
        		vm.ebooks = response.data;
        	});
        }        
		 	
	}
})();