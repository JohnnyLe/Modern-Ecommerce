'use strict';

angular.module('marketplace.login', [])

.controller('LoginCtrl', [ '$scope', 'user', function( $scope, user ) {
    
    $scope.id='demo@marketplace.com';
    $scope.pass='123456';
    // Alert array
    $scope.alerts = [], $scope.submitting = false;
    
    // Close alert
    $scope.closeAlert = function( index ) {
        $scope.alerts.splice(index, 1);
    };
    
//  We can watch this model like this
//    $scope.$watch( 'user.id.$modelValue', function () {
//        
//        console.log( 'changed' )
//        
//    }, true );
    
    // Handle submit action
    $scope.submit = function () {
       
        // Prevent submit multiple time
       if ( $scope.submitting )
           return;
        
       if ( $scope.user.id.$modelValue === undefined || $scope.user.pass.$modelValue === undefined ) {
           
           $scope.alerts = [{
                type: 'danger',
                msg: "Invalid ID or password"
           }];
       
           return;
       }
       
       $scope.submitting = true;
       
       // Do login
       user.login({
           
           id: $scope.user.id.$modelValue,
           pass: $scope.user.pass.$modelValue
           
       }, function ( err ) {
           
           // Handle error
           $scope.submitting = false;
           
           $scope.alerts = [{
                type: 'danger',
                msg: "Invalid ID or password"
           }];
           
       });
      
   };
   
}]);