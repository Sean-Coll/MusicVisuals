package C18332623;

import ie.tudublin.Visual;
import processing.core.PApplet;

public class VisualFX extends Visual
{
    private float x;
    private float y;
    private float w;
    private float h;
    private float hue;
    private float saturation;
    private float brightness;
    

    public VisualFX(float x, float y, float w, float h, float hue, float saturation, float brightness) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.hue = hue;
        this.brightness = brightness;
        this.saturation = saturation;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }

    public float getHue() {
        return hue;
    }

    public void setHue(float hue) {
        this.hue = hue;
    }

    public float getSaturation() {
        return saturation;
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }

    public float getBrightness() {
        return brightness;
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }

    @Override
    public String toString() {
        return "VisualFX [brightness=" + brightness + ", h=" + h + ", hue=" + hue + ", saturation=" + saturation
                + ", w=" + w + ", x=" + x + ", y=" + y + "]";
    }

    public void changeCol(float hue, float bright, float sat)
    {
        this.hue = hue;
        this.saturation = sat;
        this.brightness = bright;
    }

    public void render(PApplet pa)
    {
        println("Rendering Object");
    }
}
