%先用矩阵存储实验得到的数据
%本机通信得到的数据
Local_256kb_time = [6,5,4,5];%本机通信-256kb的数据量下的用时-单位：ms
Local_256kb_lose = [0,0,0,0];%本机通信256KB数据量下的丢包率
%1MB数据量下
Local_1MB_time = [17,18,17,16];%同理，1MB情况下的用时
Local_1MB_lose = [0,0,0,0];%丢包率
%16MB数据量下
Local_16MB_time = [187,196,180,192];
Local_16MB_lose = [0,0,0,0];
%256MB数据量下
Local_256MB_time = [2466,2557,2408,2475];
Local_256MB_lose = [0,0,0.01,0];

%Android-本机通信下的实验得到的数据
%256kb
Android_256kb_time = [62,141,63,199];
Android_256kb_lose = [0.238,0.281,0.136,0];
%1MB
Android_1MB_time = [409,764,811,1093];
Android_1MB_lose = [0.69,0.301,0.517,0.257];
%16MB
Android_16MB_time = [20199,9778,16861,20569];
Android_16MB_lose = [0.017,0.482,0.141,0.203];
%256MB
Android_256MB_time = [85221,155602,181999,58937];
Android_256MB_lose = [0.803,0.675,0.664,0.912];

%接下来开始进行数据分析

%先进行本机通信的数据分析

%先计算各个数据量下的平均用时和平均丢包率
Local_256kb_aveTime = mean(Local_256kb_time);
Local_1MB_aveTime = mean(Local_1MB_time);
Local_16MB_aveTime = mean(Local_16MB_time);
Local_256MB_aveTime = mean(Local_256MB_time);
%丢包率
Local_256kb_aveLose = mean(Local_256kb_lose);
Local_1MB_aveLose = mean(Local_1MB_lose);
Local_16MB_aveLose = mean(Local_16MB_lose);
Local_256MB_aveLose = mean(Local_256MB_lose);
%用于初始化数据量的坐标 坐标值= 数据量/256（为了画图化的下）
DataIndex = [256/256,1024/256,1024*16/256,1024*256/256];

%用于画图的矩阵
darwMar_time = [Local_256kb_aveTime,Local_1MB_aveTime,Local_16MB_aveTime,Local_256MB_aveTime];
darwMar_lose = [Local_256kb_aveLose,Local_1MB_aveLose,Local_16MB_aveLose,Local_256MB_aveLose];
%开始画图
subplot(2,2,1);
plot(DataIndex,darwMar_time);
title("本地通信-时间");
subplot(2,2,2);
axis([0 1024 0 1]);
plot(DataIndex,darwMar_lose);
title("本地通信-丢包率");

%接下来进行双机通信的数据分析
Android_256kb_aveTime = mean(Android_256kb_time);
Android_1MB_aveTime = mean(Android_1MB_time);
Android_16MB_aveTime = mean(Android_16MB_time);
Android_256MB_aveTime = mean(Android_256MB_time);
%丢包率
Android_256kb_aveLose = mean(Android_256kb_lose);
Android_1MB_aveLose = mean(Android_1MB_lose);
Android_16MB_aveLose = mean(Android_16MB_lose);
Android_256MB_aveLose = mean(Android_256MB_lose);
darwMar_time = [Android_256kb_aveTime,Android_1MB_aveTime,Android_16MB_aveTime,Android_256MB_aveTime];
darwMar_lose = [Android_256kb_aveLose,Android_1MB_aveLose,Android_16MB_aveLose,Android_256MB_aveLose];
%开始画图
subplot(2,2,3);
plot(DataIndex,darwMar_time,'-ro','LineWidth',1.5);
title("双机通信-时间");
subplot(2,2,4);
plot(DataIndex,darwMar_lose,'-ro','LineWidth',1.5);
title("双机通信-丢包率");



