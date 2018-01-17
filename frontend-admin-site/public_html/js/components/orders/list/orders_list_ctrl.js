'use strict';

angular.module('ec-admin.app', ['ec-admin'])
        .controller('ListOrderCtrl', [
            '$scope', 'Util', 'API', '$state', 'AppConfig', '$compile',
            function ($scope, Util, API, $state, AppConfig, $compile) {


                $scope.pagination = {};
                $scope.searchString = "";
                var param = {
                    searchKey: "",
                    sortCase: -1,
                    ascSort: 0,
                    pageNumber: 1,
                    pageSize: 10,
                    status:-1
                };
                // Paging config max size show
                $scope.maxSize = 5;
                $scope.currentPage = 1;
                $scope.pageSize = 10;
                $scope.pageChanged = function () {
                    $scope.loadListOrders();
                };

                $scope.doSort = function (sortCase) {
                    $scope.loadListOrders(sortCase);
                };
                $scope.doFilter = function () {
                    console.log("$scope.statusOrder :",$scope.statusOrder);
                    if($scope.statusOrder){
                         param.status = $scope.statusOrder;
                    }else{
                        param.status=-1;
                    }
                   
                    $scope.loadListOrders();
                };

                $scope.loadListOrders = function (sortCase) {
                    param.pageNumber = $scope.currentPage - 1;
                    param.pageSize = $scope.pageSize;
                    if (sortCase) {
                        $scope.isSort = !$scope.isSort;
                        param.ascSort = $scope.isSort;
                        param.sortCase = sortCase;
                    }
                    param.searchKey = $scope.searchString;
                    $scope.listOrders = [];
                    $scope.curentSelected = [];
                    $scope.pagination = {};
                    $scope.selectedAll = false;
                    Util.createRequest(API.GET_ORDER_BY_COMPANY, param, function (response) {

                        var status = response.status;
                        if (status === 200) {
                            $scope.listOrders = response.data.content;
                            $scope.totalItems = response.data.totalElements;
                            $scope.totalPage = response.data.totalPage;

                            _.map($scope.listOrders, function (item) {
                                item.fullname = item.customerFirstname + item.customerLastname;
                                switch (item.status) {
                                        case 0:
                                            item.status = "Pending";
                                            break;
                                        case 1:
                                            item.status = "Shipping";
                                            break;
                                        case 2:
                                            item.status = "Complete";
                                            break;
                                        default:
                                            item.status = "Pending";
                                            break;
                                    }
                                
                            });
                            console.log("List order : ",$scope.listOrders);
                        } else {
                            Util.showErrorAPI(status);
                        }
                    });
                };

                $scope.loadDetailOrders = function (id) {
                    $state.go('orders.detail', {id: id});
                };

                $scope.loadListOrders();

            }
        ]);