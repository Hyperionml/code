import torch
import time
from utils import MyUtils
from apscheduler.schedulers.background import BackgroundScheduler
from sqlalchemy import create_engine, Column, Integer, String
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker
from Model.CNN import CNN


# 创建数据库连接引擎
engine = create_engine('mysql+pymysql://root:Herobrine717hak.maimaiDXisNiceW6@8.138.5.73/db_bearing', echo=True)
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


def my_task(model):
    print("任务正在运行...")
    # 获取当前的日期和时间
    # now = datetime.now() - timedelta(seconds=2)
    # current_datetime = now.strftime("%Y%m%d/%H%M/%S")
    now = MyUtils.time
    path = MyUtils.linuxPath + now + str(MyUtils.count)
    print(path)
    # 指定文件路径
    # file_path = 'D:/TransitionalFiles/test/20241031/2145/012'

    txt = MyUtils.read_all_txt_files_in_directory(path)

    txt_tensor = torch.tensor([float(x) for x in txt.split(" ")[:-1]], dtype=torch.float32)
    txt_tensor = txt_tensor.view(-1, 1, 2048)

    device = "cuda" if torch.cuda.is_available() else "cpu"
    txt_tensor = txt_tensor.to(device)

    output = torch.argmax(model(txt_tensor),dim=1)
    output = list(output)
    print(output)

    for index, i in enumerate(output):
        code = int(i.item())
        bearing_to_update = session.query(Bearing).filter_by().all()[index]
        if code == 0:
            # 更新记录
            bearing_to_update.status = 'normal'
            bearing_to_update.msg = None
            session.commit()
        elif code != 0:
            # 更新记录
            bearing_to_update.status = 'err'
            bearing_to_update.msg = MyUtils.errCode2str(code)
            session.commit()

    MyUtils.count += 1


if __name__ == '__main__':
    model = torch.load('pt/vgg16_bearing_full.pt', map_location=torch.device('cpu'))
    model.eval()

    scheduler = BackgroundScheduler()
    scheduler.add_job(my_task, 'interval', seconds=1, args=(model,))
    scheduler.start()

    try:
        while True:
            time.sleep(2)
    except (KeyboardInterrupt, SystemExit):
        scheduler.shutdown()


