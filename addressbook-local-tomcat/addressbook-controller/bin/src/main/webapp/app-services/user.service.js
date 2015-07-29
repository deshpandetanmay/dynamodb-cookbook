(function() {
	'use strict';

	angular.module('app').factory('UserService', UserService);

	UserService.$inject = [ '$http' ];
	function UserService($http) {
		var service = {};

		service.Create = Create;
		service.GetAllContacts = GetAllContacts;
		service.save = save;
		

		return service;

		function Create(user) {
			return $http.post('/addressbook/rest/v1/register', user).then(
					handleSuccess, handleError('Error creating user'));
		}

		function GetAllContacts(parentId) {

			return $http({
				url : '/addressbook/rest/v1/contact/all',
				method : "GET",
				params : {
					userEmail : parentId
				}
			}).then(handleSuccess, handleError('Error getting all users'));

		}

		function save(contact) {

			return $http.post('/addressbook/rest/v1/contact', contact).then(
					handleSuccess, handleError('Error creating contact'));

		}
		// private functions

		function handleSuccess(data) {
			/*
			 * return { success : true, contacts : data };
			 */

			return {
				data : data,
				success : true
			};
		}

		function handleError(error) {
			return function() {
				return {
					success : false,
					message : error
				};
			};
		}
	}

})();
