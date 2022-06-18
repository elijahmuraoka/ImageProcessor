### Welcome to Elijah and Damian's Image Processor Application!
> In this document, you will find everything you need to understand our program design and also use it yourself.

## Design Overview
> This section provides details on all interfaces, classes, and methods within our program including their purposes and relationships with one another.
We used the Model View Controller design pattern to have the user interact with the programme, the Command design pattern to handle image manipulation, saving and loading, and abstract classes to simplify our code and make it more readable.

**Interface IPCommand (public):** This class represents any command that can be executed on a specific image.
~~~~
IPModel execute(IPModel m, Scanner scan) throws IllegalStateException: This method carries out a specific function, applying it to the given model.
    takes in the Image represented model to be used and acted on.
    takes in the scanner used to read and retrieve user inputs.
    the Image data retrieved from the scanner must meet the required command arguments needed.
~~~~
* **Class VerticalFlip (public):** This class represents the command that is used to flip an image vertically.
~~~~
execute(IPModel m, Scanner scan): Flip an image vertically to create a new image, referred to henceforth by the given destination name.
~~~~

* **Class HorizontalFlip (public):** This class represents the command that is used to flip an image Horizontally.
~~~~
execute(IPModel m, Scanner scan): Flip an image horizontally to create a new image, referred to henceforth by the given destination name.
~~~~

* **Class GreyScale (public):** This class represents the command that is used to create a greyscale version of an image according to a specific channel. Either red, blue, green, value, intensity or luma.
~~~~
execute(IPModel m, Scanner scan): Create a greyscale version of the image with a new name, and refer to it henceforth in the program by the given destination name. You should be able to create greyscale images that specifically visualize the following:
   * Redness of a pixel
   * Green-ness of a pixel
   * Blue-ness of a pixel
   * Value of a pixel
   * Intensity of a pixel
   * Luma of a pixel
~~~~
~~~~
vHelper(int[] pixel): The helper method used to alter a pixel's components according to the greyscale * visualizing type.
  takes in a size-3 array of integers each representing a red, green, and blue component respectively.
~~~~

* **Class ChangeBrightness (public):** This class represents the command that is used to change the brightness of a certain image. This means all RGB values increase or decrease by a set increment amount.
~~~~
execute(IPModel m, Scanner scan): Change the brightness of the image by the given increment to create a new image, referred to henceforth by the given destination name. The increment may be positive (brightening) or negative (darkening).
~~~~
~~~~
cbHelper(int[] pixel): The helper method used to change the brightness of a pixel.
  Takes in a size-3 array of integers each representing a red, green, and blue component respectively.
~~~~

* **Class AbstractKernelFilter (abstract, implements IPCommand):** This abstract class is used when creating any Image Processor editing features that need a kernel matrix to filter images in a specific manner. A basic operation in many image processing algorithms is filtering. A filter has a "kernel," which is a 2D array of numbers, having odd dimensions (3x3, 5x5, etc.). Given a pixel in the image and a channel, the result of the filter can be computed for that pixel and channel.
~~~~
generateKernelMatrix(): Generates kernel matrix needed to filter the image.
~~~~
~~~~
blurAndSharpenHelper(IPModel m): A helper command method used in both the blur and sharpen classes.
    Takes in the IPModel to be modified.
~~~~

* **Class AbstractTransformColor (abstract, implements IPCommand):** This abstract class is used when creating any Image Processor editing features that use a matrix to transform the color (RGB values) of an image. Use matrix multiplication to change a pixel's RGB components accordingly.
~~~~
generateColorMatrix(): Generate the matrix needed to transform the image's color.
~~~~
~~~~
transformColorHelper(IPModel m, double[][] matrix): A helper method used that can be used in color transformation commands.
    Takes in the IPModel to be modified.
    The matrix which will be used in the image processing algorithm to either blur or sharpen an image.
~~~~

