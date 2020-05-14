package C18332623;

public class Triangle extends VisualFX
{
    public Triangle(float x, float y, float w, float h) 
    {
        super(x, y, w, h);
    }

    @Override
    public String toString() {
        return "Triangle []";
    }

    public void render(Startup st)
    {
        st.triangle(getX(), getY(), 
                    getX() - (getW() / 2), getY() + getH(), 
                    getX() + (getW() / 2), getY() + getH());
    }
}