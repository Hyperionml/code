from glob import glob
import numpy as np
from tqdm.auto import tqdm
import json

data_list = glob("./DATA/CRWU_1d_2048/data/*/*.txt")

data = np.zeros((120000,2048))
for idx, data_path in enumerate(tqdm(data_list)):
    with open(data_path, 'r') as r:
        txt_content = r.read()

    arr = [float(x) for x in txt_content.split()]
    data[idx, :] = arr

mean = data.mean(axis=0)
std = data.std(axis=0)
mean = list(mean)
std = list(std)

ret = {"mean": mean, "std": std}
print(ret)
with open("./z-score.json", 'w') as w:
    w.write(json.dumps(ret))
print("finished")