class View{
    constructor(){
      window.addEventListener("hashchange",e =>this.onRouteChange(e));
  
    }
     onRouteChange(e){
      const url=window.location.hash;
      console.log("url "+url)
    }
  }
  new View();