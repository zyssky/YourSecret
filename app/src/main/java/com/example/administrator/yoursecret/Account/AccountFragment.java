package com.example.administrator.yoursecret.Account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.yoursecret.R;


public class AccountFragment extends Fragment implements View.OnClickListener {

    private LinearLayout basic_set,account_st;
    public AccountFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView= inflater.inflate(R.layout.fragment_account, container, false);
        basic_set=(LinearLayout)rootView.findViewById(R.id.basics_set);
        account_st=(LinearLayout)rootView.findViewById(R.id.set_wode);
        basic_set.setOnClickListener(this);
        account_st.setOnClickListener(this);
        return rootView;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.basics_set:
                Intent intent1=new Intent();
                intent1.setClass(getActivity(),Basic_set_Activity.class);
                startActivity(intent1 );
                break;
            case R.id.set_wode:

                Intent intent2=new Intent();
                intent2.setClass(getActivity(),Account_set_Activity.class);
                startActivity(intent2 );
                break;
        }
    }
}
