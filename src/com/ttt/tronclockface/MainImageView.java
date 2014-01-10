package com.ttt.tronclockface;

import java.util.Calendar;
import java.util.Vector;

import android.R.string;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Matrix;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Xfermode;
import android.util.Log;
import android.widget.ImageView;

public class MainImageView extends ImageView 
{
	Paint paint,neonPaint;
	private int glowColor=Color.argb(255,81, 255, 13);
	private float glowRadius=3;
	private int strokewidth=10;
	RadialGradient radialseconds_gradient,radialGradientOuter;
	SweepGradient seconds_gradient,gradientouter,minutes_gradient,wedgeGradient;
	
	int seconds;
	Calendar c;
	
	int[] colors = {Color.BLACK,glowColor};
	int[] colors2 = {Color.BLACK,Color.argb(255, 14, 239,199),Color.BLACK,Color.argb(255, 14, 239,199)};
	int[] colors3 = {Color.BLACK,Color.argb(255, 40 ,255 ,225)};
	
	
	float[] positions= {0f,0.13f,0.66f,1f};
	
	Point p1=new Point(30,30);
	Point p2=new Point(10,10);
	     
	Path myPath = new Path();
	private int startAngle;
	float radius = 120+1,center_x=160,center_y=160;
	
	private RectF oval2= new RectF();
	private RectF oval1= new RectF();
	private RectF oval3= new RectF();
	private RectF oval1_1= new RectF();
	private Bitmap bitmapShaderImg;
	private BitmapShader fillBMPshader;
	ComposeShader combinedShader;
	
	Bitmap second_wedge_bitmap,minute_wedge_bitmap;
	
	int rot_degrees=0;
	
	Vector <SweepGradient> sweepGradientsVec;
	Vector <RectF> ovalsVecF;
	Vector<Paint> paintsVec;
	Vector<int[]> ellipse_points;
	

	public MainImageView(Context context) {
		super(context);
		
		
		paint = new Paint();
	    paint.setStyle(Style.STROKE);
	    paint.setAntiAlias(true);
	    
	    paint.setDither(true);
	    paint.setStrokeWidth(strokewidth);
		
	    neonPaint= new Paint();
	    neonPaint.setStyle(Style.STROKE);
	    neonPaint.setAntiAlias(true);
	    neonPaint.setColor(glowColor);
	    
	    neonPaint.setStrokeWidth(3);
	    neonPaint.setMaskFilter(new BlurMaskFilter(glowRadius, Blur.NORMAL));//For Inner glow set Blur.INNER
	    neonPaint.setStrokeCap(Paint.Cap.ROUND); 
	    
	    
	    radialseconds_gradient=new RadialGradient(160, 160, 100, glowColor, Color.argb(0, 0,0,0), Shader.TileMode.REPEAT);
	    radialGradientOuter=new RadialGradient(160, 160, 100, Color.argb(255, 255, 255, 255), Color.argb(0, 0,0,0), Shader.TileMode.REPEAT);
	    
	    
//	    seconds_gradient = new SweepGradient(160, 160, colors , positions);
  
//	    gradientouter=new SweepGradient(160, 160, colors2, positions);
	    
	    colors2[0]=Color.parseColor("#00e1e1");
	    colors2[1]=Color.argb(125, 128, 128, 128);
	    colors2[2]=Color.parseColor("#007272");
	    colors2[3]=Color.parseColor("#004747");
	    
	    
	    minutes_gradient=new SweepGradient(160, 160, colors2, positions);
	    
//	    wedgeGradient=new SweepGradient(160, 160, colors3, positions);
	    
	    paint.setShader(seconds_gradient);
	    
	    oval1=new RectF(center_x - radius,center_y - radius,center_x + radius,center_y + radius);
	    radius=160-strokewidth-neonPaint.getStrokeWidth();
	    oval2=new RectF(center_x - radius,center_y - radius,center_x + radius,center_y + radius);
	  //  oval1_1=new RectF(center_x - radius,center_y - radius,center_x + radius,center_y + radius);
	    
	    radius=160-5*strokewidth-5*neonPaint.getStrokeWidth();
	    oval3=new RectF(center_x - radius,center_y - radius,center_x + radius,center_y + radius);
	    
	    neonPaint.setStrokeWidth(2);
	    
	    
		bitmapShaderImg = Bitmap.createBitmap(new int[] { 0xFFFFFFFF, 0xFFCCCCCC,
                0xFFCCCCCC, 0xFFFFFFFF }, 2, 2,
                Bitmap.Config.RGB_565);
		bitmapShaderImg=BitmapFactory.decodeResource(getResources(), R.drawable.stars);
		fillBMPshader = new BitmapShader(bitmapShaderImg,
       Shader.TileMode.REPEAT,
       Shader.TileMode.REPEAT);
		Matrix m = new Matrix();
		m.setScale(6, 6);
		fillBMPshader.setLocalMatrix(m);
	
//	combinedShader=new ComposeShader(fillBMPshader, seconds_gradient, Mode.SRC);

		second_wedge_bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.second_hand);
	  	  
