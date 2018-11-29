package com.muslimlife.tf.footballappkotlang.features.detail

import com.muslimlife.tf.footballappkotlang.data.api.FootBallRest
import com.muslimlife.tf.footballappkotlang.data.model.Event
import com.muslimlife.tf.footballappkotlang.data.model.Events
import com.muslimlife.tf.footballappkotlang.data.model.local_db.FavoriteMatch
import com.muslimlife.tf.footballappkotlang.extensions.rx.BaseSchedulerProvider
import com.muslimlife.tf.footballappkotlang.extensions.rx.TestSchedulerProvider
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.exceptions.verification.junit.ArgumentsAreDifferent

class MatchScheduleDetailPresenterTest {

    @Mock
    private lateinit var mViewMock: MatchScheduleDetailContract.View

    @Mock
    private lateinit var eventMock: Event

    @Mock
    private lateinit var eventListMock: Events

    @Mock
    private lateinit var footBallRest: FootBallRest

    private lateinit var favoriteEventMock: FavoriteMatch

    private lateinit var mPresenter: MatchScheduleDetailPresenter

    private lateinit var scheduler: BaseSchedulerProvider

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        scheduler = TestSchedulerProvider()
        mPresenter = MatchScheduleDetailPresenter(scheduler)
        mPresenter.attach(mViewMock)
        favoriteEventMock = FavoriteMatch(
            id = null,
            matchId = "123",
            matchName = "",
            homeTeamName = "",
            homeTeamScore = null,
            awayTeamName = "",
            awayTeamScore = null,
            matchDate = "",
            matchTime = ""
        )
    }

    @Test
    fun setupViewWithNoEventData_isValid() {
        mPresenter.setupView(null, null)
        verify(mViewMock).onSetupViewFailed(false)
    }

    @Test(expected = ArgumentsAreDifferent::class)
    fun setupViewWithNoEventData_isInValid() {
        mPresenter.setupView(null, null)
        verify(mViewMock).onSetupViewFailed(true)
    }

    @Test
    fun setupViewWithEventData_isValid() {
        mPresenter.setupView(eventMock, null)
        verify(mViewMock).onSetupViewSuccess(eventMock)
    }

    @Test
    fun setupViewWithFavoriteEventData_isValid() {
        mPresenter.setupView(null, favoriteEventMock)
        verify(mViewMock).showDetailActivityActionLoading()
        `when`(footBallRest.getMatchDetailById(ArgumentMatchers.anyString())).thenReturn(Flowable.just(eventListMock))
        footBallRest.getMatchDetailById(ArgumentMatchers.anyString())
        verify(footBallRest).getMatchDetailById(ArgumentMatchers.anyString())
        mViewMock.onSetupViewSuccess(eventMock)
        verify(mViewMock).onSetupViewSuccess(eventMock)
    }


}