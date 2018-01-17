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
    }]);
