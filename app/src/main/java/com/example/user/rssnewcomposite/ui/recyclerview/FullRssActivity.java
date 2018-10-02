package com.example.user.rssnewcomposite.ui.recyclerview;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import com.example.user.rssnewcomposite.R;
import com.example.user.rssnewcomposite.adapter.RssFeedListAdapter;
import com.example.user.rssnewcomposite.model.RssFeedModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FullRssActivity extends AppCompatActivity {

    private static final String TAG = "FullRssActivity";

    private RecyclerView mRecyclerView;

    private SwipeRefreshLayout mSwipeLayout;


    private List<RssFeedModel> mFeedModelList;
    private String mFeedTitle;
    private String mFeedLink;
    private String mFeedDescription;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_rss);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        new FetchFeedTask().execute((Void) null);

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new FetchFeedTask().execute((Void) null);
            }
        });



    }



        public List<RssFeedModel> parseFeed(InputStream inputStream) throws XmlPullParserException, IOException {
            String title = null;
            String link = null;
            String description = null;
            boolean isItem = false;
            List<RssFeedModel> items = new ArrayList<>();

            try {
                XmlPullParser xmlPullParser = Xml.newPullParser();
                xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                xmlPullParser.setInput(inputStream, null);

                xmlPullParser.nextTag();
                while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                    int eventType = xmlPullParser.getEventType();

                    String name = xmlPullParser.getName();
                    if(name == null)
                        continue;

                    if(eventType == XmlPullParser.END_TAG) {
                        if(name.equalsIgnoreCase("item")) {
                            isItem = false;
                        }
                        continue;
                    }

                    if (eventType == XmlPullParser.START_TAG) {
                        if(name.equalsIgnoreCase("item")) {
                            isItem = true;
                            continue;
                        }
                    }

                    Log.d("MainActivity", "Parsing name ==> " + name);
                    String result = "";
                    if (xmlPullParser.next() == XmlPullParser.TEXT) {
                        result = xmlPullParser.getText();
                        xmlPullParser.nextTag();
                    }

                    if (name.equalsIgnoreCase("title")) {
                        title = result;
                    } else if (name.equalsIgnoreCase("link")) {
                        link = result;
                    } else if (name.equalsIgnoreCase("description")) {
                        description = result;
                    }

                    if (title != null && link != null && description != null) {
                        if(isItem) {
                            RssFeedModel item = new RssFeedModel(title, link, description);
                            items.add(item);
                        }
                        else {
                            mFeedTitle = title;
                            mFeedLink = link;
                            mFeedDescription = description;
                        }

                        title = null;
                        link = null;
                        description = null;
                        isItem = false;
                    }
                }

                return items;
            } finally {
                inputStream.close();
            }
        }

        private class FetchFeedTask extends AsyncTask<Void, Void, Boolean> {

            private String urlLink;

            @Override
            protected void onPreExecute() {
                mSwipeLayout.setRefreshing(true);
                mFeedTitle = null;
                mFeedLink = null;
                mFeedDescription = null;

                Bundle extras = getIntent().getExtras();
                if (extras != null) {

                    int position = extras.getInt("id");
                    urlLink = extras.getString("text");

                    Toast.makeText(getApplicationContext(),
                            "Position :" + position + " " + urlLink, Toast.LENGTH_LONG)
                            .show();


                }
            }

            @Override
            protected Boolean doInBackground(Void... voids) {
                if (TextUtils.isEmpty(urlLink))
                    return false;

                try {
                    if(!urlLink.startsWith("http://") && !urlLink.startsWith("https://"))
                        urlLink = "http://" + urlLink;

                    URL url = new URL(urlLink);
                    InputStream inputStream = url.openConnection().getInputStream();
                    mFeedModelList = parseFeed(inputStream);
                    return true;
                } catch (IOException e) {
                    Log.e(TAG, "Error", e);
                } catch (XmlPullParserException e) {
                    Log.e(TAG, "Error", e);
                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                mSwipeLayout.setRefreshing(false);

                if (success) {

                    // Fill RecyclerView
                    mRecyclerView.setAdapter(new RssFeedListAdapter(mFeedModelList));
                } else {
                    Toast.makeText(FullRssActivity.this,
                            "Enter a valid Rss feed url",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
}

