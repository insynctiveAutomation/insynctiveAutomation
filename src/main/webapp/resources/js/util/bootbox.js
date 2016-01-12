function Bootbox(){}

Bootbox.removeDialog = function(message, yesCallback){
	return bootbox.dialog({
		  message: message,
		  title: "Remove Element",
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


