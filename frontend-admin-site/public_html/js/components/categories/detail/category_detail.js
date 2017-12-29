'use strict';

angular.module('ec-admin.app', ['ec-admin'])
    .controller('CategoryDetailCtrl', [

        '$scope',
        'Util',
        'API',
        '$state',
        'AppConfig',
        '$http',
        '$stateParams',
        '$uibModal',
        '$rootScope',

        function ($scope, Util, API, $state, AppConfig, $http, $stateParams, $uibModal, $rootScope) {
            $scope.showBntSave = true;
            $scope.submitting = false;

            var originCategory;

            $scope.init = function () {
                getListCategory(function () {
                    $scope.getCategoryInfo();
                });
            };

            function getListCategory(cb) {
                var params = {
                    company_id: 1,
                    filter_search: "",
                    sort_key: 1,
                    page_size: 100,
                    page_number: 1
                };

                Util.createRequest(API.LIST_CATEGORY, params, function (response) {
                    var status = response.status;
                    if (status === 200) {
                        $scope.categories = response.data.content;
                        cb && cb();
                    } else {
                        Util.showErrorAPI(status);
                    }
                });
            }

            $scope.getCategoryInfo = function () {
                Util.createRequest(API.DETAIL_CATEGORY.path + $stateParams.id, function (response) {
                    var status = response.status;
                    if (status === 200) {
                        $scope.category = response.data;
                        if ($scope.category.parentId) {
                            $scope.category.parent = _.find($scope.categories, {'categoryId': $scope.category.parentId});
                        }else{
                            $scope.category.parent = null;
                        }

                        originCategory = angular.copy($scope.category);

                        setWatch();

                    } else {
                        Util.showErrorAPI(status);
                    }
                }).finally(function () {
                    $scope.submitting = false;
                });
            };

            function setWatch() {
                $scope.$watch('category', function (newValue) {
                    $scope.origin = angular.equals(newValue, originCategory);
                }, true);
            }

            $scope.updateCategory = function () {
                $scope.showBntSave = false;
                $scope.submitting = false;

                var params = {
                    id : $scope.category.categoryId,
                    name: $scope.category.name,
                    description: $scope.category.description,
                    position: $scope.category.position,
                    parentId: $scope.category.parent ? $scope.category.parent.categoryId : null
                };

                Util.createRequest(API.UPDATE_CATEGORY, params, function (response) {
                    var status = response.status;
                    if (status === 200) {
                        Util.showSuccessToast('message.product.update_category_success');
                    } else {
                        Util.showErrorToast('message.product.update_category_error');
                    }
                }).finally(function () {

                });

            };

        }
    ]);