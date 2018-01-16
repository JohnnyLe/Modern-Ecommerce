'use strict';

angular.module('marketplace.products.details', [])

        .controller('ProductDetailsCtrl', ['$scope', 'util', '$', '$timeout', '$stateParams', 'ShoppingCart', '$http', 'toastr', 'AppConstant', function ($scope, util, $, $timeout, $stateParams, cart, $http, toastr, AppConstant) {


                $scope.proComment = {};
                $scope.random = 1;
                $scope.totalPage = 1;
                $scope.quantity = 1;
                $scope.pathFile = AppConstant.pathFile;
                // Data model biding
                $scope.loadData = function () {
                    util.callRequest('products/detail/' + $stateParams.productId, "GET").then(function (data) {
                        $scope.product = data.data.product;
//                        if ($scope.product.defaultImage !== 'img/no-image-available.png') {
//                            $scope.product.defaultImage = $scope.pathFile + $scope.product.defaultImage;
//                        }
                        $scope.loadListProduct(-1);
                        $scope.loadListProductRecomad(-1);
                    });

//        util.callRequest('reviews/' + $stateParams.productId, "GET").then(function (data) {
//            $scope.reviews = data.result;
//        });
                };

                $scope.insertComment = function () {
                    $scope.proComment.productId = $stateParams.productId;
                    $scope.proComment.rank = 1;

                    util.callRequest('reviews/add?token=' + "dfasdfasdf", "POST", $scope.proComment).then(function (data) {
                    });
                };

                $scope.loadData();

                var param = {
                    categoryId: -1,
                    attributeId: -1,
                    companyId: 1,
                    searchKey: "",
                    minRank: -1,
                    maxRank: -1,
                    minPrice: 0,
                    maxPrice: -1,
                    sortCase: -1,
                    ascSort: 0,
                    pageNumber: 0,
                    pageSize: 4

                };
                var paramRe = {
                    categoryId: -1,
                    attributeId: -1,
                    companyId: 1,
                    searchKey: "",
                    minRank: -1,
                    maxRank: -1,
                    minPrice: 0,
                    maxPrice: -1,
                    sortCase: -1,
                    ascSort: 0,
                    pageNumber: 0,
                    pageSize: 4

                };
                $scope.loadListProduct = function (categoryId) {
                    param.categoryId = categoryId;
                    util.callRequest('/products/filter', "POST", param).then(function (response) {
                        $scope.products = response.data.data;
                    });
                };

                $scope.loadListProductRecomad = function (categoryId, pNumber) {
                    paramRe.categoryId = categoryId;
                    paramRe.pageSize = 3;
                    paramRe.pageNumber = pNumber;
                    util.callRequest('/products/filter', "POST", paramRe).then(function (response) {
                        $scope.productRecomands = response.data.data;
                        $scope.totalPage = response.data.totalPage;
                    });
                };

                $scope.loadPageRe = function (page) {
                    if (page === 0) {
                        page += 2;
                    }
                    if(page === $scope.totalPage){
                        page -= 2;
                    }
                    if($scope.random < page){
                        $scope.random++;
                    }else{
                        $scope.random--;
                    }
                    $scope.loadListProductRecomad(-1, page);
                };

                $timeout(function () {
                    // init silder
                    $('#similar-product').carousel({
                        interval: 5000
                    });
                });

                $scope.addToCart = function (product) {
                    var item = {
                        product_id: product.productId,
                        default_image: product.defaultImage,
                        product_name: product.name,
                        price: product.salePrice,
                        quantity: $scope.quantity
                    };
                    cart.addItem(item, item.quantity);
                    toastr.success("Add product to cart successful");
                };

            }]);


