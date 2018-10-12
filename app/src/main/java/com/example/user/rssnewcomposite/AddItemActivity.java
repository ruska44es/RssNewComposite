package com.example.user.rssnewcomposite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.example.user.rssnewcomposite.controller.RealmController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



/*public class AddItemActivity extends AppCompatActivity {

    @BindView(R.id.title)
    EditText title;



    private boolean isEditMode = false;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null) {
            isEditMode = getIntent().getExtras().getBoolean(IS_EDIT);
            title.setText(getIntent().getExtras().getString(TITLE));

            id = getIntent().getExtras().getInt(ID);
        }
    }

    @OnClick(R.id.addButton)
    public void onAddClick() {
        if (!isEditMode)
            new RealmController().addInfo(title.getText().toString());
        else
            new RealmController().updateInfo(id, title.getText().toString());
        finish();
    }
}*/
