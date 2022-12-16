package com.miniclip.poulefase.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miniclip.poulefase.data.model.Pool
import com.miniclip.poulefase.simulator.GameSimulator
import com.miniclip.poulefase.utilities.Constants

class HomeViewModel: ViewModel() {
    private val _homeState = MutableLiveData<HomeDataState>()
    val homeState: LiveData<HomeDataState> = _homeState

    private val pools: MutableList<Pool> = mutableListOf()

    init {
        createPools()
    }

    // This function makes the pools
    private fun createPools(){
        pools.add(Pool(Constants.POOL_NAME))
        updateLiveData()
    }

    fun playGame(pool: Pool){
        val team1 = pool.getTeam1()
        val team2 = pool.getTeam2()

        // A game is simulated and the outcome of the game is returned
        val game = GameSimulator.simulateGame(team1 = team1, team2 = team2)

        pool.addGame(game)
        pool.setGameResults(game)
        pool.setNextGame()
        pool.setPoolLinesInTheRightOrder()

        updateLiveData()
    }

    private fun updateLiveData(){
        _homeState.postValue(HomeDataState(pools = pools))
    }
}