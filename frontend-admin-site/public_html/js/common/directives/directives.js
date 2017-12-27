'use strict';

angular.module('ec-admin')
// Compare to another value
// Such as confirm password
    .directive("compareTo", function () {

        return {
            require: "ngModel",
            scope: {
                otherModelValue: "=compareTo"
            },
            link: function (scope, element, attributes, ngModel) {

                ngModel.$validators.compareTo = function (modelValue) {

                    var cp = (modelValue === scope.otherModelValue.$modelValue);
                    ngModel.$setValidity('compare', cp);
                    return cp;
                };

                scope.$watch("otherModelValue.$modelValue", function () {

                    ngModel.$validate();
                });
            }
        };
    })

    // Another way check repeat
    .directive("repeatPassword", function () {
        return {
            require: "ngModel",
            link: function (scope, elem, attrs, ctrl) {

                var otherInput = elem.inheritedData("$formController")[attrs.repeatPassword];

                ctrl.$parsers.push(function (value) {
                    if (value === otherInput.$viewValue) {
                        ctrl.$setValidity("repeat", true);
                        return value;
                    }
                    ctrl.$setValidity("repeat", false);
                    // ctrl.$invalid = false;
                });

                otherInput.$parsers.push(function (value) {
                    ctrl.$setValidity("repeat", value === ctrl.$viewValue);
                    // ctrl.$invalid = ( value === ctrl.$viewValue );
                    return value;
                });
            }
        };
    })
    .directive('ngFileModel', ['$parse', function ($parse) {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
                var model = $parse(attrs.ngFileModel);
                var isMultiple = attrs.multiple;
                var modelSetter = model.assign;
                element.bind('change', function () {
                    var values = [];
                    angular.forEach(element[0].files, function (item) {
                        var value = {
                            // File Name
                            name: item.name,
                            //File Size
                            size: item.size,
                            //File URL to view
                            url: URL.createObjectURL(item),
                            // File Input Value
                            _file: item
                        };
                        values.push(value);
                    });
                    scope.$apply(function () {
                        if (isMultiple) {
                            modelSetter(scope, values);
                        } else {
                            modelSetter(scope, values[0]);
                        }
                    });
                });
            }
        };
    }])

    // Datatable directive
    .directive('datatable', [
        'app', '$timeout', 'checkbox', 'util', 'session',
        function (app, $timeout, checkbox, util, session) {

            return {
                restrict: "A",
                scope: {
                    opts: '=datatableOpts', // Options
                    dt: '=datatable' // DT object
                },
                replace: true,
                transclude: false,
                compile: function () {

                    return function ($scope, element, attrs) {

                        var lang = session.getUser().lang;
                        var defaultOpts = {
                            "autoWidth": false,
                            // "dom": '<"dt-table-info hidden-xs"i>tr<"text-center"p>', // Hide info for mobile
                            // devices
                            "dom": 'R<"row dt-ctrl-wrapper"<"col-sm-6"f><"col-sm-6 text-right"p>>rt',
                            "lengthChange": false,
                            // "searching": false,
                            "info": true,
                            "paging": true,
                            "pageLength": 10,
                            "serverSide": true,
                            "processing": true,
                            "language": {
                                "paginate": {
                                    "next": ">>",
                                    "previous": "<<"
                                },
                                "lengthMenu": util.translate('datatable.sLengthMenu'),
                                "search": util.translate('datatable.sSearch'),
                                "info": util.translate('datatable.sInfo'),
                                "infoEmpty": util.translate('datatable.sInfoEmpty'),
                                "emptyTable": util.translate('datatable.sEmptyTable'),
                                "processing": util.translate('datatable.sProcessing')
                                //"url": lang ==="ja" ? "/carereachd/i18n/ja.datatable.json" :
                                // "/carereachd/i18n/en.datatable.json"
                            },
                            "order": [2, 'asc'],
                            // Disable sort
                            "columnDefs": [
                                {'orderable': false, 'targets': [0, 1]}
                            ],
                            "rowCallback": function (row, data) {
                                // var s = angular.toJson( data ); // we can use JSON.stringtify()
                            }
                        };

                        // Apply datatable with given options

                        $scope.dt = element.DataTable(angular.extend({}, defaultOpts, $scope.opts));

                        $scope.$emit("$dtLoaded", $scope.dt);

                        // Reinit if the option has been changed
                        $scope.$on('$dtHasChanged', function (event, opt) {

                            // Destroy
                            $scope.dt && $scope.dt.destroy();

                            // Reinit
                            $scope.dt = element.DataTable(angular.extend({}, defaultOpts, opt || $scope.opts));

                        }, true);

                        // We can use a service to handle checkbox
                        $scope.$on('$destroy', function () {
                            // Cleanup the array
                            checkbox.cleanUp();
                        });
                    };
                }
            };
        }]);
