# Music Visualiser Project

Name: Seán Coll

Student Number: C18332623

# Description of the assignment

The purpose of this assignment is to write an audio responsive program inspired by the sentence *"Something beautiful to enjoy while listening to music."*. Firstly, the user is welcomed and informed that a colour and sound check will take place. These checks are imaginary and only serve as an introduction to the program. The checks are done by drawing a parabolic shape on the screen while the oscillator outputs a tone gradually increasing in pitch. Once the parabola reaches halfway, the pitch declines at the same speed it increased. The 'checks' are now complete and the user is informed they can press 's' to start and then 1, 2 or 3 to change visuals. The program's flow is now in the user's control and they decide what to look at. The user remains in control until they decide to end the program.

# Instructions

The onscreen instructions will guide the user through the program up to the point music starts playing. Then, the user can choose which visual to display by pressing 1, 2 or 3 until they end the program. A full rundown of instructions is as follows:
- 'spacebar' to start the checks
- 's' to start the music and use the phase1 visual
- '2' to switch to the phase2 visual
- '3' to switch to the phase3 visual
- '1' to switch back to the phase1 visual

# How it works

The program works using java and many processing libraries mainly PApplet libraries. Many of the elements in the program use the getSmoothedAmplitude() method which gets the amplitude from the audioBuffer and lerps it, creating a smooth transition. The elements react to the different amplitudes by growing/shrinking in size. The overall flow of the program works using an enumerator called mode. mode is used in a switch statement in the draw method and controls what methods are called. The values mode can have are changed in a sequential order until the music starts. This prevents the program restarting if certain keys are pressed. Once the music starts, the user can then select one of three visuals which are drawn using different phase methods. All of the elements in each of the phase method inherit from a VisualFX superclass. Each subclass is a different shape, line, triangle, ellipse, etc. and has its own render method. All the elements start as VisualFX objects and are initialised to their respective type, this is polymorphism.

## phase1

The first visual the user will see is phase1 and it contains a circle in the centre and two line visualisers, one of the left and one on the right. The circle's size, hue and visibility (saturation and brightness) are influenced by the audio. When the amplitude is low, the circle is small and can't be seen well. Inversly, when the amplitude is high, the circle is large and can be seen clearly. Both line visualisers are identical and will draw a horizontal line for each element in the audioBuffer. The value in each element determines the width of the line.

## phase2

If the user presses '2' they will see a different visual which is drawn by the phase2 method. This visual also contains two line visualisers except they are at the top and bottom. The strokeWeight used for these visualisers is also different to give it a different look. In the middle of the screen, several rotating triangles are drawn to the screen. The triangles are drawn at random positions in a fixed range and their colours gradually cycle through the HSB colour space. Each triangle persists for about one second and then disappears. To control the creation and deletion of the triangles, an arrayList is used.

## phase3

When '3' is pressed, a thrid visual is displayed. It consists of a 'S'-like shape made up of lines. Each line has a different length that responds to audio and the colours of the line cycle through the HSB colour space in a rotating fashion. The entire shape rotates slowly as to show constant movement but it is slow enough to not be too overstimulating.

# What I am most proud of in the assignment

I am most proud of the parabolic shape that is used to 'check' the audio and colour. It took some time to puzzle it out. At first it was just a method drawing the vertices and connecting them. Later, I managed to move it into its own class using a render method to draw it. It works by using the offsets, height and width specified to plot out vertices which are connected to form a parabolic shape. beginShape() and endShape() are used to connect the vertices and they could have been moved into the render method but I left them out for the simplicity of not needing the Startup object to use them. Meanwhile, an oscillator generates a pitch that represents the height of the parabolic shape as it grows.

# Link to YouTube Video

Here is the link to the video demonstration on YouTube:
[![YouTube](https://i9.ytimg.com/vi/4Bkf2GWX-1Q/mqdefault.jpg?time=1589749227852&sqp=CNDLhvYF&rs=AOn4CLBWtpe2WlUO3S6zn38VqOPmUkNJOA)](https://youtu.be/4Bkf2GWX-1Q)
