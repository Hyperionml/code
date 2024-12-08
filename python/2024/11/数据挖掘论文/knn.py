import os

import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from sklearn.model_selection import train_test_split
from sklearn.naive_bayes import GaussianNB
from sklearn.neighbors import KNeighborsClassifier
from sklearn.metrics import accuracy_score
from tqdm import tqdm
from sklearn.metrics import confusion_matrix, ConfusionMatrixDisplay


def load_datasets_and_labels(base_path):
    data_folder = os.path.join(base_path, "data")
    label_files = [f for f in os.listdir(base_path) if f.endswith(".txt") and f != "README.txt"]

    all_data = []
    all_labels = []
    #count = 0

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

                # 加载数据文件内容，假设是浮点数，空格或换行分隔
                try:
                    data_content = np.loadtxt(dataset_full_path, dtype=float)
                except Exception as e:
                    print(f"Error reading {dataset_full_path}: {e}")
                    continue

                # 存储到列表
                all_data.append(data_content)  # "path": dataset_full_path,
                all_labels.append(label)

    # 转换为 NumPy array
    all_data = np.array(all_data)
    all_labels = np.array(all_labels)
    return all_data, all_labels


if __name__ == '__main__':
    # 使用路径加载
    base_path = "./DATA/CRWU_1d_2048"
    data, labels = load_datasets_and_labels(base_path)

    X_train, X_test, y_train, y_test = train_test_split(data, labels, test_size=0.2, random_state=42, shuffle=True)

    # 模型搭建与训练
    print(X_train.shape, X_test.shape, y_train.shape, y_test.shape)
    knn = KNeighborsClassifier(n_neighbors=3)  # 选择K值为3 , verbose=True
    bc = GaussianNB()
    knn.fit(X_train, y_train)
    bc.fit(X_train, y_train)

    # 模型预测
    y_pred = knn.predict(X_test)
    b_pred = bc.predict(X_test)

    knn_cm = confusion_matrix(y_test, y_pred)
    bc_cm = confusion_matrix(y_test, b_pred)

    # 模型评估
    accuracy = accuracy_score(y_test, y_pred)
    print(f"knn准确率: {accuracy:.2f}")

    bc_acc = accuracy_score(y_test, b_pred)
    print(f"bc准确率: {bc_acc:.2f}")

    # 打印混淆矩阵
    print("Confusion Matrix:")
    print(knn_cm)
    print(bc_cm)
