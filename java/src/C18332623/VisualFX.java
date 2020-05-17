package C18332623;

import ie.tudublin.Visual;

public class VisualFX extends Visual
{
    private float x;
    private float y;
    private float w;
    private float h;

    public VisualFX(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
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

    @Override
    public String toString() {
        return "VisualFX [h=" + h + ", w=" + w + ", x=" + x + ", y=" + y + "]";
    }

    public void render()
    {
        println("Rendering Object");
    }
}
