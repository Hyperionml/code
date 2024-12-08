// 1.导入fs
const fs = require('fs')

// 2.
fs.writeFile('./file/3.txt', 'ok123', function(err){
    //console.log(err);
    //文件写入成功则err的值为null

    if(err){
        return console.log('文件写入失败', + err.message);
    }

    console.log('文件写入成功！');
})