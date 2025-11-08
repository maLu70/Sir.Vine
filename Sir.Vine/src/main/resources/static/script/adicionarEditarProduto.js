document.addEventListener('DOMContentLoaded', () => {
    const vinho = document.querySelector('#vinho');
    const queijo = document.querySelector('#queijo');
    const espumante = document.querySelector('#espumante');

    const adVinho = document.querySelector('#adVinho');
    const adQueijo = document.querySelector('#adQueijo');
    const adEspuma = document.querySelector('#adEspuma');

    const btns = document.querySelector('#btns');

    function atualizar() {
        if (vinho.checked) {
            adVinho.style.display = 'flex';
            adEspuma.style.display = 'none';
            adQueijo.style.display = 'none';

            btns.style.display = 'flex';

            console.log("vinho");

        } else if (espumante.checked) {
            adVinho.style.display = 'none';
            adEspuma.style.display = 'flex';
            adQueijo.style.display = 'none';

            btns.style.display = 'flex';

            console.log("espuma");

        } else if (queijo.checked) {
            adVinho.style.display = 'none';
            adEspuma.style.display = 'none';
            adQueijo.style.display = 'flex';

            btns.style.display = 'flex';

            console.log("queijo");

        } else {
            adVinho.style.display = 'none';
            adEspuma.style.display = 'none';
            adQueijo.style.display = 'none';

            btns.style.display = 'none';
        }
    }

    vinho.addEventListener('change', atualizar);
    queijo.addEventListener('change', atualizar);
    espumante.addEventListener('change', atualizar);

    atualizar();
});