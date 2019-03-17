module.exports = {
    devServer: {
        proxy: {
            '/movies': {
                target: 'http://localhost:8080',
                ws: true,
                changeOrigin: true
            },
            '/movie-data': {
                target: 'http://localhost:8080',
                ws: true,
                changeOrigin: true
            }
        }
    }
}

