const menuElementList = [
    {href: '/', title: 'Pradžia'},
    {href: '/monitoring', title: 'Stebėjimas'},
    {href: '/devices', title: 'Įrenginiai'},
    {href: '/about', title: 'Apie'},
];

const body = document.querySelector('body');

const header = document.createElement('header');

const main = document.createElement('main');

createTopMenu();
// createMain();
createFooter();
markActive();

function createTopMenu() {
    const nav = document.createElement('nav');
    const ul = document.createElement('ul');
    for (let i = 0; i < menuElementList.length; i++) {
        let li = document.createElement('li');
        li.className = 'topMenuElement';
        let a = document.createElement('a');
        a.href = menuElementList[i].href;
        a.textContent = menuElementList[i].title;
        li.append(a);
        ul.append(li);
    }
    nav.append(ul);
    header.append(nav);
    body.prepend(header);
}

function createMain() {
    body.append(main);
}

function createFooter() {
    const footer = document.createElement('footer');
    const div = document.createElement('div');
    div.textContent = 'Sukurta 2023 \u00A9';
    footer.append(div);
    body.append(footer);
}

function markActive() {
    let url = window.location.pathname;
    let href = url.split("/").reverse()[0];
    let menuElements = document.getElementsByClassName('topMenuElement');

    for (let i = 0; i < menuElements.length; i++) {
        let element = menuElements[i];
        let child = element.children;
        let elementHref = child[0].href.split("/").reverse()[0];
        if (elementHref === href) {
            element.classList.add('activeMenuElement');
        }
    }
}

// function addHref(id) {
//     // let aHref = document.getElementById(id)
//     // aHref.href = "monitoring/id"
//     return "minotirng/" + id;
// }



