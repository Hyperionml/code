const fs = require('fs');

fs.readFile('./file/1.txt', 'utf8', function(err, dataStr){
    if(err){
        return console.log('读取文件失败' + err.message);
    }
    console.log('读取文件成功！' + dataStr);
})