package com.miniclip.poulefase.utilities

import android.app.Activity
import kotlin.system.exitProcess

object GlobalFunctions {

    fun closeApplication(activity: Activity){
        activity.finishAffinity()
        // By a nonzero status code indicates abnormal termination.
        exitProcess(0)
    }
}