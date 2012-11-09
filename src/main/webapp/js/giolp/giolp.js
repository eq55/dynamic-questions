//jquery plugin to hide a section
(function ($) {
	$.fn.hideSection = function (p_propertiesObj) {
		var	settings = $.extend( {
				// default hide transition is fadeOut with a duration of 0
				fadeDuration: 0,
				transition: function() {this.fadeOut(settings.fadeDuration);}
			},
			p_propertiesObj
		);

		return this.each(function () {
			var	$this = $(this);
			// apply the hide if this element is currently visible (no point hiding something thats already hidden)
			if ($this.is(":visible")) {
				// hide the element by using the transition function in the settings object
				settings.transition.apply($this);
				// and disable form fields within this section so they are not submitted as part of the form
				$this.find("input, select").attr("disabled", "disabled");
			}
		});
	};
})(jQuery);

//jquery plugin to show a section
(function ($) {
	$.fn.showSection = function (p_propertiesObj) {
		var	settings = $.extend( {
				// default show transition is fadeIn with a duration of 0
				fadeDuration: 0,
				transition: function() {this.fadeIn(settings.fadeDuration);}
			},
			p_propertiesObj
		);

		return this.each(function () {
			var	$this = $(this);
			// apply the show if this element is not currently visible (no point showing something thats already visible)
			if (!$this.is(":visible")) {
				// re-enable form fields within this section so long as they are not meant to be disabled (ie. if data-savedDisabledAttr is not true)
				$this.find("input, select").each( function(idx) {
					if ($(this).attr("data-savedDisabledAttr") != "true") {
						$(this).removeAttr("disabled");			
					}
				});
				// show the element by using the transition function in the settings object
				settings.transition.apply($this);
			}
		});
	};
})(jQuery);

// jQuery plugin to convert a field into a date field with components for each part (day, month, year)
// You can control how each component is presented by passing in an optional properties object literal
(function ($) {
	$.fn.dateFields = function (p_propertiesObj) {
		var	settings = $.extend( {
				dayComponent: {
					elem: $("<select />").addClass("answer dayDropdown"),
					list: giolp.lists.getOptionElements("daysList", {
						listAtStart: [$("<option value=\"\">Day</option>")]
					})
				},
				monthComponent: {
					elem: $("<select />").addClass("answer monthDropdown"),
					list: giolp.lists.getOptionElements("monthFullNamesList", {
						listAtStart: [$("<option value=\"\">Month</option>")]
					})
				},
				yearComponent: {
					elem: $("<input />").addClass("answer yearField").attr("placeholder", "yyyy").attr("maxlength", "4")
				}
			},
			p_propertiesObj
		);
		
		return this.each(function () {
			var	$this = $(this),
				d,
				m,
				y;

			// get the current values from the field (based on dd/mm/yyyy format)
			d = $this.val().substr(0, 2);
			m = $this.val().substr(3, 2);
			y = $this.val().substr(6, 4);
			// create our new fields before the original field, based on the appropriate properties in the settings object
			$this.before(
					settings.dayComponent.elem.attr("id", "day_" + $this.attr("id"))
			)
			.before(
					settings.monthComponent.elem.attr("id", "month_" + $this.attr("id"))
			).before(
					settings.yearComponent.elem.attr("id", "year_" + $this.attr("id"))
			);
			// for each field append a list of options if the list property is not null
			if (settings.dayComponent.list != null) {
				$("#day_" + $this.attr("id")).append(settings.dayComponent.list);
			}
			if (settings.monthComponent.list != null) {
				$("#month_" + $this.attr("id")).append(settings.monthComponent.list);
			}
			if (settings.yearComponent.list != null) {
				$("#year_" + $this.attr("id")).append(settings.yearComponent.list);
			}
			// for each of the new fields, set the title attribute from the original, the value and blur callback
			$("#day_" + $this.attr("id") + ", #month_" + $this.attr("id") + ", #year_" + $this.attr("id")).attr("title", $this.attr("title"));
			$("#day_" + $this.attr("id")).val(d);
			$("#month_" + $this.attr("id")).val(m);
			$("#year_" + $this.attr("id")).val(y);
			$("#day_" + $this.attr("id") + ", #month_" + $this.attr("id") + ", #year_" + $this.attr("id")).blur(function() {
				// reconstruct the real date field
				$this.val($("#day_" + $this.attr("id")).val() + "/" + $("#month_" + $this.attr("id")).val() + "/" + $("#year_" + $this.attr("id")).val());
				
				// TODO Add some date validation code
				var field_id = $this.attr("id");
				var rowElem = $("#row_" + field_id);
				rowElem.find(".tickcross").addClass("questionTick");
			});
			
			// hide this field - we just need to hide it rather than remove or disable it, as it still needs to be submitted with the rest of the form
			$this.hide();
		});
	};
})(jQuery);	

