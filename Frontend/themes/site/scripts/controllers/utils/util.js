/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

'use strict';

angular.module( 'marketplace' )

// Utilities for our app
.factory( 'util', [ '$http', '$q', 'server', '$log', '_', 'api', '$modal', '$i18next', function ( $http, $q, server, $log, _, api, $modal, $i18next ) {
        
    // Check array has all empty value or not
    // Example [ '', '', '' ]
    function isAllValueEmpty( array ) {

        var check = true;

        _.forEach ( array, function ( item ) {

            if ( item && item !== null && item !== '' ) {

                check = false;
            }
        });

        return check;
    }    
        
    return {
        
        // Create interceptor request
        callRequest: function ( api, method, data, abstractUrl ) {
            
            var apiName = api, m = method, d = data;
            
            if ( angular.isObject( api ) ) {
                
                apiName = api.name;
                m = api.type || api.method;
                
                d = method;
            }
            
            var URL = abstractUrl ? apiName : server.URL + '/' + apiName;
            
            var dfd = $q.defer();
            
            $http( {
               
               url: URL,
               method: m || 'POST',
               data: d
                
            }).then( function ( result ) {
                
                // Redirect could be dismiss data
                // It happens when a request is posting while transition is executing
                if ( typeof result === 'undefined' ) {
                    
                     dfd.reject( data );
                 
                     return;
                }
                 
                var data = result.data;

                // Contain any error
                if ( 'errCode' in data && data.errCode > 0 && data.status !== 'OK' ) {

                    // Support trace error
                    $log.log( "Request error: ", api, data );

                    dfd.reject( data );
                }
                else 
                    dfd.resolve( data );
                
                
            }, function ( err ) {
                
                // We can handle this error at interceptor
                dfd.reject( err );
            });
            
            return dfd.promise;
        },
        
        // Get text from path
        translate: function ( path ) {
            
            return $i18next( path );
        },
        
        // Escape HTML
        escape: function ( text ) {
            
            return _.escape( text );
        },
        
        // Unescape HTML
        unescape: function ( text ) {
            
            return _.unescape( text );
        },
        
        // When using object to store param, you can be easy to extend your method but a little difficult to use
        // So that It's all up to you based on the context of using the method
        // @param {object} opts : The setting of modal
        // @param {function} dismissCallback : The dismiss callback
        openModal: function ( opts, dismissCallback ) {
            
            var setting = {
                
                backdrop: 'static',
                templateUrl: opts.tmpl,
                controller: opts.ctrl
            };
            
            if ( opts.data ) {
                
                setting.resolve = {
                    
                    data: function() { 
                        
                        return opts.data;
                    }
                };
            }
            
            var instance = $modal.open( setting );
            
            // Bind event dismiss modal
            instance.result.then( function ( type ) {
                
                if ( typeof dismissCallback === 'function' )
                    dismissCallback( type );
                
            });
            
            return instance;
        }
        
       
    };
}]);
