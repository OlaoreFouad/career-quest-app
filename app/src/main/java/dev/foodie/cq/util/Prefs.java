package dev.foodie.cq.util;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    private Context ctx;
    private SharedPreferences sharedPreferences;

    public Prefs(Context context) {
        this.ctx = context;
        sharedPreferences = this.ctx.getSharedPreferences(Util.PREFS_KEY, Context.MODE_PRIVATE);
    }

    public void setAuthState(boolean authState) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("authenticated", authState);
        editor.apply();
    }

    public boolean getAuthState() {
        return sharedPreferences.getBoolean("authenticated", false);
    }

}
