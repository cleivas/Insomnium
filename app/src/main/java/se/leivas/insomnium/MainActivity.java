package se.leivas.insomnium;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private ToggleButton toggleButton;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleButton = findViewById(R.id.startStopButton);
        textView = findViewById(R.id.infoTV);
        textView.setText(R.string.notReceivingVisionsTV);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    textView.setText(R.string.receivingVisionsTV);

                    Intent intent = new Intent(MainActivity.this, MainService.class);
                    startService(intent);
                }
                else{
                    textView.setText(R.string.notReceivingVisionsTV);

                    Intent intent = new Intent(MainActivity.this, MainService.class);
                    stopService(intent);
                }
            }
        });
    }
}
