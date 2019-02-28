package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.rezultate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.MainActivity;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.R;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.database.DatabaseHandler;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model.Nota;

public class NoteActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mRecyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseHandler databaseHandler;
    private Button backButton;

    private List<Nota> note = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        databaseHandler = new DatabaseHandler(this);
        note = databaseHandler.findAllNote();
        backButton = findViewById(R.id.back_button);

        mRecyclerView = findViewById(R.id.note_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mRecyclerAdapter = new NoteRecyclerAdapter(note);
        mRecyclerView.setAdapter(mRecyclerAdapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        backPressed();
    }

    private void backPressed() {
        Intent intent = new Intent(NoteActivity.this, RezultateActivity.class);
        startActivity(intent);
        finish();
    }
}
