package example;

import ie.tudublin.Visual;

public class CubeVisual extends Visual
{
    public void settings()
    {
        size(800,800,P3D);
    }

    public void setup()
    {
        colorMode(HSB);
        startMinim();
        startListening();
        loadAudio("heroplanet.mp3");
        as.trigger();
    }

    public void draw()
    {
        background(0);
        calculateAverageAmplitude();
        noFill();
        stroke(map(lerpedAmplitude, 0, 1, 0, 255), 255, 255);
        // noStroke();
        // fill(255);
        lights();
        camera(0,0,0,0,0,-1,0,1,0);
        pushMatrix();
        strokeWeight(5);
        // translate(width /2, height / 2);
        rotateX(angle);
        rotateY(angle);
        // println(amplitude);
        box(50 + (lerpedAmplitude * 500));
        strokeWeight(1);
        sphere(20 + (lerpedAmplitude * 200));
        angle += 0.01f;
        popMatrix();

        // pushMatrix();
        // translate(150,0,-250);
        // fill(0,255,0);
        // sphere(100);
        // popMatrix();
    }

    float angle = 0;
}