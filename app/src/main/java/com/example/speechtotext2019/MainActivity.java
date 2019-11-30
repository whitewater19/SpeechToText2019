package com.example.speechtotext2019;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
///1介面完
///2左res New AndroidResourceDirectory, name改menu,type選menu建一個menu資料夾
///3menu資料夾New Menu resource file,name改main_menu(menu_speech)
///4在menu介面拉MenuItem給id(action_settings)
///5menu介面右icon選圖案
///6menu介面右showAsAction(在上方顯示),選always(如果沒勾選,就預設在3個點點的項目(menu_speech裡的Title可以改字)裡)
                                     //可以多拉幾個MenuItem(改Title)
public class MainActivity extends AppCompatActivity {
///7
    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
                     //自設常數名稱(int)       判斷是否接收到語音
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();//錄音recordSpeechInput();
            }
        });
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
//11紅,Add'catch' clause(s)
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
//12改catch( )內
//13刪e.printStackTrace( ); 然後打Toast
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }
//14右產生Override方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//15紅,Add super call
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && data != null) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));
                }
                break;
            }

        }
    }

//7但要顯示icon還是要寫程式碼:產生override方法onCreateOptionsMenu跟onOptionsItemSelected
//8紅就Make onCreateOptionsMenu public
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
                    //依不同activity指定menu選單
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_speech, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        //用if還是switch都可以
//        if (id == R.id.action_settings) {
//            return true;
//        }
        switch (id){
            case R.id.action_settings:
              Toast.makeText(MainActivity.this,"你按下了星星!",Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }
}
//9manifests授權