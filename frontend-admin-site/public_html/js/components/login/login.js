'use strict';

angular.module('ec-admin.auth', [])

.controller('LoginCtrl', [

    '$scope',
    'Session',
    'Patterns',
    'APIStatus',
    'toastr',
    'Util',
    '$state',

    function ($scope, Session, Patterns, APIStatus, toastr, Util, $state) {
        // Checking admin already login
        if (Session.getAccessToken() && Session.getUser()){
            $state.go('categories.list');
            return;
        }
        

        $scope.submitting = false;

        $scope.user = {
            username: '',
            password: ''
        };

        $scope.registerConsoleUser = function () {

            $scope.submitting = true;
            Session.consoleLogin({
                username: $scope.user.username,
                password: Util.MD5($scope.user.password)
            }, function (response) {

                var status = response.status;
                if (status === 200) {
                    
                    // redirect page
                    $state.go('categories.list');
                } else {

                    var err = _.find(APIStatus, {status: status});
                    if (err) {
                        toastr.error(Util.translate(err.mgsKey));
                    }
                }
            }).finally(function () {
                $scope.submitting = false;
            });
        };

        return {
            Patterns: Patterns
        };

    }
]);