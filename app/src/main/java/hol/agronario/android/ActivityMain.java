package hol.agronario.android;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import hol.agronario.android.adapters.ActivityMainAdapter;
import hol.agronario.android.data.IdiomaDataSource;
import hol.agronario.android.fragments.ListPalavrasFragment;
import hol.agronario.android.listeners.RecyclerItemClickListener;
import hol.agronario.android.ob.Idioma;
import hol.agronario.android.util.Util;

/**
 * Created by MarcosPaulo on 5/5/15.
 */
public class ActivityMain extends ActionBarActivity {

    boolean doubleBackToExitPressedOnce = false;

    //Similarly we Create a String Resource for the name and email in the header view
    //And we also create a int resource for profile picture in the header view

    List<Idioma> mIdiomas;
    String NAME = "";
    String EMAIL = "";
    int PROFILE = R.drawable.ic_profile;

    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    ActivityMainAdapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                                  // Declaring DrawerLayout

    ActionBarDrawerToggle mDrawerToggle;                  // Declaring Action Bar Drawer Toggle

    Toolbar mToolbar;
    Spinner mSpinnerNav;

    RecyclerItemClickListener.OnItemClickListener onItemClickListener;

    private FragmentManager.OnBackStackChangedListener
            mOnBackStackChangedListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            syncActionBarArrowState();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSpinnerNav = (Spinner) findViewById(R.id.spinner_nav);
        mSpinnerNav.setVisibility(View.GONE);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View
        mRecyclerView.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size

        mIdiomas = populateIdiomas();
        mAdapter = new ActivityMainAdapter(this, mIdiomas, NAME, EMAIL);       // Creating the Adapter of MyAdapter class(which we are going to see in a bit)

        // And passing the titles,icons,header view name, header view email,
        // and header view profile picture

        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView

        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager

        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager


        onItemClickListener = new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // position 0 is the header, so it doesnt count
                if (position > 0 && mAdapter.getItemCount() > 1) {
                    Drawer.closeDrawers();
                    mAdapter.setPositionChecked(position);

                    Bundle data = new Bundle();
                    data.putInt(Util.ARGUMENT_ID_IDIOMA, mIdiomas.get(position - 1).getId());
                    changeFragment(new ListPalavrasFragment(), data);
                }
            }
        };
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, onItemClickListener));

        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this, Drawer, R.string.open_drawer, R.string.close_drawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                mDrawerToggle.setDrawerIndicatorEnabled(true);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                syncActionBarArrowState();
            }
        };

        if (savedInstanceState == null) {
            //first position
            onItemClickListener.onItemClick(null, 1);
        }

        // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State

        getSupportFragmentManager().addOnBackStackChangedListener(mOnBackStackChangedListener);
    }


    private List<Idioma> populateIdiomas() {
        try {
            IdiomaDataSource ids = new IdiomaDataSource(this);
            ids.open();
            List<Idioma> list = ids.getAllIdiomas();

            ids.close();
            return list;

        } catch (SQLException e) {
            Log.e(getClass().getName(), e.getMessage());
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//         Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.isDrawerIndicatorEnabled() &&
                mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else if (item.getItemId() == android.R.id.home &&
                getSupportFragmentManager().popBackStackImmediate()) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getString(R.string.press_again_to_exit), Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    public void changeFragment(Fragment fragment, Bundle bundle) {
        doubleBackToExitPressedOnce = false;

        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);

        if (!(fragment instanceof ListPalavrasFragment)) {
            transaction.addToBackStack(null);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            fragment.setAllowEnterTransitionOverlap(true);
//            fragment.setAllowReturnTransitionOverlap(true);

            Fade fadeTransitionIn = new Fade(Fade.MODE_IN);
            fadeTransitionIn.setDuration(400);
            fragment.setEnterTransition(fadeTransitionIn);

//            Fade fadeTransitionOut = new Fade(Fade.MODE_OUT);
//            fadeTransitionOut.setDuration(300);
//            fragment.setReturnTransition(fadeTransitionOut);

        }

        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        getSupportFragmentManager().removeOnBackStackChangedListener(mOnBackStackChangedListener);
        super.onDestroy();
    }


    private void syncActionBarArrowState() {
        int backStackEntryCount =
                getSupportFragmentManager().getBackStackEntryCount();
        mDrawerToggle.setDrawerIndicatorEnabled(backStackEntryCount == 0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public Spinner getSpinnerNav() {
        return mSpinnerNav;
    }

}
