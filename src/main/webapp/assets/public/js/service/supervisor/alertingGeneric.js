angular.module('asti.supervisor').factory('alertingGeneric',['$timeout', function($timeout) {
        
        var currentAlerts = [];
        var alertTypes = ["success", "info", "warning", "danger", "arrow_box1"];

        var addWarning = function (message) {
            console.log("warning "+message);
            addAlert("warning", message);
        };

        var addDanger = function (message) {
            addAlert("danger", message);
        };

        var addInfo = function (message) {
            addAlert("info", message);
        };

        var addSuccess = function (message) {
            addAlert("success", message);
        };
        

        var addAlert = function (type, message) {
        	
    		var alert = { type: type, message: message };
    		currentAlerts.push(alert);
            console.log("warning push");

    		$timeout(function () {
    			removeAlert(alert);
    		}, 5000);
        	
        };

        var removeAlert = function (alert) {
            for (var i = 0; i < currentAlerts.length; i++) {
                if (currentAlerts[i] === alert) {
                    currentAlerts.splice(i, 1);
                    break;
                }
            }
        };
        
        var removeAllAlerts = function () {
            currentAlerts.length = [];
        };

        var errorHandler = function (description) {
            return function () {
                addDanger(description);
            };
        };
        

        return {
            addWarning: addWarning,
            addDanger: addDanger,
            addInfo: addInfo,
            addSuccess: addSuccess,
            addAlert: addAlert,
            removeAlert: removeAlert,
            errorHandler: errorHandler,
            currentAlerts: currentAlerts,
            alertTypes: alertTypes,
            removeAllAlerts: removeAllAlerts
        };
           

}]);