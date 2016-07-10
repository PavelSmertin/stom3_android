package com.stom3.android.postponeMessage;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.stom3.android.BaseActivity;
import com.stom3.android.R;

import java.lang.reflect.Field;

public class DiscussDialogFragment extends DialogFragment {
    private String mMessage;
    private BaseActivity.OnPostponeListener acceptListener;

    public static DiscussDialogFragment newInstance(String message) {
        DiscussDialogFragment f = new DiscussDialogFragment();
        Bundle args = new Bundle();
        args.putString("message", message);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMessage = getArguments().getString("message");
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppAlertDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_discuss_dialog, container, false);

        TextView discussTitle = (TextView) v.findViewById(R.id.discuss_title_label);
        TextView discussMessage = (TextView) v.findViewById(R.id.discuss_message);

        discussTitle.setText(getString(R.string.permission_explain_title));
        discussMessage.setText(mMessage);

        Button buttonPositive = (Button) v.findViewById(R.id.button_positive);
        Button buttonNegative = (Button) v.findViewById(R.id.button_negative);

        View.OnClickListener onPositiveClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //StatisticAdapter.sendButtonEvent(new ButtonParamsPull("b_PermissionExplain_Positive"));
                if (acceptListener != null) {
                    acceptListener.onAccept();
                }
                terminate();
            }
        };

        View.OnClickListener onNegativeClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //StatisticAdapter.sendButtonEvent(new ButtonParamsPull("b_PermissionExplain_Negative"));
                if (acceptListener != null) {
                    acceptListener.onCancel();
                }
                terminate();
            }
        };

        buttonPositive.setOnClickListener(onPositiveClickListener);
        buttonNegative.setOnClickListener(onNegativeClickListener);

        return v;
    }

    public void setAcceptListener(BaseActivity.OnPostponeListener acceptListener) {
        this.acceptListener = acceptListener;
    }

    private void terminate() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(DiscussDialogFragment.this);
        ft.commitAllowingStateLoss();
        DiscussDialogFragment.this.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // fix Activity has been destroyed http://stackoverflow.com/questions/25185950/java-lang-illegalstateexception-activity-has-been-destroyed
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}