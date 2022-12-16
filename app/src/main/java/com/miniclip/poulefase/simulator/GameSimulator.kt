package com.miniclip.poulefase.simulator

import com.miniclip.poulefase.data.model.FootballMatch
import com.miniclip.poulefase.data.model.Team
import com.miniclip.poulefase.utilities.Constants
import kotlin.random.Random

object GameSimulator {

    // Function to determine the result of the match
    private fun simulateGoalAttempt(team1Strength: Double, team2Strength: Double): Int {
        // A random number is generated to determine who wins
        val randomNumber = Math.random()

        // The team with higher power has more chance to win.
        return if (randomNumber < team1Strength / (team1Strength + team2Strength))
                Constants.TEAM_1_NUMBER  else Constants.TEAM_2_NUMBER
    }

    fun simulateGame(team1: Team, team2: Team): FootballMatch{

        //Get random amount goal attempt between MIN_GOAL_ATTEMPT and MAX_GOAL_ATTEMPT
        val amountGoalAttempt: Int =
            Random.nextInt(Constants.MIN_GOAL_ATTEMPT,Constants.MAX_GOAL_ATTEMPT)

        var team1Score = Constants.BEGIN_SCORE
        var team2Score = Constants.BEGIN_SCORE

        for (i in 0 until amountGoalAttempt){
            val result = simulateGoalAttempt(team1.strength, team2.strength)

            // If result == TEAM_1_NUMBER then team1 has scored
            // If result == TEAM_2_NUMBER then team2 has scored
            if(result == Constants.TEAM_1_NUMBER) team1Score++
            else team2Score++
        }

        // Return result from the match
        return FootballMatch(team1 = team1, team2 = team2,
            team1Goals= team1Score, team2Goals = team2Score)
    }
}