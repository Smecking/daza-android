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

import android.content.Context;
import android.support.multidex.MultiDex;

import com.facebook.stetho.Stetho;
import com.growingio.android.sdk.collection.Configuration;
import com.growingio.android.sdk.collection.GrowingIO;

import org.blankapp.app.Application;

import im.fir.sdk.FIR;
import io.daza.app.util.Auth;
import io.yunba.android.core.YunBaService;
import io.yunba.android.manager.YunBaManager;

public class AppContext extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Auth.initialize(this);
        Stetho.initializeWithDefaults(this);
        // 初始化 YunBa
        YunBaManager.start(getApplicationContext());
        YunBaManager.setAlias(this, Auth.alias(), null);

        // 初始化 GrowingIO
        GrowingIO.startWithConfiguration(this, new Configuration()
                .useID()
                .trackAllFragments()
                .setChannel("GooglePlay"));

        // 初始化 BugHD
        FIR.init(this);
    }

}
