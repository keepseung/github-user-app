package drama.github.user

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import drama.github.user.di.DaggerAppComponent

class MyGithubUserApplication : DaggerApplication(){

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
            .builder()
            .application(this)
            .build()
    }
}