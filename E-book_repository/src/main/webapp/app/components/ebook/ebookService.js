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
       		}
       
		}		
	}
})();