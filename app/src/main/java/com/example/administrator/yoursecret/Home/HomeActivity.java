package com.example.administrator.yoursecret.Home;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.administrator.yoursecret.AppManager.ApplicationDataManager;
import com.example.administrator.yoursecret.Comment.CommentActivity;
import com.example.administrator.yoursecret.Editor.EditorActivity;
import com.example.administrator.yoursecret.Network.NetworkManager;
import com.example.administrator.yoursecret.Network.NetworkMonitor;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.Recieve.RecieveDataManager;
import com.example.administrator.yoursecret.Record.RecordDataManager;
import com.example.administrator.yoursecret.AppManager.UserManager;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.FunctionUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeActivity extends AppCompatActivity{

    private BottomNavigationView navigationView;

    private Fragment currentFragment;

    private int curFragmentPos = FragmentsHouse.RECIEVE_FRAGMENT;

    private FragmentsHouse fragments;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.publish_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.publish:
                Intent intent  = new Intent(this, EditorActivity.class);
                startActivity(intent);
                break;
            case R.id.comments:
                return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initManager();

        fragments = FragmentsHouse.getInstance();

        if(savedInstanceState!=null) {
            int resId = savedInstanceState.getInt(AppContants.KEY, FragmentsHouse.RECIEVE_FRAGMENT);
            curFragmentPos = resId;
        }

        currentFragment = fragments.getFragment(curFragmentPos);

        switchContent(currentFragment);

        navigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        FunctionUtils.disableShiftMode(navigationView);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_recieve:
                        curFragmentPos = FragmentsHouse.RECIEVE_FRAGMENT;
                        break;
                    case R.id.nav_discover:
                        curFragmentPos = FragmentsHouse.DISCOVER_FRAGMENT;
                        break;
                    case R.id.nav_record:
                        curFragmentPos = FragmentsHouse.RECORD_FRAGMENT;
                        break;
                    case R.id.nav_account:
                        curFragmentPos = FragmentsHouse.ACCOUNT_FRAGMENT;
                        break;
                    default:
                        return true;
                }
                switchContent(fragments.getFragment(curFragmentPos));
                return true;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(AppContants.KEY,curFragmentPos);
    }

    private void initManager(){
        ApplicationDataManager.getInstance().setAppContext(getApplicationContext());
        ApplicationDataManager.getInstance().setUserManager(new UserManager());
        ApplicationDataManager.getInstance().setRecordDataManager(new RecordDataManager());
        ApplicationDataManager.getInstance().setRecieveDataManager(new RecieveDataManager());
        ApplicationDataManager.getInstance().setNetworkManager(new NetworkManager());
        ApplicationDataManager.getInstance().setNetworkMonitor(new NetworkMonitor());
    }

    public void switchContent(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(!currentFragment.isAdded()){
            transaction.add(R.id.container, currentFragment);
        }
        if(currentFragment != targetFragment && !targetFragment.isAdded() ){
            transaction.add(R.id.container, targetFragment);
        }

        transaction.hide(currentFragment).show(targetFragment).commit();

        currentFragment = targetFragment;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ApplicationDataManager.onDestroy();
    }
}

