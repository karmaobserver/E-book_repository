(function() {
	'use strict';

	angular
		.module('ebookApp')
		.factory('HomeService', HomeService);

	HomeService.$inject = ['$http', '$base64'];
	function HomeService($http, $base64) {

		return {
		/*var sayHello = function() {
			return "Hello ";
		},*/	

            getUserGroups: function(token, username){
	            var req = {
	                method: "GET",
	                url: 'http://localhost:8080/activiti-rest/service/identity/groups?member=' + username,
	                headers: {
	                  'Content-Type': 'application/json',
	                  'Authorization': 'Basic ' + token
	                }
	            }
                return $http(req)
                		.then(function(result) {
                			console.log("getUserGroups");
                			console.log(result);
                            return result;
                        })
                        .catch(function(error) {
                        	console.log(error);
							return error;
						});	;           	
       		 },

	        encodeUser: function(username,password){
	    		var decodedToken = username + ":" + password;
	            var encodedToken = $base64.encode(decodedToken);
            return encodedToken;
    	}

		}
		


	}
})();