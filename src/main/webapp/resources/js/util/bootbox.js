function Bootbox(){}

Bootbox.removeDialog = function(title, message, yesCallback){
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


