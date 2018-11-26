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
        footBallRest.getLastmatchByLeagueId("123")
        verify(footBallRest).getLastmatchByLeagueId("123")
    }

    @Test(expected = ArgumentsAreDifferent::class)
    fun getLastMatch_isWrongArgumentCalled() {
        footBallRest.getLastmatchByLeagueId("123")
        verify(footBallRest).getLastmatchByLeagueId("")
    }

    @Test
    fun getLastMatch_isWrongMethodCalled() {
        footBallRest.getLastmatchByLeagueId("123")
        verify(footBallRest, never()).getUpcomingMatchByLeagueId("123")
    }

    @Test
    fun getUpcomingMatch_isValid() {
        footBallRest.getUpcomingMatchByLeagueId("123")
        verify(footBallRest).getUpcomingMatchByLeagueId("123")
    }

    @Test(expected = ArgumentsAreDifferent::class)
    fun getUpcomingMatch_isWrongArgumentCalled() {
        footBallRest.getUpcomingMatchByLeagueId("123")
        verify(footBallRest).getUpcomingMatchByLeagueId("")
    }

    @Test
    fun getUpcomingMatch_isWrongMethodCalled() {
        footBallRest.getUpcomingMatchByLeagueId("123")
        verify(footBallRest, never()).getLastmatchByLeagueId("123")
    }

    @Test
    fun getMatchDetail_isValid() {
        footBallRest.getMatchDetailById("123")
        verify(footBallRest).getMatchDetailById("123")
    }

    @Test(expected = ArgumentsAreDifferent::class)
    fun getMatchDetail_isWrongArgumentCalled() {
        footBallRest.getMatchDetailById("123")
        verify(footBallRest).getMatchDetailById("")
    }

    @Test
    fun getMatchDetail_isWrongMethodCalled() {
        footBallRest.getMatchDetailById("123")
        verify(footBallRest, never()).getTeamById("123")
    }

    @Test
    fun getTeam_isValid() {
        footBallRest.getTeamById("123")
        verify(footBallRest).getTeamById("123")
    }

    @Test(expected = ArgumentsAreDifferent::class)
    fun getTeam_isWrongArgumentCalled() {
        footBallRest.getTeamById("123")
        verify(footBallRest).getTeamById("")
    }

    @Test
    fun getTeam_isWrongMethodCalled() {
        footBallRest.getTeamById("123")
        verify(footBallRest, never()).getMatchDetailById("123")
    }

    @Test
    fun getAllLeagues_isValid() {
        footBallRest.getAllLeagues()
        verify(footBallRest).getAllLeagues()
    }

    @Test
    fun getAllLeagues_isWrongMethodCalled() {
        footBallRest.getAllLeagues()
        verify(footBallRest, never()).getMatchDetailById("123")
    }
}
