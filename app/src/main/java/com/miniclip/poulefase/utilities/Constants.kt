package com.miniclip.poulefase.utilities

import java.text.DecimalFormat

object Constants {
    const val DEFAULT_NUMBER = 0
    const val TEAM_1_NUMBER = 1
    const val TEAM_2_NUMBER = 2
    const val BEGIN_SCORE = 0
    const val MIN_STRENGTH = 3.0
    const val MAX_STRENGTH = 10.0
    const val WIN_POINTS = 3
    const val DRAW_POINTS = 1
    const val MIN_GOAL_ATTEMPT = 0
    const val MAX_GOAL_ATTEMPT = 5

    // Ane decimal after the coma
    val ONE_DECIMAL = DecimalFormat("#.#")

    val TEAMS_NAMES = listOf("Brazil", "Netherlands", "Argentina", "Croatia")
    const val POOL_NAME = "Pool WK 2022"
}