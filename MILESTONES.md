Add entries to this file summarising each project milestone. Don't forget that these need to have been discussed with your TA before the milestone deadline; also, remember to commit and push them by then!

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

