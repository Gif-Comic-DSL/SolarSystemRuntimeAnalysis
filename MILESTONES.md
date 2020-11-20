Add entries to this file summarising each project milestone. Don't forget that these need to have been discussed with your TA before the milestone deadline; also, remember to commit and push them by then!

# MILESTONE 4
Status of Implementation:
- VISUALIZATION (using three.js and tween.js libraries)
    - We have a “default” image of a solar system with planets orbiting and a spaceship flying between them
    - basic space background (done) 
    - planets model (done) 
    - spaceship model (done)
    - planets orbiting (done)
    - spaceship traveling between two points (done)
    - working on creating planets iteratively based on input file
    - adding planet explosions for when an exception is thrown 
- DYNAMIC ANALYSIS (using core Java and writing a plugin)
    - AST generation (done)
        - We have a basic visitor that can traverse and read info from the AST
    - AST modification (to add logging of runtime information) - IN PROGRESS
        - We're planning to write a plugin to add runtime logging statements and then do an offline analysis
        - This tutorial is very helpful: https://www.baeldung.com/java-build-compiler-plugin
        - We're meeting tomorrow (Sat Nov 21) to discuss this further and keep the implementation phase moving along
    - AST evaluation (during which details of interest will be logged to json file) - IN PROGRESS
        - We’re learning how to run a given AST before making any modifications to it
    - mock up the json data (done)
        - we'll need to log the object ID, class name and method name for every method call made/returned from
        - JSON file will contain this info and pass it to the front end to be visualized
- INTEGRATE ANALYSIS INTO VISUALIZATION AND PERFORM THE OFFLINE ANALYSIS (TODO)
    - pass JSON file to front end to be visualized
        - Iteratively create planets according to the json objects (IN PROGRESS)
        - display the class name above the planets (done)
        - spaceship travels through the planets in order listed in JSON file (done - with mock data)
        - display the function name/return during the spaceship flight (done)

Plans for final user study:
- We're planning to have the project fully implemented by Nov 27, then we'll perform the user study and compile our results

Planned timeline for remaining days:
- Nov 21: group meeting regarding plugin implementation
- Nov 25: dynamic analysis finished, JSON file ready for front end
- Nov 27: project fully implemented
- Nov 27/28: final user study and compiled results
- Nov 29: finalized video ready to submit
- Nov 30, 9:00am: project due

# MILESTONE 3

- user study 1 results and planned changes are listed in user-study-1.txt
- the mockup we used for the user study currently consists of paper sketches and verbal descriptions of how the spaceship would move
- we are working on building a digital version of the mockup as we get the visualization components of our project up and running

# MILESTONE 2

## RoadMap
- finalize plan for dynamic analysis implementation by November 11th, define subtasks and create issues
- user study 1 by Nov 13
- visualization library tests working by Nov 13
- Nov 20th: output from dynamic analysis up and running
- Nov 27th: have everything connected together and working
- Nov 29th: finalized video

## Division of Responsiblities
- Ravina: Generating AST
- Jen: Dynamic analysis (modifying AST before parsing it, running AST)
- Abraham: Passing text/JSON output of dynamic analysis to react for visualization
- Astro and Alex: JS and front end bits, displaying info from dynamic analysis


## Progress so far
- Roadmap done
- some design aspects figured out and we have specific quesitons re dynamic analysis that we're working on asap
- libraries for visualization component have been identified and investigated



# MILESTONE 1
## Project: Space Adventure Dynamic Analysis

### Brief Desctiption:
For our program analysis project, we plan to illustrate the control flow of a program using a Solar System analogy
- The sun could represent the main class
- Other planets represent other classes
- Control flow will be visualized using a spaceship, which will travel between the planets
- Exceptions and errors can be modelled using blackholes or explosions
- Relationships between classes will be represented by shooting star lines

### Use Case:
- beginning/young programmers can use our visualization to better understand the control flow of their Java programs as they watch the spaceship trace the execution path of method calls between instances of classes

### Feedback from TA:
- How much would you be implementing as opposed to using existing libraries?
- Will you be implementing the visualization and/or analysis components?
- It would be a good idea to add something about what approach you are planning to follow in the milestone as a follow-up point. 

### Planned follow-ups / features still to design:
- We plan to use existing libraries to implement the visualization components, however this will still require a 
non-trivial amount of work. Currently we are considering/investigating the below libraries for this purpose:
  - https://typpo.github.io/spacekit/
  - https://github.com/mrdoob/three.js/
  - https://www.creative-technologies.de/space-game-visualization-engine/ (example built on three.js for reference/inspiration)

- For the control flow analysis, we will need to do more research and perhaps material from the next few lectures. Some ideas we have so far are:
    - We could use some existing debugger functionality to step through a program’s execution in discrete steps and extract the information needed for our visualization.
    - We could use an existing library to build an AST of the program and then render the spaceship at the currently executing planet when that node is visited. Since we'll most likely need to use JS for the visualization, the image will rerender each time the spaceship's location changes so this will make it appear to be moving. We can modify this to make the movement smoother to simulate flying

