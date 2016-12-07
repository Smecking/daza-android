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

import android.support.v14.preference.PreferenceFragment;
import android.os.Bundle;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.Preference;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.List;

import io.daza.app.R;
import io.daza.app.handler.ErrorHandler;
import io.daza.app.model.Result;
import io.daza.app.model.UserConfig;
import io.daza.app.ui.base.BaseActivity;
import io.daza.app.util.Auth;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static io.daza.app.api.ApiClient.API;

public class NotificationSettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);
    }

    public static class NotificationSettingsFragment extends PreferenceFragment {

        private static final String KEY_NOTIFICATION_FOLLOWED   = "key_notification_followed";
        private static final String KEY_NOTIFICATION_SUBSCRIBED = "key_notification_subscribed";
        private static final String KEY_NOTIFICATION_UPVOTED    = "key_notification_upvoted";
        private static final String KEY_NOTIFICATION_COMMENT    = "key_notification_comment";
        private static final String KEY_NOTIFICATION_MENTION    = "key_notification_mention";

        private List<UserConfig> mUserConfigs;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            addPreferencesFromResource(R.xml.pref_notification_settings);
            setHasOptionsMenu(true);

            if (Auth.check()) {
                mUserConfigs = Auth.userConfigs();
                ((SwitchPreference) findPreference(KEY_NOTIFICATION_FOLLOWED)).setChecked(isCheckedByKey(KEY_NOTIFICATION_FOLLOWED));
                ((SwitchPreference) findPreference(KEY_NOTIFICATION_SUBSCRIBED)).setChecked(isCheckedByKey(KEY_NOTIFICATION_SUBSCRIBED));
                ((SwitchPreference) findPreference(KEY_NOTIFICATION_UPVOTED)).setChecked(isCheckedByKey(KEY_NOTIFICATION_UPVOTED));
                ((SwitchPreference) findPreference(KEY_NOTIFICATION_COMMENT)).setChecked(isCheckedByKey(KEY_NOTIFICATION_COMMENT));
                ((SwitchPreference) findPreference(KEY_NOTIFICATION_MENTION)).setChecked(isCheckedByKey(KEY_NOTIFICATION_MENTION));
            }
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.menu_notification_settings, menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() == R.id.action_save) {
                boolean notificationFollowed = ((SwitchPreference) findPreference(KEY_NOTIFICATION_FOLLOWED)).isChecked();
                boolean notificationSubscribed = ((SwitchPreference) findPreference(KEY_NOTIFICATION_SUBSCRIBED)).isChecked();
                boolean notificationUpvoted = ((SwitchPreference) findPreference(KEY_NOTIFICATION_UPVOTED)).isChecked();
                boolean notificationComment = ((SwitchPreference) findPreference(KEY_NOTIFICATION_COMMENT)).isChecked();
                boolean notificationMention = ((SwitchPreference) findPreference(KEY_NOTIFICATION_MENTION)).isChecked();
                API.updateConfigs(notificationFollowed,
                        notificationSubscribed,
                        notificationUpvoted,
                        notificationComment,
                        notificationMention).enqueue(new Callback<Result<List<UserConfig>>>() {
                    @Override
                    public void onResponse(Call<Result<List<UserConfig>>> call, Response<Result<List<UserConfig>>> response) {
                        if (new ErrorHandler(getActivity()).handleErrorIfNeed(response.errorBody())) {
                            return;
                        }
                        if (response.isSuccessful()) {
                            getActivity().finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Result<List<UserConfig>>> call, Throwable t) {
                        new ErrorHandler(getActivity()).handleError(t);
                    }
                });
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        private boolean isCheckedByKey(String key) {
            key = key.replace("key_", "");
            if (mUserConfigs == null) {
                return false;
            }
            for (UserConfig userConfig : mUserConfigs) {
                if (key.equals(userConfig.getKey())) {
                    return "true".equals(userConfig.getValue());
                }
            }
            return true;
        }
    }
}
