import { CSS2DRenderer, CSS2DObject } from './js/CSS2DRenderer.js';
var labelRenderer, pointLight, sun, earth, mars, ufo, earthOrbit, marsOrbit, controls, scene, camera, renderer, scene,  tween;
var planetSegments = 48;
var earthData = constructPlanetData(365.2564, 0.015, 25, "earth", "img/earth.jpg", 1, planetSegments);

var marsData = constructPlanetData(345.2564, 0.015, 40, "mars", "img/mars.jpg", 1, planetSegments);
var orbitData = {value: 200, runOrbit: true, runRotation: true};
var clock = new THREE.Clock();
var loader = new THREE.ObjectLoader();

// Get the planets: 
var arrayOfClasses;

// A function to get the current position for a planet data
function getCurrentPosition(pData, loadedPlanets){
    var position = "";
    loadedPlanets.forEach(function(planet) {
        if(getName(pData) == planet.name) {
            position = planet.position;
        }
    })
    return position;
}


// A function to get the planet data
function getPlanetsData(planet, arrayOfPlanetDatas){
    var pData = "";
    arrayOfPlanetDatas.forEach(function(data) {
        if(data.name == planet.name) {
            pData = data;
        }
    })
    return pData;
}

// A function to get the url for the planet map for Three.js texture use
function getTextureUrl(textureKey){
    return "textures/" + textureKey + ".jpg";
}

// A function to get the text display that combines object id and class name like "className:id"
function getName(object){
    var combined = object.class + ":" + object.id;
    return combined;
}

// A function to see has this object id being displayed as a planet already
function hasBeingDisplayed(planets, object){
    var displayed = false;
    if(planets == null) return displayed;
    planets.forEach(function(p) {
        if(p.name == getName(object)){
            displayed = true;
        }
    })
    return displayed;
}
/**
 * This eliminates the redundance of having to type property names for a planet object.
 * @param {type} myOrbitRate decimal
 * @param {type} myRotationRate decimal
 * @param {type} myDistanceFromAxis decimal
 * @param {type} myName string
 * @param {type} myTexture image file path
 * @param {type} mySize decimal
 * @param {type} mySegments integer
 * @returns {constructPlanetData.mainAnonym$0}
 */
function constructPlanetData(myOrbitRate, myRotationRate, myDistanceFromAxis, myName, myTexture, mySize, mySegments) {
    return {
        orbitRate: myOrbitRate
        , rotationRate: myRotationRate
        , distanceFromAxis: myDistanceFromAxis
        , name: myName
        , texture: myTexture
        , size: mySize
        , segments: mySegments
    };
}

/**
 * create a visible ring and add it to the scene.
 * @param {type} size decimal
 * @param {type} innerDiameter decimal
 * @param {type} facets integer
 * @param {type} myColor HTML color
 * @param {type} name string
 * @param {type} distanceFromAxis decimal
 * @returns {THREE.Mesh|myRing}
 */
function getRing(size, innerDiameter, facets, myColor, name, distanceFromAxis) {
    var ring1Geometry = new THREE.RingGeometry(size, innerDiameter, facets);
    var ring1Material = new THREE.MeshBasicMaterial({color: myColor, side: THREE.DoubleSide});
    var myRing = new THREE.Mesh(ring1Geometry, ring1Material);
    myRing.name = name;
    myRing.position.set(distanceFromAxis, 0, 0);
    myRing.rotation.x = Math.PI / 2;
    scene.add(myRing);
    return myRing;
}


/**
 * Simplifies the creation of materials used for visible objects.
 * @param {type} type
 * @param {type} color
 * @param {type} myTexture
 * @returns {THREE.MeshStandardMaterial|THREE.MeshLambertMaterial|THREE.MeshPhongMaterial|THREE.MeshBasicMaterial}
 */
function getMaterial(type, color, myTexture) {
    var materialOptions = {
        color: color === undefined ? 'rgb(255, 255, 255)' : color,
        map: myTexture === undefined ? null : myTexture
    };

    switch (type) {
        case 'basic':
            return new THREE.MeshBasicMaterial(materialOptions);
        case 'lambert':
            return new THREE.MeshLambertMaterial(materialOptions);
        case 'phong':
            return new THREE.MeshPhongMaterial(materialOptions);
        case 'standard':
            return new THREE.MeshStandardMaterial(materialOptions);
        default:
            return new THREE.MeshBasicMaterial(materialOptions);
    }
}

/**
 * Draws all of the orbits to be shown in the scene.
 * @param {type} planetsData array of data for planets
 * @returns {undefined}
 */
function createVisibleOrbits(planetsData) {
    var orbitWidth = 0.01;
    planetsData.forEach(function(p) {
        getRing(p.distanceFromAxis + orbitWidth
            , p.distanceFromAxis - orbitWidth
            , 320
            , 0xffffff
            , p.name
            , 0);
    });
}

/**
 * Simplifies the creation of a sphere.
 * @param {type} material THREE.SOME_TYPE_OF_CONSTRUCTED_MATERIAL
 * @param {type} size decimal
 * @param {type} segments integer
 * @returns {getSphere.obj|THREE.Mesh}
 */
