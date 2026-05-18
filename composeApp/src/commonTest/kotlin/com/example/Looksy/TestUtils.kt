package com.example.Looksy

import io.ktor.client.*
import kotlinx.coroutines.test.runTest

fun runTestWithClient(client: HttpClient, testBlock: suspend (HttpClient) -> Unit) {
    runTest {
        try {
            testBlock(client)
        } finally {
            client.close()
        }
    }
}
