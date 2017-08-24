(function() {
	'use strict';

	angular
		.module('ebookApp')
		.factory('CategoryService', CategoryService);

	CategoryService.$inject = ['$http'];
	function CategoryService($http) {

		return {

			getAllCategories: function() {
        	var req = {
                method: 'GET',
                url: 'api/categories',
                headers: {
                    'Content-Type': 'application/json'
                    // 'Authorization': 'Basic ' + token
                }
            }
            return $http(req)
            		.then(function(result) {
            			console.log("getCategories");
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