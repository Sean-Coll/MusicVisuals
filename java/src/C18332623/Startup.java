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

    float oscilAmp = 500;
    float lineX = 0;
    float lineY = 450;
    float offset = 0.1f;
    float parabTop = 250;
    int count = 0;
    boolean halfParab = false;
    boolean loaded = false;

    public void settings()
    {
        size(500,500);
    }

    public void setup()
    {
        minim = new Minim(this);

        out = minim.getLineOut();
        wave = new Oscil(oscilAmp, 0.5f, Waves.SINE);
        wave.patch(out);
        background(0);
        colorMode(HSB);
    }

    public void draw()
    {
        if(loaded == false)
        {
            loadingBar();
            loaded = true;
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
            }
        }
        
    }

    public void drawParabola()
    {
        float lineCol = map(oscilAmp, 500, 1250, 0, 255);
        strokeWeight(10);
        stroke(lineCol, 255, 255);

        if(halfParab == false)
        {
            beginShape();
            vertex(lineX,lineY);
            lineX++;
            lineY = map(oscilAmp, 500, 1250, 450, 50);
            oscilAmp += 1 + offset;
            wave.setFrequency(oscilAmp);
            vertex(lineX,lineY);
            endShape();
            offset += 0.01f;
        }
        
        
        if(halfParab == true)
        {
            beginShape();
            vertex(lineX,lineY);
            lineX++;
            lineY = map(oscilAmp, 500, 1250, 450, 50);
            oscilAmp -= 1 + offset;
            wave.setFrequency(oscilAmp);
            vertex(lineX,lineY);
            endShape();
            offset -= 0.01f;
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
        PFont consoleFont;
        float barFrameX = cx /2;
        float barFrameY = cy + (cy / 2);
        float barFrameH = cy / 10;
        float barFrameW = cx;
        float padding = 5;
        float barX = barFrameX + padding;
        float barY = barFrameY + padding;
        float barH = barFrameH - (padding * 2);
        float barW = 0;

        consoleFont = createFont("LUCON.TTF", 40);
        textFont(consoleFont);
        fill(255);
        background(0);
        text("LOADING...", cx - 100, cy);
        noFill();
        stroke(255);
        rect(barFrameX, barFrameY, barFrameW, barFrameH, padding);
        fill(255);
        // while(barW <= barFrameW - (padding * 2))
        // {
        //     rect(barX, barY, barW, barH);
        //     barW += 0.01f;
        // }
        for(barW = 0; barW <= barFrameW - (padding * 2); barW++)
        {
            rect(barX, barY, barW, barH);   
        }
    }
}