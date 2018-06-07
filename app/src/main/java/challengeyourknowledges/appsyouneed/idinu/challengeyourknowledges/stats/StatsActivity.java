package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.stats;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import java.util.List;

import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.MainActivity;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.R;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.database.DatabaseData;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model.Rankings;

public class StatsActivity extends AppCompatActivity {
    private List<Rankings> rankingsList;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView rankingsNotFoundTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        rankingsNotFoundTextView = (TextView) findViewById(R.id.rankings_not_found_text_view);
        rankingsNotFoundTextView.setVisibility(View.INVISIBLE);
        rankingsList = DatabaseData.getRankings();
        if (rankingsList.size() == 0) {
            recyclerView.setVisibility(View.INVISIBLE);
            rankingsNotFoundTextView.setVisibility(View.VISIBLE);
        } else {
            rankingsNotFoundTextView.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);

            mLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerViewAdapter = new RecyclerViewAdapter(rankingsList);
            recyclerView.setAdapter(recyclerViewAdapter);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(StatsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
