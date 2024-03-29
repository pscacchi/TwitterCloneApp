package ar.scacchipa.twittercloneapp.data

import ar.scacchipa.twittercloneapp.utils.MockTokenProvider
import org.junit.Assert
import org.junit.Test

class UserAccessTokenMapperTest {

    private val subject = UserAccessTokenMapper()

    @Test
    fun subjectReturnCredential() {
        val expected = MockTokenProvider.credential1()
        val actual = subject.toDomain( MockTokenProvider.userAccessTokenData1() )
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun subjectReturnUserAccessData() {
        val expected = MockTokenProvider.mappedUserAccessTokenData1()
        val actual = subject.fromDomain( MockTokenProvider.credential1() )
        Assert.assertEquals(expected, actual)
    }
}