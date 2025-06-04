document.addEventListener('DOMContentLoaded', () => {
    const logo = document.getElementById('logo');
    const main = document.getElementById('containerLogo');
    
    // Coordenadas do centro da logo
    const MedidasLogo = logo.getBoundingClientRect();
    const centroX = MedidasLogo.left + MedidasLogo.width / 2;
    const centroY = MedidasLogo.top + MedidasLogo.height / 2;
    
    main.addEventListener('mousemove', (e) => {
        logo.classList.add('active-mouse');

        // Distância do cursor ao centro
        const distanciaX = e.clientX - centroX;
        const distanciaY = e.clientY - centroY;
        
        // Calcula a direção oposta
        const distancia = Math.sqrt(distanciaX * distanciaX + distanciaY * distanciaY);
        const maxDistancia = 300;
        
        if (distancia < maxDistancia) {
            const moveFactor = (maxDistancia - distancia) / maxDistancia;
            const intensidade = 0.15;
            const moveX = -distanciaX * moveFactor * intensidade;
            const moveY = -distanciaY * moveFactor * intensidade;
            
            logo.style.transform = `translate(${moveX}px, ${moveY}px)`;
        } else {
            logo.style.transform = 'translate(0, 0)';
        }
    });
    
    main.addEventListener('mouseleave', () => {
        logo.style.transform = 'translate(0, 0)';
    });
});
