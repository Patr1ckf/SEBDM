const paths = {
    "dragon_route": [
        { x: 160, y: 130 },
        { x: 300, y: 130 },
        { x: 370, y: 190 },
        { x: 500, y: 190 },
        { x: 600, y: 20 },
        { x: 720, y: 20 },
        { x: 950, y: 275 }
    ],
    "smurfs_route": [
        { x: 150, y: 370 },
        { x: 280, y: 260 },
        { x: 400, y: 320 },
        { x: 600, y: 300 },
        { x: 720, y: 370 },
        { x: 935, y: 120 }
    ]
};

class Routes {
    constructor(containerId) {
        this.container = document.getElementById(containerId);
        this.init();
    }

    async init() {
        this.container.innerHTML = `
            <div class="map-container">
            <div class="tooltip" id="tooltip"></div>
                <svg class="paths" width="2000" height="500" viewBox="0 0 600 500" preserveAspectRatio="xMidYMid meet">
                <g class="paths-content">
                    <text x="20" y="5" fill="rgb(138, 57, 199)" font-size="25" font-weight="bold">Dragon Route</text>
                    <text x="10" y="300" fill="rgb(43, 163, 167)" font-size="25" font-weight="bold">Smurfs Route</text>

                    <path class="path" data-route="dragon_route" d="M40 30 L160 130 L300 130 L370 190 L500 190 L600 20 L720 20 L800 275 L950 275 L1025 200" stroke="rgb(138, 57, 199)" stroke-width="23" fill="none" />
                    <path class="path" data-route="smurfs_route" d="M30 330 L150 370 L280 260 L400 320 L600 300 L720 370 L935 120 L1025 120" stroke="rgb(43, 163, 167)" stroke-width="23" fill="none" />
                </g>
                </svg>
            </div>
        `;

        this.addStyles();
        this.addStaticCircles();
        await this.loadAttractions();
        this.addHoverEffects();
    }

    addStaticCircles() {
        const svg = this.container.querySelector('.paths-content');

        Object.entries(paths).forEach(([route, points]) => {
            points.forEach(({ x, y }, index) => {
                const circle = document.createElementNS("http://www.w3.org/2000/svg", "circle");
                circle.setAttribute("cx", x);
                circle.setAttribute("cy", y);
                circle.setAttribute("r", "18");
                circle.setAttribute("class", "map-point static-circle");
                circle.setAttribute("data-route", route);
                circle.setAttribute("data-index", index);

                if (route === "dragon_route") {
                    circle.setAttribute("fill", "rgb(93, 29, 142)");
                } else if (route === "smurfs_route") {
                    circle.setAttribute("fill", "rgb(24, 112, 116)");
                }
                svg.appendChild(circle);
            });
        });

        const sharedOuterCircle = document.createElementNS("http://www.w3.org/2000/svg", "circle");
        sharedOuterCircle.setAttribute("cx", "800");
        sharedOuterCircle.setAttribute("cy", "275");
        sharedOuterCircle.setAttribute("r", "18");
        sharedOuterCircle.setAttribute("class", "map-point shared-static-circle");
        sharedOuterCircle.setAttribute("fill", "rgb(93, 29, 142)");
        svg.appendChild(sharedOuterCircle);

        const sharedInnerCircle = document.createElementNS("http://www.w3.org/2000/svg", "circle");
        sharedInnerCircle.setAttribute("cx", "800");
        sharedInnerCircle.setAttribute("cy", "275");
        sharedInnerCircle.setAttribute("r", "10");
        sharedInnerCircle.setAttribute("class", "map-point shared-static-circle2");
        sharedInnerCircle.setAttribute("fill", "rgb(24, 112, 116)");
        svg.appendChild(sharedInnerCircle);
    }


