package edu.up.cs301.fangman;

        import android.content.Context;
        import android.content.res.Resources;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.graphics.Path;
        import android.graphics.RectF;
        import android.util.AttributeSet;
        import android.util.Log;
        import android.view.SurfaceView;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.util.ArrayList;
        import java.util.Random;

/**
 * SurfaceView for playing "Fang Man" game.
 *
 * @author **** Paige McKinney ****
 * @author Steven R. Vegdahl
 * @version **** 9/26/16 ****
 */
public class FangManSurfaceView extends SurfaceView
{

    private static int gamesWon = 0;


    //This will keep track of all the incorrect guesses the user has made.
    private int numIncorrect = 0;

    //This will keep track of whether or not the user has correctly guessed the word.
    private boolean allTrue = false;


    private char[] alphabet = {
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u',
            'v','w','x','y','z'
    };

    //This will contain all the indexes of where the letters are in the alphabet.
    //for example, if the word was at, the array would contain 0,19.
    private int[] wordInt;

    //This array will keep track of what letters in the word the user has guessed.
    private boolean[] wordBool;

    //Index of the word
    private int idx;

    private int[] guesses = new int[26];
    private int numGuesses = 0;

    private boolean changed = false;

    // the array of many English words, from which one is picked. This is
    // read in from a resource file.
    private static String[] words;

    // constructor, modeled after superclass constructor
    public FangManSurfaceView(Context context)
    {
        super(context);
        init(context);
        //
        //pickWord();
    }

