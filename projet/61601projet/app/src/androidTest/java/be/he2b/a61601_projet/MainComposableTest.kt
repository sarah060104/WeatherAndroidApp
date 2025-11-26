package be.he2b.a61601_projet

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainComposableTest {


    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()


    @Test
    fun showsError_whenEmailInvalid() {
        composeRule.onNodeWithTag("emailField").performTextInput("notanemail")
        composeRule.onNodeWithTag("okButton").performClick()
        composeRule.onNodeWithText("Adresse e-mail invalide").assertExists()
    }


    @Test
    fun navigatesToHome_andShowsLogo_whenEmailValid() {
        composeRule.onNodeWithTag("emailField").performTextClearance()
        composeRule.onNodeWithTag("emailField").performTextInput("user@he2b.be")
        composeRule.onNodeWithTag("okButton").performClick()
        composeRule.onNodeWithContentDescription("LogoESI").assertExists()
    }


}