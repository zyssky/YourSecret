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

import com.example.administrator.yoursecret.AppManager.ApplicationDataManager;
import com.example.administrator.yoursecret.Comment.CommentActivity;
import com.example.administrator.yoursecret.Editor.EditorActivity;
import com.example.administrator.yoursecret.Network.NetworkManager;
import com.example.administrator.yoursecret.Network.NetworkMonitor;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.Recieve.RecieveDataManager;
import com.example.administrator.yoursecret.Record.RecordDataManager;
import com.example.administrator.yoursecret.AppManager.UserManager;
import com.example.administrator.yoursecret.utils.FunctionUtils;

public class HomeActivity extends AppCompatActivity implements HomeContract.View{

    private BottomNavigationView navigationView;

    private Fragment currentFragment;

    private HomeContract.Presenter presenter;

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
                startActivity(new Intent(this, CommentActivity.class));
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initManager();

//        ApplicationDataManager.getInstance().getNetworkManager().register();
//        ApplicationDataManager.getInstance().getNetworkManager().login();


        presenter = new HomePresenter(this);

        presenter.switchContent(HomePresenter.RECIEVE_FRAGMENT);

        navigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        FunctionUtils.disableShiftMode(navigationView);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_recieve:
                        presenter.switchContent(HomePresenter.RECIEVE_FRAGMENT);
                        break;
                    case R.id.nav_discover:
                        presenter.switchContent(HomePresenter.DISCOVER_FRAGMENT);
                        break;
                    case R.id.nav_record:
                        presenter.switchContent(HomePresenter.RECORD_FRAGMENT);
                        break;
                    case R.id.nav_account:
                        presenter.switchContent(HomePresenter.ACCOUNT_FRAGMENT);
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });
    }

    private void initManager(){
        ApplicationDataManager.getInstance().setAppContext(getApplicationContext());
        ApplicationDataManager.getInstance().setUserManager(new UserManager());
        ApplicationDataManager.getInstance().setRecordDataManager(new RecordDataManager());
        ApplicationDataManager.getInstance().setRecieveDataManager(new RecieveDataManager());
        ApplicationDataManager.getInstance().setNetworkManager(new NetworkManager());
        ApplicationDataManager.getInstance().setNetworkMonitor(new NetworkMonitor());
    }


    @Override
    public void switchContent(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(currentFragment==null){
            transaction.add(R.id.container, targetFragment).commit();
        }
        else if (currentFragment != targetFragment) {
            if (!targetFragment.isAdded()) {	// 先判断是否被add过
                transaction.hide(currentFragment).add(R.id.container, targetFragment).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(currentFragment).show(targetFragment).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
        currentFragment = targetFragment;
    }

}

