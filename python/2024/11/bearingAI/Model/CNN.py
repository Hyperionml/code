import torch


class CNN(torch.nn.Module):
    def __init__(self, conv_archs, num_classes=10, input_channels=1):
        super(CNN, self).__init__()
        # CNN参数
        self.conv_archs = conv_archs  # 网络结构
        self.input_channels = input_channels
        self.features = self.make_layers()
        self.avgpool = torch.nn.AdaptiveAvgPool1d(9)  # 9
        # 定义全连接层
        self.classfier = torch.nn.Sequential(
            torch.nn.Linear(512 * 9, 500),
            torch.nn.ReLU(inplace=True),
            torch.nn.Dropout(0.5),
            torch.nn.Linear(500, num_classes)
        )

    # 卷积池化结构
    def make_layers(self):
        layers = []
        for (num_convs, out_channels) in self.conv_archs:
            for _ in range(num_convs):
                layers.append(torch.nn.Conv1d(self.input_channels, out_channels, kernel_size=3, padding=1))
                layers.append(torch.nn.ReLU(inplace=True))
                self.input_channels = out_channels
            layers.append(torch.nn.MaxPool1d(kernel_size=2, stride=2))
        return torch.nn.Sequential(*layers)

    # 定义前向传播
    def forward(self, input_seq):
        # 改变输入形状，适应网络输入 （batch,H_in,seq_length）
        input_seq = input_seq.view(-1, 1, 2048)
        features = self.features(input_seq)
        X = self.avgpool(features)
        flat_tensor = X.view(X.shape[0], -1)
        output = self.classfier(flat_tensor)
        return output