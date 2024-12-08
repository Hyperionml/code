const fs = require('fs')

//出现路径错误的问题是因为使用了相对路径
/* fs.readFile('./file/1.txt', 'utf8', function(err, dataStr){
    if(err){
        return console.log('文件读取失败！' + err.message);
    }

    console.log('文件读取成功！' + dataStr);
}) */


//移植性非常差，且不易维护
/* fs.readFile('D:\\workspace\\Frontend\\2024\\selfStudy\\file\\1.txt', 'utf8', function(err, dataStr){
    if(err){
        return console.log('文件读取失败！' + err.message);
    }

    console.log('文件读取成功！' + dataStr);
}) */


//console.log(__dirname);
fs.readFile(__dirname + '/file/1.txt', 'utf8', function(err, dataStr){
    if(err){
        return console.log('文件读取失败！' + err.message);
    }

    console.log('文件读取成功！' + dataStr);
}) 