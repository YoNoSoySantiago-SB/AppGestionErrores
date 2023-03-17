const events = require('../event-logs');
const { String } = require('core-js');

beforeEach(() => {
  events.clearEvents();
});


test('categorizeEvent should push an event with the correct action type and level error for button event', () => {

  events.categorizeEvent('Button clicked', 'Button');
  const userEvents=events.getLastEvents(1)

  expect(userEvents[0]).toEqual({
    fechaHoraAccion: expect.any(String),
    accionUsuario: 'Button clicked',
    nombreNivel: 'Info' ,
    nombreAccion: 'Boton' 
  });
});

test('categorizeEvent should push an event with the correct action type and level error for input event', () => {

  events.categorizeEvent('Se digitó: 1193033570', 'Input');
  const userEvents=events.getLastEvents(1)

  expect(userEvents[0]).toEqual({
    fechaHoraAccion: expect.any(String),
    accionUsuario: 'Se digitó: 1193033570',
    nombreNivel: 'Info' ,
    nombreAccion: 'Input' 
  });
});

test('categorizeEvent should push an event with the correct action type and level error for request event', () => {

  events.categorizeEvent('GET a http://localhost:8080/cars', 'Request');
  const userEvents=events.getLastEvents(1)

  expect(userEvents[0]).toEqual({
    fechaHoraAccion: expect.any(String),
    accionUsuario: 'GET a http://localhost:8080/cars',
    nombreNivel: 'Info' ,
    nombreAccion: 'Request' 
  });
});

test('categorizeEvent should push an event with the correct action type and level error for navigation event', () => {

  events.categorizeEvent('{from: /login to: /login}', 'Navigation');
  const userEvents=events.getLastEvents(1)

  expect(userEvents[0]).toEqual({
    fechaHoraAccion: expect.any(String),
    accionUsuario: '{from: /login to: /login}',
    nombreNivel: 'Info' ,
    nombreAccion: 'Navegacion' 
  });
});

test('categorizeEvent should push an event with the correct action type and level error for exception event', () => {

  events.categorizeEvent('500 internal server error', 'Exception');
  const userEvents=events.getLastEvents(1)

  expect(userEvents[0]).toEqual({
    fechaHoraAccion: expect.any(String),
    accionUsuario: '500 internal server error',
    nombreNivel: 'Excepcion' ,
    nombreAccion: 'Excepcion' 
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
  const accionUsuario = {
    fechaHoraAccion: '2023-03-01T22:43:16.851-05:00[America/Bogota]',
    accionUsuario:'500 internal server error',
    nombreAccion:"Excepcion",
    nombreNivel:"Excepcion"
  }; 
  events.pushEvent(accionUsuario);
  const userEvents=events.getLastEvents(1)

  expect(userEvents.length).toEqual(1);

});

test('saveRequestHTTP should receive an http request and send it to categorize, so it is saved in the event list.', () => {

  events.saveRequestHTTP('GET a http://localhost:8080/cars');
  const userEvents=events.getLastEvents(1)

  expect(userEvents[0]).toEqual({
    fechaHoraAccion: expect.any(String),
    accionUsuario: 'GET a http://localhost:8080/cars',
    nombreNivel: 'Info' ,
    nombreAccion: 'Request' 
  });
});

test('saveNavigation should receive an http request and send it to categorize, so it is saved in the event list.', () => {

  events.saveNavigation('{from: /login to: /login}');
  const userEvents=events.getLastEvents(1)

  expect(userEvents[0]).toEqual({
    fechaHoraAccion: expect.any(String),
    accionUsuario: '{from: /login to: /login}',
    nombreNivel: 'Info' ,
    nombreAccion: 'Navegacion' 
  });
});