//setup a question with ajax help
(function ($) {
	$.fn.setAjaxHelp = function () {
		return this.each(function () {
			var $this, url, row;
			$this = $(this);
			
			if (!($this.parent().parent().hasClass("noAjaxHelp"))) {
				// first off, get the url from the a tag href attribute
				url = $this.attr("href");

				// now set the onfocus event for the row - an ajax request using the url plus an extra param to indicate its an ajax request
				row = $this.parent().parent();
				if (row !== '') {
					$(row).find("input, select").focus(function () {
						$.ajax({
							type: "POST",
							url: url + "&ajax",
							context: $this,
							success: function (data) {
								var $this, $help, $row;
								$this = $(this);

								// add the help article to the dom if its not there already
								$help = $("#ajaxHelp");
								if ($help.length === 0) {
									$("form").append('<div id="ajaxHelp"><div id="helpContent"><div class="popupHelp"><div id="helpArrow"><div></div></div><div id="theHelp">$$HELPTEXT$</div></div><div id="helpShadow"></div></div></div>');
									$help = $("#ajaxHelp");
								}
								// put the content in the div
								$help.find("#theHelp").empty().html(data);
								// set the top of the div - basically we want its center line at the center line of the row
								$help.css("top", (($this.parent().position().top) - ($help.height() / 2)));
								// set the top of the arrow - we need this to be centered against the height of the help div
								$("#helpArrow").css("top", ($help.height() / 2)-13);
								// and finally show it
								$help.show();
							}
						});
					}).blur(function () {
						// hide the right hand side popup help
						$("#ajaxHelp").hide();
					});
				}
			}
		});
	};
})(jQuery);

//setup a to convert a standard link to an overlay using progressive enhancement.
(function ($) {
	$.fn.setLinkOverlay = function (params) {
		return this.each(function () {
			var $this, $helpTextHTML, $htmlToOverlayDivId, $htmlCloseButton, $url,$settings, $addCustomWidth, $screenWidth;
			
			$settings =  $.extend({},{
				isCloseButtonIncluded: true, /* Sets the close button visibility on-off*/
				width:"" /* Sets a custom width for the overly.*/
			},params);
			//$screenWidth = ($(window).width() - this.width())/2+$(window).scrollLeft() + "px";
			$this = $(this);
			$this.click(function(e){
				//Prevent href from submitting
				e.preventDefault();
				
				//First off, get the url from the a tag href attribute
				$url = $this.attr("href");
				if($settings.isCloseButtonIncluded){
					$htmlCloseButton = "<a href='#' title='Close' class='overlayCloseBtn'><img src='./images/lv/closeBtn.png' alt='Close' title='Close' /></a>";
				}else{
					$htmlCloseButton = "";
				}
				
				//Build the ajax request get the HTML from help.htm.
				$.ajax({
					type: "POST",
					url: $url + "&ajax",
					context: $this,
					success: function (data) {
						$helpTextHTML = data;
						$htmlToOverlayDivId = $("#linkToOverlayContent");
						if ($htmlToOverlayDivId.length != 0) {$("#linkToOverlayContent").remove();}
						$("body").append('<div id="linkToOverlayContent"  class="genericOverlay"><div class="overlayContent"></div></div>');
						if($settings.width != 0) {$("#linkToOverlayContent").css("width",$settings.width);}
						$htmlToOverlayDivId = $("#linkToOverlayContent");
						//Call the overlay with width
						giolp.overlays.makeStandardOverlay($htmlToOverlayDivId);
						//Call centre align manually. 
						$(".genericOverlay").centerThis();
						//Empty any current content, then add our new content
						$htmlToOverlayDivId.find(".overlayContent").empty().html($helpTextHTML + $htmlCloseButton);
						//Now display the overlay
						$htmlToOverlayDivId.overlay().load();
						//Close the overlay when "close button(not the cross)" is clicked.
						$(".overlayCloseBtn").click(function(e){e.preventDefault();$htmlToOverlayDivId.overlay().close();$("#linkToOverlayContent").remove();});
					}
				});
			});
		});
	};
})(jQuery);


