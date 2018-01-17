/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
'use strict';

angular.module('marketplace.directive', ['components', 'rzModule'])

// Using for list using datatable
        .directive('datatable', ['$', 'checkbox', 'app', '_', function ($, checkbox, app, _) {

                return {

                    restrict: "A",
                    scope: {

                        opts: '=datatableOpts', // From used scope
                        dt: '=datatableTable'
                    },
                    replace: true,
                    transclude: false,

                    compile: function () {

                        return function ($scope, element, attrs) {

                            var defaultOpts = {

                                "autoWidth": false,
                                "dom": '<"dt-table-info hidden-xs"i>tr<"text-center"p>', // Hide info for mobile devices
                                "lengthChange": false,
                                "searching": false,
                                "info": true,
                                "paging": true,
                                "pageLength": app.PAGE_LENGTH || 10,
                                "serverSide": true,
                                "processing": true,
                                "language": {
                                    "paginate": {
                                        "next": ">>",
                                        "previous": "<<"
                                    }
                                },
                                "order": [3, 'asc'],

                                // Disable sort
                                "columnDefs": [
                                    {'orderable': false, 'targets': [0, 1]}
                                ],

                                "rowCallback": function (row, data) {

                                    var s = angular.toJson(data); // we can use JSON.stringtify()

                                    // Checkbox
                                    $("td:eq(0)", row).html("<input type='checkbox' name='child' id='" + data.objectId + "'/>");
                                    // Number  - Hidden class
                                    $("td:eq(1)", row).addClass('hidden-xs');
                                    // ID
                                    $("td:eq(2)", row).html("<a class='link' ng-click='showCUDModal( 2, " + _.escape(s) + " )'>" + data[ $scope.opts.columns[ 2 ].data ] || data.objectId + "</a>");
                                }
                            };

                            // Apply datatable with given optiions
                            $scope.dt = $('#' + element[0].id).DataTable(angular.extend(defaultOpts, $scope.opts));

                            // Reinit if the option has been changed
                            $scope.$on('$dtHasChanged', function ( ) {

                                // Destroy
                                if ($scope.dt)
                                    $scope.dt.destroy();

                                // Reinit
                                $scope.dt = $('#' + element[0].id).DataTable(angular.extend(defaultOpts, $scope.opts));

                            }, true);

                            // We can use a service to handle checkbox
                            $scope.$on('$destroy', function () {

                                // Cleanup the array
                                checkbox.cleanUp();
                            });
                        };
                    }
                };

            }])

// Show error on form
        .directive('showErrors', function ($timeout) {

            return {

                restrict: 'A',

                require: '^form',

                link: function (scope, el, attrs, formCtrl) {

                    // find the text box element, which has the 'name' attribute
                    var inputEl = el[0].querySelector("[name]");
                    // convert the native text box element to an angular element
                    var inputNgEl = angular.element(inputEl);
                    // get the name on the text box
                    var inputName = inputNgEl.attr('name');

                    inputNgEl.bind('blur', function () {

                        el.toggleClass('has-error', formCtrl[inputName].$invalid);

                        // Set it as dirty when it's invalid
                        if (formCtrl[inputName].$invalid) {

                            formCtrl[inputName].$dirty = true;

                            // Notify that it's has changed
                            scope.$digest();
                        }
                    });

                    // Watch model
                    scope.$watch(function () {

                        return formCtrl[inputName].$invalid;

                    }, function (invalid) {

                        // Apply when dirty input
                        if (!formCtrl[inputName].$dirty && invalid) {
                            return;
                        }

                        el.toggleClass('has-error', invalid);

                        // Green color
                        // el.toggleClass('has-success', !invalid);

                    });

                    // Apply check
                    scope.$on('show-errors-check-validity', function () {

                        // Set model as dirty
                        formCtrl[inputName].$dirty = true;

                        el.toggleClass('has-error', formCtrl[inputName].$invalid);

                    });

                    // Reset form
                    scope.$on('show-errors-reset', function () {

                        $timeout(function () {

                            el.removeClass('has-error');

                        }, 0, false);

                    });
                }
            };
        })

