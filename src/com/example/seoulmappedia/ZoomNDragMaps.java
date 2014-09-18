package com.example.seoulmappedia;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.MeasureSpec;

public class ZoomNDragMaps extends View implements OnGestureListener, OnScaleGestureListener {
	
	private Context mContext;

	public ZoomNDragMaps(Context context) {
		super(context);
		mContext = context; 
		initImagePositionerView();
		
	}
	
	
private static final String LOG_TAG = "ZoomNDragMaps";
	
	private Bitmap m_bitmap = null;
	private int m_bitmapWidth = 0;
	private int m_bitmapHeight = 0;
	private Paint m_paint = new Paint();
	private GestureDetector m_gestureDetector = null;
	private ScaleGestureDetector m_scaleGestureDetector = null;
	private float m_oldFocusX = 0;
	private float m_oldFocusY = 0;
	private Matrix m_matrix = new Matrix();
	private float m_aspectRatio = 4.0f/3.0f;
	private float m_insetSize = 0;
	private float m_cropWidth = 0;
	private float m_cropHeight = 0;
	private float m_cropLeft = 0;
	private float m_cropTop = 0;
	private float m_cropRight = 0;
	private float m_cropBottom = 0;
	
	public void setBitmap( int resourceId ) {
		setBitmap(BitmapFactory.decodeResource(mContext.getResources(), resourceId));
	}
	
	public void initImagePositionerView() {
		m_gestureDetector = new GestureDetector(mContext,this);
		m_scaleGestureDetector = new ScaleGestureDetector(mContext,this);
	}
	
	public void setInsetSize( float insetSize ) {
		// TODO:  Allow the top, left, bottom and right insets to be set individually
		m_insetSize = insetSize;
		invalidate();
	}
	
	public void setAspectRatio( float aspectRatio ) {
		m_aspectRatio = aspectRatio;
		invalidate();
	}
	
	public Matrix getMatrix() {
		return new Matrix(m_matrix);
	}
	
	public void setMatrix(Matrix matrix) {
		m_matrix = matrix;
		invalidate();
	}
	
	public RectF getCropRect() {
		// TODO:  This should return the crop rectangle IN THE COORDINATE SPACE OF THE IMAGE
		return new RectF();
	}
	
	public void setCropRect( RectF rect) {
		// TODO:  This should accept the crop rectangle IN THE COORDINATE SPACE OF THE IMAGE and set the matrix accordingly
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		boolean conusmed = false;
		conusmed = m_scaleGestureDetector.onTouchEvent(event) || conusmed;
		if( !m_scaleGestureDetector.isInProgress() )
			conusmed = m_gestureDetector.onTouchEvent(event) || conusmed;
		if( conusmed )
			return true;
		return super.onTouchEvent(event);
	}

	public void setBitmap( Bitmap bm ) {
		m_bitmap = bm;
		if( bm!=null ) {
			m_bitmapWidth = bm.getWidth();
			m_bitmapHeight = bm.getHeight();
		} else {
			m_bitmapWidth = 0;
			m_bitmapHeight = 0;
		}
		invalidate();
	}
	
	public Bitmap getBitmap() {
		return m_bitmap;
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		calculateCropRect();
		applyConstraints();
		
		if( m_bitmap!=null ) {
			canvas.drawBitmap(m_bitmap, m_matrix, m_paint);
		}
		
		// TODO: Change this to draw a black partially-transparent shadow outside the crop area,
		//       and white crop marks in the corners of the crop area.
		
		m_paint.setColor(0xFFFFFFFF);
		m_paint.setStyle(Style.STROKE);
		canvas.drawRect(m_cropLeft, m_cropTop, m_cropRight, m_cropBottom, m_paint);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int width = 100;
		int height = 100;
		
		switch( MeasureSpec.getMode(heightMeasureSpec) ) {
		case MeasureSpec.AT_MOST:
		case MeasureSpec.EXACTLY:
			setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
			break;
		case MeasureSpec.UNSPECIFIED:
		default:
			setMeasuredDimension(width, height);
			break;
		}
	}


	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
	{
		m_matrix.postTranslate(-distanceX, -distanceY);
		invalidate();
		
		return true;
	}


	public boolean onScale(ScaleGestureDetector detector)
	{
		
		m_matrix.postTranslate(detector.getFocusX() - m_oldFocusX, detector.getFocusY() - m_oldFocusY);
		m_oldFocusX = detector.getFocusX();
		m_oldFocusY = detector.getFocusY();
		m_matrix.postScale(detector.getScaleFactor(),detector.getScaleFactor(), detector.getFocusX(), detector.getFocusY());
		invalidate();
		return true;
	}

