package com.miniclip.poulefase.data.model

import com.miniclip.poulefase.rules.PoolRules
import com.miniclip.poulefase.utilities.Constants
import kotlin.random.Random

class Pool(val poolName: String) {

   // A teams list to keep the order of the games
    private val teams: MutableList<Team> = mutableListOf()

    var poolLines: MutableList<PoolLine> = mutableListOf()
        private set

    var games: MutableList<FootballMatch> = mutableListOf()
       private set

    private var currentTeamIndex: Int = 0
    private var currentOpponentIndex: Int = currentTeamIndex +1

    init {
        createTeams()
    }

    fun getTeam1() : Team { return teams[currentTeamIndex] }

    fun getTeam2() : Team { return teams[currentOpponentIndex] }

    fun hasNextGame(): Boolean { return currentOpponentIndex < teams.size }

    fun addGame(game: FootballMatch) { games.add(game)}

    // Set GameResults in a poolLine
    fun setGameResults(game: FootballMatch){
        // Update amount of goals
        for (poolLine in poolLines){
            if(poolLine.team == game.team1){
                poolLine.goalsFor += game.team1Goals
                poolLine.goalsAgainst += game.team2Goals
            }else if(poolLine.team == game.team2){
                poolLine.goalsFor += game.team2Goals
                poolLine.goalsAgainst += game.team1Goals
            }
        }

        // Check which team (win or loss or draw) and update poolLine
        if(game.team1Goals > game.team2Goals){
            setTeamWin(game.team1)
            setTeamLoss(game.team2)
        }else if(game.team1Goals < game.team2Goals){
            setTeamWin(game.team2)
            setTeamLoss(game.team1)
        }else{
            setTeamDraw(game.team1)
            setTeamDraw(game.team2)
        }
    }

    // Update poolLine by win
    private fun setTeamWin(team: Team){
        for (poolLine in poolLines){
            if(poolLine.team == team){
                poolLine.wins++
                poolLine.gamesPlayed++
                poolLine.points += Constants.WIN_POINTS
            }
        }
    }

    // Update poolLine by loss
    private fun setTeamLoss(team: Team){
        for (poolLine in poolLines){
            if(poolLine.team == team){
                poolLine.gamesPlayed++
                poolLine.losses++
            }
        }
    }

    // Update poolLine by draw
    private fun setTeamDraw(team : Team){
        for (poolLine in poolLines){
            if(poolLine.team == team){
                poolLine.gamesPlayed++
                poolLine.draw++
                poolLine.points += Constants.DRAW_POINTS
            }
        }
    }

    // Create teams from constant and set a random strength
    private  fun createTeams(){
        for(name in Constants.TEAMS_NAMES){
            val randomStrength = Random.nextDouble(Constants.MIN_STRENGTH, Constants.MAX_STRENGTH)
            val team = Team(name = name, strength = randomStrength)
            teams.add(team)
            poolLines.add(PoolLine(team = team))
        }
    }

    // Update currentTeamIndex and currentOpponentIndex to determine the next game
    fun setNextGame(){
        currentOpponentIndex++

        if(currentOpponentIndex == teams.size){
            currentTeamIndex++
            currentOpponentIndex = currentTeamIndex +1
        }
    }

    // This function sets the pool lines in the correct order by comparing their games played and checking if one team performs better than the other.
    fun setPoolLinesInTheRightOrder(){
        val organizedList = mutableListOf<PoolLine>()

        for (pooLine in poolLines){

            // if the team has not yet played a game, place the team at the end of the list
            // or if the list is empty then add an item
            if(pooLine.gamesPlayed == 0 || organizedList.isEmpty()){
                organizedList.add(pooLine)
            }else{
                var added = false
                loop@ for(i in 0 until organizedList.size){
                    val poolLine2 = organizedList[i]
                    if(poolLine2.gamesPlayed != 0){
                        // Check if the team (poolLine) better perform than the team (poolLine2)
                        if(PoolRules.checkIfTeamBetterPerformThanTheOther(pooLine, poolLine2, this)){
                            // if team (poolLine) better perform dan the team (poolLine2)
                            // then place the team (poolLine) in front of the team (poolLine2)
                            organizedList.add(i, pooLine)
                            added = true
                            break@loop
                        }
                    }else {
                        // if the current team (poolLine2) has not yet spelled a game,
                        // then place the new  team (poolLine) in front of the current team (poolLine2)
                        organizedList.add(i, pooLine)
                        added = true
                        break@loop
                    }
                }

                // check if the team (poolLine) has already been added to the list
                // if not, then place the team (poolLine) at the end of the list
                if(!added)  organizedList.add(pooLine)
            }
        }

        // Clear old pool and add the new organized pool
        poolLines.clear()
        poolLines.addAll(organizedList)
    }
}