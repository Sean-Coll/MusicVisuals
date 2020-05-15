package C18332623;

import java.util.ArrayList;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioOutput;
import ddf.minim.Minim;
import ddf.minim.ugens.Oscil;
import ddf.minim.ugens.Waves;
import ie.tudublin.Visual;
import processing.core.PFont;
import processing.core.PGraphics;

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
    VisualFX line1;
    VisualFX line2;
    VisualFX parab1;
    VisualFX triangle1;
    ArrayList<Triangle> triangles = new ArrayList<Triangle>();

    enum Mode
    {
        WELCOME,
        LOAD,
        PARABOLA,
        CHECKSCOMPLETE,
        PHASE1,
        PHASE2
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
        line1 = new Line(border * 3, 0, border * 3, 0);
        line2 = new Line(width - border * 3, 0, width - border * 3, 0);
        parab1 = new Parabola(0, height - border, width, cx, 2, 0.04f, 0.1f, false);
        // triangle1 = new Triangle(width/2, height/2, 75, 75);
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

            case PHASE2:
            {
                phase2();
                break;
            }
        }
    }

    float mappedI;
    float lerpedVal;
    public void phase1()
    {
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
				map(i, 0, ab.size(), 0, hsbMax)
				, hsbMax
				, hsbMax
            );
            line1.setX(lineX - (lerpedVal * lineWidth));
            line1.setY(mappedI);
            ((Line) line1).setX2(lineX + (lerpedVal * lineWidth));
            ((Line) line1).setY2(mappedI);
            line2.setX(width - lineX - (lerpedVal * lineWidth));
            line2.setY(mappedI);
            ((Line) line2).setX2(width - lineX + (lerpedVal * lineWidth));
            ((Line) line2).setY2(mappedI);

            ((Line) line1).render(this);
            ((Line) line2).render(this);
        }
    }

    float randHue = 0;
    public void phase2()
    {
        float triH = 75;
        float triW = 75;
        float triX = 0;
        float triY = 0;
        

        strokeWeight(3);
        background(0);
        fill(0);
        // ((Triangle) triangle1).render(this);
        float lineHeight = border;
        for(int i = 1 ; i < ab.size() ; i ++)
        {
            lerpedVal = lerp(ab.get(i - 1), ab.get(i), 0.1f);
            mappedI = map(i, 0, ab.size(), 0, width);
            stroke(
				map(i, 0, ab.size(), 0, hsbMax)
				, hsbMax
				, hsbMax
            );
            line1.setX(mappedI);
            line1.setY(0);
            ((Line) line1).setX2(mappedI);
            ((Line) line1).setY2((lerpedVal * lineHeight) + (border));
            line2.setX(mappedI);
            line2.setY(height);
            ((Line) line2).setX2(mappedI);
            ((Line) line2).setY2((height - border) - (lerpedVal * lineHeight));

            ((Line) line1).render(this);
            ((Line) line2).render(this);

            stroke(255);
            line(0, border * 2, width, border * 2);
            line(0, height - (border * 2), width, height - (border * 2));
        }

        if(frameCount % 2 == 0)
        {
            triX = random(border, width - border);
            triY = random(border * 2, (height - (border * 2)) - triH);
            triangles.add(new Triangle(0, 0, triW, triH, frameCount, triX, triY));
        }

        // for(Triangle t : triangles)
        // {
        //     push();
        //     randHue = random(0, hsbMax + 1);
        //     fill(randHue, hsbMax, hsbMax);
        //     t.render(this);
        //     pop();
        //     triangleFade(t);
        // }

        for(int i = 0; i < triangles.size(); i++)
        {
            // randHue = random(0, hsbMax + 1);
            fill(0, hsbMax, hsbMax);
            pushMatrix();
            translate(triangles.get(i).getOriginX(), triangles.get(i).getOriginY() + (triangles.get(i).getH() / 2));
            rotate(random(0, TWO_PI));
            triangles.get(i).render(this);
            popMatrix();
            triangleFade(triangles.get(i), frameCount);
        }
    }

    public void triangleFade(Triangle t, float startTime)
    {  
        if(frameCount == t.getCreateTime() + 60)
        {
            triangles.remove(t);
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
        if(key == '2')
        {
            background(0);
            mode = Mode.PHASE2;
        }
    }
}