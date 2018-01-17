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
                
                $scope.emailPattern = Patterns.EMAIL_PATTERN;
                $scope.phoneNumber = Patterns.PHONE_PATTERN;
                $scope.submitting = false;

                $scope.user = {
                    "firstName": "",
                    "lastName": "",
                    "middleName": "",
                    "email": "",
                    "phone": "",
                    "fax": "",
                    "address": "",
                    "city": "",
                    "country": ""
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
                            Util.showErrorToast(response.description);
                            $scope.submitting = false;
                        }
                    });

                };
            }
        ]);