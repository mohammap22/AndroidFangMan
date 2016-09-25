package edu.up.cs301.fangman;

        import android.content.Context;
        import android.content.res.Resources;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.graphics.Path;
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
    private static int numIncorrect = 0;

    //This will keep track of whether or not the user has correctly guessed the word.
    private static boolean allTrue = false;


    private static char[] alphabet = {
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U',
            'V','W','X','Y','Z'
    };

    //This will contain all the indexes of where the letters are in the alphabet.
    //for example, if the word was at, the array would contain 0,19.
    private static int[] wordInt;

    //This array will keep track of what letters in the word the user has guessed.
    private static boolean[] wordBool;

    //Index of the word
    private static int idx;


    // the array of many English words, from which one is picked. This is
    // read in from a resource file.
    private static String[] words;

    // constructor, modeled after superclass constructor
    public FangManSurfaceView(Context context)
    {
        super(context);
        init(context);
        //
        pickWord();
    }

    // constructor, modeled after superclass constructor
    public FangManSurfaceView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
        //
        pickWord();
    }

    // constructor, modeled after superclass constructor
    public FangManSurfaceView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);
        //
        pickWord();
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

        /*wordInt = new int[words[idx].length()];
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
        }*/
        if (!allTrue)
        {
            if (numIncorrect == 1)
            {
                c.drawCircle(100, 600,100, p);
            }
            if (numIncorrect == 2)
            {
                c.drawCircle(100, 600,200, p);
            }
        }



    }

    /**
     * This method will pick a word and set up all the instance variables.
     */
    public static void pickWord()
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
    public static void letterSelected(int letterNum)
    {
        for (int i = 0; i < wordInt.length; i++)
        {
            if (letterNum == wordInt[i])
            {
                wordBool[i] = true;
            }
            else
            {
                numIncorrect = numIncorrect++;

            }
        }
        wordGuessed();

    }


    /**
     * This will loop through an array which contains trues and falses illustrating which letters
     * have been correctly guessed by the user.  If the array is all true, then the user has
     * correctly guessed the word.
     */
    public static void wordGuessed()
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

