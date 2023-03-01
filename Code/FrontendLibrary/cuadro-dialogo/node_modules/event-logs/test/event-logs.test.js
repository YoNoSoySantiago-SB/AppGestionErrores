const events = require('../event-logs');


beforeEach(() => {
  events.clearEvents();
});

test('prueba :v', () => {


expect(userEvents[0]).toEqual({
  fechaHoraAccion: expect.any(Date),
  accionUsuario: 'Se clickeó el botón con id: testButton y con nombre: testButtonName',
  nivelError: { nombreNivel: 'Info' },
  tipoAccion: { nombreAccion: 'Boton' }
});

});

test('categorizeEvent should push an event with the correct action type and level error for button event', () => {

  events.categorizeEvent('Button clicked', 'Button');
  const userEvents=events.getLastEvents(1)

  expect(userEvents[0]).toEqual({
    fechaHoraAccion: expect.any(Date),
    accionUsuario: 'Button clicked',
    nivelError: { nombreNivel: 'Info' },
    tipoAccion: { nombreAccion: 'Boton' }
  });
});

test('categorizeEvent should push an event with the correct action type and level error for input event', () => {

  events.categorizeEvent('Se digitó: 1193033570', 'Input');
  const userEvents=events.getLastEvents(1)

  expect(userEvents[0]).toEqual({
    fechaHoraAccion: expect.any(Date),
    accionUsuario: 'Se digitó: 1193033570',
    nivelError: { nombreNivel: 'Info' },
    tipoAccion: { nombreAccion: 'Input' }
  });
});

test('categorizeEvent should push an event with the correct action type and level error for request event', () => {

  events.categorizeEvent('GET a http://localhost:8080/cars', 'Request');
  const userEvents=events.getLastEvents(1)

  expect(userEvents[0]).toEqual({
    fechaHoraAccion: expect.any(Date),
    accionUsuario: 'GET a http://localhost:8080/cars',
    nivelError: { nombreNivel: 'Info' },
    tipoAccion: { nombreAccion: 'Request' }
  });
});

test('categorizeEvent should push an event with the correct action type and level error for navigation event', () => {

  events.categorizeEvent('{from: /login to: /login}', 'Navigation');
  const userEvents=events.getLastEvents(1)

  expect(userEvents[0]).toEqual({
    fechaHoraAccion: expect.any(Date),
    accionUsuario: '{from: /login to: /login}',
    nivelError: { nombreNivel: 'Info' },
    tipoAccion: { nombreAccion: 'Navegacion' }
  });
});

test('categorizeEvent should push an event with the correct action type and level error for exception event', () => {

  events.categorizeEvent('500 internal server error', 'Exception');
  const userEvents=events.getLastEvents(1)

  expect(userEvents[0]).toEqual({
    fechaHoraAccion: expect.any(Date),
    accionUsuario: '500 internal server error',
    nivelError: { nombreNivel: 'Excepcion' },
    tipoAccion: { nombreAccion: 'Excepcion' }
  });
});

  test('getLastEvents should return the last x events generated when x < number of total events', () => {

    events.categorizeEvent('{from: /login to: /login}', 'Navigation');
    events.categorizeEvent('GET a http://localhost:8080/cars', 'Request');
    events.categorizeEvent('Se digitó: 1193033570', 'Input');
    events.categorizeEvent('500 internal server error', 'Exception');
    const userEvents=events.getLastEvents(3)
  
    expect(userEvents.length).toEqual(3);

});

test('getLastEvents should return the last x events generated when x > number of total events then x = number of total events.', () => {

  events.categorizeEvent('{from: /login to: /login}', 'Navigation');
  events.categorizeEvent('GET a http://localhost:8080/cars', 'Request');
  events.categorizeEvent('Se digitó: 1193033570', 'Input');
  const userEvents= events.saveError('500 internal server error', 'Exception');

  expect(userEvents.length).toEqual(4);

});

test('pushEvent should store all events in the array.', () => {
  let tipoAccion;
  let nivelError;
  tipoAccion= {
    nombreAccion:"Excepcion"
  }
  nivelError= {
    nombreNivel:"Excepcion"
  }

  const accionUsuario = {
    fechaHoraAccion: new Date(),
    accionUsuario:'500 internal server error',
    nivelError,
    tipoAccion
  }; 
  events.pushEvent(accionUsuario);
  const userEvents=events.getLastEvents(1)

  expect(userEvents.length).toEqual(1);

});

test('saveRequestHTTP should receive an http request and send it to categorize, so it is saved in the event list.', () => {

  events.saveRequestHTTP('GET a http://localhost:8080/cars');
  const userEvents=events.getLastEvents(1)

  expect(userEvents[0]).toEqual({
    fechaHoraAccion: expect.any(Date),
    accionUsuario: 'GET a http://localhost:8080/cars',
    nivelError: { nombreNivel: 'Info' },
    tipoAccion: { nombreAccion: 'Request' }
  });
});

test('saveNavigation should receive an http request and send it to categorize, so it is saved in the event list.', () => {

  events.saveNavigation('{from: /login to: /login}');
  const userEvents=events.getLastEvents(1)

  expect(userEvents[0]).toEqual({
    fechaHoraAccion: expect.any(Date),
    accionUsuario: '{from: /login to: /login}',
    nivelError: { nombreNivel: 'Info' },
    tipoAccion: { nombreAccion: 'Navegacion' }
  });
});