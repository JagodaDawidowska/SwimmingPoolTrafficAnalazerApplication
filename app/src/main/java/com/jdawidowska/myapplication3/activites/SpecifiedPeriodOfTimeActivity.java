package com.jdawidowska.myapplication3.activites;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

public class SpecifiedPeriodOfTimeActivity extends AppCompatActivity {

    Button btnBackToMenu;
    private String SPECIFIED_PERIOD_OF_TIME_URL = "http://192.168.1.4:8090/DTO_Peroid_of_TimeBODY";
    private String BASE_URL = "http://192.168.1.4:8090/DTO_Peroid_of_TimeBODY";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specified_period_of_time);
        AnyChartView anyChartView = findViewById(R.id.specified_period_of_time_data_line);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar_specified_time));
        fetchData(anyChartView);

        btnBackToMenu = findViewById(R.id.btnBackSpeciifiedPeroidOfTime);
        btnBackToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    private static class CustomDataEntrySpecifiedPeroidOfTime extends ValueDataEntry {
        CustomDataEntrySpecifiedPeroidOfTime(String x, Number value1, Number value2, Number value3, Number value4) {
            super(x, value1);
            setValue("value2", value2);
            setValue("value3", value3);
            setValue("value4", value4);
        }
    }

    public void fetchData(AnyChartView anyChartView) {
        // generated date 2023-04-25T19:20:00.71856+0200
        String dateFrom = TimeUtil.returnDateFrom();
        //"2023-04-25T19:20:00.71856+02:00";
        String dateTo = TimeUtil.returnDateTo();
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
                        dataModel.setAmountOfFreeSpotsAtPool(responseObj.getInt("amountOfFreeSpotsAtPool"));
                        dataModel.setAmountPeopleAtSpa(responseObj.getInt("amountPeopleAtSpa"));
                        dataModel.setAmountOfFreeSpotsAtSpa(responseObj.getInt("amountOfFreeSpotsAtSpa"));
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


    public String modifyingDateAxis(String dateString) {
        return dateString.substring(14, 16);
    }

    List<DataEntry> seriesData(List<DataModel> DataFromApiList) {
        List<DataModel> DataFromApiList1 = DataFromApiList;
        List<DataEntry> seriesData = new ArrayList<>();
        for (int i = 0; i < DataFromApiList1.size(); i++) {
            seriesData.add(new CustomDataEntrySpecifiedPeroidOfTime(
                    DataFromApiList1.get(i).getDownloadDate(),
                    DataFromApiList1.get(i).getAmountPeopleAtPool(),
                    DataFromApiList1.get(i).getAmountOfFreeSpotsAtPool(),
                    DataFromApiList1.get(i).getAmountPeopleAtSpa(),
                    DataFromApiList1.get(i).getAmountOfFreeSpotsAtSpa()
            ));
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
        cartesian.yAxis(0).title("ilosc osob i wolnych miejsc");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);
        List<DataEntry> seriesData1 = seriesData(DataFromApiList1);

        Set set = Set.instantiate();
        set.data(seriesData1);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value1' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");
        Mapping series4Mapping = set.mapAs("{ x: 'x', value: 'value4' }");

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

        Line series2 = cartesian.line(series2Mapping);
        series2.name("wolne basen");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series3 = cartesian.line(series3Mapping);
        series3.name("spa");
        series3.hovered().markers().enabled(true);
        series3.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series3.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series4 = cartesian.line((series4Mapping));
        series4.name("wolne miejsca spa");
        series4.hovered().markers().enabled(true);
        series4.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series4.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);
        anyChartView.setChart(cartesian);
    }

}