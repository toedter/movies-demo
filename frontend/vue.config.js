module.exports = {
    devServer: {
        proxy: {
            '/movies': {
                target: 'http://localhost:8080',
                changeOrigin: false
            },
            '/directors': {
                target: 'http://localhost:8080',
                changeOrigin: false
            },
            '/movie-data': {
                target: 'http://localhost:8080'
            }
        }
    }
}

