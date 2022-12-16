package com.miniclip.poulefase.rules

import com.miniclip.poulefase.data.model.FootballMatch
import com.miniclip.poulefase.data.model.Pool
import com.miniclip.poulefase.data.model.PoolLine
import kotlin.random.Random

object PoolRules {

    private lateinit var pool: Pool
    private lateinit var team1: PoolLine
    private lateinit var team2: PoolLine

    // By return True = team1 perform better than team2
    // By return False = team2 perform better than team1
    fun checkIfTeamBetterPerformThanTheOther(team1: PoolLine , team2: PoolLine, pool: Pool) : Boolean{
        this.pool = pool
        this.team1 = team1
        this.team2 = team2
        return checkWhoHasTheMostPoints()
    }

    // Check which team has the most points.
    // The team that has the most point, perform better than the other team.
    // If the points are equal then the Goal Difference will be compared
    private fun checkWhoHasTheMostPoints(): Boolean{
        return when{
            team1.points > team2.points -> true
            team1.points < team2.points -> false
            else -> checkGoalDifference()
        }
    }

    // Check the  Goal Difference between the teams
    // The Goal Difference is (goals for - goals against)
    // The team with the most Goal Difference, perform better than the other team.
    // If the Goal Difference are equal, then the Goals For will be compared
    private fun checkGoalDifference() : Boolean{
        val team1GoalDifference = team1.goalsFor - team1.goalsAgainst
        val team2GoalDifference = team2.goalsFor - team2.goalsAgainst
        return when{
            team1GoalDifference > team2GoalDifference -> true
            team1GoalDifference < team2GoalDifference -> false
            else -> checkWhoHasTheMostScoredGoals()
        }
    }

    // Check the Goals For between the teams
    // The team that has scored the most, perform better than the other team.
    // If the Goals For are equal, then the Mutual Result will be checked
    private fun checkWhoHasTheMostScoredGoals() : Boolean{
        return when{
            team1.goalsFor > team2.goalsFor -> true
            team1.goalsFor < team2.goalsFor -> false
            else -> checkMutualResult()
        }
    }

    // Check the Mutual Result between the teams
    // The Mutual Result check which team has won during the match against each other
    // If the teams had a draw, then a random team is chosen.
    private fun checkMutualResult() : Boolean{
        var playedGame: FootballMatch? = null

        for(game in pool.games){
            playedGame = when{
                game.team1 == team1.team && game.team2== team2.team -> game
                game.team1 == team2.team && game.team2== team1.team -> game
                else -> continue
            }
        }
        return if(playedGame!= null) {
            when{
                playedGame.team1Goals > playedGame.team2Goals -> true
                playedGame.team1Goals < playedGame.team2Goals -> false
                else -> raffleWhoWillWin()
            }
        }else{
            raffleWhoWillWin()
        }
    }

    // A random team is chosen
    private fun raffleWhoWillWin() : Boolean{ return Random.nextBoolean() }
}