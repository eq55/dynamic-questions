// jquery document ready handler is not the right event to use as it triggers when the DOM is ready, but not necessarily when all the assets (images) have loaded
// we need to use window.load
$(window).load(function() {
      var htmlElement = $('html');
      
      // copy the rich presentation input field into the main presentation field
      $("input[name='PRESENTATION_TYPE']").val( $("input[name='RICH_PRESENTATION_TYPE']").val() );

      // setup and load overlay
      $('#waitOverlay').overlay({ 
            expose: {         
                  opacity: 0.5
            }, 
            closeOnClick: false,
            closeOnEsc: false
      });
      
      //Centre align the overlay.
      $('#waitOverlay').centerThis();
      
      /* we need to do a minor hack here to ensure the animated gif (rotating hearts) continues to animate
       * for IE we need to submit the form BEFORE we load the overlay
       * for other browsers we need to submit the form shortly AFTER loading the overlay
       * Not ideal, but we'll use the css class applied to the html element to test
       * Perhaps in the future a more elegant solution might be to use something like Modernizr
	   *
	   * Note particular case for ie7, which requires a further step.
	   * The by copying the html of the overlay and reapplying it, the browser is forced to re-render the page
       */
      if (htmlElement.hasClass("ie6") || htmlElement.hasClass("ie8") || htmlElement.hasClass("ie9")) {
			$("form")[0].submit();
			$('#waitOverlay').overlay().load();
	  } else if (htmlElement.hasClass("ie7")) {
			$("form")[0].submit();
			var waitOverlayHtml = $("#waitOverlay").html();
			$("#waitOverlay").html(waitOverlayHtml + "");
			$('#waitOverlay').overlay().load();
      } else {
            $('#waitOverlay').overlay().load();
            setTimeout(function() {
                  $("form")[0].submit();
            }, 250);
      }
            
});


(function ($) {
	$.fn.centerThis = function () {
		this.css("top", Math.max(0, (($(window).height() - this.outerHeight()) / 2) + $(window).scrollTop()) + "px");
		this.css("left", Math.max(0, (($(window).width() - this.outerWidth()) / 2) + $(window).scrollLeft()) + "px");
		return this;
	};
})(jQuery);