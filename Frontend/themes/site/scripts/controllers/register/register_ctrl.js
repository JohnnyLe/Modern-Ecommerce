'use strict';

angular.module('marketplace.register', [])

.controller('RegisterCtrl', ['$scope', 'user', function ($scope, user) {
    // Alert array
    $scope.alerts = [], $scope.submitting = false;

    // Close alert
    $scope.closeAlert = function (index) {
        $scope.alerts.splice(index, 1);
    };

    // Handle submit action
    $scope.submit = function () {

        // Prevent submit multiple time
        if ($scope.submitting)
            return;

        if ($scope.email === undefined || $scope.password === undefined || $scope.firstName === undefined || $scope.lastName === undefined) {

            $scope.alerts = [{
                    type: 'danger',
                    msg: "Invalid ID or password"
                }];

            return;
        }

        $scope.submitting = true;

        // Do login
        user.register({
            
            email: $scope.email,
            passwordHash: $scope.password,
            lastName: $scope.lastName,
            firstName: $scope.firstName

        }, function (err) {

            // Handle error
            $scope.submitting = false;

            $scope.alerts = [{
                    type: 'danger',
                    msg: "Invalid ID or password"
                }];

        });
    };

}]);