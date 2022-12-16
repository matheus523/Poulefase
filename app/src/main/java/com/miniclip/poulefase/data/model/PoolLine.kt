package com.miniclip.poulefase.data.model

import com.miniclip.poulefase.utilities.Constants

data class PoolLine(val team: Team, var points: Int = Constants.DEFAULT_NUMBER,
                    var gamesPlayed : Int = Constants.DEFAULT_NUMBER,
                    var wins: Int = Constants.DEFAULT_NUMBER,
                    var losses : Int = Constants.DEFAULT_NUMBER,
                    var draw: Int = Constants.DEFAULT_NUMBER,
                    var goalsFor: Int = Constants.DEFAULT_NUMBER,
                    var goalsAgainst: Int = Constants.DEFAULT_NUMBER)
