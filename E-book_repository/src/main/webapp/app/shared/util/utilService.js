(function() {
	'use strict';

	angular
		.module('ebookApp')
		.factory('UtilService', UtilService);

	UtilService.$inject = ['$http', '$base64'];
	function UtilService($http, $base64) {

		return {
        
	        getAllLanguages: function() {
            	var req = {
                    method: 'GET',
                    url: 'api/languages',
                    headers: {
                        'Content-Type': 'application/json'
                        // 'Authorization': 'Basic ' + token
                    }
                }
                return $http(req)
                		.then(function(result) {
                			console.log("getlanguages");
                			console.log(result);
                            return result;
                        })
                        .catch(function(error) {
                        	console.log(error);
    						return error;
    					});	         	
       		},

		}
	}
})();