package C18332623;

import ddf.minim.AudioOutput;
import ddf.minim.Minim;
import ddf.minim.ugens.Oscil;
import ddf.minim.ugens.Waves;
import ie.tudublin.Visual;
import processing.core.PApplet;
import processing.core.PFont;

/*  This class will run a startup sequence complete with loading bar,
    parabolic frequency test and colour test
*/

public class Startup extends PApplet
{
    Minim minim;
    AudioOutput out;
    Oscil wave;
    PFont consoleFont;

    float oscilAmp = 500;
    float lineX = 0;
    float lineY = (height / 15);
    float offset = 0.1f;
    int count = 0;
    boolean halfParab = false;
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
        minim = new Minim(this);

        out = minim.getLineOut();
        wave = new Oscil(oscilAmp, 0.5f, Waves.SINE);
        consoleFont = createFont("LUCON.TTF", 32);
        textFont(consoleFont);
        background(0);
        colorMode(HSB);
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
                if(count <= width)
                {
                    drawParabola();
                }
                if(count >= width)
                {
                    minim.stop();
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
        float cx = width / 2;
        float cy = height / 2;
        float border = width / 15;
        float topAlignY = cy - (cy /2);
        float bulletOffsetX = cx - (cx / 2);
        float bulletOffsetY = topAlignY / 2;
        float bulletR = 30;

        background(0);
        noFill();
        stroke(255);
        textSize(45);
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
        float parabTop = width / 2;
        float border = height / 15;
        float lineOffset = 0.01f;
        lineY = map(oscilAmp, ampMin, ampMax, height - border,  border);
        
        strokeWeight(10);
        stroke(lineCol, 255, 255);

        if(halfParab == false)
        {
            beginShape();
            vertex(lineX,lineY);
            lineX += 1;
            lineY = map(oscilAmp, ampMin, ampMax, height - border,  border);
            oscilAmp += 1 + offset;
            wave.setFrequency(oscilAmp);
            vertex(lineX,lineY);
            endShape();
            offset += lineOffset;
        }
        
        if(halfParab == true)
        {
            beginShape();
            vertex(lineX,lineY);
            lineX += 1;
            lineY = map(oscilAmp, ampMin, ampMax, height - border, border);
            oscilAmp -= 1 + offset;
            wave.setFrequency(oscilAmp);
            vertex(lineX,lineY);
            endShape();
            offset -= lineOffset;
        }
        count ++;
        if(count == parabTop)
        {
            halfParab = true;
        }
            
        
    }

    public void loadingBar()
    {
        float cx = width / 2;
        float cy = height / 2;
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
        float cx = width / 2;
        float cy = height / 2;

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
        }
    }
}