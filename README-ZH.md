# emoji-edit

emoji-edit是一个基于Xposed类框架的插件，能够在任何输入框中根据你的输入将文本替换成合适的emoji符号。

![1](img/1.gif)

## 依赖

- 已Root并安装了Magisk的安卓手机
- Riru模块
- LSPosed模块

## 使用教程

- 下载Release中的安装包并安装
- 在LSPosed管理器中激活emoji-edit模块
- 在模块中勾选需要使用此模块的应用
- 重启勾选的应用
- 在应用信息中允许emoji-edit自启动，并将emoji-edit在后台锁定
- 愉快的使用

## 兼容性

目前仅有较少的系统经过测试，包括

- [x] H<sub>2</sub>OS		API29
- [x] MIUI		API28

如果emoji-edit在您的系统上正常运行，你可以在discussion下回复。

如果emoji-edit在您的系统上，或者在某个应用中发生异常，欢迎提出issue，并附上错误发生前的LSPosed日志。

## FAQ

我可以使用其他的Xposed类框架吗？

- 其他的Xposed类框架（比如EdXposed、太极）理论上也能生效。但是没有作用域的限制，emoji-edit将在您的所有应用程序中生效，这可能导致兼容性和稳定性上的问题。

这个模块会造成系统变慢/耗电增加吗？

- 在开发过程中尽可能考虑了这方面的问题，目前emoji-edit对系统的影响和耗电都是极低的。

emoji-edit问什么要常驻后台？通知栏的通知是什么？

- 为了提升运行速度，emoji-edit启动了一个本地查询服务，因此必须要有一个服务常驻后台。通知栏的通知就是这个服务。如果您允许了开机自启动，当您第一次重启后，emoji-edit的app便可不需要运行，仅有服务停留在后台。

为什么我有一些emoji显示不出来/乱码

- 在部分安卓系统上emoji字库不完整，您可以安装这个[Magisk模块](https://github.com/Keinta15/Magisk-iOS-Emoji)来补全您的字库。