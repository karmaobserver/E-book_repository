(function() {
	'use strict';

	angular
		.module('ebookApp')
		.factory('UtilService', UtilService);

	UtilService.$inject = ['$http', '$base64'];
	function UtilService($http, $base64) {

		return {
        
	        encodeUser: function(username,password){
	    		var decodedToken = username + ":" + password;
	            var encodedToken = $base64.encode(decodedToken);
           		return encodedToken;
    		}

		}
	}
})();