package com.example.mcalcpro;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;
import ca.roumani.i2c.MPro;

public class MainActivity extends AppCompatActivity implements
        TextToSpeech.OnInitListener, SensorEventListener
{
    private TextToSpeech tts;

    MPro mp = new MPro();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.tts = new TextToSpeech(this, this);

        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        assert sm != null;
        sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

    }

    @SuppressLint("DefaultLocale")
    public void buttonClicked(View v) {
        try {
            String pinput = ((EditText) findViewById(R.id.pBox)).getText().toString();
            mp.setPrinciple(pinput);
            String ainput = ((EditText) findViewById(R.id.aBox)).getText().toString();
            mp.setAmortization(ainput);
            String iinput = ((EditText) findViewById(R.id.iBox)).getText().toString();
            mp.setInterest(iinput);

            String s = "Monthly Payment = " + mp.computePayment("%,.2f");
            s += "\n\n";
            s += "By making this payment monthly for " + ainput + " ";
            s += "years, the mortgage will be paid in full. But if ";
            s += "you terminate the mortgage on its nth ";
            s += "anniversary, the balance still owing depends ";
            s += "on n as shown below:";
            s += "\n\n";
            s += String.format("%8s"," n") + String.format("%16s","balance");
            s += "\n\n";

            s += String.format("%8d", 0) + mp.outstandingAfter(0, "%,16.0f");
            s += "\n\n";
            s += String.format("%8d", 1) + mp.outstandingAfter(1, "%,16.0f");
            s += "\n\n";
            s += String.format("%8d", 2) + mp.outstandingAfter(2, "%,16.0f");
            s += "\n\n";
            s += String.format("%8d", 3) + mp.outstandingAfter(3, "%,16.0f");
            s += "\n\n";
            s += String.format("%8d", 4) + mp.outstandingAfter(4, "%,16.0f");
            s += "\n\n";
            s += String.format("%8d", 5) + mp.outstandingAfter(5, "%,16.0f");
            s += "\n\n";
            s += String.format("%8d", 10) + mp.outstandingAfter(10, "%,16.0f");
            s += "\n\n";
            s += String.format("%8d", 15) + mp.outstandingAfter(15, "%,16.0f");
            s += "\n\n";
            s += String.format("%8d", 20) + mp.outstandingAfter(20, "%,16.0f");

            ((TextView) findViewById(R.id.output)).setText(s);
            tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
        } catch (Exception e)
        {
            Toast label = Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
            label.show();
        }

    }
    public void onSensorChanged(SensorEvent event)
    {
        double ax = event.values[0];
        double ay = event.values[1];
        double az = event.values[2];
        double a = Math.sqrt(ax * ax + ay * ay + az * az);
        if (a > 20)
        {
            ((EditText) findViewById(R.id.pBox)).setText("");
            ((EditText) findViewById(R.id.aBox)).setText("");
            ((EditText) findViewById(R.id.iBox)).setText("");
            ((TextView) findViewById(R.id.output)).setText("");
        }
    }

    public void onAccuracyChanged (Sensor arg0, int arg1)
    {

    }
    @Override
    public void onInit(int status)
    {

        this.tts.setLanguage(Locale.US);
    }
}