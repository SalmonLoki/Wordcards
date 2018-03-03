package com.wordcards.krovlya.wordcards

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.os.Bundle
import android.view.View
import android.support.design.widget.CollapsingToolbarLayout
import android.widget.ImageView
import com.squareup.picasso.Picasso
import android.support.design.widget.FloatingActionButton
import android.util.Log
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.one_card.view.*
import android.content.DialogInterface


class MainActivity : AppCompatActivity() {
    private var mToolbar: Toolbar? = null
    private var mCollapsingToolbar: CollapsingToolbarLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mToolbar = findViewById<View>(R.id.toolbar) as Toolbar
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mCollapsingToolbar = findViewById<View>(R.id.collapsing_toolbar) as CollapsingToolbarLayout
        mCollapsingToolbar!!.title = "Your cards"


        //TOOLBAR IMAGE
        val im = findViewById<View>(R.id.toolbarImage) as ImageView
        Picasso.with(this).load(R.drawable.captain).into(im)


        //ALERT DIALOG BUILDER
        AlertDialog.Builder(this)
                .setTitle("Nuke planet Jupiter?")
                .setMessage("Note that nuking planet Jupiter will destroy everything in there.")
                .setPositiveButton("Nuke") { dialog, which -> Log.d("MainActivity", "Sending atomic bombs to Jupiter") }
                .setNegativeButton("Abort") { dialog, which -> Log.d("MainActivity", "Aborting mission...") }


        //МАССИВ КАРТОЧЕК
        var allCards: ArrayList<View> = ArrayList()


        //FAB BUTTON
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        //FAB CLICK
        fab.setOnClickListener{
            //Получаем view из файла prompt.xml для диалогового окна:
            val li = LayoutInflater.from(this)
            val promptsView = li.inflate(R.layout.prompt, null)

            //Создаем AlertDialog
            val mDialogBuilder = AlertDialog.Builder(this)

            //Настраиваем prompt.xml для AlertDialog:
            mDialogBuilder.setView(promptsView)
            //Настраиваем отображение поля для ввода текста в открытом диалоге:
            val userInputRussian = promptsView.findViewById(R.id.input_text_russian) as EditText
            val userInputEnglish = promptsView.findViewById(R.id.input_text_english) as EditText

            mDialogBuilder
                    .setCancelable(false)

                    .setPositiveButton("Add") {
                        _, _ ->
                        val cardint = LayoutInflater.from(this)
                        val newCard = cardint.inflate(R.layout.one_card, null)
                        newCard.info_text_russian.text = userInputRussian.text
                        newCard.info_text_english.text = userInputEnglish.text

                        allCards.add(newCard)
                        linear_layout_main.addView(newCard)
                    }

                    .setNegativeButton("Cancel") {
                        _, _ ->
                    }

            val alertDialog = mDialogBuilder.create()
            alertDialog.show()
        }
    }

}
