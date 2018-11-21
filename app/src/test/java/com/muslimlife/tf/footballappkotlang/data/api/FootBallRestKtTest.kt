package com.muslimlife.tf.footballappkotlang.data.api

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.exceptions.verification.junit.ArgumentsAreDifferent

class FootBallRestKtTest {

    @Mock
    private lateinit var footBallRest: FootBallRest

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getLastMatch_isValid() {
        footBallRest.getLastmatch("123")
        verify(footBallRest).getLastmatch("123")
    }

    @Test(expected = ArgumentsAreDifferent::class)
    fun getLastMatch_isWrongArgumentCalled() {
        footBallRest.getLastmatch("123")
        verify(footBallRest).getLastmatch("1234")
    }

    @Test
    fun getLastMatch_isWrongMethodCalled() {
        footBallRest.getLastmatch("123")
        verify(footBallRest, never()).getUpcomingMatch("123")
    }

    @Test
    fun getUpcomingMatch_isValid() {
        footBallRest.getUpcomingMatch("123")
        verify(footBallRest).getUpcomingMatch("123")
    }

    @Test(expected = ArgumentsAreDifferent::class)
    fun getUpcomingMatch_isWrongArgumentCalled() {
        footBallRest.getUpcomingMatch("123")
        verify(footBallRest).getUpcomingMatch("1234")
    }

    @Test
    fun getUpcomingMatch_isWrongMethodCalled() {
        footBallRest.getUpcomingMatch("123")
        verify(footBallRest, never()).getLastmatch("123")
    }

    @Test
    fun getMatchDetail_isValid() {
        footBallRest.getMatchDetail("123")
        verify(footBallRest).getMatchDetail("123")
    }

    @Test(expected = ArgumentsAreDifferent::class)
    fun getMatchDetail_isWrongArgumentCalled() {
        footBallRest.getMatchDetail("123")
        verify(footBallRest).getMatchDetail("1234")
    }

    @Test
    fun getMatchDetail_isWrongMethodCalled() {
        footBallRest.getMatchDetail("123")
        verify(footBallRest, never()).getTeam("123")
    }

    @Test
    fun getTeam_isValid() {
        footBallRest.getTeam("123")
        verify(footBallRest).getTeam("123")
    }

    @Test(expected = ArgumentsAreDifferent::class)
    fun getTeam_isWrongArgumentCalled() {
        footBallRest.getTeam("123")
        verify(footBallRest).getTeam("1234")
    }

    @Test
    fun getTeam_isWrongMethodCalled() {
        footBallRest.getTeam("123")
        verify(footBallRest, never()).getMatchDetail("123")
    }

    @Test
    fun getAllLeagues_isValid() {
        footBallRest.getAllLeagues()
        verify(footBallRest).getAllLeagues()
    }

    @Test
    fun getAllLeagues_isWrongMethodCalled() {
        footBallRest.getAllLeagues()
        verify(footBallRest, never()).getMatchDetail("123")
    }
}
