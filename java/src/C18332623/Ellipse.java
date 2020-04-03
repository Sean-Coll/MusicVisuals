package C18332623;

public class Ellipse extends Startup
{
    float radius;
    float x;
    float y;
    float hue;
    float brightness;
    float saturation;

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
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

    public float getHue() {
        return hue;
    }

    public void setHue(float hue) {
        this.hue = hue;
    }

    public float getBrightness() {
        return brightness;
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }

    public float getSaturation() {
        return saturation;
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }

    @Override
    public String toString() {
        return "Ellipse [brightness=" + brightness + ", hue=" + hue + ", radius=" + radius + ", saturation="
                + saturation + ", x=" + x + ", y=" + y + "]";
    }

    public Ellipse(float radius, float x, float y, float hue, float brightness, float saturation) {
        this.radius = radius;
        this.x = x;
        this.y = y;
        this.hue = hue;
        this.brightness = brightness;
        this.saturation = saturation;
    }

    public void changeCol(float hue, float bright, float sat)
    {
        this.hue = hue;
        this.brightness = bright;
        this.saturation = sat;
    }
}