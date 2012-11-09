		$(document).ready(function(){
			$("#canonicalQuote_quoteRisk_vehicle_security_postcodeOvernight").bind('change', binder);
			
			$("#submit").click(function(){
				$.post("test", {execution: $('#key').val(), ajaxSource: "true", _eventId_submit: "submit"}, function(data){
					$("body").prepend(data);
//					var standardOverlayProperties = {
//							mask: {
//								color: '#000000',
//								loadSpeed: 200,
//								opacity: 0.5
//							},
//							closeOnClick: false,
//							closeOnEsc: false
//						};
//					$("#pleaseWaitCalculatingOverlay").overlay(standardOverlayProperties);
				});
				return false;
			});
			
		});	
		
		function binder() {
			$.post("test", {execution: $('#key').val(), postcode:$(this).val(), ajaxSource: "true", _eventId_submit: "postcode"}, function(data){
				$("#row_canonicalQuote_quoteRisk_vehicle_security_postcodeOvernight").replaceWith(data);
				$("#canonicalQuote_quoteRisk_vehicle_security_postcodeOvernight").bind('change', binder);
			});
			return false;
		}
		