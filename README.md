# BIN2PNG
This program embed binary (exe, zip, ...) to png image. Since some email providers don't want us to share data they don't understand, this is a simple way to let there bots "think" it's just a picture.
You can theroreticaly embed a 2GB file ( only tested with 700MB file).  
This is a java one file tool without dependencies. It uses NIO so you need a jre 1.7+ to run it.  

**Build**: javac BIN2PNG.java  

**Usage** (for a file named foo.zip):
- *java BIN2PNG encode foo.zip* (will create new png : foo.zip.png)
- *java BIN2PNG decode foo.zip.png* (warnig, may overwrite foo.zip)
