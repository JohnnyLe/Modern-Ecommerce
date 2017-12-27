'use strict';

angular.module('ec-admin.app', ['ec-admin'])
        .controller('UserEditCtrl', [

            '$scope',
            'Util',
            'API',
            '$state',
            'Patterns',
            '$stateParams',
            'AppConfig',

            function ($scope, Util, API, $state, Patterns, $stateParams, AppConfig) {

                $scope.submitting = false;
                $scope.getUserInfo = function (cb) {
                    Util.createRequest(API.GET_USER_INFO.path + $stateParams.id, function (response) {
                        var status = response.status;
                        if (status === 200) {
                            $scope.userInfo = response.data;
                        } else {
                            Util.showErrorAPI(status);
                        }
                    });
                };

                $scope.getUserInfo();

                $scope.updateUser = function () {
                    var paramUser = {};
                    paramUser.userId = $stateParams.id;
                    paramUser.firstName = $scope.userInfo.firstName;
                    paramUser.lastName = $scope.userInfo.lastName;
                    paramUser.middleName = $scope.userInfo.middleName,
                    paramUser.email = $scope.userInfo.email;
                    paramUser.phone = $scope.userInfo.phone;
                    paramUser.fax = $scope.userInfo.fax;
                    paramUser.address = $scope.userInfo.address;
                    paramUser.city = "HCMC";
                    paramUser.country = "VN";
                    $scope.submitting = true;
                    
                    Util.createRequest(API.EDIT_USER, paramUser, function (response) {
                        var status = response.status;
                        if (status === 200) {
                            Util.showSuccessToast('message.user.update_user_success');
                            $scope.submitting = false;
                        } else {
                            Util.showErrorAPI('message.user.update_user_error');
                            $scope.submitting = false;
                        }
                    });
                };
                
                $scope.goChangePassword = function(){
                    $state.go('users.changePassword', {id: $stateParams.id});
                };
            }
        ]);