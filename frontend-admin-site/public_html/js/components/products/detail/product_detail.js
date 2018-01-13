'use strict';

angular.module('ec-admin.app', ['ec-admin'])
        .controller('ProductsDetailCtrl', [

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
                $scope.tags = [];
                $scope.image = 'img/no-image-available.png';
                $scope.imageUpdate = '';
                $scope.files = [];
                $scope.pathFile = AppConfig.PATH_FILE;
                $rootScope.checkTags = [];
//                $scope.fileMedia = [];
//                $scope.multipleImage = [];
                $scope.$watch('files', function () {
                    if($scope.files.length > 0){
                        $scope.origin = false;
                    }
                });
                $scope.paramProduct = {
                    name: "",
                    productId: 0,
                    companyId: 1,
                    description: "",
                    browsingName: "",
                    defaultImage: "",
                    salePrice: 0,
                    listCategoriesId: [],
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

                $scope.getProductInfo = function (cb) {
                    Util.createRequest(API.DETAIL_PRODUCT.path + $stateParams.id, function (response) {
                        var status = response.status;
                        if (status === 200) {
                            $scope.tags = response.data.list_category;
                            var tagsCheck = angular.copy($scope.tags);
                            $scope.$watch('tags', function () {
                                $rootScope.checkTags = $scope.tags;
                                $scope.origin = angular.equals($scope.tags, tagsCheck);
                            }, true);
//                            $scope.imageUpdate = response.data.product.defaultImage;
                            $scope.product = response.data.product;
                            if ($scope.product.defaultImage !== 'img/no-image-available.png') {
                                $scope.product.defaultImage = $scope.pathFile + $scope.product.defaultImage;
                            }
                            var originCar = angular.copy($scope.product);
                            $scope.$watch('product', function () {
                                $scope.origin = angular.equals($scope.product, originCar);
                            }, true);
                            if (cb) {
                                cb && cb();
                            }
                        } else {
                            Util.showErrorAPI(status);
                        }
                    }).finally(function () {
                        $scope.submitting = false;
                    });
                };

                $scope.$watchCollection('files', function (newVal) {
                    if (newVal) {
                        $scope.origin = false;
                    }
                });

                $scope.updateProduct = function () {
                    $scope.paramProduct.productId = $stateParams.id;
                    $scope.paramProduct.browsingName = $scope.product.browsingName;
                    $scope.paramProduct.description = $scope.product.description;
                    $scope.paramProduct.name = $scope.product.name;
                    $scope.paramProduct.quantity = $scope.product.quantity;
                    $scope.paramProduct.overview = $scope.product.overview;
                    $scope.paramProduct.sku = $scope.product.sku;
                    $scope.paramProduct.salePrice = $scope.product.salePrice;
                    $scope.paramProduct.defaultImage = $scope.imageUpdate;
                    $scope.tags.forEach(function (result) {
                        $scope.paramProduct.listCategoriesId.push(result.id);
                    });
                    $scope.showBntSave = false;
                    $scope.submitting = false;
                    if ($scope.files.length > 0) {
                        $scope.paramProduct.defaultImage = $scope.files[0].name;
                    }
                    Util.createRequest(API.UPDATE_PRODUCT, $scope.paramProduct, function (response) {
                        var status = response.status;
                        if (status === 200) {
                            Util.showSuccessToast('message.product.update_product_success');
                            // goto car branch list
                            $state.go('products.list');
                        } else {
                            Util.showErrorToast('message.product.update_product_error');
                        }
                    }).finally(function () {

                    });

                };

                // Pop-up
                $scope.addCategory = function () {
                    var modalInstance = $uibModal.open({
                        templateUrl: 'js/components/template/confirm_category_modal.html',
                        windowClass: 'large-Modal',
                        controller: ['$scope', '$uibModalInstance', 'Util', '$rootScope', function ($scope, $uibModalInstance, Util, $rootScope) {
                                $scope.searchString = "";
                                $scope.title = Util.translate('table.header.pop_up.list_cate');
                                $scope.message = "";
                                $scope.listCategory = [];
                                $scope.curentSelected = [];
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
                                var params = {
                                    company_id: 1,
                                    search_key: "",
                                    sort_key: 1,
                                    page_size: 100,
                                    page_number: 1
                                };

                                Util.createRequest(API.LIST_CATEGORY, params, function (response) {
//                                    console.log($scope.product.listCategoriesId);
                                    var status = response.status;
                                    if (status === 200) {
                                        $scope.listCategory = response.data.content;
                                        $scope.listCategory.forEach(function (result) {
                                            result.selected = false;
                                            $rootScope.checkTags.forEach(function(check){
                                                if(check.id === result.categoryId){
                                                    $scope.curentSelected.push(result);
                                                }
                                            });
                                        });
                                        $scope.removeSelected($scope.curentSelected);
                                        var originCar = angular.copy($scope.listCategory);
                                        $scope.$watch('listCategory', function () {
                                            $scope.origin = angular.equals($scope.listCategory, originCar);
                                        }, true);
//                                        $scope.totalItems = response.data.totalResult;
//                                        $scope.totalPage = response.data.totalPage;
                                    } else {
                                        Util.showErrorAPI(status);
                                    }
                                });

                                $scope.loadListSearch = function () {
                                    params.search_key = $scope.searchString;
                                    Util.createRequest(API.LIST_CATEGORY, params, function (response) {

                                        var status = response.status;
                                        if (status === 200) {
                                            $scope.listCategory = response.data.content;
                                            $scope.listCategory.forEach(function (result) {
                                                result.selected = false;
                                            });
                                        } else {
                                            Util.showErrorAPI(status);
                                        }
                                    });
                                };

                                $scope.removeSelected = function (list) {
                                    list.forEach(function (item) {
                                        var index = $scope.listCategory.indexOf(item);
                                        $scope.listCategory.splice(index, 1);
                                    });
                                    $scope.curentSelected = [];
                                };
                                
                                $scope.onClose = function () {
                                    $uibModalInstance.close();
                                };

                                $scope.onDismiss = function () {
                                    $uibModalInstance.close();
                                };

                                $scope.onAccept = function () {
                                    var chooseCate = [];
                                    $scope.listCategory.forEach(function (result) {
                                        if (result.selected) {
                                            chooseCate.push(result);
                                        }
                                    });
                                    $rootScope.$broadcast('greeting', {any: chooseCate});
                                    $uibModalInstance.close();

                                };
                            }]
                    });
                    return modalInstance;
                };

                $scope.$on('greeting', listenGreeting);

                function listenGreeting($event, response) {
                    if (response.any.length > 0) {
                        response.any.forEach(function (result) {
                            var text = {
                                text: result.name,
                                id: result.categoryId
                            };
                            $scope.tags.push(text);
                        });
                    }
                }
//                $scope.$watch('tags', function () {
//                    $scope.tags.forEach(function(result){
//                        $scope.paramProduct.listCategoriesId.push(result.id);
//                    });
//                }, true);
                $scope.loadTags = function (query) {
                    return $http.get('/tags?query=' + query);
                };

                $scope.openPopUp = function () {
                    $scope.addCustomer();
                };

                function innit() {
                    $scope.getProductInfo();
                }
                ;

                innit();
            }
        ]);