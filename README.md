# ImageProcessor

### Welcome to Elijah and Damian's Image Processor Application!

> In this document, you will find everything you need to understand our program design and also use
> it yourself.

## Design Overview

> This section provides details on all interfaces, classes, and methods within our program including
> their purposes and relationships with one another. We used the Model View Controller (MVC) design
> pattern to have the user interact with the programme and section the functionalities of our
> application, the Command design pattern to handle image manipulation, saving and loading, the
> Factory design pattern to dynamically create objects as needed, and abstract classes to simplify
> our code and make it more readable.

## Design Changes

> Here, I will be documenting changes we have made to existing code or anything else we added to
> improve functionality.
> One large change that we made earlier was getting rid of the ImageFile class and moving everything
> inside a typical ImageModel object.
> We felt that it was pointless for the ImageFile class just to read data and transfer it to the
> model. At that point,
> we saw it much more efficient just to have the model read its own data and store everything within
> the image (IPModel) itself in hopes
> of reducing any potential transmission errors.
> When it was first announced that we needed to read additional file types such as JPG and PNG, we
> saw it most fit to create some sort of Abstract Image File class since they would generally use
> the same methods and setup with a few exceptions here and there. We first implemented it this way
> then when we changed to only the model (no file classes), we just created an AbstractModel class.
> We then extended this original AbstractImageModel class in order to create a
> BetterAbstractImageModel class with more model capabilities comparatively. We implemented a new
> BetterModel interface as well, allowing us to store a model's changes, undo changes on a specific
> model, and generate its histogram data as needed.
> In order to implement the GUI, we needed to add more in addition to the change mentioned
> previously. For one, we added a new BetterController interface and its respective abstract class
> that extends the old abstract controller class. This new controller is meant to work with the GUI
> program specifically, but can be adapted and improved further to communicate between both
> text-based and GUI views and an image model.
> On top of all this, we eventually created both CommandFactory and ImageFactory classes in order
> to reduce lists of conditionals and improve code efficiency + flexibility by creating objects
> dynamically as a client goes through the program.
> I, Elijah, enjoy taking photos on my free time so our aim overall was for this to be as close as
> possible to an actual image processor/editor application. In this sense, the ability to load and
> work on multiple images simultaneously as well as undo changes was an absolute-must. We did a
> good job implementing the basic features of this application, but further work can be done as the
> program becomes significantly slower when loading many files at once or very large image files
> that contain a lot of data. It also becomes slower when there are a lot of changes done on one
> model since the undo feature works by storing and recursively calling all the commands on a single
> image model anytime a change is made.

### Commands

**Interface IPCommand (public):** This class represents any command that can be executed on a
specific image.

~~~~
execute(IPModel m, Scanner scan) throws IllegalStateException: This method carries out a specific function, applying it to the given model.
    takes in the Image represented model to be used and acted on.
    takes in the scanner used to read and retrieve user inputs.
    the Image data retrieved from the scanner must meet the required command arguments needed.
~~~~

* **Class CommandFactory (public):** A CommandFactory constructor used to initialize the command
  field when given one.
    ~~~~
    createCommand: Creates the appropriate IPCommand object based on the user's input.
    ~~~~

* **Class VerticalFlip (public):** This class represents the command that is used to flip an image
  vertically.
    ~~~~
    execute(IPModel m, Scanner scan): Flip an image vertically to create a new image, referred to henceforth by the given destination name.
    ~~~~

* **Class HorizontalFlip (public):** This class represents the command that is used to flip an image
  Horizontally.
    ~~~~
    execute(IPModel m, Scanner scan): Flip an image horizontally to create a new image, referred to henceforth by the given destination name.
    ~~~~

