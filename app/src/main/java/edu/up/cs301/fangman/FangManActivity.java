package edu.up.cs301.fangman;

import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * activity class for the Fang Man game
 *
 * @author **** Paige McKinney ****
 * @author **** 9/26/16 ****
 *
 */
public class FangManActivity extends AppCompatActivity implements View.OnClickListener
{

    private FangManSurfaceView fang;


    MediaPlayer bigLaugh;
    MediaPlayer chillingChallenge;
    MediaPlayer followHome;
    MediaPlayer laugh;
    MediaPlayer welcome;
    MediaPlayer dismay;

    //This array will store all of the ideas for the letter buttons,
    //allowing for easier access later.
    private int[] buttonIds = {
            R.id.aButton,
            R.id.bButton,
            R.id.cButton,
            R.id.dButton,
            R.id.eButton,
            R.id.fButton,
            R.id.gButton,
            R.id.hButton,
            R.id.iButton,
            R.id.jButton,
            R.id.kButton,
            R.id.lButton,
            R.id.mButton,
            R.id.nButton,
            R.id.oButton,
            R.id.pButton,
            R.id.qButton,
            R.id.rButton,
            R.id.sButton,
            R.id.tButton,
            R.id.uButton,
            R.id.vButton,
            R.id.wButton,
            R.id.xButton,
            R.id.yButton,
            R.id.zButton,
    };


    int selectColor = Color.argb(230, 0, 0, 0); //The semi-transparent black the buttons will be set
    //to upon being selected.


    //Array containing all the letters in the alphabet.
    private boolean[] picked = new boolean[26];




    /**
     * standard 'onCreate' method
     *
     * @param savedInstanceState saved instance state
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        // invoke superclass constructor
        super.onCreate(savedInstanceState);



        // load layout
        setContentView(R.layout.activity_fang_man);

        //Loop through the buttonId array and set each one as an onClickListener.
        for (int i = 0; i < buttonIds.length; i++)
        {
            Button letterBut = (Button)findViewById(buttonIds[i]);
            letterBut.setOnClickListener(this);
        }

        Button restartBut = (Button)findViewById(R.id.restartButton);
        restartBut.setOnClickListener(this);

        for (int j = 0; j < picked.length; j++)
        {
            picked[j] = false; //If the button has not been selected by the user it will be
            //initially set to false.  It will be set to true when the user picks the button.

        }

        fang = (FangManSurfaceView)findViewById(R.id.main_view);
        fang.pickWord();

        bigLaugh = MediaPlayer.create(this, R.raw.big_laugh);
        chillingChallenge = MediaPlayer.create(this, R.raw.chilling_challenge);
        followHome = MediaPlayer.create(this, R.raw.follow_you_home);
        laugh = MediaPlayer.create(this, R.raw.laugh);
        welcome = MediaPlayer.create(this, R.raw.welcome);
        dismay = MediaPlayer.create(this, R.raw.dismaying_observation);





    }

    /**
     * standard create-options menu method
     *
     * @param menu the menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fang_man, menu);
        return true;
    }

    /**
     * standard callback method for menu-item selection
     *
     * @param item the item selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view)
    {
        for (int ind = 0; ind < buttonIds.length; ind++)
        {
            //if the button selected matches one of the buttons in the buttonIds array,
            //it will change the color of that button and mark it as selected.
            if (view == findViewById(buttonIds[ind]))
            {
                buttonSelect(ind);

                fang.letterSelected(ind); //This will call a method in surface view
                //that will change the number of incorrect guesses and see what letters the user
                //has guessed.  It will also call wordGuessed() which will evaluate an array
                //to see if the user has guessed the entire word.

                boolean correct = fang.getChanged();

                        fang.invalidate();

                if(correct)
                {
                    laugh.start();
                }
                else
                {
                    if (fang.getNumIncorrect() != 5)
                    {
                        bigLaugh.start();
                    }
                }

                if (fang.getNumIncorrect() == 5)
                {
                    for (int i = 0; i < buttonIds.length; i++)
                    {
                        buttonSelect(i);
                        followHome.start();
                    }
                }
                if(fang.getAllTrue())
                {
                    dismay.start();
                }

            }
            if (view == findViewById(R.id.restartButton))
            {
                recreate();
                chillingChallenge.start();
            }

        }


    }

    public void buttonSelect(int index)
    {
        picked[index] = true;
        findViewById(buttonIds[index]).setBackgroundColor(selectColor);
        findViewById(buttonIds[index]).setEnabled(false);
    }
}



