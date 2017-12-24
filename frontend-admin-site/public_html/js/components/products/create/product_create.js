'use strict';

angular.module('ec-admin.app', ['ec-admin'])
        .controller('ProductsCreateCtrl', [

            '$scope',
            'Util',
            'API',
            '$state',
            'AppConfig',
            '$http',
            '$uibModal',

            function ($scope, Util, API, $state, AppConfig, $http, $uibModal) {
                $scope.showBntSave = true;
                $scope.submitting = false;
                $scope.image = 'img/no-image-available.png';
                $scope.files = [];
//                $scope.fileMedia = [];
//                $scope.multipleImage = [];
//                $scope.$watch('fileMedia', function () {
//                    $scope.multipleImage = _.extend($scope.fileMedia, $scope.multipleImage);
//                });
                $scope.product = {
                    name: "",
                    productId: 0,
                    companyId: 1,
                    description: "",
                    browsingName: "",
                    defaultImage: "",
                    salePrice: 0,
                    listCategoriesId: [1, 2],
                    listPrice: "",
                    overview: "",
                    quantity: 0,
                    isStockControlled: true,
                    sku: "",
                    rank: 0
                };
                $scope.uploadFile = {
                    file_name: "",
                    file: {},
                    object_type: "",
                    object_sub_type: "",
                    object_id: 0,
                    name: "",
                    chunk: "",
                    chunks: ""
                };
                $scope.files = [];
                $scope.createProduct = function () {
                    console.log($scope.files);
                    $scope.showBntSave = false;
                    $scope.submitting = false;
                    if ($scope.files.length > 0) {
                        $scope.product.defaultImage = $scope.files[0].name;
                    } else {
                        $scope.product.defaultImage = $scope.image;
                    }
                    Util.createRequest(API.CREATE_PRODUCT, $scope.product, function (response) {
                        var status = response.status;
                        if (status === 200) {
                            Util.showSuccessToast('message.product.create_product_success');
                            // goto car branch list
                            $state.go('products.list');
                        } else {
                            Util.showErrorToast('message.product.create_product_error');
                        }
                    }).finally(function () {

                    });

                };
                $scope.tags = ['abc', 'def'];
                $scope.loadTags = function (query) {
                    return $http.get('/tags?query=' + query);
                };

                // Pop-up
                $scope.addCustomer = function () {
                    var modalInstance = $uibModal.open({
                        templateUrl: 'js/components/template/confirm_category_modal.html',
                        windowClass: 'large-Modal',
                        controller: ['$scope', '$uibModalInstance', 'Util', '$rootScope', function ($scope, $uibModalInstance, Util, $rootScope) {
                                $scope.searchString = "";
                                $scope.title = Util.translate('page_title.cus.list');
                                $scope.message = "";
                                $scope.listCategory = [];
                                $scope.curentSelected = [];
                                $scope.showButtonChoose = true;
                                $scope.pagination = {};
                                $scope.opts = {
                                    lblAccept: Util.translate('button.add'),
                                    lblDismiss: Util.translate('button.cancel')
                                };
                                $scope.maxSize = 5;
                                $scope.currentPage = 1;
                                $scope.pageSize = 10;
                                $scope.pageChanged = function () {
                                    $scope.loadListSearch();
                                };
                                var paramCategory = {
                                    searchKey: "",
                                    sortCase: -1,
                                    ascSort: 1,
                                    pageNumber: 0,
                                    pageSize: 10
                                };

                                Util.createRequest(API.GET_LIST_CUS, paramCategory, function (response) {

                                    var status = response.status;
                                    if (status === 200) {
                                        $scope.listCategory = response.data.content;
                                        $scope.totalItems = response.data.totalResult;
                                        $scope.totalPage = response.data.totalPage;
                                    } else {
                                        Util.showErrorAPI(status);
                                    }
                                });

                                $scope.checkId = function (position, listCate) {
                                    $scope.showButtonChoose = true;
                                    angular.forEach(listCate, function (cate, index) {
                                        if (position !== index)
                                            cate.selected = false;
                                    });

                                    angular.forEach(listCate, function (cate) {
                                        if (cate.selected)
                                            $scope.showButtonChoose = false;
                                    });
                                };
                                $scope.checkDbClick = function (position, listCus) {
                                    angular.forEach(listCus, function (cus, index) {
                                        if (position !== index) {
                                            cus.selected = false;
                                        } else {
                                            cus.selected = true;
                                        }
                                    });
                                    $scope.onAccept();
                                };

                                $scope.loadListSearch = function () {
                                    paramCategory.searchKey = $scope.searchString;
                                    Util.createRequest(API.GET_LIST_CUS, paramCategory, function (response) {

                                        var status = response.status;
                                        if (status === 200) {
                                            $scope.listCategory = response.data.content;
                                            $scope.pagination = {
                                                first: response.data.first,
                                                last: response.data.last,
                                                currentPage: response.data.number,
                                                totalPages: response.data.totalPages,
                                                pageObj: new Array(response.data.totalPages)
                                            };
                                        } else {
                                            Util.showErrorAPI(status);
                                        }
                                    });
                                };
                                $scope.onClose = function () {
                                    $uibModalInstance.close();
                                };

                                $scope.onDismiss = function () {
                                    $uibModalInstance.close();
                                };

                                $scope.onAccept = function () {
                                    $scope.listCategory.forEach(function (result) {
                                        if (result.selected) {
                                            $rootScope.$broadcast('greeting', {any: result});
                                        }
                                    });
                                    $uibModalInstance.close();

                                };
                            }]
                    });
                    return modalInstance;
                };

                $scope.$on('greeting', listenGreeting);

                function listenGreeting($event, message) {
                }

                $scope.openPopUp = function () {
                    $scope.addCustomer();
                };
            }
        ]);

