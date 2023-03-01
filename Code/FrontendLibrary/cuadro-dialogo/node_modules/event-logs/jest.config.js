module.exports = {
  transform: {
    "^.+\\.js$": "babel-jest",
  },
  testEnvironment: 'jsdom',
  setupFiles: ['text-encoding']

};
