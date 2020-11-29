import { CSS2DRenderer, CSS2DObject } from './js/CSS2DRenderer.js';
var labelRenderer, pointLight, sun, ufo, controls, scene, camera, renderer, scene;
var planetSegments = 48;
var orbitData = {value: 200, runOrbit: true, runRotation: true};
var loader = new THREE.ObjectLoader();
var uniforms;

// A function to get the current position for a planet data
function getCurrentPosition(pData, loadedPlanets){
    if(pData.class == "main"){
        return sun.position;     
    }
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

// This funciton causes the sun to explode and stop the ufo 
function explode(tween) {

    uniforms = {
        time: { type: "f", value: 0.0 }
    };

    var SunGeometry = sun.geometry;

    var explodeModifier = new THREE.ExplodeModifier();
    explodeModifier.modify(SunGeometry);
    var numFaces = SunGeometry.faces.length;
    SunGeometry = new THREE.BufferGeometry().fromGeometry( SunGeometry );
    var colors = new Float32Array( numFaces * 3 * 3 );
    var displacement = new Float32Array( numFaces * 3 * 3 );
    var color = new THREE.Color();

    for ( var f = 0; f < numFaces; f ++ ) {
        var index = 9 * f;
        var h = 1.2 * Math.random();
        var s = 1.5 + 0.5 * Math.random();
        var l = 1.5 + 0.5 * Math.random();
        color.setHSL( h, s, l );
        var x = Math.random()*10;
        var y = Math.random()*10;
        var z = Math.random()*10;
        for ( var i = 0; i < 3; i ++ ) {
            displacement[ index + ( 3 * i )     ] = x;
            displacement[ index + ( 3 * i ) + 1 ] = y;
            displacement[ index + ( 3 * i ) + 2 ] = z;
        }
    }
    SunGeometry.addAttribute( 'customColor', new THREE.BufferAttribute( colors, 3 ) );
	SunGeometry.addAttribute( 'displacement', new THREE.BufferAttribute( displacement, 3 ) );

    var shaderMaterial = new THREE.ShaderMaterial( {
        uniforms:       uniforms,
        vertexShader:   document.getElementById( 'vertexshader' ).textContent,
        fragmentShader: document.getElementById( 'fragmentshader' ).textContent
    });

    var mesh = new THREE.Mesh( SunGeometry, shaderMaterial );
    scene.add( mesh );
    tween.stop();
}

// this function fills in the data for a planet.
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

// create a visible orbit ring and add it to the scene.
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


// Simplifies the creation of materials used for visible objects.
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

// Draws all of the orbits to be shown in the scene.
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

// this function creates a sphere model.
function getSphere(material, size, segments) {
    var geometry = new THREE.SphereGeometry(size, segments, segments);
    var obj = new THREE.Mesh(geometry, material);
    obj.castShadow = true;

    return obj;
}

// Creates a planet model and adds it to the scene.
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

// A test function to load the text 
function loadText(object, text, higher) {
    const Div = document.createElement( 'div' );
    Div.className = 'label';
    Div.textContent = text;
    Div.style.color = "white";
    Div.style.marginTop = '-1em';
    const Label = new CSS2DObject( Div );
    if(higher){
        Label.position.set( 0, 3, 0 );
    } else {
        Label.position.set( 0, 1, 0 );
    }    
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

// creates a light that disperses in all directions.
function getPointLight(intensity, color) {
    var light = new THREE.PointLight(color, intensity);
    light.castShadow = true;

    light.shadow.bias = 0.001;
    light.shadow.mapSize.width = 2048;
    light.shadow.mapSize.height = 2048;
    return light;
}

// Move the planet around its orbit, and rotate it.
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
// A function for moving ufo according to the order in the json data
function moveUFO(ufo, arrayOfClasses, loadedPlanets) {

    var firstTween;
    var newTween;
    var oldTween;
    var higher = true;
    
    loadText(ufo, arrayOfClasses[0].method, higher);
    arrayOfClasses.forEach(function(object, index) {
        if(oldTween == null) {
            firstTween = new TWEEN.Tween(ufo.position).to(getCurrentPosition(object, loadedPlanets), 1000).onComplete(function () {
                    loadText(ufo, arrayOfClasses[index+1].method, higher);
                    // Caught an exception
                    if(arrayOfClasses[index+1].method.toLowerCase().indexOf("exception") != -1) {
                        explode(firstTween);
                    }
            });
            oldTween = firstTween;
        } else {
            newTween = new TWEEN.Tween(ufo.position).to(getCurrentPosition(object, loadedPlanets), 2100).onComplete(function () {
                if(index + 1 == arrayOfClasses.length) return;
                loadText(ufo, arrayOfClasses[index+1].method, higher);
                // Caught an exception
                if(arrayOfClasses[index+1].method.toLowerCase().indexOf("exception") != -1) {
                    explode(firstTween);
                }
            });
            oldTween.chain(newTween);
            oldTween = newTween;
        }
    });

    var finalTween = new TWEEN.Tween(ufo.position).to(sun.position, 1000).onComplete(function () {
        console.log("finished");
    });
    oldTween.chain(finalTween);
    firstTween.start();
}


// This function loads the model and return a promise
function modelLoader(url) {
    return new Promise((resolve, reject) => {
        loader.load(url, data=> resolve(data));
    });
}

// This function is called in a loop to create animation.
function update(renderer, scene, camera, controls, loadedPlanets, arrayOfPlanetDatas) {
    pointLight.position.copy(sun.position);
    controls.update();
    var time = Date.now();

    // This line will be activated when explosion happens
    if(uniforms){
        uniforms.time.value = 1.0 + Math.sin(time * 0.0005); //time % interval / interval;
    }

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

// This is the function that starts everything.

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

    // Read in the JSON data;
    var arrayOfClasses;
    await fetch("./input/project1.txt").then(response => response.json().then(data => arrayOfClasses = data));

    // await fetch("./input/project1.json").then(response => response.json().then(data => arrayOfClasses = data));

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
    var size = 1.5;

    // Construct the planet data for each of object
    arrayOfClasses.forEach(function(object) {
        if(!hasBeingDisplayed(arrayOfPlanetDatas, object)){
            if(object.class == "main") return;
            var p = constructPlanetData(orbitRate, rotationRate, distanceFromAxis, getName(object), getTextureUrl(textureKey), size, planetSegments);
            arrayOfPlanetDatas.push(p);
            orbitRate += 10;
            rotationRate += 0.001;
            distanceFromAxis += 5;
            if(textureKey >= 9) {
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
