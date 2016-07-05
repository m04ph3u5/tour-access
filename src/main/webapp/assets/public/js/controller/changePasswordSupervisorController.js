angular.module('asti.supervisor').controller('changePasswordSupervisorCtrl', ['$state', 'apiService', 'alertingGeneric',
              function changePasswordSupervisorCtrl($state, apiService, alertingGeneric){
	
	var self = this;
	
	self.change = function(){


		if(!self.oldPwd){
			alertingGeneric.addWarning("Inserisci la vecchia password");
			self.pwd="";
			self.rePwd="";
		}
		else if(!self.pwd){
			alertingGeneric.addWarning("Inserisci la nuova password");
		}else if(self.pwd.length<8){
			alertingGeneric.addWarning("La password inserita è troppo corta. Utilizza almeno 8 caratteri.");
			self.pwd="";
			self.rePwd="";
		}
		else if(!self.rePwd){
			alertingGeneric.addWarning("Per favore digita nuovamente la nuova password.");
		}
		else if(self.pwd!=self.rePwd){
			alertingGeneric.addWarning("Le password inserite non coincidono!");
			self.pwd="";
			self.rePwd="";
		}
		else{
			
			var passwordDTO = {};
			passwordDTO.oldPassword = self.oldPwd;
			passwordDTO.newPassword = self.pwd;
			apiService.changePassword(passwordDTO).then(
					function(data){
						alertingGeneric.addSuccess("Password cambiata correttamente!");
					},
					function(reason){
						alertingGeneric.addDanger("Impossibile cambiare la password, si è verificato un errore inaspettato. La preghiamo di riprovare");
					}
			);
		}
		
		
	}
	
}]);