package com.propwing.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.propwing.R;
import com.propwing.database.PropertyDatabase;
import com.propwing.service.PropertyService;
import com.propwing.service.event.OnPropertyEvent;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private EventBus mEventBus;

    private ImageView mAvatar;
    private TextView mName;
    private ImageView mRefreshBtn;
    private EditText mUrl;
    private Button mSubmitBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEventBus = EventBus.getDefault();
        mEventBus.register(this);
        PropertyDatabase.getInstance().init(this);

        mAvatar = (ImageView) findViewById(R.id.agent_avatar);
        mName = (TextView) findViewById(R.id.agent_name);
        mRefreshBtn = (ImageView) findViewById(R.id.btn_refresh);
        mUrl = (EditText) findViewById(R.id.url_et);
        mSubmitBtn = (Button) findViewById(R.id.submit_btn);

        mRefreshBtn.setOnClickListener(this);
        mSubmitBtn.setOnClickListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setNestedScrollingEnabled(false);

        PropertyService.getInstance().getProperties("http://kenkong.iagent.my", 0, "IP");
    }

    @Subscribe
    public void onEvent(final OnPropertyEvent event) {
        if (event.isSuccess()) {
            mName.setText(event.getProperty().getAgentName());
            Picasso.with(this).load(event.getProperty().getAgentPictureUrl())
                    .noPlaceholder()
                    .resizeDimen(R.dimen.circular_image_width, R.dimen.circular_image_height)
                    .centerCrop()
                    .into(mAvatar);
            ListingAdapter mAdapter = new ListingAdapter(this, event.getProperty());
            mAdapter.notifyDataSetChanged();
            mRecyclerView.setAdapter(mAdapter);
        } else {
            Toast.makeText(this, event.getInfo(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        if (mEventBus != null) {
            mEventBus.unregister(this);
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_refresh:
                PropertyService.getInstance().getProperties("http://kenkong.iagent.my", 1, "IP");
                Toast.makeText(this, "Refreshing...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.submit_btn:
                PropertyDatabase.getInstance().setCustomUrl(mUrl.getText().toString());
                PropertyService.getInstance().getPropertiesFromCustomUrl(mUrl.getText().toString());
                Toast.makeText(this, "Refreshing...", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
