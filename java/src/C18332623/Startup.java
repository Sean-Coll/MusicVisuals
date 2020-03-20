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

    public void settings()
    {
        size(500,500);
    }

    public void setup()
    {
        minim = new Minim(this);

        out = minim.getLineOut();
        wave = new Oscil(500, 0.5f, Waves.SINE);
        wave.patch(out);
    }

    public void draw()
    {
        background(0);
    }
}