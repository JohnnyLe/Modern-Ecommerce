/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
'use strict';

angular.module('marketplace.directive', [ 'components' ])

// Using for list using datatable
.directive( 'datatable', [ '$', 'checkbox', 'app', '_', function ( $, checkbox, app, _ ) {
   
   return {
       
        restrict: "A",
        scope: {
            
            opts: '=datatableOpts', // From used scope
            dt: '=datatableTable'
        },
        replace: true,
        transclude: false,
        
        compile: function () {
            
            return function ( $scope, element, attrs) {
                
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
                    "order": [ 3, 'asc' ],
                    
                     // Disable sort
                    "columnDefs": [
                        {'orderable': false, 'targets': [0, 1]}
                    ],
                    
                    "rowCallback": function( row, data ) {
                        
                        var s = angular.toJson( data ); // we can use JSON.stringtify()
                        
                        // Checkbox
                        $("td:eq(0)", row).html("<input type='checkbox' name='child' id='"+ data.objectId +"'/>");
                        // Number  - Hidden class
                        $("td:eq(1)", row).addClass( 'hidden-xs' );
                        // ID
                        $("td:eq(2)", row).html("<a class='link' ng-click='showCUDModal( 2, "+ _.escape( s ) +" )'>"+ data[ $scope.opts.columns[ 2 ].data ] || data.objectId +"</a>");
                    }
                };
                
                // Apply datatable with given optiions
                $scope.dt = $( '#' +  element[0].id ).DataTable( angular.extend( defaultOpts, $scope.opts ) );
                
                // Reinit if the option has been changed
                $scope.$on( '$dtHasChanged', function ( ) {
                    
                     // Destroy
                     if ( $scope.dt )
                         $scope.dt.destroy();
                     
                     // Reinit
                     $scope.dt = $( '#' +  element[0].id ).DataTable( angular.extend( defaultOpts, $scope.opts ) );
                    
                }, true );
                
                // We can use a service to handle checkbox
                $scope.$on( '$destroy', function () {
                    
                    // Cleanup the array
                    checkbox.cleanUp();
                });
            };
        }
   };
   
}])

// Show error on form
.directive('showErrors', function( $timeout ) {
    
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

              inputNgEl.bind('blur', function() {
                  
                  el.toggleClass('has-error', formCtrl[inputName].$invalid);
                  
                  // Set it as dirty when it's invalid
                  if ( formCtrl[inputName].$invalid ) {
                      
                      formCtrl[inputName].$dirty = true;
                      
                      // Notify that it's has changed
                      scope.$digest();
                  }
              });
              
              // Watch model
              scope.$watch( function() {
                  
                  return formCtrl[inputName].$invalid;

              }, function( invalid ) {
                  
                   // Apply when dirty input
                   if ( !formCtrl[inputName].$dirty && invalid) { return; }
                  
                   el.toggleClass('has-error', invalid);
                   
                   // Green color
                   // el.toggleClass('has-success', !invalid);
                  
              });
              
              // Apply check
              scope.$on('show-errors-check-validity', function() {

                    // Set model as dirty
                    formCtrl[inputName].$dirty = true;  

                    el.toggleClass('has-error', formCtrl[inputName].$invalid );
                
              });
              
              // Reset form
              scope.$on('show-errors-reset', function() {

                  $timeout(function() {
                      
                    el.removeClass('has-error');
                    
                  }, 0, false);

              });
        }
    };
})

// Selectbox directive
.directive( 'selectbox', function ( $, $timeout, _ ) {

    return {

        restrict:'EA',
        // controller:'AlertController',
        templateUrl:'template/select_box_tmpl.html',
        transclude:true,
        replace:true,
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

            return function ( scope, element, attrs, ctrl ) {
                
                scope.items = [];
                
                var defaultOpts = {
                    
                    width: '100%'
                };
                 
                // Set multiple or not
                if ( scope.multiple  === 'true' )
                    element.attr( 'multiple', true );
                else
                    element.removeAttr( 'multiple' );
                
                // Watch src
                scope.$watch( 'src', function () {
                        
                        // Filter then push to source
                        _.forEach( scope.src, function ( item ) {

                            var id = item[scope.optId], name = item[scope.optName];

                            if ( id !== undefined && name !== undefined ) {

                                scope.items.push( {
                                    id: id,
                                    name: _.unescape( name ) // Avoid HTML characters
                                });
                            }
                        });
                        
                        // Set model
                        if ( scope.model === undefined ) {
                            
                            scope.model = ( scope.items.length > 0 ) ? scope.items[0].id : null;
                        }
                        
                        $timeout( function () {
                            
                            // Refresh plugin 
                            $( element[0] ).selectpicker('refresh');
                        });
                    
                }, true );
                
                // Listen model
                scope.$watch( 'model', function ( n ) {
                    
                    // Clean up
                    // Set value to element value
                    if ( n && n !== null ) {

                        // console.log( n );
                        $timeout( function () {
                            
                            // Refresh plugin 
                            $( element[0] ).selectpicker('val', n );
                        });
                    }
                    else {
                        
                        $( element[0] ).selectpicker('val', null );
                    }

                }, true );
                
                $timeout( function () {

                    // Cache element
                    var $el = $( element[0] );
                    
                    // Using selectpicker plugin
                    $el.selectpicker( angular.extend( defaultOpts, angular.fromJson( scope.opts || {} ) ) );
                    
                    // Set as value
                    $el.selectpicker( 'refresh' );
                    
                    // Add placeholder for any selectbox
                    var menus = $( '.bootstrap-select' ).find( '.dropdown-menu' );
                            menus.find( '.bs-searchbox input[type=text]').attr( 'placeholder', "Search..." );
                    // Listen change
                    $el.on( 'change', function () {
                        
                        // This method is used to set value to this model & view
                        // ctrl.$setViewValue( this.value );
                        // scope.ngModel = $( this ).selectpicker( 'val' );
                        scope.model = $( this ).selectpicker( 'val' );
                    });            
                    // Destroy element
                    scope.$on( '$destroy', function () {
                       
                       $el.selectpicker( 'destroy' );
                    });
                });
            };
        }
    };
})

