(function() {
	'use strict';

	angular
		.module('ebookApp')
		.factory('SearchService', SearchService);

	SearchService.$inject = ['$http'];
	function SearchService($http) {

		return {

			search: function(searchAttributes) {
        	var req = {
                method: 'POST',
                url: 'api/search',
                headers: {
                    'Content-Type': 'application/json'
                    // 'Authorization': 'Basic ' + token
                },
                data: searchAttributes
            }
            return $http(req)
            		.then(function(result) {
            			console.log("search");
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