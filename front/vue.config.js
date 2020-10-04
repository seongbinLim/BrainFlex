const DisURL = 'http://j3c105.p.ssafy.io';
const LocalURL = 'http://localhost:8080';
var isLocal = false;

var API_URL = DisURL;
if (isLocal) {
  API_URL = LocalURL;
}

module.exports = {
    transpileDependencies: ['vuetify'],

    devServer: {
        port: 3000,
        proxy: {
            '/api': {
                target: API_URL,
                ws: true,
                changeOrigin: true,
            },
        },
    },
};