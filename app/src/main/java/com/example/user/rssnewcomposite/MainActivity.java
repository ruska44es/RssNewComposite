package com.example.user.rssnewcomposite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.user.rssnewcomposite.ui.recyclerview.FullRssActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    //private Realm realm;


    @BindView(R.id.postListView)
    ListView recyclerView;

    @BindView(R.id.edit)
    EditText editText;

    @BindView(R.id.button_find)
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


        final ArrayList<String> rssNames = new ArrayList<>();

        // Создаём адаптер ArrayAdapter, чтобы привязать массив к ListView

        final ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rssNames);

        // Привяжем массив через адаптер к ListView
        recyclerView.setAdapter(adapter);







        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                String listItem = (String) adapterView.getItemAtPosition(position);

                Intent myIntent = new Intent(view.getContext(), FullRssActivity.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                myIntent.putExtra("id", position);
                myIntent.putExtra("text", listItem);
                startActivityForResult(myIntent, 0);

            }
        });



        button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            rssNames.add(0, editText.getText().toString());
            adapter.notifyDataSetChanged();
            editText.setText("");
        }
        });

    }








    @Override
    protected void onDestroy() {
        super.onDestroy();
        //realm.close();
    }



}
