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
    float hsbMax = 255;

    VisualFX circ1;
    VisualFX line1;
    VisualFX line2;
    VisualFX parab1;
    ArrayList<Triangle> triangles = new ArrayList<Triangle>();

    enum Mode
    {
        WELCOME,
        LOAD,
        PARABOLA,
        CHECKSCOMPLETE,
        PHASE1,
        PHASE2,
        PHASE3
    }

    Mode mode = Mode.WELCOME;
    
    public void settings()
    {
        size(500,500);
        fullScreen();
    }

    float oscilAmp = 500;
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
        noCursor();
        cx = width / 2;
        cy = height / 2;
        border = width / 15;

        pg = createGraphics(width, height);

        circ1 = new Ellipse(cx, cy, width/2);
        line1 = new Line(border * 3, 0, border * 3, 0);
        line2 = new Line(width - border * 3, 0, width - border * 3, 0);
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

            case PHASE2:
            {
                phase2();
                break;
            }

            case PHASE3:
            {
                phase3();
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
        triangles.clear();

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

    float triHue = 0;
    float rotOffset = 0;
    public void phase2()
    {
        float triH = 75;
        float triW = 75;
        float triX = 0;
        float triY = 0;
        float hueMultiplier = 5;

        background(0);
        strokeWeight(3);
        fill(0);
        calculateAverageAmplitude();
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
        }

        if(frameCount % 2 == 0)
        {
            triX = random(border, width - border);
            triY = random(border * 2 + (triH / 2), (height - (border * 2)) - (triH / 2));
            triangles.add(new Triangle(0, -(triH / 2), triW, triH, frameCount, triX, triY));
        }
    
        stroke(hsbMax);

        for(int i = 0; i < triangles.size(); i++)
        {
            push();
            fill((triHue + i * hueMultiplier) % hsbMax, hsbMax, hsbMax);
            translate(triangles.get(i).getOriginX(), triangles.get(i).getOriginY());
            rotate(radians(i + rotOffset));
            triangles.get(i).render(this);
            triangleRemove(triangles.get(i));
            rotOffset += 0.1f;
            triHue = (triHue + 0.05f);
            pop();
        }
    }

    public void triangleRemove(Triangle t)
    {  
        if(frameCount == t.getCreateTime() + 60)
        {
            triangles.remove(t);
        }
    }

    float lineHue = 0;
    public void phase3()
    {
        background(0);
        strokeWeight(1);
        translate(cx, cy);
        calculateAverageAmplitude();

        float mappedY = 0;
        float lengthMultiplier = 3;
        float rotateMultipler = 5;
        int maxNumLines = 37;
        
        for(int i = 1; i < maxNumLines; i ++)
        {
            push();
            rotate(radians((i * rotateMultipler + rotOffset)));
            mappedY = map(getSmoothedAmplitude(), 0, 1, border * 2, width / lengthMultiplier);
            stroke(lineHue % hsbMax, hsbMax, hsbMax);
            lineHue += (hsbMax / maxNumLines);
            line1.setX(0);
            line1.setY(i * lengthMultiplier + mappedY);
            ((Line) line1).setX2(0);
            ((Line) line1).setY2(-(i * lengthMultiplier + mappedY));
            ((Line) line1).render(this);
            rotOffset += 0.005f;
            pop();
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
        stroke(hsbMax);
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

    float barW = 0;
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

        fill(hsbMax);
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
        text("Then press 1, 2, or 3 to switch between effects.", cx, height - border);
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
 
        if(key == '1')
        {
            if(mode == Mode.PHASE2 || mode == Mode.PHASE3)
            {
                background(0);
                strokeWeight(1);
                mode = Mode.PHASE1;
            }
           
        }
        if(key == '2')
        {
            if(mode == Mode.PHASE1 || mode == Mode.PHASE3)
            {
                background(0);
                triangles.clear();
                mode = Mode.PHASE2;
            }
        }
        if(key == '3')
        {
            if(mode == Mode.PHASE1 || mode == Mode.PHASE2)
            {
                background(0);
                mode = Mode.PHASE3;
            }
        }
    }
}