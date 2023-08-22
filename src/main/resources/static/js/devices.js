
const main = document.getElementsByTagName('main');

let mainDiv = document.createElement('div');
mainDiv.setAttribute('th:each','device: *{devices}');

let pId = document.createElement('p');
pId.setAttribute('th:text','${device.id}');
mainDiv.append(pId);

let pVnr = document.createElement('p');
pVnr.setAttribute('th:text','${device.vnr}');
mainDiv.append(pVnr);

let pComment = document.createElement('p');
pComment.setAttribute('th:text','${device.comment}');
mainDiv.append(pComment);

let pDate = document.createElement('p');
pDate.setAttribute('th:text','${device.date}');
mainDiv.append(pDate);

main.append(mainDiv);