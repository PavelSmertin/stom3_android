package com.stom3.android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.stom3.android.api.RestClient;
import com.stom3.android.auth.AuthActivity;
import com.stom3.android.postponeMessage.DiscussDialogFragment;
import com.stom3.android.postponeMessage.PostponeDiscussMessage;
import com.stom3.android.postponeMessage.PostponeMessage;
import com.stom3.android.storage.PreferencesHelper;

import java.util.ArrayList;
import java.util.List;


abstract public class BaseActivity extends AppCompatActivity implements RestClient.OnLogoutListener {

    private ProgressDialog mDialog;

    private List<PostponeMessage> postponeMessages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RestClient.getInstance().addOnLogoutListener(this);
    }

    public void showLoader(String message){
        if(!isFinishing()) {
            mDialog = new ProgressDialog(this);
            if(message.length() == 0) {
                mDialog.setMessage(getString(R.string.common_please_wait));
            } else {
                mDialog.setMessage(message);
            }
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setCancelable(false);
            mDialog.show();
        }
    }

    public void hideLoader() {
        if(mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }





    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(postponeMessages.size() > 0) {
            if(postponeMessages.get(0) instanceof PostponeDiscussMessage) {
                showDiscuss(postponeMessages.get(0));
            }
            postponeMessages.clear();
        }
    }

    public <T extends PostponeMessage> void showPostponeMessage(T message) {
        postponeMessages.add(message);
    }

    public void showPostponeDiscuss(String message, OnPostponeListener listener) {
        postponeMessages.add(new PostponeDiscussMessage(message, listener));
    }

    private void showDiscuss(PostponeMessage message) {
        DiscussDialogFragment dialog = DiscussDialogFragment.newInstance(message.getText());
        dialog.setAcceptListener(message.getListener());
        dialog.show(getSupportFragmentManager(), "permission_explanation");
    }

    public interface OnPostponeListener {
        void onAccept();
        void onCancel();
    }





    @Override
    public void onLogout() {
        PreferencesHelper.getInstance().signout();
        Intent intent = new Intent(getApplicationContext(), AuthActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }



}