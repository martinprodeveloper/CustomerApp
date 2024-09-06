import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.martinprodeveloper.customerapp.ui.login.LoginViewModel
import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockAuth = mockk<FirebaseAuth>()
    private val mockPhoneAuthProvider = mockk<PhoneAuthProvider>()
    private val viewModel = LoginViewModel(mockAuth, mockPhoneAuthProvider)

    @Test
    fun testSendVerificationCode_success() {
        // Arrange
        val phoneNumber = "+51999888777"
        val verificationId = "mockVerificationId"
        val mockToken = mockk<PhoneAuthProvider.ForceResendingToken>()

        every { mockPhoneAuthProvider.verifyPhoneNumber(
            eq(phoneNumber),
            any(),
            any(),
            any(),
            any()
        ) } answers {
            val callback = it.invocation.args[3] as PhoneAuthProvider.OnVerificationStateChangedCallbacks
            callback.onCodeSent(verificationId, mockToken)
        }

        // Act
        viewModel.sendVerificationCode(phoneNumber, mockk())

        // Assert
        assertEquals(
            LoginViewModel.AuthenticationState.CodeSent(verificationId),
            viewModel.authenticationState.value
        )
    }

    @Test
    fun testSendVerificationCode_failure() {
        // Arrange
        val phoneNumber = "+51999888777"
        val errorMessage = "Verification failed"
        val mockException = mockk<FirebaseException> {
            every { message } returns errorMessage
        }

        every { mockPhoneAuthProvider.verifyPhoneNumber(
            eq(phoneNumber),
            any(),
            any(),
            any(),
            any()
        ) } answers {
            val callback = it.invocation.args[3] as PhoneAuthProvider.OnVerificationStateChangedCallbacks
            callback.onVerificationFailed(mockException)
        }

        // Act
        viewModel.sendVerificationCode(phoneNumber, mockk())

        // Assert
        assertEquals(
            LoginViewModel.AuthenticationState.Error(errorMessage),
            viewModel.authenticationState.value
        )
    }
}
