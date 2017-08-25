
(function () {
	'use strict';
  angular
       .module('ebookApp')
      .config(config)
     .run(run);	

 function config($stateProvider, $urlRouterProvider, $locationProvider) {

    $urlRouterProvider.otherwise('/home');

    $stateProvider  
      .state('login', {
	      url: "/login",
        views: {
                 'content': {
                           templateUrl: 'app/components/login/login.html',
                           controller: "LoginCtrl",
                           controllerAs: "vm"
                       }
                } 
      })
      .state('home', {
        url: "/home",
        views: {
                 'content': {
                           templateUrl: 'app/components/home/home.html',
                           controller: "HomeCtrl",
                           controllerAs: "vm"
                       },
                  'navigation':{
                           templateUrl: 'app/components/navigation/navigation.html',
                           controller: "NavigationCtrl",
                           controllerAs: "vm"
                       }
                } 
      })
      .state('categories', {
        url: "/categories",
        views: {
                 'content': {
                           templateUrl: 'app/components/category/category.html',
                           controller: "CategoryCtrl",
                           controllerAs: "vm"
                       },
                  'navigation':{
                           templateUrl: 'app/components/navigation/navigation.html',
                           controller: "NavigationCtrl",
                           controllerAs: "vm"
                       }
                } 
      })
      .state('categoryAdd', {
        url: "/categoryAdd",
        views: {
                 'content': {
                           templateUrl: 'app/components/category/categoryAdd.html',
                           controller: "CategoryCtrl",
                           controllerAs: "vm"
                       },
                  'navigation':{
                           templateUrl: 'app/components/navigation/navigation.html',
                           controller: "NavigationCtrl",
                           controllerAs: "vm"
                       }
                } 
      })
      .state('userAdd', {
        url: "/userAdd",
        views: {
                 'content': {
                           templateUrl: 'app/components/user/userAdd.html',
                           controller: "UserCtrl",
                           controllerAs: "vm"
                       },
                  'navigation':{
                           templateUrl: 'app/components/navigation/navigation.html',
                           controller: "NavigationCtrl",
                           controllerAs: "vm"
                       }
                } 
      })
      .state('ebooks', {
        url: "/ebooks",
        views: {
                 'content': {
                           templateUrl: 'app/components/ebook/ebook.html',
                           controller: "EbookCtrl",
                           controllerAs: "vm"
                       },
                  'navigation':{
                           templateUrl: 'app/components/navigation/navigation.html',
                           controller: "NavigationCtrl",
                           controllerAs: "vm"
                       }
                } 
      })
      .state('profile', {
        url: "/profile",
        views: {
                 'content': {
                           templateUrl: 'app/components/profile/profile.html',
                           controller: "ProfileCtrl",
                           controllerAs: "vm"
                       },
                  'navigation':{
                           templateUrl: 'app/components/navigation/navigation.html',
                           controller: "NavigationCtrl",
                           controllerAs: "vm"
                       }
                } 
      })
      .state('users', {
        url: "/users",
        views: {
                 'content': {
                           templateUrl: 'app/components/user/user.html',
                           controller: "UserCtrl",
                           controllerAs: "vm"
                       },
                  'navigation':{
                           templateUrl: 'app/components/navigation/navigation.html',
                           controller: "NavigationCtrl",
                           controllerAs: "vm"
                       }
                } 
      });



     //Koristim da bi izbacio # iz url-a. Jos u server.js koristim rewrite mehanizam // to ked osm pravel XO makso
    $locationProvider.html5Mode(true); 
  
  }

  function run($rootScope, $state, localStorageService) {


  }

}());