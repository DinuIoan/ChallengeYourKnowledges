package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.rezultate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.MainActivity;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.R;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.database.DatabaseData;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.database.DatabaseHandler;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model.Nota;

public class GraficeActivity extends AppCompatActivity {
    private Button backButton;
    private LineChart lineChart;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafice);
        databaseHandler = new DatabaseHandler(this);

        backButton = findViewById(R.id.back_button);

        lineChart = findViewById(R.id.chart);

        initializeChart();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backPressed();
            }
        });

    }

    private void initializeChart() {
        List<Nota> note = databaseHandler.findAllNote();

        List<Entry> entries = new ArrayList<Entry>();
        int i = 0;
        if (note.size() > 25) {
            note = note.subList(note.size() - 25, note.size());
        }
        for (Nota data : note) {
            // turn your data into Entry objects
            entries.add(new Entry(i, Float.parseFloat("" + data.getNota())));
            i++;
        }
        LineDataSet dataSet = new LineDataSet(entries, "Note"); // add entries to dataset
        dataSet.setColors(R.color.black);
        dataSet.setFillColor(R.color.black);
        dataSet.setValueTextColor(R.color.black);
        dataSet.setLineWidth(2);
        dataSet.setValueTextSize(7);
        LineData lineData = new LineData(dataSet);

        YAxis leftAxis = lineChart.getAxis(YAxis.AxisDependency.LEFT);
        YAxis rightAxis= lineChart.getAxis(YAxis.AxisDependency.RIGHT);
        leftAxis.setGridColor(R.color.black);
        leftAxis.setAxisLineColor(R.color.black);
        leftAxis.setGranularity(1f);
        leftAxis.setAxisMaximum(10f);
        leftAxis.setAxisMinimum(0f);

        rightAxis.setEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);


        Description description = new Description();
        description.setEnabled(false);
        lineChart.setGridBackgroundColor(R.color.black);
        lineChart.setBorderColor(R.color.black);
        lineData.setValueTextColor(R.color.black);
        lineChart.setNoDataText("Nu sunt note disponibile.");
        lineChart.setDescription(description);
        lineChart.setData(lineData);
        lineChart.invalidate();

    }

    @Override
    public void onBackPressed() {
        backPressed();
    }

    private void backPressed() {
        Intent intent = new Intent(GraficeActivity.this, RezultateActivity.class);
        startActivity(intent);
        finish();
    }
}
