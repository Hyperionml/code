module.exports = {
  pages: {
    index: {
      entry:'src/main.js',

    }
  },
  lintOnSave: false,
  devServer: {
    proxy: {
      '/api': {
        target: 'http://8.138.5.73:10001',
        pathRewrite: {'^/api':''},
        ws: true,
        changeOrigin:true
      }
    }
  }
}