* **Class GreyScale (public):** This class represents the command that is used to create a greyscale
  version of an image according to a specific channel. Either red, blue, green, value, intensity or
  luma.
    ~~~~
    execute(IPModel m, Scanner scan): Create a greyscale version of the image with a new name, and
      refer to it henceforth in the program by the given destination name.
      You should be able to create greyscale images that specifically visualize the following:
       * Red: a pixel's red component
       * Green: a pixel's green component
       * Blue: a pixel's blue component
       * Value: the maximum value of the three components for each pixel
       * Intensity: the average of the three components for each pixel
       * Luma: the weighted sum (0.2126 * R) + (0.7152 * G) + (0.0722 * B)
    ~~~~
    ~~~~
    vHelper(int[] pixel): The helper method used to alter a pixel's components according 
      to the greyscale * visualizing type (visType).
      Takes in a size-3 array of integers each representing a red, green, and blue component respectively.
    ~~~~

* **Class ChangeBrightness (public):** This class represents the command that is used to change the
  brightness of a certain image. This means all RGB values increase or decrease by a set increment
  amount.
    ~~~~
    execute(IPModel m, Scanner scan):Change the brightness of the image by the given increment to create a new image, referred to henceforth by the given destination name. The increment may be positive (brightening) or negative (darkening).
    ~~~~
    ~~~~
    cbHelper(int[] pixel): The helper method used to change the brightness of a pixel.
      Takes in a size-3 array of integers each representing a red, green, and blue component respectively
      Produces a new pixel with its components incremented(or decremented) appropriately
    ~~~~

* **Class AbstractKernelFilter (abstract, implements IPCommand):** This abstract class is used when
  creating any Image Processor editing features that need a kernel matrix to filter images in a
  specific manner. A basic operation in many image processing algorithms is filtering. A filter has
  a "kernel," which is a 2D array of numbers, having odd dimensions (3x3, 5x5, etc.). Given a pixel
  in the image and a channel, the result of the filter can be computed for that pixel and channel.

~~~~
generateKernelMatrix(): Generates kernel matrix needed to filter the image.
~~~~

~~~~
blurAndSharpenHelper(IPModel m): A helper command method used in both the blur and sharpen classes.
    Takes in the IPModel to be modified.
~~~~

* **Class AbstractTransformColor (abstract, implements IPCommand):** This abstract class is used
  when creating any Image Processor editing features that use a matrix to transform the color (RGB
  values) of an image. Use matrix multiplication to change a pixel's RGB components accordingly.

~~~~
generateColorMatrix(): Generate the matrix needed to transform the image's color.
~~~~

~~~~
transformColorHelper(IPModel m, double[][] matrix): A helper method used that can be used in color transformation commands.
    Takes in the IPModel to be modified.
    The matrix which will be used in the image processing algorithm to either blur or sharpen an image.
~~~~

* **Class Blur (public, extends AbstractKernelFilter):** This class represents the command that is
  used to Gaussian blur an image using a filtering algorithm using a 3x3 kernel matrix.

~~~~
generateKernelMatrix(): Generates a specific kernel matrix used to blur an image.
~~~~

* **Class Sepia (public, extends AbstractTransformColor):** This command is used to convert a normal
  color image into a sepia-toned image.

~~~~
generateKernelMatrix(): Generates a specific kernel matrix used to create a Sepia-tone image.
~~~~

* **Class Sharpen (public, extends AbstractKernelFilter):** This command is used to sharpen an
  image.

~~~~
generateKernelMatrix(): Generates a specific kernel matrix used to sharpen an image.
~~~~

### Controller

**Interface IPController (public):** The Image Processor's controller interface which supports the '
go' method used to run the application and transmit inputs and outputs between the view and the
model.

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

* **Class IPControllerImpl (public):** An implementation of the Image Processor controller interface
  used to process user inputs and communicate between the model and view. Specifically, this
  controller supports and can apply any operation provided from its list of commands (See Commands
  section below).

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

**Interface BetterIPController (public):** An additional controller interface used to incorporate
and add on more functionality to the original controller interface without modifying it.

~~~~
getKnownImageModels(): Retrieves the controller's knownImageModels field and passes it on to the client. 
    The known image models are represented as a map of image names to the image models themself.
~~~~

~~~~
setReadable(Readable in): Sets the 'in' field of the current controller using the given readable object.
~~~~

