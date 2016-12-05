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

package io.daza.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import io.daza.app.model.AccessToken;
import io.daza.app.model.Model;
import io.daza.app.model.User;
import io.daza.app.model.UserConfig;

public class Auth {

    private static final String AUTH_JWT_TOKEN = "auth.jwt_token";
    private static final String AUTH_USER = "auth.user";
    private static final String AUTH_USER_CONFIGS = "auth.user_configs";

    private static boolean sIsInitialized;
    private static Context sContext;
    private static SharedPreferences sSharedPreferences;
    private static Gson sGson;

    private static AccessToken sJwtToken;
    private static User sUser;
    private static List<UserConfig> sUserConfigs;

    public static synchronized void initialize(Context context) {
        if (sIsInitialized) {
            return;
        }
        sContext = context;
        sIsInitialized = true;
        sGson = new Gson();
    }

    private static SharedPreferences getSharedPreferences() {
        if (sSharedPreferences == null) {
            sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(sContext);
        }
        return sSharedPreferences;
    }

    public static boolean check() {
        return user() != null;
    }

    public static AccessToken jwtToken() {
        if (sJwtToken == null) {
            String jsonString = getSharedPreferences().getString(AUTH_JWT_TOKEN, "");
            if (!TextUtils.isEmpty(jsonString)) {
                sJwtToken = Model.parseObject(jsonString, AccessToken.class);
            }
        }
        return sJwtToken;
    }

    public static void jwtToken(AccessToken data) {
        String jsonString = "";
        if (data != null) {
            jsonString = data.toJSONString();
        }
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(AUTH_JWT_TOKEN, jsonString);
        editor.apply();

        sJwtToken = data;
    }

    public static int id() {
        if (check()) {
            return user().getId();
        }
        return 0;
    }

    public static User user() {
        if (sUser == null) {
            String jsonString = getSharedPreferences().getString(AUTH_USER, "");
            if (!TextUtils.isEmpty(jsonString)) {
                sUser = Model.parseObject(jsonString, User.class);
            }
        }
        return sUser;
    }

    public static void user(User data) {
        String jsonString = "";
        if (data != null) {
            jsonString = data.toJSONString();
        }
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(AUTH_USER, jsonString);
        editor.apply();

        sUser = data;
    }

    public static List<UserConfig> userConfigs() {
        if (sUserConfigs == null && check()) {
            String jsonString = getSharedPreferences().getString(AUTH_USER_CONFIGS, "");
            sUserConfigs = sGson.fromJson(jsonString, new TypeToken<List<UserConfig>>() {}.getType());
        }
        return sUserConfigs;
    }

    public static void userConfigs(List<UserConfig> data) {
        String jsonString = "";
        if (data != null) {
            jsonString = sGson.toJson(data);
        }
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(AUTH_USER_CONFIGS, jsonString);
        editor.apply();

        sUserConfigs = data;
    }

    public static String alias() {
        if (check()) {
            try {
                MessageDigest md = null;
                md = MessageDigest.getInstance("MD5");
                md.update(String.valueOf(Auth.id()).getBytes());
                byte[] digest = md.digest();
                StringBuffer sb = new StringBuffer();
                for (byte b : digest) {
                    sb.append(String.format("%02x", (0xFF & b)));
                }
                return sb.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
