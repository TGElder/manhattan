package elder.manhattan.gui;


import elder.geometry.Point;
import elder.manhattan.graphics.CityDrawerLayer;

public class MouseListenerManager implements MouseListener
{
	
	private MouseListener listener;
	
	@Override
	public void onMove(Point cityPoint)
	{
		listener.onMove(cityPoint);
	}

	@Override
	public void onLeftClick(Point cityPoint)
	{
		listener.onLeftClick(cityPoint);
	}

	@Override
	public void onMiddleClick(Point cityPoint)
	{
		listener.onMiddleClick(cityPoint);
	}

	@Override
	public void onRightClick(Point cityPoint)
	{
		listener.onRightClick(cityPoint);
	}

	@Override
	public void onLeftDrag(Point screenOffset)
	{
		listener.onLeftDrag(screenOffset);
	}

	@Override
	public void onMiddleDrag(Point screenOffset)
	{
		listener.onMiddleDrag(screenOffset);
	}

	@Override
	public void onRightDrag(Point screenOffset)
	{
		listener.onRightDrag(screenOffset);
	}

	@Override
	public void onWheelUp()
	{
		listener.onWheelUp();
	}

	@Override
	public void onWheelDown()
	{
		listener.onWheelDown();
	}

	public MouseListener getListener()
	{
		return listener;
	}
	
	public void setListener(MouseListener listener)
	{
		this.listener = listener;
	}

}
