package com.ayush.directchat.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.CallLog;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ayush.directchat.R;
import com.ayush.directchat.adapter.CallsLogsAdapter;
import com.ayush.directchat.model.CallsModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import static android.Manifest.permission.READ_CALL_LOG;

public class LastCallsFragment extends Fragment {

    private final ArrayList<CallsModel> callsModelArrayList = new ArrayList<>();
    private CallsLogsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_last_calls, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new CallsLogsAdapter(getContext(), callsModelArrayList);
        recyclerView.setAdapter(adapter);

        getCallList();

        adapter.setOnItemClickListener(new CallsLogsAdapter.OnClick() {
            @Override
            public void OnClick(int position) {
                String number = callsModelArrayList.get(position).getNumber();
                onClickCopy(number);

            }

            @Override
            public void OnClickFabClick(int position) {
                String number = callsModelArrayList.get(position).getNumber();
                sendData(number);
            }
        });
        return view;
    }

    private void getCallList() {
        callsModelArrayList.clear();

        if (ActivityCompat.checkSelfPermission(requireContext(), READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Cursor cursor = getContext().getContentResolver().query(CallLog.Calls.CONTENT_URI,
                null, null, null, CallLog.Calls.DATE + " DESC");
        assert cursor != null;
        int name = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);

        while (cursor.moveToNext()) {
            String callName = cursor.getString(name);
            String phNumber = cursor.getString(number);
            String callType = cursor.getString(type);
            String callDate = cursor.getString(date);

            String dateString = DateFormat.format("h:mm a   |   dd MMMM yyyy", new Date(Long.parseLong(callDate))).toString();

            String dir = null;
            switch (Integer.parseInt(callType)) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    break;
            }

            CallsModel callsModel = new CallsModel(phNumber,callName,dateString,dir);
            callsModelArrayList.add(callsModel);
        }
        cursor.close();

        adapter.notifyDataSetChanged();

    }

    private void sendData(String number){

        startActivity(
                new Intent(Intent.ACTION_VIEW,
                        Uri.parse(
                                String.format("https://api.whatsapp.com/send?phone=%s&text=%s", number, "")
                        )
                )
        );
    }

    private void onClickCopy(String string) {

        ClipboardManager clipboard = (ClipboardManager) Objects.requireNonNull(requireActivity()).getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("simple text", string);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
        }
        Toast.makeText(getContext(), "copied" + " " + string, Toast.LENGTH_SHORT).show();


    }



}