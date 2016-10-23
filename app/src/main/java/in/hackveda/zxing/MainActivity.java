package in.hackveda.zxing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static in.hackveda.zxing.R.id.scan;

public class MainActivity extends AppCompatActivity {
    Button bt1;
    TextView tv1;
    ListView lv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt1=(Button)findViewById(scan);
        final Activity activity=this;
        tv1=(TextView)findViewById(R.id.tv);
        lv=(ListView)findViewById(R.id.listview) ;
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator=new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan Code");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();


            }
        });
        MyDatabase scan1=new MyDatabase(this);
        scan1.open();
        String scanner=scan1.read();
        tv1.setText(scanner);
        scan1.close();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result!=null)
        {
            if(result.getContents()==null)
            {
                Log.d("MainActivity","Cancelled Scan");
                Toast.makeText(this,"Cancelled",Toast.LENGTH_LONG).show();
            }
            else {
                String code=result.getContents().toString();
                    MyDatabase scan=new MyDatabase(this);
                    scan.open();
                    scan.write(code);

              String scanner=scan.read();
                tv1.setText(scanner);



             //   List<String> your_array_list = new ArrayList<String>();
            //    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, your_array_list );
             //   your_array_list.add(result.getContents().toString());
                Log.d("MainActivity","Scanned");
                Toast.makeText(this,"Scanned",Toast.LENGTH_LONG).show();
               // tv1.setText(result.getContents().toString());
               // lv.setAdapter(arrayAdapter);

                scan.close();
            }

        }else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

}
