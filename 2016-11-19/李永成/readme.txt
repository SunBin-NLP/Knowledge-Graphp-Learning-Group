1、Java实现
2、统计FB15k数据集中的关系类型
3、关系理解
1-to-1 	该关系下，头、尾实体一对一，即已知关系和其中一个，另一个是唯一。例：首都	<中国, 首都, 北京>.

1-to-N	该关系下，头、尾实体一对多，即已知关系和头实体，尾实体有多个，已知关系和尾实体，头实体固定唯一。例：父子	<曹操, 父子, 曹丕>、<曹操, 父子, 曹植>.

N-to-1	该关系下，头、尾实体多对一，即已知关系和头实体，尾实体唯一，已知关系和尾实体，头实体有多个。例：性别	<曹操, 性别, 男>、<曹丕, 性别, 男>.

N-to-N	该关系下，头、尾实体多对多，即已知关系和头实体，尾实体有多个，已知关系和尾实体，头实体有多个。例：兄弟	<曹丕, 兄弟, 曹植>、<曹丕, 兄弟, 曹冲>、<曹植, 兄弟, 曹彰>.

	<曹彰, 兄弟, 曹植>
4、统计方法：For each relation r, we compute averaged number of tails per head (tphr ), averaged number of head per tail (hptr ). If tphr < 1.5 and hptr < 1.5, r is treated as one-to-one. If tphr ≥ 1.5 and hptr ≥ 1.5, r is treated as a many-to-many. If hptr < 1.5 and tphr ≥ 1.5, r is treated as one-to-many. If hptr ≥ 1.5 and tphr < 1.5, r is treated as many-to-one.