function getSphere(material, size, segments) {
    var geometry = new THREE.SphereGeometry(size, segments, segments);
    var obj = new THREE.Mesh(geometry, material);
    obj.castShadow = true;

    return obj;
}

/**
 * Creates a planet and adds it to the scene.
 * @param {type} myData data for a planet object
 * @param {type} x integer
 * @param {type} y integer
 * @param {type} z integer
 * @param {type} myMaterialType string that is passed to getMaterial()
 * @returns {getSphere.obj|THREE.Mesh|loadTexturedPlanet.myPlanet}
 */
function loadTexturedPlanet(myData, x, y, z, text) {
    var myMaterial;
    var passThisTexture;

    if (myData.texture && myData.texture !== "") {
        passThisTexture = new THREE.ImageUtils.loadTexture(myData.texture);
    }
    myMaterial = getMaterial("lambert", "rgb(255, 255, 255 )", passThisTexture);
    myMaterial.receiveShadow = true;
    myMaterial.castShadow = true;
    var myPlanet = getSphere(myMaterial, myData.size, myData.segments);
    myPlanet.receiveShadow = true;
    myPlanet.name = myData.name;
    scene.add(myPlanet);
    myPlanet.position.set(x, y, z);
    loadText(myPlanet, text);

    return myPlanet;
}

/**
 * A test function to load the text.
 * @param {type} object object to attach the text on
 * @param {type} text the text to be displayed
 * @returns {THREE.PointLight|getPointLight.light}
 */
function loadText(object, text) {
    const Div = document.createElement( 'div' );
    Div.className = 'label';
    Div.textContent = text;
    Div.style.color = "white";
    Div.style.marginTop = '-1em';
    const Label = new CSS2DObject( Div );
    Label.position.set( 0, 1, 0 );
    
    // remove the previous labels to avoid overlapping
    for(var i = 0; i < object.children.length; i++) {
        if(object.children[i] instanceof CSS2DObject) {
            object.remove(object.children[i]);
        }
    }
    object.add(Label);

    labelRenderer = new CSS2DRenderer();
    labelRenderer.setSize( window.innerWidth, window.innerHeight );
    labelRenderer.domElement.style.position = 'absolute';
    labelRenderer.domElement.style.top = '10px';
    document.body.appendChild( labelRenderer.domElement );

    const controls = new THREE.OrbitControls( camera, labelRenderer.domElement );
    controls.minDistance = 5;
    controls.maxDistance = 100;
}

/**
 * Simplifies creating a light that disperses in all directions.
 * @param {type} intensity decimal
 * @param {type} color HTML color
 * @returns {THREE.PointLight|getPointLight.light}
 */
function getPointLight(intensity, color) {
    var light = new THREE.PointLight(color, intensity);
    light.castShadow = true;

    light.shadow.bias = 0.001;
    light.shadow.mapSize.width = 2048;
    light.shadow.mapSize.height = 2048;
    return light;
}

/**
 * Move the planet around its orbit, and rotate it.
 * @param {type} myPlanet
 * @param {type} myData
 * @param {type} myTime
 * @param {type} stopRotation optional set to true for rings
 * @returns {undefined}
 */
function movePlanet(myPlanet, myData, myTime, stopRotation) {
    if (orbitData.runRotation && !stopRotation) {
        myPlanet.rotation.y += myData.rotationRate;
    }
    if (orbitData.runOrbit) {
        myPlanet.position.x = Math.cos(myTime 
                * (1.0 / (myData.orbitRate * orbitData.value)) + 10.0) 
                * myData.distanceFromAxis;
        myPlanet.position.z = Math.sin(myTime 
                * (1.0 / (myData.orbitRate * orbitData.value)) + 10.0) 
                * myData.distanceFromAxis;
    }
}
/**
 * A testing function for moving space ship from planet1 to planet2
 */
function moveUFO(ufo, arrayOfClasses, loadedPlanets) {

    var firstTween;
    var newTween;
    var oldTween;

    
    loadText(ufo, arrayOfClasses[0].method);
    arrayOfClasses.forEach(function(object, index) {
        if(oldTween == null) {
            firstTween = new TWEEN.Tween(ufo.position).to(getCurrentPosition(object, loadedPlanets), 3000).onComplete(function () {
                    loadText(ufo, arrayOfClasses[index+1].method);
            });
            oldTween = firstTween;
        } else {
            newTween = new TWEEN.Tween(ufo.position).to(getCurrentPosition(object, loadedPlanets), 3000).onComplete(function () {
                if(index + 1 == arrayOfClasses.length) return;
                loadText(ufo, arrayOfClasses[index+1].method);
            });
            oldTween.chain(newTween);
            oldTween = newTween;
        }
    });

    var finalTween = new TWEEN.Tween(ufo.position).to(sun.position, 3000).onComplete(function () {
        console.log("finished");
    });
    oldTween.chain(finalTween);
    firstTween.start();
}


