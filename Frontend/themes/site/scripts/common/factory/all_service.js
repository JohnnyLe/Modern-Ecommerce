'use strict';

// Components
angular.module('components', [])

// Lodash
.factory('_', function() {
    
    return window._;
})

// jQuery
.factory('$', function() {
    
    return window.$;
})

// Noty - Toast notification
.factory('noty', function() {
    
    return window.noty;
})

// Checkbox factory
// Provide checkbox service
.factory( 'checkbox', [ '_', '$', function ( _, $ ) {
    
    var array = [];
    
    return {
        
        // This function should be put in #drawCallback of datatable
        // I use this way to increase performance instead using directive
        // @param {string} wrapper selector wrapper these checkbox
        // @param {function} callback : the callback after item has been checked
        bindCheck: function ( wrapper, callback ) {
            
            // Get checkbox
            var parent = $( wrapper ).find( 'input:checkbox[name=parent]' ),
                    children = $( wrapper ).find( 'input:checkbox[name=child]' );
            
            // Render first
            var isAll = true;
            
            _.forEach( children, function ( child ) {
                
                if ( _.contains( array, child.id )) {
                    
                    child.checked = true;
                }
                else 
                    isAll = false;
            });
            
            // Enable check all
            ( isAll && children.length > 0 ) ? parent.prop( 'checked', true ) : parent.prop( 'checked', false );
            
            // Find button check-all
            // Prevent mutiple bind click
            parent.unbind( 'click' ).bind( 'click', function () {
                
                if ( this.checked ) {
                    
                    // Add all
                    _.forEach( children, function ( child ) {
                       
                        if ( !child.checked ) {
                            
                            if ( !_.contains( array, child.id )) 
                                array.push( child.id );
                        }
                    });
                }
                else {
                    // Remove all
                    _.forEach( children, function ( child ) {
                        
                        if ( child.checked ) {
                            
                            if ( _.contains( array, child.id )) 
                                
                                _.remove( array, function ( item ) {

                                    return item === child.id;
                                });
                        }
                    });
                }
                
                // Check/uncheck children
                ( this.checked ) ? children.prop( 'checked', true ) : children.prop( 'checked', false );
                
                // Invoke callback
                ( callback && typeof callback === 'function' ) && callback( array );
            });
            
            // Find child button
            children.bind( 'click', function () {
                
                var that = this, id = that.id;
                
                // Check
                if ( that.checked ) {

                    if ( !_.contains( array, id ) )
                        array.push( id );
                }
                else {
                    // Remove
                    _.remove( array, function ( item ) {

                        return item === id;
                    });
                }
                
                // Length of item is not checked
                var l = children.filter( function () {
                    
                    return this.checked === false;
                    
                }).length;
                
                // Enable check all
                ( l > 0 ) ? parent.prop( 'checked', false ) : parent.prop( 'checked', true );
                
                // Invoke callback
                ( callback && typeof callback === 'function' ) && callback( array );
            });
        },
        
        // Get/set array
        list: function ( value ) {
            
            if ( value ) {
                
                array = value;
            }
            
            return array;
        },
        
        // Cleanup
        cleanUp: function () {
            
            array = [];
        }
    };
}]);

