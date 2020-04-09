package C18332623;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioOutput;
import ddf.minim.Minim;
import ddf.minim.ugens.Oscil;
import ddf.minim.ugens.Waves;
import example.AudioBandsVisual;
import ie.tudublin.Visual;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import C18332623.Ellipse;
import C18332623.VisualFX;
import C18332623.Line;


public class Startup extends Visual
{
    AudioBuffer ab;
    Minim testSound;
    AudioOutput out;
    Oscil wave;
    PFont consoleFont;
    PGraphics pg;

    float cx;
    float cy;
    float border;
    float oscilAmp = 500;
    float lineX = 0;
    float lineY;
    float offset = 0.1f;
    float barW = 0;
    float hsbMax = 255;

    boolean halfParab = false;

    VisualFX circ1;
    VisualFX lineLeft;
    PApplet pa;

    enum Mode
    {
        WELCOME,
        LOAD,
        PARABOLA,
        CHECKSCOMPLETE,
        PHASE1
    }

    Mode mode = Mode.WELCOME;
    

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
        loadAudio("NeverGonnaGiveYouUp.mp3");

        consoleFont = createFont("LUCON.TTF", 45);
        textFont(consoleFont);
        background(0);
        colorMode(HSB);

        cx = width / 2;
        cy = height / 2;
        border = width / 15;

        pg = createGraphics(width, height);

        circ1 = new Ellipse(cx, cy, width/2, hsbMax, hsbMax, hsbMax);
        lineLeft = new Line(border * 3, 0, border * 3, height, hsbMax, hsbMax, hsbMax);
    }

    public void draw()
    {
        switch(mode)
        {
            case WELCOME:
            {
                welcome();
                break;
            }

            case LOAD:
            {
                loadingBar();
                break;
            }

            case PARABOLA:
            {
                drawParabola();
                break;
            }
            
            case CHECKSCOMPLETE:
            {
                testSound.stop();
                background(0);
                checksComplete();
                break;
            }
            
            case PHASE1:
            {
                phase1();
                break;
            }
        }
    }

    public void phase1()
    {
        float mappedI;
        float lerpedVal;
        background(0);
        calculateAverageAmplitude();
        ab = getAudioBuffer();
        stroke(0);
        ((Ellipse) circ1).setRadius(map(getSmoothedAmplitude(), 0, 1, border, cx));
        circ1.setHue(map(getSmoothedAmplitude(), 0, 1, 0, hsbMax) * 2);
        fill(circ1.getHue(), circ1.getHue(), circ1.getHue());
        ((Ellipse) circ1).render(this);
        for(int i = 1 ; i < ab.size() ; i ++)
        {
            lerpedVal = lerp(ab.get(i - 1), ab.get(i), 0.1f);
            mappedI= map(i, 0, ab.size(), 0, height);
            stroke(
				map(i, 0, ab.size(), 0, 255)
				, 255
				, 255
            );
            line(width - border * 3 - (lerpedVal * border), mappedI,  width - border * 3 + (lerpedVal * border), mappedI);
            // line(border * 3 - (lerpedVal * border), mappedI, border * 3 + (lerpedVal * border), mappedI);
            line(border * 3 - (ab.get(i) * border), mappedI, border * 3 + (ab.get(i) * border), mappedI);
        }
        // ((Line) lineLeft).render(this);
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
        float lineCol = map(oscilAmp, ampMin, ampMax, 0, hsbMax);
        float lineOffset = 0.04f;
        float stepUp = 2;

        lineY = map(oscilAmp, ampMin, ampMax, height - border,  border);
        
        strokeWeight(border / 2);
        stroke(lineCol, hsbMax, hsbMax);

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
            mode = Mode.CHECKSCOMPLETE;
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
        stroke(hsbMax);
        rect(barFrameX, barFrameY, barFrameW, barFrameH, padding);
        fill(hsbMax);
        rect(barX, barY, barW, barH);
        barW += barWIncrement;
        if(barW == (barFrameW - (padding * 2)))
        {
            mode = Mode.PARABOLA;
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
            if(mode == Mode.WELCOME)
            {
                mode = Mode.LOAD;
            }
            
        }
        if(key =='s')
        {
            if(mode == Mode.CHECKSCOMPLETE)
            {
                background(0);
                strokeWeight(1);
                getAudioPlayer().cue(0);
                getAudioPlayer().play();
                mode = Mode.PHASE1;
            } 
        }
        // SHORTCUT TO PHASE1 REMOVE WHEN TESTING/PROGRAM IS COMPLETE
        if(key == '1')
        {
            background(0);
            strokeWeight(1);
            getAudioPlayer().cue(0);
            getAudioPlayer().play();
            mode = Mode.PHASE1;
        }
    }
}