/**
 * An array that contains user events.
 * @type {Array}
 */
let events =  [];

/**
 * The amount of user events to be retrieved.
 * @type {number}
 */
let amount = 20;

/**
 * Anonymous function that adds two event listeners to the window to register click and text input events.
 */
(function() { 
  /**
   * Event listener for button click event or for the text input event on an input.
   * Logs the ID and name of the button clicked, and sends to categorise the event as a "Button" or logs the value and ID of the input and sends to categorise the event as an "Input".
   *
   * @param {Object} e - The object of the click event or of the text input (blur) event.
   */
  window.addEventListener('click', function (e) {
    if (e.target.tagName === 'BUTTON') {
      if(e.target.id!='description-dialog-errores' && e.target.id!='email-dialog-errores' && e.target.id!='name-dialog-errores'){
        let buttonEventDescription = "Se clickeó el botón con id: " + (e.target.id || "Desconocido") + " y con nombre: " + (e.target.name || "Desconocido");
        categorizeEvent(buttonEventDescription,"Button");
      }
    }
    if (e.target.tagName === 'INPUT') {
      if(e.target.id!='reportar-dialog-errores' && e.target.id!='noreportar-dialog-errores' ){
        if (e.target.dataset.inputType === "false") {
          e.target.addEventListener('blur', function (e) {
            let inputEventDescription= '[Filtrado]';
            categorizeEvent(inputEventDescription,"Input")
          });
        }else{
          e.target.addEventListener('blur', function (e) {
            let inputEventDescription = e.target.value ? "Se digitó: " + e.target.value + " en el input con id " (e.target.id || "Desconocido") : "No se digitó nada";
            categorizeEvent(inputEventDescription,"Input")
          });
        }
      }
    }
  });

  })();
  
  /**
  * Categorises the logged event, whether it was a button click, an http request, a navigation, an exception or a write to an input.
  *
  * @param {string} description - Description of the event.
  * @param {string} type  - The type of event.
  */
  export function categorizeEvent(description,type) {
    let nombreAccion='';
    let nombreNivel='';
    if(type==="Button"){
      nombreAccion="Boton"
      nombreNivel="Info"
    }else if(type==="Input"){
      nombreAccion="Input"
      nombreNivel="Info"
    }else if(type==="Request"){
      nombreAccion="Request"
      nombreNivel="Info"
    }else if(type==="Navigation"){
      nombreAccion="Navegacion"
      nombreNivel="Info"
    }
    else if(type==="Exception"){
      nombreAccion="Excepcion"
      nombreNivel="Excepcion"
    }
    const accionUsuario = {
      fechaHoraAccion: new Date(),
      accionUsuario:description,
      nombreNivel:nombreNivel,
      nombreAccion:nombreAccion
    };  
    pushEvent(accionUsuario);
  }

  /**
  * Returns the last events in the specified amount.
  *
  * @param {number} amount - The number of events to retrieve.
  * @returns {array} - An array of the last events in the specified amount.
  */
  export function getLastEvents(amount) {
    if (amount >= events.length) {
      return events;
    }else{
      return events.slice(-amount);;
    }
  }

  /**
  * Receives an http request and sends it to categorise it.
  *
  * @param {string} request - The string with the http method and the url.
  */
  export function saveRequestHTTP(request){
    categorizeEvent(request,"Request")
  }

  /**
  * Receives the generated error and sends it to categorise.
  *
  * @param {string} error - Controlled or uncontrolled error.
  * @returns {array} - An array of the last events.
  */
  export function saveError(error){
    categorizeEvent(error,"Exception");
    let savedEvents = getLastEvents(amount);
    let copiedEvents = [...savedEvents];
    clearEvents()
    return copiedEvents;
  }

  /**
  * Receives page navigation and sends it to categorise it.
  *
  * @param {string} navigation - The string with the navigation page url.
  */
  export function saveNavigation(navigation){
    categorizeEvent(navigation,"Navigation")
  }

  /**
  * Adds a user event to the list.
  *
  * @param {Object} event - The user-generated event
  */
  export function pushEvent(event) {
    events.push(event);
  }
  /**
  * Clear the list of events to test it.
  *
  */
  export function clearEvents() {
    events = [];
  }