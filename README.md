### 关于

[itchat](https://github.com/littlecodersh/ItChat) in Java. 微信个人号接口，又一个轮子。此项目灵感来自 [itchat](https://github.com/littlecodersh/ItChat) 和 [itchat4j](https://github.com/yaphone/itchat4j)

### 与其他轮子的不同

与其他轮子不同，本项目更趋于“工业化”应用，其提供了强大的可扩展/编程接口，封装了复杂的内部逻辑实现，通常你只需要了解几个特定的接口类，即可编写属于自己的 Wechat Robot。它们包括
```Java
Message   // 收到消息
Send      // 消息发送
Listener  // 消息监听器
```
