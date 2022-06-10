package com.ayush.directchat.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.ayush.directchat.DeveloperActivity;
import com.ayush.directchat.R;
import com.ayush.directchat.Utility.StorageUtil;
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;
import com.google.android.material.snackbar.Snackbar;

import eu.dkaratzas.android.inapp.update.Constants;
import eu.dkaratzas.android.inapp.update.InAppUpdateManager;
import eu.dkaratzas.android.inapp.update.InAppUpdateStatus;

public class MenuFragment extends Fragment implements View.OnClickListener, InAppUpdateManager.InAppUpdateHandler {

    private TextView textViewVersion;
    private static final int REQ_CODE_VERSION_UPDATE = 530;
    private InAppUpdateManager inAppUpdateManager;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch darkModeSwitch;
    private StorageUtil storage;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        TextView textViewDeveloper = view.findViewById(R.id.textviewDeveloper);
        TextView textViewShareApp = view.findViewById(R.id.textviewShareApp);
        TextView textViewRateThisApp = view.findViewById(R.id.textviewRateApp);
        TextView textView_policy = view.findViewById(R.id.textview_privacy_policy);
        TextView textView_open_source = view.findViewById(R.id.textview_open_source);
        darkModeSwitch = view.findViewById(R.id.darkModeSwitch);
        textViewVersion = view.findViewById(R.id.textview_version);
        TextView textViewTermsConditions = view.findViewById(R.id.textview_terms_conditions);

        textViewDeveloper.setOnClickListener(this);
        textViewShareApp.setOnClickListener(this);
        textViewRateThisApp.setOnClickListener(this);
        textView_policy.setOnClickListener(this);
        textView_open_source.setOnClickListener(this);
        textViewVersion.setOnClickListener(this);
        textViewTermsConditions.setOnClickListener(this);

        storage = new StorageUtil(getContext());
        darkModeSwitch.setChecked(storage.getMode());
        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> changeMode());

        return view;
    }

    private void changeMode() {
        if (storage.getMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            storage.setMode(false);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            storage.setMode(true);
        }

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.textviewDeveloper:
                startActivity(new Intent(getContext(), DeveloperActivity.class));
                break;
            case R.id.textviewShareApp:
                openShareApp();
                break;
            case R.id.textviewRateApp:
                appRate();
                break;
            case R.id.textview_version:
                inAppUpdateManager = InAppUpdateManager.Builder((AppCompatActivity) getActivity(), REQ_CODE_VERSION_UPDATE)
                        .resumeUpdates(true) // Resume the update, if the update was stalled. Default is true
                        .mode(Constants.UpdateMode.FLEXIBLE)
                        .useCustomNotification(true)
                        .handler(this);

                inAppUpdateManager.checkForAppUpdate();
                Toast.makeText(requireContext(), textViewVersion.getText().toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.textview_privacy_policy:
                openPolicy();
                break;
            case R.id.textview_terms_conditions:
                openTermsConditions();
                break;
            case R.id.textview_open_source:
                OssLicensesMenuActivity.setActivityTitle(getString(R.string.open_source_licences));
                startActivity(new Intent(requireContext(), OssLicensesMenuActivity.class));
                break;
            default:
        }
    }

    private void openTermsConditions() {
        Intent viewIntent =
                new Intent("android.intent.action.VIEW",
                        Uri.parse(getString(R.string.terms_conditions)));
        startActivity(viewIntent);
    }

    private void openShareApp() {

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_app_link));
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_using)));
    }

    private void appRate() {
        Intent viewIntent =
                new Intent("android.intent.action.VIEW",
                        Uri.parse(getString(R.string.rate_app_link)));
        startActivity(viewIntent);
    }

    private void openPolicy() {
        Intent viewIntent =
                new Intent("android.intent.action.VIEW",
                        Uri.parse(getString(R.string.policy)));
        startActivity(viewIntent);
    }


    @Override
    public void onInAppUpdateError(int code, Throwable error) {

    }

    @Override
    public void onInAppUpdateStatus(InAppUpdateStatus status) {

        if (status.isDownloaded()) {

            View rootView = (requireActivity()).getWindow().getDecorView().findViewById(android.R.id.content);

            Snackbar snackbar = Snackbar.make(rootView,
                    "An update has just been downloaded.",
                    Snackbar.LENGTH_INDEFINITE);

            snackbar.setAction("RESTART", view -> {

                // Triggers the completion of the update of the app for the flexible flow.
                inAppUpdateManager.completeUpdate();

            });

            snackbar.show();

        }

    }
}