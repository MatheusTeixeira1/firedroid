const slides = document.querySelector('.slides');
const totalSlides = document.querySelectorAll('.slide').length;
let currentSlide = 0;

function nextSlide() {
    currentSlide = (currentSlide + 1) % totalSlides;
    slides.style.transform = `translateX(-${currentSlide * 100}%)`;
}

setInterval(nextSlide, 4000);