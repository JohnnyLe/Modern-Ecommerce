'use strict';

angular.module('ec-admin.app', ['ec-admin'])
        .controller('OrderDetailCtrl', [
            '$scope', 'Util', 'API', '$state', 'AppConfig', '$compile', '$stateParams',
            function ($scope, Util, API, $state, AppConfig, $compile, $stateParams) {
                $scope.orderDetail = {
                    cusName: "",
                    cusEmail: "",
                    cusPhone: "",
                    cusAddress: "",
                    cusGender: "",
                    cusPayment: ""
                };
                $scope.pathFile = AppConfig.PATH_FILE;
                $scope.image = 'img/no-image-available.png';
                $scope.ordersStatus = "0";
                $scope.totalPrice = "0 $";
                $scope.changeStatus = function () {
                    Util.showConfirmModal({
                        title: Util.translate('message.order.change_status'),
                        message: Util.translate('message.order.change_status_msg')
                    }, function () {
                        Util.createRequest(API.CHANGE_STATUS_ORDERS.path + $stateParams.id + "/" + parseInt($scope.ordersStatus), function (response) {
                            var status = response.status;
                            if (status === 200) {
                                $scope.selected = [];
                                // show message delete successfully
                                Util.showSuccessToast('message.order.change_status_success');
                            } else {
                                Util.showErrorToast('message.order.change_status_error');
                            }
                        }).finally(function () {
                            $scope.submitting = false;
                        });
                    });
                };
                $scope.listProduct = [];
                $scope.getOrderDetail = function () {
                    Util.createRequest(API.DETAIL_ORDERS.path + $stateParams.id, function (response) {
                        var status = response.status;
                        if (status === 200) {
                            //todo
                            $scope.ordersStatus = response.data.orders.status.toString();
                            $scope.orderDetail.cusName = response.data.orders.customerFirstname + " " + response.data.orders.customerMiddlename + " " + response.data.orders.customerLastname;
                            $scope.orderDetail.cusEmail = response.data.orders.customerEmail;
                            $scope.orderDetail.cusGender = response.data.orders.customerGender;
                            $scope.orderDetail.cusAddress = response.data.orderAddress.adress;
                            $scope.orderDetail.cusPhone = response.data.orderAddress.phone;

                            var listOrdersDetail = [];
                            var countPrice = 0;
                            listOrdersDetail = response.data.listOrdersDetail;
                            listOrdersDetail.forEach(function (result) {
                                var product = {
                                    name: "",
                                    image: "",
                                    quantity: 0,
                                    price: 0
                                };
                                product.name = result.product.name;
                                product.image = result.product.defaultImage;
                                product.quantity = result.ordersDetail.quantity;
                                product.price = result.ordersDetail.price;
                                product.payment = result.payment.name;
                                $scope.listProduct.push(product);
                                countPrice = countPrice + result.ordersDetail.price;
                            });
                            $scope.totalPrice = countPrice + " $";
                        } else {
                            Util.showErrorAPI(status);
                        }
                    }).finally(function () {
                        $scope.submitting = false;
                    });
                };

                $scope.getOrderDetail();
            }
        ]);


