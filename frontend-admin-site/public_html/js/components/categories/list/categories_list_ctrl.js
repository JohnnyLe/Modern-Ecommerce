'use strict';

angular.module('ec-admin.app', ['ec-admin'])
    .controller('ListCategoriesCtrl', [
        '$scope', 'Util', 'API', '$state', 'AppConfig', '$compile',
        function ($scope, Util, API, $state, AppConfig, $compile) {

            function makeParam(data) {

                var pageNumber = data.start / data.length;

                var post = {
                    size: data.length,
                    page: pageNumber,
                    search: data.search.value
                };

                var sortDir = data.order[0].dir,
                    sortCol = data.order[0].column,
                    colName = data.columns[sortCol].name;

                post.sort = colName + ',' + sortDir;

                return post;
            }

            // Datatable obj
            $scope.categoryTable;

            $scope.categories = [];

            // Selected items
            $scope.selectedCategories = [];

            $scope.opts = {
                "order": [5, 'desc'],
                "pageLength": 10,
                "dom": '<"row dt-ctrl-wrapper"<"col-sm-6 text-left"f><"col-sm-6 text-right"l>><"row"<"col-sm-12"rt>>"<"row dt-ctrl-wrapper"<"col-sm-12 text-right"p>>',
                lengthChange: true,
                responsive: true,
                "ajax": function (data, callback) {

                    var params = makeParam(data);

                    Util.createRequest(API.LIST_CATEGORY, params,
                        function (dataCallback) {
                            console.log(dataCallback);

                            $scope.patients = dataCallback.data.content;

                            var json = {
                                "draw": data.draw,
                                "recordsTotal": $scope.total_records,
                                "recordsFiltered": $scope.total_records,
                                "data": []
                            };

                            $.each($scope.patients, function (key, value) {

                                // json.data.push(pa);
                            });
                            // Insert value
                            callback(json);
                        },
                        null,
                        'application/json;charset=utf-8'
                    );
                },
                "rowCallback": function (row, data, index) {
                    $("td:eq(0)", row).addClass("text-center").html("<input type='checkbox' class='icheckbox' role='child-checkbox' id='" + data.hpp_id + "' />");
                    $("td:eq(1)", row).addClass('text-center').html("<span>" + data.patient_clinic_id + "</span>");
                    $("td:eq(2)", row).addClass('text-center').html("<span>" + data.patient + "</span>");

                    $("td:eq(3)", row).addClass('text-center').html("<span>" + (data.last_visit !== null ? util.formatDate(data.last_visit, 'YYYY-MM-DD') : "") + "</span>");
                    $("td:eq(4)", row).addClass('text-center').html("<span>" + (data.next_visit !== null ? util.formatDate(data.next_visit, 'YYYY-MM-DD') : "") + "</span>");
                    $("td:eq(5)", row).addClass('text-center').html("<span>" + data.number_visits + "</span>");

                    $("td:eq(6)", row).addClass('text-center').html("<button style='margin: 2px' class='btn btn-success btn-xs' ng-click='viewDetail(\"" + index + "\")'>"
                        + util.translate("button.detail") + "</button>");
                },
                "drawCallback": function (s) {

                    // We should use directive to change data & bind event
                    checkbox.bindCheck(s.nTable, function (array) {

                        // We can get the list after items have been checked
                        $scope.selectedCategories = array;

                        // Let it knows
                        $scope.$digest();
                    });

                    // Using compile service
                    $compile(s.nTBody)($scope);
                },
                'columns': [
                    {'data': 'hpp_id'},
                    {'data': 'patient_clinic_id', 'name': 'patientFacilityNumber'},
                    {'data': 'patient', 'name': 'firstName,lastName'},
                    {'data': 'last_visit'},
                    {'data': 'next_visit'},
                    {'data': 'number_visits'},
                    {'data': null}
                ],
                'columnDefs': [
                    {'orderable': false, 'targets': [0, 6]}
                ]
            };

        }
    ]);