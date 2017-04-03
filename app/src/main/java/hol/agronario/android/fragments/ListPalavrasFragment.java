package hol.agronario.android.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.sql.SQLException;
import java.util.List;

import hol.agronario.android.ActivityMain;
import hol.agronario.android.R;
import hol.agronario.android.adapters.CustomSpinnerAdapter;
import hol.agronario.android.adapters.PalavrasAdapter;
import hol.agronario.android.components.CustomFragment;
import hol.agronario.android.data.ClasseGramaticalDataSource;
import hol.agronario.android.data.PalavraDataSource;
import hol.agronario.android.ob.ClasseGramatical;
import hol.agronario.android.ob.Palavra;
import hol.agronario.android.util.Util;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by shouts on 8/17/14.
 */
public class ListPalavrasFragment extends CustomFragment {

    private StickyListHeadersListView stickyList;
    private PalavrasAdapter adapter;
    private boolean fadeHeader = true;

    private List<Palavra> mPalavrasList;
    private int mIdClasseGramatical = -1;
    private int mIdIdioma = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View root = inflater.inflate(R.layout.palavras, container, false);

        stickyList = (StickyListHeadersListView) root.findViewById(R.id.list);
        stickyList.setEmptyView(getActivity().findViewById(R.id.empty));
        stickyList.setDrawingListUnderStickyHeader(true);
        stickyList.setAreHeadersSticky(true);

        mIdIdioma = getData();

        adapter = new PalavrasAdapter(getActivity());
        stickyList.setAdapter(adapter);

        populateCgSpinner();

        return root;
    }

    private int getData() {
        Bundle data = this.getArguments();
        if (data != null) {
            return data.getInt(Util.ARGUMENT_ID_IDIOMA, -1);
        }
        return -1;
    }

    private List<Palavra> searchPalavras(String searchedWord) {
        List<Palavra> list;
        try {
            PalavraDataSource palavraDs = new PalavraDataSource(getActivity());
            palavraDs.open();

            searchedWord = searchedWord == null || searchedWord.isEmpty() ? null : searchedWord;
            list = palavraDs.getPalavras(searchedWord, mIdClasseGramatical, mIdIdioma);

            palavraDs.close();
            return list;

        } catch (SQLException e) {
            Log.e(getClass().getName(), e.getMessage());
        }
        return null;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        android.support.v7.app.ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setQueryHint(getActivity().getString(R.string.search));
        searchView.setSubmitButtonEnabled(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.setData(searchPalavras(s));
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private List<ClasseGramatical> searchClassesGramaticais() {
        List<ClasseGramatical> list;
        try {
            ClasseGramaticalDataSource cgds = new ClasseGramaticalDataSource(getActivity());

            cgds.open();
            list = cgds.getClasseGramaticalByIdioma(mIdIdioma);
            cgds.close();

            return list;

        } catch (SQLException e) {
            Log.e(getClass().getName(), e.getMessage());
        }
        return null;
    }

    private void populatePalavras() {
        mPalavrasList = searchPalavras(null);
        adapter.setData(mPalavrasList);
        adapter.notifyDataSetChanged();
    }

    public void populateCgSpinner() {
        List<ClasseGramatical> listCg = searchClassesGramaticais();

        if (listCg != null && listCg.size() > 0) {
            listCg.add(0, new ClasseGramatical(-1, "TODOS"));
            final CustomSpinnerAdapter spinAdapter = new CustomSpinnerAdapter(getActivity(), listCg);
            ((ActivityMain) getActivity()).getSpinnerNav().setVisibility(View.VISIBLE);
            ((ActivityMain) getActivity()).getSpinnerNav().setAdapter(spinAdapter);

            mIdClasseGramatical = listCg.get(0).getId();
        } else {
            ((ActivityMain) getActivity()).getSpinnerNav().setAdapter(null);
            ((ActivityMain) getActivity()).getSpinnerNav().setVisibility(View.GONE);
        }

        ((ActivityMain) getActivity()).getSpinnerNav().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                ClasseGramatical itemCg = (ClasseGramatical) adapterView.getItemAtPosition(position);
                mIdClasseGramatical = itemCg.getId();

                populatePalavras();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}
