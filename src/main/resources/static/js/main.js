(function($){
	Turbolinks.Location.prototype.isHTML = function() {
		  var extension = this.getExtension();
		  return extension === ".html" ||
		    extension === ".cmd" ||
		    extension === ".jsp";
		}
	var quaggaRunning = false;
	$(document).on('turbolinks:load', function() {
		if(! $(document.documentElement).hasClass("native-app")){
			$(".button-collapse").sideNav();
			$("form[method=GET]").submit(function(event){
				event.preventDefault();
				Turbolinks.visit($(this).attr("action") + "?" + $(this).serialize());
			});
		}
		Materialize.updateTextFields();
		if(quaggaRunning){
			Quagga.stop();
			quaggaRunning=false;
		}
		if($("#barcode-scanner-viewport").length){
			$(".camera-instructions").show();
			  $(".camera-error").hide();
			$.ajax({
				  url: "https://serratus.github.io/quaggaJS/examples/js/quagga.min.js",
				  dataType: "script",
				  cache: true,
				}).fail(function(){
					alert("failed to load QuaggaJS");
				}).done(function(){
					Quagga.init({
					    inputStream : {
					      name : "Live",
					      type : "LiveStream",
					      target: document.querySelector('#barcode-scanner-viewport')    // Or '#yourElement' (optional)
					    },
					    decoder : {
					      readers : ["ean_reader", "ean_8_reader", "upc_reader", "upc_e_reader"]
					    }
					  }, function(err) {
					      $(".camera-instructions").hide();
					      if (err) {
							  $(".camera-error").show();
					          console.log(err);
					          return
					      }
					      console.log("Initialization finished. Ready to start");
					      Quagga.start();
					      quaggaRunning = true;
					  });
					Quagga.onDetected(function(data){
						Turbolinks.visit("/catalog/search.cmd?keyword=" + data.codeResult.code);
					});
				});
		}
		var getNavigationItemForLi = function($li){
			if($li.find("> ul.collapsible").length==1){
				var submenu = {children: []};
				var $header = $($li.find("> ul.collapsible .collapsible-header")[0]);
				submenu.text = $header.text();
				var img = $header.find("img");
				if(img.length==1) submenu.imgSrc = img.attr("src");
				$li.find("> ul.collapsible > li > .collapsible-body > ul > li").each(function(){
					submenu.children.push(getNavigationItemForLi($(this)));
				});
				return submenu;
			}else{
				var item = {};
				item.text = $li.text();
				var img = $li.find("img");
				if(img.length==1) item.imgSrc = img.attr("src");
				var a = $li.find("a");
				if(a.length == 1) item.href = a.attr("href");
				return item;
			}
		}
		// used by the native apps to get the left navigation so they can render it native
		window.getNavigationObject = function(){
			var ret = [];
			$("ul#nav-mobile > li:not(.native-app-exclude)").each(function(){
				var $li=$(this);
				ret.push(getNavigationItemForLi($li));
			});
			return ret;
		};
		
		$("a.offlineReload").attr("href", location.hash?location.hash.substr(1):location.href);
		
		addAppCacheIframe();
	});

	function addAppCacheIframe(){
		var iframe = document.createElement('IFRAME');
		iframe.setAttribute('style', 'width:0px; height:0px; visibility:hidden; position:absolute; border:none');
		iframe.src = '/manifest.html';
		iframe.id = 'appcacheloader';
		document.head.appendChild(iframe);
	}
})(jQuery); // end of jQuery name space

