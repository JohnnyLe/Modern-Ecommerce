'use strict';

angular.module('ec-admin.app', ['ec-admin'])
        .controller('UserCreateCtrl', [

            '$scope',
            'Util',
            'API',
            '$state',
            'Patterns',
            '$http',

            function ($scope, Util, API, $state, Patterns, $http) {
                $scope.submitting = false;

                $scope.user = {
                    "firstName": "",
                    "lastName": "",
                    "middleName": "",
                    "email": "",
                    "passwordHash": "5252be3f45d410ad6b478429af835ba4",
                    "phone": "",
                    "fax": "",
                    "address": "",
                    "city": "HCMC",
                    "country": "VN"
                };


                $scope.createUser = function () {
                    $scope.submitting = true;                    
                    Util.createRequest(API.REGISTER_USER, $scope.user, function (response) {
                        var status = response.status;
                        if (status === 200) {
                            // show message delete successfully
                            Util.showSuccessToast("Create User successfully");
                            // goto car branch list
                            $state.go('users.list');
                        } else {
                            Util.showErrorAPI("An error occur");
                            $scope.submitting = false;
                        }
                    });

                };
            }
        ]);