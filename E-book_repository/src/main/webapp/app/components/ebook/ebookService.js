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
            }
       
		}		
	}
})();