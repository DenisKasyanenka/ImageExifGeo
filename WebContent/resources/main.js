$(document).ready(function() {
	setUploadFormHandlers();
	loadThumbs();
	setPreviewHandlers();
	setEditHandlers();
});

function setUploadFormHandlers() {
	var $form = $('#form');
	$form.on('submit', function(e) {
		e.preventDefault();
		var formData = new FormData(this);
		$.ajax({
			url: 'images',
			type: 'POST',
			data: formData,
			cache: false,
			contentType: false,
			processData: false,
			success: function(response) {
				loadThumbs();
			}
		});
	});
	$('#file').change(function() {
		$('#name').val($('#file').val());
	});
}



function loadThumbs() {
	$('.preview-item').remove();
	$.ajax({
		url: "images",
		dataType: "text",
		contentType: "application/json; charset=utf-8",
		success: function(data) {
			var $imagesData = JSON.parse(data);
			$.each($imagesData, function(i, item) {
				var newThumb = $('<img />');
				newThumb.attr('src', "thumbs/" + $imagesData[i].id);
				newThumb.attr('alt', "images/" + $imagesData[i].id);
				newThumb.attr('data-id', $imagesData[i].id);
				newThumb.addClass('preview-item');
				$('.preview').append(newThumb);
			});
		}
	});
};




function setPreviewHandlers() {
	$('.preview').on('click', '.preview-item', function(e) {
		$('.big_image').remove();
		var selected = $(this);
		$.ajax({
			url: selected.attr('alt'),
			type: 'GET',
			datatype: 'image',
			success: function() {
				var newImage = $('<img />');
				newImage.attr('src', selected.attr('alt'));
				newImage.addClass('big_image')
				$('#display_image').append(newImage);
				$('.exif_values').remove();
				$('#desc_text').empty();
				getImageInfo(selected.attr('data-id'));
			},
		});
	});
}



	function setEditHandlers() {
		$('#edit_desc').on('click', function(e) {
			e.preventDefault();
			$('#edit_desc_span').hide();
			$('#textarea_span').show();
		});
	}




function getImageInfo(id) {
	tinyMCE.activeEditor.setContent("");
	$('#googleMap').remove();
	getAndParseJson();

	function getAndParseJson() {
		$.get("exif/" + id, function(data) {
			setValues(data);
			gmap(data);
			setSaveButtonParams(data);
		});
	}


	function setValues($exifJSON) {
		$('#exif').empty($exifJSON);
		$("#gmap_holder").append("<div id='googleMap' class='gmap'></div>");
		$('#desc_text').html($exifJSON.description);
		tinyMCE.activeEditor.setContent($exifJSON.description || "не установлено");
		$("<h2></h2>").text("Image Info").appendTo($('#exif'));
		$("<p></p>").text("manufacturer: " + ($exifJSON.manufacturer || "не установлено")).appendTo($('#exif'));
		$("<p></p>").text("model: " + ($exifJSON.model || "не установлено")).appendTo($('#exif'));
		$("<p></p>").text("Date andTime: " + (new Date($exifJSON.dateAndTime).toString() || "не установлено")).appendTo($('#exif'));
		$("<p></p>").text("Compression: " + ($exifJSON.compression || "не установлено")).appendTo($('#exif'));
		$("<p></p>").text("Exposure Time: " + ($exifJSON.exposureTime || "не установлено")).appendTo($('#exif'));
		$("<p></p>").text("Exif Version: " + ($exifJSON.exifVersion || "не установлено")).appendTo($('#exif'));
		$("<p></p>").text("latitude: " + ($exifJSON.latitude || "не установлено")).appendTo($('#exif'));
		$("<p></p>").text("longitude: " + ($exifJSON.longitude || "не установлено")).appendTo($('#exif'));
		$('#desc_text').html($exifJSON.description || "не установлено");
	}
	
	function setSaveButtonParams(json){
		$('#save_desc').unbind('click');
		$('#save_desc').click(function() {
			json.description = tinyMCE.activeEditor.getContent({format: 'raw'});
			$('#textarea_span').hide();
			$('#edit_desc_span').show();
			$('#desc_text').html(tinyMCE.activeEditor.getContent({format: 'raw'}));
			$.ajax({
				url: "exif/" + json.id,
				type: "PUT",
				dataType: "json",
				contentType: "application/json; charset=utf-8",
				data: JSON.stringify(json),
				success: function() {}
			});
		});
	}
	
	function gmap(json) {
		if (json.latitude == "0") {
			$('#googleMap').append("<strong>No Geo Data</strong>");
		} else {
			var mapProp = {
				center: new google.maps.LatLng(json.latitude, json.longitude),
				zoom: 10,
				mapTypeId: google.maps.MapTypeId.ROADMAP
			};
			var map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
			var mark = new google.maps.Marker({
				position: new google.maps.LatLng(json.latitude, json.longitude),
				animation: google.maps.Animation.BOUNCE
			});
			mark.setMap(map);
		}
	};
}