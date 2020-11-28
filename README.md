## Using the plugin to compile a project

`javac -cp PATH_TO_PLUGIN:PATH_TO_TARGET_PROJECT -Xplugin:MyPlugin -d OUTPUT_DIR PATH_TO_MAIN`

For example:

`javac -cp ~/Desktop/UBC2020/CPSC410/teamProject/cpsc410_project2_team2/target/classes:./src -Xplugin:MyPlugin -d modified_out src/ui/Main.java`

Definitely a tricky thing that once you add thje `-cp` option `javac` stops looking in the current directory for classes!

To then run the modified compiled program:
`java -cp ./modified_out ui.Main 2> LOGFILEPATH`

## Building/Compiling

This plugin isn't an application that can be ran on its own. Therefore, it doesn't need a Main file/method, and we shouldn't be clicking the green triangle that `runs` the project. 
However, to the left of Add Configuration there's a little hammer which `builds` the project, and that button works perfectly ;)

