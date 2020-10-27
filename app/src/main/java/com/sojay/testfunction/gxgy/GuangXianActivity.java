package com.sojay.testfunction.gxgy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sojay.testfunction.R;
import com.sojay.testfunction.utils.DataUtil;
import com.sojay.testfunction.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class GuangXianActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvMax;
    private TextView tvMin;
    private TextView tvPY;
    private TextView tvStart;
    private TextView tvStop;
    private TextView tvSave;
    private RecyclerView mRv;

    private SensorManager sensorManager;
    private Sensor sensor;
    private GuangXianAdapter mAdapter;
    private List<GuangXianBean> mList;
    private float max;
    private float min;
    private float sum;
    private int count;

    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            float lux = event.values[0];

            if (max == 0 || lux > max)
                max = lux;

            if (min == 0 || lux < min)
                min = lux;

            sum+=lux;
            count++;

            //提示当前光照强度
            tvMax.setText(DataUtil.keepTwo(max));
            tvMin.setText(DataUtil.keepTwo(min));
            tvPY.setText(DataUtil.keepTwo(sum / count));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guang_xian);

        mList = new ArrayList<>();

        initView();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

    }

    private void initView() {
        tvMax = findViewById(R.id.tv_max);
        tvMin = findViewById(R.id.tv_min);
        tvPY = findViewById(R.id.tv_pingjun);
        tvStart = findViewById(R.id.tv_start);
        tvStop = findViewById(R.id.tv_stop);
        tvSave = findViewById(R.id.tv_save);
        mRv = findViewById(R.id.rv);

        tvStart.setOnClickListener(this);
        tvStop.setOnClickListener(this);
        tvSave.setOnClickListener(this);

        tvStop.setEnabled(false);
        tvSave.setEnabled(false);

        mRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new GuangXianAdapter(mList);
        mRv.setAdapter(mAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //传感器使用完毕，释放资源
        if(sensorManager != null)
            sensorManager.unregisterListener(listener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start:
                max = 0f;
                min = 0f;
                sum = 0f;
                count = 0;
                sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
                tvStop.setEnabled(true);
                break;

            case R.id.tv_stop:
                if(sensorManager != null)
                    sensorManager.unregisterListener(listener);
                tvStop.setEnabled(false);
                tvSave.setEnabled(true);
                break;

            case R.id.tv_save:
                mList.add(0, new GuangXianBean(max, min, sum/count, DateUtils.long2Str(System.currentTimeMillis(), true)));
                mAdapter.notifyDataSetChanged();
                tvSave.setEnabled(false);
                break;
        }
    }
}
