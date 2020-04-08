package C18332623;

public class Ellipse extends VisualFX
{
    private float radius;
    
    public Ellipse(float x, float y, float radius, float hue, float saturation, float brightness) 
    {
        super(x, y, radius, radius, hue, saturation, brightness);
        this.setRadius(radius);
    }

    public Ellipse()
    {
        this(0,0,0,0,0,0);
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void render()
    {
        ellipse(getX(), getY(), getRadius(), getRadius());
    }
}