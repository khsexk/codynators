package com.example.summerproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

// 2021.08.01 khsexk: 체크인 구성
class CheckInActivity : AppCompatActivity() {
    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val myRef : DatabaseReference = database.getReference("Table Use Information")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkin)

        IntentIntegrator(this).initiateScan()

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Unit {
        val result : IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                // todo
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();


                // Firebase Realtime DB에 쓰기
                var useTable : String = result.getContents()
                var Table : TableData = TableData("", 1)
                var Map : HashMap<String, Any> = HashMap<String, Any>()
                Map.put(useTable, Table)

                myRef.updateChildren(Map)
                finish()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}