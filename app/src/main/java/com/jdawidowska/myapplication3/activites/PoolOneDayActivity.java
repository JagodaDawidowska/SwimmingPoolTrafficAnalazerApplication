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
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.jdawidowska.myapplication3.R;
import com.jdawidowska.myapplication3.utils.TimeUtil;
import com.jdawidowska.myapplication3.models.DataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PoolOneDayActivity extends AppCompatActivity {

    private String SPECIFIED_PERIOD_OF_TIME_URL = "http://192.168.1.4:8090/DTO_Peroid_of_TimeBODY";
    public Button btnBackToMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pool_one_day);

        ConstraintLayout constraintLayout = findViewById(R.id.constraint_pool_day);
        constraintLayout.setBackgroundResource(R.drawable.pool_background);

        AnyChartView anyChartView = findViewById(R.id.pool_day_data_line);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar_day_data_pool));
        fetchData(anyChartView);

        btnBackToMenu = findViewById(R.id.btnBackPoolDayDataPool);
        btnBackToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    private static class CustomDataEntryOneDay extends ValueDataEntry {
        CustomDataEntryOneDay(String x, Number value1) {
            super(x, value1);
        }
    }

    public void fetchData(AnyChartView anyChartView) {
        String dateFrom = TimeUtil.returnDateFromDay();
        //"2023-04-25T19:20:00.71856+02:00";
        String dateTo = TimeUtil.returnDateToDay();
        //"2023-04-25T20:30:00.71856+02:00";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        List<DataModel> dataModelList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("dateFrom", dateFrom);
            jsonObject.put("dateTo", dateTo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, SPECIFIED_PERIOD_OF_TIME_URL, null, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject responseObj = response.getJSONObject(i);
                        DataModel dataModel = new DataModel();
                        dataModel.setAmountPeopleAtPool(responseObj.getInt("amountPeopleAtPool"));
                        String dateBuffor = responseObj.getString("downloadDate");
                        dataModel.setDownloadDate(modifyingDateAxis(dateBuffor));
                        dataModelList.add(dataModel);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                setLines(anyChartView, dataModelList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public byte[] getBody() {
                return jsonObject.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        requestQueue.add(jsonArrayRequest);
    }

    private String modifyingDateAxis(String dateString) {
        return dateString.substring(11, 13);
    }

    List<DataEntry> seriesData(List<DataModel> DataFromApiList) {
        List<DataModel> DataFromApiList1 = DataFromApiList;
        List<DataEntry> seriesData = new ArrayList<>();
        for (int i = 0; i < DataFromApiList1.size(); i++) {
            seriesData.add(new CustomDataEntryOneDay(
                    DataFromApiList1.get(i).getDownloadDate(),
                    DataFromApiList1.get(i).getAmountPeopleAtPool()));
        }
        return seriesData;
    }

    public void setLines(AnyChartView anyChartView, List<DataModel> DataFromApiList) {
        List<DataModel> DataFromApiList1 = DataFromApiList;

        Cartesian cartesian = AnyChart.line();
        cartesian.animation(true);
        cartesian.padding(10d, 20d, 5d, 20d);
        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.title("Dane z basenu CRS w Zielonej Gorze");
        cartesian.yAxis(0).title("ilosc osob na basenie w ciagu dnia");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        List<DataEntry> seriesData1 = seriesData(DataFromApiList1);
        Set set = Set.instantiate();
        set.data(seriesData1);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value1' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("basen");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);
        series1.hovered().labels("X");
        anyChartView.setChart(cartesian);
    }
}