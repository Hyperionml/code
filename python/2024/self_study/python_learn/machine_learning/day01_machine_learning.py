from sklearn.datasets import load_iris
from sklearn.model_selection import train_test_split

def datasets_demo():
    """
    seklearn数据集使用
    :return:
    """
    #获取数据集
    iris = load_iris()
    print("鸢尾花数据集：\n", iris)
    print("查看数据集描述：\n", iris.DESCR)
    print("查看特征值的名字：\n", iris.feature_names)
    print("查看特征值：\n", iris.data, iris.data.shape)

    #数据集划分
    train_X, test_X, train_y, test_y = train_test_split(iris.data, iris.target, test_size=0.2, random_state=22)
    print("训练集的特征值：\n", train_X, train_X.shape)
    return None

if __name__ == '__main__':
    #代码1：sklearn数据集使用
    datasets_demo()