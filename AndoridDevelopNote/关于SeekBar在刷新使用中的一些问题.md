# Android�����ʼǣ�����SeekBar��ˢ��ʹ���е�һЩ����    

## ���⣺  
   ��������Navigation ������Fragment֮�䵼��ʱ�����˴ӵڶ���Fragment���ص���һ��FragmentʱSeekBar�������ݶ�ʧ�ͽ�������ˢ�µ������   

   ����Demo���ڵ�һ��Fragemnt��ͨ���������ı�ViewModel�е����ݣ����ڵ����ť��
   ��������ڶ���Fragment���ڵڶ���Fragment�м����ı�����ݣ�Ȼ����ͨ�����ؼ���
   �ص�һ��Fragment����ˢ�½����������ݡ�

   ������ʵViewModel�е���������ȷ�ĵġ�   

## �������⣺  
   ͨ�������־��SeekBar��صķ�����ִ��������з����󣬷����ڶ�SeekBar���ü���
   ���󣬵�һ�δ���Fragment�е���ͼ��SeekBar�еļ��������� 

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)

   �ܻ���ִ��һ�Σ����Ҹ÷����е�progressֵ��Ϊ��ǰ��ֵ��������UIҲ��Ȼ��֮ǰ�Ľ��ȡ�   ��֮������ͬ���ķ�������һЩ���⡣

## ���������   
   ��Fragment�����һ����־λ���ж�onProgressChanged�Ƿ��ǵ�һ��ִ�У����ǵ�һ��ִ�У�����SeekBar�ж�ȡviewModel�е�ֵ��������onProgressChanged�������ֶ����Ľ�������   

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
                                

   -���򣬾ͽ���������ֵ��ȡ������ViewModel�е�ֵ��        