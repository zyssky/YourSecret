package com.example.administrator.yoursecret.Home;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.administrator.yoursecret.Module.Account.AccountFragment;
import com.example.administrator.yoursecret.AppManager.App;
import com.example.administrator.yoursecret.AppManager.FoundationManager;
import com.example.administrator.yoursecret.Module.Discover.DiscoverFragment;
import com.example.administrator.yoursecret.Module.Editor.EditorActivity;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.Module.Recieve.RecieveFragment;
import com.example.administrator.yoursecret.Module.Record.RecordFragment;
import com.example.administrator.yoursecret.Service.PushService;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.FunctionUtils;

public class HomeActivity extends AppCompatActivity{

    private BottomNavigationView navigationView;

    private Fragment currentFragment;

    private String curFragmentPos = RecieveFragment.class.getSimpleName();

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
    public void onBackPressed() {

        if(curFragmentPos.equals(RecieveFragment.class.getSimpleName())){
            super.onBackPressed();
        }
        else{
            switchContent(fragments.getFragment(RecieveFragment.class.getSimpleName()));
            navigationView.setSelectedItemId(R.id.nav_recieve);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        App.getInstance().setAppContext(getApplicationContext());

        if(FoundationManager.isAutoPush()){
            Intent intent = new Intent(this, PushService.class);
            startService(intent);
        }

        fragments = FragmentsHouse.getInstance();

        if(savedInstanceState!=null) {
            String cur = savedInstanceState.getString(AppContants.KEY, RecieveFragment.class.getSimpleName());
            curFragmentPos = cur;
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
                        curFragmentPos = RecieveFragment.class.getSimpleName();
                        break;
                    case R.id.nav_discover:
                        curFragmentPos = DiscoverFragment.class.getSimpleName();
                        break;
                    case R.id.nav_record:
                        curFragmentPos = RecordFragment.class.getSimpleName();
                        break;
                    case R.id.nav_account:
                        curFragmentPos = AccountFragment.class.getSimpleName();
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
        outState.putString(AppContants.KEY,curFragmentPos);
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
//        App.onDestroy();
    }
}