// Selectbox directive
        .directive('selectbox', function ($, $timeout, _) {

            return {

                restrict: 'EA',
                // controller:'AlertController',
                templateUrl: 'template/select_box_tmpl.html',
                transclude: true,
                replace: true,
                // require: '^ngModel',
                scope: {
                    search: '@', // Get as text
                    src: '=', // Refer to the scoped object
                    optId: '@',
                    optName: '@',
                    // ngModel: '=',
                    model: '=',
                    multiple: '@',
                    opts: "@"
                            // close: '&' // invoke
                            // clear: '&'
                },
                compile: function () {

                    return function (scope, element, attrs, ctrl) {

                        scope.items = [];

                        var defaultOpts = {

                            width: '100%'
                        };

                        // Set multiple or not
                        if (scope.multiple === 'true')
                            element.attr('multiple', true);
                        else
                            element.removeAttr('multiple');

                        // Watch src
                        scope.$watch('src', function () {

                            // Filter then push to source
                            _.forEach(scope.src, function (item) {

                                var id = item[scope.optId], name = item[scope.optName];

                                if (id !== undefined && name !== undefined) {

                                    scope.items.push({
                                        id: id,
                                        name: _.unescape(name) // Avoid HTML characters
                                    });
                                }
                            });

                            // Set model
                            if (scope.model === undefined) {

                                scope.model = (scope.items.length > 0) ? scope.items[0].id : null;
                            }

                            $timeout(function () {

                                // Refresh plugin 
                                $(element[0]).selectpicker('refresh');
                            });

                        }, true);

                        // Listen model
                        scope.$watch('model', function (n) {

                            // Clean up
                            // Set value to element value
                            if (n && n !== null) {

                                // console.log( n );
                                $timeout(function () {

                                    // Refresh plugin 
                                    $(element[0]).selectpicker('val', n);
                                });
                            } else {

                                $(element[0]).selectpicker('val', null);
                            }

                        }, true);

                        $timeout(function () {

                            // Cache element
                            var $el = $(element[0]);

                            // Using selectpicker plugin
                            $el.selectpicker(angular.extend(defaultOpts, angular.fromJson(scope.opts || {})));

                            // Set as value
                            $el.selectpicker('refresh');

                            // Add placeholder for any selectbox
                            var menus = $('.bootstrap-select').find('.dropdown-menu');
                            menus.find('.bs-searchbox input[type=text]').attr('placeholder', "Search...");
                            // Listen change
                            $el.on('change', function () {

                                // This method is used to set value to this model & view
                                // ctrl.$setViewValue( this.value );
                                // scope.ngModel = $( this ).selectpicker( 'val' );
                                scope.model = $(this).selectpicker('val');
                            });
                            // Destroy element
                            scope.$on('$destroy', function () {

                                $el.selectpicker('destroy');
                            });
                        });
                    };
                }
            };
        })

        .directive('uaLogout', ['$timeout', 'user', function ($timeout, user) {

                return {
                    restrict: 'A',

                    link: function (scope, element, attrs) {

                        // Handler for logout
                        var evHandler = function (e) {

                            e.preventDefault();
                            // Do logout
                            user.logout();

                            return false;
                        };

                        element.on ? element.on('click', evHandler) : element.bind('click', evHandler);
                    }
                };
            }])

// auto hide menu when menu item is clicked
        .directive('menu', ['$', function ($) {
                return {
                    restrict: 'A',
                    scope: {
                        menuId: '@', // element id (sub menu) which will be add 'menuActiveClass' when hover
                        menuActiveClass: '@', // class name is added when menu (parent menu) is hovering
                        menuCloseQuery: '@' // css query of element which hide menu when it's clicked
                    },
                    link: function (scope, element) {
                        element.on('mouseenter', function () {
                            $('#' + scope.menuId).addClass(scope.menuActiveClass);
                        });
                        element.on('mouseleave', function () {
                            $('#' + scope.menuId).removeClass(scope.menuActiveClass);
                        });

                        $(scope.menuCloseQuery).on('click', function () {
                            $('#' + scope.menuId).removeClass(scope.menuActiveClass);
                        });
                    }
                };
            }])

// slider for home page
        .directive('slider', [function () {
                return {
                    restrict: 'EA',
                    replace: true,
                    templateUrl: 'template/slider_tmpl.html',
                    scope: {},
                    controller: ['$scope', 'util', '$timeout', function ($scope, util, $timeout) {
                            $scope.items = [];

                            $scope.loadData = function () {
                                util.callRequest('http://localhost:8383/eCommereTheme/json/slider.json', "GET", null, true).then(function (data) {
                                    $scope.items = data.results;
                                });
                            };

                            $scope.loadData();

                            $timeout(function () {
                                // init silder
                                $('#slider-carousel').carousel({
                                    interval: 5000
                                });
                            });
                        }]
                };
            }])

