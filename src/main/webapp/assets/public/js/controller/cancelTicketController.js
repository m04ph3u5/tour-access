angular.module('asti.application').controller('cancelTicketCtrl', [ 'apiService', '$state', 
              function cancelTicketCtrl(apiService, $state){
	
	var self = this;
	var buffer = new Array();
	self.validation = true;
	self.load = false;
	self.error = false;
	self.ticket="";
	
	self.removeTicket = function(){
		apiService.removeTicket(self.ticket).then(
				function(response){
					self.load = false;	
					self.validation = false;
				},
				function(reason){
					self.error=true;
					self.load = false;
					self.validation = false;
					console.log("Impossibile rimuovere il biglietto selezionato");
					console.log(reason);
					self.errorMessage = reason.data.message;
				}
		);
	}
	
	self.keyPress = function(event){
		event.preventDefault();
		console.log("KEYPRESS");
		if(event.keyCode!=13){
			buffer.push(event.keyCode);
		}else{
			elaborateBuffer();
		}
	}
	
	self.invalidate = function(){
		if(validateTicket())
			self.removeTicket();
		else{
			self.ticket="";
			alert("Si e' verificato un errore. Ti preghiamo di riprovare");
		}
	}
	
	var validateTicket = function(){
		var regex = /^[0-9]{10}$/;
		return regex.test(self.ticket);
	}	
	
	var elaborateBuffer = function(){
		console.log("ELABORATE");
		var codeRead = new Array();
		for(var i=0; i<buffer.length; i++){
			if(buffer[i]!=18){
				codeRead.push(buffer[i])
			}else{
				if(codeRead.length>0){
					var n=0;
					n=(10*(codeRead[2]-96))+(codeRead[3]-96);
					self.ticket+=n-48;
					codeRead.splice(0,codeRead.length);
				}
			}
		}
		n=(10*(codeRead[2]-96))+(codeRead[3]-96);
		self.ticket+=n-48;
		
		
		if(validateTicket())
			self.removeTicket();
		else{
			self.ticket="";
			buffer.splice(0,buffer.length);
			alert("Si e' verificato un errore. Ti preghiamo di riprovare");
		}

	}
	
}]);