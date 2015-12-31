jQuery(function($) {
  
     //controllo biglietto
        var url= window.location.href;
        {
            var n = url.indexOf("?c=");
            if(n!=-1){
              window.location.assign("https://www.astimusei.it/app#download"); 
            }
            
        };
    
    
    
	//#main-slider
	$(function(){
		$('#main-slider.carousel').carousel({
			interval: 8000
		});
	});

	$( '.centered' ).each(function( e ) {
		$(this).css('margin-top',  ($('#main-slider').height() - $(this).height())/2);
	});

	$(window).resize(function(){
		$( '.centered' ).each(function( e ) {
			$(this).css('margin-top',  ($('#main-slider').height() - $(this).height())/2);
		});
	});

	//places
	$(window).load(function(){
		$places_selectors = $('.places-filter >li>a');
		if($places_selectors!='undefined'){
			$places = $('.places-items');
//			$places.isotope({
//				itemSelector : 'li',
//				layoutMode : 'fitRows'
//			});
			$places_selectors.on('click', function(){
				$places_selectors.removeClass('active');
				$(this).addClass('active');
				var selector = $(this).attr('data-filter');
				$places.isotope({ filter: selector });
				return false;
			});
		};
        
        
       
        
        
        
	});

	//contact form
	var form = $('.contact-form');
	form.submit(function () {
		$this = $(this);
		$.post($(this).attr('action'), function(data) {
			$this.prev().text(data.message).fadeIn().delay(3000).fadeOut();
		},'json');
		return false;
	});

	//goto top
	$('.gototop').click(function(event) {
		event.preventDefault();
		$('html, body').animate({
			scrollTop: $("body").offset().top
		}, 500);
	});	

	//Pretty Photo
	$("a[rel^='prettyPhoto']").prettyPhoto({
		social_tools: false
	});	
});
