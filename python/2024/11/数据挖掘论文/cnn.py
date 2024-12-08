import os

import numpy as np
import torch
from sklearn.metrics import confusion_matrix, accuracy_score
from sklearn.model_selection import train_test_split
from torch import device
from torch.utils.data import TensorDataset, DataLoader
from tqdm import tqdm
from model.CNN import CNN


def load_datasets_and_labels(base_path):
    data_folder = os.path.join(base_path, "data")
    label_files = [f for f in os.listdir(base_path) if f.endswith(".txt") and f != "README.txt"]

    all_data = []
    all_labels = []

    for label_file in tqdm(label_files):
        label_file_path = os.path.join(base_path, label_file)
        with open(label_file_path, 'r') as lf:
            for line in lf:
                # 获取数据文件相对路径和标签
                line = line.strip()
                if not line:
                    continue
                dataset_path, label = line.split()
                label = int(label)  # 转换为整数

                # 构建数据文件的完整路径
                dataset_full_path = os.path.join(dataset_path)

                # 检查文件是否存在
                if not os.path.exists(dataset_full_path):
                    print(f"Warning: File {dataset_full_path} not found. Skipping.")
                    continue

                try:
                    data_content = np.loadtxt(dataset_full_path, dtype=float)
                except Exception as e:
                    print(f"Error reading {dataset_full_path}: {e}")
                    continue

                # 存储到列表
                all_data.append(data_content)
                all_labels.append(label)


    # 转换为 NumPy array
    all_data = np.array(all_data)
    all_labels = np.array(all_labels)
    return all_data, all_labels


if __name__ == '__main__':
    # 使用路径加载
    base_path = "./DATA/CRWU_1d_2048"
    device = "cuda" if torch.cuda.is_available() else "cpu"
    data, labels = load_datasets_and_labels(base_path)

    X_train, X_test, y_train, y_test = train_test_split(data, labels, test_size=0.2, random_state=42, shuffle=True)

    # 模型搭建与训练
    print(X_train.shape, X_test.shape, y_train.shape, y_test.shape)

    model = torch.load('./pt/vgg16_bearing_full.pt').to(device)
    model.eval()

    # 将测试数据和标签转换为 TensorDataset
    X_test_tensor = torch.tensor(X_test).float()
    y_test_tensor = torch.tensor(y_test).float()
    test_dataset = TensorDataset(X_test_tensor, y_test_tensor)

    # 使用 DataLoader 创建批量加载器，批量大小为 32
    test_loader = DataLoader(test_dataset, batch_size=32, shuffle=False)

    # 模型预测
    y_pred_list = []
    y_true_list = []

    with torch.no_grad():  # 禁用梯度计算，节省显存
        for X_batch, y_batch in test_loader:
            X_batch = X_batch.to(device)
            y_batch = y_batch.to(device)

            # 模型预测
            y_pred = model(X_batch).argmax(dim=1)
            y_pred_list.append(y_pred.cpu().numpy())  # 转为 NumPy 并保存
            y_true_list.append(y_batch.cpu().numpy())

    # 将所有批次的预测结果拼接
    y_pred = np.concatenate(y_pred_list)
    y_test = np.concatenate(y_true_list)

    cnn_cm = confusion_matrix(y_test, y_pred)

    # 模型评估
    accuracy = accuracy_score(y_test, y_pred)
    print(f"cnn准确率: {accuracy:.2f}")

    # 打印混淆矩阵
    print("Confusion Matrix:")
    print(cnn_cm)