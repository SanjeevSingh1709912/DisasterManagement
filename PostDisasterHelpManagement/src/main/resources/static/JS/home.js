/**
 * 
 */

 function openNav() {
    document.getElementById("mySidebar").style.width = "250px";
    document.getElementById("my-ticket-label").style.width = "250px";
    document.getElementById("main").style.marginLeft = "250px";
    document.getElementById("side-btn").style.display = "none";
   
   
}

  function closeNav() {
    document.getElementById("mySidebar").style.width = "0";
    document.getElementById("main").style.marginLeft= "0";
    document.getElementById("side-btn").style.display = "block";
    document.getElementById("my-ticket-label").style.width = "250px";
   
    
}

