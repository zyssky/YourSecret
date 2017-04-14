package com.example.administrator.yoursecret.Home;

import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.administrator.yoursecret.R;

import java.lang.reflect.Field;

public class HomeActivity extends AppCompatActivity implements HomeContract.View{

    private BottomNavigationView navigationView;

    private Fragment currentFragment;

    private HomeContract.Presenter presenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        presenter = new HomePresenter(this);

        presenter.switchContent(HomePresenter.RECIEVE_FRAGMENT);

        navigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        BottomNavigationViewHelper.disableShiftMode(navigationView);
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

// 利用反射机制，改变 item 的 mShiftingMode 变量
class BottomNavigationViewHelper {
    public static void disableShiftMode(BottomNavigationView navigationView) {
        BottomNavigationMenuView menuView =
                (BottomNavigationMenuView) navigationView.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);

            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
                itemView.setShiftingMode(false);
                itemView.setChecked(itemView.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            // Log
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // Log
            e.printStackTrace();
        }
    }
}