'use strict';

angular.module('ec-admin')

.directive('icheck', function ($timeout, $parse) {
    return {
        require: 'ngModel',
        link: function ($scope, element, $attrs, ngModel) {
            return $timeout(function () {
                var value;
                value = $attrs['value'];
                $scope.$watch($attrs['ngModel'], function (newValue) {
                    $(element).iCheck('update');
                });

                return $(element).iCheck({
                    checkboxClass: 'icheckbox_flat-blue',
                    radioClass: 'iradio_flat-blue'

                }).on('ifChanged', function (event) {
                    if ($(element).attr('type') === 'checkbox' && $attrs['ngModel']) {
                        $scope.$apply(function () {
                            return ngModel.$setViewValue(event.target.checked);
                        });
                    }
                    if ($(element).attr('type') === 'radio' && $attrs['ngModel']) {
                        return $scope.$apply(function () {
                            return ngModel.$setViewValue(value);
                        });
                    }
                });
            });
        }
    };
})

.directive('icheckTable', function ($timeout, $parse) {
    return {
        link: function ($scope, element, $attrs) {
            return $timeout(function () {
                var value;
                value = $attrs['value'];

                $(element).on('tblUpdateCheckbox', function () {
                    $(element).iCheck('update');
                });

                return $(element).iCheck({
                    checkboxClass: 'icheckbox_flat-blue',
                    radioClass: 'iradio_flat-blue'

                }).on('ifChanged', function (event) {
                    $(element).change();
                });
            });
        }
    };
})

.directive('icheckBasic', function ($timeout, $parse) {
    return {
        link: function ($scope, element, $attrs) {
            return $timeout(function () {
                var value;
                value = $attrs['value'];

                return $(element).iCheck({
                    checkboxClass: 'icheckbox_flat-blue',
                    radioClass: 'iradio_flat-blue'

                }).on('ifChanged', function (event) {
                    $(element).click();
                });
            });
        }
    };
});