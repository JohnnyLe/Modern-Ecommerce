'use strict';

angular.module('ec-admin.app', ['ec-admin'])
        .controller('ListUsersCtrl', [

            '$scope',
            'Util',
            'API',
            '$state',
            'AppConfig',

            function ($scope, Util, API, $state, AppConfig) {

                $scope.selected = [];
                $scope.curentSelected = [];
                $scope.pagination = {};
                $scope.selectedAll = false;
                $scope.searchString = "";
                $scope.isSort = true;
                var param = {
                    searchKey: "",
                    sortCase: -1,
                    ascSort: 1,
                    pageNumber: 1,
                    pageSize: 10
                };
                $scope.currentPage = 1;
                $scope.pageSize = 10;
                $scope.pageChanged = function () {
                    $scope.loadListUsers();
                };



                $scope.doSort = function (sortCase) {
                    $scope.loadListUsers(sortCase);
                };

                $scope.loadListUsers = function (sortCase) {
                    param.pageNumber = $scope.currentPage - 1;
                    param.pageSize = $scope.pageSize;
                    if (sortCase) {
                        $scope.isSort = !$scope.isSort;
                        param.ascSort = $scope.isSort;
                        param.sortCase = sortCase;
                    }
                    // reset state
                    $scope.listProduct = [];
                    $scope.curentSelected = [];
                    $scope.pagination = {};
                    $scope.selectedAll = false;
                    param.searchKey = $scope.searchString;
                    
                    Util.createRequest(API.GET_USER_LIST, param, function (response) {

                        var status = response.status;
                        if (status === 200) {
                            $scope.users = response.data.data;
                            $scope.totalItems = response.data.totalResult;
                            $scope.totalPage = response.data.totalPage;
                            if ($scope.selected.length > 0) {
                                var numberCheckAll = 0;
                                $scope.users.forEach(function (result) {
                                    $scope.selected.forEach(function (selected) {
                                        if (result.userId === selected.userId) {
                                            result._selected = true;
                                            numberCheckAll++;
                                            if (numberCheckAll === $scope.users.length) {
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

                $scope.confirmDeleteUser = function (userId) {
                    var listId = [];
                    // show model confirm delete orders
                    Util.showConfirmModal({
                        title: Util.translate('message.user.delete'),
                        message: Util.translate('message.user.delete_mgs')
                    }, function () {
                        var userIds = _.map($scope.selected, 'userId');
                        if (userId) {
                            listId.push(userId);
                        } else {
                            listId = userIds;
                        }
                        // cal API cancel orders
                        Util.createRequest(API.DELETE_USER, listId, function (response) {

                            var status = response.status;
                            if (status === 200) {
                                $scope.selected = [];
                                // show message delete successfully
                                Util.showSuccessToast('message.delete_success');
                                // reload list
                                $scope.loadListUsers();
                            } else {
                                Util.showErrorToast('message.delete_error');
                            }
                        });
                    });
                };

                $scope.toggleSelectAll = function (isSelectAll) {
                    if (isSelectAll) {
                        if ($scope.selected.length === 0) {
                            angular.copy($scope.users, $scope.selected);
                        } else {
                            $scope.selected = $scope.selected.concat($scope.users);
                            for (var i = 0; i < $scope.selected.length; ++i) {
                                for (var j = i + 1; j < $scope.selected.length; ++j) {
                                    if ($scope.selected[i].userId === $scope.selected[j].userId)
                                        $scope.selected.splice(j--, 1);
                                }
                            }
                        }
                    } else {
                        $scope.users.forEach(function (item) {
                            $scope.selected.forEach(function (selected) {
                                if (item.userId === selected.userId)
                                    $scope.curentSelected.push(selected);
                            });
                        });
                        $scope.removeSelected($scope.curentSelected);
                    }

                    _.forEach($scope.users, function (orders) {
                        orders._selected = isSelectAll;
                    });
                };

                $scope.toggleSelected = function (obj) {
                    if (angular.isObject(obj) && obj.userId) {
                        var index = _.findIndex($scope.selected, {'userId': obj.userId});
                        if (index > -1) {
                            $scope.selected.splice(index, 1);
                            obj._selected = false;
                        } else {
                            $scope.selected.push(obj);
                            obj._selected = true;
                        }
                        $scope.selectedAll = ($scope.selected.length === $scope.users.length);
                        var numberCheckAll = 0;
                        $scope.users.forEach(function (result) {
                            $scope.selected.forEach(function (selected) {
                                if (result.userId === selected.userId) {
                                    result._selected = true;
                                    numberCheckAll++;
                                    if (numberCheckAll === $scope.users.length) {
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

                $scope.loadUserDetail = function (userId) {
                    $state.go('users.edit', {id: userId});
                };

                $scope.loadListUsers();
            }
        ]);