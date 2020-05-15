package C18332623;

public class Triangle extends VisualFX
{
    private float createTime;

    public Triangle(float x, float y, float w, float h, float ct) 
    {
        super(x, y, w, h);
        this.createTime = ct;
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

    public float getCreateTime() {
        return createTime;
    }

    public void setCreateTime(float createTime) {
        this.createTime = createTime;
    }
}