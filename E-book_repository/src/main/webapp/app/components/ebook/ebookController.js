(function() {
	'use strict';

	angular
		.module('ebookApp')
		.controller('EbookCtrl', EbookCtrl);

	EbookCtrl.$inject = ['$scope', '$rootScope', 'EbookService', 'localStorageService', 'CategoryService', 'UserService', 'UtilService', '$state' ];
	function EbookCtrl($scope, $rootScope, EbookService, localStorageService, CategoryService, UserService, UtilService, $state) {
		var vm = this;

		vm.isEditing = false;
		vm.hasAddError = false;
		vm.hasEditError = false;
		vm.file = null;
		vm.showUploadSpinner = false;

		

		vm.userIdLogged = localStorageService.get("userId");

		getAllEbooks();
		getAllUsers();
		getAllLanguages();
		getAllCategories();

    	//ebook by category
        vm.ebookByCategory = function(categoryId) {
        	vm.categoryIdForSearch = categoryId;
        	console.log("CATEGORY ID" + categoryId);
        	if (categoryId == null) {
        		getAllEbooks();
        	} else {
        		EbookService.getAllEbooksByCategory(categoryId).then(function(response) {
	        		vm.ebooks = response.data;
	        	});
        	}
        	
        }

        vm.deleteEbook = function(ebookId) {
			EbookService.removeEbook(ebookId)
						.then(function(response) {
							console.log('Deleted ebook');
							if (vm.categoryIdForSearch == null) {
				        		getAllEbooks();
				        	} else {
				        		EbookService.getAllEbooksByCategory(vm.categoryIdForSearch).then(function(response) {
					        		vm.ebooks = response.data;
					        	});
				        	}

						})
						.catch(function(response) {
							console.log('dOSLO DO CATCH');
						});
		}

        function getAllEbooks() {
		    EbookService.getAllEbooks().then(function(response) {
	        	console.log(response.data);
	        	vm.ebooks = response.data;
	        });
		}

		function getAllUsers() {
			UserService.getAllUsers().then(function(response) {
	        	console.log(response.data);
	        	vm.users = response.data;
	        });
		}

		function getAllLanguages() {
			UtilService.getAllLanguages().then(function(response) {
	        	console.log(response.data);
	        	vm.languages = response.data;
	        });
		}

		function getAllCategories() {
			CategoryService.getAllCategories().then(function(response) {
				console.log(response.data);
				vm.categories = response.data;
			});
		}

		//--------------------------------------------------------Editing and adding part-------------------------------------------------------
		vm.editEbook = function(ebookId) {
			vm.isEditing = true;
			vm.ebookId = ebookId;		
		};

		vm.editEbookCancel = function() {
			vm.isEditing = false;	
		};

		vm.modiflyEbook = function(ebookId) {
			vm.isEditing = false;
			console.log("Ebook JE");
			console.log(vm.name);

			var ebook = {};
			
		   	ebook = {title: vm.title, author: vm.author,  keywords: vm.keywords,  publicationYear: vm.publicationYear, ebookId: ebookId, categoryId: vm.categorySelected, 
		   		languageId: vm.languageSelected, userId: vm.userSelected, fileName: vm.fileName};
			
			EbookService.modiflyEbook(ebook)
						.then(function(response) {
							console.log('Modificated ebook');
							console.log(response.data);
								if (response.status == 406) {
					   				vm.hasEditError = true;
					   				vm.error = "Title and filename can't be empty!";
					   			} else {
					   				if (vm.categoryIdForSearch == null) {
					   					getAllEbooks();
					   				} else {
					   					EbookService.getAllEbooksByCategory(vm.categoryIdForSearch).then(function(response) {
							        		vm.ebooks = response.data;
							        	});
					   				}								
								}						
						})
						.catch(function (response) {
							console.log('dOSLO DO CATCH');
						});
		} 

		vm.addEbook = function() {
			$state.go('ebookAdd');
		}
		vm.addNewEbookCancel = function() {
			$state.go('ebooks');
		}

		
		vm.upload = function(files) {
			console.log(files[0]);

			var formData = new FormData();
			formData.append("file", files[0]);
			vm.file = formData;
			
			console.log(formData);

			vm.showUploadSpinner = true;
			EbookService.upload(formData)
					.then(function(response) {
						console.log('Uploaded ebook');
						console.log(response.data);
						
						vm.ebook = response.data;

						vm.ebook.mime = "application/pdf";
						vm.ebook.fileName = files[0].name.slice(0, -4);
						
						// TODO: you can change these things
						vm.ebook.publicationYear = 1984;
						vm.ebook.language = vm.languages[1];
						vm.ebook.category = vm.categories[1];
						console.log(vm.ebook.category.id);
						vm.showUploadSpinner = false;
								
					})
					.catch(function (response) {
						console.log('dOSLO DO CATCH');
					});

		}

		vm.addNewEbook = function() {

			if (!vm.ebook.title) {	//if string is empty
				vm.hasAddError = true;
				vm.error = "Title can not be empty!";
			} else if (!vm.ebook.fileName) {	//if string is empty
				vm.hasAddError = true;
				vm.error = "File name can not be empty!";
			} else {

				var newEbook = {};
				newEbook = {title: vm.ebook.title, author: vm.ebook.author,  keywords: vm.ebook.keywords,  publicationYear: vm.ebook.publicationYear, categoryId: vm.ebook.category.id, 
		   			languageId: vm.ebook.language.id, userId: vm.userIdLogged, fileName: vm.ebook.fileName, mime: vm.ebook.mime};

				vm.file.append('ebook', new Blob([JSON.stringify(newEbook)], {
					type: "application/json"
				}));

				EbookService.addNewEbook(vm.file)
						.then(function(response) {
							if (response.status == 409) {
					   				vm.hasAddError = true;
					   				vm.error = "File with that name already exists!";
					   		} else {
								console.log('Added new ebook');
								console.log(response.data);
								//$state.go('ebooks');
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