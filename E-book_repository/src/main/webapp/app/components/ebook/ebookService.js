(function() {
	'use strict';

	angular
		.module('ebookApp')
		.factory('EbookService', EbookService);

	EbookService.$inject = ['$http'];
	function EbookService($http) {

		return {

			getAllEbooks: function() {
        	var req = {
                method: 'GET',
                url: 'api/ebooks',
                headers: {
                    'Content-Type': 'application/json'
                    // 'Authorization': 'Basic ' + token
                }
            }
            return $http(req)
            		.then(function(result) {
            			console.log("getEbooks");
            			console.log(result);
                        return result;
                    })
                    .catch(function(error) {
                    	console.log(error);
						return error;
					});	    	
       		},

            getAllEbooksByCategory: function(categoryId) {
            var req = {
                method: 'POST',
                url: 'api/ebooksByCategory',
                headers: {
                    'Content-Type': 'application/json'
                    // 'Authorization': 'Basic ' + token
                },
                data: categoryId
            }
            return $http(req)
                    .then(function(result) {
                        console.log("getEbooks by Category");
                        console.log(result);
                        return result;
                    })
                    .catch(function(error) {
                        console.log(error);
                        return error;
                    });         
            },

            removeEbook: function(ebookId) {
            var req = {
                method: 'DELETE',
                url: 'api/deleteEbook',
                headers: {
                    'Content-Type': 'application/json'
                    // 'Authorization': 'Basic ' + token
                },
                data: ebookId
            }
            return $http(req)
                    .then(function(result) {
                        console.log("deletedEbook");
                        console.log(result);
                        return result;
                    })
                    .catch(function(error) {
                        console.log(error);
                        return error;
                    });         
            },

            modiflyEbook: function(ebook) {
            var req = {
                method: 'PUT',
                url: 'api/ebookEdit',
                headers: {
                    'Content-Type': 'application/json'
                    // 'Authorization': 'Basic ' + token
                },
                data: ebook
            }
            return $http(req)
                    .then(function(result) {
                        console.log("Ebook modificated");
                        console.log(result);
                        return result;
                    })
                    .catch(function(error) {
                        console.log(error);
                        return error;
                    });         
            },

            addNewEbook: function(file) {
            var req = {
                transformRequest: angular.identity,
                method: 'POST',
                url: 'api/ebookAdd',
                headers: {
                    'Content-Type': undefined
                },
                data: file
            }
            return $http(req)
                    .then(function(result) {
                        console.log("Ebook added!");
                        console.log(result);
                        return result;
                    })
                    .catch(function(error) {
                        console.log(error);
                        return error;
                    });         
            },

            upload: function(file) {
            var req = {
                transformRequest: angular.identity,
                method: 'POST',
                url: 'api/upload',
                headers: {
                    'Content-Type': undefined
                },
                data: file
            }
            return $http(req)
                    .then(function(result) {
                        console.log("Uploaded!");
                        console.log(result);
                        return result;
                    })
                    .catch(function(error) {
                        console.log(error);
                        return error;
                    });         
            }
       
		}		
	}
})();