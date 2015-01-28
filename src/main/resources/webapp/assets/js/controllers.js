

var checkpointApp = angular.module('checkpointApp', []);


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


checkpointApp.controller('CheckPointHome', function($scope, $http){



});

checkpointApp.controller('CheckPointList', function($scope, $http){


            $http.get('/api/checkpoint/machines').success(function(data) {
                    // format dates

                    var nodes = new Array();
                    _.each(data, function(el) {
                        var d = new Date(el.joined);
                        var d2 = new Date(el.lastHb);
                        el.joined = d.dateTime();
                        el.lastHb = d2.dateTime();
                          nodes.push(el);
                    });
                    $scope.instances = nodes;
              });

});


checkpointApp.controller('CheckPointCluster', function($scope, $http){


            $http.get('/api/checkpoint/clusters').success(function(data) {

                    $scope.clusters = nodes;
              });

});


checkpointApp.controller('Ec2Ctrl', function($scope, $http){

            var tdata = [

                {
                    id: 90,
                    instanceId: "i-b5706159",
                    ami: "ami-4ae27e22",
                    privatednsname: "ip-172-31-15-154.ec2.internal",
                    keyname: "ivan",
                    instancetype: "m3.xlarge",
                    availabilityzone: "us-east-1c",
                    groupname: null,
                    privateipaddress: "172.31.15.154",
                    name: "Spark 1.1.2 Slave - Shing",
                    iamprofile: "arn:aws:iam::756033317358:instance-profile/ec2_t0_s3_backups",
                    product: "Axon",
                    department: "Ai",
                    tags: null,
                    partofcluster: false,
                    statecode: 80,
                    state: "stopped"
                    },
                    {
                        id: 93,
                        instanceId: "i-2cd2f1c0",
                        ami: "ami-4ae27e22",
                        privatednsname: "ip-172-31-0-8.ec2.internal",
                        keyname: "ivan",
                        instancetype: "c3.2xlarge",
                        availabilityzone: "us-east-1c",
                        groupname: null,
                        privateipaddress: "172.31.0.8",
                        name: "Cassandra Axon",
                        iamprofile: "arn:aws:iam::756033317358:instance-profile/ec2_t0_s3_backups",
                        product: "Axon",
                        department: null,
                        tags: null,
                        partofcluster: false,
                        statecode: 16,
                        state: "running"
                        }
            ];

            var instanceTypes = new Array();
             instanceTypes.push("");

            $http.get('/api/ec2').success(function(data) {
                    var nodes = new Array();
                    _.each(data, function(el) {

                        if(!_.contains(instanceTypes, el.instancetype)){
                            instanceTypes.push(el.instancetype);
                        }

                        if(el.statecode == 16){
                            el.stateIcon = "fa fa-cloud-upload";
                        }else{
                            el.stateIcon = "fa fa-cloud";
                        }
                        nodes.push(el);
                     });
                    instanceTypes.sort();
                    $scope.instanceTypes = instanceTypes;
                    $scope.instances = nodes;
              });

            // $scope.instances = tdata;

});