	public boolean onScaleBegin(ScaleGestureDetector detector)
	{
		m_oldFocusX = detector.getFocusX();
		m_oldFocusY = detector.getFocusY();
		return true;
	}
	
	private void applyConstraints() {
		applySizeConstraints();
		applyPositionConstraints();
	}
	
	private void applySizeConstraints() {
		
		float[] imagePoints = {0,0,m_bitmapWidth,m_bitmapHeight};
		m_matrix.mapPoints(imagePoints);
		
		float left		= imagePoints[0];
		float top		= imagePoints[1];
		float right		= imagePoints[2];
		float bottom	= imagePoints[3];
		float width		= right-left;
		float height	= bottom-top;
		
		if( width < m_cropWidth && height < m_cropHeight ) {
			float scale = Math.max(m_cropWidth/width, m_cropHeight/height);
			m_matrix.postScale(scale, scale, width/2f, height/2f);
		} else if( width < m_cropWidth ) { 
			m_matrix.postScale(m_cropWidth/width, m_cropWidth/width, width/2f, height/2f);
		} else if( height < m_cropHeight ) { 
			m_matrix.postScale(m_cropHeight/height, m_cropHeight/height, width/2f, height/2f);
		}
		
		// TODO:  At minimum size, trying to make the image smaller causes it to slide 
		//        downward.  I think this is because of the center point used in the postScale
		//        calls above.  This needs to be adjusted.
		
		// TODO:  Add constraint for maximum size, not just minimum
		
	}
	
	private void applyPositionConstraints() {
		
		float[] imagePoints = {0,0,m_bitmapWidth,m_bitmapHeight};
		m_matrix.mapPoints(imagePoints);
		
		float left		= imagePoints[0];
		float top		= imagePoints[1];
		float right		= imagePoints[2];
		float bottom	= imagePoints[3];
		
		// We assume that size constraints have already been applied,
		// so we only have to handle left OR right, not both (since
		// the image can't be narrower than m_cropRight-mCropLeft).
		if( left > m_cropLeft ) {
			m_matrix.postTranslate(m_cropLeft-left, 0);
		} else if( right < m_cropRight ) {
			m_matrix.postTranslate(m_cropRight-right, 0);
		}
		
		// See the comment above; this assumes size constraints
		// have already been applied.
		if( top > m_cropTop ) { 
			m_matrix.postTranslate(0, m_cropTop-top);
		} else if( bottom < m_cropBottom ) { 
			m_matrix.postTranslate(0, m_cropBottom-bottom);
		}
		
	}
	
	private void calculateCropRect() {
		m_cropWidth = getWidth()-m_insetSize*2.0f;
		m_cropHeight = getHeight()-m_insetSize*2.0f;
		
		if( m_cropHeight*m_aspectRatio > m_cropWidth ) {
			m_cropHeight = m_cropWidth/m_aspectRatio;
		} else {
			m_cropWidth = m_cropHeight*m_aspectRatio;
		}
		
		m_cropLeft = (getWidth() - m_cropWidth)/2f;
		m_cropTop = (getHeight() - m_cropHeight)/2f;
		m_cropRight = m_cropLeft + m_cropWidth;
		m_cropBottom = m_cropTop + m_cropHeight;
	}

	public boolean onDown(MotionEvent e)
	{
		return false;
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
	{
		// TODO:  Handle fling gesture to add momentum (this will require 
		//        using a timer or scheduling callbacks on a handler; see NexGallery 
		//        for an example of handling fling)
		return false;
	}

	public void onLongPress(MotionEvent e)
	{
	}

	public void onShowPress(MotionEvent e)
	{
	}

	public boolean onSingleTapUp(MotionEvent e)
	{
		return false;
	}

	public void onScaleEnd(ScaleGestureDetector detector)
	{
	}

	// TODO:  (low priority) Add support for rotation.  Rotation should be optional
	//        via a getRotationEnabled() and setRotationEnabled() method pair.  When near a
	//		  90-degree angle, rotation should snap to that angle.
	
	// TODO:  (low priority) Allow constraints to be overridden via setMinimumSize() and
	//        setMaximumSize().  If no minimum size is set, the crop rectangle should
	//        be used (current behavior).  If a minimum size is set that's smaller than
	//		  the crop rectangle, then the image should "snap" to the crop rectangle if
	//		  it is close, but otherwise honor the specified minimum size.  If rotation
	//		  is also enabled, snapping should happen only if the rotation is set at a 90-degree angle.
	
	// TODO:  (low priority) Add a setListener() method to allow a listener for changes
	//        to the image position.
	

}
