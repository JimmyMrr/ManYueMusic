package com.example.ritchie_huang.manyuemusic.Lyric;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.ritchie_huang.manyuemusic.Lyric.LyricContent;
import com.example.ritchie_huang.manyuemusic.R;

import java.util.List;

/**
 * Created by ritchie-huang on 16-8-22.
 */
public class LyricView extends TextView {

    private float width;
    private float height;
    private Paint currentPaint;             //用来描绘当前正在播放的那句歌词
    private Paint notCurrentPaint;          //用来描绘非当前歌词
    private float textHeight = 50;
    private float textSize = 30;
    private int index = 0;                  //当前歌词的索引



    private List<LyricContent> myLyricList = null;        //每个LyricCOntent对应着一句话,这个List就是整个解析后的歌词文件


    public void setIndex(int index){
        this.index = index;
    }


    public void setMyLyricList(List<LyricContent> lyricList){
        this.myLyricList = lyricList;
    }


    public List<LyricContent> getMyLyricList(){
        return this.myLyricList;
    }


    public LyricView(Context context){
        super(context);
        init();
    }


    public LyricView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        init();
    }


    public LyricView(Context context, AttributeSet attributeSet, int defSytle){
        super(context,attributeSet,defSytle);
        init();
    }


    private void init(){                            //初始化画笔
        setFocusable(true);


        currentPaint = new Paint();
        currentPaint.setAntiAlias(true);
        currentPaint.setTextAlign(Paint.Align.CENTER);


        notCurrentPaint = new Paint();
        notCurrentPaint.setAntiAlias(true);
        notCurrentPaint.setTextAlign(Paint.Align.CENTER);


    }

    /*
    onDraw()就是画歌词的主要方法了
    在PlayFragment中会不停地调用
    lyricView.invalidate();这个方法
    此方法写在了一个Runnable的run()函数中
    通过不断的给一个handler发送消息,不断的重新绘制歌词
    来达到歌词同步的效果
     */


    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        if(canvas == null){
            return ;
        }


        currentPaint.setColor(getResources().getColor(R.color.test_color));
        notCurrentPaint.setColor(getResources().getColor(R.color.white_text));


        currentPaint.setTextSize(40);
        currentPaint.setTypeface(Typeface.DEFAULT_BOLD);


        notCurrentPaint.setTextSize(textSize);
        notCurrentPaint.setTypeface(Typeface.DEFAULT);


        try{
            setText("");


            float tempY = height / 2;                                                                     //画出之前的句子
            for(int i =index - 1;i >= 0; i --){
                tempY -= textHeight;
                canvas.drawText(myLyricList.get(i).getLyricString(),width/2,tempY,notCurrentPaint);
            }
            canvas.drawText(myLyricList.get(index).getLyricString(),width/2,height/2,currentPaint);       //画出当前的句子


            tempY = height / 2;                                                                           //画出之后的句子
            for(int i =index + 1;i<myLyricList.size(); i ++){
                tempY += textHeight;
                canvas.drawText(myLyricList.get(i).getLyricString(),width/2,tempY,notCurrentPaint);
            }


        }
        catch(Exception e){
            setText("并没有发现歌词，请先下载.......");
        }
    }


    @Override
    protected void onSizeChanged(int w,int h,int oldW,int oldH){
        super.onSizeChanged(w,h,oldW,oldH);
        this.width = w;
        this.height = h;
    }
}