// left slidebar
        .directive('leftSlidebar', ['util', function (util) {
                return {
                    restrict: 'EA',
                    replace: true,
                    templateUrl: 'template/left_slidebar_tmpl.html',
                    scope: {
                        minPrice: '=?',
                        maxPrice: '=?',
                        minSliderValue: '=?',
                        maxSliderValue: '=?'
                    },
                    controller: ['$scope', 'util', '$timeout', '$', 'AppConstant', function ($scope, util, $timeout, $, AppConstant) {
                            var minPrice = angular.isDefined($scope.minPrice) ? $scope.minPrice : AppConstant.DEFAULT_MIN_PRICE_RANGE,
                                    maxPrice = angular.isDefined($scope.maxPrice) ? $scope.maxPrice : AppConstant.DEFAULT_MAX_PRICE_RANGE,
                                    minSliderValue = angular.isDefined($scope.minSliderValue) ? $scope.minSliderValue : $scope.minPrice,
                                    maxSliderValue = angular.isDefined($scope.maxSliderValue) ? $scope.maxSliderValue : $scope.maxPrice;


                            $scope.slider = {
                                min: 0,
                                max: 999,
                                options: {
                                    hideLimitLabels: true,
                                    floor: minPrice,
                                    ceil: maxPrice,
                                    translate: function (value) {
                                        return '$' + value;
                                    },
                                    onEnd: function () {
                                        // call API and set data response into 'result' variable
                                        var result = 'test result';
                                        // util.callRequest(...)
//                                        $scope.$broadcast('priceRangeResult', result);
                                        console.debug('todo');
                                    }
                                }
                            };
                            $scope.searchWithPrice = function () {
                                console.log($scope.slider);
                                $timeout(function () {
                                    $scope.$emit('priceRangeResult', $scope.slider);
                                });
                            };

                            $scope.categories = [];
                            $scope.brands = [];
                            var paramCategory = {
                                search_key: "",
                                sort_case: 1,
                                asc_sort: 0,
                                page_number: 1,
                                page_size: 100
                            };
                            $scope.loadData = function () {
                                // load categories                
                                util.callRequest('categories/list', 'GET', paramCategory).then(function (data) {
                                    $scope.categories = sortOutAllCategories(data.data.content, 0);
                                });

                                // load brands
                                util.callRequest('http://localhost:8383/eCommereTheme/json/brand-products.json', "GET", null, true).then(function (data) {
                                    $scope.brands = data.results;
                                });
                            };

                            $scope.loadData();

                            $timeout(function () {
                                // init price range
                                $('#sl2').slider();
                            });

                            function sortOutAllCategories(catArray, parentID) {
                                var cats = [];
                                if (typeof catArray !== 'undefined') {
                                    catArray.forEach(function (cat)
                                    {
                                        if (cat.parentId === parentID) {
                                            var out = sortOutAllCategories(catArray, cat.categoryId);
                                            var outJson = {};
                                            outJson.name = cat.name;
                                            outJson.id = cat.categoryId;
                                            outJson.child = out;
                                            cats.push(outJson);
                                            if (cat.position && cats.length > cat.position) {
                                                var temp = cats[cat.position - 1];
                                                cats[cat.position - 1] = outJson;
                                                cats[cats.length - 1] = temp;
                                            }
                                        }
                                    });
                                }
                                return cats;
                            }
                        }]
                };
            }])

        .directive('shoppingCart', [function () {
                return {
                    restrict: 'EA',
                    replace: true,
                    templateUrl: 'template/shopping_cart.html',
                    scope: {
                        'showPayment': '=?'
                    },
                    controller: ['$scope', 'ShoppingCart', '$state', 'AppConstant', function ($scope, cart, $state, AppConstant) {

                            $scope.pathFile = AppConstant.pathFile;
                            if (angular.isDefined($scope.minPrice)) {
                                $scope.showPayment = false;
                            }
//                            $scope.noQuantity = 1;
                            $scope.changeCart = function (index){
                                var quantity = parseInt($scope.items[index].quantity);
                                cart.addItem($scope.items[index], quantity);
                            };

                            $scope.items = [];

                            $scope.loadData = function () {
                                $scope.items = cart.getItems();
                            };

                            $scope.loadData();

                            $scope.increaseQuantity = function (index) {
                                var item = $scope.items[index];

                                if (item) {
                                    item.quantity += 1;
                                    // update cookie store
                                    cart.addItem(item, item.quantity);
                                }
                            };

                            $scope.decreaseQuantity = function (index) {
                                var item = $scope.items[index];

                                if (item && item.quantity > 1) {
                                    item.quantity -= 1;
                                    // update cookie store
                                    cart.addItem(item, item.quantity);
                                }
                            };

                            $scope.removeItem = function (index) {
                                var itemId = $scope.items[index].product_id;
                                // remove in list
                                $scope.items.splice(index, 1);
                                // update cookie store
                                cart.removeItem(itemId);
                                $state.reload();
                            };
                        }]
                };
            }])


        .directive('searchBox', ['util', function (util) {
                return {
                    restrict: 'EA',
                    replace: true,
                    template: '<input type="text" placeholder="Search" ng-model="searchKey" ng-keypress="doSearch($event)"/>',
                    controller: ['$scope', '$state', function ($scope, $state) {

                            $scope.doSearch = function (event) {
                                if (event.which === 13) {
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
                                        pageSize: 12

                                    };
                                    $state.go('index', {search: $scope.searchKey});
                                    param.searchKey = $scope.searchKey;
                                    util.callRequest('/products/filter', "POST", param).then(function (response) {
                                        var result = response.data;
                                        $scope.$broadcast('searchResult', result);
                                    });
                                }

                                // util.callRequest(...)

                                console.debug('Todo');
                            };
                        }]
                };
            }])
        .directive('ngElevateZoom', function () {
            return {
                restrict: 'A',
                link: function (scope, element, attrs) {
                    //Will watch for changes on the attribute
                    attrs.$observe('zoomImage', function () {
                        linkElevateZoom();
                    });

                    function linkElevateZoom() {
                        //Check if its not empty
                        if (!attrs.zoomImage)
                            return;
                        element.attr('data-zoom-image', attrs.zoomImage);
                        $(element).elevateZoom();
                    }

                    linkElevateZoom();

                }
            };
        });
