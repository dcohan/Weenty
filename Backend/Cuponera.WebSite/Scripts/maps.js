var geo = {
	maps: {
		internal: {
			__defZoom: 1,
			__defCenter: { 
				lat: -37.78617433071393, 
				lng: -57.4816894903779
			},
			__defMapTypeId: google.maps.MapTypeId.ROADMAP,

			__selectedCoord: {
				lat: 0,
				lng: 0
			},


			initialize: function() {
				var mapProp = {
			   		center: new google.maps.LatLng(this.__defCenter.lat, this.__defCenter.lng),
			    	zoom: this.__defZoom,
			    	mapTypeId: this.__defMapTypeId
			  	};
			  	var map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
			  	var wrapperMap = this.wrapper(map);

			  	var internal = this;
				google.maps.event.addListener(wrapperMap.map, 'click', function(event) {
					internal.__selectedCoord.lat = event.latLng.k;
					internal.__selectedCoord.lng = event.latLng.B;
					var coord = new google.maps.LatLng(internal.__selectedCoord.lat, internal.__selectedCoord.lng);

					wrapperMap.drawMarker(internal.__selectedCoord.lat, internal.__selectedCoord.lng);
				});

				return wrapperMap;
			},


			wrapper: function(map) {
				var internal = this;
				return {
					lat: 0,
					lng: 0,

					map: map,
					marker: undefined,

					setCenter: function(lat, lng) {
						var coord = new google.maps.LatLng(lat, lng);
						this.map.setCenter(coord);
					},
					setZoom: function(level) {
						this.map.setZoom(level);
					},
					drawMarker: function(lat, lng) {
						this.clearMarkers();
						var coord = new google.maps.LatLng(lat, lng);
						this.marker = new google.maps.Marker({
							position: coord,
							map: this.map,
							animation: google.maps.Animation.DROP
						})

						this.lat = lat;
						this.lng = lng;

						if (drawMarkerCallback && typeof drawMarkerCallback === 'function') {
							drawMarkerCallback(lat, lng);
						}
					},
					clearMarkers: function() {
						if (this.marker) {
							this.marker.setMap(null);	
						}
					}
				};
			},

		},


		initialize: function() {
			return this.internal.initialize();
		}
	},







	geocoder: {
		internal: {
			__mainUrl: 'http://maps.googleapis.com/maps/api/geocode/json?',

			initialize: function(map) {
				return this.wrapper(map);
			},

			wrapper: function(map) {
				var internal = this;
				return {
					map: map,

					getCoordinatesByAddress: function(address, city, province, country) {
						var wrapper = this;
						$.get(internal.__mainUrl + 'address=' + this._encodeUrl(address + ',' + city + ',' + province + ',' + country) + '&sensor=false')
							.done(function(response){
								if (response.status != 'OK' || response.results.length === 0) { return; }
								
								var results = response.results[0];
								coord = results.geometry.location;
								wrapper._drawLocation(coord);
							});
					},

					checkIfUserCanAskForCoordinates: function(callbacks) {
						$.get('geo/userCanDoGeoRequest')
							.done(function(response){
								if (callbacks && callbacks.done) { 
									callbacks.done(response);
								}
							});
					},

					_drawLocation: function(coord) {
						this.map.drawMarker(coord.lat, coord.lng);
						this.map.setZoom(16);
						this.map.setCenter(coord.lat, coord.lng);
					},

					_encodeUrl: function(url) {
						return url.replace(/ /g, '+');
					}
				};
			},

			

		},

		initialize: function(map) {
			return this.internal.initialize(map);
		}
	}
};



var maps = {
	initialize: function() {
		return geo.maps.initialize();
	}
};

var geocoder = {
	initialize: function(map) {
		return geo.geocoder.initialize(map);
	}
};