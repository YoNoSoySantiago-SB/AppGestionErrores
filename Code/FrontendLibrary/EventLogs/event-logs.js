const events = [];
const amount = 20;

(function() { 
  window.addEventListener('click', function (e) {
    if (e.target.tagName === 'BUTTON') {
        let buttonEventDescription="Se clickeo el botón con id: " + (e.target.id || "Desconocido") +
        "\nInformación Adicional: " +
        "\nName: " + (e.target.name || "Desconocido") +
        "\nType: " + (e.target.type || "Desconocido")+
        "\nText Content: " + (e.target.textContent || "Desconocido");
       categorizeEvent(buttonEventDescription,"Button")
       ;
    }
    if (e.target.tagName === 'INPUT') {
      console.log(typeof e.target.dataset.inputType);
      if (e.target.dataset.inputType === "false") {
        e.target.addEventListener('blur', function (e) {
          let inputEventDescription= '[Filtrado]';
          categorizeEvent(inputEventDescription,"Input")
        });
      }else{
        e.target.addEventListener('blur', function (e) {
          let inputEventDescription="Se digitó el input: " + (e.target.value ? e.target.value : "No se digitó nada") +
          "\nInformación Adicional: " +
          "\nId: " + (e.target.id || "Desconocido")
          categorizeEvent(inputEventDescription,"Input")
        });
      }
    }
  });

  })();
  
  function categorizeEvent(event,type) {
    let level;
    let action;
    if(type==="Button"){
      level=2
      action=5
    }else if(type==="Input"){
      level=2
      action=4
    }else if(type==="Request"){
      level=2
      action=3
    }else if(type==="Navigation"){
      level=2
      action=2
    }
    else if(type==="Exception"){
      level=1
      action=1
    }
    const eventObject = {
      nombre_accion:action,
      accion_usuario:event,
      level,
      fecha_hora_accion: new Date(),
    };  
    pushEvent(eventObject);
  }

  function getLastEvents(amount) {
    if (amount >= events.length) {
      return events;
    }else{
      return events.slice(-amount);
    }
  }
/**
 * Esta función obtiene la peticion http y la envia a categorizar
 * @param {string} request - Peticion http enviada al servidor.
 * @returns {void}
 */
  export function saveRequestHTTP(request){
    categorizeEvent(request,"Request")
  }

  export function saveError(error){
    categorizeEvent(error,"Exception")
    saveEvent()
    events.splice(0, events.length);
  }

  export function saveNavigation(navigation){
    categorizeEvent(navigation,"Navigation")
  }

  function pushEvent(event) {
    events.push(event);
  }

  async function saveEvent() {
    // Obtener los últimos eventos
    const events = getLastEvents(amount);
  
    // Conectar a la base de datos
    //await client.connect();
  
      // Iterar sobre los eventos y guardarlos en la base de datos
      for (const event of events) {
        /*await client.query({
          text: 'INSERT INTO accion_usuario(id_aplicacion_error, id_nivel_error,id_tipo_accion, fecha_hora_accion, accion_usuario) VALUES($1, $2, $3, $4, $5)',
          values: [id, event.level, event.nombre_accion, event.fecha_hora_accion, event.accion_usuario]
        });*/
        console.log(event)
      }
  }