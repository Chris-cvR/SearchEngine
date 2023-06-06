/* jshint esversion: 6 */

document.getElementById("searchButton").onclick = () => {
  search();
};

document
  .getElementById("searchInput")
  .addEventListener("keydown", function (event) {
    if (event.key == "Enter") {
      search();
    }
  });

function search() {
  if (document.getElementById("results") != null) {
    document.getElementById("responsesize").innerHTML = "";
    document.getElementById("results").remove();
  }
  if (document.getElementById("searchInput").value != "") {

    jQuery('#catbutton').css("display", "none");
    jQuery('.tab').empty();
    jQuery('.index-container').empty();

    fetch("/search?q=" + document.getElementById("searchInput").value)
      .then((response) => response.json()).catch(function () {
        let results_length = `<p class=results_length>Invalid query.</p>`
        jQuery('.index-container').prepend(results_length);
      })
      .then((data) => {
        if (!(data.length <= 0)) {
          let index = 1;
          let results = data.map(
            (page) => `<div class="result"><a href="${page.url}">${page.title}</a></div>`
          );
          for (var i = 0; i < results.length; i++) {
            if (i % 20 != 0) {
              addTabContent(index - 1, results[i]);
            } else {
              addIndexTab(index);
              addTabContent(index, results[i]);
              index++;
            }
          }
          let results_length = `<p class=results_length>${data.length} websites retrieved</p>`
          jQuery('.index-container').prepend(results_length);
          clickHandle(this, 'tab1');
        } else {
          let results_length = `<p class=results_length>No web page contains the query word.</p>`
          jQuery('.index-container').prepend(results_length);
          jQuery('#catbutton').insertAfter('.index-container');  
          jQuery('#catbutton').css("display", "flex");
        }
      });
  }
}

function cat() {
  let img = document.createElement("img");
  var x = document.body.clientWidth;
  var y = document.body.clientHeight;
  var randomX = Math.floor(Math.random() * x);
  var randomY = Math.floor(Math.random() * y);

  fetch("https://cataas.com/cat")
    .then((response) => response.blob())
    .then((imageBlob) => {
      catURL = URL.createObjectURL(imageBlob);
      img.src = catURL;
      img.style = `position:absolute;left:${randomX};top:${randomY}`;
      document.body.appendChild(img);
    });
}

function clickHandle(event, tabname) {
  let i, tabcontent, tablinks;

  // This is to clear the previous clicked content.
  tabcontent = document.getElementsByClassName("tabcontent");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }

  // Set the tab to be "active".
  tablinks = document.getElementsByClassName("tablinks");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].className = tablinks[i].className.replace(" active", "");
  }

  // Display the clicked tab and set it to active.
  document.getElementById(tabname).style.display = "flex";
  event.currentTarget.className += " active";
}

function addIndexTab(index) {
  let tab_button = `<button class="tablinks" onclick="clickHandle(event, 'tab${index}')">${index}</button>`;
  jQuery(".tab").append(tab_button);
  let tab_content = `<div id="tab${index}" class="tabcontent"></div>`;
  jQuery(tab_content).appendTo(".index-container");
}

function addTabContent(tabindex, content) {
  let target = jQuery(`#tab${tabindex}`);
  target.append(content);
}
