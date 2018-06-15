client3和client4两个项目配合使用，作为两个相互通讯的client。
client3向主题tsp/tbox/#发布消息。
client4订阅主题tsp/tbox/#的消息。
client4收到主题tsp/tbox/#的消息后，
会随机向以下三个主题中的一个：
client/tbox/sn0/aa、client/tbox/sn1/aa、client/tbox/sn2/aa
发布消息。

client3会订阅client/tbox/sn1/aa中的消息。

这两个项目模拟了车机和平台间的通讯过程。











