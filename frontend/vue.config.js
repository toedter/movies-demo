module.exports = {
    devServer: {
        proxy: {
            '/api': {
                target: 'http://localhost:8080',
                changeOrigin: false
            },
            '/movie-data': {
                target: 'http://localhost:8080'
            }
        }
    }
}

