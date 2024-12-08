// 1.导入fs模块
const fs = require('fs');


//2.调用fs.readFile()
fs.readFile('./file/1.txt', 'utf-8', function(err, dataStr){
    console.log(err);
    console.log('----------');
    console.log(dataStr);
})