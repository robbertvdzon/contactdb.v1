'use strict';

/**
 * @ngdoc function
 * @name mswFrontendApp.controller:GameCtrl
 * @description
 * # ContactsCtrl
 * Controller of the mswFrontendApp
 */
angular.module('mswFrontendApp')

    .controller('ContactsCtrl', ['$scope', '$rootScope', 'domainService', '$http', function ($scope, $rootScope, domainService, $http) {

        $scope.contacts = [];
        $scope.selectedcontact = {};
        $scope.newcontact = {};


        $scope.initialize = function () {
            $scope.loadData();
        }

        $scope.loadData = function () {
            {
            $http({
                url: "/api/resources/contacts/getContacts",
                method: "GET",
             }).success(
                   function (response) {
                       $scope.contacts = response;
                       $scope.selectedcontact = [];
                       $scope.newcontact = [];
               });
            }
        }

       $scope.edit = function (uuid) {
            for (var i = 0; i < $scope.contacts.length; i++) {
                  var contact = $scope.contacts[i];
                  if (contact.uuid===uuid) {
                       $scope.selectedcontact = {};
                       $scope.selectedcontact.uuid = contact.uuid;
                       $scope.selectedcontact.name = contact.name;
                       $scope.selectedcontact.email = contact.email;
                  }
            }
        }

       $scope.save = function () {
            $http({
                url: "/api/resources/contacts/saveContact",
                method: "POST",
                params: {contact: $scope.selectedcontact}
             }).success(
                   function (response) {
                       $scope.reloadContacts(response);
               });

        }

       $scope.delete = function (uuid) {
            $http({
                url: "/api/resources/contacts/"+uuid,
                method: "DELETE",
                params: {contact: $scope.selectedcontact}
             }).success(
                   function (response) {
                       $scope.reloadContacts(response);
               });
        }

       $scope.add = function () {
            var newContact = {};
            newContact.name = $scope.newcontact.name;
            newContact.email = $scope.newcontact.email;
            $http({
                url: "/api/resources/contacts/",
                method: "PUT",
                params: {contact: newContact}
             }).success(
                   function (response) {
                       $scope.reloadContacts(response);
               });

        }

       $scope.reloadContacts = function (contacts) {
            $scope.contacts = contacts;
            $scope.selectedcontact = [];
            $scope.newcontact = [];
       }


        $scope.initialize();

    }]);
