package edu.pg.DiA.ui.literature;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import edu.pg.DiA.R;
import edu.pg.DiA.interfaces.DrawerLocker;

public class LiteratureFragment extends Fragment {

    private LiteratureViewModel literatureViewModel;
    public WebView webView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_literature, container, false);
        initView(root);
        updateData();
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.literature_menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        if (item.getItemId() == R.id.url_first) {
            changeUrl("https://diabetyk.pl/");
            return true;
        }
        else if (item.getItemId() == R.id.url_second) {
            changeUrl("https://cukrzyca.pl/kategoria/porady/");
            return true;
        }
        return false;
    }

    private void updateData() {

        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();

        literatureViewModel = ViewModelProviders.of(this).get(LiteratureViewModel.class);

        literatureViewModel.getTitle().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                ab.setTitle(i);
            }
        });
    }

    private void initView(View root) {

        setHasOptionsMenu(true);
        ((DrawerLocker)getActivity()).setDrawerLocked(false);

        webView = (WebView) root.findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        literatureViewModel = ViewModelProviders.of(this).get(LiteratureViewModel.class);
        changeUrl("https://diabetyk.pl/");
    }

    private void changeUrl(String url) {

        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });
    }
}