.directive('uaLogout', ['$timeout', 'user', function( $timeout, user) {
        
    return {
        restrict: 'A',

        link: function( scope, element, attrs ) {
            
            // Handler for logout
            var evHandler = function(e) {
                
                e.preventDefault();
                // Do logout
                user.logout();

                return false;
            };

            element.on ? element.on('click', evHandler) : element.bind('click', evHandler);
        }
    };
}])

.directive('menu', ['$', function( $ ) {
    return {
        restrict: 'A',
        scope: {
            menuId: '@', // element id (sub menu) which will be add 'menuActiveClass' when hover
            menuActiveClass: '@', // class name is added when menu (parent menu) is hovering
            menuCloseQuery: '@' // css query of element which hide menu when it's clicked
        },
        link: function( scope, element ) {
            element.on('mouseenter', function() {
                $('#'+scope.menuId).addClass(scope.menuActiveClass);
            });
            element.on('mouseleave', function() {
                $('#'+scope.menuId).removeClass(scope.menuActiveClass);
            });
            
            $(scope.menuCloseQuery).on('click', function() {
                $('#'+scope.menuId).removeClass(scope.menuActiveClass);
            });
        }
    };        
}])

// slider for home page
.directive('slider', [function() {
    return {
        restrict: 'EA',
        replace: true,
        templateUrl: 'template/slider_tmpl.html',
        scope: {},
        controller: ['$scope', 'util', '$timeout', function( $scope, util, $timeout ) {
            $scope.items = [];
            
            $scope.loadData = function () {
                util.callRequest('http://localhost:8383/eCommereTheme/json/slider.json', "GET", null, true).then(function (data) {
                    $scope.items = data.results;
                });
            };
            
            $scope.loadData();
            
            $timeout(function() {
                // init silder
                $('#slider-carousel').carousel({
                    interval: 5000
                });
            });
        }]
    };        
}])

// left slidebar
.directive('leftSlidebar', [function() {
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
        controller: ['$scope', 'util', '$timeout', '$', 'AppConstant', function( $scope, util, $timeout, $, AppConstant ) {
            $scope.minPrice = angular.isDefined($scope.minPrice) ? $scope.minPrice : AppConstant.DEFAULT_MIN_PRICE_RANGE;
            $scope.maxPrice = angular.isDefined($scope.maxPrice) ? $scope.maxPrice : AppConstant.DEFAULT_MAX_PRICE_RANGE;
            $scope.minSliderValue = angular.isDefined($scope.minSliderValue) ? $scope.minSliderValue : $scope.minPrice;
            $scope.maxSliderValue = angular.isDefined($scope.maxSliderValue) ? $scope.maxSliderValue : $scope.maxPrice;
            $scope.categories = [];
            $scope.brands = [];
            
            $scope.loadData = function () {
                // load categories                
                util.callRequest('api/category/list', 'GET').then( function( data ) {
                    $scope.categories = sortOutAllCategories(data.result, 0);
                    
//                    console.debug(data.result);
//                    console.debug($scope.categories);
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
                catArray.forEach(function (cat)
                {
                    if (cat.parent_id === parentID) {
                        var out = sortOutAllCategories(catArray, cat.category_id);
                        var outJson = {};
                        outJson.name = cat.name;
                        outJson.id = cat.category_id;
                        outJson.child = out;
                        cats.push(outJson);
//                        if (cats.length > cat.position) {
//                            var temp = cats[cat.position - 1];
//                            cats[cat.position - 1] = outJson;
//                            cats[cats.length - 1] = temp;
//                        }
                    }
                });

                return cats;
            }
        }]
    };        
}]);