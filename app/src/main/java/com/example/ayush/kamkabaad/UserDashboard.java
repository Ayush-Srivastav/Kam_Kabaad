package com.example.ayush.kamkabaad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class UserDashboard extends AppCompatActivity {

    private CardView card_view_prev , card_add_new , submit_order;
    private LinearLayout add_new_layout , layout_addr_map , layout_addr_str;
    private EditText et_user_name , et_user_address;
    private TextView tv_addr_map;
    private Spinner spinner_garbage_type;
    ArrayAdapter<CharSequence> garbage_type_adapter;
    Boolean show_addr_str , show_addr_map;
    String username , address , garbage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        show_addr_map = false;
        show_addr_str = false;
        card_view_prev = (CardView)findViewById(R.id.card_view_prev);
        card_add_new = (CardView)findViewById(R.id.card_add_new);
        add_new_layout = (LinearLayout)findViewById(R.id.add_new_layout);
        et_user_name = (EditText)findViewById(R.id.et_user_name);
        spinner_garbage_type = (Spinner)findViewById(R.id.spinner_garbage_type);
        et_user_address = (EditText)findViewById(R.id.et_user_address);
        layout_addr_map = (LinearLayout)findViewById(R.id.layout_addr_map);
        layout_addr_str = (LinearLayout)findViewById(R.id.layout_addr_str);
        tv_addr_map = (TextView)findViewById(R.id.tv_addr_map);
        submit_order = (CardView)findViewById(R.id.submit_order);

        add_new_layout.setVisibility(View.GONE);
        et_user_address.setVisibility(View.GONE);
        setUpAdapter();
        card_view_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPrevoiusOrders();
            }
        });

        card_add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewOrder();
            }
        });

        layout_addr_str.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAdreessAsString();
            }
        });

        layout_addr_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAdressAsMap();
            }
        });

        submit_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrder();
            }
        });

    }

    private void setUpAdapter(){
        garbage_type_adapter = ArrayAdapter.createFromResource(this , R.array.garbage_type , android.R.layout.simple_spinner_item);
        garbage_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_garbage_type.setAdapter(garbage_type_adapter);
        spinner_garbage_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                garbage = String.valueOf(garbage_type_adapter.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                garbage = "";
            }
        });
    }

    private void showPrevoiusOrders() {
        Toast.makeText(this, "Sorry !! this part is not yet implemented. We will be back with this feature shortly.", Toast.LENGTH_SHORT).show();
    }

    private void addNewOrder() {
        add_new_layout.setVisibility(View.VISIBLE);
    }

    private void selectAdreessAsString(){
        et_user_address.setVisibility(View.VISIBLE);
        tv_addr_map.setVisibility(View.GONE);
        show_addr_str = true;
        show_addr_map = false;
    }

    private void selectAdressAsMap() {
        show_addr_map = true;
        show_addr_str = false;
    }

    private void submitOrder() {
        username = et_user_address.getText().toString();
        if(show_addr_str){
            address = et_user_address.getText().toString();
        } else if(show_addr_map) {

        }
    }
}
