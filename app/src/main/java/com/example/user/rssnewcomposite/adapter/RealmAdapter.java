package com.example.user.rssnewcomposite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.rssnewcomposite.R;
import com.example.user.rssnewcomposite.controller.RealmController;
import com.example.user.rssnewcomposite.model.RealmModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

public class RealmAdapter extends RealmBaseAdapter<RealmModel> {

    private OnClickListener onClickListener;

    public RealmAdapter(Context context, RealmResults<RealmModel> realmResults) {
        super(realmResults);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_realm, parent, false);
        RealmViewHolder viewHolder = new RealmViewHolder(convertView);

        final RealmModel model = getRealmResults().get(position);
        viewHolder.title.setText(model.getTitle());

        viewHolder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RealmController().removeItemById(getRealmResults().get(position).getId());
            }
        });
        viewHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onEditButtonCLick(model.getId(), model.getTitle());
            }
        });
        return convertView;
    }

    public RealmResults<RealmModel> getRealmResults() {
        return null;
    }

    public class RealmViewHolder {

        @BindView(R.id.title)
        TextView title;



        @BindView(R.id.editButton)
        ImageView editButton;

        @BindView(R.id.removeButton)
        ImageView removeButton;

        public RealmViewHolder(final View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onEditButtonCLick(int id, String title);
    }
}
