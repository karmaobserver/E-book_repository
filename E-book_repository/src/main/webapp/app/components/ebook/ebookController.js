(function() {
	'use strict';

	angular
		.module('ebookApp')
		.controller('EbookCtrl', EbookCtrl);

	EbookCtrl.$inject = ['$scope', '$rootScope', 'EbookService', 'localStorageService' ];
	function EbookCtrl($scope, $rootScope, EbookService, localStorageService) {
		var vm = this;

		vm.userType = "administrator";

		EbookService.getAllEbooks().then(function(response) {
        	console.log(response.data);
        	vm.ebooks = response.data;
        });         
		 	
	}
})();