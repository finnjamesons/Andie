# Team Debug Rebug's Andie

Contributors: *Angus Henderson, Dominic Tekanene, Finn O'Neill, Matt Tyler*

---

## Features 

**Brightness & contrast adjustment**

*Contributors:* Dominic Tekanene & Angus Henderson

*Description:* Adjusts the brightness and contrast of the image.

*Access:* Accessible via the colour menu or alternatively using the ^ + C shortcut command. 

*Testing:* Tested both brightness and contrast features using the min/max adjustments to the colour of the image. Pixels go to white or black as expected. Tested in conjunction with filter and transform features to ensure synchronicity in feature application. 

*Implementation errors:* PNG files do not apply filters as intended.
___

**Exception Handling & Error Avoidance**

*Contributors:* Matt Tyler

*Description:* Fixing issues with code that may cause the software to act unintentionally or crash. 

*Testing:* User based testing, attempted to break the program however we could .

*Implementation errors:* When exporting to a wbmp, no file is written however no exception is thrown of any kind. 

Fixed Errors: 

  - Icons not loading. 
  - Image operations applying when no image
  - Save when no image exits system
  - Zoom full not repainting
  - Opening new image applies full ops stack to new image
  - Save as uses an open dialogue instead of save
  
  Exceptions Caught:

 - File IO exceptions when reading any icons in the image
 - File IO exceptions when reading and writing any files in ANDIE

___

**Gaussian blur filter**

*Contributors:* Matt Tyler

*Description:* Adjusts the high frequency components of the image and blurs section of the image.

*Access:* Accessed via the filter menu or alternatively using the ^ + g shortcut command.

*Testing:* Tested on several different sized images. No formal testing framework was used, but all radii (including values out range) were tested.

*Implementation errors:* No known errors.
___

**Image export**

*Contributors:* Matt Tyler

*Description:* Exports the image in file types of bmp, gif, jpeg, jpg, png, tif, tiff and wbmp.

*Access:* The image export feature is accessed via the toolbar file menu and ^ + E shortcut.

*Testing:* Tested exporting to all file types on different operating systems. Also checked for any issues with naming files.

*Implementation errors:* wbmp will not write.

___

**Image Flip**

*Contributors:* Finn O'Neill & Matt Tyler

*Description:* Flips the image around both vertically and horizontally.

*Access:* Accessed via the transform menu.

*Testing:* Tested all variations of multiple flips, horizontal and vertical. 

*Implementation errors:* BufferedImage is created without the Alpha Premultiplied. This changes the way transparency is treated.
___

**Image rotation**

*Contributors:* Angus Henderson

*Description:* Rotates the image, 90 degrees left, right and 180 degrees.

*Access:* Accessed via the transform toolbar section as well as the shortcut commands: ^ + 9 for 90 degrees, ^ + 8 for 180 degrees, ^ + 9 for 270 degrees.

*Testing:* No formal testing framework used, but tested all combinations of multiple rotations on different sized images.

*Implementation errors:* BufferedImage is created without the Alpha Premultiplied. This changes the way transparency is treated.

___

**Keyboard shortcuts**

*Contributors:* Matt Tyler

*Description:* Keyboard command shortcuts to access ANDIE features. 

*Access:* Keyboard commands begin with control command following by a corresponding command to execute the feature. 

*Testing:* Tested different combinations of keyboard commands to ensure shortcuts work as intended.

*Implementation errors:* Then the mnemonic alt+esc is called in the file menu for exiting ANDIE it just minimises instead of quiting.

**Discussion**

We decided to make keyboard shortcuts for the all features of Andie except exiting with an accelerator key. This ensures the accessibility of the features at all times.  

___

**Median filter**

*Contributors:* Matt Tyler

*Description:* Reduces noise from the image. 

*Access:* Accessed via the filter menu, alternatively accessed using the ^ + B shortcut command.

*Testing:* Tested the median filter with other filter changes as well as transform and colour changes. Tested at every level of filter capacity from min to max. 

*Implementation errors:* No known errors.

