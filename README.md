# Using the plugin 
DISCLAIMER: these commands are for Mac users! They are slightly different on Windows (we'll update this with Windows commands soon)

## Building/Compiling the plugin

This plugin isn't an application that can be run on its own; it's a tool used to modify the source code of other Java projects.
Therefore, it doesn't need a Main file/method, and we shouldn't be clicking the green triangle that `runs` the project. 
However, to the left of the Add Configuration menu, there's a little hammer which `builds` the project, and that button works perfectly ;)
Simply click the hammer to build the plugin and check to make sure the build was successful.

Troubleshooting:
1. Make sure the language is set to Java 8 with SDK version 1.8 
    - in IntelliJ, this is configured under File > Project Structure > Project
2. Ensure tools.jar is added as an external library
    - the jar file is included in SDK 1.8
    - expand <1.8> under External Libraries, right click tools.jar and select copy > copy path...
    - open File > Project Structure > Modules > Dependencies, click the + icon in the bottom left corner and click "JARs or directories"
    - follow the copied path, select tools.jar and click open
    
## Running the plugin on a target project
!!! RUN BASH SCRIPT !!! <br>
BELOW INSTRUCTIONS ARE TO DO IT MANUALLY - VERY MESSY!   

## Compiling a target project using our plugin

On the command line, enter:
`javac -cp PATH_TO_PLUGIN:PATH_TO_TARGET_PROJECT -Xplugin:MyPlugin -d OUTPUT_DIR PATH_TO_MAIN`

We suggest you do this from inside the top level of your target project, so your paths will be: <br>
PATH_TO_PLUGIN = `~/<path to our repo>/cpsc410_project2_team2/<path to JavacPlugin.class's parent directory>` <br>
PATH_TO_TARGET_PROJECT = `./src` <br>
OUTPUT_DIR = `modified_out` to create a new directory to house the modified compiled classes <br>
PATH_TO_MAIN = `./src/<path to main>` <br>

For example (using a tinyVars variation as the target project): <br>
`javac -cp ~/Desktop/UBC2020/CPSC410/teamProject/cpsc410_project2_team2/target/classes:./src -Xplugin:MyPlugin -d modified_out src/ui/Main.java`

Definitely a tricky thing that once you add the `-cp` option `javac` stops looking in the current directory for classes!

## Running the modified compiled program:
Run `java -cp ./OUTPUT_DIR MAIN_CLASS 2> LOGFILE_PATH` where: <br>
OUTPUT_DIR = the name of the output directory used above <br>
MAIN_CLASS = the name of the main class (including directories beyond `src`) <br>
LOGFILE_PATH = the location at which to store the generated logs

For example:
`java -cp ./modified_out ui.Main 2> tmp/pluginLogs.txt`
