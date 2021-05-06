package com.paragon.sensonic.ui.activities.splash

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.paragon.sensonic.R
import com.paragon.sensonic.ui.activities.login.LoginActivity
import com.paragon.sensonic.ui.activities.profile.ProfileActivity
import com.paragon.utils.ActivityNavigator
import com.paragon.utils.local.AppPreference
import com.paragon.utils.local.PreferenceKeys

class SplashActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startThread(2000)
    }

    private fun startThread(timeInMilli: Long) {
        Handler().postDelayed({
            if (!TextUtils.isEmpty(AppPreference.getInstance(this)
                    .getValue(PreferenceKeys.CREDENTIALS))) {
                launchHomeScreen()
                finish()
            } else {
                var activityNavigator = ActivityNavigator(this)
                activityNavigator.startAct(LoginActivity::class.java)
                finish()
            }
        }, timeInMilli)
    }

    private fun launchHomeScreen() {
        var activityNavigator = ActivityNavigator(this)
        activityNavigator.startAct(ProfileActivity::class.java)
    }
}