/*
 *Exit overlay with NCD questions 
 */
(function ($) {
	$.fn.setNCDOverlay = function (params) {
		return this.each(function () {
			var $this, $overlayHTML ,$overlayDivId, $htmlCloseButton,$settings, $screenWidth,$htmlNextButton;
			
			$settings =  $.extend({},{
				isCloseButtonIncluded: true, /* Sets the close button visibility on-off*/
				width:"" /* Sets a custom width for the overly.*/
			},params);
			
			$this = $(this);
			if ($(".showNCDOverlay").length != 0) {
				//First off, get the url from the a tag href attribute					
				if ($settings.isCloseButtonIncluded) {
					$htmlCloseButton = "<a href='#' title='Close' class='overlayCloseBtn'><img src='./images/lv/closeBtn.png' alt='Close' title='Close' /></a>";
				}
				else {
					$htmlCloseButton = "";
				}
				$overlayDivId = $("#ncdOfferOverlay");
				if ($settings.width != 0) {
					$(".genericOverlay").css("width", $settings.width);
				}
				giolp.overlays.makeStandardOverlay($overlayDivId);
				//Call centre align manually. 
				$(".genericOverlay").centerThis();
				$("#closeContainer").empty().html($htmlCloseButton);
				$htmlNextButton = $("body").find("input[name='_eventId_Next']").clone();
				$("#submitContainer").empty().html($htmlNextButton);
				$("#submitContainer input").css({"margin-top":"-53px","float":"right"});
				$(".genericOverlay .partnersNCD").css("display", "block");
				
				//Now display the overlay
				$overlayDivId.overlay().load();
				$(".overlayCloseBtn").click(function(e){
					e.preventDefault();
					$overlayDivId.overlay().close()
				});
			}
		});
	};
})(jQuery);


/*
 *Please wait overlay for calculating quote 
 */
(function ($) {
	$.fn.setPleaseWaitCalcOverlay = function (params) {
		return this.each(function () {
			var $this, $overlayHTML ,$overlayDivId, $htmlCloseButton,$settings, $screenWidth,$htmlNextButton;
			
			$this = $(this);
			
			$("body").find("input[name='_eventId_Next']").click(function(e){
				if ($("#hasModifications_2").is(":checked")) {
					// The user didn't say there were modifications, so can show please wait overlay
					$overlayDivId = $("#pleaseWaitCalculatingOverlay");
					giolp.overlays.makeStandardOverlay($overlayDivId);
					
					setTimeout(function(){
						//Call centre align manually. 
						$("#pleaseWaitCalculatingOverlay").centerThis();
						//Now display the overlay
						$overlayDivId.overlay().load();
					},0);
				}
			});
		});
	};
})(jQuery);

/*
 * GIOLP Javascript Library
 *
 */

//This is our only global variable. Everything else is defined within it as properties
var giolp = (giolp != null ? giolp : {});


/* Augment the giolp object with a helpers library
 * It uses a simple namespace pattern because all we are doing is defining functions to be called by other things
 */
giolp.helpers = {
	isArray: function (anElement) {
		return (typeof anElement === "object" && anElement.constructor === Array);
	},

	flatten: function (oArray) {
		var retVal = [], i, j, tempFlatt;
		for (i = 0; i < oArray.length; i = i + 1) {
			if (!giolp.helpers.isArray(oArray[i])) {
				retVal.push(oArray[i]);
			} else {
				tempFlatt = giolp.helpers.flatten(oArray[i]);
				for (j = 0; j < tempFlatt.length; j = j + 1) {
					retVal.push(tempFlatt[j]);
				}
			}
		}
		return retVal;
	}
};

//Fix for the overlay left align issue caused from the jQuery 1.8 update. So Manually centre align the overlay  using the plugin below until jTools release 1.8 compatible version of their code.  
(function ($) {
	$.fn.centerThis = function () {
		this.css("top", Math.max(0, (($(window).height() - this.outerHeight()) / 2) + $(window).scrollTop()) + "px");
		this.css("left", Math.max(0, (($(window).width() - this.outerWidth()) / 2) + $(window).scrollLeft()) + "px");
		return this;
	};
})(jQuery);

/* Augment the giolp object with rich occupations lookup/typeahead functionality
 * It uses the module pattern to leverage the 'init' style code.
 * We dont need to define some private members, so this could sit within the init code of the main giolp object
 * but having it as a seperate module does provide a degree of separation.
 */