___

**Sharpen Filter**

*Contributors:* Matt Tyler, Finn O'Neill

*Description:* Sharpens the image. 

*Access:* Accessed via the toolbar filter menu, alternatively by using the ^ + V shortcut command. 

*Testing:* Tested the sharpen filter with other filter changes as well as transform and colour changes. Tested at every level of filter capacity from min to max. 

*Implementation errors:* No known errors.
___

**Toolbar for common operations**

*Contributors:* Matt Tyler

*Description:* A side toolbar menu containing zoom in, zoom out, undo, redo and save features for ANDIE. 

*Access:* The toolbar for common operations feature is accessed via the Andie side toolbar.

*Testing:* No formal testing done. Toolbar buttons work as expected. 

*Implementation errors:* No known errors. 

**Discussion**

The toolbar contatins zoom in, zoom out, undo, redo and save as they are the features that are almost always accessed in every ANDIE project and are the most common.

We also added a transform menu to categorise the resize, rotate and flip features. Within this menu there are two sub menus, one for the different rotations, and one for the different flips. 

___

**Exception handling and Error Avoidance**

A list of exceptions and errors we have handled and dealt with over the project.

*Non-image zoom error:* Zoom can be applied whether image is there or not. When zoomed in multiple times and an image opened, the zoom would be maxed.


___

**Emboss and Edge Detection Filter**

*Contributors:* Angus Henderson, Finn O'Neill, Matt Tyler

*Description:* Create the effect of the image being pressed into or raised out of a sheet of paper.

*Access:* Accessible via the filter menu.

*Testing:* Tested all eight emboss as well as two sobel filters on top of other filter, colour and transform alterations to the image. Tested over cropped images. 

*Implementation errors:* No known errors.

___

**Posterisation Effect**

*Contributors:* Angus Henderson, Finn O'Neill

*Description:* The posterise effect reduces the number of unique colours in the image.

*Access:* Accessible via the colour menu or alternatively using the ^ + p shortcut command.

*Testing:* Tested the posterisation effect on top of filter, colour and transform alterations to the image. 

*Implementation errors:* No known erros.

___

**Macros**

*Contributors:* Dominic Tekanene, Matt Tyler

*Description:* Allows users to record a set of actions as a macro and save that file to disk.

*Access:* Accessed via the macro menu. The record function is accessible via the sidebar. 

*Testing:* Tested recording multiple actions (crop, drawing, moving) on several different images and applying them to different images. 

*Implementation errors:* No known errors. 

___

**Mouse-Based Selection Tools**

*Contributors:* Matt Tyler

*Description:* Features for cropping, drawing tools (including rectangle, ellipse and line shapes). Shapes can be filled with different colours via colour picker.

*Implementation* Crops made out of bonds return an empty image operation to the stack. This is done for usability, instead of throwing an error. This would have involved changing the image operation interface and would interupt maros when being applied. 

*Access:* Accessible via toolbar icon button. 

*Testing:* Tested filter actions inside a cropped image. 

*Implementation errors:* As moving changes the 0,0 point on the image panel, if that is set to off screen and a new image is made suffeciently smaller (or another image opened) it can give the appearance that the image has disappeared. The image can be dragged back into view with the move tool. This isn't exactly an error, just something that would be nice to fix. 

___


## Show us Something


**Inversion effect**

*Contributors:* Dominic Tekanene

*Description:* Inverts the colours of the image making it negative. 

*Access:* Accessible through the colour menu.

*Testing:* General testing with other colour effect applications as well as filter actions. 

*Implementation errors:* No known errors. 


___


**Move Tool & Touch Compatability For Windows**

*Contributors:* Matt Tyler

*Description:* Allows for moving the image arround the screen, zoomin with mouse wheel, and touch controls such as pinch to zoom and touch to move on windows devices. Can be accessed when other mosue tools are in use via the M key. 

*Access:* Accessible via toolbar icon button. 

*Testing:* Tested filter actions inside a cropped image. 

*Implementation errors:* No known errors.
