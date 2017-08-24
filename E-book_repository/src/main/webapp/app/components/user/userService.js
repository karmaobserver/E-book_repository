(function() {
	'use strict';

	angular
		.module('ebookApp')
		.factory('UserService', UserService);

	UserService.$inject = ['$http'];
	function UserService($http) {

		return {

			getAllUsers: function() {
        	var req = {
                method: 'GET',
                url: 'api/users',
                headers: {
                    'Content-Type': 'application/json'
                    // 'Authorization': 'Basic ' + token
                }
            }
            return $http(req)
            		.then(function(result) {
            			console.log("getUsers");
            			console.log(result);
                        return result;
                    })
                    .catch(function(error) {
                    	console.log(error);
						return error;
					});	;           	
       		},

            getUser: function(username) {
            var req = {
                method: 'GET',
                url: 'api/user' + username,
                headers: {
                    'Content-Type': 'application/json'
                    // 'Authorization': 'Basic ' + token
                }
            }
            return $http(req)
                    .then(function(result) {
                        console.log("getUser");
                        console.log(result);
                        return result;
                    })
                    .catch(function(error) {
                        console.log(error);
                        return error;
                    }); ;               
            }

		}

		
	}
})();