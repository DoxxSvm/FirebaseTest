package com.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText employeeNameEdt, employeePhoneEdt, employeeAddressEdt;
    private Button sendDatabtn;
    ListView listView;
    ChildEventListener childEventListener;
    ArrayList<EmployeeInfo> arrayList;
    StartUpAdaptor adaptor;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    EmployeeInfo employeeInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayList= new ArrayList<>();
        employeeNameEdt = findViewById(R.id.idEdtEmployeeName);
        employeePhoneEdt = findViewById(R.id.idEdtEmployeePhoneNumber);
        employeeAddressEdt = findViewById(R.id.idEdtEmployeeAddress);
        listView = findViewById(R.id.listView);
        firebaseDatabase = FirebaseDatabase.getInstance();
        adaptor = new StartUpAdaptor(this,arrayList);
        databaseReference = firebaseDatabase.getReference("Startup");
        employeeInfo = new EmployeeInfo();
        sendDatabtn = findViewById(R.id.idBtnSendData);
        listView.setAdapter(adaptor);
        sendDatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = employeeNameEdt.getText().toString();
                String phone = employeePhoneEdt.getText().toString();
                String address = employeeAddressEdt.getText().toString();

                if (TextUtils.isEmpty(name) && TextUtils.isEmpty(phone) && TextUtils.isEmpty(address)) {

                    Toast.makeText(MainActivity.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                } else {
                    addDatatoFirebase(name, phone, address);
                    //read(arrayList);
                }
            }
        });
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                   EmployeeInfo employeeInfo= snapshot.getValue(EmployeeInfo.class);
                   arrayList.add(employeeInfo);
                   adaptor.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.addChildEventListener(childEventListener);





    }
    void read(ArrayList<EmployeeInfo> arrayList){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

//                snapshot.getChildren().iterator().next().getKey();
//                System.out.println("doxx" + key);
                arrayList.clear();
                for(DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    String key = dataSnapshot1.getKey();
                    if(key.length() ==20){
                        EmployeeInfo test = new EmployeeInfo();
                        test.setpotential((String) dataSnapshot1.child("potential").getValue());
                        test.setstartUpName(dataSnapshot1.child("startUpName").getValue(String.class));
                        test.setbuildTime(dataSnapshot1.child("buildTime").getValue(String.class));
                        arrayList.add(test);
                    }
                }
                update();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
    void update(){
        adaptor = new StartUpAdaptor(this,arrayList);
        listView.setAdapter(adaptor);
    }
    private void addDatatoFirebase(String name, String phone, String address) {
        // below 3 lines of code is used to set
        // data in our object class.
        employeeInfo.setstartUpName(name);
        employeeInfo.setpotential(phone);
        employeeInfo.setbuildTime(address);
        String id = databaseReference.push().getKey();


        assert id != null;

        databaseReference.child(id).setValue(employeeInfo);





        // we are use add value event listener method
        // which is called with database reference.
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                // inside the method of on Data change we are setting
//                // our object class to our database reference.
//                // data base reference will sends data to firebase.
//                databaseReference.setValue(employeeInfo);
//
//                // after adding this data we are showing toast message.
//                Toast.makeText(MainActivity.this, "data added", Toast.LENGTH_SHORT).show();
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // if the data is not added or it is cancelled then
//                // we are displaying a failure toast message.
//                Toast.makeText(MainActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
//            }
//        });
    }



}