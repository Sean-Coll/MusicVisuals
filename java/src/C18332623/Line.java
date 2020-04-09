package C18332623;

public class Line extends VisualFX {

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
        st.line(getX(), getY(), x2, y2);
    }
}