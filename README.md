Self contained eclipse project. Uses usb4java for JSR80 implementation. 

Windows:
- use Zadig to install libusbK for ant stick
- see:  https://github.com/libusbx/libusbx/wiki/Windows-Backend#wiki-How_to_use_libusbx_on_Windows

Linux:
- make udev rule for ant-stick
- see: http://kayahr.github.io/usb4java/faq.html

OSX:
- should work, but not sure if there are additonal steps. 

- import into eclipse
- fix JRE/JDk setup
- compile and run

source code for usb4java : https://github.com/kayahr/usb4java
source code for jformica: https://github.com/cowboy-coders/JFormica/tree/jformica_branch