giolp.richOccupations = (function () {
	/* declare variables (such as functions) up front here
	*/
	var cachedOccupations = {};

	// init code here
	$(document).ready(function () {

		var originalSelect=$("#canonicalQuote_quoteRisk_drivers_driver0_occupations_mainOccupationCode");

		// add a new input field before the original field, with name and id attributes copied from the original - once the original field is removed from the dom, this new field is the one that will be submitted
		// its going to be a hidden field, and the jquery autocomplete will populate it with the key of the occupation list (rather than the text value, which is no good to be stored in the form backing object)
		originalSelect.before(
			$("<input/>").attr("type", "hidden")						// Create a hidden input field
			.attr("name", originalSelect.attr("name"))					// Set the name attribute from the original field so it gets bound properly to the form backing object
			.attr("id", originalSelect.attr("id"))						// Set the id attribute from the original field so that any js etc that targets the id will still work
			.val( originalSelect.attr("data-valueInModel") )			// set the value of the new field to be the value in the data-valueInModel attribute (the occupation key)
		);
		originalSelect.before(
			$("<input/>").attr("type", "text").addClass("answer")		// Create a text input field with a class of "answer". It wont get a name attribute as we dont want to submit it (the field above will be submitted)
			.attr("id", "autocomplete_" + originalSelect.attr("id"))	// Set the id attribute so that we can target it with css & js etc
			.val(														// Set the value of the new field using an immediate anonymous function (see, you knew they'd come in handy :) )
				(function() {											// The function uses the value in the data-valueInModel attribute (the key) and gets is value via ajax from the server
					var occupationText,
						valueInModel = originalSelect.attr("data-valueInModel");

					$.ajax({
						async: false,
						url: "AjaxGetItemFromReferenceDataList.htm",
						data: "list=richOccupationsList&value=" + valueInModel,
						dataType: "json",
						type: "POST",
						success: function (data, textStatus, jqXHR) {
							occupationText = data.listItem.value;
						}
					});
					return occupationText;
				}())
			)
		);
		// now remove the original select field from the dom
		originalSelect.remove();
		// now set the 'for' attribute of the label to point at the new field (it currently points at the originalSelect that we've just removed or the hidden text field we added earlier)
		$("#row_canonicalQuote_quoteRisk_drivers_driver0_occupations_mainOccupationCode label").attr("for", "autocomplete_canonicalQuote_quoteRisk_drivers_driver0_occupations_mainOccupationCode");
		// now set the new field up as an autocomplete field
		$("#autocomplete_canonicalQuote_quoteRisk_drivers_driver0_occupations_mainOccupationCode").autocomplete({
			minLength: 3,
			delay: 300,
			source: function( request, response ) {
				var term = request.term;
				// is the search term already in the cache? If so, return from the cache
				if ( term in cachedOccupations ) {
					response( cachedOccupations[ term ] );
					return;
				}
				// we need to call the AjaxSearchReferenceDataList to retrieve (and cache) matching occupations
				$.ajax({
					async: false,
					url: "AjaxSearchReferenceDataList.htm",
					data: "list=richOccupationsList&value=" + request.term,
					dataType: "json",
					type: "POST",
					success: function(data, textStatus, jqXHR) {
						var returnedData = $.map( data.listItems, function( item ) {
							return {
								label: item.value,
								value: item.id
							}
						});
						cachedOccupations[ term ] = returnedData;
						response( returnedData );
					}
				});

			},
			select: function(event, ui) {
				// an item has been selected in the list - set the hidden field with the key, and the autocomplete field with the value
				$("#autocomplete_canonicalQuote_quoteRisk_drivers_driver0_occupations_mainOccupationCode").val(ui.item.label);
				$("#canonicalQuote_quoteRisk_drivers_driver0_occupations_mainOccupationCode").val(ui.item.value);				
				return false;
			},
			focus: function(event, ui) {
				// triggered when the user uses the keyboard to go down through the list - set the hidden field with the key, and the autocomplete field with the value
				$("#autocomplete_canonicalQuote_quoteRisk_drivers_driver0_occupations_mainOccupationCode").val(ui.item.label);
				$("#canonicalQuote_quoteRisk_drivers_driver0_occupations_mainOccupationCode").val(ui.item.value);				
				return false;
			}
		});
	});

	// define private functions here


	// return public functions here
	return {

	};
}());


/* Augment the giolp object with some standard functions to provide standard consistent overlay functionality
 * It uses the module pattern as we need to define some private members
 * Arguably this is the wrong pattern - we could (should?) write this as a jquery plugin, but this way will do for now
 */