* **Class BetterIPControllerImpl (public, implements BetterIPController):** A more flexible
  implementation of the original IPController class that can work with both the text-based and GUI
  views as needed.
  This class overrides the original controller load, run, and save methods while also implementing
  the new better interface methods including getKnownImageModels and setReadable.

### Model

**Interface IPModel (public):** The interface for a generic Image Processor model which supports
various operations that can be applied to a specific image.

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
setMaxComponent(int maxComponent): Sets the max component field for this model using the given value.
~~~~

~~~~
setWorkingImageData(List<List<int[]>> workingImageData): Sets the workingImageData field for this model using the given value.
    Takes in a copy of the original image's pixel data represented as an array list of size-3 integer arrays representing a single pixel and its RGB values respectively
~~~~

~~~~
getImageName(): Retrieve the name of this image model.
~~~~

~~~~
getWidth(): Retrieve the width of this image model.
~~~~

~~~~
getHeight(): Retrieve the height of this image model.
~~~~

~~~~
getWorkingImageData(): Retrieve the max color component of this image model.
~~~~

~~~~
getWorkingImageData(): Retrieve the pixel data of this image model.
~~~~

~~~~
getFileName(): Retrieve the file name of this image model.
~~~~

* **Class AbstractImageModel (public abstract, implements IPModel):**

> An abstract representation for several types of image file types.
> This includes...
> - PPM
> - JPG
> - PNG
> - BMP
>
> Each model represents a single image (or version) that stores data such as the name of the image,
> the (desired) file name for the image, the height, the width, the maximum color component,
> and the image's pixel information.

**Interface BetterIPModel (public):**

~~~~
getCommandList(): Retrieves the model's current list of commands to be executed.
~~~~

~~~~
addToCommandList(String command): Adds the given command as to the commandList as a string.
~~~~

~~~~
setCommandList(List<String> commandList): Sets the model's command list using the given list of strings.
~~~~

~~~~
make(): Returns a readable that contains all commands to be executed on this model.
~~~~

~~~~
undo(): Removes the most recently added command from the list of commands.
~~~~

~~~~
setImage(Image image): Sets the image of the model using the given image.
~~~~

~~~~
getImage(): Retrieves the model's current image object representation.
~~~~

~~~~
generateHistograms(): A list of histograms representing the model's pixel values and frequencies using 
    individual RGB components as well as intensity.
~~~~

~~~~
A list of histograms representing the model's pixel values and frequencies using individual RGB components and intensity.
~~~~

~~~~
addToUndoCounter(int i): Adds an integer to the undo counter list.
~~~~

~~~~
setUndoCounter(List<Integer> undoCounter): Set the model's undoCounter field using the given list of integers.
~~~~

~~~~
getUndoCounter(): Retrieve the undoCounter field from the model.
~~~~

* **Class BetterAbstractImageModel (public abstract, extends AbstractImageModel, implements
  BetterIPModel):**
  A better abstract image model class that can be used to store and undo changes on this specific
  image model.


* **Class BMPImage (public, extends BetterAbstractImageModel):** A BMP image file and model
  representation.
* **Class JPGImage (public, extends BetterAbstractImageModel):** A JPG image file and model
  representation.
* **Class PNGImage (public, extends BetterAbstractImageModel):** A PNG image file and model
  representation.
* **Class PPMImage (public, extends BetterAbstractImageModel):** A PPM image file and model
  representation.
  (Overrides the read method in the BetterAbstractImageModel class as PPM files are read differently
  compared to the others).

**Class ImageFactory (public):** This Image Factory class is used to dynamically create unique 
IPModel objects according to a specific file path and its extension type 
(e.g. JPG, PNG, BMP, PPM, etc.).

~~~~
createImageModel(): Creates the appropriate image model object based on the file name, but more specifically, its extension.
~~~~

### View

**Interface IPView (public):** The basic view interface used for the text-based program.

~~~~
renderMessage(String message) throws IOException: Transmits and outputs the given message to a later specified destination.
    Takes in message the string message to be passed on to the user through the view's appendable object.
    throws an error when the message does not transmit/output correctly.
~~~~

