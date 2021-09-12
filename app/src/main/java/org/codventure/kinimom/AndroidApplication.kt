package org.codventure.kinimom

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import org.codventure.kinimom.framework.di.ApplicationComponent
import org.codventure.kinimom.framework.di.ApplicationModule
import org.codventure.kinimom.framework.di.DaggerApplicationComponent

class AndroidApplication : Application() {
    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        this.injectMembers()

        FacebookSdk.sdkInitialize(this)
        AppEventsLogger.activateApp(this)
    }

    private fun injectMembers() = appComponent.inject(this)
}
