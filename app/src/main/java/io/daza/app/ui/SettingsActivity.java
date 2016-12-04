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

package io.daza.app.ui;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v14.preference.PreferenceFragment;
import android.support.v7.preference.Preference;

import io.daza.app.BuildConfig;
import io.daza.app.R;
import io.daza.app.ui.base.BaseActivity;
import io.daza.app.util.Auth;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public static class SettingsFragment extends PreferenceFragment {

        private static final String KEY_NOTIFICATION_SETTINGS = "key_notification_settings";
        private static final String KEY_ACCOUNT = "key_account";
        private static final String KEY_FEEDBACK = "key_feedback";
        private static final String KEY_ABOUT = "key_about";
        private static final String KEY_LOGOUT = "key_logout";

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            addPreferencesFromResource(R.xml.pref_settings);
            try {
                PackageInfo packageInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
                findPreference(KEY_ABOUT).setSummary(packageInfo.versionName);
            } catch (PackageManager.NameNotFoundException e) {
            }

            if (!Auth.check()) {
                findPreference(KEY_NOTIFICATION_SETTINGS).setEnabled(false);
                findPreference(KEY_ACCOUNT).setEnabled(false);
                findPreference(KEY_LOGOUT).setVisible(false);
            }
        }

        @Override
        public boolean onPreferenceTreeClick(Preference preference) {
            Intent intent = null;
            switch (preference.getKey()) {
                case KEY_NOTIFICATION_SETTINGS:
                    intent = new Intent(getActivity(), NotificationSettingsActivity.class);
                    break;
                case KEY_ACCOUNT:
                    intent = new Intent(getActivity(), AccountActivity.class);
                    break;
                case KEY_FEEDBACK:
                    intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/html");
                    intent.putExtra(Intent.EXTRA_EMAIL, BuildConfig.EMAIL_HI);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "意见反馈");
                    break;
                case KEY_ABOUT:
                    intent = new Intent(getActivity(), AboutActivity.class);
                    break;
                case KEY_LOGOUT:
                    break;
                default:
                    return false;
            }
            startActivity(intent);
            return true;
        }
    }

}
