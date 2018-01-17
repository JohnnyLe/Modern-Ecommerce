'use strict';

angular.module('ec-admin.app', ['ec-admin'])
        .controller('ProductsListCtrl', [

            '$scope',
            'Util',
            'API',
            '$state',
            'AppConfig',

            function ($scope, Util, API, $state, AppConfig) {
                $scope.selected = [];
                $scope.pathFile = AppConfig.PATH_FILE;
                $scope.curentSelected = [];
                $scope.pagination = {};
                $scope.selectedAll = false;
                $scope.searchString = "";
                $scope.min_price = "";
                $scope.max_price = "";
                $scope.isSort = true;
                $scope.columnChange = "Name";
                $scope.image = 'img/no-image-available.png';
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
                    pageNumber: 1,
                    pageSize: 10

                };

                $scope.doSort = function (sortCase) {
                    $scope.loadListProduct(sortCase);
                };

                $scope.doSearch = function (keyEvent) {

                    // press Enter key
                    if (keyEvent.which === 13) {
                        if ($scope.max_price > 0 & $scope.max_price < $scope.min_price) {
                            Util.showErrorToast("message.product.err_price");
                        } else {
                            $scope.loadListProduct();
                        }
                    }
                };
                // Paging config max size show
                $scope.maxSize = 5;
                $scope.currentPage = 1;
                $scope.pageSize = 10;
                $scope.pageChanged = function () {
                    $scope.loadListProduct();
                };
                $scope.loadListProduct = function (sortCase) {
                    param.pageNumber = $scope.currentPage - 1;
                    param.pageSize = $scope.pageSize;
                    if (sortCase) {
                        $scope.isSort = !$scope.isSort;
                        param.ascSort = $scope.isSort;
                        param.sortCase = sortCase;
                    }
                    if ($scope.min_price !== null && $scope.min_price !== "") {
                        param.minPrice = $scope.min_price;
                    } else {
                        param.minPrice = 0;
                    }
                    if ($scope.max_price !== null && $scope.max_price !== "") {
                        param.maxPrice = $scope.max_price;
                    } else {
                        param.maxPrice = -1;
                    }
                    // reset state
                    $scope.listProduct = [];
                    $scope.curentSelected = [];
                    $scope.pagination = {};
                    $scope.selectedAll = false;
                    param.searchKey = $scope.searchString;
                    Util.createRequest(API.GET_LIST_PRODUCT, param, function (response) {
                        var status = response.status;
                        if (status === 200) {
                            $scope.listProduct = response.data.data;
                            $scope.totalItems = response.data.totalResult;
                            $scope.totalPage = response.data.totalPage;
                            if ($scope.selected.length > 0) {
                                var numberCheckAll = 0;
                                $scope.listProduct.forEach(function (result) {
                                    $scope.selected.forEach(function (selected) {
                                        if (result.productId === selected.productId) {
                                            result._selected = true;
                                            numberCheckAll++;
                                            if (numberCheckAll === $scope.listProduct.length) {
                                                $scope.selectedAll = true;
                                            }
                                        }
                                    });
                                });
                            }
                        } else {
                            Util.showErrorAPI(status);
                        }
                    });
                };

                $scope.confirmDeleteProduct = function (productId) {
                    var listId = [];
                    // show model confirm delete orders
                    Util.showConfirmModal({
                        title: Util.translate('message.product.delete'),
                        message: Util.translate('message.product.delete_mgs')
                    }, function () {
                        var productIds = _.map($scope.selected, 'productId');
                        if (productId) {
                            listId.push(productId);
                        } else {
                            listId = productIds;
                        }
                        console.log(listId);
                        // cal API cancel orders
                        Util.createRequest(API.DELETE_PRODUCT, listId, function (response) {

                            var status = response.status;
                            if (status === 200) {
                                $scope.selected = [];
                                // show message delete successfully
                                Util.showSuccessToast('message.product.delete_success');
                                // reload list
                                $scope.loadListProduct();
                            } else {
                                Util.showErrorToast('message.product.delete_error');
                            }
                        });
                    });
                };

                $scope.toggleSelectAll = function (isSelectAll) {
                    if (isSelectAll) {
                        if ($scope.selected.length === 0) {
                            angular.copy($scope.listProduct, $scope.selected);
                        } else {
                            $scope.selected = $scope.selected.concat($scope.listProduct);
                            for (var i = 0; i < $scope.selected.length; ++i) {
                                for (var j = i + 1; j < $scope.selected.length; ++j) {
                                    if ($scope.selected[i].productId === $scope.selected[j].productId)
                                        $scope.selected.splice(j--, 1);
                                }
                            }
                        }
                    } else {
                        $scope.listProduct.forEach(function (item) {
                            $scope.selected.forEach(function (selected) {
                                if (item.productId === selected.productId)
                                    $scope.curentSelected.push(selected);
                            });
                        });
                        $scope.removeSelected($scope.curentSelected);
                    }

                    _.forEach($scope.listProduct, function (orders) {
                        orders._selected = isSelectAll;
                    });
                };

                $scope.toggleSelected = function (obj) {

                    if (angular.isObject(obj) && obj.productId) {
                        console.log("$scope.selected", $scope.selected);
                        var index = _.findIndex($scope.selected, {productId: obj.productId});
                        if (index > -1) {
                            $scope.selected.splice(index, 1);
                            obj._selected = false;
                        } else {
                            $scope.selected.push(obj);
                            obj._selected = true;
                        }
                        $scope.selectedAll = ($scope.selected.length === $scope.listProduct.length);
                        var numberCheckAll = 0;
                        $scope.listProduct.forEach(function (result) {
                            $scope.selected.forEach(function (selected) {
                                if (result.productId === selected.productId) {
                                    result._selected = true;
                                    numberCheckAll++;
                                    if (numberCheckAll === $scope.listProduct.length) {
                                        $scope.selectedAll = true;
                                    } else {
                                        $scope.selectedAll = false;
                                    }
                                }
                            });
                        });
                    }
                };

                $scope.removeSelected = function (list) {
                    list.forEach(function (item) {
                        var index = $scope.selected.indexOf(item);
                        $scope.selected.splice(index, 1);
                    });
                    $scope.curentSelected = [];
                };

                $scope.loadProductDetail = function (productId) {
                    $state.go('products.detail', {id: productId});
                };

                $scope.loadListProduct();
            }
        ]);