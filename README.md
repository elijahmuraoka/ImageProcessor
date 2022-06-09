# ImageProcessor


# Design Overview
**public interface IPCommand:** This class represents any command that can be executed on a specific image.
* **public class VerticalFlip implements IPCommand:** This class represents the command that is used to flip an image vertically.
* **public class HorizontalFlip implements IPCommand:** This class represents the command that is used to flip an image Horizontally.
* **public class GreyScale implements IPCommand:** This class represents the command that is used to create a greyscale version of an image according to a specific channel. Either red, blue, green, value, intensity or luma.
* **public class ChangeBrightness implements IPCommand:** This class represents the command that is used to change the brightness of a certain image. This means all RGB values increase or decrease by a set increment amount.

**public interface IPController:** The Image Processor's controller interface which supports the 'go' method used to run the application and transmit inputs and outputs between the view and the model.
* **public class IPControllerImpl implements IPController:** An implementation of the Image Processor controller interface used to process user inputs and communicate between the model and view. Specifically, this controller supports and can apply any operation provided from its list of commands (See Commands section below).

**public interface IPModel:** The interface for a generic Image Processor model which supports various operations that can be applied to a specific image.
* **public class ImageModel implements IPModel:** An implementation of the Image Processor model interface.

**public class IPUtil**
* This class contains utility methods to read a PPM image from file and simply print its contents. Feel free to change this method as required.

**public interface IPView:** The view interface.
* **public class IPViewImpl implements IPView:** An implementation of the Image Processor View interface. This implementation supports the ability to render messages.

**public class ImageProcessor**
* This Image Processor application is used to edit photos and save them as a new file.


# Commands:

**Upon running the programme, type the load command below to import an image you want to work on.**
~~~~
> load {imageName} {imagePath}
~~~~

**Save any (un)modified images to a path on your deveice device.**
~~~~
> save {imageName} {saveAsName} {imagePath}
~~~~

**displays a list of commands.**
~~~~
> menu
~~~~

**quits the program at any time.**
~~~~
> q
> Q
~~~~
  
**Change the brightness of an image. Increasing then decrease bright wont go back to original image.**
~~~~
> ChangeBrightness {imageName} {increment} {destName}
> cb {imageName} {increment} {destName}
~~~~

**Flips an image horizontally.**
~~~~
> HorizontalFlip {imageName} {destName}
> flip-h {imageName} {destName}
~~~~

**Flips an image horizontally.**
~~~~
> VerticalFlip {imageName} {destName}
> flip-v {imageName} {destName}
~~~~

**Creates a greyscale image.**
~~~~
> Greyscale {imageName} {visType} {destName}
> gs {imageName} {visType} {destName}
~~~~

**Creates a greyscale that visualizes the red component of an image.**
~~~~
> gs-red {imageName} {destName}
~~~~

**Creates a greyscale that visualizes the blue component of an image.**
~~~~
> gs-blue {imageName} {destName}
~~~~

**Creates a greyscale that visualizes the green component of an image.**
~~~~
> gs-green {imageName} {destName}
~~~~

**Creates a greyscale that visualizes the value component of an image.**
~~~~
> gs-value {imageName} {destName}
~~~~

**Creates a greyscale that visualizes the intensity component of an image.**
~~~~
> gs-intensity {imageName} {destName}
~~~~
  
**Creates a greyscale that visualizes the luma component of an image.**
~~~~
> gs-luma {imageName} {destName}
~~~~
