markActive();
addLangToHref();
markActiveLang();

function markActive() {
    let url = window.location.pathname;
    let menuElements = document.getElementsByClassName('topMenuElement');
    let splitUrl = url.split("/").reverse();
    let marked = false;
    let hrefNr = 0;
    while(!marked && hrefNr < splitUrl.length) {
        let href = splitUrl[hrefNr++];
        for (let i = 0; i < menuElements.length; i++) {
            let element = menuElements[i];
            let child = element.children;
            let elementHref = child[0].href.split("/").reverse()[0];
            if (elementHref === href) {
                element.classList.add('activeMenuElement');
                document.getElementById("menuA").textContent = document.getElementsByClassName('activeMenuElement')[0].firstChild.textContent;
                marked = true;
            }
        }
    }
}

function markActiveLang() {
    let localeNow = document.getElementById("activeLang").getAttribute("locale");
    let makeActive = 'lang' + localeNow.toUpperCase();
    document.getElementById(makeActive).parentElement.classList.add('activeMenuElement');
}

function addLangToHref(){
    let lang = "lang=";
    let path = window.location.origin + window.location.pathname;
    let search = window.location.search;
    let newUrl;
    if (!search.length) {
        newUrl = path + "?" + lang;
    }
    else if (search.includes("lang=")) {
        newUrl = path + search.replace(/lang=[a-zA-Z]*/, lang);
    }
    else {
        newUrl = path + search + "&" + lang;
    }
    document.getElementById('langLT').href = newUrl + 'lt';
    document.getElementById('langEN').href = newUrl + 'en';
}




