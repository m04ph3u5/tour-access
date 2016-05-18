angular.module('regionModule', []).factory('constants', function(){
	
	
	var regions = [
	    {id: 0, name: "N.D."},
	    {id: 1, name: "Abruzzo"},
	    {id: 2, name: "Basilicata"},
	    {id: 3, name: "Calabria"},
	    {id: 4, name: "Campania"},
	    {id: 5, name: "Emilia Romagna"},
	    {id: 6, name: "Friuli Venezia Giulia"},
	    {id: 7, name: "Lazio"},
	    {id: 8, name: "Liguria"},
	    {id: 9, name: "Lombardia"},
	    {id: 10, name: "Marche"},
	    {id: 11, name: "Molise"},
	    {id: 12, name: "Piemonte"},
	    {id: 13, name: "Puglia"},
	    {id: 14, name: "Sardegna"},
	    {id: 15, name: "Sicilia"},
	    {id: 16, name: "Toscana"},
	    {id: 17, name: "Trentino Alto Adige"},
	    {id: 18, name: "Umbria"},
	    {id: 19, name: "Valle d'Aosta"},
	    {id: 20, name: "Veneto"},
	    {id: 21, name: "Europa"},
	    {id: 22, name: "Resto del mondo"}
	];
	
	
	var getRegionsList = function(){
		return regions;
	}
	
	var getRegionName = function(i){
		if(i>=0 && i<regions.length)
			return regions[i].name;
		else
			return undefined;
	}
	
	return {
		getRegionsList : getRegionsList,
		getRegionName : getRegionName
	}
});