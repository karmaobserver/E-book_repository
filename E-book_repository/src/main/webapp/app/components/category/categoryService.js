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
                        return result;
                    })
                    .catch(function(error) {
                    	console.log(error);
						return error;
					});	    	
       		},

            addNewCategory: function(categoryName) {
            var req = {
                method: 'POST',
                url: 'api/categoryAdd',
                headers: {
                    'Content-Type': 'application/json'
                    // 'Authorization': 'Basic ' + token
                },
                data: categoryName
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

            removeCategory: function(categoryId) {
            var req = {
                method: 'DELETE',
                url: 'api/deleteCategory',
                headers: {
                    'Content-Type': 'application/json'
                    // 'Authorization': 'Basic ' + token
                },
                data: categoryId
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

            modifyCategory: function(category) {
                console.log(category);
            var req = {
                method: 'PUT',
                url: 'api/editCategory',
                headers: {
                    'Content-Type': 'application/json'
                    // 'Authorization': 'Basic ' + token
                },
                data: category
            }
            return $http(req)
                    .then(function(result) {
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