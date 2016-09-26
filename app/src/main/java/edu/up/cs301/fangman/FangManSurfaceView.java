package edu.up.cs301.fangman;

        import android.content.Context;
        import android.content.res.Resources;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.graphics.Path;
        import android.graphics.RectF;
        import android.util.AttributeSet;
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
 * @author **** put your name here ****
 * @author Steven R. Vegdahl
 * @version **** put date of completion here ****
 */
public class FangManSurfaceView extends SurfaceView
{

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


        // draw a random word from our list onto the Canvas
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        //p.setTextSize(120);
        //Random ran = new Random();
        //int idx = ran.nextInt(words.length);
        //c.drawText(words[idx], 100, 600, p);
        //c.drawCircle(100, 600,100, p);

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



        //Print string
        System.out.print(words[idx]);

        //c.drawCircle(100, 600,100, p);


        //Draw a line for each letter.
        for (int i = 0; i < words[idx].length(); i++)
        {
            Path line = new Path();
            line.moveTo(100 + (i*40), 935);
            line.addRect(100 + (i*50),935, 140+(i*50), 945, Path.Direction.CW);
            line.close();
            c.drawPath(line, p);
        }

        for (int i = 0; i < wordBool.length; i++)
        {
            p.setTextSize(70);
            if (wordBool[i])
            {
                int letterInt = wordInt[i];
                c.drawText(String.valueOf(alphabet[letterInt]), 100 + (i*50) , 930, p);
            }
        }

        if (!allTrue)
        {
            if (numIncorrect == 0)
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

                //right hair
                //RectF hairRR = new RectF(500, 500, 740, 300);
                //hair.setStrokeWidth(5);
                //hair.setStyle(Paint.Style.STROKE);
                //c.drawArc(hairRR, 180, 360, false, p);

                //cheeks
                c.drawCircle(275, 525, 50, cheeks);
                c.drawCircle(625, 525, 50, cheeks);


                Path allHair = new Path();
                allHair.moveTo(160, 300);
                RectF hairShapeL = new RectF(160, 300, 450, 450);
                allHair.addArc(hairShapeL, 180, 180);
                RectF hairShapeR = new RectF(450,300,740,450);
                allHair.addArc(hairShapeR, 180, 180);
                RectF hairShapeT = new RectF(150, 150, 750, 750);
                allHair.addArc(hairShapeT, 200, 140);
                //allHair.close();
                hairColor.setStrokeWidth(5);
                hairColor.setStyle(Paint.Style.STROKE);
                c.drawPath(allHair, hairColor);


                //c.drawArc(hairShapeL, 180, 180, false, p);

                //c.drawArc(hairShapeR, 180, 180, false, p);



//                RectF hairL = new RectF(160,300,450,450);
//
//                p.setStrokeWidth(5);
//                p.setStyle(Paint.Style.STROKE);
//                c.drawArc(hairL, 180, 180, true, p);





//                RectF hairT = new RectF(740, 300, 160, 300);
//                c.drawArc(hairT, 0, 180, true, hair);



            }
            if (numIncorrect == 1)
            {
                c.drawCircle(550, 450, 300, p);
            }
            if (numIncorrect == 2)
            {
                c.drawCircle(550, 450, 300, p);
            }
            if (numIncorrect == 3)
            {
                c.drawCircle(550, 450, 300, p);
            }
            if (numIncorrect == 4)
            {
                c.drawCircle(550, 450, 300, p);
            }
            if (numIncorrect == 5)
            {
                c.drawCircle(550, 450, 300, p);
            }
            if (numIncorrect == 6)
            {
                c.drawCircle(550, 450, 300, p);
            }
        }



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
        boolean changed = false;
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

