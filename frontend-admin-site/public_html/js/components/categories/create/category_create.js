'use strict';

angular.module('ec-admin.app', ['ec-admin'])
    .controller('CategoryCreateCtrl', [

        '$scope',
        'Util',
        'API',
        '$state',
        'AppConfig',
        '$http',
        '$uibModal',
        '$rootScope',

        function ($scope, Util, API, $state, AppConfig, $http, $uibModal, $rootScope) {
            $scope.showBntSave = true;
            $scope.submitting = false;

             $scope.category = {
                name: "",
                description: "",
                position: null,
                parent: null
            };

            $scope.init = function() {
                getListCategory()
            };


            function getListCategory() {
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
                    } else {
                        Util.showErrorAPI(status);
                    }
                });
            }

            $scope.createCategory = function () {
                $scope.showBntSave = false;
                $scope.submitting = false;

                var params = {
                    name: $scope.category.name,
                    description: $scope.category.description,
                    position: $scope.category.position,
                    parentId: $scope.category.parent ? $scope.category.parent.categoryId : null
                };

                Util.createRequest(API.CREATE_CATEGORY, params, function (response) {
                    var status = response.status;
                    if (status === 200) {
                        Util.showSuccessToast('message.product.create_category_success');
                        // goto list
                        $state.go('categories.list');
                    } else {
                        Util.showErrorToast('message.product.create_category_error');
                    }
                }).finally(function () {

                });

            };
        }
    ]);

