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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import org.blankapp.validation.Rule;
import org.blankapp.validation.Validator;

import io.daza.app.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText mEdtUsername;
    private EditText mEdtEmail;
    private EditText mEdtPassword;
    private Button mBtnSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Validator validator = new Validator();

        validator.add(Rule.with(mEdtUsername).required().alphaDash().minLength(6).maxLength(32));
        validator.add(Rule.with(mEdtEmail).required().email());
        validator.add(Rule.with(mEdtPassword).required().minLength(6).maxLength(32));
    }

}
