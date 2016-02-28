var tunaryApp = angular.module("tunaryApp", ["ngRoute"]);

tunaryApp.config(function($routeProvider) {
	$routeProvider
		.when("/", {
			templateUrl: "home.html",
			controller: "tunaryController"
		})
		.when("/bands", {
			templateUrl: "bands.html",
			controller: "tunaryController"
		})
		.when("/albums", {
			templateUrl: "albums.html",
			controller: "tunaryController"
		})
		.when("/songs", {
			templateUrl: "songs.html",
			controller: "tunaryController"
		});
});

tunaryApp.controller("tunaryController", function($scope, $http) {
	$scope.band = null;
	$scope.newBand = false;

	$scope.album = null;
	$scope.newAlbum = false;

	$scope.song = null;
	$scope.newSong = false;
	
	$scope.loadBands = function() {
		$http.get("bands")
			.then(function(response) {
				$scope.bands = response.data;
			});
	}
	$scope.loadBand = function(band) {
		$scope.band = band;
		$http.get("albums/" + band.id)
			.then(function(response) {
				$scope.albums = response.data;
			});
	};
	
	$scope.bandList = function() {
		$scope.band = null;
		$scope.newBand = false;
	}
	
	$scope.saveBand = function() {
		$http.post("band", angular.toJson($scope.band))
			.then(function(response) {
				$scope.bandList.call();
				$scope.loadBands.call();
			});
	}
	$scope.createBand = function() {
		$scope.band = {};
		$scope.newBand = true;
	}
	
	$scope.loadAlbums = function() {
		$http.get("albums")
			.then(function(response) {
				$scope.albums = response.data;
			});
	}
	$scope.loadAlbum = function(album) {
		$scope.album = album;
		$http.get("songs/" + band.id)
		.then(function(response) {
			$scope.songs = response.data;
		});
	};
	
	$scope.albumList = function() {
		$scope.album = null;
		$scope.newAlbum = false;
	}
	
	$scope.saveAlbum = function() {
		$http.post("album", angular.toJson($scope.album))
			.then(function(response) {
				$scope.albumList.call();
				if($scope.band == null) {
					$scope.loadAlbums.call();
				} else {
					$scope.loadBand.call($scope, $scope.band);
				}
			});
	}
	$scope.createAlbum = function() {
		$scope.album = {};
		$scope.newAlbum = true;
		if($scope.bands == null) {
			$scope.loadBands.call();
		}
	}
	
	$scope.loadSongs = function() {
		$http.get("songs")
			.then(function(response) {
				$scope.songs = response.data;
			});
	}
	$scope.loadSong = function(song) {
		$scope.song = song;
	};
	
	$scope.songList = function() {
		$scope.song = null;
		$scope.newSong = false;
	}
	
	$scope.saveSong = function() {
		$http.post("song", angular.toJson($scope.song))
			.then(function(response) {
				$scope.songList.call();
				if($scope.album == null) {
					$scope.loadSongs.call();
				} else {
					$scope.loadAlbum.call($scope, $scope.album);
				}
			});
	}
	$scope.createSong = function() {
		$scope.song = {};
		$scope.newSong = true;
		if($scope.bands == null) {
			$scope.loadBands.call();
		}
		if($scope.albums == null) {
			$scope.loadAlbums.call();
		}
	}
});