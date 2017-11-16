(function() {
	'use strict';

	angular
		.module('ebookApp')
		.controller('EbookCtrl', EbookCtrl);

	EbookCtrl.$inject = ['$scope', '$rootScope', 'EbookService', 'localStorageService', 'CategoryService', 'UserService', 'UtilService', '$state' ];
	function EbookCtrl($scope, $rootScope, EbookService, localStorageService, CategoryService, UserService, UtilService, $state) {
		var vm = this;

		vm.hasAddError = false;
		vm.hasAddSuccess = false;
		vm.file = null;
		vm.showUploadSpinner = false;
		var failUpload = false;
		vm.currentUser = localStorageService.get("currentUser");

		getAllEbooks();
		getAllUsers();
		getAllLanguages();
		getAllCategories();

		//If edit is true, call edit function
		console.log("Ebook id: " + $state.params.ebookId);
		if ($state.params.ebookId != null) {
			editEbookPrepare($state.params.ebookId);
		}

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
	        	//console.log(response.data);
	        	vm.ebooks = response.data;
	        });
		}

		function getAllUsers() {
			UserService.getAllUsers().then(function(response) {
	        	//console.log(response.data);
	        	vm.users = response.data;
	        });
		}

		function getAllLanguages() {
			UtilService.getAllLanguages().then(function(response) {
	        	//console.log(response.data);
	        	vm.languages = response.data;
	        });
		}

		function getAllCategories() {
			CategoryService.getAllCategories().then(function(response) {
				//console.log(response.data);
				vm.categories = response.data;
			});
		}

		//--------------------------------------------------------Editing and adding part-------------------------------------------------------
		vm.editEbook = function(ebookId) {
			$state.go('ebookEdit', { ebookId: ebookId});		 	
		};

		vm.editEbookSave = function() {
			if (!vm.ebook.title) {	//if string is empty
				vm.hasAddError = true;
				vm.error = "Title can not be empty!";
			} else if (!vm.ebook.fileName) {	//if string is empty
				vm.hasAddError = true;
				vm.error = "File name can not be empty!";
			} else {
				var newEbook = {};
				newEbook = {ebookId: vm.ebookId, title: vm.ebook.title, author: vm.ebook.author,  keywords: vm.ebook.keywords,  publicationYear: vm.ebook.publicationYear, categoryId: vm.ebook.category, 
		   			languageId: vm.ebook.language, userId: vm.currentUser.userId, fileName: vm.ebook.fileName, mime: vm.ebook.mime};

	   			if (vm.file == null) {
	   				console.log("VM FILE JE NULL")
	   				var formData = new FormData();
					formData.append("file", null);
					vm.file = formData;
	   			}

	   			if (!failUpload) {
	   				console.log("Not fail upload!!")
					vm.file.append('ebook', new Blob([JSON.stringify(newEbook)], {
						type: "application/json"
					}));
				} else {
					console.log("FAIL upload")
					vm.file.delete('ebook');
					vm.file.append('ebook', new Blob([JSON.stringify(newEbook)], {
						type: "application/json"
					}));
				}

				console.log("Fajl je");
				console.log(vm.file);
				vm.showUploadSpinner = true;
				EbookService.editEbook(vm.file)
						.then(function(response) {
							vm.showUploadSpinner = false;
							if (response.status == 409) {
					   				vm.hasAddError = true;
					   				vm.error = "File with that name already exists!";
					   				failUpload = true;

					   		} else {
					   			vm.hasAddSuccess = true;
					   			vm.success = "Successfully added ebook to repository!"
					   			vm.file = null;
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

		function editEbookPrepare(ebookId) {

			EbookService.getEbookDataById(ebookId)
						.then(function(response) {
							console.log('Got ebook data');
							console.log(response.data);

						    vm.ebook = response.data;
						    vm.ebook.fileName = response.data.fileName.slice(0, -4);
						    vm.ebook.language = response.data.language.id;
							vm.ebook.category = response.data.category.id;
							vm.ebookId = ebookId;
							console.log(vm.ebook.fileName);
				
						})
						.catch(function (response) {

							console.log('dOSLO DO CATCH');
							console.log(response);
						});		
		};

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
						vm.ebook.language = 1;
						vm.ebook.category = 1;
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

				console.log(vm.ebook.fileName);
				var newEbook = {};
				newEbook = {title: vm.ebook.title, author: vm.ebook.author,  keywords: vm.ebook.keywords,  publicationYear: vm.ebook.publicationYear, categoryId: vm.ebook.category, 
		   			languageId: vm.ebook.language, userId: vm.currentUser.userId, fileName: vm.ebook.fileName, mime: vm.ebook.mime};

		   		console.log("KNJIGA");
		   		console.log(newEbook);

		   		if (!failUpload) {
					vm.file.append('ebook', new Blob([JSON.stringify(newEbook)], {
						type: "application/json"
					}));
				} else {
					vm.file.delete('ebook');
					vm.file.append('ebook', new Blob([JSON.stringify(newEbook)], {
						type: "application/json"
					}));
				}

				vm.showUploadSpinner = true;
				EbookService.addNewEbook(vm.file)
						.then(function(response) {
							vm.showUploadSpinner = false;
							if (response.status == 409) {
					   				vm.hasAddError = true;
					   				vm.error = "File with that name already exists!";
					   				failUpload = true;

					   		} else {
					   			vm.hasAddSuccess = true;
					   			vm.success = "Successfully added ebook to repository!"
					   			vm.file = null;
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
			vm.hasAddSuccess = false;
		}

		vm.register = function() {
			//to login state, since we do not need to have register by project specification! (otherwise it should be redirected to register)
			$state.go('login');
		}

		vm.downloadEbook = function(fileName) {

			EbookService.downloadEbook(fileName)
						.then(function(response) {
							console.log('Downloaded ebook');
							console.log(response.data);	
						})
						.catch(function (response) {
							console.log('dOSLO DO CATCH');
						});
		}

		 	
	}
})();