giolp.overlays = (function () {

	/* declare variables (such as functions) up front here*/

	var standardOverlayProperties;

	// init code here
	standardOverlayProperties = {
		mask: {
			color: '#000000',
			loadSpeed: 200,
			opacity: 0.5
		},
		closeOnClick: false,
		closeOnEsc: false
	};

	// define private functions here

	// return public functions here
	return {
		// public function to turn any passed-in query element into an overlay with standard GIOLP properties (display & functionality)
		// the first parameter should be a jquery object (or selector) of the element in the dom to make an overlay out of. It must be a jquery object, not a dom object
		// the optional 2nd param allows properties to be overridden, so should be an object literal
		makeStandardOverlay: function(p_jqElement, p_propertiesObj) {

			//start off by making sure the p_jqElement param is valid - not null and whose length is greater than 0 (jquery objects are essentially arrays)
			if (p_jqElement !== null && p_jqElement.length > 0) {
	
				//make sure p_propertiesObj is either itself or a new object literal - we cant have it being null even though its optional to the caller
				p_propertiesObj = (p_propertiesObj != null ? p_propertiesObj : {});
	
				// workout the settings for this overlay by merging the our standard overlay properties object with the optional propertries object
				$.extend(true, p_propertiesObj, standardOverlayProperties);
	
				// turn the passed in jquery object it into an overlay
				p_jqElement.overlay(
						p_propertiesObj
				);
	
				// lastly add bgiframe support to help IE6 overcome z-index issues
				if ($.fn.bgiframe) {
					p_jqElement.bgiframe();
				};
	
			};
		}
	};

}());

/* Augment the giolp object with rich ajax validation functionality
 * It uses the module pattern to leverage the 'init' style code.
 * We dont need to define some private members, so this could sit within the init code of the main giolp object
 * but having it as a seperate module does provide a degree of separation.
 */
giolp.richValidations = (function () {

	/* declare variables (such as functions) up front here
	 */
	var url;

	// init code here
	url="AjaxValidate.htm";
	$("form#quote input[type=text]").blur(function() {
		var that = $(this);
		var field=that.attr("name");
		var value=that.val();
		$.ajax({
			url: url,
			data: field + "=" + value + "&state=" + $("meta[name='application-name']").attr("data-viewState"),
			dataType: "json",
			type: "POST",
			success: function(data, textStatus, jqXHR) {
				// get the element for the error message and row
				var field_id = that.attr("id");
				var rowElem = $("#row_" + field_id);
				var label = rowElem.find("label");
	
				// If there is no data-questionText attribute for the label, we need to add one with the current label value
				// We should only do this if it does not exist, otherwise we could end up saving the current error message as the question text (in the case where a validation error has previously been triggered)
				if (label.attr("data-questionText") == null || label.attr("data-questionText") == "") {
					label.attr("data-questionText", label.text());
				}
	
				// Empty the label element and reset the style on the row - we might be clearing a previous error
				label.empty();
				rowElem.removeClass("questionInError").removeClass("questionValid");
	
				// if there is an error ...
				if (data.errors === true) {
					rowElem.addClass("questionInError");

					// we need to loop through the messages array in the json response and output each validatioMessage in the label
					for (var i=0; i<data.messages.length; i++) {
						label.append(data.messages[i].validationMessage);
						if (i < data.messages.length-1) {
							label.append("<br/>");
						}
					}
				} else {
					// Question is not in error
					// Put back the original label text
					label.text(label.attr("data-questionText"));
	
					// Set the questionValid style on the row but only if the field valie is not blank
					// ie. just because validation has not returned a validation error doesn't mean its valid - if the field has no value its neither valid nor invalid so should not get a style either way
					if (value != '') {
						rowElem.addClass("questionValid");
					}
				}
			}
		});
	});

	// TODO - Add proper validation for radio buttons 
	$("form#quote input[type=radio]").change(function() {
		var that = $(this);
		if (that.is(":checked") ) {
			var field_name = that.attr("name");
			var rowName = "#row_" + field_name.replace("[", "").replace("]", "").replace(/\./g, "_");			
			var rowElem = $(rowName);
			rowElem.addClass("questionValid");
		}
	});
	
	// TODO - Add proper validation for select lists 
	$("form#quote select").change(function() {
		var that = $(this);
		var field_id = that.attr("id");
		var rowElem = $("#row_" + field_id);
		if (that.val() != "" ) {
			rowElem.addClass("questionValid");
		} else {
			rowElem.removeClass("questionValid");
		}
	});

	// return public functions here
	return {

	};
}());

