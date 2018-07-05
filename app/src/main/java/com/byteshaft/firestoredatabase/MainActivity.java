package com.byteshaft.firestoredatabase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_AUTHOR = "author";
    private static final String KEY_QUOTE = "quote";
    private Button addButton;
    private EditText etQuote;
    private EditText etAuthor;


    private Button getButton;
    private TextView tvQuote;
    private TextView tvAuthor;

    private String quote;
    private String author;

    // firestore refs
    private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("sampleData/ins");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getButton = findViewById(R.id.fetch_data);
        tvQuote = findViewById(R.id.tv_quote);
        tvAuthor = findViewById(R.id.tv_author);

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });


        etQuote = findViewById(R.id.et_quote);
        etAuthor = findViewById(R.id.et_author);
        addButton = findViewById(R.id.add);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveQuote();
            }
        });
    }

    private void getData() {
        mDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String myQuote = documentSnapshot.getString(KEY_QUOTE);
                String myAuthor = documentSnapshot.getString(KEY_AUTHOR);

                tvQuote.setText(myQuote);
                tvAuthor.setText(myAuthor);
                Log.wtf("ok", "text");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.wtf("ok", "failadsfkadsf");
            }
        });
    }

    private void saveQuote() {
        quote = etQuote.getText().toString();
        author = etAuthor.getText().toString();
        if (quote.isEmpty() || author.isEmpty()) {
            return;
        }

        Map<String, Object> dataToSave = new HashMap<>();
        dataToSave.put(KEY_AUTHOR, author);
        dataToSave.put(KEY_QUOTE, quote);
        mDocRef.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.wtf("ok", "saved");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.wtf("ok", "fail");
            }
        });
    }
}
