package com.example.weatherapp.retrofit_api.checkNetworkConnection

import okio.IOException


class NoConnectivityException(message: String): IOException(message) {

}