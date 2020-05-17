package C18332623;

public class Parabola extends VisualFX 
{
    private float stepX;
    private float stepY; 
    private float offset; 
    private boolean halfway;

    public Parabola(float x, float y, float w, float h, float stepX, float stepY, float offset, boolean halfway) {
        super(x, y, w, h);
        this.stepX = stepX;
        this.stepY = stepY;
        this.offset = offset;
        this.halfway = halfway;
    }

    public float getStepX() {
        return stepX;
    }

    public void setStepX(float step) {
        this.stepX = step;
    }

    public float getStepY() {
        return stepY;
    }

    public void setStepY(float setpY) {
        this.stepY = setpY;
    }

    public float getOffset() {
        return offset;
    }

    public void setOffset(float offset) {
        this.offset = offset;
    }

    public boolean isHalfway() {
        return halfway;
    }

    public void setHalfway(boolean halfway) {
        this.halfway = halfway;
    }

    @Override
    public String toString() {
        return "Parabola [halfway=" + halfway + ", offset=" + offset + ", stepX=" + stepX + ", stepY=" + stepY + "]";
    }

    public void render(Startup st)
    {
        st.vertex(getX(), getY());
        this.setX(this.getX() + this.stepX);
        if(halfway == true)
        {
            this.setY(this.getY() + offset / 4);
            offset = offset - stepY;
        }
        else
        {
            this.setY(this.getY() - offset / 4);
            offset = offset + stepY;
        }

        if(this.getX() == this.getH())
        {
            halfway = true;
        }
    }
}