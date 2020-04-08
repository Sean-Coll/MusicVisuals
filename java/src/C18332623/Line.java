package C18332623;

public class Line extends VisualFX {

    private float x;
    private float y;
    private float x2;
    private float y2;

    // public Line(float x, float y, float x2, float y2, float hue, float saturation, float brightness)
    // {
    //     super(x, y, 0, 0, hue, saturation, brightness);
    //     setX2(x2);
    //     setY2(y2);
    // }

    public float getX2() {
        return x2;
    }

    public void setX2(float x2) {
        this.x2 = x2;
    }

    public float getY2() {
        return y2;
    }

    public void setY2(float y2) {
        this.y2 = y2;
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

    public Line(float x, float y, float x2, float y2, float hue, float saturation, float brightness) 
    {
        super(x, y, 0, 0, hue, saturation, brightness);
        this.setX(x);
        this.setY(y);
        this.setX2(x2);
        this.setY2(y2);
    }

    public void render(Startup st)
    {
        st.stroke(255);
        st.line(x, y, x2, y2);
    }
}