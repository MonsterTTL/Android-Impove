# Android开发笔记：关于SeekBar在刷新使用中的一些问题    

## 问题：  
   今天在用Navigation 在两个Fragment之间导航时发现了从第二个Fragment返回到第一个Fragment时SeekBar出现数据丢失和进度条不刷新的情况。   

   具体Demo是在第一个Fragemnt中通过滑动条改变ViewModel中的数据，并在点击按钮后
   导航进入第二个Fragment，在第二个Fragment中继续改变该数据，然后在通过返回键返
   回第一个Fragment并且刷新进度条和数据。

   但是其实ViewModel中的数据是正确的的。   

## 分析问题：  
   通过添加日志对SeekBar相关的方法的执行情况进行分析后，发现在对SeekBar设置监听
   器后，第一次创建Fragment中的视图中SeekBar中的监听方法： 

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)

   总会先执行一次，而且该方法中的progress值仍为先前的值，进度条UI也仍然是之前的进度。   总之在数据同步的方面总有一些问题。

## 解决方法：   
   在Fragment中添加一个标志位来判断onProgressChanged是否是第一次执行，若是第一次执行，就在SeekBar中读取viewModel中的值，并且在onProgressChanged方法中手动更改进度条：   

        seekBar.setOnSeekBarChangeListener(new SeekBar.
        OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, 
            boolean fromUser) {
                if(isSeekBarFirst){
                    isSeekBarFirst = false;
                    seekBar.setProgress(initData);
                }else {
                    viewModel.getNumber().setValue(seekBar.getProgress());
                }
            }    
                                

   -否则，就将进度条的值读取并更改ViewModel中的值。        