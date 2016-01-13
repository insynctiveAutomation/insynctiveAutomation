var app = angular.module('utilApp');


app.service('bootboxService', function($compile) {
	
	var self = this;
	
	this.removeDialog = function(title, message, yesCallback){
		return bootbox.dialog({
			  message: message,
			  title: title, 
			  backdrop: true,
			  onEscape: function() { },
			  buttons: {
			    success: {
			      label: "Yes",
			      className: "btn-success",
			      callback: yesCallback
			    },
			    danger: {
			      label: "No",
			      className: "btn-danger",
			      callback: function() {
			    	  
			      }
			    }
			  }
			});
	}

	//Now only can be use with test suite.
	this.listSelection = function($scope, data, title, yesCallback){
		$scope.data = data;
		var selectList = '<select ng-model="selectTS" data-ng-options="ts.testSuiteName for ts in data"><option value=""></option></select>';
		var templateSelectList = angular.element(selectList);
		var linkSelect = $compile(templateSelectList);
		var htmlSelect = linkSelect($scope);
		
		return bootbox.dialog({
			  title: title, 
			  message: htmlSelect,
			  backdrop: true,
			  onEscape: function() { },
			  buttons: {
			    success: {
			      label: "Yes",
			      className: "btn-success",
			      callback: function(){
			    	  if($scope.selectTS){
			    		  yesCallback($scope.selectTS)
			    		  return true;
			    	  } else {
			    		  return false;
			    	  }
			      }
			    },
			    danger: {
			      label: "No",
			      className: "btn-danger",
			      callback: function() { }
			    }
			  }
		})
	}

	//Now only can be use with test suite.
	this.runTestPlanBootbox = function($scope, title, yesCallback){
		var html = 
		'<select ng-model="isNotification" ng-options="o.v as o.n for o in [{ n: \'Notify in Slack\', v: true }, { n: \'Do not notify\', v: false }]" required><option value="">Notification</option></select>'+
		'<span>   </span>'+
		'<select ng-model="isRemote" ng-options="o.v as o.n for o in [{ n: \'Crossbrowser\', v: true }, { n: \'Local\', v: false }]" required><option value="">Run in</option></select>';
		
		var templateHtml = angular.element(html);
		var htmlCompile = $compile(templateHtml);
		var htmlLinked = htmlCompile($scope);
		
		return bootbox.dialog({
			  title: title, 
			  message: htmlLinked,
			  backdrop: true,
			  onEscape: function() { },
			  buttons: {
			    success: {
			      label: "Yes",
			      className: "btn-success",
			      callback: function(){
			    	  if($scope.isNotification != undefined && $scope.isRemote != undefined){
			    		  yesCallback($scope.isNotification, $scope.isRemote)
			    		  return true;
			    	  } else {
			    		  return false;
			    	  }
			      }
			    },
			    danger: {
			      label: "No",
			      className: "btn-danger",
			      callback: function() { }
			    }
			  }
		})
	}
	
})

