# ImageProcessor

### Welcome to Elijah and Damian's Image Processor Application!
> In this document, you will find everything you need to understand our program design and also use it yourself.

## Design Overview
> This section provides details on all interfaces, classes, and methods within our program including their purposes and relationships with one another.

**Interface IPCommand (public):** This class represents any command that can be executed on a specific image.
~~~~
  /**
   * This method carries out a specific function, applying it to the given model.
   *
   * @param m    the Image represented model to be used and acted on.
   * @param scan the scanner used to read and retrieve user inputs.
   * @return the modified Image model.
   * @throws IllegalStateException when the image data retrieved from the scanner does not meet the required command arguments needed.
   */
  IPModel execute(IPModel m, Scanner scan) throws IllegalStateException;
~~~~
* **Class VerticalFlip (public):** This class represents the command that is used to flip an image vertically.
~~~~
@Override IPModel execute(IPModel m, Scanner scan): Flip an image vertically to create a new image,
referred to henceforth by the given destination name.
  @return the modified IPModel that is now flipped vertically.
~~~~

* **Class HorizontalFlip (public):** This class represents the command that is used to flip an image Horizontally.
~~~~
@Override IPModel execute(IPModel m, Scanner scan): Flip an image horizontally to create a new image,
referred to henceforth by the given destination name.
  @return the modified IPModel that is now flipped horizontally.
~~~~

* **Class GreyScale (public):** This class represents the command that is used to create a greyscale version of an image according to a specific channel. Either red, blue, green, value, intensity or luma.
~~~~
@Override IPModel execute(IPModel m, Scanner scan): Create a greyscale version of the image with a new name, and
  refer to it henceforth in the program by the given destination name.
  You should be able to create greyscale images that specifically visualize the following:
   * Red: a pixel's red component
   * Green: a pixel's green component
   * Blue: a pixel's blue component
   * Value: the maximum value of the three components for each pixel
   * Intensity: the average of the three components for each pixel
   * Luma: the weighted sum (0.2126 * R) + (0.7152 * G) + (0.0722 * B)
  @return the modified IPModel that is now greyscaled.
~~~~
~~~~
private void vHelper(int[] pixel): The helper method used to alter a pixel's components according 
  to the greyscale * visualizing type (visType).
  @param pixel a size-3 array of integers each representing a red, green, and blue component respectively.
  @return the modified IPModel that is now greyscaled.
~~~~

* **Class ChangeBrightness (public):** This class represents the command that is used to change the brightness of a certain image. This means all RGB values increase or decrease by a set increment amount.
~~~~
@Override IPModel execute(IPModel m, Scanner scan):Change the brightness of the image by the given increment to create a new image,
  referred to henceforth by the given destination name. The increment may be positive (brightening) or negative (darkening).
  @return the modified image model with a new level of brightness.
~~~~
~~~~
private int[] cbHelper(int[] pixel): The helper method used to change the brightness of a pixel.
  @param pixel a size-3 array of integers each representing a red, green, and blue component respectively
  @return a new pixel with its components incremented(or decremented) appropriately
~~~~


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

![ImageProcessor](https://user-images.githubusercontent.com/91427887/173166302-d8cc9330-cea0-4dfc-a199-78bd84eaf418.png)

## Script File Instructions
In testFiles, there is a text file called "ScriptCommandExamples", if you are not running text-based commands from the terminal, you can use this file instead to run some simple script commands. First, create a valid controller and some example parameters to be used for testing. Then create a new FileReader object with this file's path given as a string. Pass this new FileReader object in as the Readable controller parameter. Then run the test. This script file should first produce an error message to the user since this first argument is not a valid one. Then it will load the appropriate image, in this case it is togo.ppm from the images directory. Then it will greyscale the togo image according to its intensity and also flip it vertically. Next, it will save the flipped greyscale togo image to the res directory as a new file. Then, since the next argument is "menu", it will produce the menu instructions again. Finally, it will quit the program as the last argument is "q".

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

##### **We own the Thanos.ppm image and authorize its use in this project.**
