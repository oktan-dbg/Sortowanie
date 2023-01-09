package com.example.sortowanie

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.time.Instant
import java.util.*


class MainActivity : AppCompatActivity() {
    //var progress_bar = findViewById<ProgressBar>(R.id.progressBar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Buttons
        var wylosuj_btn = findViewById<Button>(R.id.wylosuj_btn);
        var segreguj_btn = findViewById<Button>(R.id.segreguj_btn);

        //Edit Texts
        var ilosc = findViewById<EditText>(R.id.ilosc_inp)
        var rozmiar = findViewById<EditText>(R.id.rozmiar_inp)

        //Text Views
        var ilosc_txt = findViewById<TextView>(R.id.ilosc_txt)
        var rozmiar_txt = findViewById<TextView>(R.id.rozmiar_txt)
        var dane_surowe_txt = findViewById<TextView>(R.id.dane_surowe_txt)
        var dane_posegregowane_txt = findViewById<TextView>(R.id.dane_posegregowane_txt)
        var czas_txt = findViewById<TextView>(R.id.czas_number_txt)

        //progress bar
        var progress_bar = findViewById<ProgressBar>(R.id.progressBar)

        //wylosowana tablica
        var amplititudes  = IntArray(10) { (0..5).random() }.asList()

        wylosuj_btn.setOnClickListener{
            if((ilosc.text.isNullOrEmpty() or rozmiar.text.isNullOrEmpty())and(ilosc.text.isBlank() or rozmiar.text.isBlank())==false){
                amplititudes  = IntArray(Integer.parseInt(ilosc.text.toString())) { (0..Integer.parseInt(rozmiar.text.toString())).random() }.asList()
                //val randomValues = List(10) { Random.nextInt(0, 100) }
                dane_surowe_txt.text= " ";
                for (i in 0 until Integer.parseInt(ilosc.text.toString())) {
                    dane_surowe_txt.text=dane_surowe_txt.text.toString()+amplititudes.get(i).toString()+","
                }
                progress_bar.max= amplititudes.size-1;
                //dane_surowe_txt.text=amplititudes.get(0).toString()
            }else{
                val toast = Toast.makeText(applicationContext, "Uzupełnij dane!!!!", Toast.LENGTH_LONG)
                toast.show()
            }
        }

        segreguj_btn.setOnClickListener {
            dane_posegregowane_txt.text = " ";
            val begin = Instant.now().toEpochMilli()
            dane_posegregowane_txt.text=mergeSort(amplititudes).toString();
            val end = Instant.now().toEpochMilli()
            czas_txt.text = (end-begin).toString()+"mls"
        }
    }
    fun mergeSort(list: List<Int>): List<Int> {

        if (list.size <= 1) {
            return list
        }

        val middle = list.size / 2
        var left = list.subList(0,middle);
        var right = list.subList(middle,list.size);

        return merge(mergeSort(left), mergeSort(right))

    }
    fun merge(left: List<Int>, right: List<Int>): List<Int>  {

        var progress_bar = findViewById<ProgressBar>(R.id.progressBar)
        var indexLeft = 0
        var indexRight = 0
        var newList : MutableList<Int> = mutableListOf()

        while (indexLeft < left.count() && indexRight < right.count()) {
            if (left[indexLeft] <= right[indexRight]) {
                newList.add(left[indexLeft])
                indexLeft++
            } else {
                newList.add(right[indexRight])
                indexRight++
                progress_bar.progress++;
            }
        }

        while (indexLeft < left.size) {
            newList.add(left[indexLeft])
            indexLeft++
        }

        while (indexRight < right.size) {
            newList.add(right[indexRight])
            indexRight++
            progress_bar.progress++;
        }
        return newList;
    }
}