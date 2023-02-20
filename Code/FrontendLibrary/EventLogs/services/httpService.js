const axios = require('axios');
const environment = require('./environment').environment;

const url = environment.url;

/**
 * An application error persists in the FrontEnd, along with its traceability and user actions.
 *
 * @param {Object} applicationError - Object representing the application error.
 * @param {Object} traceabilityError - Object representing the traceability of the error.
 * @param {Array} userActions - Array of objects representing user actions.
 *
 * @returns {Promise} A promise that resolves to the request response data.
 **/
export async function persistAplicacionErrorFrontEnd(aplicacionError,trazabilidadError,accionesUsuario) {
  const payload = {
    aplicacionError: aplicacionError,
    trazabilidadError: trazabilidadError,
    accionesUsuario: accionesUsuario
  };
  const response = await axios.post(url+'aplicacionFrontEndError/save', payload);
  return response.data;
}
/**
 * Stores traceability and user actions for a specific application error.
 *
 * @param {number} applicationError - The ID of the application error.
 * @param {Object} tracebackError - Object representing the error traceback.
 * @param {Array} useractions - Array of objects representing user actions.
 *
 * @returns {Promise} A promise that resolves to the request response data.
 */
export async function saveTrazabilitiyandUserevents(idaplicacionError,trazabilidadError,accionesUsuario) {
  const payload = {
    trazabilidadError: trazabilidadError,
    accionesUsuario: accionesUsuario
  };
  const response = await axios.post(url+'saveTrazabilitiyandUserevents/'+idaplicacionError, payload);
  return response.data;
}