	    // outer glow
	  
		// TODO Auto-generated constructor stub
	}
	
	public void onDraw(Canvas canvas)
	{
		
		c = Calendar.getInstance(); 
		   
//		positions[1]=c.get(Calendar.SECOND)/60;
		
		Log.d("FDSFDSF", "secs"+c.get(Calendar.SECOND));
		
	    canvas.save();
		
		canvas.translate(160, 160);
		canvas.rotate(rot_degrees+=3);
		canvas.translate(-160, -160); 
		canvas.drawColor(Color.BLACK);
//			
//		paint.setShader(seconds_gradient);
		paint.setStrokeWidth(strokewidth*5);

		paint.setColorFilter(null);
//		canvas.drawArc(oval1, 0, c.get(Calendar.SECOND)*6, false, paint);
		
//		paint.setShader(combinedShader);
		
		
		paint.setColor(glowColor);
		paint.setStrokeWidth(1);
		paint.setStyle(Style.FILL);
		paint.setShader(minutes_gradient);
		
		seconds=c.get(Calendar.SECOND) ;
		canvas.drawArc(oval1,(seconds -1)*6, 360, false, paint);
		
//		canvas.drawCircle(190, 190, 100, paint);
//		
		paint.setShader(null);
		paint.setColor(Color.BLACK);
//		canvas.drawArc(oval2, 0, c.get(Calendar.SECOND)*6, true, paint);
//		canvas.drawArc(oval3, 0, c.get(Calendar.SECOND)*6, false, paint);
//		 
		canvas.drawCircle(160, 160, 100, paint);
//		canvas.drawBitmap(second_wedge_bitmap, 0,0,new Paint());
		
		
		paint.setStrokeWidth(strokewidth*5);
		paint.setShader(wedgeGradient);
		paint.setAlpha(200);
//		canvas.drawArc(oval1, 0, c.get(Calendar.MINUTE)*6, false, paint);
		
		paint.setAlpha(255);
		
//		 paint.setStrokeWidth(strokewidth-3);
//		 paint.setColorFilter(new PorterDuffColorFilter(paint.getColor(), Mode.MULTIPLY));
////	    paint.setMaskFilter(new BlurMaskFilter(glowRadius, Blur.NORMAL));//For Inner glow set Blur.INNER
//	    canvas.drawArc(oval1_1, 0, c.get(Calendar.SECOND)*6, false, paint);
//	    paint.setMaskFilter(null);//For Inner glow set Blur.INNER
//		    
		
		paint.setShader(gradientouter);
		
		
		paint.setStrokeWidth(5);
		paint.setShader(gradientouter);
		
//		canvas.drawArc(oval2, 0, c.get(Calendar.HOUR)*30
//				, false, paint);
//		paint.setShader(minutes_gradient);
//		canvas.drawArc(oval3, 0, c.get(Calendar.MINUTE)*6
//				, false, paint);
//		
		
		
		
		canvas.rotate(90, 160, 160);
	
		
//		paint.setAlpha(100);
		paint.setStrokeWidth(5);
		
		
//		paint.setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFFAA00FF));
		
		
//		canvas.drawCircle(160, 160, (float) (160-strokewidth), neonPaint);
		paint.setShader(minutes_gradient);
		paint.setColorFilter(null);
//		
//		canvas.drawCircle(160, 160, (float) (160), paint);
//		
		invalidate();
		
	}
	
	public void initializeDataStructures()
	{
		
		int maxRadius=180,minRadius=50;
		
		sweepGradientsVec=new Vector<SweepGradient>();
		
		ovalsVecF=new Vector<RectF>();
		paintsVec=new Vector<Paint>();
		
		for (int i=minRadius;i<maxRadius;i++)
		{
			;
		}
		
		
		
		
	}
	

}
