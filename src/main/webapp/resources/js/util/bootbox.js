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

	this.listSelection = function($scope, data, title, yesCallback){
		$scope.data = data;
		var selectList = '<select ng-model="selectTS" data-ng-options="ts.testSuiteName for ts in {{data}}"><option value=""></option></select>';
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
	
})

