# BIN2PNG

**Purpose:**  
This program embed binary (exe, zip, ...) to png image. Since some email providers don't want us to share data they don't understand, this is a simple way to let there bots "think" it's just a picture.
You can theroreticaly embed a 2GB file ( only tested with 700MB file) but encoding of such files can take some time and opening as picture can be long too.  

**Build**(optional)**:**  
This is a java one file tool without dependencies.You just have to "javac" it, you need a jdk 1.8 or higher to build it.

**Usage:**  
To run it with the provided class, you need a jre 1.8 or higher.
(for a file named foo.zip):
- *java BIN2PNG encode foo.zip* (will create new png : foo.zip.png)
- *java BIN2PNG decode foo.zip.png* (warnig, may overwrite foo.zip)
