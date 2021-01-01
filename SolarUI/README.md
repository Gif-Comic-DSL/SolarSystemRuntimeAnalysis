# Using the plugin 
DISCLAIMER: these commands are for Mac users! Windows users be ware!

## Developer Setup
1. Make sure the language is set to Java 8 with SDK version 1.8 
    - in IntelliJ, this is configured under File > Project Structure > Project
2. Ensure tools.jar is added as an external library
    - the jar file is included in SDK 1.8
    - expand <1.8> under External Libraries, right click tools.jar and select copy > copy path...
    - open File > Project Structure > Modules > Dependencies, click the + icon in the bottom left corner and click "JARs or directories"
    - follow the copied path, select tools.jar and click open
    
## Runtime Dependenices
1. The front end can be run with a js dev server like `live-server`. You can install `live-server` from `npm`:
    `npm install -g live-server`
    If you don't have  `npm` setup, you can install it with `brew install npm`.
    
## Building/Compiling the plugin

This plugin isn't an application that can be run on its own; it's a tool used to modify the source code of other Java projects.
Therefore, it doesn't need a Main file/method, and we shouldn't be clicking the green triangle that `runs` the project. 
However, to the left of the Add Configuration menu, there's a little hammer which `builds` the project, and that button works perfectly ;)
Simply click the hammer to build the plugin and check to make sure the build was successful.
    
## Running the plugin on a target project
We have written a bash script to automate the several steps required to run our dynamic analysis and visualization tool.
A call to the script is of the form:
`{PATH_TO_runSolarAnalysis.sh} {PATH_TO_THIS_PROJECT} {PATH_TO_TARGET_PROJECT} {SUBPATH_TO_TARGET_SRC} {SUBPATH_TO_MAIN}`

The `PATH_TO_TARGET_PROJECT` in particular must be an absolute path, and we assume that this is the user dir the target 
project is usually ran from, and contains any input files for the target program. 

Example call to bash script:
`Desktop/ubc2020/cpsc410/teamProject/cpsc410_project2_team2/runSolarAnalysis.sh /Users/alexanderackerman/Desktop/UBC2020/CPSC410/teamProject/cpsc410_project2_team2 /Users/alexanderackerman/Desktop/UBC2020/CPSC410/a2/tinyvars src ui/Main.java`


BELOW INSTRUCTIONS ARE TO DO IT MANUALLY - VERY MESSY!   

## Compiling a target project using our plugin

On the command line, enter:
`javac -cp PATH_TO_PLUGIN:PATH_TO_TARGET_PROJECT -Xplugin:MyPlugin -d OUTPUT_DIR PATH_TO_MAIN`

We suggest you do this from inside the top level of your target project, so your paths will be: <br>
PATH_TO_PLUGIN = `~/<path to our repo>/cpsc410_project2_team2/<path to JavacPlugin.class's parent directory>` <br>
PATH_TO_TARGET_PROJECT = `src` <br>
OUTPUT_DIR = `modified_out` to create a new directory to house the modified compiled classes <br>
PATH_TO_MAIN = `./src/<path to main>` <br>

For example (using a tinyVars variation as the target project): <br>
`javac -cp ~/Desktop/UBC2020/CPSC410/teamProject/cpsc410_project2_team2/target/classes:src -Xplugin:MyPlugin -d modified_out src/ui/Main.java`

Definitely a tricky thing that once you add the `-cp` option `javac` stops looking in the current directory for classes!

## Running the modified compiled program:
Run `java -cp ./OUTPUT_DIR MAIN_CLASS 2> LOGFILE_PATH` where: <br>
OUTPUT_DIR = the name of the output directory used above <br>
MAIN_CLASS = the name of the main class (including directories beyond `src`) <br>
LOGFILE_PATH = the location at which to store the generated logs

For example:
`java -cp ./modified_out ui.Main 2> tmp/pluginLogs.txt`