* **Class Blur (public, extends AbstractKernelFilter):** This class represents the command  that is used to Gaussian blur an image using a filtering algorithm using a 3x3 kernel matrix.
~~~~
generateKernelMatrix(): Generates a specific kernel matrix used to blur an image.
~~~~
* **Class Sepia (public, extends AbstractTransformColor):** This command is used to convert a normal color image into a sepia-toned image.
~~~~
generateKernelMatrix(): Generates a specific kernel matrix used to create a Sepia-tone image.
~~~~
* **Class Sharpen (public, extends AbstractKernelFilter):** This command is used to sharpen an image.
~~~~
generateKernelMatrix(): Generates a specific kernel matrix used to sharpen an image.
~~~~

#

**Interface IPController (public):** The Image Processor's controller interface which supports the 'go' method used to run the application and transmit inputs and outputs between the view and the model.
~~~~
run() The main controller method which handles user inputs and reacts accordingly.
    throws an error when the programme is unable to successfully read the input and/or output values appropriately.
~~~~
~~~~
load(String imageName, String imagePath): Load an image from the specified path and refer it to henceforth in the program by the given image name.
    Takes in an the name of the image to be loaded as
    Takes in the file path for a specific image
    Throws an error when unable to transmit the input(s) and/or output(s) properly
~~~~
~~~~
save(String imageName, String saveAsName, String imagePath): Save an image using its current name and new save name to the specified path.
    Takes in the current name of the image.
    Takes in the name used to save this image as
    Takes in the file path for a specific image
    Throws an error when unable to transmit the input(s) and/or output(s) properly
~~~~

* **Class IPControllerImpl (public):** An implementation of the Image Processor controller interface used to process user inputs and communicate between the model and view. Specifically, this controller supports and can apply any operation provided from its list of commands (See Commands section below).
~~~~
printMenu(): Prints and displays the menu instructions to the user.
    Throws an error when unable to transmit the input(s) and/or output(s) properly
~~~~
~~~~
processCommand(String userInput, Scanner scan): Process the user's inputs and apply some command to the given image model as long as the inputs are valid and sufficient.
    Takes in the user's input to the programme.
    Takes in the user input reader used to read the user's inputs.
    Throws an error when unable to transmit the input(s) and/or output(s) properly.
~~~~

**Interface IPModel (public):** The interface for a generic Image Processor model which supports various operations that can be applied to a specific image.
~~~~
setImageName(String imageName): Sets the image name field for this model using the given value.
    Takes in the name of the image
~~~~
~~~~
setWidth(int width): Sets the width field for this model using the given value.
    Takes in the width of the image
~~~~
~~~~
setHeight(int height): Sets the height field for this model using the given value.
    Takes in the height of the image
~~~~
~~~~
setWorkingImageData(List<List<int[]>> workingImageData): Sets the workingImageData field for this model using the given value.
    Takes in a copy of the original image's pixel data represented as an array list of size-3 integer arrays representing a single pixel and its RGB values respectively
~~~~
~~~~
getImageName(): Retrieve the name of this image model.
~~~~
~~~~
int getWidth(): Retrieve the width of this image model.
~~~~
~~~~
int getHeight(): Retrieve the height of this image model.
~~~~
~~~~
List<List<int[]>> getWorkingImageData(): Retrieve the pixel data of this image model.
~~~~

* **Class ImageModel (public):** An implementation of the Image Processor model interface.

**Interface ImageFile (public):** Handles various image files.
~~~~
read(IPView v): Read any image file and store its data.
    Takes in the Image Processor's view to render necessary message
    Errors when either the input(s) and/or output(s) are invalid
~~~~
~~~~
generateFileName(String saveAsName, String imagePath): Appends the appropriate extension and makes an appropriate file name given a name and image path.
    takes in a name to save this image as.
    takes in a path of the file.
~~~~
~~~~
getWidth(): Retrieves the width of the image file.
~~~~
~~~~
getHeight(): Retrieves the height of the image file.
~~~~
~~~~
getMaxComponent(): Retrieve the max color component of this image model.
~~~~
~~~~
getWorkingImageData(): Retrieves the image data of the image file.
~~~~


**Class IPUtil (public):** This class contains utility methods to read an image from file and simply print its contents. Feel free to change this method as required.
~~~~
readPPM(String filename, IPView v): Read an image file in the PPM format and print the colors.
   Takes in the path of the file. 