/**
 * A testing function to load the model
 */
function modelLoader(url) {
    return new Promise((resolve, reject) => {
        loader.load(url, data=> resolve(data));
    });
}

/**
 * This function is called in a loop to create animation.
 * @param {type} renderer
 * @param {type} scene
 * @param {type} camera
 * @param {type} controls
 * @returns {undefined}
 */
function update(renderer, scene, camera, controls, loadedPlanets, arrayOfPlanetDatas) {
    pointLight.position.copy(sun.position);
    controls.update();

    var time = Date.now();

    // Starting orbiting the planets
    loadedPlanets.forEach(function(p) {
        movePlanet(p, getPlanetsData(p, arrayOfPlanetDatas), time);
    })

    renderer.render(scene, camera);
    labelRenderer.render( scene, camera );
    requestAnimationFrame(function () {
        update(renderer, scene, camera, controls, loadedPlanets, arrayOfPlanetDatas);
        TWEEN.update();
    });
    
}

/**
 * This is the function that starts everything.
 * @returns {THREE.Scene|scene}
 */
async function init() {
    // Create the camera that allows us to view into the scene.
    camera = new THREE.PerspectiveCamera(
            45, // field of view
            window.innerWidth / window.innerHeight, // aspect ratio
            1, // near clipping plane
            1000 // far clipping plane
            );
    camera.position.z = 30;
    camera.position.x = -30;
    camera.position.y = 30;
    camera.lookAt(new THREE.Vector3(0, 0, 0));

    // Create the scene that holds all of the visible objects.
    scene = new THREE.Scene();

    // Create the renderer that controls animation.
    renderer = new THREE.WebGLRenderer();
    renderer.setSize(window.innerWidth, window.innerHeight);

    // Attach the renderer to the div element.
    document.getElementById('webgl').appendChild(renderer.domElement);

    // Create controls that allows a user to move the scene with a mouse.
    controls = new THREE.OrbitControls(camera, renderer.domElement);

    // Load the images used in the background.
    var path = 'cubemap/';
    var format = '.jpg';
    var urls = [
        path + 'background' + format, path + 'background' + format,
        path + 'background' + format, path + 'background' + format,
        path + 'background' + format, path + 'background' + format
    ];

    var reflectionCube = new THREE.CubeTextureLoader().load(urls);
    reflectionCube.format = THREE.RGBFormat;

    // Attach the background cube to the scene.
    scene.background = reflectionCube;

    // Create light from the sun.
    pointLight = getPointLight(1.5, "rgb(255, 220, 180)");
    scene.add(pointLight);

    // Create light that is viewable from all directions.
    var ambientLight = new THREE.AmbientLight(0xaaaaaa);
    scene.add(ambientLight);

    await fetch("./input/input.json").then(response => response.json().then(data => arrayOfClasses = data));

    // Create the sun.
    var sunMaterial = getMaterial("basic", "rgb(255, 255, 255)");
    sun = getSphere(sunMaterial, 13, 48);
    scene.add(sun);

    // Create the glow of the sun.
    var spriteMaterial = new THREE.SpriteMaterial(
            {
                map: new THREE.ImageUtils.loadTexture("img/glow.png")
                , useScreenCoordinates: false
                , color: 0xffffee
                , transparent: false
                , blending: THREE.AdditiveBlending
            });
    var sprite = new THREE.Sprite(spriteMaterial);
    sprite.scale.set(70, 70, 1.0);
    sun.add(sprite); // This centers the glow at the sun.

    // Iteratevely create the planets 
    var arrayOfPlanetDatas = [];
    var orbitRate = 365.2564;
    var rotationRate = 0.015;
    var distanceFromAxis = 25;
    var textureKey = 1;
    var size = 1;

    // Construct the planet data for each of object
    arrayOfClasses.forEach(function(object) {
        if(!hasBeingDisplayed(arrayOfPlanetDatas, object)){
            var p = constructPlanetData(orbitRate, rotationRate, distanceFromAxis, getName(object), getTextureUrl(textureKey), size, planetSegments);
            arrayOfPlanetDatas.push(p);
            orbitRate += 10;
            rotationRate += 0.001;
            distanceFromAxis += 5;
            if(textureKey > 9) {
                textureKey = 1;
            } else {
                textureKey += 1;
            }
        }
    });


    var loadedPlanets = [];
    // Load the planets
    arrayOfPlanetDatas.forEach(function(p) {
        // load the texture for the planet
        var tempP = loadTexturedPlanet(p, p.distanceFromAxis, 0, 0, p.name);
        loadedPlanets.push(tempP);
    })

    // Import the ufo model
    ufo = await modelLoader('ufo/ufo.json');
    scene.add(ufo);

    // Create the visible orbit that the Earth uses.
    createVisibleOrbits(arrayOfPlanetDatas);

    // Start the animation.
    moveUFO(ufo, arrayOfClasses, loadedPlanets);
    update(renderer, scene, camera, controls, loadedPlanets, arrayOfPlanetDatas);
}

// Start everything.
init();
