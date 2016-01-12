function Bootbox(){}

Bootbox.removeDialog = function(title, message, yesCallback){
	return bootbox.dialog({
		  message: message,
		  title: title,
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


