(function() {
	'use strict';

	angular.module('app').controller('HomeController', HomeController);

	HomeController.$inject = [ 'UserService', '$rootScope', '$scope', '$location', 'FlashService', '$route'];
	function HomeController(UserService, $rootScope, $scope, $route) {
		var vm = this;

		vm.allContacts = [];
		vm.userEmail = null;
		vm.saveContact = saveContact;
		
		initController();

		function initController() {
			vm.userEmail = $rootScope.globals.currentUser.email;

			getAllContacts();
		}

		function getAllContacts() {
			UserService.GetAllContacts($rootScope.globals.currentUser.email)
					.then(function(data) {
					
						$scope.contacts = data.data;
					});
		}
		function saveContact($route) {
			vm.contact.parentId = vm.userEmail;
			UserService.save(vm.contact);
	        vm.contact = {};
	        $scope.backLinkClick();
		}
		$scope.backLinkClick = function () {
            window.location.reload(); 
        };
		

	}
})();