    async loadAttractions() {
        try {
            const response = await fetch('http://localhost:8080/api/attractions');
            const attractions = await response.json();

            const svg = this.container.querySelector('.paths-content');

            const dragonAttractions = attractions.filter(attr => attr.route === 'dragon_route');
            const smurfsAttractions = attractions.filter(attr => attr.route === 'smurfs_route');
            const sharedAttractions = attractions.filter(attr => attr.route === 'shared');

            const createAttractionPoint = (x, y, attr) => {
                const svg = document.querySelector('.paths');

                const circle = document.createElementNS("http://www.w3.org/2000/svg", "circle");
                circle.setAttribute("cx", x);
                circle.setAttribute("cy", y+75);
                circle.setAttribute("r", "18");
                circle.setAttribute("class", "map-point");
                circle.setAttribute("data-title", attr.name);
                circle.setAttribute("data-description", attr.description);
                circle.setAttribute("data-duration", attr.duration);
                circle.setAttribute("data-route", attr.route);

                if (attr.route === 'dragon_route') {
                    circle.setAttribute("fill", "rgb(93, 29, 142)");
                } else if (attr.route === 'smurfs_route') {
                    circle.setAttribute("fill", "rgb(24, 112, 116)");
                }

                svg.appendChild(circle);


                this.addHoverEffects();
            };

            dragonAttractions.forEach((attr, index) => {
                const { x, y } = paths["dragon_route"][index];

                if (x !== 800 || y !== 275) {
                    createAttractionPoint(x, y, attr);
                }
            });

            smurfsAttractions.forEach((attr, index) => {
                const { x, y } = paths["smurfs_route"][index];

                if (x !== 800 || y !== 275) {
                    createAttractionPoint(x, y, attr);
                }
            });


            sharedAttractions.forEach((attr) => {
                const sharedPoint = { x: 800, y: 275 };

                const circle = document.createElementNS("http://www.w3.org/2000/svg", "circle");
                circle.setAttribute("cx", sharedPoint.x);
                circle.setAttribute("cy", sharedPoint.y);
                circle.setAttribute("r", "18");
                circle.setAttribute("class", "map-point shared-circle");
                circle.setAttribute("fill", "rgb(93, 29, 142)");

                circle.setAttribute("data-title", attr.name);
                circle.setAttribute("data-description", attr.description);
                circle.setAttribute("data-duration", attr.duration);

                svg.appendChild(circle);

                const innerCircle = document.createElementNS("http://www.w3.org/2000/svg", "circle");
                innerCircle.setAttribute("cx", sharedPoint.x);
                innerCircle.setAttribute("cy", sharedPoint.y);
                innerCircle.setAttribute("r", "10");
                innerCircle.setAttribute("class", "map-point shared-circle2");
                innerCircle.setAttribute("fill", "rgb(24, 112, 116)");
                innerCircle.setAttribute("data-title", attr.name);
                innerCircle.setAttribute("data-description", attr.description);
                innerCircle.setAttribute("data-duration", attr.duration);
                svg.appendChild(innerCircle);

                this.addHoverEffects();
            });


        } catch (error) {
            console.error("Błąd ładowania atrakcji:", error);
        }
    }


