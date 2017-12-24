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

            function ($scope, Util, API, $state, AppConfig, $http, $stateParams) {
                $scope.showBntSave = true;
                $scope.submitting = false;
                $scope.image = 'img/no-image-available.png';
                $scope.files = [];
                $scope.pathFile = AppConfig.PATH_FILE;
//                $scope.fileMedia = [];
//                $scope.multipleImage = [];
//                $scope.$watch('fileMedia', function () {
//                    $scope.multipleImage = _.extend($scope.fileMedia, $scope.multipleImage);
//                });
                $scope.paramProduct = {
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

                $scope.getProductInfo = function (cb) {
                    Util.createRequest(API.DETAIL_PRODUCT.path + $stateParams.id, function (response) {
                        var status = response.status;
                        if (status === 200) {
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
                    $scope.product.productId = $stateParams.id;
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
                            Util.showSuccessToast('message.product.update_product_success');
                            // goto car branch list
//                            $state.go('products.list');
                        } else {
                            Util.showErrorToast('message.product.update_product_error');
                        }
                    }).finally(function () {

                    });

                };

                $scope.tags = ['abc', 'def'];
                $scope.loadTags = function (query) {
                    return $http.get('/tags?query=' + query);
                };

                function innit() {
                    $scope.getProductInfo();
                }
                ;

                innit();
            }
        ]);