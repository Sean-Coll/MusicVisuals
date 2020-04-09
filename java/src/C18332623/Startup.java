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
    VisualFX lineRight;
    VisualFX parab1;

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
        // loadAudio("NeverGonnaGiveYouUp.mp3");
        loadAudio("Shadowflame.mp3");
        // loadAudio("heroplanet.mp3");

        consoleFont = createFont("LUCON.TTF", 45);
        textFont(consoleFont);
        background(0);
        colorMode(HSB);

        cx = width / 2;
        cy = height / 2;
        border = width / 15;

        pg = createGraphics(width, height);

        circ1 = new Ellipse(cx, cy, width/2);
        lineLeft = new Line(border * 3, 0, border * 3, 0);
        lineRight = new Line(width - border * 3, 0, width - border * 3, 0);
        parab1 = new Parabola(0, height - border, width, cx, 2, 0.04f, 0.1f, false);
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
        float ampCol;
        float lineWidth = border * 2;
        float lineX = border * 3;

        background(0);
        calculateAverageAmplitude();
        ab = getAudioBuffer();
        stroke(0);
        ((Ellipse) circ1).setRadius(map(getSmoothedAmplitude(), 0, 1, border, cx));
        ampCol =(map(getSmoothedAmplitude(), 0, 1, 0, hsbMax) * 2);
        fill(ampCol, ampCol, ampCol);
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
            lineLeft.setX(lineX - (lerpedVal * lineWidth));
            lineLeft.setY(mappedI);
            ((Line) lineLeft).setX2(lineX + (lerpedVal * lineWidth));
            ((Line) lineLeft).setY2(mappedI);
            lineRight.setX(width - lineX - (lerpedVal * lineWidth));
            lineRight.setY(mappedI);
            ((Line) lineRight).setX2(width - lineX + (lerpedVal * lineWidth));
            ((Line) lineRight).setY2(mappedI);

            ((Line) lineLeft).render(this);
            ((Line) lineRight).render(this);
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

        strokeWeight(border / 2);
        stroke(lineCol, hsbMax, hsbMax);

        beginShape();
        ((Parabola) parab1).render(this);
        // parab1.setY(map(oscilAmp, ampMin, ampMax, height - border,  border));
        // parab1.setY(parab1.getY() + ((Parabola) parab1).getOffset());
        endShape();
        if(((Parabola) parab1).isHalfway() == true)
        {
            oscilAmp -=  ((Parabola) parab1).getStepX() + ((Parabola) parab1).getOffset();
        }
        else
        {
            oscilAmp += ((Parabola) parab1).getStepX() + ((Parabola) parab1).getOffset();
        }

        wave.setFrequency(oscilAmp);
        if(((Parabola) parab1).getX() == width)
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