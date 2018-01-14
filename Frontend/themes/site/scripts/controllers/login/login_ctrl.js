'use strict';

angular.module('marketplace.login', [])

        .controller('LoginCtrl', ['$scope', 'Session', "$state", 'toastr', 'util', function ($scope, Session, $state, toastr, util) {
                // Checking admin already login
                if (Session.getAccessToken() && Session.getUser()) {
                    $state.go('index');
                    return;
                }
                $scope.email = '';
                $scope.password = '';

                $scope.registerConsoleUser = function () {
                    $scope.submitting = true;

                    Session.consoleLogin({
                        username: $scope.email,
                        password: util.MD5($scope.password),
                        keepMeLogin: 1
                    }, function (response) {
                        var status = response.status;
                        if (status === 200) {
                            // redirect page
                            $state.go('index');
                        } else {
                            util.showErrorToast("Error");
                        }
                    }).finally(function () {
                        $scope.submitting = false;
                    });
                };




            }]);