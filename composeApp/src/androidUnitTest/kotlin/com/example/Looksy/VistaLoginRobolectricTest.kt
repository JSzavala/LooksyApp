package com.example.Looksy

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.Looksy.Login.Presentacion.VistaLogin
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [34])
class VistaLoginRobolectricTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun login_muestra_titulo_LOOKSY() {
        composeTestRule.setContent {
            MaterialTheme {
                VistaLogin(
                    onLoginSuccess = {},
                    onCreateAccount = {}
                )
            }
        }
        composeTestRule.onNodeWithText("LOOKSY").assertIsDisplayed()
    }

    @Test
    fun login_muestra_boton_iniciar_sesion() {
        composeTestRule.setContent {
            MaterialTheme {
                VistaLogin(
                    onLoginSuccess = {},
                    onCreateAccount = {}
                )
            }
        }
        composeTestRule.onNodeWithText("Iniciar Sesi\u00F3n").assertIsDisplayed()
    }

    @Test
    fun login_muestra_enlace_registro() {
        composeTestRule.setContent {
            MaterialTheme {
                VistaLogin(
                    onLoginSuccess = {},
                    onCreateAccount = {}
                )
            }
        }
        val nodes = composeTestRule.onAllNodesWithText("Reg\u00EDstrate")
        assert(nodes.fetchSemanticsNodes().isNotEmpty()) { "El texto Regístrate debe existir en la composicion" }
    }

    @Test
    fun login_muestra_campos_de_texto() {
        composeTestRule.setContent {
            MaterialTheme {
                VistaLogin(
                    onLoginSuccess = {},
                    onCreateAccount = {}
                )
            }
        }
        composeTestRule.onNodeWithText("Correo").assertIsDisplayed()
        composeTestRule.onNodeWithText("Contrase\u00F1a").assertIsDisplayed()
    }

    @Test
    fun login_con_campos_vacios_muestra_error() {
        composeTestRule.setContent {
            MaterialTheme {
                VistaLogin(
                    onLoginSuccess = {},
                    onCreateAccount = {}
                )
            }
        }
        composeTestRule.onNodeWithText("Iniciar Sesi\u00F3n").performClick()
        composeTestRule.onNodeWithText("Llena todos los campos").assertIsDisplayed()
    }
}
