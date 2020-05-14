package ie.tudublin;

import C18332623.Startup;

public class Main
{	
	public void startUI()
	{
		String[] a = {"MAIN"};
        processing.core.PApplet.runSketch( a, new Startup());
		
	}

	public static void main(String[] args)
	{
		Main main = new Main();
		main.startUI();			
	}
}