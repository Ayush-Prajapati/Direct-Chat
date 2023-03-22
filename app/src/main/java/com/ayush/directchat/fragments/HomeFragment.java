package com.ayush.directchat.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.ayush.directchat.R;
import com.ayush.directchat.Utility.StorageUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hbb20.CountryCodePicker;

import java.util.Objects;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class HomeFragment extends Fragment {

    private CountryCodePicker ccp;
    private EditText editTextMsg;
    private Button btnSend;

    private Boolean isValid;
    private View view;
    private StorageUtil storage;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        castControl();
        ccp.setPhoneNumberValidityChangeListener(isValidNumber -> {
           isValid = isValidNumber;
           if (isValidNumber){
               if (storage.getIsDemo()){
                   storage.setIsDemo(false);
                   new MaterialTapTargetPrompt.Builder(getActivity())
                           .setTarget(R.id.sendButton)
                           .setPrimaryText("Click here to open the entered mobile number in WhatsApp.")
                           .show();
               }
           }
        });

        btnSend.setOnClickListener(v -> {
            if (isValid){
                sendData();
            }else {
                Toast.makeText(getActivity(), "Enter valid phone number", Toast.LENGTH_SHORT).show();
            }

        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        storage = new StorageUtil(getContext());
        if (storage.getIsDemo()){
            new MaterialTapTargetPrompt.Builder(getActivity())
                    .setTarget(R.id.editText_carrierNumber)
                    .setPrimaryText("How to use.")
                    .setSecondaryText("Enter the mobile number of the person you want to chat with.")
                    .show();
        }
    }

    private void sendData(){

        String msg = editTextMsg.getText().toString();
        if (msg.isEmpty()){
            msg = "";
        }

        startActivity(
                new Intent(Intent.ACTION_VIEW,
                        Uri.parse(
                                String.format("https://api.whatsapp.com/send?phone=%s&text=%s", ccp.getFullNumberWithPlus(), msg)
                        )
                )
        );
    }



    private void castControl() {


        ccp = view.findViewById(R.id.ccp);
        EditText editTextCarrierNumber = view.findViewById(R.id.editText_carrierNumber);
        editTextMsg = view.findViewById(R.id.editTextMsg);
        btnSend = view.findViewById(R.id.sendButton);

        editTextCarrierNumber.requestFocus();

        ccp.setContentColor(getResources().getColor(R.color.colorAccent));
        ccp.setDialogBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        ccp.setArrowColor(getResources().getColor(R.color.colorAccent));
        ccp.setDialogTextColor(getResources().getColor(R.color.colorAccent));
        ccp.registerCarrierNumberEditText(editTextCarrierNumber);
    }

}