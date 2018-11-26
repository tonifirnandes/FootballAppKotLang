package com.muslimlife.tf.footballappkotlang.features.team.detail

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.muslimlife.tf.footballappkotlang.R
import com.muslimlife.tf.footballappkotlang.R.drawable.ic_add_to_favorites
import com.muslimlife.tf.footballappkotlang.R.drawable.ic_favorited
import com.muslimlife.tf.footballappkotlang.R.menu.detail_activity_options_menu
import com.muslimlife.tf.footballappkotlang.data.model.Team
import com.muslimlife.tf.footballappkotlang.data.model.local_db.FavoriteTeam
import com.muslimlife.tf.footballappkotlang.extensions.hide
import com.muslimlife.tf.footballappkotlang.extensions.rx.SchedulerProvider
import com.muslimlife.tf.footballappkotlang.extensions.show
import kotlinx.android.synthetic.main.team_detail_activity.*
import org.jetbrains.anko.design.snackbar

class TeamDetailActivity : AppCompatActivity(), TeamDetailContract.View {

    private val teamDetailPresenter: TeamDetailPresenter = TeamDetailPresenter(
        SchedulerProvider()
    )
    private var menuItem: MenuItem? = null
    private var isFavoriteTeam: Boolean = false
    private var team: Team? = null
    private var favoriteTeam: FavoriteTeam? = null
    private var isCheckingFavorite = true

    companion object {
        const val arg_team_bundle_key: String = "team"
        const val arg_favorite_team_bundle_key: String = "favorite_team"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.team_detail_activity)
        team = intent.getParcelableExtra(arg_team_bundle_key)
        favoriteTeam = intent.getParcelableExtra(arg_favorite_team_bundle_key)
        teamDetailPresenter.attach(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        teamDetailPresenter.detach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_activity_options_menu, menu)
        menuItem = menu?.findItem(R.id.add_to_favorite)
        //sometimes menuitem is still null, so process presenter after menuitem rendered successfully
        teamDetailPresenter.setupView(team, favoriteTeam)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                true
            }
            R.id.add_to_favorite -> {
                addToFavorite()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }

    override fun onSaveFavoriteTeamLoading() {
        showDetailActivityActionLoading()
    }

    override fun onSaveFavoriteTeamSuccess() {
        cl_root_view.snackbar(R.string.str_save_favorite_success)
    }

    override fun onSaveFavoriteTeamFailed() {
        cl_root_view.snackbar(R.string.str_save_favorite_failed)
        hideDetailActivityActionLoading()
    }

    override fun onSetFavoriteView(isFavorite: Boolean) {
        isFavoriteTeam = isFavorite
        hideDetailActivityActionLoading()
        isCheckingFavorite = false
        if (isFavorite) {
            menuItem?.icon = ContextCompat.getDrawable(this, ic_favorited)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
        }
    }

    override fun onUnFavoriteTeamLoading() {
        hideDetailActivityActionLoading()
    }

    override fun onUnFavoriteTeamSuccess() {
        cl_root_view.snackbar(R.string.str_delete_favorite_success)
    }

    override fun onUnFavoriteTeamFailed() {
        cl_root_view.snackbar(R.string.str_delete_favorite_failed)
        hideDetailActivityActionLoading()
    }

    override fun onGetTeamDetailsByIdLoading() {
        showDetailActivityActionLoading()
    }

    override fun onGetTeamDetailsByIdFailed() {
        hideDetailActivityActionLoading()
    }

    override fun showDetailActivityActionLoading() {
        pb_loading_team_detail_activity_action.show()
    }

    override fun onSetupViewSuccess(teamLoaded: Team) {
        team = teamLoaded
        hideDetailActivityActionLoading()
        initView(teamLoaded)
        supportActionBar?.title = teamLoaded.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        teamDetailPresenter.isFavoriteTeam(teamLoaded.id, this)
    }

    override fun onSetupViewFailed(isRetryAble: Boolean) {
        if (isRetryAble) {
            cl_root_view.snackbar(R.string.str_generic_error_failed, R.string.str_retry) {
                teamDetailPresenter.setupView(
                    team,
                    favoriteTeam
                )
            }
        } else {
            cl_root_view.snackbar(R.string.str_generic_error_failed, R.string.str_back) {
                onBackPressed()
            }
        }
        hideDetailActivityActionLoading()
    }

    override fun hideDetailActivityActionLoading() {
        pb_loading_team_detail_activity_action.hide()
    }

    private fun initView(team: Team) {
        Glide.with(iv_team_logo)
            .load(team.badgeUrl)
            .apply(RequestOptions().placeholder(R.mipmap.ic_launcher))
            .into(iv_team_logo)
        tv_team_name.text = team.name
        tv_team_established_year.text = team.establishedYear
        tv_team_stadium.text = team.stadiumName
        vp_team_info.adapter =
                TeamDetailInfoTabAdapter(
                    supportFragmentManager,
                    resources.getStringArray(R.array.team_tab_info_types), team
                )
        tl_team_info.setupWithViewPager(vp_team_info)
        vp_team_info.show()
        tl_team_info.show()
    }

    private fun addToFavorite() {
        if (team == null || isCheckingFavorite) {
            return
        }
        if (!isFavoriteTeam) {
            teamDetailPresenter.saveFavoriteTeam(team, this)
        } else {
            teamDetailPresenter.unFavoriteTeam(team?.id, this)
        }
    }

}