window.addEventListener("scroll", function(){
    let header = this.document.querySelector('#header')
    header.classList.toggle('roll',window.scrollY > 0)
})