    // constructor, modeled after superclass constructor
    public FangManSurfaceView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
        //
        //pickWord();
    }

    // constructor, modeled after superclass constructor
    public FangManSurfaceView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);
        //
        //pickWord();
    }

    /**
     * Common constructor code -- initializes instance variables
     * @param context the context object, from the activity
     */
    private void init(Context context) {

        // read the words from the resource file
        if (words == null)
        {
            words = readWordsFromResourceFile(context);
        }
    }

    /**
     * method that draws the image for our view
     *
     * @param c the canvas on which to draw
     */
    @Override
    public void onDraw(Canvas c) {

        Log.i("word", words[idx]);

        // draw a random word from our list onto the Canvas
        Paint p = new Paint();
        p.setColor(Color.BLACK);

        //Create all the different colors for the fang man.
        Paint face = new Paint();
        face.setColor(Color.rgb(255, 245, 223));

        Paint noseC = new Paint();
        noseC.setColor(Color.rgb(255, 233, 188));

        Paint cheeks = new Paint();
        cheeks.setColor(Color.argb(50, 255, 132, 182));

        Paint bow = new Paint();
        bow.setColor(Color.rgb(40, 121, 48));

        Paint fangCol = new Paint();
        fangCol.setColor(Color.WHITE);

        Paint hairColor = new Paint();
        hairColor.setColor(Color.BLACK);

        Paint d = new Paint();
        d.setColor(Color.WHITE);


        //Draw a line for each letter.
        for (int i = 0; i < words[idx].length(); i++)
        {
            Path line = new Path();
            line.moveTo(100 + (i*40), 935);
            line.addRect(100 + (i*50),935, 140+(i*50), 945, Path.Direction.CW);
            line.close();
            c.drawPath(line, d);
        }

        //Prints the letters the user has guessed if correct.
        for (int i = 0; i < wordBool.length; i++)
        {
            d.setTextSize(70);
            if (wordBool[i])
            {
                int letterInt = wordInt[i];
                c.drawText(String.valueOf(alphabet[letterInt]), 100 + (i*50) , 930, d);
            }
        }

        c.drawText("Letters guessed: ", 100, 1100, d);
        //Draws the letters guessed:
        for (int i = 0; i < numGuesses; i++)
        {
            int letterI = guesses[i];
            c.drawText(String.valueOf(alphabet[letterI]) + " ", 640 + (i*50), 1100, d);
        }
        c.drawText("Games Won: ", 100, 1200, d);
        c.drawText(String.valueOf(gamesWon), 510, 1200, d);


        if (!allTrue)
        {
            if (numIncorrect == 0)
            {

            }
            if (numIncorrect == 1)
            {
                c.drawCircle(450, 450, 300, face);

                //ears
                c.drawCircle(160, 450, 40, face);
                c.drawCircle(740, 450, 40, face);

            }
            if (numIncorrect == 2)
            {
                c.drawCircle(450, 450, 300, face);

                //ears
                c.drawCircle(160, 450, 40, face);
                c.drawCircle(740, 450, 40, face);

                //eyes
                c.drawCircle(400, 420, 20, p);
                c.drawCircle(500, 420, 20, p);

                //nose
                RectF nose = new RectF(420, 470, 480, 500);
                noseC.setStrokeWidth(5);
                noseC.setStyle(Paint.Style.STROKE);
                c.drawArc(nose, 0, 180, false, noseC);


                //mouth
                RectF mouth = new RectF(380, 480, 520, 600);
                p.setStrokeWidth(5);
                p.setStyle(Paint.Style.STROKE);
                c.drawArc(mouth, 0, 180, false, p);

            }
            if (numIncorrect == 3)
            {
                c.drawCircle(450, 450, 300, face);

                //ears
                c.drawCircle(160, 450, 40, face);
                c.drawCircle(740, 450, 40, face);

                //eyes
                c.drawCircle(400, 420, 20, p);
                c.drawCircle(500, 420, 20, p);

                //nose
                RectF nose = new RectF(420, 470, 480, 500);
                noseC.setStrokeWidth(5);
                noseC.setStyle(Paint.Style.STROKE);
                c.drawArc(nose, 0, 180, false, noseC);


                //mouth
                RectF mouth = new RectF(380, 480, 520, 600);
                p.setStrokeWidth(5);
                p.setStyle(Paint.Style.STROKE);
                c.drawArc(mouth, 0, 180, false, p);

                //cheeks
                c.drawCircle(275, 525, 50, cheeks);
                c.drawCircle(625, 525, 50, cheeks);

            }
            if (numIncorrect == 4)
            {
                c.drawCircle(450, 450, 300, face);

                //ears
                c.drawCircle(160, 450, 40, face);
                c.drawCircle(740, 450, 40, face);

                //eyes
                c.drawCircle(400, 420, 20, p);
                c.drawCircle(500, 420, 20, p);

                //nose
                RectF nose = new RectF(420, 470, 480, 500);
                noseC.setStrokeWidth(5);
                noseC.setStyle(Paint.Style.STROKE);
                c.drawArc(nose, 0, 180, false, noseC);


                //fangs
                Path fang1 = new Path();
                fang1.moveTo(390, 576);
                fang1.lineTo(393, 660);
                fang1.lineTo(440, 603);
                fang1.close();
                c.drawPath(fang1, fangCol);

                p.setStyle(Paint.Style.STROKE);
                c.drawPath(fang1, p);

                Path fang2 = new Path();
                fang2.moveTo(510, 576);
                fang2.lineTo(507, 660);
                fang2.lineTo(460, 603);
                fang2.close();
                c.drawPath(fang2, fangCol);

                p.setStyle(Paint.Style.STROKE);
                c.drawPath(fang2, p);

                //mouth
                RectF mouth = new RectF(380, 480, 520, 600);
                p.setStrokeWidth(5);
                p.setStyle(Paint.Style.STROKE);
                c.drawArc(mouth, 0, 180, false, p);

                //cheeks
                c.drawCircle(275, 525, 50, cheeks);
                c.drawCircle(625, 525, 50, cheeks);

            }
            if (numIncorrect == 5)
            {
                c.drawCircle(450, 450, 300, face);

                //ears
                c.drawCircle(160, 450, 40, face);
                c.drawCircle(740, 450, 40, face);

                //eyes
                c.drawCircle(400, 420, 20, p);
                c.drawCircle(500, 420, 20, p);

                //nose
                RectF nose = new RectF(420, 470, 480, 500);
                noseC.setStrokeWidth(5);
                noseC.setStyle(Paint.Style.STROKE);
                c.drawArc(nose, 0, 180, false, noseC);


                //fangs
                Path fang1 = new Path();
                fang1.moveTo(390, 576);
                fang1.lineTo(393, 660);
                fang1.lineTo(440, 603);
                fang1.close();
                c.drawPath(fang1, fangCol);

                p.setStyle(Paint.Style.STROKE);
                c.drawPath(fang1, p);

                Path fang2 = new Path();
                fang2.moveTo(510, 576);
                fang2.lineTo(507, 660);
                fang2.lineTo(460, 603);
                fang2.close();
                c.drawPath(fang2, fangCol);

                p.setStyle(Paint.Style.STROKE);
                c.drawPath(fang2, p);

                //mouth
                RectF mouth = new RectF(380, 480, 520, 600);
                p.setStrokeWidth(5);
                p.setStyle(Paint.Style.STROKE);
                c.drawArc(mouth, 0, 180, false, p);

                //cheeks
                c.drawCircle(275, 525, 50, cheeks);
                c.drawCircle(625, 525, 50, cheeks);

                //hair
                Path allHair = new Path();
                allHair.moveTo(165, 300);
                RectF hairShapeL = new RectF(165, 300, 450, 450);
                allHair.arcTo(hairShapeL, 180, 180, false);
                RectF hairShapeR = new RectF(450,300,740,450);
                allHair.arcTo(hairShapeR, 180, 180, false);
                RectF hairShapeT = new RectF(149, 148, 750, 750);
                allHair.arcTo(hairShapeT, -10, -160, false);
                c.drawPath(allHair, hairColor);

                //This is the last guess, so the user has LOST.
                d.setStyle(Paint.Style.FILL);
                d.setTextSize(100);
                c.drawText("You lose!", 760, 410, d);
                d.setTextSize(75);
                c.drawText("Would you",775, 480, d);
                c.drawText("like to", 835, 540, d);
                c.drawText("play again?", 775, 600, d);
                for (int i = 0; i < wordBool.length; i++)
                {

                    d.setTextSize(70);
                    int letterInt = wordInt[i];
                    c.drawText(String.valueOf(alphabet[letterInt]), 100 + (i*50) , 930, d);

                }

            }
        }
        if (allTrue)
        {
            d.setStyle(Paint.Style.FILL);
            d.setTextSize(100);
            c.drawText("You win!", 760, 410, d);
            d.setTextSize(75);
            c.drawText("Would you",775, 480, d);
            c.drawText("like to", 835, 540, d);
            c.drawText("play again?", 775, 600, d);

        }



    }

    public int getNumIncorrect()
    {
        return numIncorrect;
    }

    public boolean getChanged()
    {
        return changed;
    }

    public boolean getAllTrue()
    {
        return allTrue;
    }

    /**
     * This method will pick a word and set up all the instance variables.
     */
    public void pickWord()
    {
        Random ran = new Random();
        idx = ran.nextInt(words.length);
        wordInt = new int[words[idx].length()];
        wordBool = new boolean[words[idx].length()];
        for(int k = 0; k < wordBool.length; k++)
        {
            wordBool[k] = false;
        }

        //Sets a position in wordInt to the index in the alphabet where that character is.
        for (int i = 0; i < alphabet.length; i++)
        {
            for (int j = 0; j < words[idx].length(); j++)
            {
                if(words[idx].charAt(j) == alphabet[i])
                {
                    wordInt[j] = i;
                }
            }
        }
    }


    /**
     * This method will be called every time the user selects a letter.  It will change the
     * number of incorrect guesses, what letters the user has picked, and it will then call
     * wordGuessed() to see if the word has been correctly guessed.
     * @param letterNum
     */
    public void letterSelected(int letterNum)
    {
        changed = false;
        guesses[numGuesses] = letterNum;
        numGuesses = numGuesses+1;
        for (int i = 0; i < wordInt.length; i++)
        {
            if (letterNum == wordInt[i])
            {
                wordBool[i] = true;
                changed = true;
            }
        }
        if (!changed)
        {
            numIncorrect = numIncorrect+1;
        }
        wordGuessed();
    }


    /**
     * This will loop through an array which contains trues and falses illustrating which letters
     * have been correctly guessed by the user.  If the array is all true, then the user has
     * correctly guessed the word.
     */
    public void wordGuessed()
    {
        for (int i = 0; i < wordBool.length; i++)
        {
            if (wordBool[i])
            {
                allTrue = true;
            }
            else
            {
                allTrue = false;
                break;
            }
        }
        if (allTrue)
        {
            gamesWon = gamesWon+1;
        }
    }






    /**
     * reads list of game-words from the resource file
     *
     * @param context the context object
     * @return the array of words
     */
    private String[] readWordsFromResourceFile(Context context) {

        // create input reader for the word file
        InputStream is = context.getResources().openRawResource(R.raw.word_list);
        InputStreamReader ir = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(ir);

        // create array-list for holding the words
        ArrayList<String> lines = new ArrayList<String>();

        // read each line as a word; add each to the array-list
        try {
            for (;;) {
                String line = br.readLine();
                if (line == null) break;
                lines.add(line.trim());
            }
            br.close();
        } catch (IOException e) {
        }
        if (lines.size() == 0) {
            // if we did not get any words, put a dummy "word" in
            lines.add("ERROR READING FILE");
        }

        // return the array-version of the word
        return lines.toArray(new String[0]);
    }
}

