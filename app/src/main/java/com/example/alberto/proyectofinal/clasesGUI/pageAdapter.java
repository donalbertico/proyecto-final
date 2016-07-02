package com.example.alberto.proyectofinal.clasesGUI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.alberto.proyectofinal.fragmentos.needsFragment;
import com.example.alberto.proyectofinal.fragmentos.newNeedFragment;
import com.example.alberto.proyectofinal.fragmentos.spinnerFrag;
import com.example.alberto.proyectofinal.fragmentos.userFragment;

/**
 * Created by Alberto on 6/17/2016.
 */
public class pageAdapter  extends FragmentPagerAdapter {

    int tabCount;
    public String user;
    private Bundle bundle = new Bundle();
    public pageAdapter(FragmentManager fm, int numberOfTabs, String us) {
        super(fm);
        this.tabCount = numberOfTabs;
        Log.d("CHILSON", us);
        bundle.putString("user",us);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {


            case 0:

                userFragment tab2 = new userFragment();
                tab2.setArguments(bundle);
                return tab2;

            case 1:

                needsFragment tab1 = new needsFragment();
                tab1.setArguments(bundle);
                return tab1;


            case 2:

                newNeedFragment tab4 = new newNeedFragment();
                tab4.setArguments(bundle);
                return tab4;

            case 3:

                spinnerFrag tab5 = new spinnerFrag();
                tab5.setArguments(bundle);
                return tab5;

            default:

                return null;

        }

    }

    @Override
    public int getCount() {         return tabCount;     }
}