* **Class IPViewImpl (public):** An implementation of the Image Processor View interface. This
  implementation supports the ability to render messages.

**Interface IPGuiView (public):** The GUI interface which supports all necessary operations such as
displaying the screens, rendering messages to the user, or refreshing the screen.

~~~~
initialize(): Initializes the GUI view and all its necessary fields. 
    This method must be called after initializing this view's controller respective controller.
~~~~

~~~~
showMainPanel(String imageName): Make the main image processor panel visible to the client.
~~~~

~~~~
refresh(): Refresh the screen when something is updated to show the new changes made.
~~~~

~~~~
renderMessage(String message): Renders a specific message to the client.
~~~~

~~~~
setController(BetterIPController controller): Sets the given controller as this view's controller field.
~~~~

* **Class SimpleGuiView (public, implements IPGuiView):** A simple GUI view class that implements
  the IPGuiView interface. This view
  allows for an easy switch between the welcome page and the main page. It is the main class used to
  refresh as well when changes are made.
  This class creates the main base frame used to display the welcome page and store the main page
  panel as well.

**Class MainPanel (public, extends JPanel):** The main panel class used for loading, editing, and
saving images altogether.
This main panel has the capability to interact and execute all commands within the command
package as seen through its east panel and various buttons. It can also load and store
multiple images so the user can work on multiple at once. A key feature of this class
is the ability to call the undo method through this class's menu bar as well. This class sets up
the main display for the Image Processor Application using many Java swing references.

**Class Histogram (public, extends JPanel):** A Histogram representing the frequency of red, green,
blue, and intensity values
that occur between 0 and 255 for a specific image model and all of its pixels. This class
specifically is used to override the
paintComponent(Graphics g) method extended by the JPanel abstract class in order to actually
draw/paint the model's Histograms
on a specified panel.

### Additional Classes

**Class IPUtil (public):** This class contains utility methods to read an image from file and simply
print its contents. Feel free to change this method as required.

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

**Class LoadActionListener (public, implements ActionListener):** This class allows the "load"
buttons to perform certain actions when clicked. This includes "loading" the image, meaning the
program reads and stores all its data in a new model instance.

~~~~
actionPerformed(ActionEvent e): This method is an overriden implementation of the actionPerformed method found in the supplied ActionListener interface.
~~~~

**Class ImageProcessor (public):** This Image Processor application is used to edit photos and save
them as a new file.

~~~~
main(String... args): This Image Processor application is used to load, edit, and save photos.
This main method can respond to program arguments in 3 different ways:
 1) Passing in no arguments results in the creation of the Image Processor GUI Program
 2) Passing in "-text" results in the creation of the Image Processor Text-Based Program
 3) Passing in "-file" followed by a valid script file path allows the script to be read
   and run through our application smoothly using various commands (You can also string together
   multiple files to run at once. E.g. -file file1Path -file file2Path -file file3Path ...
~~~~

## UML Diagram

![ImageProcessor](h![UML_FINAL](https://user-images.githubusercontent.com/91427887/176216160-f7e808bc-874e-451f-998a-b6db70ccf896.png)

## Script File Instructions

In testFiles, there is a text file called "ScriptCommandExamples", if you are not running text-based
commands from the terminal, you can use this file instead to run some simple script commands. First,
create a valid controller and some example parameters to be used for testing. Then create a new
FileReader object with this file's path given as a string. Pass this new FileReader object in as the
Readable controller parameter. Then run the test. This script file should first produce an error
message to the user since this first argument is not a valid one. Then it will load the appropriate
image, in this case it is togo.ppm from the images directory. Then it will greyscale the togo image
according to its intensity and also flip it vertically. Next, it will save the flipped greyscale
togo image to the res directory as a new file. Then, since the next argument is "menu", it will
produce the menu instructions again. Finally, it will quit the program as the last argument is "q".

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

##### **We own the "togo," "eli" and "d" images and authorize their use in this project.**

#####  

**Bucketboi Citation
URL: https://www.reddit.com/r/funny/comments/1h4w91/working_at_a_camp_and_i_found_this_kid/**
