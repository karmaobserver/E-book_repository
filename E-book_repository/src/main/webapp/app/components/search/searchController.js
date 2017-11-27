(function() {
	'use strict';

	angular
		.module('ebookApp')
		.controller('SearchCtrl', SearchCtrl);

	SearchCtrl.$inject = ['$scope', '$rootScope', 'SearchService', 'localStorageService', 'CategoryService', 'UserService', 'UtilService', '$state', 'EbookService', 'FileSaver', 'Blob' ];
	function SearchCtrl($scope, $rootScope, SearchService, localStorageService, CategoryService, UserService, UtilService, $state, EbookService, FileSaver, Blob) {
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

		vm.currentUser = localStorageService.get("currentUser");
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

		//--------------------------------------------------------Download part-------------------------------------------------------

		vm.register = function() {
			//to login state, since we do not need to have register by project specification! (otherwise it should be redirected to register)
			$state.go('login');
		}

		vm.downloadEbook = function(fileName) {

			EbookService.downloadEbook(fileName)
						.then(function(response) {
							console.log('Downloaded ebook');	
							console.log(response);	

							var blob = response.data; 
    						FileSaver.saveAs(blob, fileName);

						})
						.catch(function (response) {
							console.log('dOSLO DO CATCH');
							console.log(response);
						});
		}
	}
})();