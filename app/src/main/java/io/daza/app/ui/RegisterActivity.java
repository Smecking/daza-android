package io.daza.app.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.blankapp.annotation.ViewById;
import org.blankapp.validation.Rule;
import org.blankapp.validation.Validator;
import org.blankapp.validation.handlers.DefaultErrorHandler;
import org.greenrobot.eventbus.EventBus;

import io.daza.app.R;
import io.daza.app.event.LoginStatusChangedEvent;
import io.daza.app.handler.ErrorHandler;
import io.daza.app.model.Result;
import io.daza.app.model.User;
import io.daza.app.ui.base.BaseActivity;
import io.daza.app.util.Auth;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static io.daza.app.api.ApiClient.API;

public class RegisterActivity extends BaseActivity {

    @ViewById(R.id.edt_username)
    private EditText mEdtUsername;
    @ViewById(R.id.edt_email)
    private EditText mEdtEmail;
    @ViewById(R.id.edt_password)
    private EditText mEdtPassword;
    @ViewById(R.id.btn_submit)
    private Button mBtnSubmit;

    private Validator mValidator = new Validator();
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("注册中...");
        mProgressDialog.setCancelable(false);

        mValidator.add(Rule.with(mEdtUsername).required());
        mValidator.add(Rule.with(mEdtEmail).required().email());
        mValidator.add(Rule.with(mEdtPassword).required().minLength(6).maxLength(32));

        mValidator.setErrorHandler(new DefaultErrorHandler());

        mBtnSubmit.setOnClickListener(mSubmitClickListener);
    }

    private View.OnClickListener mSubmitClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!mValidator.validate()) {
                return;
            }

            String username = mEdtUsername.getText().toString();
            String email = mEdtEmail.getText().toString();
            String password = mEdtPassword.getText().toString();

            Call<Result<User>> call = API.register(username, email, password);

            mProgressDialog.show();
            call.enqueue(new Callback<Result<User>>() {
                @Override
                public void onResponse(Call<Result<User>> call, Response<Result<User>> response) {
                    mProgressDialog.dismiss();
                    if (new ErrorHandler(RegisterActivity.this).handleErrorIfNeed(response.errorBody())) {
                        return;
                    }
                    if (response.isSuccessful()) {
                        Result<User> result = response.body();
                        if (result.isSuccessful()) {
                            User user = result.getData();
                            Auth.jwtToken(user.getJwt_token());
                            Auth.user(user);
                            Auth.userConfigs(user.getConfigs());
                            EventBus.getDefault().post(new LoginStatusChangedEvent());
                            RegisterActivity.this.finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Result<User>> call, Throwable t) {
                    mProgressDialog.dismiss();
                    new ErrorHandler(RegisterActivity.this).handleError(t);
                }
            });
        }
    };
}
