package com.example.library10;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.Book;
import com.example.model.Book_User_Borrow;
import com.example.model.Tab1ListViewAdapter;
import com.example.model.Tab3ListViewAdapter;
import com.example.model.Tab4ListViewAdapter;
import com.example.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.camera.CameraSettings;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class LibraryTabActivity extends AppCompatActivity {
    // Tab 1:
    //List view:
    ArrayList<Book>arrBorrowList;
    Tab1ListViewAdapter adapterTab1;
    ListView lvTab1_BorrowList;
    //Buttons:
    Button btnTab1_Confirm;
    Button btnTab1_Delete;
    //Camera:
    DecoratedBarcodeView qr_scanner_view_Borrow;
    //Maximum Book Quantity Can Be Borrowed:
    long allowedMaximumBorrowingBookQuantity;
    HashMap<String,Book>hashMapTab1;
    String subStringEmail;

    //Tab 2:
    //List view:
    ArrayList<Book>arrReturnList;
    Tab1ListViewAdapter adapterTab2;
    ListView lvTab2_ReturnList;
    //Buttons:
    Button btnTab2_Confirm;
    Button btnTab2_Delete;
    //Camera:
    DecoratedBarcodeView qr_scanner_view_Return;
    TabHost tabHost;
    ArrayList<String>arrBorrowBookKeySet;
    Book deletedBook;

    //Tab 3:
    ListView lvTab3_BookSearch;
    AutoCompleteTextView txtBookSearch_Tab3;
    ImageButton btnBookSearch_Tab3;
    ArrayList<String>arrAutoCompleteTextView;
    ArrayAdapter<String>adapterAutoCompleteTextView;
    Tab3ListViewAdapter tab3ListViewAdapter;
    ArrayList<Book>arrFoundBook;
    HashMap<String,Book> hashMap;

    //Tab 4:
    TextView txtTab4_Email, txtTab4_Phone, txtTab4_Name;
    ImageButton btnTab4_ChangePassword;
    ListView lvTab4_BorringBook;
    ArrayList<Book_User_Borrow> arrBook_Tab4;
    Tab4ListViewAdapter tab4ListViewAdapter;
    Button btnTab4_SignOut;

    //Database and authentication reference:
    DatabaseReference bookReference = FirebaseDatabase.getInstance().getReference().child("root").child("Book");
    DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("root").child("User");
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference Book_User_BorrowReference = FirebaseDatabase.getInstance().getReference().child("root").child("Book_User_Borrow");
    DatabaseReference allowedMaximumBorrowingBookReference = FirebaseDatabase.getInstance().getReference().child("root").child("MaximumBookQuantity");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_tab);
        addControls();
        addEvents();
    }

    //CONTROLS:
    private void addControls() {
        addTabHost();
        addTab2();
        addTab1();
        addTab3();
        addTab4();
        uploadGeneralInformation();
    }
    private void addTabHost(){
        tabHost=findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tab1=tabHost.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("BORROW BOOKS");
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2=tabHost.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("RETURN BOOKS");
        tabHost.addTab(tab2);

        TabHost.TabSpec tab3=tabHost.newTabSpec("t3");
        tab3.setContent(R.id.tab3);
        tab3.setIndicator("SEARCH FOR BOOKS");
        tabHost.addTab(tab3);

        TabHost.TabSpec tab4=tabHost.newTabSpec("t4");
        tab4.setContent(R.id.tab4);
        tab4.setIndicator("PROFILE");
        tabHost.addTab(tab4);
        addTabEvents();
    }
    private void addTab1(){
        //Listview Tab1:
        arrBorrowList = new ArrayList<>();
        adapterTab1=new Tab1ListViewAdapter(LibraryTabActivity.this,R.layout.tab1_tab2_listviewitem,arrBorrowList);
        lvTab1_BorrowList=findViewById(R.id.lvTab1_BorrowList);
        lvTab1_BorrowList.setAdapter(adapterTab1);


        //Buttons:
        btnTab1_Confirm=findViewById(R.id.btnTab1_Confirm);
        btnTab1_Delete=findViewById(R.id.btnTab1_Delete);

        //Camera:
        qr_scanner_view_Borrow = findViewById(R.id.qr_scanner_view_Borrow);
        CameraSettings s = new CameraSettings();
        s.setRequestedCameraId(0); // front/back/etc
        qr_scanner_view_Borrow.getBarcodeView().setCameraSettings(s);
        qr_scanner_view_Borrow.resume();

        //All book's information:
        hashMapTab1=new HashMap<>();
    }
    private void addTab2() {
        //Listview Tab2:
        arrReturnList = new ArrayList<>();
        adapterTab2=new Tab1ListViewAdapter(LibraryTabActivity.this,R.layout.tab1_tab2_listviewitem,arrReturnList);
        lvTab2_ReturnList=findViewById(R.id.lvTab2_ReturnList);
        lvTab2_ReturnList.setAdapter(adapterTab2);

        //Buttons:
        btnTab2_Confirm=findViewById(R.id.btnTab2_Confirm);
        btnTab2_Delete=findViewById(R.id.btnTab2_Delete);

        //Camera:
        qr_scanner_view_Return = findViewById(R.id.qr_scanner_view_Return);
        CameraSettings s1 = new CameraSettings();
        s1.setRequestedCameraId(0); // front/back/etc
        qr_scanner_view_Return.getBarcodeView().setCameraSettings(s1);

        arrBorrowBookKeySet=new ArrayList<>();

    }
    private void addTab3() {
        lvTab3_BookSearch=findViewById(R.id.lvTab3_BookSearch);
        txtBookSearch_Tab3=findViewById(R.id.txtBookSearch_Tab3);
        btnBookSearch_Tab3=findViewById(R.id.btnBookSearch_Tab3);

        arrAutoCompleteTextView=new ArrayList<String>();


        adapterAutoCompleteTextView=new ArrayAdapter<>(
                LibraryTabActivity.this,
                android.R.layout.simple_list_item_1,
                arrAutoCompleteTextView);

        txtBookSearch_Tab3.setAdapter(adapterAutoCompleteTextView);

        arrFoundBook=new ArrayList<>();
        tab3ListViewAdapter=new Tab3ListViewAdapter(
                LibraryTabActivity.this,
                R.layout.tab3_listviewitem,
                arrFoundBook);
        lvTab3_BookSearch.setAdapter(tab3ListViewAdapter);
        hashMap=new HashMap<String,Book>();
    }
    private void addTab4() {
        txtTab4_Phone=findViewById(R.id.txtTab4_Phone);
        txtTab4_Email=findViewById(R.id.txtTab4_Email);
        txtTab4_Name=findViewById(R.id.txtTab4_Name);
        btnTab4_ChangePassword=findViewById(R.id.btnTab4_ChangePassword);
        lvTab4_BorringBook=findViewById(R.id.lvTab4_BorringBook);
        arrBook_Tab4=new ArrayList<Book_User_Borrow>();
        tab4ListViewAdapter=new Tab4ListViewAdapter(LibraryTabActivity.this,R.layout.tab4_listviewitem,arrBook_Tab4);
        lvTab4_BorringBook.setAdapter(tab4ListViewAdapter);
        btnTab4_SignOut=findViewById(R.id.btnTab4_SignOut);
    }
    private void uploadGeneralInformation() {
        //Borrow Information for Tab 4
        Book_User_BorrowReference.orderByChild("email").equalTo(firebaseUser.getEmail()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                arrBook_Tab4.add(dataSnapshot.getValue(Book_User_Borrow.class));
                arrBorrowBookKeySet.add(dataSnapshot.getKey());
                tab4ListViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                for(Book_User_Borrow book_user_borrow:arrBook_Tab4){
                    if(book_user_borrow.getISBN().equals(deletedBook.getISBN())){
                        arrBook_Tab4.remove(book_user_borrow);
                        tab4ListViewAdapter.notifyDataSetChanged();
                        break;
                    }
                }

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // User Information:
        userReference.orderByChild("email").equalTo(firebaseUser.getEmail()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    User user=snapshot.getValue(User.class);
                    txtTab4_Name.setText(user.getName());
                    txtTab4_Phone.setText(user.getPhone());
                    txtTab4_Email.setText(user.getEmail());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // Update maximum borrowing book quantity allowed:
        allowedMaximumBorrowingBookReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allowedMaximumBorrowingBookQuantity=dataSnapshot.getValue(Long.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Upload autocomplete text view and hash map for searching information in Tab 3
        uploadTab3Information();
    }
    private void uploadTab3Information() {
        hashMap=new HashMap<>();
        bookReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot bookSnapshot: dataSnapshot.getChildren()){
                    Book book1=bookSnapshot.getValue(Book.class);
                    arrAutoCompleteTextView.add(book1.getGenre());
                    arrAutoCompleteTextView.add(book1.getBookName());
                    arrAutoCompleteTextView.add(book1.getISBN());
                    arrAutoCompleteTextView.add(book1.getAuthor());
                    arrAutoCompleteTextView.add(String.valueOf(book1.getPublishedYear()));
                    arrAutoCompleteTextView.add(String.valueOf(book1.getPublishedYear()));
                    adapterAutoCompleteTextView.notifyDataSetChanged();
                    String s=book1.getISBN()+", "+book1.getBookName()+", "+book1.getAuthor()+", "+book1.getGenre()+", "+book1.getPublishedYear()+"";
                    hashMap.put(s,book1);
                    //Input book information into the following hash map for querying
                    hashMapTab1.put(book1.getISBN(),book1);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    //EVENTS:
    private void addEvents() {
        addTabEvents();
        addTab1Events();
        addTab2Events();
        addTab3Events();
        addTab4Events();
    }
    private void addTabEvents() {
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if(tabId=="t1"){
                    qr_scanner_view_Return.pause();
                    qr_scanner_view_Borrow.resume();
                }
                if(tabId=="t2"){
                    qr_scanner_view_Borrow.pause();
                    qr_scanner_view_Return.resume();
                }
            }

        });
    }
    private void addTab1Events() {
        //SCANNING EVENT:
        qr_scanner_view_Borrow.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                String sResult = result.getText().toString();
                qr_scanner_view_Borrow.pause();
                //Input found book into the list view in tab 1
                getBorrowBook_Tab1(sResult);
            }
            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }
        });
        //DELETE BUTTON
        btnTab1_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrBorrowList.clear();
                adapterTab1.notifyDataSetChanged();
            }
        });
        //CONFIRM BUTTON
        btnTab1_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmBorrowBook_Tab1();
            }
        });
    }
    private void addTab2Events() {
        btnTab2_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrReturnList.clear();
                adapterTab2.notifyDataSetChanged();
            }
        });

        qr_scanner_view_Return.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                String sResult = result.getText().toString();
                qr_scanner_view_Return.pause();
                getReturnBook_Tab2(sResult);

            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }
        });

        btnTab2_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            confirmReturnBook_Tab2();
            }
        });
    }
    private void addTab3Events() {
        btnBookSearch_Tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrFoundBook.clear();
                for(String s:hashMap.keySet()){
                    if(s.contains(txtBookSearch_Tab3.getText().toString())){
                        arrFoundBook.add(hashMap.get(s));
                        tab3ListViewAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
    private void addTab4Events() {
        btnTab4_ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LibraryTabActivity.this,ChangePassword.class);
                startActivity(intent);
            }
        });
        btnTab4_SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent=new Intent(LibraryTabActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //TAB 1's FUNCTIONS:
    private void confirmBorrowBook_Tab1() {
            qr_scanner_view_Borrow.pause();
            String currentDate=getCurrentDate_Tab1();
            int lastIndexOfEmail=firebaseUser.getEmail().lastIndexOf('@');
            subStringEmail=firebaseUser.getEmail().substring(0,lastIndexOfEmail);
            for(Book book:arrBorrowList){
                String s=generateRandomString_Tab1();
                Book_User_Borrow book_user_borrow=new Book_User_Borrow(firebaseUser.getEmail(),book.getBookName(),book.getISBN(),currentDate);
                String childName=book.getISBN()+subStringEmail+s;
                Book_User_BorrowReference.child(childName).setValue(book_user_borrow);
                minusBookQuantity_Tab1(book);
        }
            arrBorrowList.clear();
            adapterTab1.notifyDataSetChanged();
            Toast.makeText(LibraryTabActivity.this,"BOOK BORROWED SUCCESSFULLY!",Toast.LENGTH_SHORT).show();
            qr_scanner_view_Borrow.resume();
    }
    private void minusBookQuantity_Tab1(Book book) {
        int currentQuantity=book.getQuantity();
        currentQuantity-=1;
        book.setQuantity(currentQuantity);
        bookReference.child(book.getISBN()).setValue(book);
    }
    private String getCurrentDate_Tab1() {
        Calendar calendar=Calendar.getInstance();
        Date date=calendar.getTime();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(date);
    }
    private String generateRandomString_Tab1() {
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }
    private void getBorrowBook_Tab1(String sResult) {
        Book book=hashMapTab1.get(sResult);
        if(book==null){
            Toast.makeText(LibraryTabActivity.this,"BOOK NOT FOUND!", Toast.LENGTH_SHORT).show();
            qr_scanner_view_Borrow.resume();
        }else{
            if(arrBook_Tab4.size()+arrBorrowList.size()>allowedMaximumBorrowingBookQuantity){
                Toast.makeText(LibraryTabActivity.this,"BOOK QUANTITY LIMIT EXCEEDED!", Toast.LENGTH_SHORT).show();
                qr_scanner_view_Borrow.resume();
            }else{
                arrBorrowList.add(book);
                adapterTab1.notifyDataSetChanged();
                qr_scanner_view_Borrow.resume();
            }
        }
    }

    //TAB 2's FUNCTIONS:
    private void getReturnBook_Tab2(String sResult) {
        Book book=hashMapTab1.get(sResult);
        if(book==null){
            Toast.makeText(LibraryTabActivity.this,"BOOK NOT FOUND!", Toast.LENGTH_SHORT).show();
            qr_scanner_view_Return.resume();
        }else{
                arrReturnList.add(book);
                adapterTab2.notifyDataSetChanged();
                qr_scanner_view_Return.resume();
        }
    }
    private void confirmReturnBook_Tab2() {
        int flag=0;
        for(Book book:arrReturnList){
            for(String key:arrBorrowBookKeySet){
                if(key.contains(book.getISBN())||key.contains(subStringEmail)){
                    deletedBook=new Book();
                    deletedBook=book;
                    Book_User_BorrowReference.child(key).removeValue();
                    arrReturnList.remove(book);
                    flag=1;
                    Toast.makeText(LibraryTabActivity.this,book.getBookName() + " DELETED SUCCESSFULLY!",Toast.LENGTH_SHORT).show();
                    break;
                }
                if(flag==0){Toast.makeText(LibraryTabActivity.this,"YOU ARE NOT BORROWING THIS BOOK!",Toast.LENGTH_SHORT).show();}
            }
            flag=0;
        }
    }











}
