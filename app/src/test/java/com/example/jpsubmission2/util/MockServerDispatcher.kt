package com.example.jpsubmission2.util

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.io.InputStreamReader

class MockServerDispatcher {

    var fileName = String()

    /**
     * Return ok response from mock server
     */
    internal inner class RequestDispatcher() : Dispatcher() {

        override fun dispatch(request: RecordedRequest): MockResponse {
            return if (request.path != null) {
                MockResponse().setResponseCode(200).setBody(getJsonContent(fileName))
            } else {
                MockResponse().setResponseCode(400)
            }
        }
    }

    /**
     * Return error response from mock server
     */
    internal inner class ErrorDispatcher : Dispatcher() {

        override fun dispatch(request: RecordedRequest): MockResponse {
            return MockResponse().setResponseCode(400)
        }
    }

    private fun getJsonContent(fileName: String): String {
        return InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(fileName)).use { it.readText() }
    }

}

