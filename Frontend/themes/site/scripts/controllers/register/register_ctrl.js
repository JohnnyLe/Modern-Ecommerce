'use strict';

angular.module('marketplace.register', [])

.controller('RegisterCtrl', ['$scope', 'app', 'util', function ($scope, app, util) {
//    // Alert array
//    $scope.alerts = [];
//    $scope.submitting = false;
//    $scope.emailRegex = app.EMAIL_REGEX;
//
//    // Close alert
//    $scope.closeAlert = function (index) {
//        $scope.alerts.splice(index, 1);
//    };
//
//    // Handle submit action
//    $scope.submit = function () {
//
//        // Prevent submit multiple time
//        if ($scope.submitting)
//            return;
//
//        if ($scope.email === undefined || $scope.password === undefined) {
//
//            $scope.alerts = [{
//                    type: 'danger',
//                    msg: "Invalid ID or password"
//                }];
//
//            return;
//        }
//
//        $scope.submitting = true;
//
//        // Do login
//        user.register({
//            
//            email: $scope.email,
//            passwordHash: $scope.password,
//            lastName: $scope.lastName,
//            firstName: $scope.firstName
//
//        }, function (err) {
//
//            // Handle error
//            $scope.submitting = false;
//
//            $scope.alerts = [{
//                    type: 'danger',
//                    msg: "Invalid ID or password"
//                }];
//
//        });
//    };
//    
//    $scope.open = function() {
//        util.openModal();
//    };
//    
//    $scope.checkPasswordMatch = function() {
//        return $scope.password === $scope.retypePassword;
//    };

}]);