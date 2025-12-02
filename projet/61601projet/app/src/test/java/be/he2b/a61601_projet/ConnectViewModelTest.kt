package be.he2b.a61601_projet

import be.he2b.a61601_projet.httpRequestEtCie.ConnectViewModel
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
    fun onSubmitGoodEmail() {
        val vm = ConnectViewModel()
        vm.onEmailChange("user@he2b.be")
        vm.onSubmit()
        var s = vm.uiState.value
        assertFalse(s.isError)
        assertNull(s.errorMessage)
        assertTrue(s.navigate)

        vm.onNavigated()
        s = vm.uiState.value
        assertFalse(s.navigate)
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