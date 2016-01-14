angular.module('asti.supervisor').controller('changePasswordSupervisorCtrl', ['$state', 'apiService',
              function changePasswordSupervisorCtrl($state, apiService){
	
	var self = this;
	self.showError = false;
	self.error = "";
	
	self.change = function(){

		self.showError = false;
		self.error = "";
		self.errorOldPwd = false;
		self.errorPwd = false;
		self.errorRePwd = false;

		if(!self.oldPwd){
			self.showError = true;
			self.errorOldPwd = true;
			self.errorPwd = false;
			self.errorRePwd = true;
			self.error = "Digita la vecchia password";
			self.pwd="";
			self.rePwd="";
		}
		else if(!self.pwd){
			self.showError = true;
			self.error = "Digita la nuova password";
			self.errorOldPwd = false;
			self.errorPwd = true;
			self.errorRePwd = true;
		}else if(self.pwd.length<8){
			self.showError = true;
			self.error = "La password inserita è troppo corta. Per favore utilizza almeno 8 caratteri";
			self.errorOldPwd = false;
			self.errorPwd = true;
			self.errorRePwd = false;
			self.pwd="";
			self.rePwd="";
		}
		else if(!self.rePwd){
			self.showError = true;
			self.error = "Per favore digita nuovamente la nuova password";
			self.errorOldPwd = false;
			self.errorPwd = false;
			self.errorRePwd = true;
		}
		else if(self.pwd!=self.rePwd){
			self.showError = true;
			self.error = "Le password non coincidono. Per favore digitale nuovamente";
			self.errorOldPwd = false;
			self.errorPwd = true;
			self.errorRePwd = true;
			self.pwd="";
			self.rePwd="";
		}
	}
	
}]);