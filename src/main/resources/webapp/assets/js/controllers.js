

var dropangularApp = angular.module('dropangularApp', []);


Date.prototype.dateTime = function() {
   var yyyy = this.getFullYear().toString();
   var mm = (this.getMonth()+1).toString(); // getMonth() is zero-based
   var dd  = this.getDate().toString();
   var hh = this.getHours().toString();
   var min = this.getMinutes().toString();
   var sec = this.getSeconds().toString();
   var time = (hh[1]?hh:"0"+hh[0]) + ":" + (min[1]?min:"0"+min[0]) + ":" + (sec[1]?sec:"0"+sec[0]);
   var date = (dd[1]?dd:"0"+dd[0]) + "/" + (mm[1]?mm:"0"+mm[0]) + "/" + yyyy;
   return date + " " + time;
  };



dropangularApp.controller('CheckPointList', function($scope, $http){

            $http.get('/api/ping').success(function(data) {
                    var nodes = new Array();
                    _.each(data, function(el) {
                        var d = new Date(el.timestamp);
                        el.created = d.dateTime();
                        nodes.push(el);
                    });
                    $scope.pings = nodes;
              });

});


