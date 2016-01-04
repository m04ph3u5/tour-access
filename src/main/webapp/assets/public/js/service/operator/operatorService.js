angular.module('asti.application').factory('operatorService', [ 
               function operatorService(){

    var places = null;
    var numTickets = null;
    var info = null;
    var ticketTipology = null;
	
    var getPlaces = function(){
    	return places;
    }
    
    var setPlaces = function(p){
    	places = p;
    }
    
    var getNumTickets = function(){
    	return numTickets;
    }
    
    var setNumTickets = function(n){
    	if((n==1 && numTickets>1)||(n>1 && numTickets==1))
    		info = null;
    	numTickets = n;
    }
    
    var getInfo = function(){
    	return info;
    }
    
    var setInfo = function(i){
    	info = i;
    }
    
    var getTicketTipology = function(){
    	return ticketTipology;
    }
    
    var setTicketTipology = function(t){
    	ticketTipology = t;
    }
    
    var reset = function(){
    	places = null;
    	numTickets = null;
    	info = null;
    	ticketTipology = null;
    }
    
	return {
		getPlaces : getPlaces,
		setPlaces : setPlaces,
		getNumTickets : getNumTickets,
		setNumTickets : setNumTickets,
		getInfo : getInfo,
		setInfo : setInfo,
		reset : reset,
		setTip : setTicketTipology,
		getTicketTipology : getTicketTipology
		
	}

}]);
