import glob
import os
import threading
from datetime import datetime, timedelta
import torch
import sys
from sqlalchemy import create_engine, Column, Integer, String
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker

sys.path.append("D:/workspace/python/2024/11/bearingAI/")
from Model.CNN import CNN

# 创建数据库连接引擎
engine = create_engine('mysql+pymysql://root:1234@127.0.0.1/db_bearing', echo=True)

# 创建基类
Base = declarative_base()


# 定义模型（表结构）
class Bearing(Base):
    __tablename__ = 'bearing'

    sid = Column(Integer, primary_key=True, autoincrement=True)
    name = Column(String(50))
    status = Column(String(20))
    msg = Column(String(100))


# 创建会话
Session = sessionmaker(bind=engine)
session = Session()


def my_task():
    print("任务正在运行...")
    # 获取当前的日期和时间
    now = datetime.now() - timedelta(seconds=2)
    current_datetime = now.strftime("%Y%m%d/%H%M/%S")
    path = 'D:/TransitionalFiles/test/' + current_datetime
    # 打印当前的日期和时间
    print(path)
    # 递归调用以模拟周期性任务
    threading.Timer(1, my_task).start()


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


if __name__ == '__main__':
    model = torch.load('../pt/vgg16_bearing_full.pt')
    model.eval()

    file_path = 'D:/TransitionalFiles/test/20241031/2145/000'

    txt = read_all_txt_files_in_directory(file_path)

    txt_tensor = torch.tensor([float(x) for x in txt.split(" ")[:-1]], dtype=torch.float32)
    txt_tensor = txt_tensor.view(-1, 1, 2048)
    device = "cuda" if torch.cuda.is_available() else "cpu"
    txt_tensor = txt_tensor.to(device)

    output = torch.argmax(model(txt_tensor),dim=1)
    print(model(txt_tensor))
    output = list(output)
    print(output)

    for index, i in enumerate(output):
        code = int(i.item())
        if code == 0:
            # 更新记录
            bearing_to_update = session.query(Bearing).filter_by().all()[index]
            bearing_to_update.status = 'normal'
            bearing_to_update.msg = None
            session.commit()
        elif code != 0:
            # 更新记录
            bearing_to_update = session.query(Bearing).filter_by().all()[index]
            bearing_to_update.status = 'err'
            if bearing_to_update.msg is None:
                bearing_to_update.msg = code
            else:
                bearing_to_update.msg = str(code)
            session.commit()

