package com.proxsky.instagramclone;
import com.parse.Parse;
import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("9S2PBeHfTgFTC1Kn4433cqx0709e8Lsn3UubztQq")
                // if defined
                .clientKey("5hTFcHWPoUQRPgMblCx33WrZvNpipGN9DGjjf1v9")
                .server("https://parseapi.back4app.com/")
                .build()
        );

    }
}
