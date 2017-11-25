package com.andretietz.auth

import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*


class CompositeAndroidCredentialProviderTest {


    private val authProvider1 = Mockito.mock(AndroidCredentialProvider::class.java)
    private val authProvider2 = Mockito.mock(CredentialProvider::class.java)
    private val provider = CompositeAndroidCredentialProvider(hashMapOf<String, CredentialProvider>(
            "test" to authProvider1,
            "test2" to authProvider2)
    )

    @Test(expected = IllegalStateException::class)
    fun testIfAuthenticateFailsWhenFalseTypeAsArgument() {
        provider.authenticate("false-type")
    }

    @Test
    fun testIfAuthenticateRunsWhenRightTypeAsArgument() {
        provider.authenticate("test")
        verify(authProvider1, times(1)).requestCredential()
        verify(authProvider2, never()).requestCredential()
    }


    @Test
    fun testIfOnActivityResultIsCalledCorrect() {
        provider.onActivityResult(1, 2, null)

        verify(authProvider1, times(1))
                .onActivityResult(1, 2, null)
//        verify(authProvider2, times(1))
//                .onActivityResult(anyInt(), anyInt(), null)
    }

}