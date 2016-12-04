/**
 * Copyright (C) 2015 JianyingLi <lijy91@foxmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.daza.app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import io.daza.app.ui.HomeActivity;

public class AppStart extends AppCompatActivity implements Animation.AnimationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_start);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void setContentView(int layoutResID) {
        setContentView(layoutResID, 0.9f, 1f, 2000);
    }

    public void setContentView(int layoutResID, float fromAlpha, float toAlpha, long durationMillis) {
        final View view = View.inflate(this, layoutResID, null);
        setContentView(view);

        AlphaAnimation anim = new AlphaAnimation(fromAlpha, toAlpha);
        anim.setDuration(durationMillis);
        anim.setAnimationListener(this);
        view.startAnimation(anim);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Intent intent = new Intent(this, HomeActivity.class);
        this.startActivity(intent);
        this.overridePendingTransition(0, 0);
        this.finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
