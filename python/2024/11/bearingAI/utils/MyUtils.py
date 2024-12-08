import glob
import os

time = '20241031/1346/'

testPath = 'D:/TransitionalFiles/test/'

linuxPath = '/root/workspace/bearingData/'

count = 0


def read_all_txt_files_in_directory(directory):
    # 使用glob模块查找所有.txt文件
    txt_files = sorted(glob.glob(os.path.join(directory, '*.txt')))

    all_contents = ''

    for txt_file in txt_files:
        with open(txt_file, 'r', encoding='utf-8') as file:
            # 读取文件内容并添加到列表中
            contents = file.read()
            all_contents += (contents)

    return all_contents


def errCode2str(errCode):
    if errCode == 1:
        return '内圈故障0.007英寸'
    elif errCode == 2:
        return '内圈故障0.014英寸'
    elif errCode == 3:
        return '内圈故障0.021英寸'
    elif errCode == 4:
        return '外圈故障0.007英寸'
    elif errCode == 5:
        return '外圈故障0.014英寸'
    elif errCode == 6:
        return '外圈故障0.021英寸'
    elif errCode == 7:
        return '滚动体故障0.007英寸'
    elif errCode == 8:
        return '滚动体故障0.014英寸'
    else:
        return '滚动体故障0.021英寸'
