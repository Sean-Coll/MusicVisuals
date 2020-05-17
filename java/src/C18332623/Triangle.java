package C18332623;

public class Triangle extends VisualFX
{
    private float createTime;
    private float originX;
    private float originY;

    public Triangle(float x, float y, float w, float h, float ct, float ox, float oy) 
    {
        super(x, y, w, h);
        this.createTime = ct;
        this.originX = ox;
        this.originY = oy;
    }

    public float getCreateTime() {
        return createTime;
    }

    public void setCreateTime(float createTime) {
        this.createTime = createTime;
    }

    public float getOriginX() {
        return originX;
    }

    public void setOriginX(float originX) {
        this.originX = originX;
    }

    public float getOriginY() {
        return originY;
    }

    public void setOriginY(float originY) {
        this.originY = originY;
    }

    @Override
    public String toString() {
        return "Triangle [createTime=" + createTime + ", originX=" + originX + ", originY=" + originY + "]";
    }
    
    public void render(Startup st)
    {
        st.triangle(getX(), getY(), 
                    getX() - (getW() / 2), getY() + getH(), 
                    getX() + (getW() / 2), getY() + getH());
    }
}