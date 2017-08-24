(function() {
	'use strict';

	angular
		.module('ebookApp')
		.factory('LoginService', LoginService);

	LoginService.$inject = ['$http', '$base64'];
	function LoginService($http, $base64) {

		return {
	        
            login: function(token,username) {
            	var req = {
                    method: 'GET',
                    url: 'api/login' + username,
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Basic ' + token
                    }
                }
                return $http(req)
                		.then(function(result) {
                			console.log("login");
                			console.log(result);
                            return result;
                        })
                        .catch(function(error) {
                        	console.log(error);
							return error;
						});	;           	
       		 },

		}
	}
})();