giolp.rowHighlighting = (function () {
	$(function(){
		$(".row").focusin(function(){
			$(this).addClass("greenSelected");
			$(this).prev("div").children("hr").css("visibility","hidden");
		});
		$(".row").focusout(function(){
			$(this).removeClass("greenSelected");
			$(this).prev("div").children("hr").css("visibility","visible");
		});
	});
}());

giolp.autoPopulateGender = (function () {
	/* declare variables (such as functions) up front here
	 */

	// init code here
	$("#canonicalQuote_quoteRisk_drivers_driver0_personalDetails_title").change(function() {

		var genderValue = "";
		var titleValue  = $("#canonicalQuote_quoteRisk_drivers_driver0_personalDetails_title").val();

		if (titleValue === "MR" ) {
			genderValue = "M";
		} else if ((titleValue === "MISS" ) || (titleValue === "MRS" ) || (titleValue === "MS" )) {
			genderValue = "F";
		}

		if (genderValue === "M") {
			$("#canonicalQuote_quoteRisk_drivers_driver0_personalDetails_gender").prop('checked',true);
			$("#canonicalQuote_quoteRisk_drivers_driver0_personalDetails_gender_2").prop('checked',false);
		} else if (genderValue === "F") {
			$("#canonicalQuote_quoteRisk_drivers_driver0_personalDetails_gender").prop('checked',false);
			$("#canonicalQuote_quoteRisk_drivers_driver0_personalDetails_gender_2").prop('checked',true);
		}
		$("#canonicalQuote_quoteRisk_drivers_driver0_personalDetails_gender").change();
		$("#canonicalQuote_quoteRisk_drivers_driver0_personalDetails_gender_2").change();
	});

	// define private functions here


	// return public functions here
	return {

	};
}());

giolp.removeRichHelpTabStops = (function () {

	// init code here
	$(document).ready(function () {
		$(".help > a").attr("tabindex", "-1");
	});

	// return public functions here
	return {
	};

}());

/* Augment the giolp object with functionality to get (and cache) giolp lists via ajax
 * It uses the module pattern to leverage closure scope (ie. the cache(.
 */
giolp.lists = (function () {
	/* declare variables (such as functions) up front here
	 */
	listCache = {};

	// init code here

	// define private functions here

	// return public functions here
	return {
		// function to get a giolp list. It will get it from the cache if its there, else get it via ajax and cache it
		getRawList: function(p_listName) {
			// have we been passed a list name to look up?
			if (p_listName != '') {
				// if its already in our cache, simply return it
				if (p_listName in listCache) {
					return listCache[p_listName];
				}
	
				// its not in the cache, so we need to go get it from the server via ajax
				$.ajax({
					url: "AjaxGetReferenceDataList.htm",
					data: p_listName,
					dataType: "json",
					type: "POST",
					async: false,
					success: function (data, textStatus, jqXHR) {
						// put the returned list in the cache
						listCache[p_listName] = data;
					}
				});
				// and return 
				return listCache[p_listName];
			}
		},

		// function to return a giolp list as an array of html option elements
		getOptionElements: function(p_listName, p_optionalOptions) {
			var	htmlOptionTags = [],
			listItem,
			i,
			rawListData;
	
			// have we been passed a list name to look up?
			if (p_listName != '') {
				// has a listAtStart been specified?
				if (p_optionalOptions.listAtStart != null) {
					for (i=0; i<p_optionalOptions.listAtStart.length; i++) {
						htmlOptionTags.push(p_optionalOptions.listAtStart[i]);
					}
				}
				// get and process the raw list
				rawListData = giolp.lists.getRawList(p_listName);
				for (i=0; i<rawListData.listItems.length; i++) {
					listItem = rawListData.listItems[i];
					htmlOptionTags.push($("<option value=\"" + listItem.id + "\">" + listItem.value + "</option>"));
				}
				// has a listAtEnd been specified?
				if (p_optionalOptions.listAtEnd != null) {
					for (i=0; i<p_optionalOptions.listAtEnd.length; i++) {
						htmlOptionTags.push(p_optionalOptions.listAtEnd[i]);
					}
				}
			}
			return htmlOptionTags;
		}

	};
}());


