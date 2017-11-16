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
                }
            }
            return $http(req)
            		.then(function(result) {
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

            getEbookDataById: function(ebookId) {
            var req = {
                method: 'POST',
                url: 'api/getEbookData',
                headers: {
                    'Content-Type': 'application/json'
                },
                data: ebookId
            }
            return $http(req)
                    .then(function(result) {
                        console.log("Ebook edited");
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

            editEbook: function(file) {
            var req = {
                transformRequest: angular.identity,
                method: 'POST',
                url: 'api/ebookEdit',
                headers: {
                    'Content-Type': undefined
                },
                data: file
            }
            return $http(req)
                    .then(function(result) {
                        console.log("Ebook edited!");
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
            },

            downloadEbook: function(fileName) {
            var req = {
                method: 'POST',
                url: 'api/downloadEbook',
                headers: {
                    'Content-Type': 'application/json'
                },
                data: fileName
            }
            return $http(req)
                    .then(function(result) {
                        console.log("Ebook downloaded");
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