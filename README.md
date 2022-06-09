# ImageProcessor

##### Welcome to Elijah and Damian's Image Processor Application!
##### In this document, you will find everything you need to understand our program design and also use it yourself.

## Design Overview
> This section provides details on all interfaces, classes, and methods within our program including their purposes and relationships with one another.

**Interface IPCommand (public):** This class represents any command that can be executed on a specific image.
* **Class VerticalFlip (public):** This class represents the command that is used to flip an image vertically.
* **Class HorizontalFlip (public):** This class represents the command that is used to flip an image Horizontally.
* **Class GreyScale (public):** This class represents the command that is used to create a greyscale version of an image according to a specific channel. Either red, blue, green, value, intensity or luma.
* **Class ChangeBrightness (public):** This class represents the command that is used to change the brightness of a certain image. This means all RGB values increase or decrease by a set increment amount.

**Interface IPController (public):** The Image Processor's controller interface which supports the 'go' method used to run the application and transmit inputs and outputs between the view and the model.
* **Class IPControllerImpl (public):** An implementation of the Image Processor controller interface used to process user inputs and communicate between the model and view. Specifically, this controller supports and can apply any operation provided from its list of commands (See Commands section below).

**Interface IPModel (public):** The interface for a generic Image Processor model which supports various operations that can be applied to a specific image.
* **Class ImageModel (public):** An implementation of the Image Processor model interface.

**Class IPUtil (public):** This class contains utility methods to read an image from file and simply print its contents. Feel free to change this method as required.

**Interface IPView (public):** The view interface. MORE TO BE ADDED.
* **Class IPViewImpl (public):** An implementation of the Image Processor View interface. This implementation supports the ability to render messages.

**Class ImageProcessor (public):**
* This Image Processor application is used to edit photos and save them as a new file.


## UML Diagram

![ImageProcessorUML](https://user-images.githubusercontent.com/91427887/172942545-6bfd5fc2-f131-4196-bd35-694b3d16cfa5.png)


## Commands:

**Import any image you would you would like to use in this program when running.**
~~~~
> load {imageName} {imagePath}
~~~~

**Save any (un)modified images to a path on your device.**
~~~~
> save {imageName} {saveAsName} {imagePath}
~~~~

**Displays the instructions including the list of commands.**
~~~~
> menu
~~~~

**Quits the program at any time.**
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

**Flips an image vertically.**
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
