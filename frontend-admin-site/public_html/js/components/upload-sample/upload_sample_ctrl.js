'use strict';

angular.module('ec-admin.app', ['ec-admin'])
        .controller('UploadSampleCtrl', [
            '$scope',
            'Util',
            'API',
            '$state',
            'Patterns',
            '$http',
            '$stateParams',

            function ($scope, Util, API, $state, Patterns, $http, $stateParams) {
                
                $scope.image = 'img/no-image-available.png';
                $scope.files = [];
                $scope.fileMedia = [];
                $scope.multipleImage = [];
                $scope.$watch('fileMedia', function () {
                    $scope.multipleImage = _.extend($scope.fileMedia, $scope.multipleImage);
                });

                $scope.removeImage = function (item) {
                    var index = $scope.multipleImage.indexOf(item);
                    $scope.multipleImage.splice(index, 1);
                };

                return {
                    Patterns: Patterns
                };
            }
        ]);