package com.sudoajay.github_api.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.asFlow
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sudoajay.github_api.R
import com.sudoajay.github_api.databinding.ActivityMainBinding
import com.sudoajay.github_api.helper.InsetDivider
import com.sudoajay.github_api.main.BaseActivity
import com.sudoajay.github_api.repository.GithubAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    @Inject
    lateinit var navigationDrawerBottomSheet: NavigationDrawerBottomSheet

    @Inject
    lateinit var githubAdapter: GithubAdapter

    val viewModel: MainViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    private var isDarkTheme: Boolean = false
    private var TAG = "MainActivityTAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isDarkTheme = isSystemDefaultOn()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!isDarkTheme) {
                WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars =
                    true
            }

        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewmodel = viewModel
        binding.activity = this
        binding.lifecycleOwner = this
    }

    override fun onResume() {
        super.onResume()
        setReference()
    }

    private fun setReference() {

        //      Setup Swipe Refresh
        binding.swipeRefresh.setColorSchemeResources(R.color.colorAccent)
        binding.swipeRefresh.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(applicationContext, R.color.swipeBgColor)
        )

        binding.swipeRefresh.setOnRefreshListener {
//            refreshData()
        }


        //         Setup BottomAppBar Navigation Setup
        binding.bottomAppBar.navigationIcon?.mutate()?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                it.setTint(
                    ContextCompat.getColor(applicationContext, R.color.colorAccent)
                )
            }
            binding.bottomAppBar.navigationIcon = it
        }

        setSupportActionBar(binding.bottomAppBar)

        setRecyclerView()


    }

    private fun setRecyclerView() {
        val divider = getInsertDivider()
        binding.recyclerView.addItemDecoration(divider)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.recyclerView.adapter = githubAdapter
        dataChange()

        viewModel.searchValue("SudoAjay")
    }

    private fun dataChange() {
        Log.e(TAG, "dataChange:  I am here " )
        lifecycleScope.launch {
            viewModel.listItem.asFlow().collectLatest {
                Log.e(TAG, "dataChange:  I am here wuth data  ${it.toString()} " )
                githubAdapter.submitData(it)
                viewModel.hideProgress.postValue(true)
            }
        }
    }
    private fun setValueHideProgress(boolean: Boolean){
        viewModel.hideProgress.postValue(boolean)
    }

    private fun getInsertDivider(): RecyclerView.ItemDecoration {
        val dividerHeight = resources.getDimensionPixelSize(R.dimen.divider_height)
        val dividerColor = ContextCompat.getColor(
            applicationContext,
            R.color.divider
        )
        val marginLeft = resources.getDimensionPixelSize(R.dimen.divider_inset)
        return InsetDivider.Builder(this)
            .orientation(InsetDivider.VERTICAL_LIST)
            .dividerHeight(dividerHeight)
            .color(dividerColor)
            .insets(marginLeft, 0)
            .build()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bottom_toolbar_menu, menu)
        val actionSearch = menu.findItem(R.id.search_optionMenu)
        manageSearch(actionSearch)

        return super.onCreateOptionsMenu(menu)
    }

    private fun manageSearch(searchItem: MenuItem) {
        val searchView =
            searchItem.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_SEARCH
        manageFabOnSearchItemStatus(searchItem)
        manageInputTextInSearchView(searchView)
    }

    private fun manageFabOnSearchItemStatus(searchItem: MenuItem) {
        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                return true
            }
        })
    }
    private fun manageInputTextInSearchView(searchView: SearchView) {
        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newText: String): Boolean {
                val query: String = newText.lowercase(Locale.ROOT).trim { it <= ' ' }
                viewModel.searchValue(query)

                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {

                refreshData()
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> showNavigationDrawer()
            R.id.setting_optionMenu -> openSetting()
            R.id.refresh_optionMenu -> refreshData()

            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }

    private fun showNavigationDrawer() {
        navigationDrawerBottomSheet.show(
            supportFragmentManager.beginTransaction(),
            navigationDrawerBottomSheet.tag
        )
    }


    fun openSetting() {


    }

    fun refreshData(){

    }
}