giolp.init = (function () {
	/*declare variables (such as functions) up front here*/
	
	/*'init' style code - ie. code we want to run when this library is loaded */

	// for every input (all types inc radio & checkbox) and select fields, save its 'disabled' value into a custom property
	// this is because when we hide a field we set disabled=disabled (so that its not submitted), and when we show it we reset the disabled value.
	// This is OK, except for fields where we want them to stay disabled, eg: in the case of example rows in tables etc.
	$("input, select").attr("data-savedDisabledAttr", function() {return this.disabled;});

	/* DaCo - Uses jQuery event to re-centre overlays on a window resize event.
	 */
	$(window).resize(function() {
		$(".overlay:visible, .waitOverlay:visible, .genericOverlay:visible").each(function() {
			$(this).css("left", ($(window).width() - $(this).outerWidth()) / 2);
		});
    });
	
	$("#ncdUsage").hide();
	
	$("#isPartnerNCDUsed").click(function(){
		$("#ncdUsage").show('slow');
	});
	
	$("#isPartnerNCDUsed_2").click(function(){
		$("#ncdUsage").hide('slow');
	});
	
	// set ajax lookup for the help text
	$("form#quote span.help a").setAjaxHelp();
	
	// set ajax overlay text
	$("a.linkToOverlay").setLinkOverlay();
	
	//Set NCD Overlay
	//$("#leftColumn input[name='_eventId_Next']").setNCDOverlay();
	$("body").setNCDOverlay();
	
	// Prepare calculating - please wait overlay
	if ($("#pleaseWaitCalculatingOverlay").length > 0) {
		$("body").setPleaseWaitCalcOverlay();
	}
	
	// set DOB field to use 3 fields
	$("#canonicalQuote_quoteRisk_drivers_driver0_personalDetails_dateOfBirth").dateFields();
	
	//Set claims fields to use 3 fields
	$("#canonicalQuote_quoteRisk_drivers_driver0_claims_claim0_date").dateFields();
	$("#canonicalQuote_quoteRisk_drivers_driver0_claims_claim1_date").dateFields();
	$("#canonicalQuote_quoteRisk_drivers_driver0_claims_claim2_date").dateFields();
	$("#canonicalQuote_quoteRisk_drivers_driver0_claims_claim3_date").dateFields();
	
	//Set convictions fields to use 3 fields
	$("#canonicalQuote_quoteRisk_drivers_driver0_convictions_conviction0_date").dateFields();
	$("#canonicalQuote_quoteRisk_drivers_driver0_convictions_conviction1_date").dateFields();
	$("#canonicalQuote_quoteRisk_drivers_driver0_convictions_conviction2_date").dateFields();
	$("#canonicalQuote_quoteRisk_drivers_driver0_convictions_conviction3_date").dateFields();
		
	if ($("#canonicalQuote_quoteRisk_proposal_coverStartDate").length > 0) {
		// set cover start date to be a use 3 date field
		$("#canonicalQuote_quoteRisk_proposal_coverStartDate").dateFields({
			yearComponent: {
				elem: $("<select />").addClass("answer yearDropdown"),
				list: (function() {
					// build an array of html option elements from the data in the attribute data-coverStartDateYears
					var htmlOptions = [],
						i,
						listData = $.parseJSON($("#canonicalQuote_quoteRisk_proposal_coverStartDate").attr("data-coverStartDateYears"));
					for (i=0; i<listData.length; i++) {
						htmlOptions.push($("<option value=\"" + listData[i].value + "\">" + listData[i].label + "</option>"));
					}
					return htmlOptions;
				}())
			}
		});
		// and with a nice rich data picker
		$("#canonicalQuote_quoteRisk_proposal_coverStartDate").datepicker({
			dateFormat: 'dd/mm/yy',
			minDate: (""+ $("#canonicalQuote_quoteRisk_proposal_coverStartDate").attr("data-earliestCoverStartDate") ), // needs to be parsed as a string, rather than the sum of dd/mm/yy (eg: 25/08/12 = 25 divide by 8 divide by 12)
			maxDate: (""+ $("#canonicalQuote_quoteRisk_proposal_coverStartDate").attr("data-latestCoverStartDate") ), // needs to be parsed as a string, rather than the sum of dd/mm/yy (eg: 25/08/12 = 25 divide by 8 divide by 12)
			showOn: 'button',
			buttonImage: './images/lv/calendar.gif',
			numberOfMonths: parseInt($("#canonicalQuote_quoteRisk_proposal_coverStartDate").attr("data-coverStartDateRichDatePickerMonthsToShow"), 10), // needs to be parsed as an integer
			showButtonPanel: false,
			buttonImageOnly: true,
			showAnim: 'fadeIn',
			onSelect: function(dateText, inst) {
				// update our 3 component fields with the new date value - the hidden field (which is the real field which is submitted with the form) is already updated
				var d = dateText.split("/");
				$("#day_canonicalQuote_quoteRisk_proposal_coverStartDate").val(d[0]);
				$("#month_canonicalQuote_quoteRisk_proposal_coverStartDate").val(d[1]);
				$("#year_canonicalQuote_quoteRisk_proposal_coverStartDate").val(d[2]);
			}
		});
	}
	
	
	$("#startDate").dateFields({
		yearComponent: {
			elem: $("<select />").addClass("answer yearDropdown"),
			list: (function() {
				// build an array of html option elements from the data in the attribute data-coverStartDateYears
				var htmlOptions = [],
					//i,
					//listData = $.parseJSON($("#startDate").attr("data-coverStartDateYears"));
				//for (i=0; i<listData.length; i++) {
				//	htmlOptions.push($("<option value=\"" + listData[i].value + "\">" + listData[i].label + "</option>"));
				//}
				htmlOptions = "<option value='2012'>2012</option>";
				return htmlOptions;
			}())
		}
	});
	
	$(".addCoveractionButtons").click(function(){
		var $this = $(this);
		var $btnText = $this.attr("src"); 
		if($btnText.indexOf("removeBtn.png") != -1){
			$this.attr("src","./images/lv/yourQuote/addBtn.png");
			$this.parent('div').removeClass("selected");			
			//$this.parent('div').stop(true,false).removeClass("selected", {duration:1000});
		}else{
			$this.attr("src","./images/lv/yourQuote/removeBtn.png");
			$this.parent('div').addClass("selected");
			//$this.parent('div').stop(true,false).addClass("selected", {duration:1000});
		}
		
	});

	$("#startDate").datepicker({
		dateFormat: 'dd/mm/yy',
		minDate: (""+ $("#startDate").attr("data-earliestCoverStartDate") ), // needs to be parsed as a string, rather than the sum of dd/mm/yy (eg: 25/08/12 = 25 divide by 8 divide by 12)
		maxDate: (""+ $("#startDate").attr("data-latestCoverStartDate") ), // needs to be parsed as a string, rather than the sum of dd/mm/yy (eg: 25/08/12 = 25 divide by 8 divide by 12)
		showOn: 'button',
		buttonImage: './images/lv/calendar.gif',
		numberOfMonths: parseInt($("#startDate").attr("data-coverStartDateRichDatePickerMonthsToShow"), 10), // needs to be parsed as an integer
		showButtonPanel: false,
		buttonImageOnly: true,
		showAnim: 'fadeIn',
		onSelect: function(dateText, inst) {
			// update our 3 component fields with the new date value - the hidden field (which is the real field which is submitted with the form) is already updated
			var d = dateText.split("/");
			$("#day_startDate").val(d[0]);
			$("#month_startDate").val(d[1]);
			$("#year_startDate").val(d[2]);
		}
	});

		
	// Progressively enhance the claimsTable and convitionsTable - hide them and disable their fields if the corresponding Y/N is not Y
	// Set the corresponding Y/N radios to show and hide the sections as appropriate
	if (!$("isConvictionInLastFiveYears").is(":checked")) {
		// The 'y' is not checked, hide the table
		$("#convictionsTableSection").hideSection();
	};
	// set the click events for "have you had any convictions" radios
	$("#isConvictionInLastFiveYears").click(function() {
		$("#convictionsTableSection").showSection({transition:function() {this.show("blind", { direction: "vertical" }, 750);}});
	});
	$("#isConvictionInLastFiveYears_2").click(function() {
		$("#convictionsTableSection").hideSection({transition:function() {this.hide("blind", { direction: "vertical" }, 750);}});
	});	
	if (!$("isClaimedInLastFiveYears").is(":checked")) {
		// The 'y' is not checked, hide the table
		$("#claimsTable").hideSection();
	};
	// set the click events for "have you had any previous claims" radios
	$("#isClaimedInLastFiveYears").click(function() {
		$("#claimsTable").showSection({transition:function() {this.show("blind", { direction: "vertical" }, 750);}});
	});
	$("#isClaimedInLastFiveYears_2").click(function() {
		$("#claimsTable").hideSection({transition:function() {this.hide("blind", { direction: "vertical" }, 750);}});
	});

}());