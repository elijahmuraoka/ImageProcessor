# ImageProcessor

# Commands:
* menu || displays a list of commands
* q || quits the program at any time

**Load and store an image in this application**
> load {imageName} {imagePath}

**Save any (un)modified images to this device.**
> save {imageName} {saveAsName} {imagePath}

**Change the brightness of an image. Increases all values by a set amount.**
> cb {imageName} {increment} {destName}
  
**Change the brightness of an image. Increasing then decrease bright wont go back to original image.**
> ChangeBrightness {imageName} {increment} {destName} || 

* HorizontalFlip {imageName} {destName} || Flips an image horizontally
  
* flip-h {imageName} {destName} || Flips an image horizontally
  
* VerticalFlip {imageName} {destName} || Flips an image vertically
  
* flip-v {imageName} {destName} || Flips an image vertically
  
* Greyscale {imageName} {visType} {destName} || Creates a greyscale image
  
* gs {imageName} {visType} {destName} || Creates a greyscale image
  
* gs-red {imageName} {destName} || Creates a greyscale that visualizes the red component of an image
  
* gs-blue {imageName} {destName} || Creates a greyscale that visualizes the blue component of an image
  
* gs-green {imageName} {destName} || Creates a greyscale that visualizes the green component of an image
  
* gs-value {imageName} {destName} || Creates a greyscale that visualizes the value component of an image
  
* gs-intensity {imageName} {destName} || Creates a greyscale that visualizes the intensity component of an image
  
* gs-luma {imageName} {destName} #Creates a greyscale that visualizes the luma component of an image
