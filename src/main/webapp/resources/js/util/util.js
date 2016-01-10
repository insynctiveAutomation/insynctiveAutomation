function Util() {
	
}

Util.goTo = function(path, event, windows){
	if(event.ctrlKey || event.which === 2){
		windows.open(path,'_blank')
	} else {
		windows.location.href = path;
	}
}