package fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import ravikiran.pathade.ravikiranpathade.newstrends.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WebViewNewsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WebViewNewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WebViewNewsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    WebView web;
    ImageView shareButton;
    android.support.v7.widget.Toolbar toolbar;
    String titleTo;
    Bundle checkBundle;
    String rotated;
    String r;
    String scrollX;
    String scrollY;
    private OnFragmentInteractionListener mListener;

    public WebViewNewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WebViewNewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WebViewNewsFragment newInstance(String param1, String param2) {
        WebViewNewsFragment fragment = new WebViewNewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getContext().getResources().getString(R.string.scrollX), web.getScrollX());
        outState.putInt(getContext().getResources().getString(R.string.scrollY), web.getScrollY());
        outState.putString(rotated,r);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        rotated = getContext().getResources().getString(R.string.rotated);
        r = getContext().getResources().getString(R.string.r);
        scrollX = getContext().getResources().getString(R.string.scrollX);
        scrollY = getContext().getResources().getString(R.string.scrollY);


        View view = inflater.inflate(R.layout.fragment_web_view_news, container, false);

        toolbar = view.findViewById(R.id.webViewToolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle(getContext().getResources().getString(R.string.loading));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        web = view.findViewById(R.id.webViewLink);
        checkBundle = savedInstanceState;
        savedInstanceState = getArguments();
        titleTo = savedInstanceState.getString(getContext().getResources().getString(R.string.bundle_key_titleToWeb));
        final String checkedUrl = savedInstanceState.getString(getContext().getResources().getString(R.string.bundle_key_urlForWeb));
        final String webU = savedInstanceState.getString(getContext().getResources().getString(R.string.bundle_key_realWebUrl));
        WebViewClient custom = new CustomWebViewHere();
        web.setWebViewClient(custom);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        if (savedInstanceState.getString(rotated) == null) {
            web.loadUrl(checkedUrl);
        }else{
            scrollXToCustom(checkBundle.getInt(scrollX));
        }


        shareButton = view.findViewById(R.id.shareWeb);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setType(getContext().getResources().getString(R.string.text_plain));
                sendIntent.putExtra(Intent.EXTRA_TEXT, webU);
                startActivity(sendIntent);
            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void scrollXToCustom(final int x) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                web.scrollTo(0, x);
            }
        }, 200);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class CustomWebViewHere extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            if (checkBundle != null) {
                scrollXToCustom(checkBundle.getInt(scrollY));
            }
            shareButton.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            toolbar.setTitle(titleTo);



        }


    }
}
