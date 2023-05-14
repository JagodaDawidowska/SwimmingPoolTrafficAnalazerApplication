package com.jdawidowska.myapplication3.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.jdawidowska.myapplication3.R;
import com.jdawidowska.myapplication3.models.DataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CurrentDataActivity extends AppCompatActivity {

    private String CURRENT_DATA_URL = "http://192.168.1.4:8090/last_Data";
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_data);

        ConstraintLayout constraintLayout = findViewById(R.id.constrain_current_data);
        constraintLayout.setBackgroundResource(R.drawable.pool_background);

        AnyChartView anyChartView = findViewById(R.id.current_data_bar_graph);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar_current_data));
        fetchData(anyChartView);

        backButton = findViewById(R.id.btnBackCurrentData);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    public void fetchData(AnyChartView anyChartView) {
        List<DataModel> dataModelList = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                CURRENT_DATA_URL
                , null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < 1; i++) {
                            try {
                                JSONObject responseObj = response.getJSONObject(i);
                                DataModel dataModel = new DataModel();
                                dataModel.setAmountPeopleAtPool(responseObj.getInt("amountPeopleAtPool"));
                                dataModel.setAmountOfFreeSpotsAtPool(responseObj.getInt("amountOfFreeSpotsAtPool"));
                                dataModel.setAmountPeopleAtSpa(responseObj.getInt("amountPeopleAtSpa"));
                                dataModel.setAmountOfFreeSpotsAtSpa(responseObj.getInt("amountOfFreeSpotsAtSpa"));
                                dataModel.setDownloadDate(responseObj.getString("downloadDate"));
                                dataModelList.add(dataModel);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        setColumns(anyChartView, dataModelList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    List<DataEntry> enterData(List<DataModel> dataFromApiList) {
        List<DataEntry> dataEntryList = new ArrayList<>();
        for (int i = 0; i < dataFromApiList.size(); i++) {
            dataEntryList.add(new ValueDataEntry("basen", dataFromApiList.get(i).getAmountPeopleAtPool()));
            dataEntryList.add(new ValueDataEntry("wolne basen", dataFromApiList.get(i).getAmountOfFreeSpotsAtPool()));
            dataEntryList.add(new ValueDataEntry("spa", dataFromApiList.get(i).getAmountPeopleAtSpa()));
            dataEntryList.add(new ValueDataEntry("wolne spa", dataFromApiList.get(i).getAmountOfFreeSpotsAtPool()));
        }
        return dataEntryList;
    }

    public void setColumns(AnyChartView anyChartView, List<DataModel> dataFromApiList) {
        List<DataEntry> data = enterData(dataFromApiList);
        Cartesian cartesian = AnyChart.column();

        Column column = cartesian.column(data);
        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("{%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("Terazniejsze dane");
        cartesian.yScale().minimum(0d);
        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);
        cartesian.xAxis(0).title("");
        cartesian.yAxis(0).title("licza osob/wolnych miejsc");
        anyChartView.setChart(cartesian);
    }
}