~~~~
~~~~
isWithinDimensions(IPModel m, int row, int col): Checks a given row and column and whether they are both within the given image model's width and height.
   Takes in the given IPModel.
   Takes in the given row of a pixel
   Takes in the given column of a pixel
~~~~
~~~~
capComponent(IPModel m, int component): Caps a given color component, forcing it to exist between 0 and 255.
   Takes in the IPModel used to retrieve its max component.
   Takes in the the component to be evaluated.
~~~~


#

**Interface IPView (public):** The view interface. MORE TO BE ADDED.
~~~~
renderMessage(String message) throws IOException: Transmits and outputs the given message to a later specified destination.
    Takes in message the string message to be passed on to the user through the view's appendable object.
    throws an error when the message does not transmit/output correctly.
~~~~
* **Class IPViewImpl (public):** An implementation of the Image Processor View interface. This implementation supports the ability to render messages.

**Class ImageProcessor (public):**
* This Image Processor application is used to edit photos and save them as a new file.
~~~~
main(String[] args): The main method used to run this Image Processor application.
    Takes in the user's inputs in a list form.
~~~~


## UML Diagram

![ImageProcessor](https://user-images.githubusercontent.com/91427887/173166302-d8cc9330-cea0-4dfc-a199-78bd84eaf418.png)

## Script File Instructions (Togo flag image created by Damian Uduevbo using Paint.net)
In testFiles, there is a text file called "ScriptCommandExamples", if you are not running text-based commands from the terminal, you can use this file instead to run some simple script commands. First, create a valid controller and some example parameters to be used for testing. Then create a new FileReader object with this file's path given as a string. Pass this new FileReader object in as the Readable controller parameter. Then run the test. This script file should first produce an error message to the user since this first argument is not a valid one. Then it will load the appropriate image, in this case it is togo.ppm from the images directory. Then it will greyscale the togo image according to its intensity and also flip it vertically. Next, it will save the flipped greyscale togo image to the res directory as a new file. Then, since the next argument is "menu", it will produce the menu instructions again. Finally, it will quit the program as the last argument is "q".

## Commands:
#### _(All commands are case-insensitive for user-friendliness)_

**Import any image you would like to use in this program when running.**
~~~~
> load {imageName} {imagePath}

Example: load Koala images/Koala.ppm
~~~~

**Save any (un)modified images to a path on your device.**
~~~~
> save {imageName} {saveAsName} {imagePath} {extension}

Example: save Koala KoalaNew testFolder png
~~~~

**Displays the instructions including the list of commands.**
~~~~
> menu
> help
~~~~

**Quits the program at any time.**
~~~~
> q
> Q
~~~~

**Change the brightness of an image.<br />
Limitations: Increasing then decreasing brightness won't revert to original image.**
~~~~
> ChangeBrightness {imageName} {increment} {destName}
> cb {imageName} {increment} {destName}

Example: ChangeBrightness Koala 40 KoalaBright
~~~~

**Flips an image horizontally.**
~~~~
> HorizontalFlip {imageName} {destName}
> flip-h {imageName} {destName}

Example: HorizontalFlip Koala Koala-Flipped-H
~~~~

**Flips an image vertically.**
~~~~
> VerticalFlip {imageName} {destName}
> flip-v {imageName} {destName}

Example: VerticalFlip Koala Koala-Flipped-V
~~~~

**Creates a greyscale image.**
~~~~
> Greyscale {imageName} {visType} {destName}
> gs {imageName} {visType} {destName}

Example: GreyScale Koala luma KoalaGS-Luma
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

**Blurs an image.**
~~~~
> Blur {imageName} {destName}

Example: Blur Koala KoalaBlurry
~~~~

**Sharpens an image.**
~~~~
> Sharpen {imageName} {destName}

Example: Sharpen Koala KoalaSharp
~~~~

**Adds a Sepia-tone filter on an image.**
~~~~
> Sepia {imageName} {destName}

Example: Sepia Koala KoalaSepia
~~~~


##### **We own the "eli" and "d" images and authorize their use in this project.**
##### **Bucketboi URL: https://www.reddit.com/r/funny/comments/1h4w91/working_at_a_camp_and_i_found_this_kid/**

