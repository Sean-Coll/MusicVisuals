package C18332623;

import ddf.minim.AudioOutput;
import ddf.minim.Minim;
import ddf.minim.ugens.Oscil;
import ddf.minim.ugens.Waves;
import ie.tudublin.Visual;
import processing.core.PApplet;

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
        strokeWeight(10);
    }

    public void draw()
    {
        // if(frameCount % 2 == 0)
        // {
        //     drawParabola();
        // }
        if(count <= width)
        {
            drawParabola();
        }
        if(count >= width)
        {
            minim.stop();
        }
        
        
    }

    public void drawParabola()
    {
        float lineCol = map(oscilAmp, 500, 1250, 0, 255);
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
}