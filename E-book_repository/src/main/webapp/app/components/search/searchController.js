(function() {
	'use strict';

	angular
		.module('ebookApp')
		.controller('SearchCtrl', SearchCtrl);

	SearchCtrl.$inject = ['$scope', '$rootScope', 'SearchService', 'localStorageService', 'CategoryService', 'UserService', 'UtilService', '$state' ];
	function SearchCtrl($scope, $rootScope, SearchService, localStorageService, CategoryService, UserService, UtilService, $state) {
		var vm = this;

		vm.showResults = false;
		vm.showSearchSpinner = false;

		vm.searchAttributes = {

			fields : [

				{
					field:"title",
					value:"",
					occur:"MUST",
					type:"Regular"
				},
				{
					field:"author",
					value:"",
					occur:"MUST",
					type:"Regular"
				},
				{
					field:"keyword",
					value:"",
					occur:"MUST",
					type:"Regular"
				},
				{
					field:"language",
					value:"",
					occur:"MUST",
					type:"Regular"
				},
				{
					field:"text",
					value:"",
					occur:"MUST",
					type:"Regular"
				},

			]
		}

		vm.occurances = ['MUST','MUST NOT','SHOULD'];


		var searchAttributesLoad = localStorageService.get('searchAttributes');

		if(searchAttributesLoad){
			vm.searchAttributes = searchAttributesLoad;
		}

		vm.search = function() {

			console.log(vm.searchAttributes);

			vm.showSearchSpinner = true;
			SearchService.search(vm.searchAttributes)
					 	.then(function(response) {
							console.log('Searched ebook');							
					        vm.ebooks = response.data;
					        vm.showSearchSpinner = false;					       				     
						})
						.catch(function(response) {
							console.log('dOSLO DO CATCH');
						});

			vm.showResults = true;
		}

		



	}
})();