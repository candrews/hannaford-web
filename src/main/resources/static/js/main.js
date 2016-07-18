(function($){
	$(document).on('turbolinks:load', function() {
		if(! $(document.documentElement).hasClass("native-app")){
			$(".button-collapse").sideNav();
			$("form[method=GET]").submit(function(event){
				event.preventDefault();
				Turbolinks.visit($(this).attr("action") + "?" + $(this).serialize());
			});
		}
		Materialize.updateTextFields();
	});
})(jQuery); // end of jQuery name space
