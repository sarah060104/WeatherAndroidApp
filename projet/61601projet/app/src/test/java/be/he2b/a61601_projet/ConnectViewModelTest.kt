package be.he2b.a61601_projet

import be.he2b.a61601_projet.ui.connect.ConnectViewModel
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class ConnectViewModelTest {
    @Test
    fun onEmailChangeStateEmailIsActualized() {
        val viewModel = ConnectViewModel()
        viewModel.onEmailChange("abc")
        val s = viewModel.uiState.value
        assertEquals("abc", s.email)
        assertFalse(s.isError)
        assertNull(s.errorMessage)
        assertFalse(s.navigate)
    }

    @Test
    fun onSubmitValidEmailPassesFormatCheck() {
        val vm = ConnectViewModel()
        vm.onEmailChange("user@he2b.be")
        vm.onSubmit()
        val s = vm.uiState.value
       assertNotEquals("Adresse e-mail invalide", s.errorMessage)
    }

    @Test
    fun onSubmitWrongEmailFormat() {
        val vm = ConnectViewModel()
        vm.onEmailChange("notanemail")
        vm.onSubmit()
        val s = vm.uiState.value
        assertTrue(s.isError)
        assertEquals("Adresse e-mail invalide", s.errorMessage)
        assertFalse(s.navigate)
    }

    @Test
    fun onSubmitWrongEmailDomain()  {
        val vm = ConnectViewModel()
        vm.onEmailChange("test@gmail.com")
        vm.onSubmit()
        val s = vm.uiState.value
        assertTrue(s.isError)
        assertEquals("L'adresse doit se terminer par @${s.domain}", s.errorMessage)
        assertFalse(s.navigate)
    }


}