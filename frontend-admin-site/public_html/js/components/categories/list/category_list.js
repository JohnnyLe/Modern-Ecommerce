'use strict';

angular.module('ec-admin.app', ['ec-admin'])
    .controller('CategoryListCtrl', [
        '$scope', 'Util', 'API', '$state', 'AppConfig', '$compile',
        function ($scope, Util, API, $state, AppConfig, $compile) {

            $scope.selected = [];
            $scope.curentSelected = [];
            $scope.pagination = {};
            $scope.selectedAll = false;
            $scope.searchString = "";
            var param = {
                search_key: "",
                sort_case: 1,
                asc_sort: 0,
                page_number: 1,
                page_size: 10
            };
            $scope.currentPage = 1;
            $scope.pageSize = 10;
            $scope.pageChanged = function () {
                $scope.loadListCategories();
            };


            $scope.doSort = function (sortCase) {
                $scope.loadListCategories(sortCase);
            };

            $scope.doSearch = function (keyEvent) {

                // press Enter key
                if (keyEvent.which === 13) {
                    if ($scope.searchString.trim().length > 0) {
                        $scope.loadListCategories();
                    }
                }
            };

            $scope.loadListCategories = function (sortCase) {
                param.page_number = $scope.currentPage;
                param.page_size = $scope.pageSize;
                if (sortCase) {
                    $scope.isSort = !$scope.isSort;
                    param.asc_sort = $scope.isSort;
                    param.sort_case = sortCase;
                }
                // reset state
                $scope.categories = [];
                $scope.curentSelected = [];
                $scope.pagination = {};
                $scope.selectedAll = false;
                param.search_key = $scope.searchString;

                Util.createRequest(API.LIST_CATEGORY, param, function (response) {
                    var status = response.status;
                    if (status === 200) {
                        $scope.categories = response.data.content;

                        $scope.categories.forEach(function (item) {
                            if (item.parentId) {
                                var parent = _.find($scope.categories, {'categoryId': item.parentId});
                                if (parent) {
                                    item.parent_name = parent.name;
                                } else {
                                    item.parent_name = null
                                }
                            } else {
                                item.parent_name = null
                            }
                        });

                        $scope.totalItems = response.data.totalElements;
                        $scope.totalPage = response.data.totalPages;
                        if ($scope.selected.length > 0) {
                            var numberCheckAll = 0;
                            $scope.categories.forEach(function (result) {
                                $scope.selected.forEach(function (selected) {
                                    if (result.categoryId === selected.categoryId) {
                                        result._selected = true;
                                        numberCheckAll++;
                                        if (numberCheckAll === $scope.categories.length) {
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

            $scope.confirmDeleteCategory = function (categoryId) {
                var listId = [];
                // show model confirm delete orders
                Util.showConfirmModal({
                    title: Util.translate('message.category.delete'),
                    message: Util.translate('message.category.delete_mgs')
                }, function () {
                    var ids = _.map($scope.selected, 'categoryId');
                    if (categoryId) {
                        listId.push(categoryId);
                    } else {
                        listId = ids;
                    }
                    // cal API cancel orders
                    Util.createRequest(API.DELETE_CATEGORY, listId, function (response) {

                        var status = response.status;
                        if (status === 200) {
                            $scope.selected = [];
                            // show message delete successfully
                            Util.showSuccessToast('message.category.delete_success');
                            // reload list
                            $scope.loadListCategories();
                        } else {
                            Util.showErrorToast('message.category.delete_error');
                        }
                    });
                });
            };

            $scope.toggleSelectAll = function (isSelectAll) {
                if (isSelectAll) {
                    if ($scope.selected.length === 0) {
                        angular.copy($scope.categories, $scope.selected);
                    } else {
                        $scope.selected = $scope.selected.concat($scope.categories);
                        for (var i = 0; i < $scope.selected.length; ++i) {
                            for (var j = i + 1; j < $scope.selected.length; ++j) {
                                if ($scope.selected[i].categoryId === $scope.selected[j].categoryId)
                                    $scope.selected.splice(j--, 1);
                            }
                        }
                    }
                } else {
                    $scope.categories.forEach(function (item) {
                        $scope.selected.forEach(function (selected) {
                            if (item.categoryId === selected.categoryId)
                                $scope.curentSelected.push(selected);
                        });
                    });
                    $scope.removeSelected($scope.curentSelected);
                }

                _.forEach($scope.categories, function (orders) {
                    orders._selected = isSelectAll;
                });
            };

            $scope.toggleSelected = function (obj) {
                if (angular.isObject(obj) && obj.categoryId) {
                    var index = _.findIndex($scope.selected, {'categoryId': obj.categoryId});
                    if (index > -1) {
                        $scope.selected.splice(index, 1);
                        obj._selected = false;
                    } else {
                        $scope.selected.push(obj);
                        obj._selected = true;
                    }
                    $scope.selectedAll = ($scope.selected.length === $scope.categories.length);
                    var numberCheckAll = 0;
                    $scope.categories.forEach(function (result) {
                        $scope.selected.forEach(function (selected) {
                            if (result.categoryId === selected.categoryId) {
                                result._selected = true;
                                numberCheckAll++;
                                $scope.selectedAll = numberCheckAll === $scope.categories.length;
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

            $scope.loadCategoryDetail = function (categoryId) {
                $state.go('categories.detail', {id: categoryId});
            };

            $scope.loadListCategories();

        }
    ]);