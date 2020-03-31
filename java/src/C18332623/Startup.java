package C18332623;

import ddf.minim.AudioOutput;
import ddf.minim.Minim;
import ddf.minim.ugens.Oscil;
import ddf.minim.ugens.Waves;
import ie.tudublin.Visual;
import processing.core.PFont;

/*  This class will run a startup sequence complete with loading bar,
    parabolic frequency test and colour test
*/

public class Startup extends Visual
{
    Minim testSound;
    AudioOutput out;
    Oscil wave;
    PFont consoleFont;

    float cx;
    float cy;
    float border;

    float oscilAmp = 500;
    float lineX = 0;
    float lineY;
    float offset = 0.1f;

    boolean halfParab = false;
    boolean fullParab = false;
    boolean loaded = false;
    boolean welcomed = false;
    boolean start = false;

    float barW = 0;

    public void settings()
    {
        size(500,500);
        fullScreen();
    }

    public void setup()
    {
        testSound = new Minim(this);

        out = testSound.getLineOut();
        wave = new Oscil(oscilAmp, 0.5f, Waves.SINE);

        startMinim();
        loadAudio("Shadowflame.mp3");

        consoleFont = createFont("LUCON.TTF", 45);
        textFont(consoleFont);
        background(0);
        colorMode(HSB);

        cx = width / 2;
        cy = height / 2;
        border = width / 15;
    }

    public void draw()
    {
        checker();
    }

    public void checker()
    {
        if(welcomed == false)
        {
            welcome();
        }

        if(welcomed == true)
        {
            if(loaded == false)
            {
                loadingBar();
            }
            if(loaded == true)
            {
                if(fullParab == false)
                {
                    drawParabola();
                }
                if(fullParab == true)
                {
                    testSound.stop();
                    background(0);
                    if (start == false)
                    {
                        checksComplete();
                    }
                }
            }
        }
    }

    public void welcome()
    {
        float topAlignY = cy - (cy /2);
        float bulletOffsetX = cx - (cx / 2);
        float bulletOffsetY = topAlignY / 2;
        float bulletR = 30;

        background(0);
        noFill();
        stroke(255);
        // textSize(45);
        textAlign(CENTER, CENTER);

        text("Welcome!", cx, border);
        text("The following checks will now take place:", cx, topAlignY);
        text("Press space to continue...", cx, height - border);

        textAlign(LEFT, CENTER);

        ellipse(bulletOffsetX, cy, bulletR, bulletR);
        text("Sound check", bulletOffsetX + (bulletR * 2), cy);
        ellipse(bulletOffsetX, cy + bulletOffsetY, bulletR, bulletR);
        text("Colour check", bulletOffsetX + (bulletR * 2), cy + bulletOffsetY);
    }

    public void drawParabola()
    {
        float ampMin = 500;
        float ampMax = 4000;
        float lineCol = map(oscilAmp, ampMin, ampMax, 0, 255);
        float lineOffset = 0.04f;
        float stepUp = 2;

        lineY = map(oscilAmp, ampMin, ampMax, height - border,  border);
        
        strokeWeight(width / 30);
        stroke(lineCol, 255, 255);

        beginShape();
        vertex(lineX,lineY);
        lineY = map(oscilAmp, ampMin, ampMax, height - border,  border);
        if(halfParab == true)
        {
            oscilAmp -= stepUp + offset;
            offset -= lineOffset;
        }
        else
        {
            oscilAmp += stepUp + offset;
            offset += lineOffset;
        }
        wave.setFrequency(oscilAmp);
        vertex(lineX,lineY);
        endShape();

        lineX += stepUp;

        if(lineX == cx)
        {
            halfParab = true;
        }
        if(lineX == width)
        {
            fullParab = true;
        }
    }

    public void loadingBar()
    {
        float barFrameX = cx /2;
        float barFrameY = cy + (cy / 2);
        float barFrameH = cy / 10;
        float barFrameW = cx;
        float padding = 5;
        float barX = barFrameX + padding;
        float barY = barFrameY + padding;
        float barH = barFrameH - (padding * 2);
        float barWIncrement = 2;

        textAlign(CENTER, CENTER);

        fill(255);
        background(0);
        text("LOADING...", cx, cy);
        noFill();
        stroke(255);
        rect(barFrameX, barFrameY, barFrameW, barFrameH, padding);
        fill(255);
        rect(barX, barY, barW, barH);
        barW += barWIncrement;
        if(barW == (barFrameW - (padding * 2)))
        {
            loaded = true;
            wave.patch(out);
            background(0);
        }
    }

    public void checksComplete()
    {
        text("Checks Complete!\n\nPress s to start...", cx, cy);
    }

    public void keyPressed()
    {
        if(key == ' ')
        {
            welcomed = true;
        }
        if(key =='s')
        {
            start = true;
            getAudioPlayer().cue(0);
            getAudioPlayer().play();
        }
    }
}