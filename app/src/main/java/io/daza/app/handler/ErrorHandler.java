package io.daza.app.handler;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.daza.app.R;
import io.daza.app.model.Error;
import io.daza.app.model.Result;

public class ErrorHandler {

    private Context mContext;

    public ErrorHandler(Context content) {
        this.mContext = content;
    }

    public boolean handleErrorIfNeed(Result result) {
        if (result != null && !result.isSuccessful()) {
            String message = "发生异常：";

            if (result.getErrors() != null && result.getErrors().size() > 0) {
                for (int i = 0; i < result.getErrors().size(); i++) {
                    Error error = (Error) result.getErrors().get(i);
                    message += "\n" + error.getMessage();
                }
            } else {
                message += "\n" + result.getMessage();
            }

            if (!TextUtils.isEmpty(message)) {
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

    public void handleError(Throwable t) {
        String message = "";
        if (t != null) {
            t.printStackTrace();
            //加入404与500
            if (t instanceof UnknownHostException) {
                //无法解析---网络错误
                message = mContext.getString(R.string.network_not_connected);
            } else if (t instanceof SocketTimeoutException) {
                message = mContext.getString(R.string.http_exception_error);
            } else {
                message = t.getLocalizedMessage();
            }
        }

        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }
    }
}