    addStyles() {
        const style = document.createElement('style');
        style.innerHTML = `
            .map-container {
            display: flex;
            justify-content: center;
            align-items: center;
            width: 1100px;
            height: 500px;
            background-color: rgba(234, 234, 234, 0.23);
            margin: auto; 
            margin-top: 20px;
            margin-left:0px
            position: relative;
            transform: translateX(-60px);
            border-radius: 10px; 
            padding: 0;
            overflow: visible;
            }

            .paths {
            position: absolute;
            margin-top: 0px;
            padding: 0;
            top: 50%;
            left: 30%;
            padding: 0;
            max-hight: 100%;
            transform: translate(-50%, -50%);
            stroke-linecap: round;
            stroke-linejoin: round;
            stroke-width: 35;
            filter: drop-shadow(0px 0px 5px rgba(0, 0, 0, 0.3));
            overflow: visible;
            }

            .paths-content {
            transform: translateY(75px); 
}
            
            .highlighted-path {
            stroke-width: 40 !important;
            filter: drop-shadow(0px 0px 10px rgba(0, 0, 0, 0.48));
            transition: stroke-width 0.6s ease-in-out;
        }

            .shrinking-path {
            stroke-width: 23px !important; 
            transition: stroke-width 1s ease-out; 
}
            .shrinking-circle{
            r: 18 !important;
            transition: r 1s ease-in-out;
            }
            
            shrinking-shared-circle{
            r: 30 !important;
            transition: r 1s ease-in-out;
            }.s
            
            .shrinking-shared-circle2{
            r: 10 !important;
            transition: r 1s ease-in-out;
            }

            .highlighted-circle {
            r: 30 !important;
            transition: r 0.6s ease-in-out;
        }

            .highlighted-circle-rev {
            r: 18 !important;
            transition: r 0.6s ease-in-out;
        }

        .highlighted-shared-circle {
        r: 30 !important;
        transition: r 0.6s ease-in-out;
}

        .highlighted-shared-circle2 {
        r: 20 !important;
        transition: r 0.6s ease-in-out;
}


            .circle {
            position: absolute;
            width: 40px;
            height: 40px;
            border-radius: 50%;
            cursor: pointer;
            transform: translate(-50%, -50%);
            transition: transform 0.3s ease;
            }

            .circle:hover {
                background-color: #ffa07a;
            }

            .tooltip {
    position: absolute; 
    display: none;
    padding: 15px;
    background-color: rgba(255, 255, 255, 0.27);
    color: white;
    border-radius: 5px;
    pointer-events: none;
    font-size: 14px;
    z-index: 9999; 
    opacity: 1;
    width: 200px; /* Szerokość tooltipa */
    /* Wyśrodkowanie tekstu */
    line-height: 1.5;
    transform: translateX(-50%);
}


}`;
        document.head.appendChild(style);
    }

    addHoverEffects() {
        const tooltip = this.container.querySelector('#tooltip');
        const circles = this.container.querySelectorAll('.map-point');

        circles.forEach(circle => {

            if (!circle.classList.contains('static-circle') && !circle.classList.contains('shared-static-circle') &&
                !circle.classList.contains('shared-static-circle2') && !circle.classList.contains('shared-static-circle')) {
                circle.addEventListener('mouseover', (event) => {
                    const title = circle.getAttribute('data-title');
                    const description = circle.getAttribute('data-description');
                    const duration = circle.getAttribute('data-duration');

                    tooltip.style.display = 'block';
                    tooltip.innerHTML = `<strong>${title}</strong><br>${description}<br><em>Duration: ${duration} min</em>`;

                    const mapContainer = this.container.querySelector('.map-container');
                    const mapRect = mapContainer.getBoundingClientRect();

                    let tooltipX = event.clientX - mapRect.left;
                    let tooltipY = event.clientY - mapRect.top - 200 ;

                    const tooltipWidth = tooltip.offsetWidth;
                    if (tooltipX + tooltipWidth > mapRect.width) {
                        tooltipX = mapRect.width - tooltipWidth - 10;
                    }

                    const tooltipHeight = tooltip.offsetHeight;
                    if (tooltipY + tooltipHeight > mapRect.height) {
                        tooltipY = mapRect.height - tooltipHeight - 10;
                    }

                    tooltip.style.left = `${tooltipX}px`;
                    tooltip.style.top = `${tooltipY}px`;
                });

                circle.addEventListener('mousemove', (event) => {
                    const mapContainer = this.container.querySelector('.map-container');
                    const mapRect = mapContainer.getBoundingClientRect();

                    let tooltipX = event.clientX - mapRect.left;
                    let tooltipY = event.clientY - mapRect.top - 200;

                    const tooltipWidth = tooltip.offsetWidth;
                    if (tooltipX + tooltipWidth > mapRect.width) {
                        tooltipX = mapRect.width - tooltipWidth - 10;
                    }

                    const tooltipHeight = tooltip.offsetHeight;
                    if (tooltipY + tooltipHeight > mapRect.height) {
                        tooltipY = mapRect.height - tooltipHeight - 10;
                    }

                    tooltip.style.left = `${tooltipX}px`;
                    tooltip.style.top = `${tooltipY}px`;
                });

                circle.addEventListener('mouseout', () => {
                    tooltip.style.display = 'none';
                });
            }
        });
    }






}

// Eksport klasy
export default Routes;
