'use strict';
angular.module('marketplace.checkoutdetails', ['marketplace.authen'])

        .controller('CheckOutCtrl', ['$scope', 'util', '$', '$timeout', '$stateParams', 'ShoppingCart', 'Session', 'toastr', '$cookies', '$state', function ($scope, util, $, $timeout, $stateParams, cart, Session, toastr, $cookies, $state) {
                setTimeout(function () {
                    $scope.$apply(function () {
                        $scope.currentUser = Session.getUser();
                        $scope.isLogin = angular.isObject($scope.currentUser);
                    });
                }, 2000);

                $scope.emailPattern = /^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$/;
                $scope.phoneNumber = /^\+?[0-9]\d{1,16}$/;
                $scope.submitting = false;
                $scope.items = cart.getItems();
                $scope.user = {
                    "userId": "",
                    "firstName": "",
                    "lastName": "",
                    "middleName": "",
                    "email": "",
                    "phone": "",
                    "fax": "",
                    "address": "",
                    "city": "",
                    "country": ""
                };

                $scope.createOrder = function () {
                    $scope.submitting = true;
                    var params = {};
                    var products = [];
                    for (var i = 0; i < $scope.items.length; i++) {
                        var product = {
                            "productId": "",
                            "quantity": 1
                        };
                        product.productId = $scope.items[i].product_id;
                        product.quantity = $scope.items[i].quantity;
                        products[i] = product;
                    }
                    
                    if($scope.isLogin){
                       $scope.user.userId = $scope.currentUser.userId; 
                       $scope.user.firstName = $scope.currentUser.firstName; 
                       $scope.user.lastName = $scope.currentUser.lastName; 
                       $scope.user.email = $scope.currentUser.email; 
                       $scope.user.phone = $scope.currentUser.phone; 
                       $scope.user.address = $scope.currentUser.address; 
                       $scope.user.city = $scope.currentUser.city; 
                       $scope.user.country = $scope.currentUser.country; 
                    }
                    
                    params.user = angular.copy($scope.user);
                    params.productList = products;
                    params.paymentId = 1;
                    util.callRequest('orders/create', "POST", params).then(function (response) {
                        var status = response.status;
                        if(status === 200){
                           toastr.success("Create Order Success");
                           cart.emptyCart();
                           $state.go('index');
                        }else{
                           toastr.error("Error when create orders"); 
                        }
                    });
                    $scope.submitting = false;
                };
            }]);



